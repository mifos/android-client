/*
 * Copyright (c) 2005-2011 Grameen Foundation USA
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an
 * explanation of the license and how it is applied.
 */

package org.mifos.androidclient.main.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.LoanAccountDetails;
import org.mifos.androidclient.entities.customer.AccountBasicInformation;
import org.mifos.androidclient.entities.customer.LoanAccountBasicInformation;
import org.mifos.androidclient.entities.customer.SavingsAccountBasicInformation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountsExpandableListAdapter extends BaseExpandableListAdapter {

    public static final int LOANS = 0;
    public static final int SAVINGS = 1;

    private Context mContext;
    private Map<Integer, String> mKeys;
    private Map<Integer, List<AccountBasicInformation>> mValues;

    public AccountsExpandableListAdapter(Context context, Map<String, List<AccountBasicInformation>> items) {
        mContext = context;
        splitItems(items);
    }

    public int getAccountTypeForGroup(int group) {
        AccountBasicInformation account = mValues.get(group).get(0);
        int type;
        if (account.getClass() == LoanAccountBasicInformation.class) {
            type = LOANS;
        } else {
            type = SAVINGS;
        }
        return type;
    }

    @Override
    public int getGroupCount() {
        return mKeys.size();
    }

    @Override
    public int getChildrenCount(int groupPos) {
        if (mValues.containsKey(groupPos)) {
            return mValues.get(groupPos).size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPos) {
        String group = null;
        if (mKeys.containsKey(groupPos)) {
            group = mKeys.get(groupPos);
        }
        return group;
    }

    @Override
    public Object getChild(int groupPos, int childPos) {
        AccountBasicInformation child = null;
        if (mValues.containsKey(groupPos)) {
            child = mValues.get(groupPos).get(childPos);
        }
        return child;
    }

    @Override
    public long getGroupId(int groupPos) {
        return 0;
    }

    @Override
    public long getChildId(int groupPos, int childPos) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPos, boolean isExpanded, View convertView, ViewGroup parent) {
        View row;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.simple_list_group, parent, false);
        } else {
            row = convertView;
        }
        String item = (String)getGroup(groupPos);
        if (item != null) {
            TextView label = (TextView)row.findViewById(R.id.simple_list_item_label);
            label.setText(item);
        }
        return row;
    }

    @Override
    public View getChildView(int groupPos, int childPos, boolean isLastChild, View convertView, ViewGroup parent) {
        View row = null;
        AccountBasicInformation item = (AccountBasicInformation)getChild(groupPos, childPos);
        if (item == null) {
            row = new TextView(mContext);
        } else {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (item.getClass() == SavingsAccountBasicInformation.class) {
                row = inflater.inflate(R.layout.savings_account_list_item, parent, false);
            } else if (item.getClass() == LoanAccountBasicInformation.class) {
                row = inflater.inflate(R.layout.loan_account_list_item, parent, false);
            }
            TextView text;
            if (item.getClass() == SavingsAccountBasicInformation.class) {
                text = (TextView)row.findViewById(R.id.savingsAccountListItem_accountName);
                text.setText(item.getPrdOfferingName() + ", " + item.getGlobalAccountNum());
                text = (TextView)row.findViewById(R.id.savingsAccountListItem_status);
                text.setText(item.getAccountStateName());
                text = (TextView)row.findViewById(R.id.savingsAccountListItem_balance);
                text.setText(((SavingsAccountBasicInformation)item).getSavingsBalance());
            } else if (item.getClass() == LoanAccountBasicInformation.class) {
                text = (TextView)row.findViewById(R.id.loanAccountListItem_accountName);
                text.setText(item.getPrdOfferingName() + ", " + item.getGlobalAccountNum());
                text = (TextView)row.findViewById(R.id.loanAccountListItem_status);
                text.setText(item.getAccountStateName());
                text = (TextView)row.findViewById(R.id.loanAccountListItem_outstandingBalance);
                text.setText(((LoanAccountBasicInformation)item).getOutstandingBalance());
                if (item.getAccountStateId() == LoanAccountDetails.ACC_STATE_PARTIAL_APPLICATION ||
                    item.getAccountStateId() == LoanAccountDetails.ACC_STATE_APPLICATION_APPROVED ||
                    item.getAccountStateId() == LoanAccountDetails.ACC_STATE_APPLICATION_PENDING_APPROVAL) {

                    text = (TextView)row.findViewById(R.id.loanAccountListItem_amountDue_label);
                    text.setVisibility(View.GONE);
                    text = (TextView)row.findViewById(R.id.loanAccountListItem_amountDue);
                    text.setVisibility(View.GONE);
                } else {
                    text = (TextView)row.findViewById(R.id.loanAccountListItem_amountDue);
                    text.setText(((LoanAccountBasicInformation)item).getTotalAmountDue());
                }
            }
        }
        return row;
    }

    @Override
    public boolean isChildSelectable(int groupPos, int childPos) {
        return true;
    }

    private void splitItems(Map<String, List<AccountBasicInformation>> items) {
        mKeys = new HashMap<Integer, String>();
        mValues = new HashMap<Integer, List<AccountBasicInformation>>();
        int i = 0;
        for (Map.Entry<String, List<AccountBasicInformation>> item : items.entrySet()) {
            mKeys.put(i, item.getKey());
            mValues.put(i, item.getValue());
            i++;
        }
    }

}

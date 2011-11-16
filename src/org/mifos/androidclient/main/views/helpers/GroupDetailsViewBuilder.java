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

package org.mifos.androidclient.main.views.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.customer.AccountBasicInformation;
import org.mifos.androidclient.entities.customer.GroupDetails;
import org.mifos.androidclient.entities.customer.LoanAccountBasicInformation;
import org.mifos.androidclient.entities.customer.SavingsAccountBasicInformation;
import org.mifos.androidclient.main.views.adapters.AccountsExpandableListAdapter;
import org.mifos.androidclient.templates.CustomerDetailsViewBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupDetailsViewBuilder implements CustomerDetailsViewBuilder {

    private Context mContext;
    private GroupDetails mDetails;

    public GroupDetailsViewBuilder(Context context, GroupDetails details) {
        mContext = context;
        mDetails = details;
    }

    @Override
    public View buildOverviewView() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.group_overview, null);
        return view;
    }

    @Override
    public View buildAccountsView() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.customer_accounts, null);
        ExpandableListView list = (ExpandableListView)view.findViewById(R.id.customerAccounts_list);

        TextView chargesAmountDue = (TextView)view.findViewById(R.id.loanAccounts_amountDue);
        chargesAmountDue.setText(mDetails.getCustomerAccountSummary().getNextDueAmount());

        Map<String, List<AccountBasicInformation>> items = new HashMap<String, List<AccountBasicInformation>>();
        if (mDetails.getLoanAccountsInUse()!= null && mDetails.getLoanAccountsInUse().size() > 0) {
            String loanLabel = mContext.getString(R.string.loan_label);
            List<AccountBasicInformation> loanAccounts = new ArrayList<AccountBasicInformation>();
            for (LoanAccountBasicInformation account : mDetails.getLoanAccountsInUse()) {
                loanAccounts.add(account);
            }
            items.put(loanLabel, loanAccounts);
        }
        if (mDetails.getSavingsAccountsInUse() != null && mDetails.getSavingsAccountsInUse().size() > 0) {
            String savingsLabel = mContext.getString(R.string.savings_label);
            List<AccountBasicInformation> savingsAccounts = new ArrayList<AccountBasicInformation>();
            for (SavingsAccountBasicInformation account : mDetails.getSavingsAccountsInUse()) {
                savingsAccounts.add(account);
            }
            items.put(savingsLabel, savingsAccounts);
        }
        if (items.size() > 0) {
            list.setAdapter(new AccountsExpandableListAdapter(mContext, items));
        }

        return view;
    }

    @Override
    public View buildAdditionalView() {
        return null;
    }

    private LayoutInflater getLayoutInflater() {
        return (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

}

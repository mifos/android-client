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
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.customer.AccountBasicInformation;
import org.mifos.androidclient.entities.customer.CenterDetails;
import org.mifos.androidclient.entities.customer.CustomerNote;
import org.mifos.androidclient.entities.customer.SavingsAccountBasicInformation;
import org.mifos.androidclient.main.views.adapters.AccountsExpandableListAdapter;
import org.mifos.androidclient.templates.CustomerDetailsViewBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CenterDetailsViewBuilder implements CustomerDetailsViewBuilder {

    private Context mContext;
    private CenterDetails mDetails;

    public CenterDetailsViewBuilder(Context context, CenterDetails details) {
        mContext = context;
        mDetails = details;
    }

    @Override
    public View buildOverviewView() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.center_overview, null);

        TextView textView = (TextView)view.findViewById(R.id.centerOverview_name);
        textView.setText(mDetails.getCenterDisplay().getDisplayName());
        textView = (TextView)view.findViewById(R.id.customerOverview_systemId);
        textView.setText(mDetails.getCenterDisplay().getGlobalCustNum());
        textView = (TextView)view.findViewById(R.id.customerOverview_status);
        textView.setText(mDetails.getCenterDisplay().getCustomerStatusName());

        textView = (TextView)view.findViewById(R.id.centerOverview_noOfActiveClients);
        textView.setText(mDetails.getCenterPerformanceHistory().getNumberOfClients().toString());
        textView = (TextView)view.findViewById(R.id.centerOverview_noOfActiveGroups);
        textView.setText(mDetails.getCenterPerformanceHistory().getNumberOfGroups().toString());
        textView = (TextView)view.findViewById(R.id.centerOverview_totalLoanPortfolio);
        textView.setText(mDetails.getCenterPerformanceHistory().getTotalOutstandingPortfolio());
        textView = (TextView)view.findViewById(R.id.centerOverview_totalSavings);
        textView.setText(mDetails.getCenterPerformanceHistory().getTotalSavings());

        return view;
    }

    @Override
    public View buildAccountsView() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.center_accounts, null);
        ExpandableListView list = (ExpandableListView)view.findViewById(R.id.customerAccounts_list);

        TextView chargesAmountDue = (TextView)view.findViewById(R.id.loanAccounts_amountDue);
        chargesAmountDue.setText(mDetails.getCustomerAccountSummary().getNextDueAmount());

        Map<String, List<AccountBasicInformation>> items = new HashMap<String, List<AccountBasicInformation>>();
        if (mDetails.getSavingsAccountsInUse() != null && mDetails.getSavingsAccountsInUse().size() > 0) {
            String savingsLabel = mContext.getString(R.string.savings_label);
            List<AccountBasicInformation> savingsAccounts = new ArrayList<AccountBasicInformation>();
            for (SavingsAccountBasicInformation account : mDetails.getSavingsAccountsInUse()) {
                savingsAccounts.add(account);
            }
            items.put(savingsLabel, savingsAccounts);
        }
        if (mDetails.getClosedSavingsAccounts()!=null && mDetails.getClosedSavingsAccounts().size() > 0) {
            String closedSavingsLabel = mContext.getString(R.string.closedSavingsLabel);
            List<AccountBasicInformation> closedSavingsAccounts = new ArrayList<AccountBasicInformation>();
            for (SavingsAccountBasicInformation account : mDetails.getClosedSavingsAccounts()) {
                closedSavingsAccounts.add(account);
            }
            items.put(closedSavingsLabel, closedSavingsAccounts);
        }
        if (items.size() > 0) {
            list.setAdapter(new AccountsExpandableListAdapter(mContext, items));
        }

        return view;
    }

    @Override
    public View buildAdditionalView() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.center_additional, null);

        TextView textView = (TextView)view.findViewById(R.id.centerAdditional_mfiJoining);
        textView.setText(mDetails.getCenterDisplay().getMfiJoiningDate());
        textView =(TextView)view.findViewById(R.id.centerAdditional_centerStart);
        textView.setText(mDetails.getCenterDisplay().getCreatedDate());
        textView =(TextView)view.findViewById(R.id.centerAdditional_loanOfficer_name);
        textView.setText(mDetails.getCenterDisplay().getLoanOfficerName());

        if(mDetails.getRecentCustomerNotes() != null && mDetails.getRecentCustomerNotes().size() > 0 ) {
            textView = (TextView)view.findViewById(R.id.centerAdditional_recentNotes_label);
            textView.setVisibility(View.VISIBLE);

           LinearLayout recentNotesLayout = (LinearLayout)view.findViewById(R.id.centerAdditional_recentNotes);
           ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
           for(CustomerNote note : mDetails.getRecentCustomerNotes()){
                textView = new TextView(mContext);
                textView.setLayoutParams(params);
                textView.setText(note.getComment() +" "+ note.getCommentDate() + " " + note.getPersonnelName());
                recentNotesLayout.addView(textView);
            }
        }

        return view;
    }

    private LayoutInflater getLayoutInflater() {
        return (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

}

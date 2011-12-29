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
import org.mifos.androidclient.entities.customer.*;
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

        TextView textView = (TextView)view.findViewById(R.id.groupOverview_name);
        textView.setText(mDetails.getGroupDisplay().getDisplayName());
        textView = (TextView)view.findViewById(R.id.customerOverview_systemId);
        textView.setText(mDetails.getGroupDisplay().getGlobalCustNum());
        textView = (TextView)view.findViewById(R.id.customerOverview_status);
        textView.setText(mDetails.getGroupDisplay().getCustomerStatusName());

        textView = (TextView)view.findViewById(R.id.groupOverview_noOfActiveClients);
        textView.setText(mDetails.getGroupPerformanceHistory().getActiveClientCount());
        textView = (TextView)view.findViewById(R.id.groupOverview_amountOfLastGroupLoan);
        textView.setText(mDetails.getGroupPerformanceHistory().getLastGroupLoanAmount());
        textView = (TextView)view.findViewById(R.id.groupOverview_averageIndividualLoanSize);
        textView.setText(mDetails.getGroupPerformanceHistory().getAvgLoanAmountForMember());
        textView = (TextView)view.findViewById(R.id.groupOverview_totalLoanPortfolio);
        textView.setText(mDetails.getGroupPerformanceHistory().getTotalOutStandingLoanAmount());
        textView = (TextView)view.findViewById(R.id.groupOverview_portfolioAtRisk);
        textView.setText(mDetails.getGroupPerformanceHistory().getPortfolioAtRisk());
        textView = (TextView)view.findViewById(R.id.groupOverview_totalSavings);
        textView.setText(mDetails.getGroupPerformanceHistory().getTotalSavingsAmount());

        if (mDetails.getGroupPerformanceHistory().getLoanCycleCounters().size() > 0) {
            textView = (TextView)view.findViewById(R.id.groupOverview_loanCyclePerProduct_label);
            textView.setVisibility(View.VISIBLE);

            LinearLayout loanCyclePerProduct = (LinearLayout)view.findViewById(R.id.groupOverview_loanCyclePerProduct);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            for (LoanCycleCounter counter : mDetails.getGroupPerformanceHistory().getLoanCycleCounters()) {
                textView = new TextView(mContext);
                textView.setLayoutParams(params);
                textView.setText(counter.getOfferingName() + ": " + counter.getCounter());
                loanCyclePerProduct.addView(textView);
            }
        }

        return view;
    }

    @Override
    public View buildAccountsView() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.group_accounts, null);
        ExpandableListView list = (ExpandableListView)view.findViewById(R.id.customerAccounts_list);

        TextView chargesAmountDue = (TextView)view.findViewById(R.id.loanAccounts_amountDue);
        chargesAmountDue.setText(mDetails.getCustomerAccountSummary().getNextDueAmount());

        Map<String, List<AccountBasicInformation>> items = new HashMap<String, List<AccountBasicInformation>>();
        if (mDetails.getClosedLoanAccounts()!= null && mDetails.getClosedLoanAccounts().size() > 0) {
            String closedLoanLabel = mContext.getString(R.string.closedLoanLabel);
            List<AccountBasicInformation> closedLoanAccount = new ArrayList<AccountBasicInformation>();
            for (LoanAccountBasicInformation account : mDetails.getClosedLoanAccounts()) {
                closedLoanAccount.add(account);
            }
            items.put(closedLoanLabel, closedLoanAccount);
        }
        if (mDetails.getLoanAccountsInUse()!= null && mDetails.getLoanAccountsInUse().size() > 0) {
            String loanLabel = mContext.getString(R.string.loan_label);
            List<AccountBasicInformation> loanAccounts = new ArrayList<AccountBasicInformation>();
            for (LoanAccountBasicInformation account : mDetails.getLoanAccountsInUse()) {
                loanAccounts.add(account);
            }
            items.put(loanLabel, loanAccounts);
        }
        if (mDetails.getClosedSavingsAccounts()!= null && mDetails.getClosedSavingsAccounts().size() > 0) {
            String closedLoanLabel = mContext.getString(R.string.closedSavingsLabel);
            List<AccountBasicInformation> closedSavingsAccount = new ArrayList<AccountBasicInformation>();
            for (SavingsAccountBasicInformation account : mDetails.getClosedSavingsAccounts()) {
                closedSavingsAccount.add(account);
            }
            items.put(closedLoanLabel, closedSavingsAccount);
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
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.group_additional, null);

        TextView textView = (TextView)view.findViewById(R.id.groupAdditional_approvalDate);
        textView.setText(mDetails.getGroupDisplay().getCustomerActivationDate());
        textView = (TextView)view.findViewById(R.id.groupAdditional_ExternalID);
        textView.setText(mDetails.getGroupDisplay().getExternalId().toString());
        textView = (TextView)view.findViewById(R.id.groupAdditional_recruitedBy);
        textView.setText(mDetails.getGroupDisplay().getCustomerFormedByDisplayName());
        textView = (TextView)view.findViewById(R.id.groupAdditional_Address);
        textView.setText(mDetails.getAddress().getDisplayAddress());


        if(mDetails.getRecentCustomerNotes() != null && mDetails.getRecentCustomerNotes().size() > 0 ) {
            textView = (TextView)view.findViewById(R.id.groupAdditional_recentNotes_label);
            textView.setVisibility(View.VISIBLE);

            LinearLayout recentNotesLayout = (LinearLayout)view.findViewById(R.id.groupAdditional_recentNotes);
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

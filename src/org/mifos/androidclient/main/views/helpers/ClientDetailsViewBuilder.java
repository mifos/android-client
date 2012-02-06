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
import android.widget.Button;
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

public class ClientDetailsViewBuilder implements CustomerDetailsViewBuilder {

    private Context mContext;
    private ClientDetails mDetails;

    public ClientDetailsViewBuilder(Context context, ClientDetails details) {
        mContext = context;
        mDetails = details;
    }

    @Override
    public View buildOverviewView() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.client_overview, null);

        TextView textView = (TextView)view.findViewById(R.id.customerOverview_name);
        textView.setText(mDetails.getClientDisplay().getDisplayName());
        textView = (TextView)view.findViewById(R.id.customerOverview_systemId);
        textView.setText(mDetails.getClientDisplay().getGlobalCustNum());
        textView = (TextView)view.findViewById(R.id.customerOverview_status);
        textView.setText(mDetails.getClientDisplay().getCustomerStatusName());

        textView = (TextView)view.findViewById(R.id.customerOverview_loanCycleNo);
        textView.setText(mDetails.getClientPerformanceHistory().getLoanCycleNumber().toString());
        textView = (TextView)view.findViewById(R.id.customerOverview_amountOfLastLoan);
        textView.setText(mDetails.getClientPerformanceHistory().getLastLoanAmount());
        textView = (TextView)view.findViewById(R.id.customerOverview_noOfActiveLoans);
        textView.setText(mDetails.getClientPerformanceHistory().getNoOfActiveLoans().toString());
        textView = (TextView)view.findViewById(R.id.customerOverview_delinquentPortfolio);
        textView.setText(mDetails.getClientPerformanceHistory().getDelinquentPortfolioAmount());
        textView = (TextView)view.findViewById(R.id.customerOverview_totalSavings);
        textView.setText(mDetails.getClientPerformanceHistory().getTotalSavingsAmount());
        textView = (TextView)view.findViewById(R.id.customerOverview_meetingsAttended);
        textView.setText(mDetails.getClientPerformanceHistory().getMeetingsAttended().toString());
        textView = (TextView)view.findViewById(R.id.customerOverview_meetingsMissed);
        textView.setText(mDetails.getClientPerformanceHistory().getMeetingsMissed().toString());

        if (mDetails.getClientPerformanceHistory().getLoanCycleCounters().size() > 0) {
            textView = (TextView)view.findViewById(R.id.customerOverview_loanCyclePerProduct_label);
            textView.setVisibility(View.VISIBLE);

            LinearLayout loanCyclePerProduct = (LinearLayout)view.findViewById(R.id.customerOverview_loanCyclePerProduct);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            for (LoanCycleCounter counter : mDetails.getClientPerformanceHistory().getLoanCycleCounters()) {
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
        View view = inflater.inflate(R.layout.customer_accounts, null);
        ExpandableListView list = (ExpandableListView)view.findViewById(R.id.customerAccounts_list);

        TextView chargesAmountDue = (TextView)view.findViewById(R.id.loanAccounts_amountDue);
        chargesAmountDue.setText(mDetails.getCustomerAccountSummary().getNextDueAmount());

        Button viewChargesDetailsButton = (Button)view.findViewById(R.id.view_chargesDetails_button);
        viewChargesDetailsButton.setVisibility(View.VISIBLE);


        Map<String, List<AccountBasicInformation>> items = new HashMap<String, List<AccountBasicInformation>>();
        if (mDetails.getLoanAccountsInUse()!= null && mDetails.getLoanAccountsInUse().size() > 0) {
            String loanLabel = mContext.getString(R.string.loan_label);
            List<AccountBasicInformation> loanAccounts = new ArrayList<AccountBasicInformation>();
            for (LoanAccountBasicInformation account : mDetails.getLoanAccountsInUse()) {
                loanAccounts.add(account);
            }
            items.put(loanLabel, loanAccounts);
        }
        if(mDetails.getLoanAccountsInUse() != null && mDetails.getClosedLoanAccounts().size() > 0){
            String closedLoanLabel = mContext.getString(R.string.closedLoanLabel);
            List<AccountBasicInformation> closedLoanAccounts = new ArrayList<AccountBasicInformation>();
            for(LoanAccountBasicInformation account: mDetails.getClosedLoanAccounts()) {
                closedLoanAccounts.add(account);
            }
            items.put(closedLoanLabel, closedLoanAccounts);
        }
        if (mDetails.getSavingsAccountsInUse() != null && mDetails.getSavingsAccountsInUse().size() > 0) {
            String savingsLabel = mContext.getString(R.string.savings_label);
            List<AccountBasicInformation> savingsAccounts = new ArrayList<AccountBasicInformation>();
            for (SavingsAccountBasicInformation account : mDetails.getSavingsAccountsInUse()) {
                savingsAccounts.add(account);
            }
            items.put(savingsLabel, savingsAccounts);
        }
        if(mDetails.getClosedSavingsAccounts() != null && mDetails.getClosedSavingsAccounts().size() > 0){
            String closedSavingsLabel = mContext.getString(R.string.closedSavingsLabel);
            List<AccountBasicInformation> closedSavingsAccounts = new ArrayList<AccountBasicInformation>();
            for(SavingsAccountBasicInformation account: mDetails.getClosedSavingsAccounts()) {
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
        View view = inflater.inflate(R.layout.client_additional, null);

        TextView textView = (TextView)view.findViewById(R.id.customerAdditional_customerActivation);
        textView.setText(mDetails.getClientDisplay().getCustomerActivationDate());
        textView = (TextView)view.findViewById(R.id.customerAdditional_RecruitByDisplayName);
        textView.setText(mDetails.getClientDisplay().getCustomerFormedByDisplayName());
        textView = (TextView)view.findViewById(R.id.customerAdditional_dateOfBirthDay);
        textView.setText(mDetails.getClientDisplay().getDateOfBirth());
        textView =(TextView)view.findViewById(R.id.customerAdditional_ethinicity);
        textView.setText(mDetails.getClientDisplay().getEthnicity());
        textView = (TextView)view.findViewById(R.id.customerAdditional_educationLevel);
        textView.setText(mDetails.getClientDisplay().getEducationLevel());
        textView = (TextView)view.findViewById(R.id.customerAdditional_citizenship);
        textView.setText(mDetails.getClientDisplay().getCitizenship());

        if(mDetails.getRecentCustomerNotes() != null && mDetails.getRecentCustomerNotes().size() > 0 ) {
            textView = (TextView)view.findViewById(R.id.customerAdditional_recentNotes_label);
            textView.setVisibility(View.VISIBLE);

            LinearLayout recentNotesLayout = (LinearLayout)view.findViewById(R.id.customerAdditional_recentNotes);
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

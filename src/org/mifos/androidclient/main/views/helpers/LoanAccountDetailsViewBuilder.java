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
import android.provider.ContactsContract;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.LoanAccountDetails;
import org.mifos.androidclient.entities.account.LoanActivity;
import org.mifos.androidclient.entities.customer.CustomerNote;
import org.mifos.androidclient.templates.AccountDetailsViewBuilder;
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoanAccountDetailsViewBuilder implements AccountDetailsViewBuilder {

    private Context mContext;
    private LoanAccountDetails mDetails;

    public LoanAccountDetailsViewBuilder(Context context, LoanAccountDetails details) {
        mContext = context;
        mDetails = details;
    }

    @Override
    public View buildOverviewView() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.account_loan_overview, null);

        prepareAccountInformation(view);
        prepareAccountSummary(view);


        TableLayout LoanRecentActivityLayout = (TableLayout)view.findViewById(R.id.tableLoan_recentActivity);
        if(mDetails.getRecentAccountActivity() !=null && mDetails.getRecentAccountActivity().size() > 0){

            prepareLoanActivityTable(view, LoanRecentActivityLayout);
        }

        if(mDetails.getRecentNoteDtos() !=null && mDetails.getRecentNoteDtos().size() > 0){
            prepareRecentNotes(view);
        }

        preparePerformanceHistory(view);

        return view;
    }

    @Override
    public View buildDetailsView() {
        TextView view = new TextView(mContext);
        view.setText(mContext.getString(R.string.not_implemented_yet_message));
        view.setTextAppearance(mContext, R.style.Info);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        view.setLayoutParams(params);
        view.setGravity(Gravity.CENTER);
        return view;
    }


    private void preparePerformanceHistory(View view) {
        TextView textView = (TextView)view.findViewById(R.id.accountOverviewLoan_noOfPayments);
        textView.setText(mDetails.getPerformanceHistory().getNoOfPayments().toString());
        textView = (TextView)view.findViewById(R.id.accountOverviewLoan_totalNoOfMissedPayments);
        textView.setText(mDetails.getPerformanceHistory().getTotalNoOfMissedPayments().toString());
        textView = (TextView)view.findViewById(R.id.accountOverviewLoan_daysInArrears);
        textView.setText(mDetails.getPerformanceHistory().getDaysInArrears().toString());
        textView = (TextView)view.findViewById(R.id.accountOverviewLoan_loanMaturityDate);
        textView.setText(mDetails.getPerformanceHistory().getLoanMaturityDate());
    }

    private void prepareRecentNotes(View view) {
        TextView textView;
        textView = (TextView)view.findViewById(R.id.accountOverviewLoan_recentNotes_label);
        textView.setVisibility(View.VISIBLE);
        LinearLayout recentNotesLayout = (LinearLayout)view.findViewById(R.id.accountOverviewLoan_recentNotes);
        for(CustomerNote accNote: mDetails.getRecentNoteDtos()) {
                textView = new TextView(mContext);
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                textView.setText(accNote.getComment() +" "+ accNote.getCommentDate() + " " + accNote.getPersonnelName());
                recentNotesLayout.addView(textView);
        }
    }



    private void prepareAccountInformation(View view) {
        TextView textView = (TextView)view.findViewById(R.id.accountOverviewLoan_accountName);
        textView.setText(mDetails.getPrdOfferingName());
        textView = (TextView)view.findViewById(R.id.accountOverviewLoan_accountNumber);
        textView.setText("#" + mDetails.getGlobalAccountNum());
        textView = (TextView)view.findViewById(R.id.accountOverviewLoan_status);
        textView.setText(mDetails.getAccountStateName());
        textView =(TextView)view.findViewById(R.id.accountOverviewLoan_disbursalDate);
        long sDate = Long.parseLong(mDetails.getDisbursementDate());
        Date date = new Date(sDate);
        DateFormat df = new SimpleDateFormat("dd/MM/yyy");
        textView.setText(df.format(date).toString());
    }

    private void prepareAccountSummary(View view) {
        TextView textView;
        textView = (TextView)view.findViewById(R.id.accountOverviewLoan_totalAmountDueOn);
        textView.setText(mDetails.getNextMeetingDate() + ": " + mDetails.getTotalAmountDue());
        textView = (TextView)view.findViewById(R.id.accountOverviewLoan_amountInArrears);
        textView.setText(mDetails.getTotalAmountInArrears());
        textView = (TextView)view.findViewById(R.id.accountOverviewLoan_originalPrincipal);
        textView.setText(mDetails.getLoanSummary().getOriginalPrincipal());
        textView = (TextView)view.findViewById(R.id.accountOverviewLoan_principalPaid);
        textView.setText(mDetails.getLoanSummary().getPrincipalPaid());
        textView = (TextView)view.findViewById(R.id.accountOverviewLoan_principalDue);
        textView.setText(mDetails.getLoanSummary().getPrincipalDue());
        textView =(TextView)view.findViewById(R.id.accountOverviewLoan_originalInterest);
        textView.setText(mDetails.getLoanSummary().getOriginalInterest());
        textView =(TextView)view.findViewById(R.id.accountOverviewLoan_interestPaid);
        textView.setText(mDetails.getLoanSummary().getInterestPaid());
        textView =(TextView)view.findViewById(R.id.accountOverviewLoan_interestDue);
        textView.setText(mDetails.getLoanSummary().getInterestDue());
        textView =(TextView)view.findViewById(R.id.accountOverviewLoan_originalFees);
        textView.setText(mDetails.getLoanSummary().getOriginalFees());
        textView =(TextView)view.findViewById(R.id.accountOverviewLoan_feesPaid);
        textView.setText(mDetails.getLoanSummary().getFeesPaid());
        textView =(TextView)view.findViewById(R.id.accountOverviewLoan_feesDue);
        textView.setText(mDetails.getLoanSummary().getFeesDue());
        textView =(TextView)view.findViewById(R.id.accountOverviewLoan_originalPenalty);
        textView.setText(mDetails.getLoanSummary().getOriginalPenalty());
        textView =(TextView)view.findViewById(R.id.accountOverviewLoan_penaltyPaid);
        textView.setText(mDetails.getLoanSummary().getPenaltyDue());
        textView =(TextView)view.findViewById(R.id.accountOverviewLoan_penaltyDue);
        textView.setText(mDetails.getLoanSummary().getPenaltyDue());
        textView =(TextView)view.findViewById(R.id.accountOverviewLoan_originalTotal);
        textView.setText(mDetails.getLoanSummary().getTotalLoanAmnt());
        textView =(TextView)view.findViewById(R.id.accountOverviewLoan_totalPaid);
        textView.setText(mDetails.getLoanSummary().getTotalAmntPaid());
        textView =(TextView)view.findViewById(R.id.accountOverviewLoan_totalDue);
        textView.setText(mDetails.getLoanSummary().getTotalAmntDue());
    }

    private void prepareLoanActivityTable(View view, TableLayout loanRecentActivityLayout) {
        TextView textView;
        long sDate;Date date;DateFormat df;
        textView = (TextView)view.findViewById(R.id.accountOverviewLoan_recentActivity_label);
        textView.setVisibility(View.VISIBLE);

        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT);

        TableRow tableRow = new TableRow(mContext);
        tableRow.setLayoutParams(params);
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(R.string.tableLoan_recentActivity_actionDate);
        tableRow.addView(textView);
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(R.string.tableLoan_recentActivity_activity);
        tableRow.addView(textView);
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(R.string.tableLoan_recentActivity_principal);
        tableRow.addView(textView);
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(R.string.tableLoan_recentActivity_interest);
        tableRow.addView(textView);
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(R.string.tableLoan_recentActivity_fees);
        tableRow.addView(textView);
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(R.string.tableLoan_recentActivity_penalty);
        tableRow.addView(textView);
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(R.string.tableLoan_recentActivity_total);
        tableRow.addView(textView);
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(R.string.tableLoan_recentActivity_principalRunning);
        tableRow.addView(textView);
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(R.string.tableLoan_recentActivity_interestRunning);
        tableRow.addView(textView);
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(R.string.tableLoan_recentActivity_feesRunning);
        tableRow.addView(textView);
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(R.string.tableLoan_recentActivity_totalRunning);
        tableRow.addView(textView);
        loanRecentActivityLayout.addView(tableRow);

        for(LoanActivity loanActivity: mDetails.getRecentAccountActivity()){
            tableRow = new TableRow(mContext);
            tableRow.setLayoutParams(params);
            textView = new TextView(mContext);
            textView.setGravity(Gravity.RIGHT);
            sDate = Long.parseLong(loanActivity.getActionDate());
            date = new Date(sDate);
            df = new SimpleDateFormat("dd/MM/yyy");
            textView.setText(df.format(date).toString());
            tableRow.addView(textView);
            textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(loanActivity.getActivity());
            tableRow.addView(textView);
            textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(loanActivity.getPrincipal());
            tableRow.addView(textView);
            textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(loanActivity.getInterest());
            tableRow.addView(textView);
            textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(loanActivity.getFees());
            tableRow.addView(textView);
            textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(loanActivity.getPenalty());
            tableRow.addView(textView);
            textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(loanActivity.getTotal());
            tableRow.addView(textView);
            textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(loanActivity.getRunningBalancePrinciple());
            tableRow.addView(textView);
            textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(loanActivity.getRunningBalanceInterest());
            tableRow.addView(textView);
            textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(loanActivity.getRunningBalanceFees());
            tableRow.addView(textView);
            textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(loanActivity.getTotalValue().toString());
            tableRow.addView(textView);
            loanRecentActivityLayout.addView(tableRow);

        }
    }



    private LayoutInflater getLayoutInflater() {
        return (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

}

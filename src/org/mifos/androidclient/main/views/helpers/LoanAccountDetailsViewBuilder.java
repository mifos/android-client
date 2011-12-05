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
import android.database.DatabaseUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.AccountFee;
import org.mifos.androidclient.entities.account.LoanAccountDetails;
import org.mifos.androidclient.entities.account.LoanActivity;
import org.mifos.androidclient.entities.customer.CustomerNote;
import org.mifos.androidclient.templates.AccountDetailsViewBuilder;
import org.mifos.androidclient.util.ui.DateUtils;


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
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.account_loan_details, null);
        prepareAccountDetails(view);
        return view;
    }

    private void prepareAccountDetails(View view) {
        String string,substring;
        TextView textView;
        textView = (TextView)view.findViewById(R.id.accountDetailsLoan_interestRateType);
        string = mDetails.getInterestTypeName();
        substring = string.substring(14);
        textView.setText(substring);
        textView = (TextView)view.findViewById(R.id.accountDetailsLoan_interestRate);
        textView.setText(mDetails.getInterestRate().toString());
        textView = (TextView)view.findViewById(R.id.accountDetailsLoan_interestDeductedAtDisbursement);
        if(mDetails.getInterestDeductedAtDisbursement() == false){
            textView.setText(mContext.getString(R.string.no));
        }
        else textView.setText(mContext.getString(R.string.yes));
        textView = (TextView)view.findViewById(R.id.accountDetailsLoan_frequencyOfInstallments);
        if(mDetails.getRecurrenceId() == 1){
        textView.setText(mDetails.getRecurAfter().toString() + mContext.getString(R.string.weeks));
        }
        else textView.setText(mDetails.getRecurAfter().toString() + mContext.getString(R.string.months));
        textView = (TextView)view.findViewById(R.id.accountDetailsLoan_PrincipalDueOnLastInstallment);
        if(mDetails.getPrinDueLastInst() == false){
            textView.setText(mContext.getString(R.string.no));
        }
        else textView.setText(mContext.getString(R.string.yes));
        string = mDetails.getGracePeriodTypeName();
        substring = string.substring(17);
        textView =(TextView)view.findViewById(R.id.accountDetailsLoan_GracePeriodType);
        textView.setText(substring);
        textView = (TextView)view.findViewById(R.id.accountDetailsLoan_NoOfInstallments);
        textView.setText(mDetails.getNoOfInstallments().toString());
        textView = (TextView)view.findViewById(R.id.accountDetailsLoan_GracePeriodForRepayments);
        textView.setText(mDetails.getGracePeriodDuration().toString());
        textView = (TextView)view.findViewById(R.id.accountDetailsLoan_SourceOfFund);
        textView.setText(mDetails.getFundName());
        textView = (TextView)view.findViewById(R.id.accountDetailsLoan_CollateralType);
        textView.setText(mDetails.getCollateralTypeName());
        textView = (TextView)view.findViewById(R.id.accountDetailsLoan_CollateralNotes);
        textView.setText(mDetails.getCollateralNote());
        textView = (TextView)view.findViewById(R.id.accountDetailsLoan_ExternalID);
        textView.setText(mDetails.getExternalId());


        LinearLayout recurringAccountFeesLayout = (LinearLayout)view.findViewById(R.id.accountDetailsLoan_RecurringAccountFees);
        LinearLayout oneTimeAccountFeesLayout = (LinearLayout)view.findViewById(R.id.accountDetailsLoan_OneTimeAccountFees);

        for(AccountFee fee: mDetails.getAccountFees()){
            if(fee.getMeetingRecurrence() != null){
                    textView = (TextView)view.findViewById(R.id.accountDetailsLoan_RecurringAccountFees_label);
                    if(textView.getVisibility() == View.INVISIBLE){
                    textView.setVisibility(View.VISIBLE);
                    }
            textView = new TextView(mContext);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setText(fee.getFeeName() + ": " + fee.getAccountFeeAmount().toString() + " (  " + mContext.getString(R.string.recurEvery) + fee.getMeetingRecurrence() + ")");
            recurringAccountFeesLayout.addView(textView);
            }
        }
        for(AccountFee fee: mDetails.getAccountFees()){
            if(fee.getMeetingRecurrence() == null){
                    textView = (TextView)view.findViewById(R.id.accountDetailsLoan_OneTimeAccountFees_label);
                    if(textView.getVisibility() == View.INVISIBLE){
                    textView.setVisibility(View.VISIBLE);
                    }
            textView = new TextView(mContext);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setText(fee.getFeeName() + ": " + fee.getAccountFeeAmount().toString());
            oneTimeAccountFeesLayout.addView(textView);
            }
        }

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
        String string,substring;
        TextView textView = (TextView)view.findViewById(R.id.accountOverviewLoan_accountName);
        textView.setText(mDetails.getPrdOfferingName());
        textView = (TextView)view.findViewById(R.id.accountOverviewLoan_accountNumber);
        textView.setText("#" + mDetails.getGlobalAccountNum());
        textView = (TextView)view.findViewById(R.id.accountOverviewLoan_status);
        string = mDetails.getAccountStateName();
        substring = string.substring(13);
        textView.setText(substring);
        textView =(TextView)view.findViewById(R.id.accountOverviewLoan_disbursalDate);
        long sDate = Long.parseLong(mDetails.getDisbursementDate());
        textView.setText(DateUtils.format(sDate));
    }

    private void prepareAccountSummary(View view) {
        TextView textView;

        if (mDetails.getAccountStateId() == LoanAccountDetails.ACC_STATE_PARTIAL_APPLICATION ||
            mDetails.getAccountStateId() == LoanAccountDetails.ACC_STATE_APPLICATION_APPROVED ||
            mDetails.getAccountStateId() == LoanAccountDetails.ACC_STATE_APPLICATION_PENDING_APPROVAL) {

            textView = (TextView)view.findViewById(R.id.accountOverviewLoan_totalAmountDueOn_label);
            textView.setVisibility(View.GONE);
            textView = (TextView)view.findViewById(R.id.accountOverviewLoan_totalAmountDueOn);
            textView.setVisibility(View.GONE);
            textView = (TextView)view.findViewById(R.id.accountOverviewLoan_amountInArrears_label);
            textView.setVisibility(View.GONE);
            textView = (TextView)view.findViewById(R.id.accountOverviewLoan_amountInArrears);
            textView.setVisibility(View.GONE);
        } else {
            textView = (TextView)view.findViewById(R.id.accountOverviewLoan_totalAmountDueOn);
            textView.setText(mDetails.getNextMeetingDate() + ": " + mDetails.getTotalAmountDue());
            textView = (TextView)view.findViewById(R.id.accountOverviewLoan_amountInArrears);
            textView.setText(mDetails.getTotalAmountInArrears());
        }
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
        long sDate;
        textView = (TextView)view.findViewById(R.id.accountOverviewLoan_recentActivity_label);
        textView.setVisibility(View.VISIBLE);
        textView = (TextView)view.findViewById(R.id.arrows);
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
            textView.setText(DateUtils.format(sDate));
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

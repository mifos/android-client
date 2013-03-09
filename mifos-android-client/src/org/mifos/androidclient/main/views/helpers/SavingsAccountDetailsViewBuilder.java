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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.SavingsAccountDetails;
import org.mifos.androidclient.entities.account.SavingsActivity;
import org.mifos.androidclient.entities.customer.CustomerNote;
import org.mifos.androidclient.templates.AccountDetailsViewBuilder;
import org.mifos.androidclient.util.ui.DateUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SavingsAccountDetailsViewBuilder implements AccountDetailsViewBuilder {

    private Context mContext;
    private SavingsAccountDetails mDetails;

    public SavingsAccountDetailsViewBuilder(Context context, SavingsAccountDetails details) {
        mContext = context;
        mDetails = details;
    }

    @Override
    public View buildOverviewView() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.account_savings_overview, null);

        prepareAccountInformation(view);

        TableLayout savingsRecentActivityLayout = (TableLayout)view.findViewById(R.id.tableSavings_recentActivity);

        if(mDetails.getRecentActivity() !=null && mDetails.getRecentActivity().size() > 0){

            prepareSavingsRecentActivityTable(view, savingsRecentActivityLayout);
        }

        if(mDetails.getRecentNoteDtos() !=null && mDetails.getRecentNoteDtos().size() > 0){
            prepareRecentNotes(view);
        }

        preparePerformanceHistory(view);

        return view;
    }

    @Override
    public View buildTransactionView() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.account_savings_transaction, null);
        TextView tv = (TextView)view.findViewById(R.id.no_savings_operation);
        if (mDetails.getAccountStateName().contentEquals("SAVINGS_PARTIAL_APPLICATION")
                || mDetails.getAccountStateName().contentEquals("SAVINGS_PENDING_APPROVAL")
                || mDetails.getAccountStateName().contentEquals("SAVINGS_CLOSED")) {
            View button = view.findViewById(R.id.accountSavings_applyAdjustment);
            button.setVisibility(View.GONE);
            button = view.findViewById(R.id.accountSavings_savingsTransaction);
            button.setVisibility(View.GONE);
            tv.setText(mContext.getString(R.string.no_operation_available));
            tv.setVisibility(View.VISIBLE);
        }
        if (mDetails.getAccountStateName().contentEquals("SAVINGS_ACTIVE")
                || mDetails.getAccountStateName().contentEquals("SAVINGS_INACTIVE")) {
            View button = view.findViewById(R.id.accountSavings_applyAdjustment);
            button.setVisibility(View.VISIBLE);
            button = view.findViewById(R.id.accountSavings_savingsTransaction);
            button.setVisibility(View.VISIBLE);
            tv.setText(mContext.getString(R.string.no_operation_available));
            tv.setVisibility(View.GONE);
        }
        return view;  //To change body of implemented methods use File | Settings | File Templates.
   }


    @Override
    public View buildDetailsView() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.account_savings_details, null);

        prepareAccountDetails(view);

        return view;
    }

    private void prepareAccountDetails(View view) {
        TextView textView;
        textView = (TextView)view.findViewById(R.id.accountDetailsSavings_recommendedOrMandatoryAmount);
        textView.setText(mDetails.getProductDetails().getAmountForDeposit().toString());
        textView = (TextView)view.findViewById(R.id.accountDetailsSavings_depositTypeName);
        textView.setText(mDetails.getDepositTypeName());
        textView = (TextView)view.findViewById(R.id.accountDetailsSavings_maxWithdrawal);
        textView.setText(mDetails.getProductDetails().getMaxWithdrawal().toString());
        textView = (TextView)view.findViewById(R.id.accountDetailsSavings_interestRate);
        textView.setText(mDetails.getProductDetails().getInterestRate().toString());
    }

    private void prepareRecentNotes(View view) {
        TextView textView;
        textView = (TextView)view.findViewById(R.id.accountOverviewSavings_recentNotes_label);
        textView.setVisibility(View.VISIBLE);
        LinearLayout recentNotesLayout = (LinearLayout)view.findViewById(R.id.accountOverviewSavings_recentNotes);
        for(CustomerNote accNote: mDetails.getRecentNoteDtos()){
            textView = new TextView(mContext);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setText(accNote.getComment() + " " + accNote.getCommentDate() + " " + accNote.getPersonnelName());
            recentNotesLayout.addView(textView);

        }
    }

    private void preparePerformanceHistory(View view) {
        TextView textView = (TextView)view.findViewById(R.id.accountOverviewSavings_openDate);
        long sDate;
        if(mDetails.getPerformanceHistory().getOpenedDate() != null){
        sDate = Long.parseLong(mDetails.getPerformanceHistory().getOpenedDate());
        textView.setText(DateUtils.format(sDate));
        }
        textView = (TextView)view.findViewById(R.id.accountOverviewSavings_totalDeposits);
        textView.setText(mDetails.getPerformanceHistory().getTotalDeposits().toString());
        textView = (TextView)view.findViewById(R.id.accountOverviewSavings_totalInterestEarned);
        textView.setText(mDetails.getPerformanceHistory().getTotalInterestEarned().toString());
        textView = (TextView)view.findViewById(R.id.accountOverviewSavings_totalWithdrawals);
        textView.setText(mDetails.getPerformanceHistory().getTotalWithdrawals().toString());
        textView.setText(mDetails.getPerformanceHistory().getMissedDeposits());
    }

    private void prepareSavingsRecentActivityTable(View view, TableLayout savingsRecentActivityLayout) {
        TextView textView;
        textView = (TextView)view.findViewById(R.id.accountOverviewSavings_recentActivity_label);
        textView.setVisibility(View.VISIBLE);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT);

        TableRow tableRow = new TableRow(mContext);
        tableRow.setLayoutParams(params);
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(R.string.tableSavings_recentActivity_date_label);
        tableRow.addView(textView);
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(R.string.tableSavings_recentActivity_description_label);
        tableRow.addView(textView);
        textView = new TextView(mContext);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setText(R.string.tableSavings_recentActivity_amount_label);
        tableRow.addView(textView);
        savingsRecentActivityLayout.addView(tableRow);

        for(SavingsActivity savingsActivity: mDetails.getRecentActivity()){
            tableRow = new TableRow(mContext);
            tableRow.setLayoutParams(params);
            textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            long sDate = Long.parseLong(savingsActivity.getActionDate());
            Date date = new Date(sDate);
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            textView.setText(df.format(date).toString());
            tableRow.addView(textView);

            textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(" " + savingsActivity.getActivity() + " ");
            tableRow.addView(textView);

            textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER_HORIZONTAL);
            textView.setText(savingsActivity.getAmount());
            tableRow.addView(textView);
            savingsRecentActivityLayout.addView(tableRow);
        }
    }

    private void prepareAccountInformation(View view) {
        String string,substring;
        TextView textView = (TextView)view.findViewById(R.id.accountOverviewSavings_accountName);
        textView.setText(mDetails.getProductDetails().getProductDetails().getName());
        textView = (TextView)view.findViewById(R.id.accountOverviewSavings_accountNumber);
        textView.setText("#" + mDetails.getGlobalAccountNum());
        textView = (TextView)view.findViewById(R.id.accountOverviewSavings_accountStatus);
        string = mDetails.getAccountStateName();
        substring = string.substring(8);
        textView.setText(substring);
        textView = (TextView)view.findViewById(R.id.accountOverviewSavings_accountBalance);
        textView.setText(mDetails.getAccountBalance().toString());
        textView = (TextView)view.findViewById(R.id.accountOverviewSavings_totalAmountDue);
        textView.setText(mDetails.getDueDate() + ":  " + mDetails.getTotalAmountDue().toString());
    }



        private LayoutInflater getLayoutInflater() {
        return (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

}

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

package org.mifos.androidclient.main;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.AbstractAccountDetails;
import org.mifos.androidclient.entities.account.loan.LoanInstallmentDetails;
import org.mifos.androidclient.net.services.AccountService;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.mifos.androidclient.util.ui.DateUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;

public class LoanInstallmentDetailsActivity extends DownloaderActivity {

    private String mAccountNumber;
    private LoanInstallmentDetailsTask mLoanInstallmentDetailsTask;
    private LoanInstallmentDetails mDetails;
    private AccountService mAccountService;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.loan_installment_details);

        if (bundle != null) {
            if (bundle.containsKey(LoanInstallmentDetails.BUNDLE_KEY)) {
                mDetails = (LoanInstallmentDetails)bundle.getSerializable(LoanInstallmentDetails.BUNDLE_KEY);
            }
        }
        mAccountNumber = getIntent().getStringExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY);
        mAccountService = new AccountService(this);
    }

    @Override
    protected void onSessionActive() {
        super.onSessionActive();
        if (mDetails == null) {
            runLoanInstallmentDetailsTask();
        } else {
            updateContent(mDetails);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoanInstallmentDetailsTask != null) {
            mLoanInstallmentDetailsTask.terminate();
            mLoanInstallmentDetailsTask = null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(LoanInstallmentDetails.BUNDLE_KEY, mDetails);
    }

    private void runLoanInstallmentDetailsTask() {
        if (StringUtils.hasLength(mAccountNumber)) {
            if (mLoanInstallmentDetailsTask == null || mLoanInstallmentDetailsTask.getStatus() != AsyncTask.Status.RUNNING) {
                mLoanInstallmentDetailsTask = new LoanInstallmentDetailsTask(
                        this,
                        getString(R.string.dialog_getting_installment_details),
                        getString(R.string.dialog_loading_message)
                );
                mLoanInstallmentDetailsTask.execute(mAccountNumber);
            }
        }
    }

    private void updateContent(LoanInstallmentDetails details) {
        mDetails = details;
        TextView field;

        field = (TextView)findViewById(R.id.loanInstallmentDetails_currentPrincipal);
        field.setText(details.getUpcomingInstallmentDetails().getPrincipal().toString());
        field = (TextView)findViewById(R.id.loanInstallmentDetails_currentInterest);
        field.setText(details.getUpcomingInstallmentDetails().getInterest().toString());
        field = (TextView)findViewById(R.id.loanInstallmentDetails_currentFees);
        field.setText(details.getUpcomingInstallmentDetails().getFees().toString());
        field = (TextView)findViewById(R.id.loanInstallmentDetails_currentPenalty);
        field.setText(details.getUpcomingInstallmentDetails().getPenalty().toString());
        field = (TextView)findViewById(R.id.loanInstallmentDetails_currentSubTotal);
        field.setText(details.getUpcomingInstallmentDetails().getSubTotal().toString());

        field = (TextView)findViewById(R.id.loanInstallmentDetails_overduePrincipal);
        field.setText(details.getOverDueInstallmentDetails().getPrincipal().toString());
        field = (TextView)findViewById(R.id.loanInstallmentDetails_overdueInterest);
        field.setText(details.getOverDueInstallmentDetails().getInterest().toString());
        field = (TextView)findViewById(R.id.loanInstallmentDetails_overdueFees);
        field.setText(details.getOverDueInstallmentDetails().getFees().toString());
        field = (TextView)findViewById(R.id.loanInstallmentDetails_overduePenalty);
        field.setText(details.getOverDueInstallmentDetails().getPenalty().toString());
        field = (TextView)findViewById(R.id.loanInstallmentDetails_overdueSubTotal);
        field.setText(details.getOverDueInstallmentDetails().getSubTotal().toString());

        field = (TextView)findViewById(R.id.loanInstallmentDetails_totalAmountDue_columnLabel);
        field.setText(getText(R.string.loanInstallmentDetails_totalAmountDue_columnLabel) + DateUtils.format(details.getNextMeetingDate()));
        field = (TextView)findViewById(R.id.loanInstallmentDetails_totalAmountDue);
        field.setText(details.getTotalAmountDue().toString());
    }

    private class LoanInstallmentDetailsTask extends ServiceConnectivityTask<String, Void, LoanInstallmentDetails> {

        public LoanInstallmentDetailsTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected LoanInstallmentDetails doInBackgroundBody(String... params) throws RestClientException, IllegalArgumentException {
            return mAccountService.getLoanInstallmentDetails(params[0]);
        }

        @Override
        protected void onPostExecuteBody(LoanInstallmentDetails details) {
            if (details != null) {
                updateContent(details);
            }
        }

    }

}

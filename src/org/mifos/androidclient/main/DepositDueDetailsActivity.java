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
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.AbstractAccountDetails;
import org.mifos.androidclient.entities.account.savings.SavingsAccountDepositDue;
import org.mifos.androidclient.net.services.AccountService;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;

public class DepositDueDetailsActivity extends DownloaderActivity {

    private String mAccountNumber;
    private DepositDueDetailsTask mDepositDueDetailsTask;
    private SavingsAccountDepositDue mDetails;
    private AccountService mAccountService;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.deposit_due_details);

        if (bundle != null) {
            mDetails = (SavingsAccountDepositDue)bundle.getSerializable(SavingsAccountDepositDue.BUNDLE_KEY);
        }

        mAccountNumber = getIntent().getStringExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY);
        mAccountService = new AccountService(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDepositDueDetailsTask != null) {
            mDepositDueDetailsTask.terminate();
            mDepositDueDetailsTask = null;
        }
    }

    @Override
    protected void onSessionActive() {
        super.onSessionActive();
        if (mDetails == null) {
            runDepositDueDetailsTask();
        } else {
            updateContent(mDetails);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SavingsAccountDepositDue.BUNDLE_KEY, mDetails);
    }

    private void runDepositDueDetailsTask() {
        if (StringUtils.hasLength(mAccountNumber)) {
            if (mDepositDueDetailsTask == null || mDepositDueDetailsTask.getStatus() != AsyncTask.Status.RUNNING) {
                mDepositDueDetailsTask = new DepositDueDetailsTask(
                        this,
                        getString(R.string.dialog_getting_deposit_due_details),
                        getString(R.string.dialog_loading_message)
                );
                mDepositDueDetailsTask.execute(mAccountNumber);
            }
        }
    }

    private void updateContent(SavingsAccountDepositDue details) {
        mDetails = details;

    }

    private class DepositDueDetailsTask extends ServiceConnectivityTask<String, Void, SavingsAccountDepositDue> {

        public DepositDueDetailsTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected SavingsAccountDepositDue doInBackgroundBody(String... params) throws RestClientException, IllegalArgumentException {
            return mAccountService.getSavingsDepositDueDetails(params[0]);
        }

        @Override
        protected void onPostExecuteBody(SavingsAccountDepositDue savingsAccountDepositDue) {
            if (savingsAccountDepositDue != null) {
                updateContent(savingsAccountDepositDue);
            }
        }

    }

}

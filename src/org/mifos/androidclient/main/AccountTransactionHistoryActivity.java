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
import org.mifos.androidclient.entities.account.TransactionHistoryEntry;
import org.mifos.androidclient.net.services.AccountService;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.springframework.web.client.RestClientException;

import java.util.*;

public class AccountTransactionHistoryActivity extends DownloaderActivity {

    private String mAccountNumber;
    private AccountService mAccountService;
    private TransactionHistoryTask mTransactionHistoryTask;
    private List<TransactionHistoryEntry> mTransactionHistoryEntries;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.transaction_history);

        mAccountNumber = getIntent().getStringExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY);
        mAccountService = new AccountService(this);
    }

    @Override
    protected void onSessionActive() {
        super.onSessionActive();
        if (mTransactionHistoryEntries == null) {
            runTransactionHistoryTask();
        } else {
            updateContent(mTransactionHistoryEntries);
        }
    }

    private void updateContent(List<TransactionHistoryEntry> entries) {
        if (entries != null) {
            mTransactionHistoryEntries = entries;
        }
    }

    private void runTransactionHistoryTask() {
        if (mAccountNumber != null) {
            if (mTransactionHistoryTask == null || mTransactionHistoryTask.getStatus() != AsyncTask.Status.RUNNING) {
                mTransactionHistoryTask = new TransactionHistoryTask(
                        this,
                        getString(R.string.dialog_loading_message),
                        getString(R.string.dialog_getting_transaction_history)
                );
                mTransactionHistoryTask.execute(mAccountNumber);
            }
        }
    }

    private class TransactionHistoryTask extends ServiceConnectivityTask<String, Void, TransactionHistoryEntry[]> {

        public TransactionHistoryTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected TransactionHistoryEntry[] doInBackgroundBody(String... params) throws RestClientException, IllegalArgumentException {
            TransactionHistoryEntry[] entries = new TransactionHistoryEntry[0];
            if (mAccountService != null) {
                entries = mAccountService.getAccountTransactionHistory(params[0]);
            }
            return entries;
        }

        @Override
        protected void onPostExecuteBody(TransactionHistoryEntry[] transactionHistoryEntries) {
            List<TransactionHistoryEntry> entries = new ArrayList<TransactionHistoryEntry>(Arrays.asList(transactionHistoryEntries));
            updateContent(entries);
        }

    }

}

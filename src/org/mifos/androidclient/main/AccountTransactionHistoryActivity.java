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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.AbstractAccountDetails;
import org.mifos.androidclient.entities.account.TransactionHistoryEntry;
import org.mifos.androidclient.net.services.AccountService;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.mifos.androidclient.util.listadapters.SimpleListAdapter;
import org.mifos.androidclient.util.listadapters.SimpleListItem;
import org.springframework.web.client.RestClientException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountTransactionHistoryActivity extends DownloaderActivity
        implements AdapterView.OnItemClickListener{

    private String mAccountNumber;
    private AccountService mAccountService;
    private TransactionHistoryTask mTransactionHistoryTask;
    private List<TransactionHistoryEntry> mTransactionHistoryEntries;
    private ListView mTransactionHistoryList;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.transaction_history);

        if (bundle != null && bundle.containsKey(TransactionHistoryEntry.BUNDLE_KEY)) {
            mTransactionHistoryEntries = (List<TransactionHistoryEntry>)bundle.getSerializable(TransactionHistoryEntry.BUNDLE_KEY);
        }

        mTransactionHistoryList = (ListView)findViewById(R.id.transactionHistory_list);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(TransactionHistoryEntry.BUNDLE_KEY, (Serializable)mTransactionHistoryEntries);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTransactionHistoryTask != null) {
            mTransactionHistoryTask.terminate();
            mTransactionHistoryTask = null;
        }
    }

    private void updateContent(List<TransactionHistoryEntry> entries) {
        if (entries != null) {
            mTransactionHistoryEntries = entries;
            mTransactionHistoryList.setAdapter(new SimpleListAdapter(
                    this,
                    new ArrayList<SimpleListItem>(entries)
            ));
            mTransactionHistoryList.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        TransactionHistoryEntry entry = (TransactionHistoryEntry)adapterView.getAdapter().getItem(pos);
        Intent intent = new Intent().setClass(this, TransactionHistoryItemDetailsActivity.class);
        intent.putExtra(TransactionHistoryEntry.BUNDLE_KEY, entry);
        startActivity(intent);
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

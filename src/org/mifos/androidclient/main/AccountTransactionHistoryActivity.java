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
import android.os.Bundle;
import org.mifos.androidclient.entities.account.TransactionHistoryEntry;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.springframework.web.client.RestClientException;

public class AccountTransactionHistoryActivity extends DownloaderActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    private class TransactionHistoryTask extends ServiceConnectivityTask<String, Void, TransactionHistoryEntry[]> {

        public TransactionHistoryTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected TransactionHistoryEntry[] doInBackgroundBody(String... params) throws RestClientException, IllegalArgumentException {
            return new TransactionHistoryEntry[0];
        }

        @Override
        protected void onPostExecuteBody(TransactionHistoryEntry[] transactionHistoryEntries) {
        }

    }

}

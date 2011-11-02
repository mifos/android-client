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
import android.widget.ListView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.ClientsData;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.springframework.web.client.RestClientException;

public class CentersListActivity extends DownloaderActivity {

    private ListView mCentersList;
    private ClientsListTask mClientsListTask;
    private ClientsData mClientsData;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.centers_list);

        mCentersList = (ListView)findViewById(R.id.centers_list);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void runClientsListTask() {
        if (mClientsListTask == null || mClientsListTask.getStatus() != AsyncTask.Status.RUNNING) {

        }
    }

    private class ClientsListTask extends ServiceConnectivityTask<Void, Void, Boolean> {

        public ClientsListTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected Boolean doInBackgroundBodyBody(Void... params) throws RestClientException, IllegalArgumentException {
            return null;
        }

        @Override
        protected void onPostExecuteBody(Boolean aBoolean) {

        }

    }

}

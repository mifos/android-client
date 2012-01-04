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
import org.mifos.androidclient.entities.collectionsheet.CollectionSheetData;
import org.mifos.androidclient.entities.simple.AbstractCustomer;
import org.mifos.androidclient.entities.simple.Center;
import org.mifos.androidclient.net.services.CollectionSheetService;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.springframework.web.client.RestClientException;

public class CollectionSheetActivity extends DownloaderActivity {

    private Center mCenter;
    private CollectionSheetData mCollectionSheetData;

    private CollectionSheetService mCollectionSheetService;

    private CollectionSheetTask mCollectionSheetTask;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.collection_sheet);

        mCenter = (Center)getIntent().getSerializableExtra((AbstractCustomer.BUNDLE_KEY));

        mCollectionSheetService = new CollectionSheetService(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCollectionSheetTask != null) {
            mCollectionSheetTask.terminate();
            mCollectionSheetTask = null;
        }
    }

    @Override
    protected void onSessionActive() {
        super.onSessionActive();
        if (mCollectionSheetData == null) {
            runCollectionSheetTask();
        }
    }

    private void runCollectionSheetTask() {
        if (mCenter != null) {
            if (mCollectionSheetTask == null || mCollectionSheetTask.getStatus() != AsyncTask.Status.RUNNING) {
                mCollectionSheetTask = new CollectionSheetTask(
                        this,
                        getString(R.string.dialog_loading_message),
                        getString(R.string.collectionSheet_loadingText)
                );
                mCollectionSheetTask.execute(mCenter.getId());
            }
        }
    }

    private class CollectionSheetTask extends ServiceConnectivityTask<Integer,Void,CollectionSheetData> {

        public CollectionSheetTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected CollectionSheetData doInBackgroundBody(Integer... params) throws RestClientException, IllegalArgumentException {
            return mCollectionSheetService.getCollectionSheetForCustomer(params[0]);
        }

        @Override
        protected void onPostExecuteBody(CollectionSheetData collectionSheetData) {
            mCollectionSheetData = collectionSheetData;
        }

    }

}

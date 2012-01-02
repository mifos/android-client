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
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.collectionsheet.CollectionSheetData;
import org.mifos.androidclient.entities.simple.AbstractCustomer;
import org.mifos.androidclient.entities.simple.Center;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.springframework.web.client.RestClientException;

public class CollectionSheetActivity extends DownloaderActivity {

    private Center mCenter;
    private CollectionSheetData mCollectionSheetData;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.collection_sheet);

        mCenter = (Center)bundle.getSerializable(AbstractCustomer.BUNDLE_KEY);
    }

    private class CollectionSheetTask extends ServiceConnectivityTask<String, Void, CollectionSheetData> {

        public CollectionSheetTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected CollectionSheetData doInBackgroundBody(String... params) throws RestClientException, IllegalArgumentException {
            return null;
        }

        @Override
        protected void onPostExecuteBody(CollectionSheetData collectionSheetData) {

        }

    }

}

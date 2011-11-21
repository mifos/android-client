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
import android.widget.TabHost;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.customer.AccountBasicInformation;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.springframework.web.client.RestClientException;

public class AccountDetailsActivity extends DownloaderActivity {

    private AccountBasicInformation mAccount;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.account_details);

        TabHost tabs = (TabHost)findViewById(R.id.accountDetails_tabHost);
        tabs.setup();
        TabHost.TabSpec overviewSpec = tabs.newTabSpec(getString(R.string.accountDetails_tab_overview));
        overviewSpec.setIndicator(getString(R.string.accountDetails_tab_overview));
        overviewSpec.setContent(R.id.account_overview);
        TabHost.TabSpec additionalInfoSpec = tabs.newTabSpec(getString(R.string.accountDetails_tab_additionalInfo));
        additionalInfoSpec.setIndicator(getString(R.string.accountDetails_tab_additionalInfo));
        additionalInfoSpec.setContent(R.id.account_details);
        tabs.addTab(overviewSpec);
        tabs.addTab(additionalInfoSpec);

        mAccount = (AccountBasicInformation)getIntent().getSerializableExtra(AccountBasicInformation.BUNDLE_KEY);
    }

    private class AccountDetailsTask extends ServiceConnectivityTask<AccountBasicInformation, Void, Object> {

        public AccountDetailsTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected Object doInBackgroundBody(AccountBasicInformation... params) throws RestClientException, IllegalArgumentException {
            return null;
        }

        @Override
        protected void onPostExecuteBody(Object o) {

        }
    }

}

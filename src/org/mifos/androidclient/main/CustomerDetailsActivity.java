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
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.customer.ClientDetails;
import org.mifos.androidclient.entities.simple.Customer;
import org.mifos.androidclient.net.services.CustomerService;
import org.mifos.androidclient.templates.CustomerDetailsViewBuilder;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.mifos.androidclient.templates.ViewBuilderFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;

public class CustomerDetailsActivity extends DownloaderActivity {

    private Customer mCustomer;
    private CustomerDetailsTask mCustomerDetailsTask;
    private CustomerService mCustomerService;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.customer_details);

        TabHost tabs = (TabHost)findViewById(R.id.customerDetails_tabhost);
        tabs.setup();
        TabHost.TabSpec overviewSpec = tabs.newTabSpec(getString(R.string.customerDetails_tab_overview));
        overviewSpec.setIndicator(getString(R.string.customerDetails_tab_overview));
        overviewSpec.setContent(R.id.customer_overview);
        TabHost.TabSpec accountsSpec = tabs.newTabSpec(getString(R.string.customerDetails_tab_accounts));
        accountsSpec.setIndicator(getString(R.string.customerDetails_tab_accounts));
        accountsSpec.setContent(R.id.customer_accounts);
        TabHost.TabSpec additionalSpec = tabs.newTabSpec(getString(R.string.customerDetails_tab_additional));
        additionalSpec.setIndicator(getString(R.string.customerDetails_tab_additional));
        additionalSpec.setContent(R.id.customer_additional);
        tabs.addTab(overviewSpec);
        tabs.addTab(accountsSpec);
        tabs.addTab(additionalSpec);

        mCustomer = (Customer)getIntent().getSerializableExtra(Customer.BUNDLE_KEY);
        mCustomerService = new CustomerService(this);
    }

    @Override
    protected void onSessionActive() {
        super.onSessionActive();
        runCustomerDetailsTask();
    }

    private void updateContent(ClientDetails details) {
        if (details != null) {
            LinearLayout tabContent = (LinearLayout)findViewById(R.id.customer_overview);
            ViewBuilderFactory factory = new ViewBuilderFactory(this);
            CustomerDetailsViewBuilder builder = factory.createCustomerDetailsViewBuilder(details);

            if (tabContent.getChildCount() > 0) {
                tabContent.removeAllViews();
            }
            tabContent.addView(builder.buildOverviewView());
        }
    }

    private void runCustomerDetailsTask() {
        if (mCustomer == null || !StringUtils.hasLength(mCustomer.getGlobalCustNum())) {
            mUIUtils.displayLongMessage(getString(R.string.toast_customer_id_not_available));
            return;
        }
        if (mCustomerDetailsTask == null || mCustomerDetailsTask.getStatus() != AsyncTask.Status.RUNNING) {
            mCustomerDetailsTask = new CustomerDetailsTask(
                    this,
                    getString(R.string.dialog_getting_customer_data),
                    getString(R.string.dialog_loading_message)
            );
            mCustomerDetailsTask.execute(mCustomer.getGlobalCustNum());
        }
    }

    /**
     * Downloads the details of a select client from the Mifos server.
     */
    private class CustomerDetailsTask extends ServiceConnectivityTask<String, Void, ClientDetails> {

        public CustomerDetailsTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected ClientDetails doInBackgroundBody(String... params) throws RestClientException, IllegalArgumentException {
            return mCustomerService.getClientDetails(params[0]);
        }

        @Override
        protected void onPostExecuteBody(ClientDetails clientDetails) {
            updateContent(clientDetails);
        }

    }

}

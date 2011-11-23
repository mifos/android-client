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
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.customer.AccountBasicInformation;
import org.mifos.androidclient.entities.customer.CustomerDetailsEntity;
import org.mifos.androidclient.entities.simple.AbstractCustomer;
import org.mifos.androidclient.main.views.adapters.AccountsExpandableListAdapter;
import org.mifos.androidclient.net.services.CustomerService;
import org.mifos.androidclient.templates.CustomerDetailsViewBuilder;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.mifos.androidclient.templates.ViewBuilderFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;

public class CustomerDetailsActivity extends DownloaderActivity
        implements ExpandableListView.OnChildClickListener {

    private AbstractCustomer mCustomer;
    private CustomerDetailsTask mCustomerDetailsTask;
    private CustomerService mCustomerService;
    private CustomerDetailsEntity mDetails;

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

        mCustomer = (AbstractCustomer)getIntent().getSerializableExtra(AbstractCustomer.BUNDLE_KEY);
        mCustomerService = new CustomerService(this);
    }

    @Override
    protected void onSessionActive() {
        super.onSessionActive();
        if (mDetails == null) {
            runCustomerDetailsTask();
        }
    }

    @Override
    public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPos, int childPos, long id) {
        AccountsExpandableListAdapter adapter = (AccountsExpandableListAdapter)expandableListView.getExpandableListAdapter();
        AccountBasicInformation account = (AccountBasicInformation)adapter.getChild(groupPos, childPos);
        Intent intent = new Intent().setClass(this, AccountDetailsActivity.class);
        intent.putExtra(AccountBasicInformation.BUNDLE_KEY, account);
        startActivity(intent);
        return true;
    }

    private void updateContent(CustomerDetailsEntity details) {
        if (details != null) {
            mDetails = details;
            LinearLayout tabContent = (LinearLayout)findViewById(R.id.customer_overview);
            ViewBuilderFactory factory = new ViewBuilderFactory(this);
            CustomerDetailsViewBuilder builder = factory.createCustomerDetailsViewBuilder(details);
            View view;

            if (tabContent.getChildCount() > 0) {
                tabContent.removeAllViews();
            }
            tabContent.addView(builder.buildOverviewView());

            tabContent = (LinearLayout)findViewById(R.id.customer_accounts);
            if (tabContent.getChildCount() > 0) {
                tabContent.removeAllViews();
            }
            view = builder.buildAccountsView();
            setupAccountsListListeners(view);
            tabContent.addView(view);

            tabContent.addView(builder.buildAccountsView());

            tabContent = (LinearLayout)findViewById(R.id.customer_additional);
            if(tabContent.getChildCount() > 0){
                tabContent.removeAllViews();
            }
            tabContent.addView(builder.buildAdditionalView());
        }
    }

    private void setupAccountsListListeners(View view) {
        ExpandableListView list = (ExpandableListView)view.findViewById(R.id.customerAccounts_list);
        list.setOnChildClickListener(this);
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
            mCustomerDetailsTask.execute(mCustomer);
        }
    }

    /**
     * Downloads the details of a select client from the Mifos server.
     */
    private class CustomerDetailsTask extends ServiceConnectivityTask<AbstractCustomer, Void, CustomerDetailsEntity> {

        public CustomerDetailsTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected CustomerDetailsEntity doInBackgroundBody(AbstractCustomer... params) throws RestClientException, IllegalArgumentException {
            CustomerDetailsEntity result = null;
            if (mCustomerService != null) {
                result = mCustomerService.getDetailsForEntity(params[0]);
            }
            return result;
        }

        @Override
        protected void onPostExecuteBody(CustomerDetailsEntity clientDetails) {
            if (clientDetails != null) {
                updateContent(clientDetails);
            }
        }

    }

}

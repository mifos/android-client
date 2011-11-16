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
import org.mifos.androidclient.entities.simple.AbstractCustomer;
import org.mifos.androidclient.entities.simple.Center;
import org.mifos.androidclient.entities.simple.CustomersData;
import org.mifos.androidclient.entities.simple.Group;
import org.mifos.androidclient.net.services.CustomerService;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.mifos.androidclient.util.ApplicationConstants;
import org.mifos.androidclient.util.listadapters.SimpleListAdapter;
import org.mifos.androidclient.util.listadapters.SimpleListItem;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;

public class CentersListActivity extends DownloaderActivity
        implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView mCentersList;
    private CustomersListTask mCustomersListTask;
    private CustomersData mCustomersData;
    private CustomerService mCustomerService;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.centers_list);

        mCentersList = (ListView)findViewById(R.id.centers_list);
        mCustomerService = new CustomerService(this);
    }

    @Override
    protected void onSessionActive() {
        super.onSessionActive();
        runClientsListTask();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long rowId) {
        Center center = (Center)adapterView.getAdapter().getItem(position);
        Intent intent = new Intent().setClass(this, CustomersListActivity.class);
        intent.putExtra(Group.BUNDLE_KEY, new ArrayList<Group>(center.getGroups()));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long rowId) {
        Center center = (Center)adapterView.getAdapter().getItem(position);
        Intent intent = new Intent().setClass(this, CustomerDetailsActivity.class);
        intent.putExtra(AbstractCustomer.BUNDLE_KEY, center);
        startActivity(intent);
        return true;
    }

    /**
     * Refreshes contents of the centers list.
     */
    private void repopulateCustomersList() {
        mCentersList.setAdapter(new SimpleListAdapter(
                this,
                new ArrayList<SimpleListItem>(mCustomersData.getCenters())
        ));
        mCentersList.setOnItemClickListener(this);
        mCentersList.setOnItemLongClickListener(this);
    }

    /**
     * Checks if the task is not currently running and launches it
     * otherwise.
     */
    private void runClientsListTask() {
        if (mCustomersListTask == null || mCustomersListTask.getStatus() != AsyncTask.Status.RUNNING) {
            mCustomersListTask = new CustomersListTask(
                    this,
                    getString(R.string.dialog_getting_customer_data),
                    getString(R.string.dialog_loading_message)
            );
            mCustomersListTask.execute((Void[])null);
        }
    }

    /**
     * Downloads the list of loan officer's customers from the server. Refreshes
     * the list of centers upon successful download.
     */
    private class CustomersListTask extends ServiceConnectivityTask<Void, Void, CustomersData> {

        public CustomersListTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected CustomersData doInBackgroundBody(Void... params) throws RestClientException, IllegalArgumentException {
            CustomersData result = null;
            if (mCustomerService != null) {
                result =  mCustomerService.getLoanOfficersCustomers();
            }
            return result;
        }

        @Override
        protected void onPostExecuteBody(CustomersData result) {
            if (result != null) {
                mCustomersData = result;
                if (mCustomersData.getGroups() != null && mCustomersData.getGroups().size() > 0) {
                    Center emptyCenter = new Center();
                    emptyCenter.setDisplayName(getString(R.string.centerslist_no_center));
                    emptyCenter.setId(ApplicationConstants.DUMMY_IDENTIFIER);
                    emptyCenter.setSearchId(ApplicationConstants.EMPTY_STRING);
                    emptyCenter.setGroups(mCustomersData.getGroups());
                }
                repopulateCustomersList();
            }
        }

    }

}

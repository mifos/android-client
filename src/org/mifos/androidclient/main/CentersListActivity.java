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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
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

    private EditText mFilterBox;
    private CentersListTextWatcher mTextWatcher;
    private ListView mCentersList;
    private LinearLayout mContent;
    private TextView mMessage;
    private CustomersListTask mCustomersListTask;
    private CustomersData mCustomersData;
    private CustomerService mCustomerService;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.centers_list);

        if (bundle != null && bundle.containsKey(CustomersData.BUNDLE_KEY)) {
            mCustomersData = (CustomersData)bundle.getSerializable(CustomersData.BUNDLE_KEY);
        }

        mFilterBox = (EditText)findViewById(R.id.centers_list_filter_box);
        mCentersList = (ListView)findViewById(R.id.centers_list);
        mContent = (LinearLayout)findViewById(R.id.centersList_content);
        mMessage = (TextView)findViewById(R.id.centersList_noDataMessage);
        mCustomerService = new CustomerService(this);
    }

    @Override
    protected void onSessionActive() {
        if (mCustomersData == null) {
            runClientsListTask();
        } else {
            repopulateCustomersList(mCustomersData);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CustomersData.BUNDLE_KEY, mCustomersData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCustomersListTask != null) {
            mCustomersListTask.terminate();
            mCustomersListTask = null;
        }
        mFilterBox.removeTextChangedListener(mTextWatcher);
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
    private void repopulateCustomersList(CustomersData data) {
        if (data.getCenters() != null && data.getCenters().size() > 0) {
            SimpleListAdapter adapter = new SimpleListAdapter(
                    this,
                    new ArrayList<SimpleListItem>(data.getCenters())
            );
            mCentersList.setAdapter(adapter);
            if (mTextWatcher == null) {
                mTextWatcher = new CentersListTextWatcher();
            }
            mTextWatcher.setAdapter(adapter);
            mFilterBox.addTextChangedListener(mTextWatcher);
            mCentersList.setOnItemClickListener(this);
            mCentersList.setOnItemLongClickListener(this);
            mMessage.setVisibility(View.GONE);
            mContent.setVisibility(View.VISIBLE);
        } else {
            mMessage.setText(getString(R.string.centersList_no_centers_available, getUserLogin()));
            mMessage.setVisibility(View.VISIBLE);
            mContent.setVisibility(View.GONE);
        }
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

    private class CentersListTextWatcher implements TextWatcher {

        private SimpleListAdapter mAdapter;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (mAdapter != null) {
                mAdapter.getFilter().filter(charSequence);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

        private void setAdapter(SimpleListAdapter adapter) {
            mAdapter = adapter;
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
                if (result != null) {
                    result.sort();
                }
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
                repopulateCustomersList(result);
            }
        }

    }

}

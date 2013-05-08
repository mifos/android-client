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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.customer.AccountBasicInformation;
import org.mifos.androidclient.entities.customer.OverdueCustomer;
import org.mifos.androidclient.entities.simple.AbstractCustomer;
import org.mifos.androidclient.entities.simple.CustomersData;
import org.mifos.androidclient.net.services.CustomerService;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.mifos.androidclient.util.listadapters.OverdueBorrowersListAdapter;
import org.mifos.androidclient.util.listadapters.SimpleListItem;
import org.springframework.web.client.RestClientException;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OverdueBorrowersListActivity extends DownloaderActivity implements ExpandableListView.OnChildClickListener,
		AdapterView.OnItemLongClickListener {

    private EditText mFilterBox;
    private OverdueBorrowersListTextWatcher mTextWatcher;
    private ExpandableListView mOverdueBorrowersList;
    private LinearLayout mContent;
    private TextView mMessage;
    private List<OverdueCustomer> mOverdueCustomers;
    private CustomerService mCustomerService;
    private OverdueBorrowersListTask mOverdueBorrowersListTask;
    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.overdue_borrowers_list);
        
        if (bundle != null && bundle.containsKey(CustomersData.BUNDLE_KEY)) {
            mOverdueCustomers = Arrays.asList((OverdueCustomer[]) bundle.getSerializable(OverdueCustomer.BUNDLE_KEY));
        }

        mFilterBox = (EditText)findViewById(R.id.overdueBorrowersList_filter_box);
        mOverdueBorrowersList = (ExpandableListView) findViewById(R.id.overdueBorrowersList_list);
        mContent = (LinearLayout)findViewById(R.id.overdueBorrowersList_content);
        mMessage = (TextView)findViewById(R.id.overdueBorrowersList_noDataMessage);
        mCustomerService = new CustomerService(this);
    }

    @Override
    protected void onSessionActive() {
        if (mOverdueCustomers == null) {
            runClientsListTask();
        } else {
            repopulateOverdueBorrowersList(mOverdueCustomers);
        }
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mOverdueCustomers != null) {
        	outState.putSerializable(CustomersData.BUNDLE_KEY, mOverdueCustomers.toArray());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mOverdueBorrowersListTask != null) {
            mOverdueBorrowersListTask.terminate();
            mOverdueBorrowersListTask = null;
        }
        mFilterBox.removeTextChangedListener(mTextWatcher);
    }
    
    private void runClientsListTask() {
        if (mOverdueBorrowersListTask == null || mOverdueBorrowersListTask.getStatus() != AsyncTask.Status.RUNNING) {
            mOverdueBorrowersListTask = new OverdueBorrowersListTask(
                    this,
                    getString(R.string.dialog_getting_customer_data),
                    getString(R.string.dialog_loading_message)
            );
            mOverdueBorrowersListTask.execute();
        }
    }
    
    private void repopulateOverdueBorrowersList(List<OverdueCustomer> overdueCustomers) {

        if (overdueCustomers != null && overdueCustomers.size() > 0) {
        	Map<SimpleListItem, List<SimpleListItem>> items = new TreeMap<SimpleListItem, List<SimpleListItem>>(new Comparator<SimpleListItem>() {
                @Override
                public int compare(SimpleListItem simpleListItem1, SimpleListItem simpleListItem2) {
                	return simpleListItem1.getListLabel().compareToIgnoreCase(simpleListItem2.getListLabel());
                }
            });
        	for (OverdueCustomer overdueCustomer: overdueCustomers) {
        		List<SimpleListItem> loans = new ArrayList<SimpleListItem>(overdueCustomer.getOverdueLoans());

        		items.put(overdueCustomer, loans);
            }
        	OverdueBorrowersListAdapter adapter = new OverdueBorrowersListAdapter(this, items);
            mOverdueBorrowersList.setAdapter(adapter);
            if (mTextWatcher == null) {
                mTextWatcher = new OverdueBorrowersListTextWatcher();
            }
            mTextWatcher.setAdapter(adapter);
            mFilterBox.addTextChangedListener(mTextWatcher);
            mOverdueBorrowersList.setOnChildClickListener(this);
            mOverdueBorrowersList.setOnItemLongClickListener(this);
            mMessage.setVisibility(View.GONE);
            mContent.setVisibility(View.VISIBLE);
        } else {
            mMessage.setText(getString(R.string.centersList_no_centers_available, getUserLogin()));
            mMessage.setVisibility(View.VISIBLE);
            mContent.setVisibility(View.GONE);
        }
    }

    private class OverdueBorrowersListTextWatcher implements TextWatcher {

    	private OverdueBorrowersListAdapter mAdapter;

        public void setAdapter(OverdueBorrowersListAdapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (mAdapter != null) {
                mAdapter.getFilter().filter(charSequence);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {}

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
    }
    
    private class OverdueBorrowersListTask extends ServiceConnectivityTask<Void, Void, List<OverdueCustomer>> {

        public OverdueBorrowersListTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected  List<OverdueCustomer> doInBackgroundBody(Void... params) throws RestClientException, IllegalArgumentException {
        	List<OverdueCustomer> result = null;
            if (mCustomerService != null) {
                result =  mCustomerService.getLoanOfficersOverdueBorrowers();
                if (result != null) {
                    Collections.sort(result, new Comparator<OverdueCustomer>() {
						@Override
						public int compare(OverdueCustomer lhs, OverdueCustomer rhs) {
							return lhs.getDisplayName().compareTo(rhs.getDisplayName());
						}
                    });
                }
            }
            return result;
        }

        @Override
        protected void onPostExecuteBody(List<OverdueCustomer> result) {
            if (result != null) {
                mOverdueCustomers = result;
                repopulateOverdueBorrowersList(result);
            }
        }
    }
    
	@Override
	public boolean onChildClick(ExpandableListView parent, View view, int groupPos, int childPos, long id) {
		Intent intent = new Intent().setClass(this, AccountDetailsActivity.class);
		AccountBasicInformation acc = (AccountBasicInformation) parent.getExpandableListAdapter().getChild(groupPos, childPos);
		intent.putExtra(AccountBasicInformation.BUNDLE_KEY, acc);
		startActivity(intent);
		return true;
	}

	@Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long rowId) {
		Object item = adapterView.getAdapter().getItem(position);
		if (item instanceof OverdueCustomer) {
        	Intent intent = new Intent().setClass(this, CustomerDetailsActivity.class);
        	intent.putExtra(AbstractCustomer.BUNDLE_KEY, (OverdueCustomer) item);
        	startActivity(intent);
		}
        return true;
    }

	/* Shouldn't be used - For testing purposes only */
	public void setCustomerService(CustomerService customerService) {
		mCustomerService = customerService;
	}
	
	/* Shouldn't be used - For testing purposes only */
	public OverdueBorrowersListTask getOverdueBorrowersListTask() {
		return mOverdueBorrowersListTask;
	}
}

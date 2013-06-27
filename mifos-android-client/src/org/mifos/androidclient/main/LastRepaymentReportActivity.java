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
import org.mifos.androidclient.entities.customer.LastRepayment;
import org.mifos.androidclient.entities.customer.LoanAccountBasicInformation;
import org.mifos.androidclient.entities.simple.AbstractCustomer;
import org.mifos.androidclient.entities.simple.CustomersData;
import org.mifos.androidclient.net.services.CustomerService;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.mifos.androidclient.util.listadapters.LastRepaymentReportListAdapter;
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

public class LastRepaymentReportActivity extends DownloaderActivity implements ExpandableListView.OnChildClickListener,
		AdapterView.OnItemLongClickListener {

    private EditText mFilterBox;
    private LastRepaymentListTextWatcher mTextWatcher;
    private ExpandableListView mLastLoanList;
    private LinearLayout mContent;
    private TextView mMessage;
    private List<LastRepayment> mLastRepayments;
    private CustomerService mCustomerService;
    private LastRepaymentReportTask mLastRepaymentReportTask;
    
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.last_repayment_report);
        
        if (bundle != null && bundle.containsKey(CustomersData.BUNDLE_KEY)) {
            mLastRepayments = Arrays.asList((LastRepayment[]) bundle.getSerializable(LastRepayment.BUNDLE_KEY));
        }

        mFilterBox = (EditText)findViewById(R.id.lastRepayment_filter_box);
        mLastLoanList = (ExpandableListView) findViewById(R.id.lastRepayment_list);
        mContent = (LinearLayout)findViewById(R.id.lastRepayment_content);
        mMessage = (TextView)findViewById(R.id.lastRepayment_noDataMessage);
        mCustomerService = new CustomerService(this);
    }

    @Override
    protected void onSessionActive() {
        if (mLastRepayments == null) {
            runLastRepaymentsTask();
        } else {
            repopulateLastRepaymentsList(mLastRepayments);
        }
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mLastRepayments != null) {
        	outState.putSerializable(LastRepayment.BUNDLE_KEY, mLastRepayments.toArray());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLastRepaymentReportTask != null) {
        	mLastRepaymentReportTask.terminate();
        	mLastRepaymentReportTask = null;
        }
        mFilterBox.removeTextChangedListener(mTextWatcher);
    }
    
    private void runLastRepaymentsTask() {
        if (mLastRepaymentReportTask == null || mLastRepaymentReportTask.getStatus() != AsyncTask.Status.RUNNING) {
        	mLastRepaymentReportTask = new LastRepaymentReportTask(
                    this,
                    getString(R.string.dialog_getting_customer_data),
                    getString(R.string.dialog_loading_message)
            );
        	mLastRepaymentReportTask.execute();
        }
    }
    
    private void repopulateLastRepaymentsList(List<LastRepayment> lastRepayments) {

        if (lastRepayments != null && lastRepayments.size() > 0) {
        	Map<SimpleListItem, List<SimpleListItem>> items = new TreeMap<SimpleListItem, List<SimpleListItem>>(new Comparator<SimpleListItem>() {
                @Override
                public int compare(SimpleListItem lhs, SimpleListItem rhs) {
                	int result = ((LastRepayment) lhs).getLastInstallmentDate().compareTo(((LastRepayment) rhs).getLastInstallmentDate());
                	return result != 0 ? result : -1;
                }
            });
        	for (LastRepayment lastRepayment: lastRepayments) {
        		List<SimpleListItem> loans = new ArrayList<SimpleListItem>(
        				Arrays.asList(new LoanAccountBasicInformation[] { lastRepayment.getLoanAccount() }));
        		items.put(lastRepayment, loans);
            }
        	LastRepaymentReportListAdapter adapter = new LastRepaymentReportListAdapter(this, items);
            mLastLoanList.setAdapter(adapter);
            if (mTextWatcher == null) {
                mTextWatcher = new LastRepaymentListTextWatcher();
            }
            mTextWatcher.setAdapter(adapter);
            mFilterBox.addTextChangedListener(mTextWatcher);
            mLastLoanList.setOnChildClickListener(this);
            mLastLoanList.setOnItemLongClickListener(this);
            mMessage.setVisibility(View.GONE);
            mContent.setVisibility(View.VISIBLE);
        } else {
            mMessage.setText(getString(R.string.lastRepayment_no_customers, getUserLogin()));
            mMessage.setVisibility(View.VISIBLE);
            mContent.setVisibility(View.GONE);
        }
    }

    private class LastRepaymentListTextWatcher implements TextWatcher {

    	private LastRepaymentReportListAdapter mAdapter;

        public void setAdapter(LastRepaymentReportListAdapter adapter) {
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
    
    private class LastRepaymentReportTask extends ServiceConnectivityTask<Void, Void, List<LastRepayment>> {

        public LastRepaymentReportTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected  List<LastRepayment> doInBackgroundBody(Void... params) throws RestClientException, IllegalArgumentException {
        	List<LastRepayment> result = null;
            if (mCustomerService != null) {
                result =  mCustomerService.getLoanOfficersCustomersLastRepayments();
                if (result != null) {
                    Collections.sort(result, new Comparator<LastRepayment>() {
						@Override
						public int compare(LastRepayment lhs, LastRepayment rhs) {
							return lhs.getLastInstallmentDate().compareTo(rhs.getLastInstallmentDate());
						}
                    });
                }
            }
            return result;
        }

        @Override
        protected void onPostExecuteBody(List<LastRepayment> result) {
            if (result != null) {
                mLastRepayments = result;
                repopulateLastRepaymentsList(result);
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
		if (item instanceof LastRepayment) {
        	Intent intent = new Intent().setClass(this, CustomerDetailsActivity.class);
        	LastRepayment lastRepayment = (LastRepayment) item;
        	intent.putExtra(AbstractCustomer.BUNDLE_KEY, lastRepayment.getCustomer());
        	if (lastRepayment.isGroup()) {
        		intent.putExtra("isGroup", true);
        	}
        	startActivity(intent);
		}
        return true;
    }

}

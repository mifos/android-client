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
import android.widget.*;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.collectionsheet.*;
import org.mifos.androidclient.entities.customer.LoanOfficerData;
import org.mifos.androidclient.entities.simple.AbstractCustomer;
import org.mifos.androidclient.entities.simple.Center;
import org.mifos.androidclient.main.views.adapters.CollectionSheetExpandableListAdapter;
import org.mifos.androidclient.net.services.CollectionSheetService;
import org.mifos.androidclient.net.services.CustomerService;
import org.mifos.androidclient.net.services.SystemSettingsService;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

public class CollectionSheetActivity extends DownloaderActivity implements ExpandableListView.OnChildClickListener, AdapterView.OnItemLongClickListener{

    private Center mCenter;
    private CollectionSheetData mCollectionSheetData;
    private ExpandableListView mCollectionSheetList;
    private CollectionSheetService mCollectionSheetService;
    private CollectionSheetTask mCollectionSheetTask;
    private SystemSettingsService mSystemSettingsService;
    private SaveCollectionSheet mSaveCustomer = new SaveCollectionSheet();

    private CollectionSheetCustomer mSelectedCustomer;
    private CustomerService mCustomerService;
    public LoanOfficerData mLoanOfficer;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.collection_sheet);

        mCenter = (Center)getIntent().getSerializableExtra((AbstractCustomer.BUNDLE_KEY));
        mSaveCustomer = CollectionSheetHolder.getSaveCollectionSheet();
        mCollectionSheetService = new CollectionSheetService(this);
        mSystemSettingsService = new SystemSettingsService(this);
        mCustomerService = new CustomerService(this);
        mCollectionSheetList = (ExpandableListView)findViewById(R.id.collectionSheet_entries);
    }

    public void onApplyCollectionSheet(View view) {
        CollectionSheetHolder.setSaveCollectionSheet(mSaveCustomer);
        Intent intent = new Intent().setClass(this, ApplyCollectionSheetActivity.class);
        startActivity(intent);
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

    protected void runCollectionSheetTask() {
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

    protected void updateContent(CollectionSheetData collectionSheet){
        if(collectionSheet != null) {
           mCollectionSheetData = collectionSheet;

            TextView textView = (TextView)findViewById(R.id.collectionSheet_header);
            textView.setText(mCenter.getDisplayName());

            if (CollectionSheetHolder.getCollectionSheetData() == null) {
                mCollectionSheetData = collectionSheet;
            }
            else {
                mCollectionSheetData = CollectionSheetHolder.getCollectionSheetData();
            }

            mSelectedCustomer = CollectionSheetHolder.getCurrentCustomer();
            if (mSelectedCustomer !=null) {
                updateCustomers(collectionSheet);
            }

            if (collectionSheet.getCollectionSheetCustomer() != null
                    && collectionSheet.getCollectionSheetCustomer().size() > 0) {

            ExpandableListView expandableListView = (ExpandableListView)findViewById(R.id.collectionSheet_entries);
            CollectionSheetExpandableListAdapter adapter = new CollectionSheetExpandableListAdapter(mCollectionSheetData,this);
            expandableListView.setAdapter(adapter);
            expandableListView.setOnItemLongClickListener(this);
            expandableListView.setOnChildClickListener(this);
            ArrayList<SaveCollectionSheetCustomer> saveCollectionSheetCustomers = new ArrayList<SaveCollectionSheetCustomer>();
            prepareSaveCollectionSheet(saveCollectionSheetCustomers);
            CollectionSheetHolder.getSaveCollectionSheet();
            mSaveCustomer.setSaveCollectionSheetCustomers(saveCollectionSheetCustomers);
            CollectionSheetHolder.setSaveCollectionSheet(mSaveCustomer);
            CollectionSheetHolder.setCollectionSheetData(mCollectionSheetData);
            }
        }

    }



    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long rowId) {
        CollectionSheetCustomer group = (CollectionSheetCustomer)adapterView.getAdapter().getItem(position);
        CollectionSheetHolder.setCurrentCustomer(group);
        Intent intent = new Intent().setClass(this, CollectionSheetCustomerActivity.class);
        intent.putExtra(CollectionSheetCustomer.BUNDLE_KEY, group);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View view, int groupPos, int childPos, long id) {
        CollectionSheetCustomer customer = (CollectionSheetCustomer)parent.getExpandableListAdapter().getChild(groupPos,childPos);
        CollectionSheetHolder.setCurrentCustomer(customer);
        Intent intent = new Intent().setClass(this, CollectionSheetCustomerActivity.class);
        intent.putExtra(CollectionSheetCustomer.BUNDLE_KEY, customer);
        startActivity(intent);
        return true;
    }

    private class CollectionSheetTask extends ServiceConnectivityTask<Integer,Void,CollectionSheetData> {
        public CollectionSheetTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected CollectionSheetData doInBackgroundBody(Integer... params) throws RestClientException, IllegalArgumentException {
            CollectionSheetData collectionSheet = null;


            if (mCollectionSheetService != null) {
                collectionSheet = mCollectionSheetService.getCollectionSheetForCustomer(params[0]);
                mLoanOfficer =  mCustomerService.getCurrentOfficer();
            }
            return collectionSheet;

        }

        @Override
        protected void onPostExecuteBody(CollectionSheetData collectionSheetData) {
            updateContent(collectionSheetData);
        }

    }

    private void prepareSaveCollectionSheet(ArrayList<SaveCollectionSheetCustomer> saveCollectionSheetCustomers) {
        for(CollectionSheetCustomer data : mCollectionSheetData.getCollectionSheetCustomer()) {
            SaveCollectionSheetCustomer saveCollection = new SaveCollectionSheetCustomer();
            if (data.getLevelId() == 1) {
            saveCollection.setAttendanceId((short)1);
            }
            saveCollection.setCustomerId(data.getCustomerId());
            saveCollection.setParentCustomerId(data.getParentCustomerId());
            SaveCollectionSheetCustomerAccount saveAccount = new SaveCollectionSheetCustomerAccount();
            saveAccount.setAccountId(data.getCollectionSheetCustomerAccount().getAccountId());
            saveAccount.setCurrencyId(data.getCollectionSheetCustomerAccount().getCurrencyId());
            saveAccount.setTotalCustomerAccountCollectionFee(data.getCollectionSheetCustomerAccount().getTotalCustomerAccountCollectionFee());
            saveCollection.setSaveCollectionSheetCustomerAccount(saveAccount);
                ArrayList<SaveCollectionSheetCustomerLoan> loan = new ArrayList<SaveCollectionSheetCustomerLoan>();
                ArrayList<SaveCollectionSheetCustomerSaving> saving = new ArrayList<SaveCollectionSheetCustomerSaving>();
                ArrayList<SaveCollectionSheetCustomerSaving> individual = new ArrayList<SaveCollectionSheetCustomerSaving>();
                for (CollectionSheetCustomerLoan loans : data.getCollectionSheetCustomerLoan()){
                    SaveCollectionSheetCustomerLoan saveLoan = new SaveCollectionSheetCustomerLoan();
                    saveLoan.setAccountId(loans.getAccountId());
                    saveLoan.setCurrencyId(loans.getCurrencyId());
                    saveLoan.setTotalDisbursement(loans.getTotalDisbursement());
                    saveLoan.setTotalLoanPayment(loans.getTotalRepaymentDue());
                    loan.add(saveLoan);
                }
                for (CollectionSheetCustomerSavings savings : data.getCollectionSheetCustomerSaving()) {
                    SaveCollectionSheetCustomerSaving saveSaving = new SaveCollectionSheetCustomerSaving();
                    saveSaving.setAccountId(savings.getAccountId());
                    saveSaving.setCurrencyId(savings.getCurrencyId());
                    saveSaving.setTotalDeposit(savings.getTotalDepositAmount());
                    saveSaving.setTotalWithdrawal(0.0);
                    saving.add(saveSaving);
                }
                for (CollectionSheetCustomerSavings individuals : data.getIndividualSavingAccounts()) {
                    SaveCollectionSheetCustomerSaving saveIndividual = new SaveCollectionSheetCustomerSaving();
                    saveIndividual.setAccountId(individuals.getAccountId());
                    saveIndividual.setCurrencyId(individuals.getCurrencyId());
                    saveIndividual.setTotalDeposit(individuals.getTotalDepositAmount());
                    saveIndividual.setTotalWithdrawal(0.0);
                    individual.add(saveIndividual);
                }
            saveCollection.setSaveCollectionSheetCustomerLoans(loan);
            saveCollection.setSaveCollectionSheetCustomerSavings(saving);
            saveCollection.setSaveCollectionSheetCustomerIndividualSavings(individual);
            saveCollectionSheetCustomers.add(saveCollection);
        }
    }



    private void updateCustomers(CollectionSheetData collectionSheet) {
        List<CollectionSheetCustomer> tmpCustomer = collectionSheet.getCollectionSheetCustomer();
        for (CollectionSheetCustomer customer : tmpCustomer) {
            if (customer.getName().equalsIgnoreCase(mSelectedCustomer.getName())) {
                tmpCustomer.set(tmpCustomer.indexOf(customer), mSelectedCustomer);
            }
       }
    }

}

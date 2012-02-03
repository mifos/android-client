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
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputType;
import android.view.View;
import android.widget.*;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.AcceptedPaymentTypes;
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
import org.mifos.androidclient.util.ListMeasuringUtils;
import org.springframework.web.client.RestClientException;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CollectionSheetActivity extends DownloaderActivity implements DatePickerDialog.OnDateSetListener,
        View.OnFocusChangeListener, ExpandableListView.OnChildClickListener, AdapterView.OnItemLongClickListener{
    private static final int DATE_DIALOG_ID = 0;

    private Center mCenter;
    private CollectionSheetData mCollectionSheetData;
    private ExpandableListView mCollectionSheetList;
    private CollectionSheetService mCollectionSheetService;
    private CollectionSheetTask mCollectionSheetTask;
    private AcceptedPaymentTypes mAcceptedPaymentTypes;
    private SystemSettingsService mSystemSettingsService;
    private Map<String, Integer> mTransactionTypes;
    private EditText dateField;
    private EditText receiptID;
    private Spinner typesSpinner;
    private CustomerService mCustomerService;
    private LoanOfficerData mLoanOfficer;
    private SaveCollectionSheet mSaveCustomer = new SaveCollectionSheet();
    private List<CollectionSheetCustomer> mCustomerList;
    private CollectionSheetCustomer mSelectedCustomer;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.collection_sheet);

        mCenter = (Center)getIntent().getSerializableExtra((AbstractCustomer.BUNDLE_KEY));
        mCustomerService = new CustomerService(this);
        mCollectionSheetService = new CollectionSheetService(this);
        mSystemSettingsService = new SystemSettingsService(this);
        mCollectionSheetList = (ExpandableListView)findViewById(R.id.collectionSheet_entries);

        receiptID = (EditText)findViewById(R.id.collectionSheet_formField_receiptId);
        receiptID.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        dateField = (EditText)findViewById(R.id.collectionSheet_formField_receiptDate);
        dateField.setInputType(InputType.TYPE_NULL);
        dateField.setOnFocusChangeListener(this);
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
        if (mCollectionSheetData == null || mAcceptedPaymentTypes == null) {
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

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        StringBuilder builder = new StringBuilder()
                .append(String.format("%02d", dayOfMonth)).append("-")
                .append(String.format("%02d", monthOfYear + 1)).append("-")
                .append(year);
        dateField.setText(builder.toString());
    }

    public void onDateFieldClicked(View view) {
        dateFieldEdit(view);
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (hasFocus) {
            dateFieldEdit(view);
        }
    }

    public void dateFieldEdit(View view) {
        dateField = (EditText)view;
        showDialog(DATE_DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog;
        switch (id) {
            case DATE_DIALOG_ID:
                Calendar today = Calendar.getInstance();
                return new DatePickerDialog(
                        this,
                        this,
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH),
                        today.get(Calendar.DAY_OF_MONTH)
                );
            default:
                dialog = null;
        }
        return dialog;
    }

    private void updateContent(CollectionSheetData collectionSheet){
        LinearLayout linearLayout;
        if(collectionSheet != null) {
           mCollectionSheetData = collectionSheet;
            if(CollectionSheetHolder.getCollectionSheetData() == null){
                mCollectionSheetData = collectionSheet;
            } else {
                mCollectionSheetData = CollectionSheetHolder.getCollectionSheetData();
            }
            mSelectedCustomer = CollectionSheetHolder.getCurrentCustomer();
            if(mSelectedCustomer !=null){
                 List<CollectionSheetCustomer> tmpCustomer = collectionSheet.getCollectionSheetCustomer();
                 for(CollectionSheetCustomer customer : tmpCustomer) {
                     if(customer.getName().equalsIgnoreCase(mSelectedCustomer.getName())) {
                         tmpCustomer.set(tmpCustomer.indexOf(customer), mSelectedCustomer);
                     }
                }

            }

            EditText editText;
            linearLayout = (LinearLayout)findViewById(R.id.collectionSheet_formFields);
            linearLayout.clearFocus();
            editText = (EditText)linearLayout.findViewById(R.id.collectionSheet_formField_transactionDate);
            editText.setInputType(InputType.TYPE_NULL);
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mCollectionSheetData.getDate());
            calendar.add(Calendar.MONTH, -1);
            Date date = calendar.getTime();
            editText.setText(df.format(date).toString());
            editText.setEnabled(false);
            LinearLayout layout = (LinearLayout)findViewById(R.id.collectionSheet_entriesWrapper);
            layout.requestFocus();
            if(collectionSheet.getCollectionSheetCustomer() != null
                    && collectionSheet.getCollectionSheetCustomer().size() > 0) {
                ExpandableListView expandableListView = (ExpandableListView)findViewById(R.id.collectionSheet_entries);
                CollectionSheetExpandableListAdapter adapter = new CollectionSheetExpandableListAdapter(collectionSheet,this);
                expandableListView.setAdapter(adapter);
                ListMeasuringUtils.setListViewHeightBasedOnChildren(expandableListView);
                expandableListView.setOnItemLongClickListener(this);
                expandableListView.setOnChildClickListener(this);
                mLoanOfficer =  mCustomerService.getCurrentOfficer();
                mSaveCustomer.setUserId(mLoanOfficer.getId());
                mSaveCustomer.setPaymentType((short)1);
                mSaveCustomer.setReceiptId(receiptID.getText().toString());

                mSaveCustomer.setTransactionDate(date);

                try {
                    mSaveCustomer.setReceiptDate(df.parse(dateField.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ArrayList<SaveCollectionSheetCustomer> saveCollectionSheetCustomers = new ArrayList<SaveCollectionSheetCustomer>();
                prepareSaveCollectionSheet(saveCollectionSheetCustomers);
                mSaveCustomer.setSaveCollectionSheetCustomers(saveCollectionSheetCustomers);
            }
        }

        mTransactionTypes = mAcceptedPaymentTypes.allTypes();
        linearLayout =  (LinearLayout)findViewById(R.id.collectionSheet_formFields);
        typesSpinner = (Spinner)linearLayout.findViewById(R.id.collectionSheet_spinner_paymentTypes);
        Object[] list = mTransactionTypes.keySet().toArray();
        typesSpinner.setAdapter(new ArrayAdapter(this, R.layout.combo_box_item, list));
        double dueCollections = 0.0;
        double totalCollections = 0.0;
        double otherCollections = 0.0;
        double loanDisbursements = 0.0;
        double withdrawals = 0.0;


        List<CollectionSheetCustomer> customer = mCollectionSheetData.getCollectionSheetCustomer();
        mCustomerList = customer;
        for(CollectionSheetCustomer cst: customer) {
            CollectionSheetCustomerAccount accounts = cst.getCollectionSheetCustomerAccount();

            otherCollections += accounts.getTotalCustomerAccountCollectionFee();

            if(cst.getCollectionSheetCustomerLoan().size() > 0 && cst.getCollectionSheetCustomerLoan() != null) {
                List<CollectionSheetCustomerLoan> loans = cst.getCollectionSheetCustomerLoan();
                for(CollectionSheetCustomerLoan loan : loans) {
                    dueCollections += loan.getTotalRepaymentDue();
                    loanDisbursements += loan.getTotalDisbursement();
                }
            }
            if(cst.getCollectionSheetCustomerSaving() != null && cst.getCollectionSheetCustomerSaving().size() > 0){
                List<CollectionSheetCustomerSavings> savings = cst.getCollectionSheetCustomerSaving();
                for(CollectionSheetCustomerSavings saving :savings) {
                    dueCollections += saving.getTotalDepositAmount();
                }
            }
            if(cst.getIndividualSavingAccounts() != null && cst.getIndividualSavingAccounts().size() > 0) {
                List<CollectionSheetCustomerSavings> individuals = cst.getIndividualSavingAccounts();
                for(CollectionSheetCustomerSavings individual : individuals) {
                    dueCollections += individual.getTotalDepositAmount();
                }
            }
        }

        TextView textView = (TextView)findViewById(R.id.collectionSheet_dueCollections);
        textView.setText(String.format("%.1f", dueCollections));
        textView = (TextView)findViewById(R.id.collectionSheet_otherCollections);
        textView.setText(String.format("%.1f", otherCollections));
        textView = (TextView)findViewById(R.id.collectionSheet_collectionsTotal);
        textView.setText(String.format("%.1f", (otherCollections + dueCollections)));
        textView = (TextView)findViewById(R.id.collectionSheet_loanDisbursements);
        textView.setText(String.format("%.1f", (loanDisbursements)));
        textView = (TextView)findViewById(R.id.collectionSheet_withdrawals);
        textView.setText(String.format("%.1f", (withdrawals)));
        textView = (TextView)findViewById(R.id.collectionSheet_issuesWithdrawalsTotal);
        textView.setText(String.format("%.1f", (withdrawals + loanDisbursements)));
        textView = (TextView)findViewById(R.id.collectionSheet_netCash);
        textView.setText(String.format("%.1f", (dueCollections + otherCollections - loanDisbursements)));
    }

    private int getSelectedFee(){
        mTransactionTypes = mAcceptedPaymentTypes.allTypes();
        return 1;
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
            mAcceptedPaymentTypes = mSystemSettingsService.getAcceptedPaymentTypes();

            if (mCollectionSheetService != null) {
                collectionSheet = mCollectionSheetService.getCollectionSheetForCustomer(params[0]);

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
            if (data.getLevelId() ==1) {
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

}

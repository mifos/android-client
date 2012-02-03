package org.mifos.androidclient.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.collectionsheet.CollectionSheetCustomer;
import org.mifos.androidclient.entities.collectionsheet.CollectionSheetCustomerLoan;
import org.mifos.androidclient.entities.collectionsheet.CollectionSheetCustomerSavings;
import org.mifos.androidclient.entities.simple.AbstractCustomer;
import org.mifos.androidclient.entities.simple.Center;
import org.mifos.androidclient.templates.MifosActivity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class CollectionSheetCustomerActivity extends MifosActivity {
    private CollectionSheetCustomer mCollectionSheetCustomer;
    private List<EditText> mFieldsValues = new ArrayList<EditText>();
    private EditText feeField;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.collection_sheet_customer);

        mCollectionSheetCustomer = CollectionSheetHolder.getCurrentCustomer();
        TextView textView = (TextView)findViewById(R.id.collectionSheet_customer_header);
        textView.setText(mCollectionSheetCustomer.getName());
        List<CollectionSheetCustomerLoan> customerLoans = mCollectionSheetCustomer.getCollectionSheetCustomerLoan();
        List<CollectionSheetCustomerSavings> customerSavings = mCollectionSheetCustomer.getCollectionSheetCustomerSaving();
        List<CollectionSheetCustomerSavings> customerIndividuals = mCollectionSheetCustomer.getIndividualSavingAccounts();
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
        TableLayout dueCollectionsLayout = (TableLayout)findViewById(R.id.collectionSheet_customerTableLayout);

        prepareCollectionTable(customerLoans, customerSavings, customerIndividuals, params ,dueCollectionsLayout, mCollectionSheetCustomer);

        feeField = (EditText)findViewById(R.id.collectionSheetCustomer_ac_Collections);
        feeField.setText(String.valueOf(mCollectionSheetCustomer.getCollectionSheetCustomerAccount().getTotalCustomerAccountCollectionFee()));
        feeField.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

    }

    public void onCustomerSave(View view) {
        mCollectionSheetCustomer.getCollectionSheetCustomerAccount().setTotalCustomerAccountCollectionFee(Double.valueOf(feeField.getText().toString()));
        List<CollectionSheetCustomerLoan> customerLoans = mCollectionSheetCustomer.getCollectionSheetCustomerLoan();
        List<CollectionSheetCustomerSavings> customerSavings = mCollectionSheetCustomer.getCollectionSheetCustomerSaving();
        List<CollectionSheetCustomerSavings> customerIndividuals = mCollectionSheetCustomer.getIndividualSavingAccounts();
        totalCountsForCustomer(customerLoans,customerSavings,customerIndividuals);
        CollectionSheetHolder.setCurrentCustomer(mCollectionSheetCustomer);
        Center center = CollectionSheetHolder.getSelectedCenter();
        Intent intent = new Intent().setClass(this, CollectionSheetActivity.class);
        intent.putExtra(AbstractCustomer.BUNDLE_KEY, center);
        startActivity(intent);
    }

    private void totalCountsForCustomer(List<CollectionSheetCustomerLoan> customerLoans, List<CollectionSheetCustomerSavings> customerSavings, List<CollectionSheetCustomerSavings> customerIndividuals) {
        int i = 0;
        if (customerLoans.size() > 0) {
            int j = 0;
            for (CollectionSheetCustomerLoan loans : customerLoans) {
                mCollectionSheetCustomer.getCollectionSheetCustomerLoan().get(j).setTotalRepaymentDue(Double.valueOf(mFieldsValues.get(i).getText().toString()));
                i++;
                mCollectionSheetCustomer.getCollectionSheetCustomerLoan().get(j).setTotalDisbursement(Double.valueOf(mFieldsValues.get(i).getText().toString()));
                i++;
                j++;
            }
        }
        if (customerSavings.size() > 0) {
            int k = 0;
            for (CollectionSheetCustomerSavings savings : customerSavings) {
                mCollectionSheetCustomer.getCollectionSheetCustomerSaving().get(k).setTotalDepositAmount(Double.valueOf(mFieldsValues.get(i).getText().toString()));
                k++;
                i++;
            }
        }
        if (customerIndividuals.size() > 0) {
            int l = 0;
            for (CollectionSheetCustomerSavings individuals: customerIndividuals) {
                mCollectionSheetCustomer.getIndividualSavingAccounts().get(l).setTotalDepositAmount(Double.valueOf(mFieldsValues.get(i).getText().toString()));
                l++;
                i++;
            }
        }
    }

    private void prepareCollectionTable(List<CollectionSheetCustomerLoan> customerLoans, List<CollectionSheetCustomerSavings> customerSavings, List<CollectionSheetCustomerSavings> customerIndividuals, TableRow.LayoutParams params, TableLayout layout, CollectionSheetCustomer customer) {
        TextView textView;
        TableRow tableRow;
        EditText editText;

        tableRow = new TableRow(this);
        tableRow.setLayoutParams(params);
        textView = new TextView(this);
        textView.setText(" ");
        textView.setLayoutParams(new TableRow.LayoutParams(0));
        textView.setGravity(Gravity.CENTER);
        textView = new TextView(this,null, R.style.TableColumnHeader);
        textView.setText("  " + getString(R.string.collectionSheetCustomer_payment_label));
        textView.setLayoutParams(new TableRow.LayoutParams(1));
        textView.setGravity(Gravity.CENTER);
        tableRow.addView(textView);
        textView = new TextView(this,null, R.style.TableColumnHeader);
        textView.setText("  " + getString(R.string.collectionSheetCustomer_disbursal_label));
        textView.setLayoutParams(new TableRow.LayoutParams(2));
        textView.setGravity(Gravity.CENTER);
        tableRow.addView(textView);
        textView = new TextView(this,null, R.style.TableColumnHeader);
        textView.setText("  " + getString(R.string.collectionSheetCustomer_deposit_label));
        textView.setLayoutParams(new TableRow.LayoutParams(3));
        textView.setGravity(Gravity.CENTER);
        tableRow.addView(textView);
        textView = new TextView(this,null, R.style.TableColumnHeader);
        textView.setText("  " + getString(R.string.collectionSheetCustomer_withdrawals_label));
        textView.setLayoutParams(new TableRow.LayoutParams(4));
        textView.setGravity(Gravity.CENTER);
        tableRow.addView(textView);
        layout.addView(tableRow);

        for(CollectionSheetCustomerLoan customerLoan : customerLoans) {
            tableRow = new TableRow(this);
            tableRow.setLayoutParams(params);
            textView = new TextView(this, null , R.style.TableItem);
            textView.setText(customerLoan.getProductShortName());
            textView.setLayoutParams(new TableRow.LayoutParams(0));
            textView.setGravity(Gravity.LEFT);
            tableRow.addView(textView);


                    editText = new EditText(this);
                    editText.setText(String.valueOf(customerLoan.getAmountDueAtDisbursement() + customerLoan.getTotalRepaymentDue()));
                    editText.setLayoutParams(new TableRow.LayoutParams(1));
                    editText.setGravity(Gravity.RIGHT);
                    editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    mFieldsValues.add(editText);
                    tableRow.addView(editText);

                    editText = new EditText(this);
                    editText.setText(String.valueOf(customerLoan.getTotalDisbursement()));
                    editText.setLayoutParams(new TableRow.LayoutParams(2));
                    editText.setGravity(Gravity.RIGHT);
                    editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    mFieldsValues.add(editText);
                    tableRow.addView(editText);


            layout.addView(tableRow);
        }

        for(CollectionSheetCustomerSavings customerSaving : customerSavings) {
            tableRow = new TableRow(this);
            tableRow.setLayoutParams(params);
            textView = new TextView(this, null , R.style.TableItem);
            textView.setText(customerSaving.getProductShortName());
            textView.setLayoutParams(new TableRow.LayoutParams(0));
            tableRow.addView(textView);
            editText = new EditText(this);

                editText.setText(String.valueOf(customerSaving.getTotalDepositAmount()));
                editText.setLayoutParams(new TableRow.LayoutParams(3));
                editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                mFieldsValues.add(editText);
                tableRow.addView(editText);
                layout.addView(tableRow);

        }

        for(CollectionSheetCustomerSavings customerIndividual :customerIndividuals) {
            tableRow = new TableRow(this);
            tableRow.setLayoutParams(params);

            textView = new TextView(this, null , R.style.TableItem);
            textView.setText(customerIndividual.getProductShortName());
            textView.setLayoutParams(new TableRow.LayoutParams(0));
            tableRow.addView(textView);
            editText = new EditText(this);
            editText.setText(String.valueOf(customerIndividual.getTotalDepositAmount()));
            editText.setLayoutParams(new TableRow.LayoutParams(3));
            editText.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            mFieldsValues.add(editText);
            tableRow.addView(editText);
            layout.addView(tableRow);
        }
    }
}

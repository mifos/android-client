package org.mifos.androidclient.main;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.*;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.AcceptedPaymentTypes;
import org.mifos.androidclient.entities.collectionsheet.CollectionSheetData;
import org.mifos.androidclient.entities.collectionsheet.SaveCollectionSheet;
import org.mifos.androidclient.entities.simple.AbstractCustomer;
import org.mifos.androidclient.entities.simple.Center;
import org.mifos.androidclient.net.services.SystemSettingsService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

public class PreCollectionSheetActivity extends CollectionSheetActivity implements DatePickerDialog.OnDateSetListener,
        View.OnFocusChangeListener {

    private static final int DATE_DIALOG_ID = 0;
    private CollectionSheetData mCollectionSheetData;
    private EditText dateField;
    private EditText receiptID;
    private Spinner typesSpinner;
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    private Map<String, Integer> mTransactionTypes;
    private SaveCollectionSheet mSaveCustomer = new SaveCollectionSheet();

    private AcceptedPaymentTypes mAcceptedPaymentTypes;
    private SystemSettingsService mSystemSettingsService;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.pre_collection_sheet);
        mSystemSettingsService = new SystemSettingsService(this);


    }

    public void onContinueCollectionSheet(View view) {
        mSaveCustomer.setUserId(mLoanOfficer.getId());
        mSaveCustomer.setPaymentType((short)1);
        mSaveCustomer.setReceiptId(receiptID.getText().toString());
        mSaveCustomer.setTransactionDate(mCollectionSheetData.getDate());
        if (mSaveCustomer.getReceiptDate() != null) {
            try {
                mSaveCustomer.setReceiptDate(df.parse(dateField.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        CollectionSheetHolder.setSaveCollectionSheet(mSaveCustomer);
        Center center = CollectionSheetHolder.getSelectedCenter();
        Intent intent = new Intent().setClass(this, CollectionSheetActivity.class);
        intent.putExtra(AbstractCustomer.BUNDLE_KEY, center);
        startActivity(intent);
    }

    public void onBackFromPreCollectionSheet(View view) {
        Intent intent = new Intent().setClass(this, CollectionSheetCentersActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSessionActive() {
        super.onSessionActive();
        if (mCollectionSheetData == null) {
            runCollectionSheetTask();
        }
    }

    @Override
    public void updateContent(CollectionSheetData collectionSheet) {
        if (collectionSheet != null) {

            mCollectionSheetData = collectionSheet;
            LinearLayout linearLayout;
            EditText editText;
            linearLayout = (LinearLayout)findViewById(R.id.pre_collectionSheet_formFields);
            editText = (EditText)linearLayout.findViewById(R.id.collectionSheet_formField_transactionDate);
            editText.setInputType(InputType.TYPE_NULL);
            editText.setText(df.format(mCollectionSheetData.getDate()).toString());
            editText.setEnabled(false);
            linearLayout = (LinearLayout)findViewById(R.id.collectionSheet_entriesWrapper);
            mAcceptedPaymentTypes = mSystemSettingsService.getAcceptedPaymentTypes();
            receiptID = (EditText)findViewById(R.id.collectionSheet_formField_receiptId);
            receiptID.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            dateField = (EditText)findViewById(R.id.collectionSheet_formField_receiptDate);
            dateField.setInputType(InputType.TYPE_NULL);
            dateField.setOnFocusChangeListener(this);

            mTransactionTypes = mAcceptedPaymentTypes.allTypes();
            linearLayout =  (LinearLayout)findViewById(R.id.pre_collectionSheet_formFields);
            typesSpinner = (Spinner)linearLayout.findViewById(R.id.collectionSheet_spinner_paymentTypes);
            Object[] list = mTransactionTypes.keySet().toArray();
            typesSpinner.setAdapter(new ArrayAdapter(this, R.layout.combo_box_item, list));
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
}

package org.mifos.androidclient.main;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.AbstractAccountDetails;
import org.mifos.androidclient.entities.simple.Fee;
import org.mifos.androidclient.net.services.AccountService;
import org.mifos.androidclient.templates.OperationFormActivity;
import org.mifos.androidclient.util.ApplicationConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApplyLoanAccountChargeActivity extends OperationFormActivity
        implements AdapterView.OnItemSelectedListener {

    public static final int REQUEST_CODE = 2;

    private static final String PARAM_FEE_ID = "feeId";
    private static final String PARAM_AMOUNT = "amount";

    private String mAccountNumber;
    private Map<String, Map<String, String>> mApplicableFees;
    private AccountService mAccountService;
    private Spinner mFeeType;
    private EditText mAmountInput;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mAccountNumber = getIntent().getStringExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY);
        mApplicableFees = (Map<String, Map<String, String>>)getIntent().getSerializableExtra(Fee.BUNDLE_KEY);

        mAccountService = new AccountService(this);

        setFormHeader(getString(R.string.applyLoanAccountCharge_header));
        setStatusVisible(false);
        mFeeType = addComboBoxFormField(getString(R.string.applyLoanAccountCharge_feeType_fieldLabel), new ArrayList<String>(mApplicableFees.keySet()));
        mFeeType.setOnItemSelectedListener(this);
        mAmountInput = addDecimalNumberFormField(getString(R.string.applyLoanAccountCharge_amount_fieldLabel));
        setFormFieldsVisible(true);
        updateFormForSelectedFee((String) mFeeType.getSelectedItem());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        updateFormForSelectedFee((String) mFeeType.getSelectedItem());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }

    @Override
    protected Map<String, String> onPrepareParameters() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(PARAM_FEE_ID, mApplicableFees.get(mFeeType.getSelectedItem()).get(Fee.KEY_ID));
        params.put(PARAM_AMOUNT, mAmountInput.getText().toString());
        return params;
    }

    @Override
    protected Map<String, String> onFormSubmission(Map<String, String> parameters) {
        return mAccountService.applyLoanCharge(mAccountNumber, parameters);
    }

    @Override
    protected void onSubmissionResult(Map<String, String> result) {
        super.onSubmissionResult(result);
    }

    private void updateFormForSelectedFee(String selectedFee) {
        Map<String, String> fee = mApplicableFees.get(selectedFee);
        StringBuilder info = new StringBuilder();
        if (fee.get(Fee.KEY_AMOUNT_OR_RATE) != null) {
            mAmountInput.setText(fee.get(Fee.KEY_AMOUNT_OR_RATE));
        } else {
            mAmountInput.setText(ApplicationConstants.EMPTY_STRING);
        }
        if (fee.get(Fee.KEY_PERIODICITY) != null) {
            mAmountInput.setEnabled(false);
            info.append(fee.get(Fee.KEY_PERIODICITY));
            info.append(". ");
        } else {
            mAmountInput.setEnabled(true);
        }
        if (fee.get(Fee.KEY_FORMULA) != null) {
            info.append(fee.get(Fee.KEY_FORMULA));
            info.append(".");
        }
        if (info.length() > 0) {
            setFormAdditionalInformation(info.toString());
            setAdditionalInformationVisible(true);
        } else {
            setFormAdditionalInformation(ApplicationConstants.EMPTY_STRING);
            setAdditionalInformationVisible(false);
        }
    }

}

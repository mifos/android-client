package org.mifos.androidclient.main;


import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.AbstractAccountDetails;
import org.mifos.androidclient.entities.simple.Fee;
import org.mifos.androidclient.net.services.AccountService;
import org.mifos.androidclient.templates.OperationFormActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApplyLoanAccountChargeActivity extends OperationFormActivity {

    public static final int REQUEST_CODE = 2;

    private static final String PARAM_FEE_ID = "feeId";
    private static final String PARAM_AMOUNT = "amount";

    private String mAccountNumber;
    private Map<String, String> mApplicableFees;
    private AccountService mAccountService;
    private Spinner mFeeType;
    private EditText mAmountInput;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mAccountNumber = getIntent().getStringExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY);
        mApplicableFees = (Map<String, String>)getIntent().getSerializableExtra(Fee.BUNDLE_KEY);

        mAccountService = new AccountService(this);

        setFormHeader(getString(R.string.applyLoanAccountCharge_header));
        setStatusVisible(false);
        mFeeType = addComboBoxFormField(getString(R.string.applyLoanAccountCharge_feeType_fieldLabel), new ArrayList<String>(mApplicableFees.keySet()));
        mAmountInput = addDecimalNumberFormField(getString(R.string.applyLoanAccountCharge_amount_fieldLabel));
        setFormFieldsVisible(true);

    }

    @Override
    protected Map<String, String> onPrepareParameters() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(PARAM_FEE_ID, mApplicableFees.get(mFeeType.getSelectedItem()));
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

}

package org.mifos.androidclient.main;

import android.os.Bundle;
import android.widget.EditText;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.AbstractAccountDetails;
import org.mifos.androidclient.net.services.AccountService;
import org.mifos.androidclient.templates.OperationFormActivity;

import java.util.HashMap;
import java.util.Map;


public class ApplyLoanAccountRepayLoanActivity extends OperationFormActivity {
 public static final int REQUEST_CODE = 2;

    private static final String PARAM_AMOUNT = "amount";

    private String mAccountNumber;
    private AccountService mAccountService;
    private EditText mAmountInput;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mAccountNumber = getIntent().getStringExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY);
        mAccountService = new AccountService(this);

        setFormHeader(getString(R.string.accountTransaction_repayLoanButton_header));
        setStatusVisible(false);
        mAmountInput = addDecimalNumberFormField(getString(R.string.applyLoanAccountRepayLoan_amount_label));
        setFormFieldsVisible(true);

    }

    @Override
    protected Map<String, String> onPrepareParameters() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(PARAM_AMOUNT, mAmountInput.getText().toString());
        return params;
    }

    @Override
    protected Map<String, String> onFormSubmission(Map<String, String> parameters) {
        return mAccountService.repayLoan(mAccountNumber, parameters);
    }

    @Override
    protected void onSubmissionResult(Map<String, String> result) {
        super.onSubmissionResult(result);
    }
}
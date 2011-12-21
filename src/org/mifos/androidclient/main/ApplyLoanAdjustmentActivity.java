

package org.mifos.androidclient.main;

import android.os.Bundle;
import android.widget.EditText;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.AbstractAccountDetails;
import org.mifos.androidclient.net.services.AccountService;
import org.mifos.androidclient.templates.OperationFormActivity;

import java.util.HashMap;
import java.util.Map;

public class ApplyLoanAdjustmentActivity extends OperationFormActivity {

    public final static int REQUEST_CODE = 6;

    private final static String PARAM_NOTE = "note";

    private String mAccountNumber;

    private AccountService mAccountService;

    private EditText mNoteInput;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mAccountNumber = getIntent().getStringExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY);

        mAccountService = new AccountService(this);

        mNoteInput = addTextFormField(getString(R.string.applyLoanAdjustment_note_fieldLabel));

        setFormHeader(getString(R.string.applyLoanAdjustment_header));
        setFormAdditionalInformation(getString(R.string.applyLoanAdjustment_additionalInformation));
        setAdditionalInformationVisible(true);
        setFormFieldsVisible(true);
    }

    @Override
    protected Map<String, String> onPrepareParameters() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(PARAM_NOTE, mNoteInput.getText().toString());
        return params;
    }

    @Override
    protected Map<String, String> onFormSubmission(Map<String, String> parameters) {
        return mAccountService.applyLoanAccountAdjustment(mAccountNumber, parameters);
    }

}

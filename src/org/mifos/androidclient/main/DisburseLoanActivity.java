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

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.AbstractAccountDetails;
import org.mifos.androidclient.entities.account.AcceptedPaymentTypes;
import org.mifos.androidclient.entities.account.AccountFee;
import org.mifos.androidclient.entities.account.LoanSummary;
import org.mifos.androidclient.net.services.AccountService;
import org.mifos.androidclient.templates.OperationFormActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DisburseLoanActivity extends OperationFormActivity {

    public static final int REQUEST_CODE = 3;

    private String mGlobalAccountNumber;
    private Double mOriginalPrincipal;
    private List<AccountFee> mOnDisbursementFees;
    private Map<String, Integer> mDisbursementPaymentTypes;
    private Map<String, Integer> mFeePaymentTypes;

    private EditText mDisbursalDateInput;
    private EditText mReceiptIdInput;
    private EditText mReceiptDateInput;
    private EditText mLoanAmountInput;
    private Spinner mDisbursementPaymentModeInput;
    private EditText mFeeAmountInput;
    private Spinner mFeePaymentModeInput;

    private AccountService mAccountService;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mGlobalAccountNumber = getIntent().getStringExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY);
        try {
            mOriginalPrincipal = Double.parseDouble(getIntent().getStringExtra(LoanSummary.ORIGINAL_PRINCIPAL_BUNDLE_KEY));
        } catch (NumberFormatException e) {
            mOriginalPrincipal = 0.0;
        }

        mOnDisbursementFees = (List<AccountFee>)getIntent().getSerializableExtra(AccountFee.BUNDLE_KEY);
        mDisbursementPaymentTypes = (Map<String, Integer>)getIntent().getSerializableExtra(AcceptedPaymentTypes.ACCEPTED_DISBURSEMENT_PAYMENT_TYPES_BUNDLE_KEY);
        mFeePaymentTypes = (Map<String, Integer>)getIntent().getSerializableExtra(AcceptedPaymentTypes.ACCEPTED_FEE_PAYMENT_TYPES_BUNDLE_KEY);

        mAccountService = new AccountService(this);

        mDisbursalDateInput = addDateFormField(getString(R.string.disburseLoan_disbursalDate_fieldLabel));
        mReceiptIdInput = addTextFormField(getString(R.string.disburseLoan_receiptId_fieldLabel));
        mReceiptDateInput = addDateFormField(getString(R.string.disburseLoan_receiptDate_fieldLabel));
        mLoanAmountInput = addDecimalNumberFormField(getString(R.string.disburseLoan_loanAmount_fieldLabel));
        mDisbursementPaymentModeInput = addComboBoxFormField(getString(R.string.disburseLoan_loanPaymentMode_fieldLabel), new ArrayList<String>(mDisbursementPaymentTypes.keySet()));
        mFeeAmountInput = addDecimalNumberFormField(getString(R.string.disburseLoan_feeAmount_fieldLabel));
        mFeePaymentModeInput = addComboBoxFormField(getString(R.string.disburseLoan_feePaymentMode_fieldLabel), new ArrayList<String>(mFeePaymentTypes.keySet()));

        mLoanAmountInput.setText(mOriginalPrincipal.toString());
        setInputEnabled(mLoanAmountInput, false);
        mFeeAmountInput.setText(calculateFeesTotal(mOnDisbursementFees).toString());
        setInputEnabled(mFeeAmountInput, false);

        setFormHeader(getString(R.string.disburseLoan_header));
        setFormAdditionalInformation(getString(R.string.disburseLoan_additionalInformation, mOriginalPrincipal));
        setAdditionalInformationVisible(true);
    }

    @Override
    protected Map<String, String> onPrepareParameters() {
        return null;
    }

    @Override
    protected Map<String, String> onFormSubmission(Map<String, String> parameters) {
        return mAccountService.disburseLoan(mGlobalAccountNumber, parameters);
    }

    private Double calculateFeesTotal(List<AccountFee> fees) {
        double total = 0.0;
        for (AccountFee fee : fees) {
            total += fee.getAccountFeeAmount();
        }
        return total;
    }

}

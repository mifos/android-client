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
import org.mifos.androidclient.net.services.AccountService;
import org.mifos.androidclient.templates.OperationFormActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApplyLoanAccountRepayLoanActivity extends OperationFormActivity {

    public static final int REQUEST_CODE = 2;

    private static final String PARAM_AMOUNT = "amount";
    private static final String PARAM_TRANSACTION_DATE = "paymentDate";
    private static final String PARAM_RECEIPT_ID = "receiptId";
    private static final String PARAM_RECEIPT_DATE = "receiptDate";
    private static final String PARAM_PAYMENT_MODE = "paymentModeId";

    private String mAccountNumber;
    private Map<String, Integer> mTransactionTypes;

    private AccountService mAccountService;

    private EditText mTransactionDateInput;
    private EditText mAmountInput;
    private Spinner mPaymentModeInput;
    private EditText mReceiptIdInput;
    private EditText mReceiptDateInput;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mAccountNumber = getIntent().getStringExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY);
        mTransactionTypes = (Map<String, Integer>)getIntent().getSerializableExtra(AcceptedPaymentTypes.ACCEPTED_REPAYMENT_PAYMENT_TYPES_BUNDLE_KEY);

        mAccountService = new AccountService(this);

        setFormHeader(getString(R.string.accountTransaction_repayLoanButton_header));
        setStatusVisible(false);

        mTransactionDateInput = addDateFormField(getString(R.string.applyLoanAccountRepayLoan_transactionDate_label));
        mAmountInput = addDecimalNumberFormField(getString(R.string.applyLoanAccountRepayLoan_amount_label));
        mPaymentModeInput = addComboBoxFormField(
                getString(R.string.applyLoanAccountRepayLoan_paymentMode_label),
                new ArrayList<String>(mTransactionTypes.keySet())
        );
        mReceiptIdInput = addTextFormField(getString(R.string.applyLoanAccountRepayLoan_receiptId_label));
        mReceiptDateInput = addDateFormField(getString(R.string.applyLoanAccountRepayLoan_receiptDate_label));

        setFormFieldsVisible(true);
    }

    @Override
    protected Map<String, String> onPrepareParameters() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(PARAM_TRANSACTION_DATE, mTransactionDateInput.getText().toString());
        params.put(PARAM_AMOUNT, mAmountInput.getText().toString());
        params.put(PARAM_PAYMENT_MODE, mTransactionTypes.get(mPaymentModeInput.getSelectedItem()).toString());
        params.put(PARAM_RECEIPT_ID, mReceiptIdInput.getText().toString());
        params.put(PARAM_RECEIPT_DATE, mReceiptDateInput.getText().toString());
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
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.AbstractAccountDetails;
import org.mifos.androidclient.entities.account.AcceptedPaymentTypes;
import org.mifos.androidclient.net.services.AccountService;
import org.mifos.androidclient.templates.OperationFormActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SavingsTransactionActivity extends OperationFormActivity
        implements AdapterView.OnItemSelectedListener {

    public static final int REQUEST_CODE = 7;

    private String mAccountNumber;

    private Map<String, Map<String, Integer>> mTransactionTypes;

    private EditText mTransactionDateInput;
    private Spinner mTransactionTypeInput;
    private EditText mAmountInput;
    private Spinner mPaymentModeInput;
    private EditText mReceiptId;
    private EditText mReceiptDate;

    private AccountService mAccountService;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mAccountNumber = getIntent().getStringExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY);
        Map<String, Integer> depositPaymentTypes = (Map<String, Integer>)getIntent().getSerializableExtra(AcceptedPaymentTypes.ACCEPTED_DEPOSIT_PAYMENT_TYPES_BUNDLE_KEY);
        Map<String, Integer> withdrawalPaymentTypes = (Map<String, Integer>)getIntent().getSerializableExtra(AcceptedPaymentTypes.ACCEPTED_WITHDRAWAL_PAYMENT_TYPES_BUNDLE_KEY);

        mTransactionTypes = new HashMap<String, Map<String, Integer>>();
        mTransactionTypes.put(getString(R.string.savingsTransaction_transactionType_deposit), depositPaymentTypes);
        mTransactionTypes.put(getString(R.string.savingsTransaction_transactionType_withdrawal), withdrawalPaymentTypes);

        mAccountService = new AccountService(this);

        mTransactionDateInput = addDateFormField(getString(R.string.savingsTransaction_transactionDate_fieldLabel));
        mTransactionTypeInput = addComboBoxFormField(getString(R.string.savingsTransaction_transactionType_fieldLabel), new ArrayList<String>(mTransactionTypes.keySet()));
        mTransactionTypeInput.setOnItemSelectedListener(this);
        mAmountInput = addDecimalNumberFormField(getString(R.string.savingsTransaction_amount_fieldLabel));
        mPaymentModeInput = addComboBoxFormField(
                getString(R.string.savingsTransaction_paymentMode_fieldLabel),
                new ArrayList<String>(mTransactionTypes.get(mTransactionTypeInput.getSelectedItem()).keySet())
        );
        mReceiptId = addTextFormField(getString(R.string.savingsTransaction_receiptId_fieldLabel));
        mReceiptDate = addDateFormField(getString(R.string.savingsTransaction_receiptDate_fieldLabel));

        setFormHeader(getString(R.string.savingsTransaction_header));
        setFormFieldsVisible(true);
    }

    @Override
    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
        List<String> items = new ArrayList<String>();
        items.addAll(mTransactionTypes.get(mTransactionTypeInput.getSelectedItem()).keySet());
        replaceComboBoxItems(mPaymentModeInput, items);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parentView) { }

    @Override
    protected Map<String, String> onPrepareParameters() {
        return null;
    }

    @Override
    protected Map<String, String> onFormSubmission(Map<String, String> parameters) {
        return null;
    }

}

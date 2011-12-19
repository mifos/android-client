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
import android.widget.TextView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.AbstractAccountDetails;
import org.mifos.androidclient.net.services.AccountService;
import org.mifos.androidclient.templates.OperationFormActivity;

import java.util.HashMap;
import java.util.Map;

public class ApplySavingsAdjustmentActivity extends OperationFormActivity {

    public static final int REQUEST_CODE = 5;

    private static final String PARAM_AMOUNT = "amount";
    private static final String PARAM_NOTE = "note";

    private String mAccountNumber;

    private TextView mAmountInput;
    private TextView mNoteInput;

    private AccountService mAccountService;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mAccountNumber = getIntent().getStringExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY);

        mAccountService = new AccountService(this);

        mAmountInput = addDecimalNumberFormField(getString(R.string.applySavingsAdjustment_amount_fieldLabel));
        mNoteInput = addTextFormField(getString(R.string.applySavingsAdjustment_note_fieldLabel));

        setFormHeader(getString(R.string.applySavingsAdjustment_header));
        setFormFieldsVisible(true);
    }

    @Override
    protected Map<String, String> onPrepareParameters() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(PARAM_AMOUNT, mAmountInput.getText().toString());
        params.put(PARAM_NOTE, mNoteInput.getText().toString());
        return params;
    }

    @Override
    protected Map<String, String> onFormSubmission(Map<String, String> parameters) {
        return mAccountService.applySavingsAccountAdjustment(mAccountNumber, parameters);
    }

}

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
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.simple.AbstractCustomer;
import org.mifos.androidclient.net.services.CustomerService;
import org.mifos.androidclient.templates.OperationFormActivity;

import java.util.HashMap;
import java.util.Map;

public class ApplyCustomerChargeActivity extends OperationFormActivity {

    public static final int REQUEST_CODE = 2;

    private static final String PARAM_FEE_ID = "feeId";
    private static final String PARAM_AMOUNT = "amount";

    private String mGlobalCustomerNumber;
    private CustomerService mCustomerService;
    private EditText mFeeIdInput;
    private EditText mAmountInput;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mGlobalCustomerNumber = getIntent().getStringExtra(AbstractCustomer.CUSTOMER_NUMBER_BUNDLE_KEY);
        mCustomerService = new CustomerService(this);

        setFormHeader(getString(R.string.applyCustomerCharge_header));
        setStatusVisible(false);
        mFeeIdInput = addTextFormField(getString(R.string.applyCustomerCharge_feeId_fieldLabel));
        mAmountInput = addDecimalNumberFormField(getString(R.string.applyCustomerCharge_amount_fieldLabel));
        setFormFieldsVisible(true);
    }

    @Override
    protected Map<String, String> onPrepareParameters() {
        Map<String, String> params = new HashMap<String, String>();
        params.put(PARAM_FEE_ID, mFeeIdInput.getText().toString());
        params.put(PARAM_AMOUNT, mAmountInput.getText().toString());
        return params;
    }

    @Override
    protected Map<String, String> onFormSubmission(Map<String, String> parameters) {
        return mCustomerService.applyCharge(mGlobalCustomerNumber, parameters);
    }

    @Override
    protected void onSubmissionResult(Map<String, String> result) {
        super.onSubmissionResult(result);

    }

}

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
import org.mifos.androidclient.entities.simple.AbstractCustomer;
import org.mifos.androidclient.entities.simple.Fee;
import org.mifos.androidclient.net.services.CustomerService;
import org.mifos.androidclient.templates.OperationFormActivity;
import org.mifos.androidclient.util.ApplicationConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApplyCustomerChargeActivity extends OperationFormActivity
        implements AdapterView.OnItemSelectedListener{

    public static final int REQUEST_CODE = 2;

    private static final String PARAM_FEE_ID = "feeId";
    private static final String PARAM_AMOUNT = "amount";

    private String mGlobalCustomerNumber;
    private Map<String, Map<String, String>> mApplicableFees;
    private CustomerService mCustomerService;
    private Spinner mFeeType;
    private EditText mAmountInput;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mGlobalCustomerNumber = getIntent().getStringExtra(AbstractCustomer.CUSTOMER_NUMBER_BUNDLE_KEY);
        mApplicableFees = (Map<String, Map<String, String>>)getIntent().getSerializableExtra(Fee.BUNDLE_KEY);

        mCustomerService = new CustomerService(this);

        setFormHeader(getString(R.string.applyCustomerCharge_header));
        setStatusVisible(false);
        mFeeType = addComboBoxFormField(getString(R.string.applyCustomerCharge_feeType_fieldLabel), new ArrayList<String>(mApplicableFees.keySet()));
        mFeeType.setOnItemSelectedListener(this);
        mAmountInput = addDecimalNumberFormField(getString(R.string.applyCustomerCharge_amount_fieldLabel));
        setFormFieldsVisible(true);
        updateFormForSelectedFee((String) mFeeType.getSelectedItem());
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        updateFormForSelectedFee((String)mFeeType.getSelectedItem());
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
        return mCustomerService.applyCharge(mGlobalCustomerNumber, parameters);
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

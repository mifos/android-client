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

package org.mifos.androidclient.templates;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.SessionStatus;
import org.mifos.androidclient.main.LoginActivity;
import org.mifos.androidclient.net.services.LoginService;
import org.mifos.androidclient.net.services.SessionStatusService;
import org.springframework.web.client.RestClientException;

import java.util.Map;

/**
 * A base class for forms used to perform operations on Mifos customers
 * and accounts.
 */
public abstract class OperationFormActivity extends MifosActivity {

    private LoginService mLoginService;
    private SessionStatusService mSessionStatusService;
    private FormSubmissionTask mFormSubmissionTask;
    private boolean mLoginRequired;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mLoginService = new LoginService(this);
        mSessionStatusService = new SessionStatusService(this);
    }

    public void setFormHeader(String value) {
        TextView header = (TextView)findViewById(R.id.operationForm_header);
        header.setText(value);
    }

    public void setFormAdditionalInformation(String value) {
        TextView additionalInformation = (TextView)findViewById(R.id.operationForm_additionalInformation);
        additionalInformation.setText(value);
    }

    public void setStatus(boolean success) {
        TextView status = (TextView)findViewById(R.id.operationForm_status);
        if (success) {
            status.setText(getString(R.string.operationForm_status_success));
            status.setTextColor(getResources().getColor(R.color.successGreen));
        } else {
            status.setText(getString(R.string.operationForm_status_error));
            status.setTextColor(getResources().getColor(R.color.errorRed));
        }
    }

    public void setOperationSummaryContent(Map<String,String> operationResults) {

    }

    public EditText addTextFormField() {
        return null;
    }

    public EditText addDecimalNumberFormField() {
        return null;
    }

    public void setSubmissionButtonSet() {
        Button button;
        button = (Button)findViewById(R.id.operationForm_submitButton);
        setViewVisible(button, true);
        button = (Button)findViewById(R.id.operationForm_goBackButton);
        setViewVisible(button, true);
        button = (Button)findViewById(R.id.operationForm_okButton);
        setViewVisible(button, false);
    }

    public void setSuccessButtonSet() {
        Button button;
        button = (Button)findViewById(R.id.operationForm_submitButton);
        setViewVisible(button, false);
        button = (Button)findViewById(R.id.operationForm_goBackButton);
        setViewVisible(button, false);
        button = (Button)findViewById(R.id.operationForm_okButton);
        setViewVisible(button, true);
    }

    public void setAdditionalInformationVisible(boolean visible) {
        TextView additionalInformation = (TextView)findViewById(R.id.operationForm_additionalInformation);
        setViewVisible(additionalInformation, visible);
    }

    public void setOperationSummaryVisible(boolean visible) {
        TableLayout operationSummary = (TableLayout)findViewById(R.id.operationForm_operationSummary);
        setViewVisible(operationSummary, visible);
    }

    public void setFormFieldsVisible(boolean visible) {
        LinearLayout formFields = (LinearLayout)findViewById(R.id.operationForm_formFields);
        setViewVisible(formFields, visible);
    }

    public void onOkPressed(View view) {
        finish();
    }

    public void onSubmitPressed(View view) {
        runFormSubmissionTask(onPrepareParameters());
    }

    public void onGoBackPressed(View view) {
        finish();
    }

    private void setViewVisible(View view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public boolean isLoginRequired() {
        synchronized (this) {
            return mLoginRequired;
        }
    }

    public void setLoginRequired(boolean loginRequired) {
        synchronized (this) {
            this.mLoginRequired = loginRequired;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LoginActivity.REQUEST_CODE:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        break;
                    case Activity.RESULT_CANCELED:
                        finish();
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
    }

    protected abstract Map<String,String> onPrepareParameters();

    protected abstract Map<String, String> onFormSubmission(Map<String,String> parameters);

    protected void onSubmissionResult(Map<String, String> result) {

    }

    protected void runFormSubmissionTask(Map<String,String> parameters) {
        if (mFormSubmissionTask == null || mFormSubmissionTask.getStatus() != AsyncTask.Status.RUNNING) {
            setLoginRequired(false);
            mFormSubmissionTask = new FormSubmissionTask(
                    this,
                    getString(R.string.dialog_loading_message),
                    getString(R.string.dialog_submitting_data)
            );
            mFormSubmissionTask.execute(parameters);
        }
    }

    private class FormSubmissionTask extends ServiceConnectivityTask<Map<String,String>, Void, Map<String,String>> {

        public FormSubmissionTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected Map<String, String> doInBackgroundBody(Map<String, String>... params) throws RestClientException, IllegalArgumentException {
            Map<String, String> result = null;
            SessionStatus status = mSessionStatusService.getSessionStatus();
            if (status.isSuccessful()) {
                result = onFormSubmission(params[0]);
            } else {
                if (hasUserCredentials()) {
                    status = mLoginService.logIn(getUserLogin(), getUserPassword());
                    if (status.isSuccessful()) {
                        result = onFormSubmission(params[0]);
                    } else {
                        setTerminated(true);
                        setLoginRequired(true);
                    }
                }
            }
            return result;
        }

        @Override
        protected void onPostExecuteBody(Map<String, String> result) {
            if (isLoginRequired()) {
                Intent intent = new Intent().setClass(mContext, LoginActivity.class);
                startActivityForResult(intent, LoginActivity.REQUEST_CODE);
            } else {
                onSubmissionResult(result);
            }
        }

    }

}

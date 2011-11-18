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

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.SessionStatus;
import org.mifos.androidclient.net.RestConnector;
import org.mifos.androidclient.net.services.LoginService;
import org.mifos.androidclient.templates.MifosActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.springframework.web.client.RestClientException;

public class LoginActivity extends MifosActivity {

    public static final int REQUEST_CODE = 1;

    private EditText mLoginField;
    private EditText mPasswordField;
    private TextView mLoginErrors;

    private LoginService mLoginService;
    private LoginTask mLoginTask;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_panel);

        mLoginField = (EditText)findViewById(R.id.login_login);
        mPasswordField = (EditText)findViewById(R.id.login_password);
        mLoginErrors = (TextView)findViewById(R.id.login_errors);
        mLoginService = new LoginService(this);
    }

    /**
     * Executed when the login button is pressed by the user.<br />
     * Configured in the layout file of this activity.
     *
     * @param loginButton the button which has been pressed
     */
    public void onLoginClicked(View loginButton) {
        String userLogin = mLoginField.getText().toString();
        String userPassword = mPasswordField.getText().toString();
        runLoginTask(userLogin, userPassword);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

    private void runLoginTask(String login, String password) {
        if (mLoginTask == null || mLoginTask.getStatus() != AsyncTask.Status.RUNNING) {
            mLoginTask = new LoginTask(this, getString(R.string.dialog_login_title), getString(R.string.dialog_loading_message));
            mLoginTask.execute(login, password);
        }
    }

    private class LoginTask extends ServiceConnectivityTask<String, Void, Boolean> {

        public LoginTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected Boolean doInBackgroundBody(String... params)
                throws RestClientException, IllegalArgumentException {
            boolean result = false;
            SessionStatus status = mLoginService.logIn(params[0], params[1]);
            if (status.isSuccessful()) {
                setUserLogin(params[0]);
                setUserPassword(params[1]);
                result = true;
            }
            return result;
        }

        @Override
        protected void onPostExecuteBody(Boolean result) {
            if (result) {
                if (mLoginErrors.isShown()) {
                    mLoginErrors.setVisibility(View.INVISIBLE);
                }
                RestConnector.getInstance().setLoggedIn(true);
                setResult(Activity.RESULT_OK);
                finish();
            } else {
                if(!mLoginErrors.isShown()) {
                    mLoginErrors.setVisibility(View.VISIBLE);
                }
            }
        }

    }

}

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

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.RequestStatus;
import org.mifos.androidclient.net.services.LoginService;
import org.mifos.androidclient.templates.MifosActivity;

public class LoginActivity extends MifosActivity {

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


    private void runLoginTask(String login, String password) {
        if (mLoginTask == null || mLoginTask.getStatus() != AsyncTask.Status.RUNNING) {
            mLoginTask = (LoginTask)new LoginTask().execute(login, password);
        }
    }

    private class LoginTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mUIUtils.displayProgressDialog(getString(R.string.dialog_login_title), getString(R.string.dialog_login_message), false);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            RequestStatus status = mLoginService.logIn(strings[0], strings[1]);
            return status.isSuccessful();
        }

        @Override
        protected void onPostExecute(Boolean loginSuccessful) {
            super.onPostExecute(loginSuccessful);
            mUIUtils.cancelProgressDialog();
            if (loginSuccessful) {
                if (mLoginErrors.isShown()) {
                    mLoginErrors.setVisibility(View.INVISIBLE);
                }
            } else {
                if(!mLoginErrors.isShown()) {
                    mLoginErrors.setVisibility(View.VISIBLE);
                }
            }
        }

    }

}

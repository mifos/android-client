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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import org.mifos.androidclient.R;
import org.mifos.androidclient.main.ClientMainActivity;
import org.mifos.androidclient.net.RestConnector;
import org.mifos.androidclient.util.ApplicationConstants;
import org.mifos.androidclient.util.ui.UIUtils;

public abstract class MifosActivity extends Activity {

    protected UIUtils mUIUtils;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mUIUtils = new UIUtils(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    protected void logOut() {
        resetUserCredentials();
        RestConnector.resetConnection();
        Intent intent = new Intent().setClass(this, ClientMainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    protected boolean hasUserCredentials() {
        SharedPreferences settings = getSharedPreferences(ApplicationConstants.MIFOS_APPLICATION_PREFERENCES, MODE_PRIVATE);
        return settings.contains(ApplicationConstants.USER_LOGIN) && settings.contains(ApplicationConstants.USER_PASSWORD);
    }

    protected void resetUserCredentials() {
        SharedPreferences settings = getSharedPreferences(ApplicationConstants.MIFOS_APPLICATION_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(ApplicationConstants.USER_LOGIN)
              .remove(ApplicationConstants.USER_PASSWORD)
              .commit();
    }

    protected String getUserPassword() {
        SharedPreferences settings = getSharedPreferences(ApplicationConstants.MIFOS_APPLICATION_PREFERENCES, MODE_PRIVATE);
        return settings.getString(ApplicationConstants.USER_PASSWORD, ApplicationConstants.EMPTY_STRING);
    }

    protected void setUserPassword(String password) {
        SharedPreferences settings = getSharedPreferences(ApplicationConstants.MIFOS_APPLICATION_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(ApplicationConstants.USER_PASSWORD, password).commit();
    }

    protected String getUserLogin() {
        SharedPreferences settings = getSharedPreferences(ApplicationConstants.MIFOS_APPLICATION_PREFERENCES, MODE_PRIVATE);
        return settings.getString(ApplicationConstants.USER_LOGIN, ApplicationConstants.EMPTY_STRING);
    }

    protected void setUserLogin(String login) {
        SharedPreferences settings = getSharedPreferences(ApplicationConstants.MIFOS_APPLICATION_PREFERENCES, MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(ApplicationConstants.USER_LOGIN, login).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changeServerAddress:
                SharedPreferences settings = getSharedPreferences(ApplicationConstants.MIFOS_APPLICATION_PREFERENCES, MODE_PRIVATE);
                String currentAddress = settings.getString(ApplicationConstants.MIFOS_SERVER_ADDRESS_KEY, getString(R.string.server_name_template));
                mUIUtils.promptForTextInput(getString(R.string.dialog_server_address), currentAddress, new UIUtils.DialogCallbacks() {

                    @Override
                    public void onCommit(Object inputData) {
                        String newAddress = (String)inputData;
                        newAddress = newAddress.trim();
                        SharedPreferences settings = getSharedPreferences(ApplicationConstants.MIFOS_APPLICATION_PREFERENCES, MODE_PRIVATE);
                        if (settings.contains(ApplicationConstants.MIFOS_SERVER_ADDRESS_KEY)) {
                            String oldAddress = settings.getString(ApplicationConstants.MIFOS_SERVER_ADDRESS_KEY, "");
                            if (oldAddress.equals(newAddress)) {
                                return;
                            }
                        }
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(ApplicationConstants.MIFOS_SERVER_ADDRESS_KEY, newAddress);
                        editor.commit();
                        mUIUtils.displayLongMessage(getString(R.string.toast_address_set));
                        logOut();
                    }

                    @Override
                    public void onCancel() { }

                });
                break;
            case R.id.logOut:
                logOut();
                break;
            case R.id.synchronize:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

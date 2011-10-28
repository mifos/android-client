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

import android.content.SharedPreferences;
import android.os.Bundle;
import org.mifos.androidclient.R;
import org.mifos.androidclient.templates.MifosActivity;
import org.mifos.androidclient.util.MifosConstants;
import org.mifos.androidclient.util.ui.UIUtils;

public class ClientMainActivity extends MifosActivity {

    private UIUtils mUIUtils;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mUIUtils = new UIUtils(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        checkForServerAddressAndRun();
    }

    private void checkForServerAddressAndRun() {
        final SharedPreferences settings = getSharedPreferences(MIFOS_APPLICATION_PREFERENCES, MODE_PRIVATE);
        if (settings.contains(MifosConstants.MIFOS_SERVER_ADDRESS_KEY)) {
            runMain();
        } else {
            mUIUtils.promptForTextInput(getString(R.string.dialog_server_address), new UIUtils.OnInputCommit() {
                public void onCommit(Object inputData) {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(MifosConstants.MIFOS_SERVER_ADDRESS_KEY, (String)inputData);
                    editor.commit();
                    runMain();
                }
            });
        }
    }

    private void runMain() {

    }

}

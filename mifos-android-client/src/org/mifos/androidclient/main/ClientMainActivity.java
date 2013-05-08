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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import org.mifos.androidclient.R;
import org.mifos.androidclient.templates.MifosActivity;
import org.mifos.androidclient.util.ApplicationConstants;
import org.mifos.androidclient.util.ui.UIUtils;

public class ClientMainActivity extends MifosActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    }

    @Override
    public void onStart() {
        super.onStart();
        checkForServerAddress();
    }

    /**
     * Called upon pressing the clients list button.<br />
     * Configured in the layout file of this activity.
     *
     * @param view the button which was pressed
     */
    public void onClientListSelected(View view) {
        Intent intent = new Intent().setClass(this, CentersListActivity.class);
        startActivity(intent);
    }

    public void onMeetingSelected(View view){
        Intent intent= new Intent().setClass(this, MeetingListActivity.class);
        startActivity(intent);
    }

    /**
     * Called upon pressing the collection sheets button.<br />
     * Configured in the layout file of this activity.
     *
     * @param view the button which was pressed
     */
    public void onCollectionSheetsSelected(View view) {
        Intent intent = new Intent().setClass(this, CollectionSheetCentersActivity.class);
        startActivity(intent);
    }
    
    public void onOverdueBorrowersClick(View view) {
        Intent intent = new Intent().setClass(this, OverdueBorrowersListActivity.class);
        startActivity(intent);
    }

    /**
     * Checks whether the Mifos server address has been specified or not and
     * displays a dialog prompting for it in the latter case.
     */
    private void checkForServerAddress() {
        final SharedPreferences settings = getSharedPreferences(ApplicationConstants.MIFOS_APPLICATION_PREFERENCES, MODE_PRIVATE);
        if (!settings.contains(ApplicationConstants.MIFOS_SERVER_ADDRESS_KEY)) {
            mUIUtils.promptForTextInput(getString(R.string.dialog_server_address), getString(R.string.server_name_template), new UIUtils.DialogCallbacks() {
                @Override
                public void onCommit(Object inputData) {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(ApplicationConstants.MIFOS_SERVER_ADDRESS_KEY, (String) inputData);
                    editor.commit();
                    mUIUtils.displayLongMessage(getString(R.string.toast_first_address_set));
                }

                @Override
                public void onCancel() {
                    mUIUtils.displayLongMessage(getString(R.string.toast_first_address_canceled));
                }
            });
        }
    }

}

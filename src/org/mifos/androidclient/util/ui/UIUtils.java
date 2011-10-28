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

package org.mifos.androidclient.util.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import org.mifos.androidclient.R;

/**
 * Provides simple UI utilities, like dialogs which can be used to
 * prompt the user for input.
 */
public class UIUtils {

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public UIUtils(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * Displays a simple dialog which prompts user for text input. The user can either
     * commit the input or cancel the dialog.
     *
     * @param labelText the label to be used for the dialog
     * @userCommitCallback the callback to be invoked when user commits the input
     * @return input provided by the user, or null value if the dialog was canceled
     */
    public void promptForTextInput(String labelText, final OnInputCommit userCommitCallback) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final View dialogView = mLayoutInflater.inflate(R.layout.text_input_dialog, null);
        final TextView dialogLabel = (TextView)dialogView.findViewById(R.id.dialogLabel);
        dialogLabel.setText(labelText);
        final EditText textInput = (EditText)dialogView.findViewById(R.id.dialogInput);

        builder
            .setView(dialogView)
            .setPositiveButton(mContext.getText(R.string.dialog_ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    userCommitCallback.onCommit(textInput.getText().toString().trim());
                }
            })
            .setNegativeButton(mContext.getText(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            })
            .show();
    }

    /**
     * An interface representing the callback which should be invoked
     * when the user commits dialog input.
     */
    public interface OnInputCommit {

        /**
         * Called upon user committing the input.
         *
         * @param inputData the data entered by the user
         */
        void onCommit(Object inputData);

    }

}

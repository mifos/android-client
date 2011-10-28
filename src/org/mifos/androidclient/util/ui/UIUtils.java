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
import android.widget.Toast;
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
     * Displays a short duration toast message.
     *
     * @param messageText the text to be displayed
     */
    public void displayShortMessage(String messageText) {
        displayMessage(messageText, Toast.LENGTH_SHORT);
    }

    /**
     * Displays a long duration toast message.
     *
     * @param messageText the text to be displayed
     */
    public void displayLongMessage(String messageText) {
        displayMessage(messageText, Toast.LENGTH_LONG);
    }

    /**
     * Displays a simple dialog which prompts user for text input. The user can either
     * commit the input or cancel the dialog.
     *
     * @param labelText the label to be used for the dialog
     * @param callbacks the callbacks to be invoked when user interacts with the input
     * @return input provided by the user, or null value if the dialog was canceled
     */
    public void promptForTextInput(String labelText, final DialogCallbacks callbacks) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        final View dialogView = mLayoutInflater.inflate(R.layout.text_input_dialog, null);
        final TextView dialogLabel = (TextView)dialogView.findViewById(R.id.dialogLabel);
        dialogLabel.setText(labelText);
        final EditText textInput = (EditText)dialogView.findViewById(R.id.dialogInput);

        builder
            .setView(dialogView)
            .setPositiveButton(mContext.getText(R.string.dialog_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    callbacks.onCommit(textInput.getText().toString().trim());
                }
            })
            .setNegativeButton(mContext.getText(R.string.dialog_cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    callbacks.onCancel();
                    dialogInterface.cancel();
                }
            })
            .setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    callbacks.onCancel();
                }
            })
            .show();
    }

    private void displayMessage(String messageText, int duration) {
        Toast.makeText(mContext, messageText, duration).show();
    }

    /**
     * An interface representing the callbacks which should be invoked
     * when the user interacts with an input dialog.
     */
    public interface DialogCallbacks {

        /**
         * Called upon user committing the input.
         *
         * @param inputData the data entered by the user
         */
        void onCommit(Object inputData);

        /**
         * Called upon cancelling the dialog.
         */
        void onCancel();
    }

}

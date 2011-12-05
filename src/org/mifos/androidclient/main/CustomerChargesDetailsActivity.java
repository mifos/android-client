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

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.AccountFee;
import org.mifos.androidclient.entities.account.AccountFeeSchedule;
import org.mifos.androidclient.entities.customer.CustomerChargesDetails;
import org.mifos.androidclient.entities.customer.CustomerRecentActivity;
import org.mifos.androidclient.entities.simple.AbstractCustomer;
import org.mifos.androidclient.net.services.CustomerService;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.mifos.androidclient.util.ValueUtils;
import org.mifos.androidclient.util.ui.DateUtils;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.List;

public class CustomerChargesDetailsActivity extends DownloaderActivity {

    private final static int TABLE_ROW_SEPARATOR_WIDTH = 1;
    private final static int TABLE_CELL_PADDING = 5;

    private CustomerChargesTask mCustomerChargesTask;
    private AbstractCustomer mCustomer;
    private CustomerService mCustomerService;
    private CustomerChargesDetails mDetails;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.customer_charges_details);

        mCustomer = (AbstractCustomer)getIntent().getSerializableExtra(AbstractCustomer.BUNDLE_KEY);
        if (bundle != null) {
            mDetails = (CustomerChargesDetails)bundle.getSerializable(CustomerChargesDetails.BUNDLE_KEY);
        }
        mCustomerService = new CustomerService(this);
    }

    @Override
    protected void onSessionActive() {
        super.onSessionActive();
        if (mDetails == null) {
            runCustomerChargesTask();
        } else {
            updateContent(mDetails);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCustomerChargesTask != null) {
            mCustomerChargesTask.terminate();
            mCustomerChargesTask = null;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CustomerChargesDetails.BUNDLE_KEY, mDetails);
    }

    private void updateContent(CustomerChargesDetails details) {
        mDetails = details;
        TextView textView;
        TableLayout table;
        TableRow row;
        View disabledView;

        textView = (TextView)findViewById(R.id.customerChargesDetails_amountDue);
        textView.setText(details.getNextDueAmount().toString());
        textView = (TextView)findViewById(R.id.customerChargesDetails_amountOverdue);
        textView.setText(details.getTotalAmountInArrears().toString());
        textView = (TextView)findViewById(R.id.customerChargesDetails_total);
        textView.setText(details.getTotalAmountDue().toString());

        if (ValueUtils.hasValue(details.getUpcomingInstallment().getMiscFee()) ||
            ValueUtils.hasValue(details.getUpcomingInstallment().getMiscPenalty()) ||
            ValueUtils.hasElements(details.getUpcomingInstallment().getFeesActionDetails())) {

            disabledView = findViewById(R.id.customerChargesDetails_upcomingCharges_label);
            disabledView.setVisibility(View.VISIBLE);
            disabledView = findViewById(R.id.customerChargesDetails_upcomingCharges_scrollView);
            disabledView.setVisibility(View.VISIBLE);

            table = (TableLayout)findViewById(R.id.customerChargesDetails_upcomingCharges_table);

            if (ValueUtils.hasValue(details.getUpcomingInstallment().getMiscFee())) {
                row = createTableRow();
                textView = createTableCell(getString(R.string.customerChargesDetails_miscFee_label), 1);
                row.addView(textView);
                textView = createTableCell(details.getUpcomingInstallment().getMiscFee().toString(), 2);
                row.addView(textView);
                table.addView(row);
                table.addView(createRowSeparator());
            }

            if (ValueUtils.hasValue(details.getUpcomingInstallment().getMiscPenalty())) {
                row = createTableRow();
                textView = createTableCell(getString(R.string.customerChargesDetails_miscPenalty_label), 1);
                row.addView(textView);
                textView = createTableCell(details.getUpcomingInstallment().getMiscPenalty().toString(), 2);
                row.addView(textView);
                table.addView(row);
                table.addView(createRowSeparator());
            }

            if (ValueUtils.hasElements(details.getUpcomingInstallment().getFeesActionDetails())) {
                for (AccountFeeSchedule fee : details.getUpcomingInstallment().getFeesActionDetails()) {
                    row = createTableRow();
                    textView = createTableCell(fee.getFeeName(), 1);
                    row.addView(textView);
                    textView = createTableCell(fee.getFeeAmount().toString(), 2);
                    row.addView(textView);
                    table.addView(row);
                    table.addView(createRowSeparator());
                }
            }
        }

        if (ValueUtils.hasElements(details.getRecentActivities())) {
            disabledView = findViewById(R.id.customerChargesDetails_recentAccountActivity_label);
            disabledView.setVisibility(View.VISIBLE);
            disabledView = findViewById(R.id.customerChargesDetails_recentAccountActivity_scrollView);
            disabledView.setVisibility(View.VISIBLE);

            table = (TableLayout)findViewById(R.id.customerChargesDetails_recentAccountActivity_table);

            for (CustomerRecentActivity activity : details.getRecentActivities()) {
                row = createTableRow();
                textView = createTableCell(DateUtils.format(activity.getActivityDate()), 1);
                row.addView(textView);
                textView = createTableCell(activity.getDescription(), 2);
                row.addView(textView);
                textView = createTableCell(activity.getAmount(), 3);
                row.addView(textView);
                textView = createTableCell(activity.getPostedBy(), 4);
                row.addView(textView);
                table.addView(row);
                table.addView(createRowSeparator());
            }
        }

        if (ValueUtils.hasElements(details.getAccountFees())) {
            boolean hasRecurring = false;
            List<AccountFee> recurringFees = new ArrayList<AccountFee>();
            for (AccountFee fee : details.getAccountFees()) {
                if (fee.getMeetingRecurrence() != null) {
                    hasRecurring = true;
                    recurringFees.add(fee);
                }
            }
            if (hasRecurring) {
                disabledView = findViewById(R.id.customerChargesDetails_recurringAccountFees_label);
                disabledView.setVisibility(View.VISIBLE);
                disabledView = findViewById(R.id.customerChargesDetails_recurringAccountFees_scrollView);
                disabledView.setVisibility(View.VISIBLE);

                table = (TableLayout)findViewById(R.id.customerChargesDetails_recurringAccountFees_table);

                for (AccountFee fee : recurringFees) {
                    row = createTableRow();
                    textView = createTableCell(fee.getFeeName(), 1);
                    row.addView(textView);
                    textView = createTableCell(fee.getAccountFeeAmount().toString(), 2);
                    row.addView(textView);
                    textView = createTableCell(fee.getMeetingRecurrence(), 2);
                    row.addView(textView);
                    table.addView(row);
                    table.addView(createRowSeparator());
                }
            }
        }

    }

    private TextView createTableCell(String text, int column) {
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setLayoutParams(new TableRow.LayoutParams(column));
        textView.setPadding(TABLE_CELL_PADDING, 0, TABLE_CELL_PADDING, 0);
        return textView;
    }

    private TableRow createTableRow() {
        TableRow row = new TableRow(this);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
        return row;
    }

    private View createRowSeparator() {
        View separator = new View(this);
        separator.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TABLE_ROW_SEPARATOR_WIDTH));
        separator.setBackgroundResource(R.color.tableSeparator);
        return separator;
    }

    private void runCustomerChargesTask() {
        if (mCustomer != null) {
            if (mCustomerChargesTask == null || mCustomerChargesTask.getStatus() != AsyncTask.Status.RUNNING) {
                mCustomerChargesTask = new CustomerChargesTask(
                        this,
                        getString(R.string.dialog_getting_customer_charges),
                        getString(R.string.dialog_loading_message)
                );
            }
            mCustomerChargesTask.execute(mCustomer);
        }
    }

    private class CustomerChargesTask extends ServiceConnectivityTask<AbstractCustomer, Void, CustomerChargesDetails> {

        public CustomerChargesTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected CustomerChargesDetails doInBackgroundBody(AbstractCustomer... params) throws RestClientException, IllegalArgumentException {
            CustomerChargesDetails details = null;
            if (mCustomerService != null) {
                details = mCustomerService.getChargesForEntity(params[0]);
            }
            return details;
        }

        @Override
        protected void onPostExecuteBody(CustomerChargesDetails details) {
            if (details != null) {
                updateContent(details);
            }
        }

    }

}

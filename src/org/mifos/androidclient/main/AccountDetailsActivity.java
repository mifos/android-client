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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.*;
import org.mifos.androidclient.entities.customer.AccountBasicInformation;
import org.mifos.androidclient.entities.simple.Fee;
import org.mifos.androidclient.net.services.AccountService;
import org.mifos.androidclient.templates.AccountDetailsViewBuilder;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.mifos.androidclient.templates.ViewBuilderFactory;
import org.mifos.androidclient.util.ApplicationConstants;
import org.mifos.androidclient.util.ValueUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;

import java.io.Serializable;
import java.util.*;

public class AccountDetailsActivity extends DownloaderActivity {

    public static final String SELECTED_TAB_BUNDLE_KEY = AccountDetailsActivity.class.getSimpleName() + "-selectedTab";

    private AccountBasicInformation mAccount;
    private AbstractAccountDetails mDetails;
    private AccountService mAccountService;
    private AccountDetailsTask mAccountDetailsTask;
    private Map<String, String> mApplicableFees;
    private List<TransactionHistoryEntry> mTransactionHistoryEntries;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.account_details);

        TabHost tabs = (TabHost)findViewById(R.id.accountDetails_tabHost);
        tabs.setup();
        TabHost.TabSpec overviewSpec = tabs.newTabSpec(getString(R.string.accountDetails_tab_overview));
        overviewSpec.setIndicator(getString(R.string.accountDetails_tab_overview));
        overviewSpec.setContent(R.id.account_overview);
        TabHost.TabSpec additionalInfoSpec = tabs.newTabSpec(getString(R.string.accountDetails_tab_additionalInfo));
        additionalInfoSpec.setIndicator(getString(R.string.accountDetails_tab_additionalInfo));
        additionalInfoSpec.setContent(R.id.account_details);
        TabHost.TabSpec transactionSpec = tabs.newTabSpec(getString(R.string.accountDetails_tab_transaction));
        transactionSpec.setIndicator(getString(R.string.accountDetails_tab_transaction));
        transactionSpec.setContent(R.id.account_transaction);
        tabs.addTab(overviewSpec);
        tabs.addTab(transactionSpec);
        tabs.addTab(additionalInfoSpec);

        if (bundle != null) {
            if (bundle.containsKey(AbstractAccountDetails.BUNDLE_KEY)) {
                mDetails = (AbstractAccountDetails)bundle.getSerializable(AbstractAccountDetails.BUNDLE_KEY);
            }
            if (bundle.containsKey(Fee.BUNDLE_KEY)) {
                mApplicableFees = (Map<String, String>)bundle.getSerializable(Fee.BUNDLE_KEY);
            }
            if (bundle.containsKey(SELECTED_TAB_BUNDLE_KEY)) {
                tabs.setCurrentTab(bundle.getInt(SELECTED_TAB_BUNDLE_KEY));
            }

        }

        mAccount = (AccountBasicInformation)getIntent().getSerializableExtra(AccountBasicInformation.BUNDLE_KEY);
        mAccountService = new AccountService(this);
    }

    @Override
    protected void onSessionActive() {
        super.onSessionActive();
            if (mDetails == null || mApplicableFees == null) {
            runAccountDetailsTask();
        } else {
            updateContent(mDetails);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAccountDetailsTask != null) {
            mAccountDetailsTask.terminate();
            mAccountDetailsTask = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                runAccountDetailsTask();
                break;
            case Activity.RESULT_CANCELED:
                break;
            default:
                break;
        }
    }

    /**
     * A handler of the button for account's transactions history browsing.
     *
     * @param view the view on in which the pressed button resides
     */
    public void onTransactionsHistorySelected(View view) {
        Intent intent = new Intent().setClass(this, AccountTransactionHistoryActivity.class);
        intent.putExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY, mAccount.getGlobalAccountNum());
        startActivity(intent);
    }

    /**
     * A handler of the button for savings account's deposit due details browsing.
     *
     * @param view the view on which the button resides
     */
    public void onDepositDueDetailsSelected(View view) {
        Intent intent = new Intent().setClass(this, DepositDueDetailsActivity.class);
        intent.putExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY, mAccount.getGlobalAccountNum());
        startActivity(intent);
    }

    /**
     * A handler of the button for loan account's installment details browsing.
     *
     * @param view the view on which the pressed button resides
     */
    public void onInstallmentDetailsSelected(View view) {
        Intent intent = new Intent().setClass(this, LoanInstallmentDetailsActivity.class);
        intent.putExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY, mAccount.getGlobalAccountNum());
        startActivity(intent);
    }

    /**
     * A handler of the button for loan account's repayment schedule browsing.
     *
     * @param view the view on which the pressed button resides
     */
    public void onRepaymentScheduleSelected(View view) {
        Intent intent = new Intent().setClass(this, AccountRepaymentScheduleActivity.class);
        intent.putExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY, mAccount.getGlobalAccountNum());
        startActivity(intent);
    }

    /**
     * A handler of the button for disbursing a loan account.
     *
     * @param view the view on which the pressed button resides
     */
    public void onDisburseLoanSelected(View view) {
        Intent intent = new Intent().setClass(this, DisburseLoanActivity.class);
        intent.putExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY, mDetails.getGlobalAccountNum());
        String originalPrincipal = ((LoanAccountDetails)mDetails).getLoanSummary().getOriginalPrincipal();
        intent.putExtra(LoanSummary.ORIGINAL_PRINCIPAL_BUNDLE_KEY, originalPrincipal);
        startActivityForResult(intent, DisburseLoanActivity.REQUEST_CODE);
    }

    public void onLoanApplyChargeSelected(View view) {
        Intent intent = new Intent().setClass(this, ApplyLoanAccountChargeActivity.class);
        intent.putExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY, mAccount.getGlobalAccountNum());
        intent.putExtra(Fee.BUNDLE_KEY, (Serializable)mApplicableFees);
        startActivityForResult(intent, ApplyLoanAccountChargeActivity.REQUEST_CODE);
    }

    public void onApplyLoanRepaySelected(View view) {
        Intent intent = new Intent().setClass(this, ApplyLoanAccountRepayLoanActivity.class);
        intent.putExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY, mAccount.getGlobalAccountNum());
        startActivityForResult(intent, ApplyLoanAccountRepayLoanActivity.REQUEST_CODE);
    }

    /**
     * A handler for the button for applying a savings account adjustment.
     *
     * @param view the button which has been pressed.
     */
    public void onApplySavingsAdjustmentSelected(View view) {
        Intent intent = new Intent().setClass(this, ApplySavingsAdjustmentActivity.class);
        intent.putExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY, mAccount.getGlobalAccountNum());
        startActivityForResult(intent, ApplySavingsAdjustmentActivity.REQUEST_CODE);
    }

    /**
     * A handler for the button for applying a loan account adjustment.
     *
     * @param view the button which has been pressed.
     */
    public void onApplyLoanAdjustmentSelected(View view) {
        Intent intent = new Intent().setClass(this, ApplyLoanAdjustmentActivity.class);
        intent.putExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY, mAccount.getGlobalAccountNum());
        if (ValueUtils.hasElements(mTransactionHistoryEntries)) {
            TransactionHistoryEntry entry = mTransactionHistoryEntries.get(0);
            String lastTransactionType = entry.getType();
            Double lastTransactionAmount;
            if (entry.getCredit().equals(ApplicationConstants.EMPTY_CELL)) {
                lastTransactionAmount = Double.parseDouble(entry.getDebit());
            } else {
                lastTransactionAmount = Double.parseDouble(entry.getCredit());
            }
            intent.putExtra(TransactionHistoryEntry.PREVIOUS_TRXN_TYPE_BUNDLE_KEY, lastTransactionType);
            intent.putExtra(TransactionHistoryEntry.PREVIOUS_TRXN_AMOUNT_BUNDLE_KEY, lastTransactionAmount);
        }
        startActivityForResult(intent, ApplyLoanAdjustmentActivity.REQUEST_CODE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(AbstractAccountDetails.BUNDLE_KEY, mDetails);
        outState.putSerializable(Fee.BUNDLE_KEY, (Serializable)mApplicableFees);
        TabHost tabs = (TabHost)findViewById(R.id.accountDetails_tabHost);
        if (tabs != null) {
            outState.putSerializable(SELECTED_TAB_BUNDLE_KEY, tabs.getCurrentTab());
        }
    }

    private void updateContent(AbstractAccountDetails details) {
        if (details != null) {
            mDetails = details;
            ViewBuilderFactory factory = new ViewBuilderFactory(this);
            AccountDetailsViewBuilder builder = factory.createAccountDetailsViewBuilder(details);

            LinearLayout tabContent = (LinearLayout)findViewById(R.id.account_overview);
            if (tabContent.getChildCount() > 0) {
                tabContent.removeAllViews();
            }
            tabContent.addView(builder.buildOverviewView());

            tabContent = (LinearLayout)findViewById(R.id.account_transaction);
            if (tabContent.getChildCount() > 0) {
                tabContent.removeAllViews();
            }
            tabContent.addView(builder.buildTransactionView());

            tabContent = (LinearLayout)findViewById(R.id.account_details);
            if (tabContent.getChildCount() > 0) {
                tabContent.removeAllViews();
            }
            tabContent.addView(builder.buildDetailsView());

            if (details.getClass() == SavingsAccountDetails.class) {
                if (((SavingsAccountDetails)details).getDepositTypeName().equals(SavingsAccountDetails.MANDATORY_DEPOSIT)) {
                    Button depositDueButton = (Button)findViewById(R.id.view_depositDueDetails_button);
                    depositDueButton.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void runAccountDetailsTask() {
        if (mAccount == null || !StringUtils.hasLength(mAccount.getGlobalAccountNum())) {
            mUIUtils.displayLongMessage(getString(R.string.toast_customer_id_not_available));
            return;
        }
        if (mAccountDetailsTask == null || mAccountDetailsTask.getStatus() != AsyncTask.Status.RUNNING) {
            mAccountDetailsTask = new AccountDetailsTask(
                    this,
                    getString(R.string.dialog_getting_account_data),
                    getString(R.string.dialog_loading_message)
            );
            mAccountDetailsTask.execute(mAccount);
        }
    }

    private class AccountDetailsTask extends ServiceConnectivityTask<AccountBasicInformation, Void, AbstractAccountDetails> {

        public AccountDetailsTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected AbstractAccountDetails doInBackgroundBody(AccountBasicInformation... params) throws RestClientException, IllegalArgumentException {
            AbstractAccountDetails details = null;
            TransactionHistoryEntry[] transactionHistoryEntries;
            if (mAccountService != null) {
                mApplicableFees = mAccountService.getApplicableFees(params[0].getGlobalAccountNum());
                transactionHistoryEntries = mAccountService.getAccountTransactionHistory(params[0].getGlobalAccountNum());
                Arrays.sort(transactionHistoryEntries, new Comparator<TransactionHistoryEntry>() {
                    @Override
                    public int compare(TransactionHistoryEntry a, TransactionHistoryEntry b) {
                        return a.getAccountTrxnId().compareTo(b.getAccountTrxnId());
                    }
                });
                mTransactionHistoryEntries = Arrays.asList(transactionHistoryEntries);
                details = mAccountService.getAccountDetailsForEntity(params[0]);
            }
            return details;
        }

        @Override
        protected void onPostExecuteBody(AbstractAccountDetails details) {
            if (details != null) {
                updateContent(details);
            }
        }
    }

}

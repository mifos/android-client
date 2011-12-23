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

package org.mifos.androidclient.net.services;

import android.content.Context;
import org.mifos.androidclient.entities.account.*;
import org.mifos.androidclient.entities.account.loan.LoanInstallmentDetails;
import org.mifos.androidclient.entities.account.savings.SavingsAccountDepositDue;
import org.mifos.androidclient.entities.customer.AccountBasicInformation;
import org.mifos.androidclient.entities.customer.LoanAccountBasicInformation;
import org.mifos.androidclient.entities.customer.SavingsAccountBasicInformation;

import java.util.Map;

public class AccountService extends RestNetworkService {

    private static final String LOAN_ACCOUNT_DETAILS_PATH_PREFIX = "/account/loan/num-";
    private static final String SAVINGS_ACCOUNT_DETAILS_PATH_PREFIX = "/account/savings/num-";
    private static final String TRANSACTION_HISTORY_PATH = "/account/num-%s/trxnhistory.json";
    private static final String LOAN_INSTALLMENT_DETAILS_PATH = "/account/loan/num-%s/installment.json";
    private static final String SAVINGS_DEPOSIT_DUE_DETAILS_PATH = "/account/savings/num-%s/due.json";
    private static final String REPAYMENT_SCHEDULE_PATH = "/account/loan/num-%s/schedule.json";

    private static final String LOAN_ACCOUNT_APPLY_CHARGE_PATH = "/account/loan/num-%s/charge.json";
    private static final String LOAN_ACCOUNT_FULL_REPAY_LOAN_PATH = "/account/loan/num-%s/fullrepay.json";
    private static final String LOAN_ACCOUNT_REPAY_LOAN_PATH = "/account/loan/num-%s/repay.json";
    private static final String LOAN_ACCOUNT_INTEREST_WAIVABLE_PATH = "/account/loan/num-%s/interestWaivable.json";
    private static final String LOAN_ACCOUNT_APPLICABLE_FEES_PATH = "/account/loan/num-%s/fees.json";

    private static final String DISBURSE_LOAN_PATH = "/account/loan/num-%s/disburse.json";

    private static final String APPLY_SAVINGS_ADJUSTMENT_PATH = "/account/savings/num-%s/adjustment.json";
    private static final String APPLY_LOAN_ADJUSTMENT_PATH = "/account/loan/num-%s/adjustment.json";

    private static final String MAKE_SAVINGS_DEPOSIT_PATH = "/account/savings/num-%s/deposit.json";
    private static final String MAKE_SAVINGS_WITHDRAWAL_PATH = "/account/savings/num-%s/withdraw.json";


    public AccountService(Context context) {
        super(context);
    }

    public LoanAccountDetails getLoanAccountDetails(String loanAccountNumber) {
        String url = getServerUrl() + LOAN_ACCOUNT_DETAILS_PATH_PREFIX + loanAccountNumber + PATH_SUFFIX;
        return mRestConnector.getForObject(url, LoanAccountDetails.class);
    }

    public SavingsAccountDetails getSavingsAccountDetails(String savingsAccountNumber) {
        String url = getServerUrl() + SAVINGS_ACCOUNT_DETAILS_PATH_PREFIX + savingsAccountNumber + PATH_SUFFIX;
        return mRestConnector.getForObject(url, SavingsAccountDetails.class);
    }

    public AbstractAccountDetails getAccountDetailsForEntity(AccountBasicInformation account) {
        AbstractAccountDetails details = null;
        if (account.getClass() == LoanAccountBasicInformation.class) {
            details = getLoanAccountDetails(account.getGlobalAccountNum());
        } else if (account.getClass() == SavingsAccountBasicInformation.class) {
            details = getSavingsAccountDetails(account.getGlobalAccountNum());
        }
        return details;
    }

    public TransactionHistoryEntry[] getAccountTransactionHistory(String globalAccountNumber) {
        String url = getServerUrl() + String.format(TRANSACTION_HISTORY_PATH, globalAccountNumber);
        return mRestConnector.getForObject(url, TransactionHistoryEntry[].class);
    }

    public RepaymentScheduleItem[] getAccountRepaymentSchedule(String globalAccountNumber) {
        String url = getServerUrl() + String.format(REPAYMENT_SCHEDULE_PATH, globalAccountNumber);
        return mRestConnector.getForObject(url,RepaymentScheduleItem[].class);
    }

    public LoanInstallmentDetails getLoanInstallmentDetails(String globalAccountNumber) {
        String url = getServerUrl() + String.format(LOAN_INSTALLMENT_DETAILS_PATH, globalAccountNumber);
        return mRestConnector.getForObject(url, LoanInstallmentDetails.class);
    }

    public SavingsAccountDepositDue getSavingsDepositDueDetails(String globalAccountNumber) {
        String url = getServerUrl() + String.format(SAVINGS_DEPOSIT_DUE_DETAILS_PATH, globalAccountNumber);
        return mRestConnector.getForObject(url, SavingsAccountDepositDue.class);
    }

    public Map<String, String> applyLoanCharge(String accountNumber, Map<String, String> params) {
        String url = getServerUrl() + String.format(LOAN_ACCOUNT_APPLY_CHARGE_PATH, accountNumber);
        url += prepareQueryString(params);
        return mRestConnector.postForObject(url, null, Map.class);
    }

    public Map<String, Map<String, String>> getApplicableFees(String globalAccountNumber) {
        String url = getServerUrl() + String.format(LOAN_ACCOUNT_APPLICABLE_FEES_PATH, globalAccountNumber);
        return mRestConnector.getForObject(url, Map.class);
    }

    public Map<String, String> disburseLoan(String accountNumber, Map<String, String> params) {
        String url = getServerUrl() + String.format(DISBURSE_LOAN_PATH, accountNumber);
        url += prepareQueryString(params);
        return mRestConnector.postForObject(url, null, Map.class);
    }

    public Map<String, String> applySavingsAccountAdjustment(String accountNumber, Map<String, String> params) {
        String url = getServerUrl() + String.format(APPLY_SAVINGS_ADJUSTMENT_PATH, accountNumber);
        url += prepareQueryString(params);
        return mRestConnector.postForObject(url, null, Map.class);
    }

    public Map<String, String> applyLoanAccountAdjustment(String accountNumber, Map<String, String> params) {
        String url = getServerUrl() + String.format(APPLY_LOAN_ADJUSTMENT_PATH, accountNumber);
        url += prepareQueryString(params);
        return mRestConnector.postForObject(url, null, Map.class);
    }

    public Map<String, String> isLoanInterestWaivable(String globalAccountNumber) {
        String url = getServerUrl() + String.format(LOAN_ACCOUNT_INTEREST_WAIVABLE_PATH, globalAccountNumber);
        return mRestConnector.getForObject(url, Map.class);

    }

    public Map<String, String> fullRepayLoan(String accountNumber, Map<String, String> params) {
        String url = getServerUrl() + String.format(LOAN_ACCOUNT_FULL_REPAY_LOAN_PATH, accountNumber);
        url +=prepareQueryString(params);
        return mRestConnector.postForObject(url, null, Map.class);
     }

    public Map<String, String> repayLoan(String accountNumber, Map<String, String> params) {
        String url = getServerUrl() + String.format(LOAN_ACCOUNT_REPAY_LOAN_PATH, accountNumber);
        url += prepareQueryString(params);
        return mRestConnector.postForObject(url, null, Map.class);
    }

    public Map<String, String> makeSavingsDeposit(String accountNumber, Map<String, String> params) {
        String url = getServerUrl() + String.format(MAKE_SAVINGS_DEPOSIT_PATH, accountNumber);
        url += prepareQueryString(params);
        return mRestConnector.postForObject(url, null, Map.class);
    }

    public Map<String, String> makeSavingsWithdrawal(String accountNumber, Map<String, String> params) {
        String url = getServerUrl() + String.format(MAKE_SAVINGS_WITHDRAWAL_PATH, accountNumber);
        url += prepareQueryString(params);
        return mRestConnector.postForObject(url, null, Map.class);
    }

}

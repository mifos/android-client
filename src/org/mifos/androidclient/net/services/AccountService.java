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
import org.mifos.androidclient.entities.account.AbstractAccountDetails;
import org.mifos.androidclient.entities.account.LoanAccountDetails;
import org.mifos.androidclient.entities.account.SavingsAccountDetails;
import org.mifos.androidclient.entities.account.TransactionHistoryEntry;
import org.mifos.androidclient.entities.account.loan.LoanInstallmentDetails;
import org.mifos.androidclient.entities.customer.AccountBasicInformation;
import org.mifos.androidclient.entities.customer.LoanAccountBasicInformation;
import org.mifos.androidclient.entities.customer.SavingsAccountBasicInformation;

public class AccountService extends RestNetworkService {

    private static final String LOAN_ACCOUNT_DETAILS_PATH_PREFIX = "/account/loan/num-";
    private static final String SAVINGS_ACCOUNT_DETAILS_PATH_PREFIX = "/account/savings/num-";
    private static final String TRANSACTION_HISTORY_PATH_PREFIX = "/account/trxnhistory/num-";
    private static final String LOAN_INSTALLMENT_DETAILS_PATH_PREFIX = "/account/loan/installment/num-";

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
        String url = getServerUrl() + TRANSACTION_HISTORY_PATH_PREFIX + globalAccountNumber + PATH_SUFFIX;
        return mRestConnector.getForObject(url, TransactionHistoryEntry[].class);
    }

    public LoanInstallmentDetails getLoanInstallmentDetails(String globalAccountNumber) {
        String url = getServerUrl() + LOAN_INSTALLMENT_DETAILS_PATH_PREFIX + globalAccountNumber + PATH_SUFFIX;
        return mRestConnector.getForObject(url, LoanInstallmentDetails.class);
    }

}

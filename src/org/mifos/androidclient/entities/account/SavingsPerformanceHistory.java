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

package org.mifos.androidclient.entities.account;

public class SavingsPerformanceHistory {

    private String dateAccountOpened;
    private Double totalDeposits;
    private Double totalInterestEarned;
    private Double totalWithdrawals;

    public String getDateAccountOpened() {
        return dateAccountOpened;
    }

    public void setDateAccountOpened(String dateAccountOpened) {
        this.dateAccountOpened = dateAccountOpened;
    }

    public Double getTotalDeposits() {
        return totalDeposits;
    }

    public void setTotalDeposits(Double totalDeposits) {
        this.totalDeposits = totalDeposits;
    }

    public Double getTotalInterestEarned() {
        return totalInterestEarned;
    }

    public void setTotalInterestEarned(Double totalInterestEarned) {
        this.totalInterestEarned = totalInterestEarned;
    }

    public Double getTotalWithdrawals() {
        return totalWithdrawals;
    }

    public void setTotalWithdrawals(Double totalWithdrawals) {
        this.totalWithdrawals = totalWithdrawals;
    }

}

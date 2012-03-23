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

package org.mifos.androidclient.entities.customer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.mifos.androidclient.entities.account.AccountFee;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerChargesDetails implements Serializable {

    public static final String BUNDLE_KEY = CustomerChargesDetails.class.getSimpleName();

    private Double nextDueAmount;
    private Double totalAmountInArrears;
    private Double totalAmountDue;
    private Date upcomingChargesDate;
    private CustomerSchedule upcomingInstallment;
    private List<CustomerRecentActivity> recentActivities;
    private List<AccountFee> accountFees;

    public Double getNextDueAmount() {
        return nextDueAmount;
    }

    public void setNextDueAmount(Double nextDueAmount) {
        this.nextDueAmount = nextDueAmount;
    }

    public Double getTotalAmountInArrears() {
        return totalAmountInArrears;
    }

    public void setTotalAmountInArrears(Double totalAmountInArrears) {
        this.totalAmountInArrears = totalAmountInArrears;
    }

    public Double getTotalAmountDue() {
        return totalAmountDue;
    }

    public void setTotalAmountDue(Double totalAmountDue) {
        this.totalAmountDue = totalAmountDue;
    }

    public Date getUpcomingChargesDate() {
        return upcomingChargesDate;
    }

    public void setUpcomingChargesDate(Date upcomingChargesDate) {
        this.upcomingChargesDate = upcomingChargesDate;
    }

    public CustomerSchedule getUpcomingInstallment() {
        return upcomingInstallment;
    }

    public void setUpcomingInstallment(CustomerSchedule upcomingInstallment) {
        this.upcomingInstallment = upcomingInstallment;
    }

    public List<CustomerRecentActivity> getRecentActivities() {
        return recentActivities;
    }

    public void setRecentActivities(List<CustomerRecentActivity> recentActivities) {
        this.recentActivities = recentActivities;
    }

    public List<AccountFee> getAccountFees() {
        return accountFees;
    }

    public void setAccountFees(List<AccountFee> accountFees) {
        this.accountFees = accountFees;
    }

}

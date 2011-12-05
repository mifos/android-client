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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.mifos.androidclient.entities.customer.CustomerNote;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SavingsAccountDetails extends AbstractAccountDetails implements Serializable {

    public static final String MANDATORY_DEPOSIT = "MANDATORY";
    public static final String VOLUNTARY_DEPOSIT = "VOLUNTARY";

    private SavingsProduct productDetails;
    private String accountStateName;
    private Double accountBalance;
    private String dueDate;
    private Double totalAmountDue;
    private SavingsPerformanceHistory performanceHistory;
    private List<SavingsActivity> recentActivity;
    private List<CustomerNote> recentNoteDtos;
    private String recommendedOrMandatoryAmount;
    private String depositTypeName;

    public SavingsProduct getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(SavingsProduct productDetails) {
        this.productDetails = productDetails;
    }

    public String getDepositTypeName() {
        return depositTypeName;
    }

    public void setDepositTypeName(String depositTypeName) {
        this.depositTypeName = depositTypeName;
    }

    public String getAccountStateName() {
        return accountStateName;
    }

    public void setAccountStateName(String accountStateName) {
        this.accountStateName = accountStateName;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Double getTotalAmountDue() {
        return totalAmountDue;
    }

    public void setTotalAmountDue(Double totalAmountDue) {
        this.totalAmountDue = totalAmountDue;
    }

    public SavingsPerformanceHistory getPerformanceHistory() {
        return performanceHistory;
    }

    public void setPerformanceHistory(SavingsPerformanceHistory performanceHistory) {
        this.performanceHistory = performanceHistory;
    }

    public List<SavingsActivity> getRecentActivity() {
        return recentActivity;
    }

    public void setRecentActivity(List<SavingsActivity> recentActivity) {
        this.recentActivity = recentActivity;
    }

    public List<CustomerNote> getRecentNoteDtos() {
        return recentNoteDtos;
    }

    public void setRecentNoteDtos(List<CustomerNote> recentNoteDtos) {
        this.recentNoteDtos = recentNoteDtos;
    }

    public String getRecommendedOrMandatoryAmount() {
        return recommendedOrMandatoryAmount;
    }

    public void setRecommendedOrMandatoryAmount(String recommendedOrMandatoryAmount) {
        this.recommendedOrMandatoryAmount = recommendedOrMandatoryAmount;
    }

}

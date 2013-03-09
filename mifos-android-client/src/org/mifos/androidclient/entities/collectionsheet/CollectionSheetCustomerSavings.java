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

package org.mifos.androidclient.entities.collectionsheet;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionSheetCustomerSavings implements Serializable {

    private Integer customerId;
    private Integer accountId;
    private Short productId;
    private String productShortName;
    private Short recommendedAmountUnitId;
    private Short currencyId;
    private Double depositDue;
    private Double depositPaid;
    private Double totalDepositAmount;
    private Double withdrawal = 0.0;
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Double getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(Double withdrawal) {
        this.withdrawal = withdrawal;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Short getProductId() {
        return productId;
    }

    public void setProductId(Short productId) {
        this.productId = productId;
    }

    public String getProductShortName() {
        return productShortName;
    }

    public void setProductShortName(String productShortName) {
        this.productShortName = productShortName;
    }

    public Short getRecommendedAmountUnitId() {
        return recommendedAmountUnitId;
    }

    public void setRecommendedAmountUnitId(Short recommendedAmountUnitId) {
        this.recommendedAmountUnitId = recommendedAmountUnitId;
    }

    public Short getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Short currencyId) {
        this.currencyId = currencyId;
    }

    public Double getDepositDue() {
        return depositDue;
    }

    public void setDepositDue(Double depositDue) {
        this.depositDue = depositDue;
    }

    public Double getDepositPaid() {
        return depositPaid;
    }

    public void setDepositPaid(Double depositPaid) {
        this.depositPaid = depositPaid;
    }

    public Double getTotalDepositAmount() {
        return totalDepositAmount;
    }
    public void setTotalDepositAmount(Double totalDepositAmount) {
        this.totalDepositAmount = totalDepositAmount;
    }

}

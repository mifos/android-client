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
public class CollectionSheetCustomerLoan implements Serializable {

    private Integer customerId;
    private Integer accountId;
    private Short accountStateId;
    private String productShortName;
    private Short productId;
    private Short currencyId;
    private Double payInterestAtDisbursement;
    private Double amountDueAtDisbursement;
    private Double totalRepaymentDue;
    private Double totalDisbursement;
    private boolean disbursalAccount;

    public Double getPayInterestAtDisbursement() {
        return payInterestAtDisbursement;
    }

    public void setPayInterestAtDisbursement(Double payInterestAtDisbursement) {
        this.payInterestAtDisbursement = payInterestAtDisbursement;
    }

    public Double getAmountDueAtDisbursement() {
        return amountDueAtDisbursement;
    }

    public void setAmountDueAtDisbursement(Double amountDueAtDisbursement) {
        this.amountDueAtDisbursement = amountDueAtDisbursement;
    }

    public Double getTotalDisbursement() {
        return totalDisbursement;
    }

    public void setTotalDisbursement(Double totalDisbursement) {
        this.totalDisbursement = totalDisbursement;
    }

    public Double getTotalRepaymentDue() {
        return totalRepaymentDue;
    }

    public void setTotalRepaymentDue(Double totalRepaymentDue) {
        this.totalRepaymentDue = totalRepaymentDue;
    }


    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Short getAccountStateId() {
        return accountStateId;
    }

    public void setAccountStateId(Short accountStateId) {
        this.accountStateId = accountStateId;
    }

    public String getProductShortName() {
        return productShortName;
    }

    public void setProductShortName(String productShortName) {
        this.productShortName = productShortName;
    }

    public Short getProductId() {
        return productId;
    }

    public void setProductId(Short productId) {
        this.productId = productId;
    }

    public Short getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Short currencyId) {
        this.currencyId = currencyId;
    }

    public boolean isDisbursalAccount() {
        return disbursalAccount;
    }

    public void setDisbursalAccount(boolean disbursalAccount) {
        this.disbursalAccount = disbursalAccount;
    }

}

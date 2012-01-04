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

@JsonIgnoreProperties(ignoreUnknown = true, value = { "totalRepaymentDue", "totalDisbursement" })
public class CollectionSheetCustomerLoan implements Serializable {

    private Integer customerId;
    private Integer accountId;
    private Short accountStateId;
    private String productShortName;
    private Short productId;
    private Short currencyId;
    private BigDecimal principalDue;
    private BigDecimal principalPaid;
    private BigDecimal interestDue;
    private BigDecimal interestPaid;
    private BigDecimal penaltyDue;
    private BigDecimal penaltyPaid;
    private BigDecimal miscFeesDue;
    private BigDecimal miscFeesPaid;
    private BigDecimal miscPenaltyDue;
    private BigDecimal miscPenaltyPaid;

    private Double totalAccountFees;

    private BigDecimal disbursementAmount;
    private Short payInterestAtDisbursement;
    private Double amountDueAtDisbursement;

    private boolean disbursalAccount;

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

    public BigDecimal getPrincipalDue() {
        return principalDue;
    }

    public void setPrincipalDue(BigDecimal principalDue) {
        this.principalDue = principalDue;
    }

    public BigDecimal getPrincipalPaid() {
        return principalPaid;
    }

    public void setPrincipalPaid(BigDecimal principalPaid) {
        this.principalPaid = principalPaid;
    }

    public BigDecimal getInterestDue() {
        return interestDue;
    }

    public void setInterestDue(BigDecimal interestDue) {
        this.interestDue = interestDue;
    }

    public BigDecimal getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(BigDecimal interestPaid) {
        this.interestPaid = interestPaid;
    }

    public BigDecimal getPenaltyDue() {
        return penaltyDue;
    }

    public void setPenaltyDue(BigDecimal penaltyDue) {
        this.penaltyDue = penaltyDue;
    }

    public BigDecimal getPenaltyPaid() {
        return penaltyPaid;
    }

    public void setPenaltyPaid(BigDecimal penaltyPaid) {
        this.penaltyPaid = penaltyPaid;
    }

    public BigDecimal getMiscFeesDue() {
        return miscFeesDue;
    }

    public void setMiscFeesDue(BigDecimal miscFeesDue) {
        this.miscFeesDue = miscFeesDue;
    }

    public BigDecimal getMiscFeesPaid() {
        return miscFeesPaid;
    }

    public void setMiscFeesPaid(BigDecimal miscFeesPaid) {
        this.miscFeesPaid = miscFeesPaid;
    }

    public BigDecimal getMiscPenaltyDue() {
        return miscPenaltyDue;
    }

    public void setMiscPenaltyDue(BigDecimal miscPenaltyDue) {
        this.miscPenaltyDue = miscPenaltyDue;
    }

    public BigDecimal getMiscPenaltyPaid() {
        return miscPenaltyPaid;
    }

    public void setMiscPenaltyPaid(BigDecimal miscPenaltyPaid) {
        this.miscPenaltyPaid = miscPenaltyPaid;
    }

    public Double getTotalAccountFees() {
        return totalAccountFees;
    }

    public void setTotalAccountFees(Double totalAccountFees) {
        this.totalAccountFees = totalAccountFees;
    }

    public BigDecimal getDisbursementAmount() {
        return disbursementAmount;
    }

    public void setDisbursementAmount(BigDecimal disbursementAmount) {
        this.disbursementAmount = disbursementAmount;
    }

    public Short getPayInterestAtDisbursement() {
        return payInterestAtDisbursement;
    }

    public void setPayInterestAtDisbursement(Short payInterestAtDisbursement) {
        this.payInterestAtDisbursement = payInterestAtDisbursement;
    }

    public Double getAmountDueAtDisbursement() {
        return amountDueAtDisbursement;
    }

    public void setAmountDueAtDisbursement(Double amountDueAtDisbursement) {
        this.amountDueAtDisbursement = amountDueAtDisbursement;
    }

    public Double getTotalRepaymentDue() {
        return principalDue.add(interestDue).add(penaltyDue).add(miscFeesDue).add(miscPenaltyDue).subtract(
                principalPaid.add(interestPaid).add(penaltyPaid).add(miscFeesPaid).add(miscPenaltyPaid)
        ).add(new BigDecimal(this.totalAccountFees)).doubleValue();
    }

    public Double getTotalDisbursement() {
        return disbursementAmount.doubleValue();
    }

    public boolean isDisbursalAccount() {
        return disbursalAccount;
    }

    public void setDisbursalAccount(boolean disbursalAccount) {
        this.disbursalAccount = disbursalAccount;
    }

}

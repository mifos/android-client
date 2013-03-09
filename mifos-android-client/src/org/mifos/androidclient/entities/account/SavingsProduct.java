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

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SavingsProduct implements Serializable {

    private boolean openSavingsAccountsExist;
    private ProductDetails productDetails;
    private Double amountForDeposit;
    private Double maxWithdrawal;
    private boolean groupMandatorySavingsAccount;
    private Double interestRate;
    private Integer interestCalculationFrequency;
    private Integer interestCalculationFrequencyPeriod;
    private Integer interestPostingMonthlyFrequency;
    private Double minBalanceForInterestCalculation;
    private Integer depositGlCode;
    private String depositGlCodeValue;
    private Integer interestGlCode;
    private String interestGlCodeValue;

    public boolean isOpenSavingsAccountsExist() {
        return openSavingsAccountsExist;
    }

    public void setOpenSavingsAccountsExist(boolean openSavingsAccountsExist) {
        this.openSavingsAccountsExist = openSavingsAccountsExist;
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    public Double getAmountForDeposit() {
        return amountForDeposit;
    }

    public void setAmountForDeposit(Double amountForDeposit) {
        this.amountForDeposit = amountForDeposit;
    }

    public Double getMaxWithdrawal() {
        return maxWithdrawal;
    }

    public void setMaxWithdrawal(Double maxWithdrawal) {
        this.maxWithdrawal = maxWithdrawal;
    }

    public boolean isGroupMandatorySavingsAccount() {
        return groupMandatorySavingsAccount;
    }

    public void setGroupMandatorySavingsAccount(boolean groupMandatorySavingsAccount) {
        this.groupMandatorySavingsAccount = groupMandatorySavingsAccount;
    }

    public Integer getInterestCalculationFrequency() {
        return interestCalculationFrequency;
    }

    public void setInterestCalculationFrequency(Integer interestCalculationFrequency) {
        this.interestCalculationFrequency = interestCalculationFrequency;
    }

    public Integer getInterestCalculationFrequencyPeriod() {
        return interestCalculationFrequencyPeriod;
    }

    public void setInterestCalculationFrequencyPeriod(Integer interestCalculationFrequencyPeriod) {
        this.interestCalculationFrequencyPeriod = interestCalculationFrequencyPeriod;
    }

    public Integer getInterestPostingMonthlyFrequency() {
        return interestPostingMonthlyFrequency;
    }

    public void setInterestPostingMonthlyFrequency(Integer interestPostingMonthlyFrequency) {
        this.interestPostingMonthlyFrequency = interestPostingMonthlyFrequency;
    }

    public Integer getDepositGlCode() {
        return depositGlCode;
    }

    public void setDepositGlCode(Integer depositGlCode) {
        this.depositGlCode = depositGlCode;
    }

    public String getDepositGlCodeValue() {
        return depositGlCodeValue;
    }

    public void setDepositGlCodeValue(String depositGlCodeValue) {
        this.depositGlCodeValue = depositGlCodeValue;
    }

    public Integer getInterestGlCode() {
        return interestGlCode;
    }

    public void setInterestGlCode(Integer interestGlCode) {
        this.interestGlCode = interestGlCode;
    }

    public String getInterestGlCodeValue() {
        return interestGlCodeValue;
    }

    public void setInterestGlCodeValue(String interestGlCodeValue) {
        this.interestGlCodeValue = interestGlCodeValue;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Double getMinBalanceForInterestCalculation() {
        return minBalanceForInterestCalculation;
    }

    public void setMinBalanceForInterestCalculation(Double minBalanceForInterestCalculation) {
        this.minBalanceForInterestCalculation = minBalanceForInterestCalculation;
    }

}

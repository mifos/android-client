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

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanAccountDetails extends AbstractAccountDetails {

    private String accountStateName;
    private String customerName;
    private String globalCustNum;
    private String prdOfferingName;
    private List<String> accountFlagNames;
    private String disbursementDate;
    private Boolean redone;
    private String businessActivityName;
    private String gracePeriodTypeName;
    private String interestTypeName;
    private String accountTypeName;
    private String officeName;
    private String nextMeetingDate;
    private String totalAmountDue;
    private String totalAmountInArrears;
    private LoanSummary loanSummary;
    private Boolean loanActivityDetails;
    private Double interestRate;
    private Boolean interestDeductedAtDisbursement;
    private Short recurAfter;
    private Short recurrenceId;
    private String meetingTypeName;
    private Boolean prinDueLastInst;
    private Short noOfInstallments;
    private Short minNoOfInstall;
    private Short maxNoOfInstall;
    private Short gracePeriodDuration;
    private String fundName;
    private String collateralTypeName;
    private String collateralNote;
    private String externalId;
    private List<AccountFee> accountFees;
    private  String createdDate;
    private LoanPerformanceHistory performanceHistory;
    private Boolean group;
    private List<LoanActivity> recentAccountActivities;
    private Boolean disbursed;
    private List<CustomerNote> recentNoteDtos;

    public String getAccountStateName() {
        return accountStateName;
    }

    public void setAccountStateName(String accountStateName) {
        this.accountStateName = accountStateName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getGlobalCustNum() {
        return globalCustNum;
    }

    public void setGlobalCustNum(String globalCustNum) {
        this.globalCustNum = globalCustNum;
    }

    public String getPrdOfferingName() {
        return prdOfferingName;
    }

    public void setPrdOfferingName(String prdOfferingName) {
        this.prdOfferingName = prdOfferingName;
    }

    public List<String> getAccountFlagNames() {
        return accountFlagNames;
    }

    public void setAccountFlagNames(List<String> accountFlagNames) {
        this.accountFlagNames = accountFlagNames;
    }

    public String getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(String disbursementDate) {
        this.disbursementDate = disbursementDate;
    }

    public Boolean getRedone() {
        return redone;
    }

    public void setRedone(Boolean redone) {
        this.redone = redone;
    }

    public String getBusinessActivityName() {
        return businessActivityName;
    }

    public void setBusinessActivityName(String businessActivityName) {
        this.businessActivityName = businessActivityName;
    }

    public String getGracePeriodTypeName() {
        return gracePeriodTypeName;
    }

    public void setGracePeriodTypeName(String gracePeriodTypeName) {
        this.gracePeriodTypeName = gracePeriodTypeName;
    }

    public String getInterestTypeName() {
        return interestTypeName;
    }

    public void setInterestTypeName(String interestTypeName) {
        this.interestTypeName = interestTypeName;
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public void setAccountTypeName(String accountTypeName) {
        this.accountTypeName = accountTypeName;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getNextMeetingDate() {
        return nextMeetingDate;
    }

    public void setNextMeetingDate(String nextMeetingDate) {
        this.nextMeetingDate = nextMeetingDate;
    }

    public String getTotalAmountDue() {
        return totalAmountDue;
    }

    public void setTotalAmountDue(String totalAmountDue) {
        this.totalAmountDue = totalAmountDue;
    }

    public String getTotalAmountInArrears() {
        return totalAmountInArrears;
    }

    public void setTotalAmountInArrears(String totalAmountInArrears) {
        this.totalAmountInArrears = totalAmountInArrears;
    }

    public LoanSummary getLoanSummary() {
        return loanSummary;
    }

    public void setLoanSummary(LoanSummary loanSummary) {
        this.loanSummary = loanSummary;
    }

    public Boolean getLoanActivityDetails() {
        return loanActivityDetails;
    }

    public void setLoanActivityDetails(Boolean loanActivityDetails) {
        this.loanActivityDetails = loanActivityDetails;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public Boolean getInterestDeductedAtDisbursement() {
        return interestDeductedAtDisbursement;
    }

    public void setInterestDeductedAtDisbursement(Boolean interestDeductedAtDisbursement) {
        this.interestDeductedAtDisbursement = interestDeductedAtDisbursement;
    }

    public Short getRecurAfter() {
        return recurAfter;
    }

    public void setRecurAfter(Short recurAfter) {
        this.recurAfter = recurAfter;
    }

    public Short getRecurrenceId() {
        return recurrenceId;
    }

    public void setRecurrenceId(Short recurrenceId) {
        this.recurrenceId = recurrenceId;
    }

    public String getMeetingTypeName() {
        return meetingTypeName;
    }

    public void setMeetingTypeName(String meetingTypeName) {
        this.meetingTypeName = meetingTypeName;
    }

    public Boolean getPrinDueLastInst() {
        return prinDueLastInst;
    }

    public void setPrinDueLastInst(Boolean prinDueLastInst) {
        this.prinDueLastInst = prinDueLastInst;
    }

    public Short getNoOfInstallments() {
        return noOfInstallments;
    }

    public void setNoOfInstallments(Short noOfInstallments) {
        this.noOfInstallments = noOfInstallments;
    }

    public Short getMinNoOfInstall() {
        return minNoOfInstall;
    }

    public void setMinNoOfInstall(Short minNoOfInstall) {
        this.minNoOfInstall = minNoOfInstall;
    }

    public Short getMaxNoOfInstall() {
        return maxNoOfInstall;
    }

    public void setMaxNoOfInstall(Short maxNoOfInstall) {
        this.maxNoOfInstall = maxNoOfInstall;
    }

    public Short getGracePeriodDuration() {
        return gracePeriodDuration;
    }

    public void setGracePeriodDuration(Short gracePeriodDuration) {
        this.gracePeriodDuration = gracePeriodDuration;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getCollateralTypeName() {
        return collateralTypeName;
    }

    public void setCollateralTypeName(String collateralTypeName) {
        this.collateralTypeName = collateralTypeName;
    }

    public String getCollateralNote() {
        return collateralNote;
    }

    public void setCollateralNote(String collateralNote) {
        this.collateralNote = collateralNote;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public List<AccountFee> getAccountFees() {
        return accountFees;
    }

    public void setAccountFees(List<AccountFee> accountFees) {
        this.accountFees = accountFees;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public LoanPerformanceHistory getPerformanceHistory() {
        return performanceHistory;
    }

    public void setPerformanceHistory(LoanPerformanceHistory performanceHistory) {
        this.performanceHistory = performanceHistory;
    }

    public Boolean getGroup() {
        return group;
    }

    public void setGroup(Boolean group) {
        this.group = group;
    }

    public List<LoanActivity> getRecentAccountActivities() {
        return recentAccountActivities;
    }

    public void setRecentAccountActivities(List<LoanActivity> recentAccountActivities) {
        this.recentAccountActivities = recentAccountActivities;
    }

    public Boolean getDisbursed() {
        return disbursed;
    }

    public void setDisbursed(Boolean disbursed) {
        this.disbursed = disbursed;
    }

    public List<CustomerNote> getRecentNoteDtos() {
        return recentNoteDtos;
    }

    public void setRecentNoteDtos(List<CustomerNote> recentNoteDtos) {
        this.recentNoteDtos = recentNoteDtos;
    }

}

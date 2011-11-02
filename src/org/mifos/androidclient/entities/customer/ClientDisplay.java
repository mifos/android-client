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

import org.mifos.androidclient.entities.BaseEntity;

import java.util.List;

public class ClientDisplay extends BaseEntity {

    private String displayName;
    private Integer age;
    private Integer customerId;
    private Integer loanOfficerId;
    private Integer branchId;
    private String maritalStatus;
    private String globalCustNum;
    private String governmentId;
    private String externalId;
    private String customerActivationDate;
    private Integer customerLevelId;
    private String customerStatusName;
    private String spouseFatherName;
    private String dateOfBirth;
    private String trainedDate;
    private List<ClientFamilyDetail> familyDetails;
    private String businessActivities;
    private String citizenship;
    private String educationLevel;
    private String handicapped;
    private Integer numChildren;
    private String povertyStatus;
    private Integer customerStatusId;
    private String loanOfficerName;
    private String parentCustomerDisplayName;
    private String customerFormedByDisplayName;
    private Boolean blackListed;
    private String branchName;
    private Boolean clientUnderGroup;
    private String ethnicity;
    private Boolean areFamilyDetailsRequired;
    private String spouseFatherValue;

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getLoanOfficerId() {
        return loanOfficerId;
    }

    public void setLoanOfficerId(Integer loanOfficerId) {
        this.loanOfficerId = loanOfficerId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getGlobalCustNum() {
        return globalCustNum;
    }

    public void setGlobalCustNum(String globalCustNum) {
        this.globalCustNum = globalCustNum;
    }

    public String getGovernmentId() {
        return governmentId;
    }

    public void setGovernmentId(String governmentId) {
        this.governmentId = governmentId;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getCustomerActivationDate() {
        return customerActivationDate;
    }

    public void setCustomerActivationDate(String customerActivationDate) {
        this.customerActivationDate = customerActivationDate;
    }

    public Integer getCustomerLevelId() {
        return customerLevelId;
    }

    public void setCustomerLevelId(Integer customerLevelId) {
        this.customerLevelId = customerLevelId;
    }

    public String getCustomerStatusName() {
        return customerStatusName;
    }

    public void setCustomerStatusName(String customerStatusName) {
        this.customerStatusName = customerStatusName;
    }

    public String getSpouseFatherName() {
        return spouseFatherName;
    }

    public void setSpouseFatherName(String spouseFatherName) {
        this.spouseFatherName = spouseFatherName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getTrainedDate() {
        return trainedDate;
    }

    public void setTrainedDate(String trainedDate) {
        this.trainedDate = trainedDate;
    }

    public List<ClientFamilyDetail> getFamilyDetails() {
        return familyDetails;
    }

    public void setFamilyDetails(List<ClientFamilyDetail> familyDetails) {
        this.familyDetails = familyDetails;
    }

    public String getBusinessActivities() {
        return businessActivities;
    }

    public void setBusinessActivities(String businessActivities) {
        this.businessActivities = businessActivities;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getHandicapped() {
        return handicapped;
    }

    public void setHandicapped(String handicapped) {
        this.handicapped = handicapped;
    }

    public Integer getNumChildren() {
        return numChildren;
    }

    public void setNumChildren(Integer numChildren) {
        this.numChildren = numChildren;
    }

    public String getPovertyStatus() {
        return povertyStatus;
    }

    public void setPovertyStatus(String povertyStatus) {
        this.povertyStatus = povertyStatus;
    }

    public Integer getCustomerStatusId() {
        return customerStatusId;
    }

    public void setCustomerStatusId(Integer customerStatusId) {
        this.customerStatusId = customerStatusId;
    }

    public String getLoanOfficerName() {
        return loanOfficerName;
    }

    public void setLoanOfficerName(String loanOfficerName) {
        this.loanOfficerName = loanOfficerName;
    }

    public String getParentCustomerDisplayName() {
        return parentCustomerDisplayName;
    }

    public void setParentCustomerDisplayName(String parentCustomerDisplayName) {
        this.parentCustomerDisplayName = parentCustomerDisplayName;
    }

    public String getCustomerFormedByDisplayName() {
        return customerFormedByDisplayName;
    }

    public void setCustomerFormedByDisplayName(String customerFormedByDisplayName) {
        this.customerFormedByDisplayName = customerFormedByDisplayName;
    }

    public Boolean getBlackListed() {
        return blackListed;
    }

    public void setBlackListed(Boolean blackListed) {
        this.blackListed = blackListed;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Boolean getClientUnderGroup() {
        return clientUnderGroup;
    }

    public void setClientUnderGroup(Boolean clientUnderGroup) {
        this.clientUnderGroup = clientUnderGroup;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public Boolean getAreFamilyDetailsRequired() {
        return areFamilyDetailsRequired;
    }

    public void setAreFamilyDetailsRequired(Boolean areFamilyDetailsRequired) {
        this.areFamilyDetailsRequired = areFamilyDetailsRequired;
    }

    public String getSpouseFatherValue() {
        return spouseFatherValue;
    }

    public void setSpouseFatherValue(String spouseFatherValue) {
        this.spouseFatherValue = spouseFatherValue;
    }

}

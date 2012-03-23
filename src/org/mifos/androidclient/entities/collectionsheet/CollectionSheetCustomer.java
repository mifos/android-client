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
import org.mifos.androidclient.util.listadapters.SimpleListItem;

import java.io.Serializable;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionSheetCustomer implements Serializable, SimpleListItem {
    public static final String BUNDLE_KEY = CollectionSheetCustomer.class.getSimpleName();

    private Integer customerId;
    private String name;
    private Short levelId;
    private Integer parentCustomerId;
    private String searchId;
    private Short branchId;
    private Short attendanceId;
    private CollectionSheetCustomerAccount collectionSheetCustomerAccount;
    private List<CollectionSheetCustomerLoan> collectionSheetCustomerLoan;
    private List<CollectionSheetCustomerSavings> collectionSheetCustomerSaving;
    private List<CollectionSheetCustomerSavings> individualSavingAccounts;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getLevelId() {
        return levelId;
    }

    public void setLevelId(Short levelId) {
        this.levelId = levelId;
    }

    public Integer getParentCustomerId() {
        return parentCustomerId;
    }

    public void setParentCustomerId(Integer parentCustomerId) {
        this.parentCustomerId = parentCustomerId;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public Short getBranchId() {
        return branchId;
    }

    public void setBranchId(Short branchId) {
        this.branchId = branchId;
    }

    public Short getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Short attendanceId) {
        this.attendanceId = attendanceId;
    }

    public CollectionSheetCustomerAccount getCollectionSheetCustomerAccount() {
        return collectionSheetCustomerAccount;
    }

    public void setCollectionSheetCustomerAccount(CollectionSheetCustomerAccount collectionSheetCustomerAccount) {
        this.collectionSheetCustomerAccount = collectionSheetCustomerAccount;
    }

    public List<CollectionSheetCustomerLoan> getCollectionSheetCustomerLoan() {
        return collectionSheetCustomerLoan;
    }

    public void setCollectionSheetCustomerLoan(List<CollectionSheetCustomerLoan> collectionSheetCustomerLoan) {
        this.collectionSheetCustomerLoan = collectionSheetCustomerLoan;
    }

    public List<CollectionSheetCustomerSavings> getCollectionSheetCustomerSaving() {
        return collectionSheetCustomerSaving;
    }

    public void setCollectionSheetCustomerSaving(List<CollectionSheetCustomerSavings> collectionSheetCustomerSaving) {
        this.collectionSheetCustomerSaving = collectionSheetCustomerSaving;
    }

    public List<CollectionSheetCustomerSavings> getIndividualSavingAccounts() {
        return individualSavingAccounts;
    }

    public void setIndividualSavingAccounts(List<CollectionSheetCustomerSavings> individualSavingAccounts) {
        this.individualSavingAccounts = individualSavingAccounts;
    }

    @Override
    public String getListLabel() {
        String listLabel;
        listLabel = getName();
        return listLabel;
    }

    @Override
    public int getItemIdentifier() {
        return getCustomerId();
    }
}

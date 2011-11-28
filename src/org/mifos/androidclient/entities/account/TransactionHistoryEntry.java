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
import org.mifos.androidclient.util.listadapters.SimpleListItem;
import org.mifos.androidclient.util.ui.DateUtils;

import java.io.Serializable;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionHistoryEntry implements SimpleListItem, Serializable {

    public static final String BUNDLE_KEY = TransactionHistoryEntry.class.getSimpleName();

    private Date transactionDate;
    private Integer paymentId;
    private Integer accountTrxnId;
    private String type;
    private String glcode;
    private String debit;
    private String credit;
    private String balance;
    private String clientName;
    private Date postedDate;
    private String postedBy;
    private String notes;
    private String userPrefferedTransactionDate;
    private String userPrefferedPostedDate;

    @Override
    public String getListLabel() {
        return DateUtils.format(transactionDate) + " - " + type;
    }

    @Override
    public int getItemIdentifier() {
        return 0;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public String getFormattedTransactionDate() {
        return DateUtils.format(transactionDate);
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getAccountTrxnId() {
        return accountTrxnId;
    }

    public void setAccountTrxnId(Integer accountTrxnId) {
        this.accountTrxnId = accountTrxnId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGlcode() {
        return glcode;
    }

    public void setGlcode(String glcode) {
        this.glcode = glcode;
    }

    public String getDebit() {
        return debit;
    }

    public void setDebit(String debit) {
        this.debit = debit;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Date getPostedDate() {
        return postedDate;
    }

    public String getFormattedPostedDate()  {
        return DateUtils.format(postedDate);
    }

    public void setPostedDate(Date postedDate) {
        this.postedDate = postedDate;
    }

    public String getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(String postedBy) {
        this.postedBy = postedBy;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUserPrefferedTransactionDate() {
        return userPrefferedTransactionDate;
    }

    public void setUserPrefferedTransactionDate(String userPrefferedTransactionDate) {
        this.userPrefferedTransactionDate = userPrefferedTransactionDate;
    }

    public String getUserPrefferedPostedDate() {
        return userPrefferedPostedDate;
    }

    public void setUserPrefferedPostedDate(String userPrefferedPostedDate) {
        this.userPrefferedPostedDate = userPrefferedPostedDate;
    }

}

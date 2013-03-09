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
public class AccountFee implements Serializable {

    public static final String BUNDLE_KEY = AccountFee.class.getSimpleName();

    public class FeeFrequency {

        public static final short PERIODIC = 1;
        public static final short ONE_TIME = 2;

    }

    public class FeePayment {

        public static final short UPFRONT = 1;
        public static final short TIME_OF_DISBURSEMENT = 2;
        public static final short TIME_OF_FIRST_LOAN_REPAYMENT = 3;

    }

    private Short feeId;
    private Short feeFrequencyTypeId;
    private Short feePaymentTypeId;
    private Short feeStatus;
    private String feeName;
    private Double accountFeeAmount;
    private String meetingRecurrence;

    public Short getFeeId() {
        return feeId;
    }

    public void setFeeId(Short feeId) {
        this.feeId = feeId;
    }

    public Short getFeeFrequencyTypeId() {
        return feeFrequencyTypeId;
    }

    public void setFeeFrequencyTypeId(Short feeFrequencyTypeId) {
        this.feeFrequencyTypeId = feeFrequencyTypeId;
    }

    public Short getFeePaymentTypeId() {
        return feePaymentTypeId;
    }

    public void setFeePaymentTypeId(Short feePaymentTypeId) {
        this.feePaymentTypeId = feePaymentTypeId;
    }

    public Short getFeeStatus() {
        return feeStatus;
    }

    public void setFeeStatus(Short feeStatus) {
        this.feeStatus = feeStatus;
    }

    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public Double getAccountFeeAmount() {
        return accountFeeAmount;
    }

    public void setAccountFeeAmount(Double accountFeeAmount) {
        this.accountFeeAmount = accountFeeAmount;
    }

    public String getMeetingRecurrence() {
        return meetingRecurrence;
    }

    public void setMeetingRecurrence(String meetingRecurrence) {
        this.meetingRecurrence = meetingRecurrence;
    }

}

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

    private String feeFrequencyTypeName;
    private String feeStatusName;
    private String feeName;
    private String accountFeeAmount;
    private String meetingRecurrence;

    public String getFeeFrequencyTypeName() {
        return feeFrequencyTypeName;
    }

    public void setFeeFrequencyTypeName(String feeFrequencyTypeName) {
        this.feeFrequencyTypeName = feeFrequencyTypeName;
    }

    public String getFeeStatusName() {
        return feeStatusName;
    }

    public void setFeeStatusName(String feeStatusName) {
        this.feeStatusName = feeStatusName;
    }

    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public String getAccountFeeAmount() {
        return accountFeeAmount;
    }

    public void setAccountFeeAmount(String accountFeeAmount) {
        this.accountFeeAmount = accountFeeAmount;
    }

    public String getMeetingRecurrence() {
        return meetingRecurrence;
    }

    public void setMeetingRecurrence(String meetingRecurrence) {
        this.meetingRecurrence = meetingRecurrence;
    }

}

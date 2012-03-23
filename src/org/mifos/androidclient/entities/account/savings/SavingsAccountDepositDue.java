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

package org.mifos.androidclient.entities.account.savings;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SavingsAccountDepositDue implements Serializable {

    public final static String BUNDLE_KEY = SavingsAccountDepositDue.class.getSimpleName();

    private DueOnDate nextDueDetail;
    private List<DueOnDate> previousDueDetails;
    private Short stateId;
    private String stateName;

    public DueOnDate getNextDueDetail() {
        return nextDueDetail;
    }

    public void setNextDueDetail(DueOnDate nextDueDetail) {
        this.nextDueDetail = nextDueDetail;
    }

    public List<DueOnDate> getPreviousDueDetails() {
        return previousDueDetails;
    }

    public void setPreviousDueDetails(List<DueOnDate> previousDueDetails) {
        this.previousDueDetails = previousDueDetails;
    }

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

}

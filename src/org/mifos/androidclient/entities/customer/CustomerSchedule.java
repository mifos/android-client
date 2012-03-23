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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.mifos.androidclient.entities.account.AccountFeeSchedule;

import java.io.Serializable;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerSchedule implements Serializable {

    private Double miscFee;
    private Double miscFeePaid;
    private Double miscPenalty;
    private Double miscPenaltyPaid;
    private List<AccountFeeSchedule> feesActionDetails;

    public Double getMiscFee() {
        return miscFee;
    }

    public void setMiscFee(Double miscFee) {
        this.miscFee = miscFee;
    }

    public Double getMiscFeePaid() {
        return miscFeePaid;
    }

    public void setMiscFeePaid(Double miscFeePaid) {
        this.miscFeePaid = miscFeePaid;
    }

    public Double getMiscPenalty() {
        return miscPenalty;
    }

    public void setMiscPenalty(Double miscPenalty) {
        this.miscPenalty = miscPenalty;
    }

    public Double getMiscPenaltyPaid() {
        return miscPenaltyPaid;
    }

    public void setMiscPenaltyPaid(Double miscPenaltyPaid) {
        this.miscPenaltyPaid = miscPenaltyPaid;
    }

    public List<AccountFeeSchedule> getFeesActionDetails() {
        return feesActionDetails;
    }

    public void setFeesActionDetails(List<AccountFeeSchedule> feesActionDetails) {
        this.feesActionDetails = feesActionDetails;
    }

}

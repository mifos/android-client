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

import java.io.Serializable;
import java.util.List;

public class ClientPerformanceHistory extends BaseEntity implements Serializable {

    private Integer loanCycleNumber;
    private String lastLoanAmount;
    private Integer noOfActiveLoans;
    private String delinquentPortfolioAmount;
    private String totalSavingsAmount;
    private Integer meetingsAttended;
    private Integer meetingsMissed;
    private List<LoanCycleCounter> loanCycleCounters;

    public Integer getLoanCycleNumber() {
        return loanCycleNumber;
    }

    public void setLoanCycleNumber(Integer loanCycleNumber) {
        this.loanCycleNumber = loanCycleNumber;
    }

    public String getLastLoanAmount() {
        return lastLoanAmount;
    }

    public void setLastLoanAmount(String lastLoanAmount) {
        this.lastLoanAmount = lastLoanAmount;
    }

    public Integer getNoOfActiveLoans() {
        return noOfActiveLoans;
    }

    public void setNoOfActiveLoans(Integer noOfActiveLoans) {
        this.noOfActiveLoans = noOfActiveLoans;
    }

    public String getDelinquentPortfolioAmount() {
        return delinquentPortfolioAmount;
    }

    public void setDelinquentPortfolioAmount(String delinquentPortfolioAmount) {
        this.delinquentPortfolioAmount = delinquentPortfolioAmount;
    }

    public String getTotalSavingsAmount() {
        return totalSavingsAmount;
    }

    public void setTotalSavingsAmount(String totalSavingsAmount) {
        this.totalSavingsAmount = totalSavingsAmount;
    }

    public Integer getMeetingsAttended() {
        return meetingsAttended;
    }

    public void setMeetingsAttended(Integer meetingsAttended) {
        this.meetingsAttended = meetingsAttended;
    }

    public Integer getMeetingsMissed() {
        return meetingsMissed;
    }

    public void setMeetingsMissed(Integer meetingsMissed) {
        this.meetingsMissed = meetingsMissed;
    }

    public List<LoanCycleCounter> getLoanCycleCounters() {
        return loanCycleCounters;
    }

    public void setLoanCycleCounters(List<LoanCycleCounter> loanCycleCounters) {
        this.loanCycleCounters = loanCycleCounters;
    }

}

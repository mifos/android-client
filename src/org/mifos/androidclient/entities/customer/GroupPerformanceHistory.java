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

public class GroupPerformanceHistory extends BaseEntity {

    private String activeClientCount;
    private String lastGroupLoanAmount;
    private String avgLoanAmountForMember;
    private String totalOutStandingLoanAmount;
    private String portfolioAtRisk;
    private String totalSavingsAmount;
    private List<LoanCycleCounter> loanCycleCounters;

    public String getActiveClientCount() {
        return activeClientCount;
    }

    public void setActiveClientCount(String activeClientCount) {
        this.activeClientCount = activeClientCount;
    }

    public String getLastGroupLoanAmount() {
        return lastGroupLoanAmount;
    }

    public void setLastGroupLoanAmount(String lastGroupLoanAmount) {
        this.lastGroupLoanAmount = lastGroupLoanAmount;
    }

    public String getAvgLoanAmountForMember() {
        return avgLoanAmountForMember;
    }

    public void setAvgLoanAmountForMember(String avgLoanAmountForMember) {
        this.avgLoanAmountForMember = avgLoanAmountForMember;
    }

    public String getTotalOutStandingLoanAmount() {
        return totalOutStandingLoanAmount;
    }

    public void setTotalOutStandingLoanAmount(String totalOutStandingLoanAmount) {
        this.totalOutStandingLoanAmount = totalOutStandingLoanAmount;
    }

    public String getPortfolioAtRisk() {
        return portfolioAtRisk;
    }

    public void setPortfolioAtRisk(String portfolioAtRisk) {
        this.portfolioAtRisk = portfolioAtRisk;
    }

    public String getTotalSavingsAmount() {
        return totalSavingsAmount;
    }

    public void setTotalSavingsAmount(String totalSavingsAmount) {
        this.totalSavingsAmount = totalSavingsAmount;
    }

    public List<LoanCycleCounter> getLoanCycleCounters() {
        return loanCycleCounters;
    }

    public void setLoanCycleCounters(List<LoanCycleCounter> loanCycleCounters) {
        this.loanCycleCounters = loanCycleCounters;
    }

}

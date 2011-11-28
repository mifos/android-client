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

import java.io.Serializable;

public class LoanSummary implements Serializable {

    private String originalPrincipal;
    private String principalPaid;
    private String principalDue;

    private String originalInterest;
    private String interestPaid;
    private String interestDue;

    private String originalFees;
    private String feesPaid;
    private String feesDue;

    private String originalPenalty;
    private String penaltyPaid;
    private String penaltyDue;

    private String totalLoanAmnt;
    private String totalAmntPaid;
    private String totalAmntDue;

    public String getOriginalPrincipal() {
        return originalPrincipal;
    }

    public void setOriginalPrincipal(String originalPrincipal) {
        this.originalPrincipal = originalPrincipal;
    }

    public String getPrincipalPaid() {
        return principalPaid;
    }

    public void setPrincipalPaid(String principalPaid) {
        this.principalPaid = principalPaid;
    }

    public String getPrincipalDue() {
        return principalDue;
    }

    public void setPrincipalDue(String principalDue) {
        this.principalDue = principalDue;
    }

    public String getOriginalInterest() {
        return originalInterest;
    }

    public void setOriginalInterest(String originalInterest) {
        this.originalInterest = originalInterest;
    }

    public String getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(String interestPaid) {
        this.interestPaid = interestPaid;
    }

    public String getInterestDue() {
        return interestDue;
    }

    public void setInterestDue(String interestDue) {
        this.interestDue = interestDue;
    }

    public String getOriginalFees() {
        return originalFees;
    }

    public void setOriginalFees(String originalFees) {
        this.originalFees = originalFees;
    }

    public String getFeesPaid() {
        return feesPaid;
    }

    public void setFeesPaid(String feesPaid) {
        this.feesPaid = feesPaid;
    }

    public String getFeesDue() {
        return feesDue;
    }

    public void setFeesDue(String feesDue) {
        this.feesDue = feesDue;
    }

    public String getOriginalPenalty() {
        return originalPenalty;
    }

    public void setOriginalPenalty(String originalPenalty) {
        this.originalPenalty = originalPenalty;
    }

    public String getPenaltyPaid() {
        return penaltyPaid;
    }

    public void setPenaltyPaid(String penaltyPaid) {
        this.penaltyPaid = penaltyPaid;
    }

    public String getPenaltyDue() {
        return penaltyDue;
    }

    public void setPenaltyDue(String penaltyDue) {
        this.penaltyDue = penaltyDue;
    }

    public String getTotalLoanAmnt() {
        return totalLoanAmnt;
    }

    public void setTotalLoanAmnt(String totalLoanAmnt) {
        this.totalLoanAmnt = totalLoanAmnt;
    }

    public String getTotalAmntPaid() {
        return totalAmntPaid;
    }

    public void setTotalAmntPaid(String totalAmntPaid) {
        this.totalAmntPaid = totalAmntPaid;
    }

    public String getTotalAmntDue() {
        return totalAmntDue;
    }

    public void setTotalAmntDue(String totalAmntDue) {
        this.totalAmntDue = totalAmntDue;
    }

}

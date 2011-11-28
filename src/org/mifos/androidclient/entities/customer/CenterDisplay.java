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

import java.io.Serializable;

public class CenterDisplay extends CustomerDisplay implements Serializable {

    private String mfiJoiningDate;
    private String createdDate;
    private Integer versionNo;
    private Integer customerLevelId;
    private Integer loanOfficerId;
    private String loanOfficerName;

    public String getMfiJoiningDate() {
        return mfiJoiningDate;
    }

    public void setMfiJoiningDate(String mfiJoiningDate) {
        this.mfiJoiningDate = mfiJoiningDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(Integer versionNo) {
        this.versionNo = versionNo;
    }

    public Integer getCustomerLevelId() {
        return customerLevelId;
    }

    public void setCustomerLevelId(Integer customerLevelId) {
        this.customerLevelId = customerLevelId;
    }

    public Integer getLoanOfficerId() {
        return loanOfficerId;
    }

    public void setLoanOfficerId(Integer loanOfficerId) {
        this.loanOfficerId = loanOfficerId;
    }

    public String getLoanOfficerName() {
        return loanOfficerName;
    }

    public void setLoanOfficerName(String loanOfficerName) {
        this.loanOfficerName = loanOfficerName;
    }

}

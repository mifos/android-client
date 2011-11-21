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

public class AccountBasicInformation extends BaseEntity implements Serializable {

    public static final String BUNDLE_KEY = AccountBasicInformation.class.getSimpleName();

    private String globalAccountNum;
    private String prdOfferingName;
    private String accountStateName;
    private Integer accountStateId;

    public String getGlobalAccountNum() {
        return globalAccountNum;
    }

    public void setGlobalAccountNum(String globalAccountNum) {
        this.globalAccountNum = globalAccountNum;
    }

    public String getPrdOfferingName() {
        return prdOfferingName;
    }

    public void setPrdOfferingName(String prdOfferingName) {
        this.prdOfferingName = prdOfferingName;
    }

    public String getAccountStateName() {
        return accountStateName;
    }

    public void setAccountStateName(String accountStateName) {
        this.accountStateName = accountStateName;
    }

    public Integer getAccountStateId() {
        return accountStateId;
    }

    public void setAccountStateId(Integer accountStateId) {
        this.accountStateId = accountStateId;
    }

}

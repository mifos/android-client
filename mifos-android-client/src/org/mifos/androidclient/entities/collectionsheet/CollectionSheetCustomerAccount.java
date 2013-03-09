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

package org.mifos.androidclient.entities.collectionsheet;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.text.DecimalFormat;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionSheetCustomerAccount implements Serializable {

    private Integer accountId;
    private Short currencyId;
    private Double totalCustomerAccountCollectionFee;

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Short getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Short currencyId) {
        this.currencyId = currencyId;
    }

    public Double getTotalCustomerAccountCollectionFee() {
        DecimalFormat df = new DecimalFormat("#.##");
        return Double.valueOf(df.format(totalCustomerAccountCollectionFee));
    }

    public void setTotalCustomerAccountCollectionFee(Double totalCustomerAccountCollectionFee) {
        this.totalCustomerAccountCollectionFee = totalCustomerAccountCollectionFee;
    }

}

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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.mifos.androidclient.util.listadapters.SimpleListItem;
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanAccountBasicInformation extends AccountBasicInformation implements Serializable, SimpleListItem {

    private String outstandingBalance;
    private String totalAmountDue;
    private String totalAmountInArrears;
    private String capitalExposure;
    
    public String getOutstandingBalance() {
        return outstandingBalance;
    }

    public void setOutstandingBalance(String outstandingBalance) {
        this.outstandingBalance = outstandingBalance;
    }

    public String getTotalAmountDue() {
        return totalAmountDue;
    }

    public void setTotalAmountDue(String totalAmountDue) {
        this.totalAmountDue = totalAmountDue;
    }

	public String getTotalAmountInArrears() {
		return totalAmountInArrears;
	}

	public void setTotalAmountInArrears(String totalAmountInArrears) {
		this.totalAmountInArrears = totalAmountInArrears;
	}

	@Override
	public String getListLabel() {
		return "unused";
	}

	@Override
	public int getItemIdentifier() {
		return 0;
	}

    public String getCapitalExposure() {
        return capitalExposure;
    }

    public void setCapitalExposure(String capitalExposure) {
        this.capitalExposure = capitalExposure;
    }
}

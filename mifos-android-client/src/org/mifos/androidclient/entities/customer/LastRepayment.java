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
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.mifos.androidclient.entities.simple.Customer;
import org.mifos.androidclient.util.listadapters.SimpleListItem;
@JsonIgnoreProperties(ignoreUnknown = true)
public class LastRepayment implements Serializable, SimpleListItem {

	public static final String BUNDLE_KEY = "last-repayment";
	
	private LoanAccountBasicInformation loanAccount;
	private Customer customer;
    private Date lastInstallmentDate;
    private boolean group;
	
	public LoanAccountBasicInformation getLoanAccount() {
		return loanAccount;
	}
	
	public void setLoanAccount(LoanAccountBasicInformation loanAccount) {
		this.loanAccount = loanAccount;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getLastInstallmentDate() {
		return lastInstallmentDate;
	}

	public void setLastInstallmentDate(Date lastInstallmentDate) {
		this.lastInstallmentDate = lastInstallmentDate;
	}

	public boolean isGroup() {
		return group;
	}

	public void setGroup(boolean group) {
		this.group = group;
	}

	@Override
	public String getListLabel() {
		return customer.getDisplayName();
	}

	@Override
	public int getItemIdentifier() {
		return 0;
	}
	
}

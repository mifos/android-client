package org.mifos.androidclient.entities.customer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.mifos.androidclient.entities.simple.AbstractCustomer;
import org.mifos.androidclient.util.listadapters.SimpleListItem;

/**
 * A simple bean class representing a Mifos customer in a basic form.
 * Used on list - provides a display name and an identifier which can
 * be used to fetch more detailed data.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OverdueCustomer extends AbstractCustomer implements Serializable, SimpleListItem {

	private String phoneNumber;
	private String address;
	private List<LoanAccountBasicInformation> overdueLoans;
	private boolean group;
    private String totalCapitalOutstanding;

	public List<LoanAccountBasicInformation> getOverdueLoans() {
		return overdueLoans;
	}

	public void setOverdueLoans(List<LoanAccountBasicInformation> overdueLoans) {
		this.overdueLoans = overdueLoans;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

    public boolean isGroup() {
		return group;
	}

	public void setGroup(boolean group) {
		this.group = group;
	}

	@Override
	public String getListLabel() {
		return this.getDisplayName();
	}

	@Override
	public int getItemIdentifier() {
		return this.getId();
	}

    public String getTotalCapitalOutstanding() {
        return totalCapitalOutstanding;
    }

    public void setTotalCapitalOutstanding(String totalOutstandingCapital) {
        this.totalCapitalOutstanding = totalOutstandingCapital;
    }
}

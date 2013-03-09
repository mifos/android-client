package org.mifos.androidclient.entities.collectionsheet;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveCollectionSheetCustomerLoan implements Serializable{
    private Integer accountId;
    private Short currencyId;
    private Double totalLoanPayment;
    private Double totalDisbursement;

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

    public Double getTotalLoanPayment() {
        return totalLoanPayment;
    }

    public void setTotalLoanPayment(Double totalLoanPayment) {
        this.totalLoanPayment = totalLoanPayment;
    }

    public Double getTotalDisbursement() {
        return totalDisbursement;
    }

    public void setTotalDisbursement(Double totalDisbursement) {
        this.totalDisbursement = totalDisbursement;
    }
}

package org.mifos.androidclient.entities.collectionsheet;

import java.io.Serializable;


public class SaveCollectionSheetCustomerAccount implements Serializable{

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
        return totalCustomerAccountCollectionFee;
    }

    public void setTotalCustomerAccountCollectionFee(Double totalCustomerAccountCollectionFee) {
        this.totalCustomerAccountCollectionFee = totalCustomerAccountCollectionFee;
    }
}

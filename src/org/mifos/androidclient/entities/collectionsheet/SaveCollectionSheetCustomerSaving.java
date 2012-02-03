package org.mifos.androidclient.entities.collectionsheet;

import java.io.Serializable;
import java.math.BigDecimal;


public class SaveCollectionSheetCustomerSaving implements Serializable{
    private Integer accountId;
    private Short currencyId;
    private Double totalDeposit;
    private Double totalWithdrawal;

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

    public Double getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(Double totalDeposit) {
        this.totalDeposit = totalDeposit;
    }

    public Double getTotalWithdrawal() {
        return totalWithdrawal;
    }

    public void setTotalWithdrawal(Double totalWithdrawal) {
        this.totalWithdrawal = totalWithdrawal;
    }
}

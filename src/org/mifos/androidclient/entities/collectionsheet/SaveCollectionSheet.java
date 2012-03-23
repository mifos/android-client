package org.mifos.androidclient.entities.collectionsheet;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveCollectionSheet implements Serializable {

    private List<SaveCollectionSheetCustomer> saveCollectionSheetCustomers;
    private Short paymentType;
    private Date transactionDate;
    private String receiptId;
    private Date receiptDate;
    private Integer userId;

    public List<SaveCollectionSheetCustomer> getSaveCollectionSheetCustomers() {
        return saveCollectionSheetCustomers;
    }

    public void setSaveCollectionSheetCustomers(List<SaveCollectionSheetCustomer> saveCollectionSheetCustomers) {
        this.saveCollectionSheetCustomers = saveCollectionSheetCustomers;
    }

    public Short getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Short paymentType) {
        this.paymentType = paymentType;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

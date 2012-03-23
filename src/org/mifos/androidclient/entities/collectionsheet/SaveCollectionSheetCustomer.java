package org.mifos.androidclient.entities.collectionsheet;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveCollectionSheetCustomer implements Serializable{
    private Integer customerId;
    private Integer parentCustomerId;
    private Short attendanceId;
    private SaveCollectionSheetCustomerAccount saveCollectionSheetCustomerAccount;
    private List<SaveCollectionSheetCustomerLoan> saveCollectionSheetCustomerLoans;
    private List<SaveCollectionSheetCustomerSaving> saveCollectionSheetCustomerSavings;
    private List<SaveCollectionSheetCustomerSaving> saveCollectionSheetCustomerIndividualSavings;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getParentCustomerId() {
        return parentCustomerId;
    }

    public void setParentCustomerId(Integer parentCustomerId) {
        this.parentCustomerId = parentCustomerId;
    }

    public Short getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Short attendanceId) {
        this.attendanceId = attendanceId;
    }

    public SaveCollectionSheetCustomerAccount getSaveCollectionSheetCustomerAccount() {
        return saveCollectionSheetCustomerAccount;
    }

    public void setSaveCollectionSheetCustomerAccount(SaveCollectionSheetCustomerAccount saveCollectionSheetCustomerAccount) {
        this.saveCollectionSheetCustomerAccount = saveCollectionSheetCustomerAccount;
    }

    public List<SaveCollectionSheetCustomerLoan> getSaveCollectionSheetCustomerLoans() {
        return saveCollectionSheetCustomerLoans;
    }

    public void setSaveCollectionSheetCustomerLoans(List<SaveCollectionSheetCustomerLoan> saveCollectionSheetCustomerLoans) {
        this.saveCollectionSheetCustomerLoans = saveCollectionSheetCustomerLoans;
    }

    public List<SaveCollectionSheetCustomerSaving> getSaveCollectionSheetCustomerSavings() {
        return saveCollectionSheetCustomerSavings;
    }

    public void setSaveCollectionSheetCustomerSavings(List<SaveCollectionSheetCustomerSaving> saveCollectionSheetCustomerSavings) {
        this.saveCollectionSheetCustomerSavings = saveCollectionSheetCustomerSavings;
    }

    public List<SaveCollectionSheetCustomerSaving> getSaveCollectionSheetCustomerIndividualSavings() {
        return saveCollectionSheetCustomerIndividualSavings;
    }

    public void setSaveCollectionSheetCustomerIndividualSavings(List<SaveCollectionSheetCustomerSaving> saveCollectionSheetCustomerIndividualSavings) {
        this.saveCollectionSheetCustomerIndividualSavings = saveCollectionSheetCustomerIndividualSavings;
    }
}

package org.mifos.androidclient.entities.account;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.mifos.androidclient.util.listadapters.SimpleListItem;
import org.mifos.androidclient.util.ui.DateUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepaymentScheduleItem implements SimpleListItem, Serializable{

    public static final String BUNDLE_KEY = RepaymentScheduleItem.class.getSimpleName();

    private Short installmentNumber;
    private Date dueDate;
    private Short paymentStatus;
    private Date paymentDate;
    private String principal;
    private String principalPaid;
    private String interest;
    private String interestPaid;
    private String penalty;
    private String penaltyPaid;
    private String extraInterest;
    private String extraInterestPaid;
    private String miscFee;
    private String miscFeePaid;
    private String miscPenalty;
    private String miscPenaltyPaid;
    private List<AccountFeeSchedule> feesActionDetails;

    public Short getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(Short installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public String getDueDate() {
        return DateUtils.format(dueDate);
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Short getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Short paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPrincipalPaid() {
        return principalPaid;
    }

    public void setPrincipalPaid(String principalPaid) {
        this.principalPaid = principalPaid;
    }

    public String getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(String interestPaid) {
        this.interestPaid = interestPaid;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getPenaltyPaid() {
        return penaltyPaid;
    }

    public void setPenaltyPaid(String penaltyPaid) {
        this.penaltyPaid = penaltyPaid;
    }

    public String getPenalty() {
        return penalty;
    }

    public void setPenalty(String penalty) {
        this.penalty = penalty;
    }

    public String getExtraInterest() {
        return extraInterest;
    }

    public void setExtraInterest(String extraInterest) {
        this.extraInterest = extraInterest;
    }

    public String getMiscFee() {
        return miscFee;
    }

    public void setMiscFee(String miscFee) {
        this.miscFee = miscFee;
    }

    public String getExtraInterestPaid() {
        return extraInterestPaid;
    }

    public void setExtraInterestPaid(String extraInterestPaid) {
        this.extraInterestPaid = extraInterestPaid;
    }

    public String getMiscFeePaid() {
        return miscFeePaid;
    }

    public void setMiscFeePaid(String miscFeePaid) {
        this.miscFeePaid = miscFeePaid;
    }

    public String getMiscPenalty() {
        return miscPenalty;
    }

    public void setMiscPenalty(String miscPenalty) {
        this.miscPenalty = miscPenalty;
    }

    public String getMiscPenaltyPaid() {
        return miscPenaltyPaid;
    }

    public void setMiscPenaltyPaid(String miscPenaltyPaid) {
        this.miscPenaltyPaid = miscPenaltyPaid;
    }

    public List<AccountFeeSchedule> getFeesActionDetails() {
        return feesActionDetails;
    }

    public void setFeesActionDetails(List<AccountFeeSchedule> feesActionDetails) {
        this.feesActionDetails = feesActionDetails;
    }

    @Override
    public String getListLabel() {
        if (paymentDate != null){
            return DateUtils.format(dueDate) + "   " + DateUtils.format(paymentDate) + "     " + principal;
        } else return DateUtils.format(dueDate) + "   Not paid yet     " + principal;
    }

    @Override
    public int getItemIdentifier() {
        return 0;
    }

}

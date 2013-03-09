package org.mifos.androidclient.entities.account;

import android.content.Context;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.mifos.androidclient.util.listadapters.SimpleListItem;
import org.mifos.androidclient.util.ui.DateUtils;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import static java.lang.Math.round;


@JsonIgnoreProperties(ignoreUnknown = true)
public class RepaymentScheduleItem implements SimpleListItem, Serializable{

    public static final String BUNDLE_KEY = RepaymentScheduleItem.class.getSimpleName();

    private Short installmentNumber;
    private Date dueDate;
    private Short paymentStatus;
    private Date paymentDate;
    private Double principal;
    private Double principalPaid;
    private Double interest;
    private Double interestPaid;
    private String penalty;
    private String penaltyPaid;
    private String extraInterest;
    private String extraInterestPaid;
    private Double miscFee;
    private Double miscFeePaid;
    private String miscPenalty;
    private String miscPenaltyPaid;
    private List<AccountFeeSchedule> feesActionDetails;

    public Short getInstallmentNumber() {
        return installmentNumber;
    }

    public void setInstallmentNumber(Short installmentNumber) {
        this.installmentNumber = installmentNumber;
    }

    public Date getDueDate() {
        return dueDate;
    }
    
    public String getDueDateFormated() {
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

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getPrincipalPaid() {
        return principalPaid;
    }

    public void setPrincipalPaid(Double principalPaid) {
        this.principalPaid = principalPaid;
    }

    public Double getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(Double interestPaid) {
        this.interestPaid = interestPaid;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
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

    public Double getMiscFee() {
        return miscFee;
    }

    public void setMiscFee(Double miscFee) {
        this.miscFee = miscFee;
    }

    public String getExtraInterestPaid() {
        return extraInterestPaid;
    }

    public void setExtraInterestPaid(String extraInterestPaid) {
        this.extraInterestPaid = extraInterestPaid;
    }

    public Double getMiscFeePaid() {
        return miscFeePaid;
    }

    public void setMiscFeePaid(Double miscFeePaid) {
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
        double feeAmount = 0;
        DecimalFormat df= new DecimalFormat("#.##");
        double feePaid = 0;
        double total = 0;
        for(int i= 0; i < feesActionDetails.size(); i++){
            feeAmount += feesActionDetails.get(i).getFeeAmount();
            feePaid += feesActionDetails.get(i).getFeeAmountPaid();
        }
        if(paymentStatus == 1){
        total = feePaid + miscFeePaid + principalPaid + interestPaid;
        }
        else total = (feeAmount - feePaid) + (miscFee - miscFeePaid)  + (principal - principalPaid) + (interest - interestPaid);

        Date date = paymentDate != null ? paymentDate : new Date();
        long diff = date.getTime() - dueDate.getTime();
        long days = diff / (86400000);
        if (days < 0) days = 0;
        
        if (paymentDate != null && paymentStatus == 1){
            return DateUtils.format(dueDate) + "   " + DateUtils.format(paymentDate) + "       " + days + "     " + Double.valueOf(df.format(round(total)));
        }else if(paymentDate != null && paymentStatus != 1){
            return DateUtils.format(dueDate) + "  Partially paid    " + days + "     " + Double.valueOf(df.format(round(total)));
        }
        else return DateUtils.format(dueDate) + "   Not paid yet     " + days + "     " + Double.valueOf(df.format(round(total)));
    }

    @Override
    public int getItemIdentifier() {
        return 0;
    }

}

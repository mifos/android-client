package org.mifos.androidclient.entities.simple;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Fee implements Serializable {

    public final static String BUNDLE_KEY = Fee.class.getSimpleName();

    public static final String KEY_ID = "feeId";
    public static final String KEY_NAME = "feeName";
    public static final String KEY_AMOUNT_OR_RATE = "amountOrRate";
    public static final String KEY_FORMULA = "formula";
    public static final String KEY_PERIODICITY = "periodicity";
    public static final String KEY_PAYMENT_TYPE = "paymentType";
    public static final String KEY_IS_RATE_TYPE = "isRateType";

    private String feeId;
    private String feeName;
    private String amountOrRate;
    private String formula;
    private String periodicity;
    private String paymentType;
    private String isRateType;

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }

    public String getAmountOrRate() {
        return amountOrRate;
    }

    public void setAmountOrRate(String amountOrRate) {
        this.amountOrRate = amountOrRate;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(String periodicity) {
        this.periodicity = periodicity;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getRateType() {
        return isRateType;
    }

    public void setRateType(String rateType) {
        isRateType = rateType;
    }

}

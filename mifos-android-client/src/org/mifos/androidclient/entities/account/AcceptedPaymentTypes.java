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

package org.mifos.androidclient.entities.account;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AcceptedPaymentTypes implements Serializable {

    public static final String ACCEPTED_DEPOSIT_PAYMENT_TYPES_BUNDLE_KEY = AcceptedPaymentTypes.class.getSimpleName() + "-deposit";
    public static final String ACCEPTED_WITHDRAWAL_PAYMENT_TYPES_BUNDLE_KEY = AcceptedPaymentTypes.class.getSimpleName() + "-withdrawal";
    public static final String ACCEPTED_DISBURSEMENT_PAYMENT_TYPES_BUNDLE_KEY = AcceptedPaymentTypes.class.getSimpleName() + "-disbursement";
    public static final String ACCEPTED_FEE_PAYMENT_TYPES_BUNDLE_KEY = AcceptedPaymentTypes.class.getSimpleName() + "-fee";
    public static final String ACCEPTED_LOAN_PAYMENT_TYPES_BUNDLE_KEY = AcceptedPaymentTypes.class.getSimpleName() + "-loan";
    public static final String ACCEPTED_REPAYMENT_PAYMENT_TYPES_BUNDLE_KEY = AcceptedPaymentTypes.class.getSimpleName() + "-repayment";

    private List<PaymentType> inFeeList;
    private List<PaymentType> outFeeList;
    private List<PaymentType> inDisbursementList;
    private List<PaymentType> outDisbursementList;
    private List<PaymentType> inRepaymentList;
    private List<PaymentType> outRepaymentList;
    private List<PaymentType> inWithdrawalList;
    private List<PaymentType> outWithdrawalList;
    private List<PaymentType> inDepositList;
    private List<PaymentType> outDepositList;

    public Map<String, Integer> asMap(List<PaymentType> paymentTypes) {
        Map<String, Integer> typesAsMap = new HashMap<String, Integer>();
        for (PaymentType paymentType : paymentTypes) {
            typesAsMap.put(paymentType.getName(), paymentType.getId().intValue());
        }
        return typesAsMap;
    }

    public Map<String, Integer>  allTypes() {
        Map<String, Integer> allTypesAsMap = new HashMap<String, Integer>();
            List<List<PaymentType>> typesList = new ArrayList<List<PaymentType>>();
            typesList.add(inFeeList);
            typesList.add(outFeeList);
            typesList.add(inDisbursementList);
            typesList.add(outDisbursementList);
            typesList.add(inRepaymentList);
            typesList.add(outRepaymentList);
            typesList.add(inWithdrawalList);
            typesList.add(outWithdrawalList);
            typesList.add(inDepositList);
            typesList.add(outDepositList);
            for(List<PaymentType> lo : typesList){
                for (PaymentType type : lo){
                     if(!allTypesAsMap.containsValue(lo.contains(type.getName()))) {
                        allTypesAsMap.put(type.getName(), type.getId().intValue());
                     }
                }
            }
        return allTypesAsMap;
    }


    public List<PaymentType> getInFeeList() {
        return inFeeList;
    }

    public void setInFeeList(List<PaymentType> inFeeList) {
        this.inFeeList = inFeeList;
    }

    public List<PaymentType> getOutFeeList() {
        return outFeeList;
    }

    public void setOutFeeList(List<PaymentType> outFeeList) {
        this.outFeeList = outFeeList;
    }

    public List<PaymentType> getInDisbursementList() {
        return inDisbursementList;
    }

    public void setInDisbursementList(List<PaymentType> inDisbursementList) {
        this.inDisbursementList = inDisbursementList;
    }

    public List<PaymentType> getOutDisbursementList() {
        return outDisbursementList;
    }

    public void setOutDisbursementList(List<PaymentType> outDisbursementList) {
        this.outDisbursementList = outDisbursementList;
    }

    public List<PaymentType> getInRepaymentList() {
        return inRepaymentList;
    }

    public void setInRepaymentList(List<PaymentType> inRepaymentList) {
        this.inRepaymentList = inRepaymentList;
    }

    public List<PaymentType> getOutRepaymentList() {
        return outRepaymentList;
    }

    public void setOutRepaymentList(List<PaymentType> outRepaymentList) {
        this.outRepaymentList = outRepaymentList;
    }

    public List<PaymentType> getInWithdrawalList() {
        return inWithdrawalList;
    }

    public void setInWithdrawalList(List<PaymentType> inWithdrawalList) {
        this.inWithdrawalList = inWithdrawalList;
    }

    public List<PaymentType> getOutWithdrawalList() {
        return outWithdrawalList;
    }

    public void setOutWithdrawalList(List<PaymentType> outWithdrawalList) {
        this.outWithdrawalList = outWithdrawalList;
    }

    public List<PaymentType> getInDepositList() {
        return inDepositList;
    }

    public void setInDepositList(List<PaymentType> inDepositList) {
        this.inDepositList = inDepositList;
    }

    public List<PaymentType> getOutDepositList() {
        return outDepositList;
    }

    public void setOutDepositList(List<PaymentType> outDepositList) {
        this.outDepositList = outDepositList;
    }

}

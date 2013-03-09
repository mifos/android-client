package org.mifos.androidclient.main;


import java.text.DecimalFormat;
import java.util.Date;

import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.RepaymentScheduleItem;
import org.mifos.androidclient.templates.MifosActivity;
import org.mifos.androidclient.util.ui.DateUtils;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

public class RepaymentScheduleActivity extends MifosActivity{

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.repayment_schedule_item);

        RepaymentScheduleItem item = (RepaymentScheduleItem)getIntent().getSerializableExtra(RepaymentScheduleItem.BUNDLE_KEY);
        TextView textView;
        TableLayout tableLayout;
        RelativeLayout relativeLayout;
        DecimalFormat df= new DecimalFormat("#.##");
        double feeAmount = 0;
        double feePaid = 0;
        for(int i=0; i< item.getFeesActionDetails().size(); i++){
            feeAmount += item.getFeesActionDetails().get(i).getFeeAmount();
            feePaid += item.getFeesActionDetails().get(i).getFeeAmountPaid();
        }

        Date date = item.getPaymentDate() != null ? item.getPaymentDate() : new Date();
        long diff = date.getTime() - item.getDueDate().getTime();
        long days = diff / (86400000);
        if (days < 0) days = 0;

        if (item.getPaymentDate() == null){ //not
            relativeLayout = (RelativeLayout)findViewById(R.id.normallyPaid);
            if(relativeLayout.getVisibility() == View.GONE){
            relativeLayout.setVisibility(View.VISIBLE);
            tableLayout = (TableLayout)findViewById(R.id.partiallyPaidTable);
            tableLayout.setVisibility(View.GONE);
            }
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_installmentNumber);
            textView.setText(item.getInstallmentNumber().toString());
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_dueDate);
            textView.setText(item.getDueDateFormated());
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_paymentDate);
            textView.setText(R.string.notPaidYet);
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_daysLate);
            textView.setText(Long.toString(days));
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_principal);
            textView.setText(Double.toString(item.getPrincipal()));
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_interest);
            textView.setText(Double.toString(item.getInterest()));
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_fees);
            textView.setText(Double.toString(feeAmount + item.getMiscFee()));
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_total);
            textView.setText(Double.valueOf(item.getPrincipal() + item.getInterest() + feeAmount).toString());
        }
        else if(item.getPaymentDate() != null && item.getPaymentStatus() != 1){  //partialy
            tableLayout = (TableLayout)findViewById(R.id.partiallyPaidTable);
            if(tableLayout.getVisibility() == View.GONE){
            tableLayout.setVisibility(View.VISIBLE);
            relativeLayout = (RelativeLayout)findViewById(R.id.normallyPaid);
            relativeLayout.setVisibility(View.GONE);
            }

            textView = (TextView)findViewById(R.id.partiallyPaidTable_PrincipalPaid);
            textView.setText(Double.toString(item.getPrincipalPaid()));
            textView = (TextView)findViewById(R.id.partiallyPaidTable_PrincipalDue);
            textView.setText(Double.valueOf(df.format(item.getPrincipal() - item.getPrincipalPaid())).toString());
            textView = (TextView)findViewById(R.id.partiallyPaidTable_interestPaid);
            textView.setText(Double.toString(item.getInterestPaid()));
            textView = (TextView)findViewById(R.id.partiallyPaidTable_interestDue);
            textView.setText(Double.toString(item.getInterest() - item.getInterestPaid()));
            textView = (TextView)findViewById(R.id.partiallyPaidTable_feesPaid);
            textView.setText(Double.toString(feePaid + item.getMiscFeePaid()));
            textView = (TextView)findViewById(R.id.partiallyPaidTable_feesDue);
            textView.setText(Double.toString(feeAmount + item.getMiscFee() - feePaid - item.getMiscFeePaid() ));
            textView = (TextView)findViewById(R.id.partiallyPaidTable_totalPaid);
            textView.setText(Double.valueOf(item.getPrincipalPaid() + item.getInterestPaid() + feePaid).toString());
            textView = (TextView)findViewById(R.id.partiallyPaidTable_totalDue);
            textView.setText(Double.valueOf(df.format(feeAmount + item.getMiscFee() - feePaid - item.getMiscFeePaid() + item.getPrincipal() - item.getPrincipalPaid()
                    + item.getInterest() - item.getInterestPaid())).toString());
            textView = (TextView)findViewById(R.id.partiallyPaidTable_datePaid);
            textView.setText(DateUtils.format(item.getPaymentDate()));
            textView = (TextView)findViewById(R.id.partiallyPaidTable_dateDue);
            textView.setText(item.getDueDateFormated());
            textView = (TextView)findViewById(R.id.partiallyPaidTable_daysLate);
            textView.setText(Long.toString(days));
        }
        else { //paid
            relativeLayout = (RelativeLayout)findViewById(R.id.normallyPaid);
            if(relativeLayout.getVisibility() == View.GONE){
            relativeLayout.setVisibility(View.VISIBLE);
            tableLayout = (TableLayout)findViewById(R.id.partiallyPaidTable);
            tableLayout.setVisibility(View.GONE);
            }
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_installmentNumber);
            textView.setText(item.getInstallmentNumber().toString());
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_dueDate);
            textView.setText(item.getDueDateFormated());
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_paymentDate);
            textView.setText(DateUtils.format(item.getPaymentDate()));
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_daysLate);
            textView.setText(Long.toString(days));
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_principal);
            textView.setText(Double.toString(item.getPrincipalPaid()));
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_interest);
            textView.setText(Double.toString(item.getInterestPaid()));
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_fees);
            textView.setText(Double.toString(feePaid + item.getMiscFeePaid()));
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_total);
            textView.setText(Double.valueOf(item.getPrincipalPaid() + item.getInterestPaid() + feePaid).toString());
        }





    }
}

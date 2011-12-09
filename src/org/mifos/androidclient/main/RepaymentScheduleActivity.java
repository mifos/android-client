package org.mifos.androidclient.main;


import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.AbstractAccountDetails;
import org.mifos.androidclient.entities.account.AccountFeeSchedule;
import org.mifos.androidclient.entities.account.LoanAccountDetails;
import org.mifos.androidclient.entities.account.RepaymentScheduleItem;
import org.mifos.androidclient.templates.MifosActivity;
import org.mifos.androidclient.util.ui.DateUtils;

import static java.lang.Math.rint;

public class RepaymentScheduleActivity extends MifosActivity{

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.repayment_schedule_item);

        RepaymentScheduleItem item = (RepaymentScheduleItem)getIntent().getSerializableExtra(RepaymentScheduleItem.BUNDLE_KEY);
        TextView textView;
        TableLayout tableLayout;
        LinearLayout linearLayout;
        double feeAmount = 0;
        double feePaid = 0;
        for(int i=0; i< item.getFeesActionDetails().size(); i++){
            feeAmount += item.getFeesActionDetails().get(i).getFeeAmount();
            feePaid += item.getFeesActionDetails().get(i).getFeeAmountPaid();
        }


        if (item.getPaymentDate() == null){ //not
            linearLayout = (LinearLayout)findViewById(R.id.normallyPaid);
            if(linearLayout.getVisibility() == View.GONE){
            linearLayout.setVisibility(View.VISIBLE);
            tableLayout = (TableLayout)findViewById(R.id.partiallyPaidTable);
            tableLayout.setVisibility(View.GONE);
            }
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_installmentNumber);
            textView.setText(item.getInstallmentNumber().toString());
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_dueDate);
            textView.setText(item.getDueDate());
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_paymentDate);
            textView.setText(R.string.notPaidYet);
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_principal);
            textView.setText(item.getPrincipal());
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_interest);
            textView.setText(item.getInterest());
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_fees);
            textView.setText(Double.toString(feeAmount));
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_total);
            textView.setText(Double.valueOf(Double.parseDouble(item.getPrincipal()) + Double.parseDouble(item.getInterest()) + feeAmount).toString());
        }
        else if(item.getPaymentDate() != null && item.getPaymentStatus() != 1){  //partialy
            tableLayout = (TableLayout)findViewById(R.id.partiallyPaidTable);
            if(tableLayout.getVisibility() == View.GONE){
            tableLayout.setVisibility(View.VISIBLE);
            linearLayout = (LinearLayout)findViewById(R.id.normallyPaid);
            linearLayout.setVisibility(View.GONE);
            }
            textView = (TextView)findViewById(R.id.partiallyPaidTable_PrincipalPaid);
            textView.setText(item.getPrincipalPaid());
            textView = (TextView)findViewById(R.id.partiallyPaidTable_PrincipalDue);
            textView.setText(Double.toString(rint(Double.parseDouble(item.getPrincipal()) - Double.parseDouble(item.getPrincipalPaid()))));
            textView = (TextView)findViewById(R.id.partiallyPaidTable_interestPaid);
            textView.setText(item.getInterestPaid());
            textView = (TextView)findViewById(R.id.partiallyPaidTable_interestDue);
            textView.setText(Double.toString(Double.parseDouble(item.getInterest()) - Double.parseDouble(item.getInterestPaid())));
            textView = (TextView)findViewById(R.id.partiallyPaidTable_feesPaid);
            textView.setText(Double.toString(feePaid));
            textView = (TextView)findViewById(R.id.partiallyPaidTable_feesDue);
            textView.setText(Double.toString(feeAmount - feePaid));
            textView = (TextView)findViewById(R.id.partiallyPaidTable_totalPaid);
            textView.setText(Double.valueOf(Double.parseDouble(item.getPrincipalPaid()) + Double.parseDouble(item.getInterestPaid()) + feePaid).toString());
            textView = (TextView)findViewById(R.id.partiallyPaidTable_totalDue);
            textView.setText(Double.toString(rint(feeAmount - feePaid + Double.parseDouble(item.getPrincipal()) - Double.parseDouble(item.getPrincipalPaid())
            + Double.parseDouble(item.getInterest()) - Double.parseDouble(item.getInterestPaid()))));
            textView = (TextView)findViewById(R.id.partiallyPaidTable_datePaid);
            textView.setText(DateUtils.format(item.getPaymentDate()));
            textView = (TextView)findViewById(R.id.partiallyPaidTable_dateDue);
            textView.setText(item.getDueDate());
        }
        else { //paid
            linearLayout = (LinearLayout)findViewById(R.id.normallyPaid);
            if(linearLayout.getVisibility() == View.GONE){
            linearLayout.setVisibility(View.VISIBLE);
            tableLayout = (TableLayout)findViewById(R.id.partiallyPaidTable);
            tableLayout.setVisibility(View.GONE);
            }
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_installmentNumber);
            textView.setText(item.getInstallmentNumber().toString());
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_dueDate);
            textView.setText(item.getDueDate());
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_paymentDate);
            textView.setText(DateUtils.format(item.getPaymentDate()));
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_principal);
            textView.setText(item.getPrincipalPaid());
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_interest);
            textView.setText(item.getInterestPaid());
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_fees);
            textView.setText(Double.toString(feePaid));
            textView = (TextView)findViewById(R.id.repaymentScheduleItem_total);
            textView.setText(Double.valueOf(Double.parseDouble(item.getPrincipalPaid()) + Double.parseDouble(item.getInterestPaid()) + feePaid).toString());
        }





    }
}

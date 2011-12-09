package org.mifos.androidclient.main;


import android.os.Bundle;
import android.widget.TextView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.AccountFeeSchedule;
import org.mifos.androidclient.entities.account.RepaymentScheduleItem;
import org.mifos.androidclient.templates.MifosActivity;
import org.mifos.androidclient.util.ui.DateUtils;

public class RepaymentScheduleActivity extends MifosActivity{

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.repayment_schedule_item);

        RepaymentScheduleItem item = (RepaymentScheduleItem)getIntent().getSerializableExtra(RepaymentScheduleItem.BUNDLE_KEY);

        TextView textView;
        textView = (TextView)findViewById(R.id.repaymentScheduleItem_installmentNumber);
        textView.setText(item.getInstallmentNumber().toString());
        textView = (TextView)findViewById(R.id.repaymentScheduleItem_dueDate);
        textView.setText(item.getDueDate());
        textView = (TextView)findViewById(R.id.repaymentScheduleItem_paymentDate);
        if (item.getPaymentDate() == null){
            textView.setText(R.string.notPaidYet);
        } else textView.setText(DateUtils.format(item.getPaymentDate()));
        textView = (TextView)findViewById(R.id.repaymentScheduleItem_principal);
        textView.setText(item.getPrincipal());
        textView = (TextView)findViewById(R.id.repaymentScheduleItem_interest);
        textView.setText(item.getInterest());
        textView = (TextView)findViewById(R.id.repaymentScheduleItem_fees);
        double total = 0;
        for(int i=0; i< item.getFeesActionDetails().size(); i++){
            total += Double.parseDouble(item.getFeesActionDetails().get(i).getFeeAmount());
        }
        textView.setText(Double.toString(total));
        textView = (TextView)findViewById(R.id.repaymentScheduleItem_total);
        textView.setText(Double.valueOf(Double.parseDouble(item.getPrincipal()) + Double.parseDouble(item.getInterest()) + total).toString());


    }
}

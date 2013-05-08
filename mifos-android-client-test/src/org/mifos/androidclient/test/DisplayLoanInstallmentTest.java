package org.mifos.androidclient.test;

import java.util.ArrayList;
import java.util.Date;

import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.AccountFeeSchedule;
import org.mifos.androidclient.entities.account.RepaymentScheduleItem;
import org.mifos.androidclient.main.RepaymentScheduleActivity;
import org.mifos.androidclient.test.utils.TextViewHelper;
import org.mifos.androidclient.util.ui.DateUtils;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;

public class DisplayLoanInstallmentTest extends ActivityInstrumentationTestCase2<RepaymentScheduleActivity> {

	@SuppressWarnings("deprecation")
	private static final Date DUE_DATE = new Date(2011, 02, 03);
	private static final short INSTALLMENT_NO = 2;
	private static final double PRINCIPAL = 100;
	private static final double INTEREST = 10;
	private static final double MISC_FEE = 2;
	private static final String EXPECTED_DAYS_LATE = "0";
	private static final String EXPECTED_TOTAL = Double.valueOf(PRINCIPAL + INTEREST).toString();
	
	public DisplayLoanInstallmentTest() {
		super(RepaymentScheduleActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@MediumTest
	public void testDisplayLoanInstallment() {

		RepaymentScheduleItem item = new RepaymentScheduleItem();
		item.setFeesActionDetails(new ArrayList<AccountFeeSchedule>());
		item.setDueDate(DUE_DATE);
		item.setInstallmentNumber(INSTALLMENT_NO);
		item.setPrincipal(PRINCIPAL);
		item.setInterest(INTEREST);
		item.setMiscFee(MISC_FEE);
		
		Intent intent = new Intent();
		intent.putExtra(RepaymentScheduleItem.BUNDLE_KEY, item);
		setActivityIntent(intent);

		final RepaymentScheduleActivity activity = getActivity();
		
		TextViewHelper textViewHelper = new TextViewHelper(activity);
		textViewHelper.verifyText(R.id.repaymentScheduleItem_installmentNumber, Integer.toString(INSTALLMENT_NO));
		textViewHelper.verifyText(R.id.repaymentScheduleItem_dueDate, DateUtils.format(DUE_DATE));
		textViewHelper.verifyText(R.id.repaymentScheduleItem_paymentDate, activity.getResources().getString(org.mifos.androidclient.R.string.notPaidYet));
		textViewHelper.verifyText(R.id.repaymentScheduleItem_daysLate, EXPECTED_DAYS_LATE);
		textViewHelper.verifyText(R.id.repaymentScheduleItem_principal, Double.toString(PRINCIPAL));
		textViewHelper.verifyText(R.id.repaymentScheduleItem_interest, Double.toString(INTEREST));
		textViewHelper.verifyText(R.id.repaymentScheduleItem_fees, Double.toString(MISC_FEE));
		textViewHelper.verifyText(R.id.repaymentScheduleItem_total, EXPECTED_TOTAL);
	}
	
}

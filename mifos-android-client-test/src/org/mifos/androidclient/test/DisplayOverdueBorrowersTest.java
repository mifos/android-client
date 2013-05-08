package org.mifos.androidclient.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.customer.LoanAccountBasicInformation;
import org.mifos.androidclient.entities.customer.OverdueCustomer;
import org.mifos.androidclient.main.OverdueBorrowersListActivity;
import org.mifos.androidclient.net.RestConnector;
import org.mifos.androidclient.net.services.CustomerService;
import org.mifos.androidclient.test.utils.AsyncTaskHelper;
import org.mifos.androidclient.test.utils.ExpandableListViewHelper;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.ExpandableListView;

public class DisplayOverdueBorrowersTest 
		extends ActivityInstrumentationTestCase2<OverdueBorrowersListActivity> {

	private static final String CLIENT_GLOBAL_CUST_NUM = "global_cust_num";
	private static final String CLIENT_STATUS = "status";
	private static final String CLIENT_DISPLAY_NAME = "display_name";
	private static final int CLIENT_ID = 2;
	
	private static final String LOAN_GLOBAL_NUM = "2";
	private static final String LOAN_PRD_OFFERING_NAME = "loan_name";
	private static final String LOAN_STATUS = "Active in Bad Standing";
	private static final String LOAN_TOTAL_AMOUNT_DUE = "200";
	private static final String LOAN_TOTAL_AMOUNT_IN_ARREARS = "100";
	
	public DisplayOverdueBorrowersTest() {
		super(OverdueBorrowersListActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@MediumTest
	public void testDisplayClientInformation() {

		RestConnector.getInstance().setLoggedIn(true);       
		OverdueBorrowersListActivity activity = getActivity();
		CustomerService customerService = mock(CustomerService.class);

		OverdueCustomer overdueCustomer = new OverdueCustomer();
		overdueCustomer.setDisplayName(CLIENT_DISPLAY_NAME);
		overdueCustomer.setGlobalCustNum(CLIENT_GLOBAL_CUST_NUM);
		overdueCustomer.setId(CLIENT_ID);
		overdueCustomer.setStatus(CLIENT_STATUS);
		
		LoanAccountBasicInformation overdueLoan = new LoanAccountBasicInformation();
		overdueLoan.setGlobalAccountNum(LOAN_GLOBAL_NUM);
		overdueLoan.setPrdOfferingName(LOAN_PRD_OFFERING_NAME);
		overdueLoan.setStatus(LOAN_STATUS);
		overdueLoan.setTotalAmountDue(LOAN_TOTAL_AMOUNT_DUE);
		overdueLoan.setTotalAmountInArrears(LOAN_TOTAL_AMOUNT_IN_ARREARS);
		overdueLoan.setAccountStateId(4);
		
		overdueCustomer.setOverdueLoans(Arrays.asList(new LoanAccountBasicInformation[] {overdueLoan}));

		when(customerService.getLoanOfficersOverdueBorrowers()).thenReturn(
				Arrays.asList(new OverdueCustomer[] {overdueCustomer}));
		
		activity.setCustomerService(customerService);
		
		getInstrumentation().callActivityOnStart(activity);
		getInstrumentation().callActivityOnResume(activity);
		
		new AsyncTaskHelper(activity.getOverdueBorrowersListTask()).waitForFinish();
	
		ExpandableListViewHelper listViewHelper = new ExpandableListViewHelper(activity, getInstrumentation(),
				(ExpandableListView) activity.findViewById(R.id.overdueBorrowersList_list));

		listViewHelper.expandGroup(0);

		listViewHelper.verifyChildsTextView(1, R.id.loanAccountListItem_accountName, LOAN_PRD_OFFERING_NAME + ", " + LOAN_GLOBAL_NUM);
		listViewHelper.verifyChildsTextView(1, R.id.loanAccountListItem_amountDue, LOAN_TOTAL_AMOUNT_DUE);
		listViewHelper.verifyChildsTextView(1, R.id.loanAccountListItem_amountInArrears, LOAN_TOTAL_AMOUNT_IN_ARREARS);
	}
	
}

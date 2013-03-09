package org.mifos.androidclient.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.customer.Address;
import org.mifos.androidclient.entities.customer.ClientDetails;
import org.mifos.androidclient.entities.customer.ClientDisplay;
import org.mifos.androidclient.entities.customer.ClientPerformanceHistory;
import org.mifos.androidclient.entities.customer.CustomerAccountSummary;
import org.mifos.androidclient.entities.customer.LoanCycleCounter;
import org.mifos.androidclient.entities.simple.AbstractCustomer;
import org.mifos.androidclient.entities.simple.Customer;
import org.mifos.androidclient.main.CustomerDetailsActivity;
import org.mifos.androidclient.net.RestConnector;
import org.mifos.androidclient.net.services.CustomerService;
import org.mifos.androidclient.test.utils.AsyncTaskHelper;
import org.mockito.Mockito;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.TextView;

public class DisplayClientInformationTest extends ActivityInstrumentationTestCase2<CustomerDetailsActivity> {

	private static final String PHONE_NUMBER = "phone number";
	private static final String GLOBAL_CUST_NUM = "global_cust_num";
	private static final String DISPLAY_NAME = "DISPLAY_NAME";
	private static final String STATUS_NAME = "status";
	private static final String ACTIVATION_DATE = "2012-01-01";
	private static final String FORMED_BY_DISPLAY_NAME = "FORMED BY";
	private static final String DATE_OF_BIRTH = "2012-29-11";
	private static final String ETHNICITY = "Huurton";
	private static final String EDUCATION_LEVEL = "przedszkole";
	private static final String CITIZENSHIP = "polskie";
	private static final int LOAN_CYCLE_NUM = 2;
	private static final String LAST_LOAN_AMOUNT = "200";
	private static final int ACTIVE_LOANS = 2;
	private static final String PORTFOLIO_AMOUNT = "200";
	private static final String TOTAL_SAVINGS = "200";
	private static final int MEETINGS_ATTENDED = 300;
	private static final int MEETINGS_MISSED = 2;
	private static final String NEXT_DUE_AMOUNT = "200";
	
	public DisplayClientInformationTest() {
		super(CustomerDetailsActivity.class);
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	@MediumTest
	public void testDisplayClientInformation() {

		Intent intent = new Intent(Intent.ACTION_MAIN);
		Customer customer = new Customer();
		customer.setGlobalCustNum(GLOBAL_CUST_NUM);
		intent.putExtra(AbstractCustomer.BUNDLE_KEY, customer);
		setActivityIntent(intent);

		RestConnector.getInstance().setLoggedIn(true);
       
		final CustomerDetailsActivity activity = getActivity();
		
		ClientDisplay clientDisplay = Mockito.mock(ClientDisplay.class);
		Mockito.when(clientDisplay.getDisplayName()).thenReturn(DISPLAY_NAME);
		Mockito.when(clientDisplay.getGlobalCustNum()).thenReturn(GLOBAL_CUST_NUM);
		Mockito.when(clientDisplay.getCustomerStatusName()).thenReturn(STATUS_NAME);
		Mockito.when(clientDisplay.getCustomerActivationDate()).thenReturn(ACTIVATION_DATE);
		Mockito.when(clientDisplay.getCustomerFormedByDisplayName()).thenReturn(FORMED_BY_DISPLAY_NAME);
		Mockito.when(clientDisplay.getDateOfBirth()).thenReturn(DATE_OF_BIRTH);
		Mockito.when(clientDisplay.getEthnicity()).thenReturn(ETHNICITY);
		Mockito.when(clientDisplay.getEducationLevel()).thenReturn(EDUCATION_LEVEL);
		Mockito.when(clientDisplay.getCitizenship()).thenReturn(CITIZENSHIP);
	       
		Address address = Mockito.mock(Address.class);
		Mockito.when(address.getPhoneNumber()).thenReturn(PHONE_NUMBER);

		ClientPerformanceHistory clientPerformanceHistory = Mockito.mock(ClientPerformanceHistory.class);
		Mockito.when(clientPerformanceHistory.getLoanCycleNumber()).thenReturn(LOAN_CYCLE_NUM);
		Mockito.when(clientPerformanceHistory.getLastLoanAmount()).thenReturn(LAST_LOAN_AMOUNT);
		Mockito.when(clientPerformanceHistory.getNoOfActiveLoans()).thenReturn(ACTIVE_LOANS);
		Mockito.when(clientPerformanceHistory.getDelinquentPortfolioAmount()).thenReturn(PORTFOLIO_AMOUNT);
		Mockito.when(clientPerformanceHistory.getTotalSavingsAmount()).thenReturn(TOTAL_SAVINGS);
		Mockito.when(clientPerformanceHistory.getMeetingsAttended()).thenReturn(MEETINGS_ATTENDED);
		Mockito.when(clientPerformanceHistory.getMeetingsMissed()).thenReturn(MEETINGS_MISSED);
		Mockito.when(clientPerformanceHistory.getLoanCycleCounters()).thenReturn(new ArrayList<LoanCycleCounter>());
		
		CustomerAccountSummary customerAccountSummary = Mockito.mock(CustomerAccountSummary.class);
		Mockito.when(customerAccountSummary.getNextDueAmount()).thenReturn(NEXT_DUE_AMOUNT);
        
		ClientDetails clientDetails = Mockito.mock(ClientDetails.class);
		Mockito.when(clientDetails.getAddress()).thenReturn(address);
		Mockito.when(clientDetails.getClientDisplay()).thenReturn(clientDisplay);
		Mockito.when(clientDetails.getClientPerformanceHistory()).thenReturn(clientPerformanceHistory);
		Mockito.when(clientDetails.getCustomerAccountSummary()).thenReturn(customerAccountSummary);

		CustomerService customerService = Mockito.mock(CustomerService.class);
		Mockito.when(customerService.getApplicableFees(Mockito.any(String.class))).thenReturn(new HashMap<String, Map<String, String>>());
		Mockito.when(customerService.getDetailsForEntity(Mockito.any(Customer.class))).thenReturn(clientDetails);
	        
		activity.setCustomerService(customerService);
		getInstrumentation().callActivityOnStart(activity);
		getInstrumentation().callActivityOnResume(activity);
		
		new AsyncTaskHelper(activity.getCustomerDetailsTask()).waitForFinish(10000);
		
		TextView textView = (TextView) activity.findViewById(org.mifos.androidclient.R.id.customerOverview_phoneNumber);
		Assert.assertEquals(PHONE_NUMBER, textView.getText().toString());
		textView = (TextView) activity.findViewById(R.id.customerOverview_name);
		Assert.assertEquals(DISPLAY_NAME, textView.getText().toString());
		textView = (TextView) activity.findViewById(R.id.customerOverview_systemId);
		Assert.assertEquals(GLOBAL_CUST_NUM, textView.getText().toString());
		textView = (TextView) activity.findViewById(R.id.customerOverview_status);
		Assert.assertEquals(STATUS_NAME, textView.getText().toString());
		textView = (TextView) activity.findViewById(R.id.customerOverview_loanCycleNo);
		Assert.assertEquals(Integer.toString(LOAN_CYCLE_NUM), textView.getText().toString());
		textView = (TextView) activity.findViewById(R.id.customerOverview_amountOfLastLoan);
		Assert.assertEquals(LAST_LOAN_AMOUNT, textView.getText().toString());
		textView = (TextView) activity.findViewById(R.id.customerOverview_noOfActiveLoans);
		Assert.assertEquals(Integer.toString(ACTIVE_LOANS), textView.getText().toString());
		textView = (TextView) activity.findViewById(R.id.customerOverview_delinquentPortfolio);
		Assert.assertEquals(PORTFOLIO_AMOUNT, textView.getText().toString());
		textView = (TextView) activity.findViewById(R.id.customerOverview_totalSavings);
		Assert.assertEquals(TOTAL_SAVINGS, textView.getText().toString());
		textView = (TextView) activity.findViewById(R.id.customerOverview_meetingsAttended);
		Assert.assertEquals(Integer.toString(MEETINGS_ATTENDED), textView.getText().toString());
		textView = (TextView) activity.findViewById(R.id.customerOverview_meetingsMissed);
		Assert.assertEquals(Integer.toString(MEETINGS_MISSED), textView.getText().toString());
		
		textView = (TextView) activity.findViewById(R.id.loanAccounts_amountDue);
		Assert.assertEquals(NEXT_DUE_AMOUNT, textView.getText().toString());
	
	   	textView = (TextView) activity.findViewById(R.id.customerAdditional_customerActivation);
		Assert.assertEquals(ACTIVATION_DATE, textView.getText().toString());
	   	textView = (TextView) activity.findViewById(R.id.customerAdditional_RecruitByDisplayName);
		Assert.assertEquals(FORMED_BY_DISPLAY_NAME, textView.getText().toString());
		textView = (TextView) activity.findViewById(R.id.customerAdditional_dateOfBirthDay);
		Assert.assertEquals(DATE_OF_BIRTH, textView.getText().toString());
		textView = (TextView) activity.findViewById(R.id.customerAdditional_ethinicity);
		Assert.assertEquals(ETHNICITY, textView.getText().toString());
		textView = (TextView) activity.findViewById(R.id.customerAdditional_educationLevel);
		Assert.assertEquals(EDUCATION_LEVEL, textView.getText().toString());
		textView = (TextView) activity.findViewById(R.id.customerAdditional_citizenship);
		Assert.assertEquals(CITIZENSHIP, textView.getText().toString());
	}
	
}

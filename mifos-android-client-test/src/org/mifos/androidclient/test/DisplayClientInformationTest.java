package org.mifos.androidclient.test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import org.mifos.androidclient.test.utils.TextViewHelper;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;

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
		System.setProperty("dexmaker.dexcache", getInstrumentation().getTargetContext().getCacheDir().getPath());
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
		
		ClientDisplay clientDisplay = mock(ClientDisplay.class);
		when(clientDisplay.getDisplayName()).thenReturn(DISPLAY_NAME);
		when(clientDisplay.getGlobalCustNum()).thenReturn(GLOBAL_CUST_NUM);
		when(clientDisplay.getCustomerStatusName()).thenReturn(STATUS_NAME);
		when(clientDisplay.getCustomerActivationDate()).thenReturn(ACTIVATION_DATE);
		when(clientDisplay.getCustomerFormedByDisplayName()).thenReturn(FORMED_BY_DISPLAY_NAME);
		when(clientDisplay.getDateOfBirth()).thenReturn(DATE_OF_BIRTH);
		when(clientDisplay.getEthnicity()).thenReturn(ETHNICITY);
		when(clientDisplay.getEducationLevel()).thenReturn(EDUCATION_LEVEL);
		when(clientDisplay.getCitizenship()).thenReturn(CITIZENSHIP);
	       
		Address address = mock(Address.class);
		when(address.getPhoneNumber()).thenReturn(PHONE_NUMBER);

		ClientPerformanceHistory clientPerformanceHistory = mock(ClientPerformanceHistory.class);
		when(clientPerformanceHistory.getLoanCycleNumber()).thenReturn(LOAN_CYCLE_NUM);
		when(clientPerformanceHistory.getLastLoanAmount()).thenReturn(LAST_LOAN_AMOUNT);
		when(clientPerformanceHistory.getNoOfActiveLoans()).thenReturn(ACTIVE_LOANS);
		when(clientPerformanceHistory.getDelinquentPortfolioAmount()).thenReturn(PORTFOLIO_AMOUNT);
		when(clientPerformanceHistory.getTotalSavingsAmount()).thenReturn(TOTAL_SAVINGS);
		when(clientPerformanceHistory.getMeetingsAttended()).thenReturn(MEETINGS_ATTENDED);
		when(clientPerformanceHistory.getMeetingsMissed()).thenReturn(MEETINGS_MISSED);
		when(clientPerformanceHistory.getLoanCycleCounters()).thenReturn(new ArrayList<LoanCycleCounter>());
		
		CustomerAccountSummary customerAccountSummary = mock(CustomerAccountSummary.class);
		when(customerAccountSummary.getNextDueAmount()).thenReturn(NEXT_DUE_AMOUNT);
        
		ClientDetails clientDetails = mock(ClientDetails.class);
		when(clientDetails.getAddress()).thenReturn(address);
		when(clientDetails.getClientDisplay()).thenReturn(clientDisplay);
		when(clientDetails.getClientPerformanceHistory()).thenReturn(clientPerformanceHistory);
		when(clientDetails.getCustomerAccountSummary()).thenReturn(customerAccountSummary);

		CustomerService customerService = mock(CustomerService.class);
		when(customerService.getApplicableFees(any(String.class))).thenReturn(new HashMap<String, Map<String, String>>());
		when(customerService.getDetailsForEntity(any(Customer.class))).thenReturn(clientDetails);
	        
		activity.setCustomerService(customerService);
		getInstrumentation().callActivityOnStart(activity);
		getInstrumentation().callActivityOnResume(activity);
		
		new AsyncTaskHelper(activity.getCustomerDetailsTask()).waitForFinish();
		TextViewHelper textViewHelper = new TextViewHelper(activity);
		
		textViewHelper.verifyText(R.id.customerOverview_phoneNumber, PHONE_NUMBER);
		textViewHelper.verifyText(R.id.customerOverview_name, DISPLAY_NAME);
		textViewHelper.verifyText(R.id.customerOverview_systemId, GLOBAL_CUST_NUM);
		textViewHelper.verifyText(R.id.customerOverview_status, STATUS_NAME);
		textViewHelper.verifyText(R.id.customerOverview_loanCycleNo, Integer.toString(LOAN_CYCLE_NUM));
		textViewHelper.verifyText(R.id.customerOverview_amountOfLastLoan, LAST_LOAN_AMOUNT);
		textViewHelper.verifyText(R.id.customerOverview_noOfActiveLoans, Integer.toString(ACTIVE_LOANS));
		textViewHelper.verifyText(R.id.customerOverview_delinquentPortfolio, PORTFOLIO_AMOUNT);
		textViewHelper.verifyText(R.id.customerOverview_meetingsAttended, Integer.toString(MEETINGS_ATTENDED));
		textViewHelper.verifyText(R.id.customerOverview_meetingsMissed, Integer.toString(MEETINGS_MISSED));
		textViewHelper.verifyText(R.id.loanAccounts_amountDue, NEXT_DUE_AMOUNT);
		textViewHelper.verifyText(R.id.customerAdditional_customerActivation, ACTIVATION_DATE);
		textViewHelper.verifyText(R.id.customerAdditional_RecruitByDisplayName, FORMED_BY_DISPLAY_NAME);
		textViewHelper.verifyText(R.id.customerAdditional_dateOfBirthDay, DATE_OF_BIRTH);
		textViewHelper.verifyText(R.id.customerAdditional_ethinicity, ETHNICITY);
		textViewHelper.verifyText(R.id.customerAdditional_educationLevel, EDUCATION_LEVEL);
		textViewHelper.verifyText(R.id.customerAdditional_citizenship, CITIZENSHIP);
	}
	
}

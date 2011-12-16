package org.mifos.androidclient.main;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.simple.AbstractCustomer;
import org.mifos.androidclient.entities.simple.Center;
import org.mifos.androidclient.entities.simple.CustomersData;
import org.mifos.androidclient.entities.simple.Group;
import org.mifos.androidclient.net.services.CustomerService;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.mifos.androidclient.util.ApplicationConstants;
import org.mifos.androidclient.util.listadapters.SimpleListAdapter;
import org.mifos.androidclient.util.listadapters.SimpleListItem;
import org.springframework.web.client.RestClientException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class MeetingListActivity extends DownloaderActivity
        implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private SimpleDateFormat curFormater = new SimpleDateFormat("dd-MM-yyyy");
    private GregorianCalendar date = new GregorianCalendar();
    private String[] mSpinnerDateList = new String[7];
    private CustomerService mCustomerService;
    private CustomersData mCustomersData;
    private ListView mCenterList;
    private CustomerMeetingTask mCustomerMeetingTask;
    private Spinner spinner;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.meeting_schedule);

        if(bundle != null && bundle.containsKey(CustomersData.BUNDLE_KEY)) {
            mCustomersData = (CustomersData)bundle.getSerializable(CustomersData.BUNDLE_KEY);
        }
        mCustomerService = new CustomerService(this);

        spinner = (Spinner)findViewById(R.id.date_spinner);
        for (int day = 0; day < 7; day++) {
        mSpinnerDateList[day] = curFormater.format(date.getTime());
        date.roll(Calendar.DAY_OF_YEAR, true);
        }

        ArrayAdapter adapter = new ArrayAdapter(
        this,
        android.R.layout.simple_spinner_item, mSpinnerDateList);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long rowId) {
            String selectedDate =  spinner.getSelectedItem().toString();
            runCustomerMeetingTask(selectedDate);

        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            //do nothing
        }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CustomersData.BUNDLE_KEY, mCustomersData);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCustomerMeetingTask != null) {
            mCustomerMeetingTask.terminate();
            mCustomerMeetingTask = null;
        }
    }

    private void runCustomerMeetingTask(String date) {
        if(mCustomerMeetingTask == null || mCustomerMeetingTask.getStatus() != AsyncTask.Status.RUNNING) {
            mCustomerMeetingTask = new CustomerMeetingTask(
                    this,
                    getString(R.string.dialog_getting_customer_data),
                    getString(R.string.dialog_loading_message)
            );
            mCustomerMeetingTask.execute(date);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long rowId) {
        Center center = (Center)adapterView.getAdapter().getItem(position);
        Intent intent = new Intent().setClass(this, CustomersListActivity.class);
        intent.putExtra(Group.BUNDLE_KEY, new ArrayList<Group>(center.getGroups()));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long rowId) {
        Center center = (Center)adapterView.getAdapter().getItem(position);
        Intent intent = new Intent().setClass(this, CustomerDetailsActivity.class);
        intent.putExtra(AbstractCustomer.BUNDLE_KEY, center);
        startActivity(intent);
        return true;
    }

    private void reCustomerMeetingList(CustomersData data) {
        if (data.getCenters() != null) {
            mCenterList = (ListView)findViewById(R.id.meeting_center_list);
            mCenterList.setVisibility(View.VISIBLE);
            mCenterList.setAdapter(new SimpleListAdapter(
                    this,
                    new ArrayList<SimpleListItem>(data.getCenters())
            ));
            mCenterList.setOnItemClickListener(this);
            mCenterList.setOnItemLongClickListener(this);
        }
    }

    private class CustomerMeetingTask extends ServiceConnectivityTask<String, Void, CustomersData> {
        public CustomerMeetingTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected CustomersData doInBackgroundBody(String... params) throws RestClientException, IllegalArgumentException {
            CustomersData result = null;
            if(mCustomerService != null){
                        result = mCustomerService.getMeetingsList(params[0]);
                }
            return result;
        }

        @Override
        protected void onPostExecuteBody(CustomersData result) {
            TextView tv = (TextView)findViewById(R.id.no_meetings);
            if(result != null){
                mCustomersData = result;
                if (mCustomersData.getGroups() != null && mCustomersData.getGroups().size() > 0){
                    tv.setVisibility(View.GONE);
                    Center emptyCenter = new Center();
                    emptyCenter.setDisplayName(getString(R.string.centerslist_no_center));
                    emptyCenter.setId(ApplicationConstants.DUMMY_IDENTIFIER);
                    emptyCenter.setSearchId(ApplicationConstants.EMPTY_STRING);
                    emptyCenter.setGroups(mCustomersData.getGroups());
                    Intent intent = new Intent().setClass(MeetingListActivity.this, CentersListActivity.class);
                    intent.putExtra(CustomersData.BUNDLE_KEY, result);
                    startActivity(intent);
                }
                else if(result.getCenters() != null && result.getCenters().size() > 0) {
                        tv.setVisibility(View.GONE);
                        reCustomerMeetingList(result);
                }
                else {
                    tv.setText(getString(R.string.no_meetings));
                    tv.setVisibility(View.VISIBLE);
                }
            }
            }

        }


}



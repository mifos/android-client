package org.mifos.androidclient.main;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.AbstractAccountDetails;
import org.mifos.androidclient.entities.account.RepaymentScheduleItem;
import org.mifos.androidclient.net.services.AccountService;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.mifos.androidclient.util.listadapters.SimpleListAdapter;
import org.mifos.androidclient.util.listadapters.SimpleListItem;
import org.springframework.web.client.RestClientException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountRepaymentScheduleActivity extends DownloaderActivity
        implements AdapterView.OnItemClickListener{

    private String mAccountNumber;
    private AccountService mAccountService;
    private RepaymentScheduleTask mRepaymentScheduleTask;
    private List<RepaymentScheduleItem> mRepaymentScheduleItems;
    private ListView mRepaymentScheduleList;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.repayment_schedule);
        if (bundle != null && bundle.containsKey(RepaymentScheduleItem.BUNDLE_KEY)){
            mRepaymentScheduleItems = (List<RepaymentScheduleItem>)bundle.getSerializable(RepaymentScheduleItem.BUNDLE_KEY);
        }
        mRepaymentScheduleList = (ListView)findViewById(R.id.repaymentSchedule_list);
        mAccountNumber = getIntent().getStringExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY);
        mAccountService = new AccountService(this);
    }

    @Override
    protected void onSessionActive(){
        super.onSessionActive();
        if (mRepaymentScheduleItems == null){
            runRepaymentScheduleTask();
        } else {
            updateContent(mRepaymentScheduleItems);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable(RepaymentScheduleItem.BUNDLE_KEY, (Serializable) mRepaymentScheduleItems);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mRepaymentScheduleTask != null){
           mRepaymentScheduleTask.terminate();
           mRepaymentScheduleTask = null;
        }
    }

    private void updateContent(List<RepaymentScheduleItem> items){
        if(items != null) {
            mRepaymentScheduleItems = items;
            mRepaymentScheduleList.setAdapter(new SimpleListAdapter(
                    this,
                    new ArrayList<SimpleListItem>(items)
            ));
            mRepaymentScheduleList.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        RepaymentScheduleItem item = (RepaymentScheduleItem)adapterView.getAdapter().getItem(pos);
        Intent intent = new Intent().setClass(this,RepaymentScheduleActivity.class);
        intent.putExtra(RepaymentScheduleItem.BUNDLE_KEY, item);
        startActivity(intent);
    }

    private void runRepaymentScheduleTask(){
        if(mAccountNumber != null){
            if (mRepaymentScheduleTask == null || mRepaymentScheduleTask.getStatus() != AsyncTask.Status.RUNNING){
                mRepaymentScheduleTask = new RepaymentScheduleTask(
                        this,
                        getString(R.string.dialog_loading_message),
                        getString(R.string.dialog_getting_repayment_schedule)
                        );
                mRepaymentScheduleTask.execute(mAccountNumber);

            }
        }
    }

    private class RepaymentScheduleTask extends ServiceConnectivityTask<String,Void, RepaymentScheduleItem[]>{
        private RepaymentScheduleTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected RepaymentScheduleItem[] doInBackgroundBody(String... params) throws RestClientException, IllegalArgumentException {
            RepaymentScheduleItem[] items = new RepaymentScheduleItem[0];
            if(mAccountService != null){
               items = mAccountService.getAccountRepaymentSchedule(params[0]);
            }
            return items;
        }

        @Override
        protected void onPostExecuteBody(RepaymentScheduleItem[] repaymentScheduleItems) {
            List<RepaymentScheduleItem> items = new ArrayList<RepaymentScheduleItem>(Arrays.asList(repaymentScheduleItems));
            updateContent(items);
        }
    }
}

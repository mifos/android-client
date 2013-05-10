package org.mifos.androidclient.main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.AbstractAccountDetails;
import org.mifos.androidclient.entities.simple.AbstractCustomer;
import org.mifos.androidclient.entities.simple.Guarantor;
import org.mifos.androidclient.net.services.AccountService;
import org.mifos.androidclient.templates.DownloaderActivity;
import org.mifos.androidclient.templates.ServiceConnectivityTask;
import org.mifos.androidclient.util.listadapters.SimpleListAdapter;
import org.mifos.androidclient.util.listadapters.SimpleListItem;
import org.springframework.web.client.RestClientException;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class AccountGuarantorsActivity extends DownloaderActivity
        implements AdapterView.OnItemClickListener{

    private String mAccountNumber;
    private AccountService mAccountService;
    private GuarantorsTask mGuarantorsTask;
    private List<Guarantor> mGuarantors;
    private ListView mGuarantorsList;

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.guarantors);
        if (bundle != null && bundle.containsKey(Guarantor.BUNDLE_KEY)){
            mGuarantors = (List<Guarantor>)bundle.getSerializable(Guarantor.BUNDLE_KEY);
        }
        mGuarantorsList = (ListView)findViewById(R.id.guarantors_list);
        mAccountNumber = getIntent().getStringExtra(AbstractAccountDetails.ACCOUNT_NUMBER_BUNDLE_KEY);
        mAccountService = new AccountService(this);
    }

    @Override
    protected void onSessionActive(){
    	super.onSessionActive();
        if (mGuarantors == null){
            runGuarantorsTask();
        } else {
            updateContent(mGuarantors);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putSerializable(Guarantor.BUNDLE_KEY, (Serializable) mGuarantors);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mGuarantorsTask != null){
           mGuarantorsTask.terminate();
           mGuarantorsTask = null;
        }
    }

    private void updateContent(List<Guarantor> items){
        if(items != null) {
            mGuarantors = items;
            mGuarantorsList.setAdapter(new SimpleListAdapter(
                    this,
                    new ArrayList<SimpleListItem>(items)
            ));
            mGuarantorsList.setOnItemClickListener(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
        Guarantor item = (Guarantor)adapterView.getAdapter().getItem(pos);
        Intent intent = new Intent().setClass(this, CustomerDetailsActivity.class);
        intent.putExtra(AbstractCustomer.BUNDLE_KEY, item);
        startActivity(intent);
    }

    private void runGuarantorsTask(){
        if(mAccountNumber != null){
            if (mGuarantorsTask == null || mGuarantorsTask.getStatus() != AsyncTask.Status.RUNNING){
            	mGuarantorsTask = new GuarantorsTask(
                        this,
                        getString(R.string.dialog_loading_message),
                        getString(R.string.dialog_getting_guarantors)
                        );
                mGuarantorsTask.execute(mAccountNumber);
            }
        }
    }

    private class GuarantorsTask extends ServiceConnectivityTask<String,Void, Guarantor[]>{
        private GuarantorsTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected Guarantor[] doInBackgroundBody(String... params) throws RestClientException, IllegalArgumentException {
        	Guarantor[] items = new Guarantor[0];
            if(mAccountService != null){
               items = mAccountService.getAccountGuarantors(params[0]);
            }
            return items;
        }

        @Override
        protected void onPostExecuteBody(Guarantor[] guarantors) {
            List<Guarantor> items = new ArrayList<Guarantor>(Arrays.asList(guarantors));
            updateContent(items);
        }
    }
    
}

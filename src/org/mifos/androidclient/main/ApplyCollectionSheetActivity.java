package org.mifos.androidclient.main;


import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;
import org.codehaus.jackson.map.ObjectMapper;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.collectionsheet.*;
import org.mifos.androidclient.net.services.CollectionSheetService;
import org.mifos.androidclient.templates.OperationFormActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ApplyCollectionSheetActivity extends OperationFormActivity {
    public static final int REQUEST_CODE = 2;
    private CollectionSheetService mCollectionSheetService;
    private SaveCollectionSheet mSaveCustomer;
    private List<CollectionSheetCustomer> mCustomerList;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.collection_sheet_operation_form);
        mCollectionSheetService = new CollectionSheetService(this);
        mCustomerList = CollectionSheetHolder.getCollectionSheetData().getCollectionSheetCustomer();
        mSaveCustomer = CollectionSheetHolder.getSaveCollectionSheet();
        TableLayout tableLayout = (TableLayout)findViewById(R.id.collectionSheet_summary);
        tableLayout.setVisibility(View.VISIBLE);
        prepareSummaryTable();
    }

    @Override
    protected Map<String, String> onPrepareParameters() throws IOException {
        TableLayout tableLayout = (TableLayout)findViewById(R.id.collectionSheet_summary);
        tableLayout.setVisibility(View.GONE);
        Map<String,String> params = new HashMap<String, String>();
        params.put("json", new ObjectMapper().writeValueAsString(mSaveCustomer));
        return params;
    }

    @Override
    protected  Map<String, String> onFormSubmission(Map<String, String> parameters) {
        return  mCollectionSheetService.setCollectionSheetForCustomer(parameters);
    }

    private void prepareSummaryTable() {
        double dueCollections = 0.0;
        double otherCollections = 0.0;
        double loanDisbursements = 0.0;
        double withdrawals = 0.0;

        for(CollectionSheetCustomer cst: mCustomerList) {
            CollectionSheetCustomerAccount accounts = cst.getCollectionSheetCustomerAccount();

            otherCollections += accounts.getTotalCustomerAccountCollectionFee();

            if(cst.getCollectionSheetCustomerLoan().size() > 0 && cst.getCollectionSheetCustomerLoan() != null) {
                List<CollectionSheetCustomerLoan> loans = cst.getCollectionSheetCustomerLoan();
                for(CollectionSheetCustomerLoan loan : loans) {
                    dueCollections += loan.getTotalRepaymentDue();
                    loanDisbursements += loan.getTotalDisbursement();
                }
            }
            if(cst.getCollectionSheetCustomerSaving() != null && cst.getCollectionSheetCustomerSaving().size() > 0){
                List<CollectionSheetCustomerSavings> savings = cst.getCollectionSheetCustomerSaving();
                for(CollectionSheetCustomerSavings saving :savings) {
                    dueCollections += saving.getTotalDepositAmount();
                    withdrawals += saving.getWithdrawal();
                }
            }
            if(cst.getIndividualSavingAccounts() != null && cst.getIndividualSavingAccounts().size() > 0) {
                List<CollectionSheetCustomerSavings> individuals = cst.getIndividualSavingAccounts();
                for(CollectionSheetCustomerSavings individual : individuals) {
                    dueCollections += individual.getTotalDepositAmount();
                    withdrawals += individual.getWithdrawal();
                }
            }
        }

        TextView textView = (TextView)findViewById(R.id.collectionSheet_dueCollections);
        textView.setText(String.format("%.1f", dueCollections));
        textView = (TextView)findViewById(R.id.collectionSheet_otherCollections);
        textView.setText(String.format("%.1f", otherCollections));
        textView = (TextView)findViewById(R.id.collectionSheet_collectionsTotal);
        textView.setText(String.format("%.1f", (otherCollections + dueCollections)));
        textView = (TextView)findViewById(R.id.collectionSheet_loanDisbursements);
        textView.setText(String.format("%.1f", (loanDisbursements)));
        textView = (TextView)findViewById(R.id.collectionSheet_withdrawals);
        textView.setText(String.format("%.1f", (withdrawals)));
        textView = (TextView)findViewById(R.id.collectionSheet_issuesWithdrawalsTotal);
        textView.setText(String.format("%.1f", (withdrawals + loanDisbursements)));
        textView = (TextView)findViewById(R.id.collectionSheet_netCash);
        textView.setText(String.format("%.1f", (dueCollections + otherCollections - loanDisbursements - withdrawals)));
    }

}

package org.mifos.androidclient.main;

import android.os.Bundle;
import android.widget.TextView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.TransactionHistoryEntry;
import org.mifos.androidclient.templates.MifosActivity;

public class TransactionHistoryItemDetailsActivity extends MifosActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.transaction_history_item_details);

        TransactionHistoryEntry entry = (TransactionHistoryEntry)getIntent().getSerializableExtra(TransactionHistoryEntry.BUNDLE_KEY);
        if (entry != null) {
            TextView item;

            item = (TextView)findViewById(R.id.transactionHistoryItemDetails_date);
            item.setText(entry.getFormattedTransactionDate());
            item = (TextView)findViewById(R.id.transactionHistoryItemDetails_paymentId);
            item.setText(entry.getPaymentId().toString());
            item = (TextView)findViewById(R.id.transactionHistoryItemDetails_transactionId);
            item.setText(entry.getAccountTrxnId().toString());
            item = (TextView)findViewById(R.id.transactionHistoryItemDetails_type);
            item.setText(entry.getType());
            item = (TextView)findViewById(R.id.transactionHistoryItemDetails_glCode);
            item.setText(entry.getGlcode());
            item = (TextView)findViewById(R.id.transactionHistoryItemDetails_debit);
            item.setText(entry.getDebit());
            item = (TextView)findViewById(R.id.transactionHistoryItemDetails_credit);
            item.setText(entry.getCredit());
            item = (TextView)findViewById(R.id.transactionHistoryItemDetails_clientName);
            item.setText(entry.getClientName());
            item = (TextView)findViewById(R.id.transactionHistoryItemDetails_datePosted);
            item.setText(entry.getFormattedPostedDate());
            item = (TextView)findViewById(R.id.transactionHistoryItemDetails_postedBy);
            item.setText(entry.getPostedBy());
            item = (TextView)findViewById(R.id.transactionHistoryItemDetails_adjustmentNotes);
            item.setText(entry.getNotes());
        }
    }

}

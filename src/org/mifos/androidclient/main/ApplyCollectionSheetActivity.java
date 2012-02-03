package org.mifos.androidclient.main;


import android.os.Bundle;
import org.codehaus.jackson.map.ObjectMapper;
import org.mifos.androidclient.entities.collectionsheet.SaveCollectionSheet;
import org.mifos.androidclient.net.services.CollectionSheetService;
import org.mifos.androidclient.templates.OperationFormActivity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ApplyCollectionSheetActivity extends OperationFormActivity {
    public static final int REQUEST_CODE = 2;
    private CollectionSheetService mCollectionSheetService;
    private SaveCollectionSheet mSaveCustomer;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mCollectionSheetService = new CollectionSheetService(this);
        mSaveCustomer = CollectionSheetHolder.getSaveCollectionSheet();
    }

    @Override
    protected Map<String, String> onPrepareParameters() throws IOException {
        Map<String,String> params = new HashMap<String, String>();
        params.put("json", new ObjectMapper().writeValueAsString(mSaveCustomer));
        return params;
    }

    @Override
    protected  Map<String, String> onFormSubmission(Map<String, String> parameters) {
        return  mCollectionSheetService.setCollectionSheetForCustomer(parameters);
    }
}

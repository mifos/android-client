/*
 * Copyright (c) 2005-2011 Grameen Foundation USA
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an
 * explanation of the license and how it is applied.
 */

package org.mifos.androidclient.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.collectionsheet.CollectionSheetCustomer;
import org.mifos.androidclient.entities.simple.AbstractCustomer;
import org.mifos.androidclient.entities.simple.Center;

public class CollectionSheetCentersActivity extends CentersListActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        CollectionSheetHolder.setCOLLECTION_SHEET(0);
        TextView header = (TextView)findViewById(R.id.centers_list_label);
        header.setText(R.string.collectionSheetCenters_header);
        TextView hint = (TextView)findViewById(R.id.centers_list_hint);
        hint.setVisibility(View.GONE);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long rowId) {
        Center center = (Center)adapterView.getAdapter().getItem(position);
        CollectionSheetHolder.setSelectedCenter(center);
        CollectionSheetHolder.setCollectionSheetData(null);
        CollectionSheetHolder.setSaveCollectionSheet(null);
        CollectionSheetHolder.setCurrentCustomer(null);
        Intent intent = new Intent().setClass(this, PreCollectionSheetActivity.class);
        intent.putExtra(AbstractCustomer.BUNDLE_KEY, center);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long rowId) {
        return true;
    }


}

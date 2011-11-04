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

import android.os.Bundle;
import android.widget.ExpandableListView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.simple.Group;
import org.mifos.androidclient.templates.MifosActivity;
import org.mifos.androidclient.util.listadapters.SimpleExpandableListAdapter;
import org.mifos.androidclient.util.listadapters.SimpleListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomersListActivity extends MifosActivity {

    private ExpandableListView mCustomersList;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.clients_list);

        mCustomersList = (ExpandableListView)findViewById(R.id.clientslist_list);

        List<Group> groups = (List<Group>)getIntent().getSerializableExtra(Group.BUNDLE_KEY);
        Map<SimpleListItem, List<SimpleListItem>> items = new HashMap<SimpleListItem, List<SimpleListItem>>();
        if (groups != null) {
            for (Group group : groups) {
                items.put(group, new ArrayList<SimpleListItem>(group.getClients()));
            }
        }
        mCustomersList.setAdapter(new SimpleExpandableListAdapter(this, items));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}

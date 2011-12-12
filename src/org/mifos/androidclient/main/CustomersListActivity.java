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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.simple.AbstractCustomer;
import org.mifos.androidclient.entities.simple.Group;
import org.mifos.androidclient.templates.MifosActivity;
import org.mifos.androidclient.util.listadapters.SimpleExpandableListAdapter;
import org.mifos.androidclient.util.listadapters.SimpleListItem;

import java.util.*;

public class CustomersListActivity extends MifosActivity
        implements ExpandableListView.OnChildClickListener, AdapterView.OnItemLongClickListener {

    private EditText mFilterBox;
    private CustomersListTextWatcher mTextWatcher;
    private ExpandableListView mCustomersList;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.customers_list);

        mCustomersList = (ExpandableListView)findViewById(R.id.clientslist_list);
        mFilterBox = (EditText)findViewById(R.id.clientsList_filter_box);

        List<Group> groups = (List<Group>)getIntent().getSerializableExtra(Group.BUNDLE_KEY);
        Map<SimpleListItem, List<SimpleListItem>> items = new TreeMap<SimpleListItem, List<SimpleListItem>>(new Comparator<SimpleListItem>() {
            @Override
            public int compare(SimpleListItem simpleListItem1, SimpleListItem simpleListItem2) {
                return simpleListItem1.getListLabel().compareToIgnoreCase(simpleListItem2.getListLabel());
            }
        });
        if (groups != null) {
            for (Group group : groups) {
                items.put(group, new ArrayList<SimpleListItem>(group.getClients()));
            }
        }
        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(this, items);
        mCustomersList.setAdapter(adapter);
        if (mTextWatcher == null) {
            mTextWatcher = new CustomersListTextWatcher();
        }
        mTextWatcher.setAdapter(adapter);
        mFilterBox.addTextChangedListener(mTextWatcher);
        mCustomersList.setOnChildClickListener(this);
        mCustomersList.setOnItemLongClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFilterBox.removeTextChangedListener(mTextWatcher);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View view, int groupPos, int childPos, long id) {
        AbstractCustomer customer = (AbstractCustomer)parent.getExpandableListAdapter().getChild(groupPos, childPos);
        Intent intent = new Intent().setClass(this, CustomerDetailsActivity.class);
        intent.putExtra(AbstractCustomer.BUNDLE_KEY, customer);
        startActivity(intent);
        return true;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (view.getId() == R.id.expandableListGroup) {
            Group group = (Group)adapterView.getAdapter().getItem(position);
            Intent intent = new Intent().setClass(this, CustomerDetailsActivity.class);
            intent.putExtra(AbstractCustomer.BUNDLE_KEY, group);
            startActivity(intent);
            return true;
        }
        return false;
    }

    private class CustomersListTextWatcher implements TextWatcher {

        private SimpleExpandableListAdapter mAdapter;

        public void setAdapter(SimpleExpandableListAdapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (mAdapter != null) {
                mAdapter.getFilter().filter(charSequence);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

    }

}

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

package org.mifos.androidclient.main.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.collectionsheet.CollectionSheetCustomer;
import org.mifos.androidclient.entities.collectionsheet.CollectionSheetData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionSheetExpandableListAdapter extends BaseExpandableListAdapter {

    private CollectionSheetData mData;
    private Context mContext;
    private Map<Integer, List<CollectionSheetCustomer>> mClients;
    private Map<Integer, CollectionSheetCustomer> mGroups;
    public CollectionSheetExpandableListAdapter(CollectionSheetData data, Context context) {
        mData = data;
        mContext = context;
        addItems(mData);
    }

    @Override
    public int getGroupCount() {
        return mGroups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(mClients.containsKey(groupPosition)) {
           return mClients.get(groupPosition).size();
            } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        CollectionSheetCustomer group = null;
        if (mGroups.containsKey(groupPosition))
            group = mGroups.get(groupPosition);
        return group;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        CollectionSheetCustomer child = null;
        if(mClients.containsKey(groupPosition))
            child = mClients.get(groupPosition).get(childPosition);
        return child;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View row;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           row = inflater.inflate(R.layout.collection_sheet_group, parent, false);
        } else {
            row = convertView;
        }
        CollectionSheetCustomer item =(CollectionSheetCustomer)getGroup(groupPosition);
        if (item != null) {
            TextView label = (TextView)row.findViewById(R.id.collectionSheetGroup_label);
            if (getChildrenCount(groupPosition) > 0) {
            label.setText(mContext.getString(R.string.clientsList_listGroupLabel_withChildren, item.getListLabel(), getChildrenCount(groupPosition)));
            } else label.setText(item.getListLabel());
        }
        return row;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View row;
        CollectionSheetCustomer customer = (CollectionSheetCustomer)getChild(groupPosition,childPosition);
            if (customer == null) {
                row = new TextView(mContext);
            }
            else {
                LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.collection_sheet_child, parent,false);
                TextView textView = (TextView)row.findViewById(R.id.collectionSheetChild_name);
                textView.setText("     " +customer.getName());
        }
        return row;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void addItems(CollectionSheetData sheetData) {
        List<CollectionSheetCustomer> customerList = sheetData.getCollectionSheetCustomer();
        mGroups = new HashMap<Integer, CollectionSheetCustomer>();
        mClients = new HashMap<Integer, List<CollectionSheetCustomer>>();
        int i = 0, j = 0;
        for (CollectionSheetCustomer list : customerList) {
            if (list.getLevelId() == 2) {
                mGroups.put(i, list);
                i++;
            }
        }
        i = 0;
        ArrayList<CollectionSheetCustomer> cst = new ArrayList<CollectionSheetCustomer>();
       for(int h = 0; h < mGroups.size(); h++){
            for (CollectionSheetCustomer list : customerList) {
                if (list.getLevelId() == 1) {
                    if((mGroups.get(h).getCustomerId()).equals(list.getParentCustomerId())) {
                        cst.add(list);
                    }
                }
            }
           mClients.put(h, cst);
           cst = new ArrayList<CollectionSheetCustomer>();
       }

    }

}


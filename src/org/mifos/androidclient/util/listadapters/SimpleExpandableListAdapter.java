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

package org.mifos.androidclient.util.listadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.mifos.androidclient.R;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * Represents an adapter which can be used with expandable lists.<br />
 * Is designed to work with entities which implement the {@link SimpleListItem}.
 */
public class SimpleExpandableListAdapter extends BaseExpandableListAdapter implements Filterable {

    private Context mContext;
    private final Map<SimpleListItem, List<SimpleListItem>> mItems;
    private Map<Integer, SimpleListItem> mKeys;
    private Map<Integer, List<SimpleListItem>> mValues;
    private SimpleExpandableListFilter mFilter;
    private Boolean mExpandGroups;

    public SimpleExpandableListAdapter(Context context, Map<SimpleListItem, List<SimpleListItem>> items) {
        mContext = context;
        mItems = new TreeMap<SimpleListItem, List<SimpleListItem>>(new Comparator<SimpleListItem>() {
            @Override
            public int compare(SimpleListItem simpleListItem1, SimpleListItem simpleListItem2) {
                return simpleListItem1.getListLabel().compareToIgnoreCase(simpleListItem2.getListLabel());
            }
        });
        mItems.putAll(items);
        mExpandGroups = false;
        splitItems(items);
    }

    @Override
    public int getGroupCount() {
        return mKeys.size();
    }

    @Override
    public int getChildrenCount(int groupPos) {
        if (mValues.containsKey(groupPos)) {
            return mValues.get(groupPos).size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPos) {
        SimpleListItem group = null;
        if (mKeys.containsKey(groupPos)) {
            group = mKeys.get(groupPos);
        }
        return group;
    }

    @Override
    public Object getChild(int groupPos, int childPos) {
        SimpleListItem child = null;
        if (mValues.containsKey(groupPos)) {
            child = mValues.get(groupPos).get(childPos);
        }
        return child;
    }

    @Override
    public long getGroupId(int groupPos) {
        return 0;
    }

    @Override
    public long getChildId(int groupPos, int childPos) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPos, boolean isExpanded, View convertView, ViewGroup parent) {
        View row;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.simple_list_group, parent, false);
        } else {
            row = convertView;
        }
        SimpleListItem item = (SimpleListItem)getGroup(groupPos);
        if (item != null) {
            TextView label = (TextView)row.findViewById(R.id.simple_list_item_label);
            label.setText(item.getListLabel());
        }
        synchronized (mExpandGroups) {
            if (mExpandGroups == true) {
                ExpandableListView list = (ExpandableListView)parent;
                list.expandGroup(groupPos);
            }
        }
        return row;
    }

    @Override
    public View getChildView(int groupPos, int childPos, boolean isLastChild, View convertView, ViewGroup parent) {
        View row;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.simple_list_child, parent, false);
        } else {
            row = convertView;
        }
        SimpleListItem item = (SimpleListItem)getChild(groupPos, childPos);
        if (item != null) {
            TextView label = (TextView)row.findViewById(R.id.simple_list_item_label);
            label.setText(item.getListLabel());
        }
        return row;
    }

    @Override
    public boolean isChildSelectable(int groupPos, int childPos) {
        return true;
    }

    private void splitItems(Map<SimpleListItem, List<SimpleListItem>> items) {
        mKeys = new HashMap<Integer, SimpleListItem>();
        mValues = new HashMap<Integer, List<SimpleListItem>>();
        int i = 0;
        for (Map.Entry<SimpleListItem, List<SimpleListItem>> item : items.entrySet()) {
            mKeys.put(i, item.getKey());
            mValues.put(i, item.getValue());
            i++;
        }
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new SimpleExpandableListFilter();
        }
        return mFilter;
    }

    private class SimpleExpandableListFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            String constraint = charSequence.toString().toLowerCase();

            if (StringUtils.hasLength(constraint)) {
                Map<SimpleListItem, List<SimpleListItem>> allItems = new HashMap<SimpleListItem, List<SimpleListItem>>();
                Map<SimpleListItem, List<SimpleListItem>> filteredItems = new HashMap<SimpleListItem, List<SimpleListItem>>();

                synchronized (mItems) {
                    allItems.putAll(mItems);
                }

                for (SimpleListItem group : allItems.keySet()) {
                    List<SimpleListItem> clients = new ArrayList<SimpleListItem>();
                    for (SimpleListItem client : allItems.get(group)) {
                        if (client.getListLabel().toLowerCase().contains(constraint)) {
                            clients.add(client);
                        }
                    }
                    if (group.getListLabel().toLowerCase().contains(constraint) || clients.size() > 0) {
                        filteredItems.put(group, clients);
                    }
                }

                synchronized (mExpandGroups) {
                    mExpandGroups = true;
                }

                results.values = filteredItems;
                results.count = filteredItems.size();
            } else {
                synchronized (mItems) {
                    results.values = mItems;
                    results.count = mItems.size();
                }
                synchronized (mExpandGroups) {
                    mExpandGroups = false;
                }
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            notifyDataSetInvalidated();
            splitItems((Map<SimpleListItem, List<SimpleListItem>>)filterResults.values);
            notifyDataSetChanged();
        }

    }

}

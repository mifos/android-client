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
import android.widget.ArrayAdapter;
import android.widget.TextView;
import org.mifos.androidclient.R;

import java.util.List;

/**
 * Represents an adapter which can be used with standard lists.<br />
 * Is designed to work with entities which implement the {@link SimpleListItem}.
 */
public class SimpleListAdapter extends ArrayAdapter<SimpleListItem> {

    public SimpleListAdapter(Context context, List<SimpleListItem> objects) {
        super(context, R.layout.simple_list_child, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.simple_list_item, parent, false);
        } else {
            row = convertView;
        }
        SimpleListItem item = getItem(position);
        if (item != null) {
            TextView label = (TextView)row.findViewById(R.id.simple_list_item_label);
            label.setText(item.getListLabel());
        }
        return row;
    }

}

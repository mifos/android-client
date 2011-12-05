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

package org.mifos.androidclient.util.ui;

import android.content.Context;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;
import org.mifos.androidclient.R;

/**
 * An utility class which helps filling in table layouts
 * programmatically.
 */
public class TableLayoutHelper {

    public final static int DEFAULT_TABLE_ROW_SEPARATOR_HEIGHT = 1;
    public final static int DEFAULT_TABLE_CELL_PADDING = 5;

    private Context mContext;
    private int mTableRowSeparatorHeight;
    private int mTableCellPadding;

    public TableLayoutHelper(Context context) {
        this(context, DEFAULT_TABLE_ROW_SEPARATOR_HEIGHT, DEFAULT_TABLE_CELL_PADDING);
    }

    public TableLayoutHelper(Context context, int tableRowSeparatorHeight, int tableCellPadding) {
        mContext = context;
        mTableRowSeparatorHeight = tableRowSeparatorHeight;
        mTableCellPadding = tableCellPadding;
    }

    public TextView createTableCell(String text, int column) {
        TextView textView = new TextView(mContext);
        textView.setText(text);
        textView.setLayoutParams(new TableRow.LayoutParams(column));
        textView.setPadding(mTableCellPadding, 0, mTableCellPadding, 0);
        return textView;
    }

    public TableRow createTableRow() {
        TableRow row = new TableRow(mContext);
        row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,TableRow.LayoutParams.WRAP_CONTENT));
        return row;
    }

    public View createRowSeparator() {
        View separator = new View(mContext);
        separator.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, mTableRowSeparatorHeight));
        separator.setBackgroundResource(R.color.tableSeparator);
        return separator;
    }

}

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.account.LoanAccountDetails;
import org.mifos.androidclient.entities.customer.LoanAccountBasicInformation;
import org.mifos.androidclient.entities.customer.OverdueCustomer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class OverdueBorrowersListAdapter extends SimpleExpandableListAdapter implements Filterable {

    public OverdueBorrowersListAdapter(Context context,
			Map<SimpleListItem, List<SimpleListItem>> items) {
		super(context, items);
	}

    @Override
    public View getGroupView(int groupPos, boolean isExpanded, View convertView, ViewGroup parent) {
        View row;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.overdue_borrower_list_item, parent, false);
        } else {
            row = convertView;
        }
        OverdueCustomer item = (OverdueCustomer) getGroup(groupPos);
        if (item != null) {
        	
            TextView name = (TextView) row.findViewById(R.id.client_name);
            TextView phone = (TextView) row.findViewById(R.id.client_phone);
            TextView address = (TextView) row.findViewById(R.id.client_address);
            TextView capital = (TextView) row.findViewById(R.id.total_capital);
            
            if (item.isGroup()) {
            	((TextView) row.findViewById(R.id.client_name_label)).setText(R.string.overdueBorrowersListItem_groupName_label);
            }
            
            name.setText(item.getDisplayName());
            phone.setText(item.getPhoneNumber());
            address.setText(item.getAddress());
            capital.setText(item.getTotalCapitalOutstanding());
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
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.overdue_loan_list_item, parent, false);
        } else {
            row = convertView;
        }
        LoanAccountBasicInformation overdueLoan = (LoanAccountBasicInformation) getChild(groupPos, childPos);
        TextView text = null;
        if (overdueLoan != null) {
        	text = (TextView)row.findViewById(R.id.loanAccountListItem_accountName);
            text.setText(overdueLoan.getPrdOfferingName() + ", " + overdueLoan.getGlobalAccountNum());
            text = (TextView)row.findViewById(R.id.loanAccountListItem_status);
            text.setText(overdueLoan.getAccountStateName());
            if (overdueLoan.getAccountStateId() == LoanAccountDetails.ACC_STATE_PARTIAL_APPLICATION ||
                overdueLoan.getAccountStateId() == LoanAccountDetails.ACC_STATE_APPLICATION_APPROVED ||
                overdueLoan.getAccountStateId() == LoanAccountDetails.ACC_STATE_APPLICATION_PENDING_APPROVAL) {

                text = (TextView)row.findViewById(R.id.loanAccountListItem_amountDue_label);
                text.setVisibility(View.GONE);
                text = (TextView)row.findViewById(R.id.loanAccountListItem_amountDue);
                text.setVisibility(View.GONE);
            } else {
                text = (TextView)row.findViewById(R.id.loanAccountListItem_amountDue);
                text.setText(overdueLoan.getTotalAmountDue());
            }
            text = (TextView) row.findViewById(R.id.loanAccountListItem_amountInArrears);
            text.setText(overdueLoan.getTotalAmountInArrears());
        }
        return row;
    }
    
	@Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new OverdueBorrowersListFilter();
        }
        return mFilter;
    }

    private class OverdueBorrowersListFilter extends SimpleExpandableListFilter {

    	@Override
    	protected Map<SimpleListItem, List<SimpleListItem>> filterGroups(Map<SimpleListItem, 
    			List<SimpleListItem>> allItems, String constraint) {
    		 Map<SimpleListItem, List<SimpleListItem>> filteredItems = new HashMap<SimpleListItem, List<SimpleListItem>>();
    		 
    		 for (SimpleListItem group : allItems.keySet()) {
                 List<SimpleListItem> clients = new ArrayList<SimpleListItem>();
                 for (SimpleListItem client : allItems.get(group)) {
                 	clients.add(client);
                 }
                 if (group.getListLabel().toLowerCase().contains(constraint)) {
                     filteredItems.put(group, clients);
                 }
             }
    		 
    		 return filteredItems;
    	}
    	
    }

}

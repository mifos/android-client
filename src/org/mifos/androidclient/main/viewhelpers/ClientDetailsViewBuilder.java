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

package org.mifos.androidclient.main.viewhelpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.customer.ClientDetails;
import org.mifos.androidclient.templates.CustomerDetailsViewBuilder;

public class ClientDetailsViewBuilder implements CustomerDetailsViewBuilder {

    private Context mContext;
    private ClientDetails mDetails;

    public ClientDetailsViewBuilder(Context context, ClientDetails details) {
        mContext = context;
        mDetails = details;
    }

    @Override
    public View buildOverviewView() {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.client_overview, null);

        TextView textView = (TextView)view.findViewById(R.id.customerOverview_name);
        textView.setText(mDetails.getClientDisplay().getDisplayName());
        textView = (TextView)view.findViewById(R.id.customerOverview_systemId);
        textView.setText(mDetails.getClientDisplay().getGlobalCustNum());
        textView = (TextView)view.findViewById(R.id.customerOverview_status);
        textView.setText(mDetails.getClientDisplay().getStatus());

        textView = (TextView)view.findViewById(R.id.customerOverview_loanCycleNo);
        textView.setText(mDetails.getClientPerformanceHistory().getLoanCycleNumber().toString());
        textView = (TextView)view.findViewById(R.id.customerOverview_amountOfLastLoan);
        textView.setText(mDetails.getClientPerformanceHistory().getLastLoanAmount());
        textView = (TextView)view.findViewById(R.id.customerOverview_noOfActiveLoans);
        textView.setText(mDetails.getClientPerformanceHistory().getNoOfActiveLoans().toString());
        textView = (TextView)view.findViewById(R.id.customerOverview_delinquentPortfolio);
        textView.setText(mDetails.getClientPerformanceHistory().getDelinquentPortfolioAmount());
        textView = (TextView)view.findViewById(R.id.customerOverview_totalSavings);
        textView.setText(mDetails.getClientPerformanceHistory().getTotalSavingsAmount());
        textView = (TextView)view.findViewById(R.id.customerOverview_meetingsAttended);
        textView.setText(mDetails.getClientPerformanceHistory().getMeetingsAttended().toString());
        textView = (TextView)view.findViewById(R.id.customerOverview_meetingsMissed);
        textView.setText(mDetails.getClientPerformanceHistory().getMeetingsMissed().toString());

        return view;
    }

    @Override
    public View buildAccountsView() {
        return null;
    }

    @Override
    public View buildAdditionalView() {
        return null;
    }

    LayoutInflater getLayoutInflater() {
        return (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

}

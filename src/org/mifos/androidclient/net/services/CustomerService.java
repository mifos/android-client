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

package org.mifos.androidclient.net.services;

import android.content.Context;
import org.mifos.androidclient.entities.customer.ClientDetails;
import org.mifos.androidclient.entities.simple.CustomersData;

public class CustomerService extends RestNetworkService {

    private final static String LOAN_OFFICER_CUSTOMERS_PATH = "/personnel/clients/id-current.json";

    private final static String CUSTOMER_DETAILS_PATH_PREFIX = "/client/num-";
    private final static String CUSTOMER_DETAILS_PATH_SUFFIX = ".json";

    public CustomerService(Context context) {
        super(context);
    }

    public CustomersData getLoanOfficersCustomers() {
        String url = getServerUrl() + LOAN_OFFICER_CUSTOMERS_PATH;
        return mRestConnector.getForObject(url, CustomersData.class);
    }

    public ClientDetails getClientDetails(String customerNumber) {
        String url = getServerUrl() + CUSTOMER_DETAILS_PATH_PREFIX + customerNumber + CUSTOMER_DETAILS_PATH_SUFFIX;
        return mRestConnector.getForObject(url, ClientDetails.class);
    }

}

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
import android.content.SharedPreferences;
import org.mifos.androidclient.net.RestConnector;
import org.mifos.androidclient.util.ApplicationConstants;

public abstract class RestNetworkService {

    protected final static String STATUS_PATH = "/status.json";

    protected Context mContext;
    protected RestConnector mRestConnector;

    private String mServerUrl;

    public RestNetworkService(Context context) {
        mContext = context;
        mRestConnector = RestConnector.getInstance();

        SharedPreferences settings = mContext.getSharedPreferences(ApplicationConstants.MIFOS_APPLICATION_PREFERENCES, mContext.MODE_PRIVATE);
        mServerUrl = settings.getString(ApplicationConstants.MIFOS_SERVER_ADDRESS_KEY, "");
    }

    protected String getServerUrl() {
        return mServerUrl;
    }

}

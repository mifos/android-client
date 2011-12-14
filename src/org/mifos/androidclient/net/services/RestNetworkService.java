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

import java.util.Map;

public abstract class RestNetworkService {

    protected final static String STATUS_PATH = "/status.json";
    protected final static String PATH_SUFFIX = ".json";

    protected Context mContext;
    protected RestConnector mRestConnector;

    private SharedPreferences mSettings;

    public RestNetworkService(Context context) {
        mContext = context;
        mRestConnector = RestConnector.getInstance();

        mSettings = mContext.getSharedPreferences(ApplicationConstants.MIFOS_APPLICATION_PREFERENCES, Context.MODE_PRIVATE);
    }

    protected String getServerUrl() {
        return mSettings.getString(ApplicationConstants.MIFOS_SERVER_ADDRESS_KEY, "");
    }

    protected String prepareQueryString(Map<String, String> params) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("?");
        for (Map.Entry<String, String> entry: params.entrySet()) {
            buffer.append(entry.getKey());
            buffer.append("=");
            buffer.append(entry.getValue());
            buffer.append("&");
        }
        buffer.deleteCharAt(buffer.length()-1);
        return buffer.toString();
    }


}

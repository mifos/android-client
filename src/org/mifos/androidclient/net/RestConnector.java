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

package org.mifos.androidclient.net;

import org.springframework.web.client.RestTemplate;

public class RestConnector extends RestTemplate {

    private static volatile RestConnector sInstance;

    private boolean loggedIn;

    public static RestConnector getInstance() {
        if (sInstance == null) {
            sInstance = new RestConnector();
        }
        return sInstance;
    }

    public static void resetConnection() {
        sInstance = null;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    private RestConnector() {
        super();
        loggedIn = false;
    }

}

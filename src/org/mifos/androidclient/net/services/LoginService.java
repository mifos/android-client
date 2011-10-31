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
import org.mifos.androidclient.entities.RequestStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class LoginService extends RestNetworkService {

    private final static String USERNAME_KEY = "j_username";
    private final static String PASSWORD_KEY = "j_password";
    private final static String SPRING_REDIRECT_KEY = "spring-security-redirect";

    private final static String LOGIN_PATH = "/j_spring_security_check";

    public LoginService(Context context) {
        super(context);
    }

    public RequestStatus logIn(String userName, String password) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add(USERNAME_KEY, userName);
        params.add(PASSWORD_KEY, password);
        params.add(SPRING_REDIRECT_KEY, STATUS_PATH);

        String url = getServerUrl() + LOGIN_PATH;

        return mRestConnector.postForObject(url, params, RequestStatus.class);
    }

}

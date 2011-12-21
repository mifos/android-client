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

package org.mifos.androidclient.util;

import android.content.Context;
import org.mifos.androidclient.R;

import java.util.HashMap;
import java.util.Map;

public class ServerMessageTranslator {

    private static final String RESOURCE_TYPE = "string";

    public static String translate(Context context, String messageKey, String defaultValue) {
        String translation = defaultValue;
        int resourceId = context.getResources().getIdentifier(messageKey, RESOURCE_TYPE, context.getPackageName());
        if (resourceId > 0) {
            translation = context.getString(resourceId);
        }
        return translation;
    }

}

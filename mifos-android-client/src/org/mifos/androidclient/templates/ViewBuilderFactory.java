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

package org.mifos.androidclient.templates;

import org.mifos.androidclient.entities.account.AbstractAccountDetails;
import org.mifos.androidclient.entities.account.LoanAccountDetails;
import org.mifos.androidclient.entities.account.SavingsAccountDetails;
import org.mifos.androidclient.entities.customer.CenterDetails;
import org.mifos.androidclient.entities.customer.ClientDetails;
import org.mifos.androidclient.entities.customer.CustomerDetailsEntity;
import org.mifos.androidclient.entities.customer.GroupDetails;
import org.mifos.androidclient.main.views.helpers.CenterDetailsViewBuilder;
import org.mifos.androidclient.main.views.helpers.ClientDetailsViewBuilder;
import org.mifos.androidclient.main.views.helpers.GroupDetailsViewBuilder;
import org.mifos.androidclient.main.views.helpers.LoanAccountDetailsViewBuilder;
import org.mifos.androidclient.main.views.helpers.SavingsAccountDetailsViewBuilder;

import android.content.Context;

public class ViewBuilderFactory {

    private Context mContext;

    public ViewBuilderFactory(Context context) {
        mContext = context;
    }

    public CustomerDetailsViewBuilder createCustomerDetailsViewBuilder(CustomerDetailsEntity entity) {
        if (entity instanceof ClientDetails) {
            return new ClientDetailsViewBuilder(mContext, (ClientDetails)entity);
        } else if (entity instanceof GroupDetails) {
            return new GroupDetailsViewBuilder(mContext, (GroupDetails)entity);
        } else if (entity instanceof CenterDetails) {
            return new CenterDetailsViewBuilder(mContext, (CenterDetails)entity);
        }
        return null;
    }

    public AccountDetailsViewBuilder createAccountDetailsViewBuilder(AbstractAccountDetails entity) {
        AccountDetailsViewBuilder builder = null;
        if (entity.getClass() == LoanAccountDetails.class) {
            builder = new LoanAccountDetailsViewBuilder(mContext, (LoanAccountDetails)entity);
        } else if (entity.getClass() == SavingsAccountDetails.class) {
            builder = new SavingsAccountDetailsViewBuilder(mContext, (SavingsAccountDetails)entity);
        }
        return builder;
    }

}

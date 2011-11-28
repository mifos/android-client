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

package org.mifos.androidclient.entities.simple;

import org.mifos.androidclient.util.listadapters.SimpleListItem;

import java.io.Serializable;
import java.util.List;

/**
 * A simple bean class representing a Mifos group in a basic form.
 * Used on list - provides a display name and an identifier which can
 * be used to fetch more detailed data.
 */
public class Group extends AbstractCustomer implements SimpleListItem, Serializable {

    public static final String BUNDLE_KEY = Group.class.getSimpleName();

    private List<Customer> clients;

    @Override
    public String getListLabel() {
        String listLabel;
        if (clients != null && clients.size() > 0) {
            listLabel = getDisplayName() + " (" + clients.size() + ")";
        } else {
            listLabel = getDisplayName();
        }
        return listLabel;
    }

    @Override
    public int getItemIdentifier() {
        return getId();
    }

    public List<Customer> getClients() {
        return clients;
    }

    public void setClients(List<Customer> clients) {
        this.clients = clients;
    }

}

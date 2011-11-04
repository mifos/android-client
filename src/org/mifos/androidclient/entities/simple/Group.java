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
public class Group implements SimpleListItem, Serializable {

    public static final String BUNDLE_KEY = Group.class.getSimpleName();

    private Integer id;
    private String displayName;
    private String searchId;
    private List<Customer> clients;

    @Override
    public String getListLabel() {
        return getDisplayName();
    }

    @Override
    public int getItemIdentifier() {
        return getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public List<Customer> getClients() {
        return clients;
    }

    public void setClients(List<Customer> clients) {
        this.clients = clients;
    }

}

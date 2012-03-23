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

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.mifos.androidclient.entities.BaseEntity;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Used to aggregate data which is downloaded when a list of
 * Loan Officer's clients is requested from the Mifos server.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomersData extends BaseEntity implements Serializable {

    public static final String BUNDLE_KEY = CustomersData.class.getSimpleName();

    private List<Center> centers;
    private List<Group> groups;
    private List<Customer> clients;

    public void sort() {
        if (centers != null) {
            Collections.sort(centers, new Comparator<Center>() {
                @Override
                public int compare(Center center1, Center center2) {
                    return center1.getDisplayName().compareToIgnoreCase(center2.getDisplayName());
                }
            });
        }
    }

    public List<Center> getCenters() {
        return centers;
    }

    public void setCenters(List<Center> centers) {
        this.centers = centers;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public List<Customer> getClients() {
        return clients;
    }

    public void setClients(List<Customer> customer) {
        this.clients = customer;
    }

}

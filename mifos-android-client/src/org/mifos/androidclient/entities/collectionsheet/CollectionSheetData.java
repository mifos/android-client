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

package org.mifos.androidclient.entities.collectionsheet;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.mifos.androidclient.util.serialization.JodaLocalDateDeserializer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectionSheetData implements Serializable {

    public static final String BUNDLE_KEY = CollectionSheetData.class.getSimpleName();

    private Date date;
    private List<CollectionSheetCustomer> collectionSheetCustomer;

    public Date getDate() {
        return date;
    }

    @JsonDeserialize(using = JodaLocalDateDeserializer.class)
    public void setDate(Date date) {
        this.date = date;
    }

    public List<CollectionSheetCustomer> getCollectionSheetCustomer() {
        return collectionSheetCustomer;
    }

    public void setCollectionSheetCustomer(List<CollectionSheetCustomer> collectionSheetCustomer) {
        this.collectionSheetCustomer = collectionSheetCustomer;
    }

}

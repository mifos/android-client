package org.mifos.androidclient.entities.simple;

import org.mifos.androidclient.util.listadapters.SimpleListItem;

import java.io.Serializable;

public class Fee implements Serializable, SimpleListItem {

    public final static String BUNDLE_KEY = Fee.class.getSimpleName();

    private Integer id;
    private String displayName;

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

}

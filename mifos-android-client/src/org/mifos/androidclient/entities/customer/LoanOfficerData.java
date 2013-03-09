package org.mifos.androidclient.entities.customer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoanOfficerData implements Serializable{

    private  Integer id;
    private  String globalPersonnelNum;
    private  String displayName;
    private  Boolean locked;
    private  String emailId;
    private  Short levelId;
    private  Integer officeId;
    private  String officeName;
    private  Integer title;
    private  Short personnelId;
    private  String userName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGlobalPersonnelNum() {
        return globalPersonnelNum;
    }

    public void setGlobalPersonnelNum(String globalPersonnelNum) {
        this.globalPersonnelNum = globalPersonnelNum;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Short getLevelId() {
        return levelId;
    }

    public void setLevelId(Short levelId) {
        this.levelId = levelId;
    }

    public Integer getOfficeId() {
        return officeId;
    }

    public void setOfficeId(Integer officeId) {
        this.officeId = officeId;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }

    public Short getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(Short personnelId) {
        this.personnelId = personnelId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

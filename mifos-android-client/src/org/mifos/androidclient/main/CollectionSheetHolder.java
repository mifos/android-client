package org.mifos.androidclient.main;


import org.mifos.androidclient.entities.collectionsheet.CollectionSheetCustomer;
import org.mifos.androidclient.entities.collectionsheet.CollectionSheetData;
import org.mifos.androidclient.entities.collectionsheet.SaveCollectionSheet;
import org.mifos.androidclient.entities.simple.Center;

public class  CollectionSheetHolder {
    static CollectionSheetData collectionSheetData;
    static CollectionSheetCustomer currentCustomer;
    static  int COLLECTION_SHEET = 1;
    static SaveCollectionSheet saveCollectionSheet;
    static Center selectedCenter;

    public static Center getSelectedCenter() {
        return selectedCenter;
    }

    public static void setSelectedCenter(Center selectedCenter) {
        CollectionSheetHolder.selectedCenter = selectedCenter;
    }

    public static SaveCollectionSheet getSaveCollectionSheet() {
        return saveCollectionSheet;
    }

    public static void setSaveCollectionSheet(SaveCollectionSheet saveCollectionSheet) {
        CollectionSheetHolder.saveCollectionSheet = saveCollectionSheet;
    }

    public static int getCOLLECTION_SHEET() {
        return COLLECTION_SHEET;
    }

    public static void setCOLLECTION_SHEET(int COLLECTION_SHEET) {
        CollectionSheetHolder.COLLECTION_SHEET = COLLECTION_SHEET;
    }

    public static CollectionSheetData getCollectionSheetData() {
        return collectionSheetData;
    }

    public static void setCollectionSheetData(CollectionSheetData collectionSheetData) {
        CollectionSheetHolder.collectionSheetData = collectionSheetData;
    }

    public static CollectionSheetCustomer getCurrentCustomer() {
        return currentCustomer;
    }

    public static void setCurrentCustomer(CollectionSheetCustomer currentCustomer) {
        CollectionSheetHolder.currentCustomer = currentCustomer;
    }
}

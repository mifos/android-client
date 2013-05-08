package org.mifos.androidclient.test.utils;

import junit.framework.Assert;
import android.widget.ListView;

public class ListViewHelper {

	private final ListView mListView;
	
	public ListViewHelper(ListView listView) {
		mListView = listView;
	}
	
	public void verifyItemsCount(int expectedItemsCount) {
		Assert.assertEquals(expectedItemsCount, mListView.getCount());
	}
	
}

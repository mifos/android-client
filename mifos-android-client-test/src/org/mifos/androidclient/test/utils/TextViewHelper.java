package org.mifos.androidclient.test.utils;

import junit.framework.Assert;
import android.app.Activity;
import android.widget.TextView;

public class TextViewHelper {

	private final Activity mActivity;
	
	public TextViewHelper(Activity activity) {
		mActivity = activity;
	}
	
	public void verifyText(int id, String text) {
		Assert.assertEquals(text, ((TextView) mActivity.findViewById(id)).getText().toString());
	}
	
}

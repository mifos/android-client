package org.mifos.androidclient.test.utils;

import junit.framework.Assert;
import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class ExpandableListViewHelper {

	private final Activity mActivity;
	private final Instrumentation mInstrumentation;
	private final ExpandableListView mListView;
	
	public ExpandableListViewHelper(Activity activity, Instrumentation instrumentation,
			ExpandableListView listView) {
		mActivity = activity;
		mInstrumentation = instrumentation;
		mListView = listView;
	}
	
	public void expandGroup(final int groupPos) {
		mActivity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mListView.expandGroup(groupPos);
			}
		});
		mInstrumentation.waitForIdleSync();
	}
	
	public void verifyChildsTextView(int childIndex, int textViewId, String expectedValue) {
		View view = mListView.getChildAt(childIndex);
		TextView textView = (TextView) view.findViewById(textViewId);
		Assert.assertEquals(expectedValue, textView.getText());
	}
	
}

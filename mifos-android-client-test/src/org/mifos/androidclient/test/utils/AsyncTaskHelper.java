package org.mifos.androidclient.test.utils;

import android.os.AsyncTask;

@SuppressWarnings("rawtypes") 
public class AsyncTaskHelper {

	private AsyncTask mAsyncTask;
	
	public AsyncTaskHelper(AsyncTask asyncTask) {
		mAsyncTask = asyncTask;
	}
	
	public void waitForFinish(int timeout) {
		long endTimeMillis = System.currentTimeMillis() + 10000;
		while (mAsyncTask.getStatus() == AsyncTask.Status.RUNNING
				&& endTimeMillis > System.currentTimeMillis());
	}
	
}

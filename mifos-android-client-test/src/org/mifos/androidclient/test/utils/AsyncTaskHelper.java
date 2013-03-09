package org.mifos.androidclient.test.utils;

import android.os.AsyncTask;

@SuppressWarnings("rawtypes") 
public class AsyncTaskHelper {

	private AsyncTask mAsyncTask;
	
	public AsyncTaskHelper(AsyncTask asyncTask) {
		mAsyncTask = asyncTask;
	}
	
	public void waitForFinish() {
		while (mAsyncTask.getStatus() == AsyncTask.Status.RUNNING);
	}
	
}

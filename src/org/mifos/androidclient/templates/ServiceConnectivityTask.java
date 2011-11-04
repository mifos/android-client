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

package org.mifos.androidclient.templates;

import android.content.Context;
import android.os.AsyncTask;
import org.mifos.androidclient.R;
import org.mifos.androidclient.util.ui.UIUtils;
import org.springframework.web.client.RestClientException;

/**
 * A base task used for asynchronous communication with the Mifos server.<br />
 * Allows to specify the title and message for the progress dialog displayed when
 * the task is in progress.
 */
public abstract class ServiceConnectivityTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    protected Context mContext;
    protected UIUtils mUIUtils;

    private String[] mCauses;
    private String mErrorCause;
    private final Object mLock;
    private String mProgressTitle;
    private String mProgressMessage;

    public ServiceConnectivityTask(Context context, String progressTitle, String progressMessage) {
        mContext = context;
        mUIUtils = new UIUtils(mContext);
        mLock = new Object();
        mCauses = new String[2];
        mCauses[0] = mContext.getString(R.string.toast_connection_error);
        mCauses[1] = mContext.getString(R.string.toast_address_invalid);
        mProgressTitle = progressTitle;
        mProgressMessage = progressMessage;
    }

    protected abstract Result doInBackgroundBody(Params... params) throws RestClientException, IllegalArgumentException;

    protected abstract void onPostExecuteBody(Result result);

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mUIUtils.displayProgressDialog(mProgressTitle, mProgressMessage, false);
    }

    @Override
    protected Result doInBackground(Params... params) {
        Result result;
        try {
            result = doInBackgroundBody(params);
        } catch (RestClientException e) {
            setErrorCause(mCauses[0]);
            return null;
        } catch (IllegalArgumentException e) {
            setErrorCause(mCauses[1]);
            return null;
        }
        return result;
    }

    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        mUIUtils.cancelProgressDialog();
        if (result != null) {
            onPostExecuteBody(result);
        } else {
            mUIUtils.displayLongMessage(getErrorCause());
        }
    }

    private void setErrorCause(String cause) {
        synchronized (mLock) {
            mErrorCause = cause;
        }
    }

    private String getErrorCause() {
        synchronized (mLock) {
            return mErrorCause;
        }
    }

}

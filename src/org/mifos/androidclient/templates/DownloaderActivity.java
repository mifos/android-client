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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import org.mifos.androidclient.R;
import org.mifos.androidclient.entities.SessionStatus;
import org.mifos.androidclient.main.LoginActivity;
import org.mifos.androidclient.net.services.SessionStatusService;
import org.springframework.web.client.RestClientException;

/**
 * A base class for activities which will download data from the Mifos server.<br />
 * A check for the availability of user session is performed in the activity's
 * onStart method.
 */
public abstract class DownloaderActivity extends MifosActivity {

    private SessionStatusService mSessionStatusService;
    private SessionStatusCheckTask mSessionStatusCheckTask;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mSessionStatusService = new SessionStatusService(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        runSessionStatusCheckTask();
    }

    private void runSessionStatusCheckTask() {
        if (mSessionStatusCheckTask == null || mSessionStatusCheckTask.getStatus() != AsyncTask.Status.RUNNING) {
            mSessionStatusCheckTask = new SessionStatusCheckTask(this, getString(R.string.dialog_checking_session), getString(R.string.dialog_login_message));
            mSessionStatusCheckTask.execute((Void[])null);
        }
    }

    /**
     * Called when the session status check completes successfully.
     */
    protected void onSessionActive() { }

    private class SessionStatusCheckTask extends ServiceConnectivityTask<Void, Void, Boolean> {

        public SessionStatusCheckTask(Context context, String progressTitle, String progressMessage) {
            super(context, progressTitle, progressMessage);
        }

        @Override
        protected Boolean doInBackgroundBody(Void... params) throws RestClientException, IllegalArgumentException {
            SessionStatus status = mSessionStatusService.getSessionStatus();
            return status.isSuccessful();
        }

        @Override
        protected void onPostExecuteBody(Boolean result) {
            if (!result) {
                Intent intent = new Intent().setClass(mContext, LoginActivity.class);
                startActivity(intent);
            } else {
                onSessionActive();
            }
        }

    }

}

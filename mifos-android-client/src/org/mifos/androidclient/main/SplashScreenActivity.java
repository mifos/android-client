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

package org.mifos.androidclient.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import org.mifos.androidclient.R;

public class SplashScreenActivity extends Activity {

    public static final int SPLASH_TIME = 3000;

    private boolean mActive;
    private Thread mSplashScreenThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        mSplashScreenThread = new Thread() {

            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(SPLASH_TIME);
                    }
                } catch (InterruptedException e) {
                }
                finally {
                    finish();
                    Intent intent = new Intent().setClass(SplashScreenActivity.this, ClientMainActivity.class);
                    startActivity(intent);
                    return;
                }

            }

        };

        mSplashScreenThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            synchronized (mSplashScreenThread) {
                mSplashScreenThread.notifyAll();
            }
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        synchronized (mSplashScreenThread) {
            mSplashScreenThread.notifyAll();
        }
    }

}

package com.ac07.oxygen;

import android.app.Activity;

import static com.ac07.oxygen.Logorg.setLogNode;

public class LoggerActivity extends Activity{

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        initializeLogging();
    }


    private void initializeLogging() {
//        LogFragment logFragment = (LogFragment) getFragmentManager().findFragmentById(R.id.framelog);

        LogCatWrapper logcat = new LogCatWrapper();
//        logcat.setNext(logFragment.getLogView());

        setLogNode((Logorg.LogNode) logcat);
    }
}

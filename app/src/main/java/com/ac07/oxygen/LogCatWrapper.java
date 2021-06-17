package com.ac07.oxygen;
public class LogCatWrapper implements Logorg.LogNode {

    private Logorg.LogNode mNext;

    public Logorg.LogNode getNext() {
        return mNext;
    }

    public void setNext(Logorg.LogNode node) {
        mNext = node;
    }

    @Override
    public void println(int priority, String tag, String msg, Throwable tr) {
        String useMsg = msg;
        if (useMsg == null) {
            useMsg = "";
        }

        if (tr != null) {
            useMsg += "\n" + android.util.Log.getStackTraceString(tr);
        }

        android.util.Log.println(priority, tag, useMsg);

        if (mNext != null) {
            mNext.println(priority, tag, msg, tr);
        }
    }

}

package com.ac07.oxygen;

import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Date;

@SuppressLint("AppCompatCustomView")
public class LogView extends TextView {
    private final Handler mHandler = new Handler();

    public LogView(Context context) {
        super(context);
    }

    public LogView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LogView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void printLog(final String msg) {
        final StringBuilder builder = new StringBuilder();
        DateFormat formatter = SimpleDateFormat.getDateTimeInstance();
        String time = formatter.format(new Date(System.currentTimeMillis()));
        builder.append(time);
        builder.append("\n");
        builder.append(msg);
        builder.append(System.lineSeparator());

        mHandler.post(() -> append("\n" + builder.toString()));
    }
}

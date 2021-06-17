package com.ac07.oxygen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.huawei.hms.kit.awareness.barrier.AwarenessBarrier;
import com.huawei.hms.kit.awareness.barrier.BarrierStatus;
import com.huawei.hms.kit.awareness.barrier.TimeBarrier;

import java.util.TimeZone;

public class TimeBarrierActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String IN_SUNRISE_OR_SUNSET_PERIOD_BARRIER_LABEL = "sunset barrier label";
    private static final String DURING_PERIOD_OF_DAT_BARRIER_LABEL = "period of day barrier label";
    private static final String DURING_TIME_PERIOD_BARRIER_LABEL = "time period barrier label";
    private static final String DURING_PERIOD_OF_WEEK_BARRIER_LABEL = "period of week barrier label";
    private static final String IN_TIME_CATEGORY_LABEL = "in time category label";

    private LogView mLogView;
    private ScrollView mScrollView;
    private PendingIntent mPendingIntent;
    private TimeBarrierReceiver mBarrierReceiver;
    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_barrier);
        initView();
        myDialog = new Dialog(this);

        final String barrierReceiverAction = getApplication().getPackageName() + "TIME_BARRIER_RECEIVER_ACTION";
        Intent intent = new Intent(barrierReceiverAction);
        // You can also create PendingIntent with getActivity() or getService().
        // This depends on what action you want Awareness Kit to trigger when the barrier status changes.
        mPendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Register a broadcast receiver to receive the broadcast sent by Awareness Kit when the barrier status changes.
        mBarrierReceiver = new TimeBarrierReceiver();
        registerReceiver(mBarrierReceiver, new IntentFilter(barrierReceiverAction));
    }
    public void ShowPopup(View v) {
        TextView txtclose;
        myDialog.setContentView(R.layout.custompopup);
        txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
        txtclose.setText("X");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }

    private void initView() {
        findViewById(R.id.add_timeBarrier_inSunriseOrSunsetPeriod).setOnClickListener(this);
        findViewById(R.id.add_timeBarrier_duringPeriodOfDay).setOnClickListener(this);
//        findViewById(R.id.add_timeBarrier_duringTimePeriod).setOnClickListener(this);
        findViewById(R.id.add_timeBarrier_duringPeriodOfWeek).setOnClickListener(this);
        findViewById(R.id.add_timeBarrier_inTimeCategory).setOnClickListener(this);
//        findViewById(R.id.delete_barrier).setOnClickListener(this);
        findViewById(R.id.clear_log).setOnClickListener(this);
        mLogView = findViewById(R.id.logView);
        mScrollView = findViewById(R.id.log_scroll);
    }

    @Override
    public void onClick(View v) {
        long oneHourMilliSecond = 60 * 60 * 1000L;
        switch (v.getId()) {
            case R.id.add_timeBarrier_inSunriseOrSunsetPeriod:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                AwarenessBarrier sunsetBarrier = TimeBarrier.inSunriseOrSunsetPeriod(TimeBarrier.SUNSET_CODE,
                        -oneHourMilliSecond, oneHourMilliSecond);
                Utils.addBarrier(this, IN_SUNRISE_OR_SUNSET_PERIOD_BARRIER_LABEL,
                        sunsetBarrier, mPendingIntent);
                break;

            case R.id.add_timeBarrier_duringPeriodOfDay:
                AwarenessBarrier periodOfDayBarrier = TimeBarrier.duringPeriodOfDay(TimeZone.getDefault(),
                        11 * oneHourMilliSecond, 12 * oneHourMilliSecond);
                Utils.addBarrier(this, DURING_PERIOD_OF_DAT_BARRIER_LABEL, periodOfDayBarrier,
                        mPendingIntent);
                break;

//            case R.id.add_timeBarrier_duringTimePeriod:
//                long currentTimeStamp = System.currentTimeMillis();
//                long tenSecondsMillis = 10 * 1000L;
//                AwarenessBarrier timePeriodBarrier = TimeBarrier.duringTimePeriod(currentTimeStamp,
//                        currentTimeStamp + tenSecondsMillis);
//                Utils.addBarrier(this, DURING_TIME_PERIOD_BARRIER_LABEL,
//                        timePeriodBarrier, mPendingIntent);
//                break;

            case R.id.add_timeBarrier_duringPeriodOfWeek:
                AwarenessBarrier periodOfWeekBarrier = TimeBarrier.duringPeriodOfWeek(TimeBarrier.MONDAY_CODE,
                        TimeZone.getDefault(), 9 * oneHourMilliSecond, 10 * oneHourMilliSecond);
                Utils.addBarrier(this, DURING_PERIOD_OF_WEEK_BARRIER_LABEL,
                        periodOfWeekBarrier, mPendingIntent);
                break;

            case R.id.add_timeBarrier_inTimeCategory:
                AwarenessBarrier inTimeCategoryBarrier = TimeBarrier.inTimeCategory(TimeBarrier.TIME_CATEGORY_WEEKEND);
                Utils.addBarrier(this, IN_TIME_CATEGORY_LABEL, inTimeCategoryBarrier, mPendingIntent);
                break;

//            case R.id.delete_barrier:
//                Utils.deleteBarrier(this, mPendingIntent);
//                break;

            case R.id.clear_log:
                mLogView.setText("");
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBarrierReceiver != null) {
            unregisterReceiver(mBarrierReceiver);
        }
    }

    final class TimeBarrierReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            BarrierStatus barrierStatus = BarrierStatus.extract(intent);
            String label = barrierStatus.getBarrierLabel();
            int barrierPresentStatus = barrierStatus.getPresentStatus();
            switch (label) {
                case IN_SUNRISE_OR_SUNSET_PERIOD_BARRIER_LABEL:
                    if (barrierPresentStatus == BarrierStatus.TRUE) {
                        mLogView.printLog("Sunset Time, Don't forget to irrigate. \uD83D\uDE09");
                    } else if (barrierPresentStatus == BarrierStatus.FALSE) {
                        mLogView.printLog("May be Dawn, Sunrise or Midday or Noon or Night. It's your wish to irrigate. \uD83D\uDE01");
                    } else {
                        mLogView.printLog("Time Unknown");
                    }
                    break;

                case DURING_PERIOD_OF_DAT_BARRIER_LABEL:
                    if (barrierPresentStatus == BarrierStatus.TRUE) {
                        mLogView.printLog("Yay! Its time to Irrigate Crops.\uD83D\uDE09 ");
                    } else if (barrierPresentStatus == BarrierStatus.FALSE) {
                        mLogView.printLog("Irrigate on your will. \uD83D\uDE2C");
                    } else {
                        mLogView.printLog("Time Unknown.");
                    }
                    break;
//                case DURING_TIME_PERIOD_BARRIER_LABEL:
//                    if (barrierPresentStatus == BarrierStatus.TRUE) {
//                        mLogView.printLog("You irrigated farm few moments ago.");
//                    } else if (barrierPresentStatus == BarrierStatus.FALSE) {
//                        mLogView.printLog("Time to irrigate farm");
//                    } else {
//                        mLogView.printLog("The time status is unknown.");
//                    }
//                    break;

                case DURING_PERIOD_OF_WEEK_BARRIER_LABEL:
                    if (barrierPresentStatus == BarrierStatus.TRUE) {
                        mLogView.printLog("Hurry! It's MONDAY , Don't forget about market. \uD83C\uDFC3");
                    } else if (barrierPresentStatus == BarrierStatus.FALSE) {
                        mLogView.printLog("Feel Free,\uD83E\uDD17 It's a normal day!!");
                    } else {
                        mLogView.printLog("Unknown Status");
                    }
                    break;

                case IN_TIME_CATEGORY_LABEL:
                    if (barrierPresentStatus == BarrierStatus.TRUE) {
                        mLogView.printLog("It's weekend, Get to know about current market!\uD83D\uDE09");
                    } else if (barrierPresentStatus == BarrierStatus.FALSE) {
                        mLogView.printLog("Take Care of farm in weekdays. \uD83D\uDE42");
                    } else {
                        mLogView.printLog("Have Fun!");
                    }
                    break;

                default:
                    break;
            }
            mScrollView.postDelayed(()-> mScrollView.smoothScrollTo(0,mScrollView.getBottom()),200);
        }
    }
}
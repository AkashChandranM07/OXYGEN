package com.ac07.oxygen;

import android.app.PendingIntent;

import com.huawei.hms.kit.awareness.barrier.AwarenessBarrier;

public class BarrierEntity {
    private String barrierLabel;
    private AwarenessBarrier barrier;
    private PendingIntent pendingIntent;

    public void setBarrierLabel(String barrierLabel) {
        this.barrierLabel = barrierLabel;
    }

    public void setBarrier(AwarenessBarrier barrier) {
        this.barrier = barrier;
    }

    public void setPendingIntent(PendingIntent pendingIntent) {
        this.pendingIntent = pendingIntent;
    }

    public String getBarrierLabel() {
        return barrierLabel;
    }

    public AwarenessBarrier getBarrier() {
        return barrier;
    }

    public PendingIntent getPendingIntent() {
        return pendingIntent;
    }
}

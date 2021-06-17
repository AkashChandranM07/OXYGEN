package com.ac07.oxygen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.banner.BannerView;
import com.huawei.hms.kit.awareness.Awareness;
import com.huawei.hms.kit.awareness.capture.WeatherStatusResponse;
import com.huawei.hms.kit.awareness.status.WeatherStatus;
import com.huawei.hms.kit.awareness.status.weather.Situation;
import com.huawei.hms.kit.awareness.status.weather.WeatherSituation;

import java.util.ArrayList;
import java.util.List;

public class Environment1 extends AppCompatActivity implements View.OnClickListener {
    private static final int PERMISSION_REQUEST_CODE=940;
    private final String[] mPermissionsOnHigherVersion=new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACTIVITY_RECOGNITION};
    private final String[] mPermissionsOnLowerVersion=new String[]{Manifest.permission.ACCESS_FINE_LOCATION,"com.huawei.hms.permission.ACTIVITY_RECOGNITION"};
    private List<String> mPermissionsDoNotGrant=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_environment1);
        findViewById(R.id.weather).setOnClickListener(this);
        findViewById(R.id.locateme).setOnClickListener(this);
        findViewById(R.id.maps).setOnClickListener(this);
        HwAds.init(this);

        // Obtain BannerView based on the configuration in layout/ad_fragment.xml.
        BannerView bottomBannerView = findViewById(R.id.hw_banner_view);
        AdParam adParam = new AdParam.Builder().build();
        bottomBannerView.loadAd(adParam);

        // Call new BannerView(Context context) to create a BannerView class.
        BannerView topBannerView = new BannerView(this);
        topBannerView.setAdId("testw6vs28auh3");
        topBannerView.setBannerAdSize(BannerAdSize.BANNER_SIZE_SMART);
        //topBannerView.loadAd(adParam);

        RelativeLayout rootView = findViewById(R.id.root_view);
        //rootView.addView(topBannerView);
        checkAndRequestPermissions();
    }

    private void checkAndRequestPermissions() {
        mPermissionsDoNotGrant.clear();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            for(String permission:mPermissionsOnHigherVersion){
                if(ActivityCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED){
                    mPermissionsDoNotGrant.add(permission);
                }
            }
        }
        else {
            for(String permission:mPermissionsOnLowerVersion){
                if(ActivityCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED){
                    mPermissionsDoNotGrant.add(permission);
                }
            }
        }
        if(mPermissionsDoNotGrant.size()>0){
            ActivityCompat.requestPermissions(this,mPermissionsDoNotGrant.toArray(new String[0]),PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.weather:
                Intent intent=new Intent(this,FetchWeather.class);
                startActivity(intent);
                break;
            case R.id.locateme:
                Intent intent2=new Intent(this,LOCmain.class);
                startActivity(intent2);
                break;
            case R.id.maps:
                Intent intent3=new Intent(this,MapKit.class);
                startActivity(intent3);
                break;
            default:
                break;
        }
    }
}
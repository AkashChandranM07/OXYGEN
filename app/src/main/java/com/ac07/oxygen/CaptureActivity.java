package com.ac07.oxygen;

//import android.Manifest;
//import android.content.pm.PackageManager;
//import android.location.Location;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ScrollView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.ActivityCompat;
//
//import com.huawei.hmf.tasks.OnFailureListener;
//import com.huawei.hmf.tasks.OnSuccessListener;
//import com.huawei.hms.kit.awareness.Awareness;
//import com.huawei.hms.kit.awareness.capture.AmbientLightResponse;
//import com.huawei.hms.kit.awareness.capture.BeaconStatusResponse;
//import com.huawei.hms.kit.awareness.capture.BehaviorResponse;
//import com.huawei.hms.kit.awareness.capture.BluetoothStatusResponse;
//import com.huawei.hms.kit.awareness.capture.HeadsetStatusResponse;
//import com.huawei.hms.kit.awareness.capture.LocationResponse;
//import com.huawei.hms.kit.awareness.capture.TimeCategoriesResponse;
//import com.huawei.hms.kit.awareness.capture.WeatherStatusResponse;
//import com.huawei.hms.kit.awareness.status.AmbientLightStatus;
//import com.huawei.hms.kit.awareness.status.BeaconStatus;
//import com.huawei.hms.kit.awareness.status.BehaviorStatus;
//import com.huawei.hms.kit.awareness.status.BluetoothStatus;
//import com.huawei.hms.kit.awareness.status.DetectedBehavior;
//import com.huawei.hms.kit.awareness.status.HeadsetStatus;
//import com.huawei.hms.kit.awareness.status.TimeCategories;
//import com.huawei.hms.kit.awareness.status.WeatherStatus;
//import com.huawei.hms.kit.awareness.status.weather.Situation;
//import com.huawei.hms.kit.awareness.status.weather.WeatherSituation;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class CaptureActivity extends AppCompatActivity implements View.OnClickListener {
//    private final String TAG = getClass().getSimpleName();
//
//    private ScrollView mScrollView;
//    private LogView mLogView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_capture);
//        mLogView = findViewById(R.id.logView);
//        mScrollView = findViewById(R.id.root_scrollView);
//        findViewById(R.id.capture_getLocation).setOnClickListener(this);
//
//        findViewById(R.id.clear_log).setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.capture_getLocation:
//                getLocation();
//                break;
//            case R.id.clear_log:
//                mLogView.setText("");
//                break;
//            default:
//                break;
//        }
//    }
//
//
//
//    private void getLocation() {
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        Awareness.getCaptureClient(this).getLocation()
//                .addOnSuccessListener(new OnSuccessListener<LocationResponse>() {
//                    @Override
//                    public void onSuccess(LocationResponse locationResponse) {
//                        Location location = locationResponse.getLocation();
//                        mLogView.printLog("Longitude of your Area is " + location.getLongitude() +
//                                "\n" + ",Latitude of your Area is " + location.getLatitude());
//                        scrollToBottom();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(Exception e) {
//                        mLogView.printLog("Failed to access location , Turn on GPS on your device");
//                    }
//                });
//    }
//
//
//    private void scrollToBottom() {
//        mScrollView.postDelayed(() -> mScrollView.smoothScrollTo(0, mScrollView.getBottom()), 200);
//    }
//}

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.spark.submitbutton.SubmitButton;

import java.util.List;
import java.util.Locale;

public class CaptureActivity extends AppCompatActivity implements LocationListener {
    View view;

    SubmitButton button_location;
    LocationManager locationManager;
    TextView textView_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);
        //gurubutton
//        view=findViewById(R.id.getaddress);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ProgressButton progressButton=new ProgressButton(CaptureActivity.this,view);
//                progressButton.buttonActivated();
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressButton.buttonFinished();
//                    }
//                },5000);
//            }
//        });
        //end
        textView_location = findViewById(R.id.text_location);
        button_location = findViewById(R.id.button_location);
        //Runtime permissions
        if (ContextCompat.checkSelfPermission(CaptureActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(CaptureActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }


        button_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create method
                getLocation();
            }
        });



    }

    @SuppressLint("MissingPermission")
    private void getLocation() {

        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,5,CaptureActivity.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(CaptureActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            textView_location.setText(address);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}







package com.ac07.oxygen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.hmf.tasks.OnFailureListener;
import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hms.kit.awareness.Awareness;
import com.huawei.hms.kit.awareness.capture.WeatherStatusResponse;
import com.huawei.hms.kit.awareness.status.WeatherStatus;
import com.huawei.hms.kit.awareness.status.weather.Situation;
import com.huawei.hms.kit.awareness.status.weather.WeatherSituation;

public class FetchWeather extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    TextView city,temp,weatherid,windspeed,winddir,humidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_weather);
        findViewById(R.id.btnweather).setOnClickListener(this);
        city = findViewById(R.id.city);
        temp = findViewById(R.id.temp);
        weatherid = findViewById(R.id.weatherid);
        windspeed = findViewById(R.id.windspeed);
        winddir = findViewById(R.id.winddir);
        humidity = findViewById(R.id.humidity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnweather:
                getWeather();
                break;
            default:
                break;
        }
    }

    @SuppressLint("MissingPermission")
    private void getWeather() {
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
        Awareness.getCaptureClient(this).getWeatherByDevice()
                // Callback listener for execution success.
                .addOnSuccessListener(new OnSuccessListener<WeatherStatusResponse>() {
                    @Override
                    public void onSuccess(WeatherStatusResponse weatherStatusResponse) {
                        WeatherStatus weatherStatus = weatherStatusResponse.getWeatherStatus();
                        WeatherSituation weatherSituation = weatherStatus.getWeatherSituation();
                        Situation situation = weatherSituation.getSituation();
                        // For more weather information, please refer to the API Reference of Awareness Kit.
                        String weatherInfoStr1 = "You're Living in " + weatherSituation.getCity().getName() ;
                        String weatherInfoStr2="Temperature is " + situation.getTemperatureC() + " ℃" + "\n" + situation.getTemperatureF() + " ℉";
                        String weatherInfoStr4="Wind speed in your surrounding is " + situation.getWindSpeed() + " km/h" ;
                        String weatherInfoStr5="Wind direction is " + situation.getWindDir() ;
                        String weatherInfoStr6="Humidity is captured as " + situation.getHumidity() + " %";
                        String weatherInfoStr3="Weather ID is found to be " + situation.getWeatherId() ;
                        city.setText(weatherInfoStr1);
                        temp.setText(weatherInfoStr2);
                        weatherid.setText(weatherInfoStr3);
                        windspeed.setText(weatherInfoStr4);
                        winddir.setText(weatherInfoStr5);
                        humidity.setText(weatherInfoStr6);

                    }
                })
        // Callback listener for execution failure.
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Log.e(TAG, "get weather failed");
                        Toast.makeText(FetchWeather.this,"Failed to fetch weather, turn on GPS on your device.",Toast.LENGTH_SHORT).show();
                    }

                });
    }
}
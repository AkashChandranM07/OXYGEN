package com.ac07.oxygen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.banner.BannerView;


public class Cropplan extends AppCompatActivity {
    Button maize, corn, potato, tomato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cropplan);
        //after
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

















        //before
        maize = findViewById(R.id.maize1);
        corn=findViewById(R.id.corn1);
        tomato=findViewById(R.id.tomato1);
        potato=findViewById(R.id.potato1);
//        //INTENTS
        maize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Cropplan.this, Maize.class);
                startActivity(i1);
            }
        });
        corn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2= new Intent(Cropplan.this, Corn.class);
                startActivity(i2);
            }
        });
        tomato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3= new Intent(Cropplan.this, Tomato.class);
                startActivity(i3);
            }
        });
        potato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i4= new Intent(Cropplan.this, Potato.class);
                startActivity(i4);
            }
        });
    }
}
package com.ac07.oxygen.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ac07.oxygen.R;

public class CropFeed extends AppCompatActivity {
WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_feed);
        web = findViewById((R.id.cropfeed));
        WebSettings webSettings=web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        web.setWebViewClient(new Cropfeed());
        web.loadUrl("https://www.google.com/amp/s/m.economictimes.com/news/economy/agriculture/amp_articlelist/1202099874.cms");

    }

    private class Cropfeed extends WebViewClient {
        @Override
        public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
            return false;
        }
    }
}

package com.ac07.oxygen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;


public class Maize extends AppCompatActivity {
    TextView wheatlink1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maize);
        wheatlink1 = findViewById(R.id.wheatlink);
        wheatlink1.setMovementMethod(LinkMovementMethod.getInstance());
  }
}
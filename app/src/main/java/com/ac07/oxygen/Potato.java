package com.ac07.oxygen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class Potato extends AppCompatActivity {
    TextView potato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potato);
        potato = findViewById(R.id.potatolink);
        potato.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
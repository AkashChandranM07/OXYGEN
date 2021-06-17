package com.ac07.oxygen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class Tomato extends AppCompatActivity {
    TextView tomato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tomato);
        tomato = findViewById(R.id.tomatolink);
        tomato.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
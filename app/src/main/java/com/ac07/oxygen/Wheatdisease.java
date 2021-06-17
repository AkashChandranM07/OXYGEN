package com.ac07.oxygen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Wheatdisease extends AppCompatActivity {
   Button g1, r1, s1, l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheatdisease);
        g1=findViewById(R.id.grain);
        r1=findViewById(R.id.root);
        s1=findViewById(R.id.stem);
        l1=findViewById(R.id.leaf);
        g1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent w1 = new Intent(Wheatdisease.this, Wgrain.class);
                startActivity(w1);
            }
        });
        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent w2 = new Intent(Wheatdisease.this, Wroot.class);
                startActivity(w2);
            }
        });
        s1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent w3 = new Intent(Wheatdisease.this, Wstem.class);
                startActivity(w3);
            }
        });
        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent w4 = new Intent(Wheatdisease.this, Wleaf.class);
                startActivity(w4);
            }

        });
    }
}

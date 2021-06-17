//package com.ac07.oxygen;
//
//import android.content.Context;
//import android.view.View;
//import android.view.animation.Animation;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import androidx.cardview.widget.CardView;
//import androidx.constraintlayout.widget.ConstraintLayout;
//
//class ProgressButton {
//    private CardView cardView1;
//    private ProgressBar progressBar1;
//    private TextView textView1;
//    private  ConstraintLayout constraintLayout;
//    Animation fade_in;
//    ProgressButton(Context ct , View view)
//    {
//        cardView1=view.findViewById(R.id.cardView);
//        constraintLayout=view.findViewById(R.id.guruanna);
//        progressBar1=view.findViewById(R.id.progressBar);
//        textView1=view.findViewById(R.id.textView24);
//
//
//    }
//    void buttonActivated(){
//        progressBar1.setVisibility(View.VISIBLE);
//        textView1.setText("Loading...");
//    }
//    void buttonFinished(){
//        constraintLayout.setBackgroundColor(cardView1.getResources().getColor(R.color.green));
//        progressBar1.setVisibility(View.GONE);
//        textView1.setText("Here's your Address..!");
//
//    }
//
//}
//

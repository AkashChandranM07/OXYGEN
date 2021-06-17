package com.ac07.oxygen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.huawei.hmf.tasks.OnSuccessListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;
import com.huawei.hms.support.hwid.request.HuaweiIdAuthParams;
import com.huawei.hms.support.hwid.service.HuaweiIdAuthService;

import java.util.Timer;
import java.util.TimerTask;

import static android.view.WindowManager.*;

public class Splash extends AppCompatActivity {

    private HuaweiIdAuthService mHuaweiIdAuthService;
    private HuaweiIdAuthParams mHuaweiIdAuthParams;
    private ProgressBar mProgressBar;
    private TimerTask timer;
    long Delay = 2000;
    CharSequence charSequence;
    int index=200;
    TextView  textView;;
    Handler handler=new Handler();
    private static final String TAG = "SplashActivity";
    public String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        mProgressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.tv);
        animatText("XYGEN");


        silentlySignIn();

        Timer RunSplash = new Timer();
        timer = new TimerTask() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (userName!=null) {
                            mProgressBar.setVisibility(View.INVISIBLE);
                            Intent intent = new Intent(Splash.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
//                            Toast.makeText(Splash.this,"Did not sign out",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Splash.this, Actkit.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
            }
        };
        RunSplash.schedule(timer, Delay);
    }
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            textView.setText(charSequence.subSequence(0,index++));
            if(index <=charSequence.length()){
                long delay1=200;
                handler.postDelayed(runnable,delay1);
            }
        }
    };
    public void animatText(CharSequence cs){
        charSequence=cs;
        index =0;
        textView.setText("");
        handler.removeCallbacks(runnable);
        long delay1=200;
        handler.postDelayed(runnable,delay1);
    }

    private void silentlySignIn(){
        AccountAuthParams authParams = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM).createParams();
        AccountAuthService service = AccountAuthManager.getService(this, authParams);
        Task<AuthAccount> task = service.silentSignIn();
        task.addOnSuccessListener(new OnSuccessListener<AuthAccount>() {
            @Override
            public void onSuccess(AuthAccount authAccount) {
                // Obtain the user's ID information.
                userName = authAccount.getDisplayName();
                Log.i(TAG, "displayName:" + authAccount.getDisplayName());
                // Obtain the ID type (0: HUAWEI ID; 1: AppTouch ID).
                Log.i(TAG, "accountFlag:" + authAccount.getAccountFlag());
            }
        });
    }
}
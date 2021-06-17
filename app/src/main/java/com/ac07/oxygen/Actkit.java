package com.ac07.oxygen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.ads.AdParam;
import com.huawei.hms.ads.BannerAdSize;
import com.huawei.hms.ads.HwAds;
import com.huawei.hms.ads.banner.BannerView;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.result.AuthAccount;
import com.huawei.hms.support.account.service.AccountAuthService;
import com.huawei.hms.support.hwid.ui.HuaweiIdAuthButton;


public class Actkit extends AppCompatActivity implements View.OnClickListener {

    Button btnIdToken,btnNext;
    AccountAuthService serviceIdToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actkit);
        btnIdToken = findViewById(R.id.btnAuthmode);
        btnNext = findViewById(R.id.btnNext1);
        btnIdToken.setOnClickListener(this);
        btnNext.setOnClickListener(this);
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
    }





    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnAuthmode:
                AccountAuthParams authParamsIdToken = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM)
                        .setMobileNumber()
                        .setEmail()
                        .setIdToken().createParams();
                serviceIdToken = AccountAuthManager.getService(Actkit.this, authParamsIdToken);
                startActivityForResult(serviceIdToken.getSignInIntent(), Constant1.SIGN_IN_ID_TOKEN);
//                Intent intent1 = new Intent(Actkit.this,MainActivity.class);
//                startActivity(intent1);
                break;
            case R.id.btnNext1:
                Intent intent1 = new Intent(Actkit.this,MainActivity.class);
               startActivity(intent1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // Process the authorization result and obtain the authorization code from AuthAccount.
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant1.SIGN_IN_ID_TOKEN) {
            Task<AuthAccount> authAccountTask = AccountAuthManager.parseAuthResultFromIntent(data);
            if (authAccountTask.isSuccessful()) {
                // The sign-in is successful, and the user's ID information and ID token are obtained.
                AuthAccount authAccount = authAccountTask.getResult();
                Intent intent = new Intent(Actkit.this,MainActivity.class);
                startActivity(intent);
                Log.i(MainActivity.class.getSimpleName(), "idToken:" + authAccount.getIdToken());
                Log.i(MainActivity.class.getSimpleName(), "idToken:" + authAccount.getEmail());
                Log.i(MainActivity.class.getSimpleName(), "idToken:" + authAccount.getDisplayName());
                // Obtain the ID type (0: HUAWEI ID; 1: AppTouch ID).
                Log.i(MainActivity.class.getSimpleName(), "accountFlag:" + authAccount.getAccountFlag());
            } else {
                // The sign-in failed. No processing is required. Logs are recorded for fault locating.
                Log.e(MainActivity.class.getSimpleName(), "sign in failed : " +((ApiException)authAccountTask.getException()).getStatusCode());
            }
        }
    }
}
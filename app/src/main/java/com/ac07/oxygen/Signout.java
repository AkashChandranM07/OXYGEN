package com.ac07.oxygen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.huawei.hmf.tasks.OnCompleteListener;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.support.account.AccountAuthManager;
import com.huawei.hms.support.account.request.AccountAuthParams;
import com.huawei.hms.support.account.request.AccountAuthParamsHelper;
import com.huawei.hms.support.account.service.AccountAuthService;


public class Signout extends AppCompatActivity {
    Button btnSignOut;
    AccountAuthService serviceIdToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signout);
        btnSignOut = findViewById(R.id.signout);
        AccountAuthParams authParamsIdToken = new AccountAuthParamsHelper(AccountAuthParams.DEFAULT_AUTH_REQUEST_PARAM).createParams();
        serviceIdToken = AccountAuthManager.getService(this, authParamsIdToken);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Task<Void> signOutTask = serviceIdToken.signOut();
                //               Intent signoutIntent = new Intent(HomeActivity.this,MainActivity.class);
//                Toast.makeText(HomeActivity.this,"Sign out",Toast.LENGTH_SHORT).show();
//                startActivity(signoutIntent);

                //AuthService service = null;
//                Task<Void> signOutTask = service.signOut();
                signOutTask.addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        serviceIdToken.cancelAuthorization();
                        // service indicates the AccountAuthService instance generated using the getService method during the sign-in authorization.
                        serviceIdToken.cancelAuthorization().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Processing after a successful authorization revoking.
                                    Log.i("", "onSuccess: ");
                                } else {
                                    // Handle the exception.
                                    Exception exception = task.getException();
                                    if (exception instanceof ApiException){
                                        int statusCode = ((ApiException) exception).getStatusCode();
                                        Log.i("", "onFailure: " + statusCode);
                                    }
                                }
                            }
                        });
                        Log.i(MainActivity.class.getSimpleName(), "signOut complete");
                        Toast.makeText(getApplicationContext(),"Signed Out",Toast.LENGTH_LONG).show();
                        Intent signoutIntent = new Intent(Signout.this,Actkit.class);
                        startActivity(signoutIntent);
                        // Processing after the sign-out.
                        Log.i("", "signOut complete");
                        finish();
                    }
                });

            }
        });
    }


}

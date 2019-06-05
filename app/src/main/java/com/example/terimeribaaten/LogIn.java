package com.example.terimeribaaten;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class LogIn extends AppCompatActivity {

    private static final int RC_SIGN_IN = 4;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Arrays.asList(
//                                   new AuthUI.IdpConfig.GoogleBuilder().build(),
//                                new AuthUI.IdpConfig.FacebookBuilder().build(),
//                                new AuthUI.IdpConfig.TwitterBuilder().build(),
//                                new AuthUI.IdpConfig.GitHubBuilder().build(),//
//                                new AuthUI.IdpConfig.EmailBuilder().build(),
                               new AuthUI.IdpConfig.PhoneBuilder().build())).build(),
                    RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==RC_SIGN_IN && resultCode == RESULT_OK){
            Intent intent = new Intent(LogIn.this,MainActivity.class);
            startActivity(intent);
        }
    }
}

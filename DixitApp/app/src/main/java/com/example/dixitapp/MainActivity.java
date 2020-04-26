package com.example.dixitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private Globals globals;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        context = getApplicationContext();
        globals = (Globals)getApplication();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        globals.setAnimDelay(800);

        final FirebaseUser user = mAuth.getCurrentUser();

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        AuthCredential credential;
        if (account != null)
        {
            credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        }
        else
        {
            credential = null;
        }

        if (user == null)
        {
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
        }
        else
        {
            user.reload();

            if (SignInActivity.loggedInWithEmail)
            {
                if (user.isEmailVerified())
                {
                    Intent intent = new Intent(MainActivity.this, AppActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent = new Intent(MainActivity.this, EmailVerificationActivity.class);
                    startActivity(intent);
                }
            }
            else
            {
                if (credential != null)
                {
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        UserAccess.checkEmailExist(user.getEmail(), new UserAccess.ExistCallback() {
                                            @Override
                                            public void onCallback(int status) {
                                                Intent intent;

                                                switch (status) {
                                                    case UserAccess.Constants.STATUS_USER_EXIST:
                                                        intent = new Intent(MainActivity.this, AppActivity.class);
                                                        startActivity(intent);
                                                        break;

                                                    case UserAccess.Constants.STATUS_USER_NOT_EXIST:
                                                        intent = new Intent(MainActivity.this, CreateAccActivity.class);
                                                        startActivity(intent);
                                                        break;

                                                    case UserAccess.Constants.STATUS_KO:
                                                        Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                                        break;
                                                }
                                            }
                                        });
                                    } else
                                        Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        }
    }
}

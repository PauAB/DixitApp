package com.example.dixitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class AppActivity extends AppCompatActivity {

    private TextView textViewLogOutApp;
    private TextView textViewDeleteAccApp;

    Context context;
    private String toastText;
    private int toastDuration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        context = getApplicationContext();

        textViewLogOutApp = findViewById(R.id.textViewLogOutApp);
        textViewDeleteAccApp = findViewById(R.id.textViewDeleteAccApp);

        textViewLogOutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInGoogleActivity.signedOut = true;

                toastText = "Logging Out...";
                Toast toast = Toast.makeText(context, toastText, toastDuration);
                toast.show();

                Intent intent = new Intent(AppActivity.this, SignInGoogleActivity.class);
                startActivity(intent);
            }
        });

        textViewDeleteAccApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastText = "Authenticating user...";
                Toast toast = Toast.makeText(context, toastText, toastDuration);
                toast.show();

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                AuthCredential credential = GoogleAuthProvider.getCredential(user.getUid(), null);

                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        toastText = "User deleted.";
                                        Toast toast = Toast.makeText(context, toastText, toastDuration);
                                        toast.show();

                                        Intent intent = new Intent(AppActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else
                                    {
                                        toastText = "Error. User can't be deleted.";
                                        Toast toast = Toast.makeText(context, toastText, toastDuration);
                                        toast.show();
                                    }
                                }
                            });
                        }
                        else
                        {
                            toastText = "Error. Authentication failed";
                            Toast toast = Toast.makeText(context, toastText, toastDuration);
                            toast.show();

                            Log.w("User", "Failed", task.getException());
                        }
                    }
                });
            }
        });
    }
}

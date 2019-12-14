package com.example.dixitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignInActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    private Guideline guidelineVerStart;
    private Guideline guidelineVerEnd;
    private Guideline guidelineHorStart;
    private Guideline guidelineHorEnd;

    static final float START_VER_POS = 0f;
    static final float END_VER_POS = 1f;
    static final float START_HOR_POS = 0f;
    static final float END_HOR_POS = 1f;

    private ImageView imageViewGoogleSignIn;
    private TextView textViewEmailBackground;
    private TextView textViewPasswordBackground;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewLogIn;
    private TextView textViewCreateAcc;

    private String email;
    private String password;

    static boolean loggedIn = false;
    static boolean loggedInWithEmail = true;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        context = getApplicationContext();

        mAuth = FirebaseAuth.getInstance();

        guidelineVerStart = findViewById(R.id.guidelineVerStart);
        guidelineVerEnd = findViewById(R.id.guidelineVerEnd);
        guidelineHorStart = findViewById(R.id.guidelineHorStart);
        guidelineHorEnd = findViewById(R.id.guidelineHorEnd);

        guidelineVerStart.setGuidelinePercent(START_VER_POS);
        guidelineVerEnd.setGuidelinePercent(END_VER_POS);
        guidelineHorStart.setGuidelinePercent(START_HOR_POS);
        guidelineHorEnd.setGuidelinePercent(END_HOR_POS);

        imageViewGoogleSignIn = findViewById(R.id.imageViewGoogleSignIn);
        textViewEmailBackground = findViewById(R.id.textViewEmailBackground);
        textViewPasswordBackground = findViewById(R.id.textViewPasswordBackground);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewLogIn = findViewById(R.id.textViewLogIn);
        textViewCreateAcc = findViewById(R.id.textViewCreateAcc);

        imageViewGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loggedInWithEmail = false;

                Intent intent = new Intent(SignInActivity.this, SignInGoogleActivity.class);
                startActivity(intent);
            }
        });

        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textViewEmailBackground.getVisibility() == View.VISIBLE) textViewEmailBackground.setVisibility(View.INVISIBLE);
                else if (textViewEmailBackground.getVisibility() == View.INVISIBLE && s.length() == 0) textViewEmailBackground.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {
                email = editTextEmail.getText().toString();
            }
        });

        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textViewPasswordBackground.getVisibility() == View.VISIBLE) textViewPasswordBackground.setVisibility(View.INVISIBLE);
                else if (textViewPasswordBackground.getVisibility() == View.INVISIBLE && s.length() == 0) textViewPasswordBackground.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        textViewLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loggedInWithEmail = true;

                password = editTextPassword.getText().toString();

                Toast.makeText(context, "Logging In...", Toast.LENGTH_SHORT).show();

                if (password != null && email != null)
                {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        FirebaseUser user = mAuth.getCurrentUser();

                                        if (user.isEmailVerified())
                                        {
                                            loggedIn = true;

                                            Intent intent = new Intent(SignInActivity.this, AppActivity.class);
                                            startActivity(intent);
                                        }
                                        else
                                            Toast.makeText(context, "Verify your email first.", Toast.LENGTH_SHORT).show();
                                    }
                                    else Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else Toast.makeText(context, "Please, complete all the fields", Toast.LENGTH_SHORT).show();
            }
        });

        textViewCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, CreateAccActivity.class);
                startActivity(intent);
            }
        });
    }
}

package com.example.dixitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.regex.Pattern;

public class CreateAccActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Guideline guidelineVerStart;
    private Guideline guidelineVerEnd;
    private Guideline guidelineHorStart;
    private Guideline guidelineHorEnd;

    static final float START_VER_POS = 0f;
    static final float END_VER_POS = 1f;
    static final float START_HOR_POS = 0f;
    static final float END_HOR_POS = 1f;

    private TextView textViewNameBackground;
    private TextView textViewEmailBackground;
    private TextView textViewPassBackground;
    private TextView textViewPassConfBackground;
    private TextView textViewCreateAcc;
    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordConfirm;
    private ImageView imageViewBack;
    private TextView textViewPassFeedback;

    private String name;
    private String email;
    private String username;
    private String password;
    private String passwordConfirm;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        context = getApplicationContext();

        mAuth = FirebaseAuth.getInstance();

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

        guidelineVerStart = findViewById(R.id.guidelineVerStart);
        guidelineVerEnd = findViewById(R.id.guidelineVerEnd);
        guidelineHorStart = findViewById(R.id.guidelineHorStart);
        guidelineHorEnd = findViewById(R.id.guidelineHorEnd);

        guidelineVerStart.setGuidelinePercent(START_VER_POS);
        guidelineVerEnd.setGuidelinePercent(END_VER_POS);
        guidelineHorStart.setGuidelinePercent(START_HOR_POS);
        guidelineHorEnd.setGuidelinePercent(END_HOR_POS);

        textViewNameBackground = findViewById(R.id.textViewNameBackground);
        textViewEmailBackground = findViewById(R.id.textViewEmailBackground);
        textViewPassBackground = findViewById(R.id.textViewPassBackground);
        textViewPassConfBackground = findViewById(R.id.textViewPassConfBackground);
        textViewCreateAcc = findViewById(R.id.textViewCreateAccConfirm);
        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPasswordConfirm = findViewById(R.id.editTextPasswordConfirm);
        imageViewBack = findViewById(R.id.imageViewBack);
        textViewPassFeedback = findViewById(R.id.textViewPassFeedback);

        textViewPassFeedback.setVisibility(View.INVISIBLE);

        final FirebaseUser currentUser = mAuth.getCurrentUser();

        if (!SignInActivity.loggedInWithEmail)
        {
            if (credential != null) {
                currentUser.reauthenticate(credential)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    name = currentUser.getDisplayName();
                                    email = currentUser.getEmail();

                                    editTextName.setText(name);
                                    editTextEmail.setText(email);

                                    Toast.makeText(context, "User load successful", Toast.LENGTH_SHORT).show();
                                } else
                                    Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }

        editTextName.setFilters(new InputFilter[] {new InputFilter.LengthFilter(24)});
        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textViewNameBackground.getVisibility() == View.VISIBLE) textViewNameBackground.setVisibility(View.INVISIBLE);
                else if (textViewNameBackground.getVisibility() == View.INVISIBLE && s.length() == 0) textViewNameBackground.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                name = editTextName.getText().toString();
            }
        });

        editTextEmail.setFilters(new InputFilter[] {new InputFilter.LengthFilter(24)});
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
                username = email.split("@")[0];
            }
        });

        editTextPassword.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});
        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textViewPassBackground.getVisibility() == View.VISIBLE) textViewPassBackground.setVisibility(View.INVISIBLE);
                else if (textViewPassBackground.getVisibility() == View.INVISIBLE && s.length() == 0) textViewPassBackground.setVisibility(View.VISIBLE);

                if (s.length() > 0) textViewPassFeedback.setVisibility(View.VISIBLE);
                else if (s.length() == 0) textViewPassFeedback.setVisibility(View.INVISIBLE);

                if (s.length() < 8)
                {
                    textViewPassFeedback.setText("Password too short");
                    textViewPassFeedback.setTextColor(getResources().getColor(R.color.redBackground));
                }
                else if (s.length() >= 8 && s.length() < 12)
                {
                    textViewPassFeedback.setText("Low security password");
                    textViewPassFeedback.setTextColor(getResources().getColor(R.color.yellowFont));
                }
                else if (s.length() >= 12 && s.length() < 16)
                {
                    textViewPassFeedback.setText("Medium security password");
                    textViewPassFeedback.setTextColor(getResources().getColor(R.color.orangeFont));
                }
                else if (s.length() >= 18)
                {
                    textViewPassFeedback.setText("High security password");
                    textViewPassFeedback.setTextColor(getResources().getColor(R.color.greenFont));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                password = editTextPassword.getText().toString();
            }
        });

        editTextPasswordConfirm.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});
        editTextPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (!password.equals(passwordConfirm))
                {
                    textViewPassFeedback.setText("Passwords don't match");
                    textViewPassFeedback.setTextColor(getResources().getColor(R.color.redBackground));
                }
                else if (password.equals(passwordConfirm)) textViewPassFeedback.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textViewPassConfBackground.getVisibility() == View.VISIBLE) textViewPassConfBackground.setVisibility(View.INVISIBLE);
                else if (textViewPassConfBackground.getVisibility() == View.INVISIBLE && s.length() == 0) textViewPassConfBackground.setVisibility(View.VISIBLE);

                if (s.length() > 0) textViewPassFeedback.setVisibility(View.VISIBLE);
                else if (s.length() == 0) textViewPassFeedback.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                passwordConfirm = editTextPasswordConfirm.getText().toString();

                if (!password.equals(passwordConfirm))
                {
                    textViewPassFeedback.setText("Passwords don't match");
                    textViewPassFeedback.setTextColor(getResources().getColor(R.color.redBackground));
                }
                else if (password.equals(passwordConfirm)) textViewPassFeedback.setVisibility(View.INVISIBLE);
            }
        });

        textViewCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name != null && email != null && password != null && passwordConfirm != null)
                {
                    Toast.makeText(context, "Creating user...", Toast.LENGTH_SHORT).show();

                    if (password.length() < 8 || passwordConfirm.length() < 8) Toast.makeText(context, "Invalid password. Write at least 8 characters.", Toast.LENGTH_SHORT).show();
                    else if (password.length() >= 8 && passwordConfirm.length() >= 8)
                    {
                        if (password.equals(passwordConfirm))
                        {
                            UserAccess.checkUserExist(name, new UserAccess.ExistCallback() {
                                @Override
                                public void onCallback(int status) {
                                    switch (status) {
                                        case UserAccess.Constants.STATUS_USER_EXIST:
                                            Toast.makeText(context, "Invalid. Name already exists.", Toast.LENGTH_SHORT).show();
                                            break;

                                        case UserAccess.Constants.STATUS_USER_NOT_EXIST:
                                            UserAccess.checkEmailExist(email, new UserAccess.ExistCallback() {
                                                @Override
                                                public void onCallback(int status) {
                                                    switch (status) {
                                                        case UserAccess.Constants.STATUS_USER_EXIST:
                                                            Toast.makeText(context, "Invalid. Email already in use.", Toast.LENGTH_SHORT).show();
                                                       break;

                                                        case UserAccess.Constants.STATUS_USER_NOT_EXIST:
                                                            if (SignInActivity.loggedInWithEmail)
                                                            {
                                                                mAuth.createUserWithEmailAndPassword(email, password)
                                                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    UserAccess.createNewUser(username, name, email, password, new UserAccess.CreateNewUserCallback() {
                                                                                        @Override
                                                                                        public void onCallback(int status) {
                                                                                            if (status == UserAccess.Constants.STATUS_OK) {
                                                                                                Toast.makeText(context, "User created.", Toast.LENGTH_SHORT).show();

                                                                                                Intent intent = new Intent(CreateAccActivity.this, EmailVerificationActivity.class);
                                                                                                startActivity(intent);
                                                                                            } else if (status == UserAccess.Constants.STATUS_KO) {
                                                                                                Toast.makeText(context, "Error. User creation failed.", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        }
                                                                                    });
                                                                                } else {
                                                                                    Toast.makeText(context, "Email already in use", Toast.LENGTH_SHORT).show();
                                                                                    Log.w("User", "Authentication failed.", task.getException());
                                                                                }
                                                                            }
                                                                        });
                                                            }
                                                            else
                                                            {
                                                                UserAccess.createNewUser(username, name, email, password, new UserAccess.CreateNewUserCallback() {
                                                                    @Override
                                                                    public void onCallback(int status) {
                                                                        if (status == UserAccess.Constants.STATUS_OK) {
                                                                            Toast.makeText(context, "User created.", Toast.LENGTH_SHORT).show();

                                                                            Intent intent = new Intent(CreateAccActivity.this, EmailVerificationActivity.class);
                                                                            startActivity(intent);
                                                                        } else if (status == UserAccess.Constants.STATUS_KO) {
                                                                            Toast.makeText(context, "Error. User creation failed.", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                });
                                                            }
                                                            break;
                                                        case UserAccess.Constants.STATUS_KO:
                                                            Toast.makeText(context, "Error. Email authentication failed.", Toast.LENGTH_SHORT).show();
                                                            break;
                                                    }
                                                }
                                            });
                                            break;
                                        case UserAccess.Constants.STATUS_KO:
                                            Toast.makeText(context, "Error. User authentication failed", Toast.LENGTH_SHORT).show();
                                            break;
                                    }
                                }
                            });
                        }
                        else Toast.makeText(context, "Passwords don't match.", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(context, "Invalid password. Write at least 8 characters.", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(context, "Please, complete all the fields.", Toast.LENGTH_SHORT).show();
            }
        });

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}

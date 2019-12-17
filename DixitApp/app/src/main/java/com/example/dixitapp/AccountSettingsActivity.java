package com.example.dixitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Map;

public class AccountSettingsActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    private Guideline guidelineVerStart;
    private Guideline guidelineVerEnd;
    private Guideline guidelineHorStart;
    private Guideline guidelineHorEnd;

    static final float START_VER_POS = 0f;
    static final float END_VER_POS = 1f;
    static final float START_HOR_POS = 0f;
    static final float END_HOR_POS = 1f;

    private TextView textViewChangeEmail;
    private TextView textViewChangePass;
    private TextView textViewDeleteAcc;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ImageView imageViewBack;

    // ANIM VIEWS -----------------------
    private ImageView imageViewDeleteBorder;
    private ImageView imageViewDeleteBackground;
    private TextView textViewMsg2;
    private TextView textViewMsg3;
    private TextView textViewPassBackground;
    private TextView textViewDeleteConfirm;
    private EditText editTextDeletePass;
    private ImageView imageViewDeleteBack;
    // ----------------------------------

    // SEPARATOR ANIM VIEWS ----------------------------------------------
    private ImageView imageViewBaseball;
    private ImageView imageViewBasketball;
    private ImageView imageViewBeachball;
    private ImageView imageViewCricketball;
    private ImageView imageViewFootball;
    private ImageView imageViewPoolball;
    private ImageView imageViewTennisball;
    private ImageView imageViewVolleyball;
    // ---------------------------------------------------------

    private String email;
    private String newEmail;
    private String password;
    private String newPassword;
    private String confirmPassword;
    private String username;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        context = getApplicationContext();

        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser currentUser = mAuth.getCurrentUser();

        guidelineVerStart = findViewById(R.id.guidelineVerStart);
        guidelineVerEnd = findViewById(R.id.guidelineVerEnd);
        guidelineHorStart = findViewById(R.id.guidelineHorStart);
        guidelineHorEnd = findViewById(R.id.guidelineHorEnd);

        guidelineVerStart.setGuidelinePercent(START_VER_POS);
        guidelineVerEnd.setGuidelinePercent(END_VER_POS);
        guidelineHorStart.setGuidelinePercent(START_HOR_POS);
        guidelineHorEnd.setGuidelinePercent(END_HOR_POS);

        textViewChangeEmail = findViewById(R.id.textViewChangeEmail);
        textViewChangePass = findViewById(R.id.textViewChangePass);
        textViewDeleteAcc = findViewById(R.id.textViewDeleteAcc);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        imageViewBack = findViewById(R.id.imageViewBack);

        // ANIM VIEWS ------------------------------------------------
        imageViewDeleteBorder = findViewById(R.id.imageViewDeleteBorder);
        imageViewDeleteBackground = findViewById(R.id.imageViewDeleteBackground);
        textViewMsg2 = findViewById(R.id.textViewMsg2);
        textViewMsg3 = findViewById(R.id.textViewMsg3);
        textViewPassBackground = findViewById(R.id.textViewPassBackground);
        textViewDeleteConfirm = findViewById(R.id.textViewDeleteConfirm);
        editTextDeletePass = findViewById(R.id.editTextDeletePass);
        imageViewDeleteBack = findViewById(R.id.imageViewDeleteBack);
        // -----------------------------------------------------------

        // ANIM DEFAULTS -----------------------------------------------------
        imageViewDeleteBorder.setVisibility(View.INVISIBLE);
        imageViewDeleteBorder.setScaleX(0.f);
        imageViewDeleteBorder.setScaleY(0.f);
        imageViewDeleteBackground.setVisibility(View.INVISIBLE);
        imageViewDeleteBackground.setScaleX(0.f);
        imageViewDeleteBackground.setScaleY(0.f);
        textViewMsg2.setVisibility(View.INVISIBLE);
        textViewMsg2.setAlpha(0.f);
        textViewMsg3.setVisibility(View.INVISIBLE);
        textViewMsg3.setAlpha(0.f);
        textViewPassBackground.setVisibility(View.INVISIBLE);
        textViewPassBackground.setAlpha(0.f);
        textViewDeleteConfirm.setVisibility(View.INVISIBLE);
        textViewDeleteConfirm.setAlpha(0.f);
        editTextDeletePass.setVisibility(View.INVISIBLE);
        editTextDeletePass.setAlpha(0.f);
        imageViewDeleteBack.setVisibility(View.INVISIBLE);
        imageViewDeleteBack.setScaleX(0.f);
        imageViewDeleteBack.setScaleY(0.f);
        imageViewDeleteBack.setAlpha(0.f);
        // -------------------------------------------------------------------

        // GET & SET SEPARATOR ANIM DEFAULTS ---------------------------------------------------
        imageViewBaseball = findViewById(R.id.imageViewBaseball);
        imageViewBasketball = findViewById(R.id.imageViewBasketball);
        imageViewBeachball = findViewById(R.id.imageViewBeachball);
        imageViewCricketball = findViewById(R.id.imageViewCricketball);
        imageViewFootball = findViewById(R.id.imageViewFootball);
        imageViewPoolball = findViewById(R.id.imageViewPoolball);
        imageViewTennisball = findViewById(R.id.imageViewTennisball);
        imageViewVolleyball = findViewById(R.id.imageViewVolleyball);

        imageViewBaseball.setScaleX(0.f);
        imageViewBaseball.setScaleY(0.f);
        imageViewBasketball.setScaleX(0.f);
        imageViewBasketball.setScaleY(0.f);
        imageViewBeachball.setScaleX(0.f);
        imageViewBeachball.setScaleY(0.f);
        imageViewCricketball.setScaleX(0.f);
        imageViewCricketball.setScaleY(0.f);
        imageViewFootball.setScaleX(0.f);
        imageViewFootball.setScaleY(0.f);
        imageViewPoolball.setScaleX(0.f);
        imageViewPoolball.setScaleY(0.f);
        imageViewTennisball.setScaleX(0.f);
        imageViewTennisball.setScaleY(0.f);
        imageViewVolleyball.setScaleX(0.f);
        imageViewVolleyball.setScaleY(0.f);
        // ---------------------------------------------------------------------------

        DisplaySeparatorAnim();

        if (currentUser != null)
        {
            email = currentUser.getEmail();
            username = email.split("@")[0];

            editTextEmail.setText(email);

            UserAccess.getUserByEmail(currentUser.getEmail(), new UserAccess.UserCallback() {
                @Override
                public void onCallback(int status, String username, Map<String, Object> userData) {
                    switch (status)
                    {
                        case UserAccess.Constants.STATUS_OK:
                            if (userData != null)
                            {
                                password = userData.toString().split("=")[1].split(",")[0];
                                editTextPassword.setText(password);
                            }
                            else Toast.makeText(context, "User data authentication failed.", Toast.LENGTH_SHORT).show();
                            break;

                        case UserAccess.Constants.STATUS_KO:
                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        }

        textViewChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newEmail = editTextEmail.getText().toString();

                if (!newEmail.equals(email)) {

                    Toast.makeText(context, "Changing email...", Toast.LENGTH_SHORT).show();
                    if (SignInActivity.loggedInWithEmail) {
                        AuthCredential credential = EmailAuthProvider.getCredential(email, password);

                        currentUser.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            currentUser.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        UserAccess.changeField(username, "email", newEmail, new UserAccess.ChangeFieldCallback() {
                                                            @Override
                                                            public void onCallback(int status) {
                                                                if (status == UserAccess.Constants.STATUS_OK) {
                                                                    Toast.makeText(context, "Email changed.", Toast.LENGTH_SHORT).show();
                                                                    email = newEmail;
                                                                } else
                                                                    Toast.makeText(context, "Email change failed.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    } else
                                                        Toast.makeText(context, "Email change failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else
                                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
                        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                        currentUser.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            currentUser.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        UserAccess.changeField(username, "email", newEmail, new UserAccess.ChangeFieldCallback() {
                                                            @Override
                                                            public void onCallback(int status) {
                                                                if (status == UserAccess.Constants.STATUS_OK) {
                                                                    Toast.makeText(context, "Email changed.", Toast.LENGTH_SHORT).show();
                                                                    email = newEmail;
                                                                } else
                                                                    Toast.makeText(context, "Email change failed.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    } else
                                                        Toast.makeText(context, "Email change failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else
                                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
                else Toast.makeText(context, "New email equals old email", Toast.LENGTH_SHORT).show();
            }
        });

        textViewChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPassword = editTextPassword.getText().toString();

                if (!newPassword.equals(password)) {

                    Toast.makeText(context, "Changing password...", Toast.LENGTH_SHORT).show();
                    if (SignInActivity.loggedInWithEmail) {
                        AuthCredential credential = EmailAuthProvider.getCredential(email, password);

                        currentUser.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            currentUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        UserAccess.changeField(username, "password", newPassword, new UserAccess.ChangeFieldCallback() {
                                                            @Override
                                                            public void onCallback(int status) {
                                                                if (status == UserAccess.Constants.STATUS_OK) {
                                                                    Toast.makeText(context, "Password changed.", Toast.LENGTH_SHORT).show();
                                                                    password = newPassword;
                                                                } else
                                                                    Toast.makeText(context, "Password change failed.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    } else
                                                        Toast.makeText(context, "Password change failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else
                                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
                        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                        currentUser.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            currentUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        UserAccess.changeField(username, "password", newPassword, new UserAccess.ChangeFieldCallback() {
                                                            @Override
                                                            public void onCallback(int status) {
                                                                if (status == UserAccess.Constants.STATUS_OK) {
                                                                    Toast.makeText(context, "Password changed.", Toast.LENGTH_SHORT).show();
                                                                    password = newPassword;
                                                                } else
                                                                    Toast.makeText(context, "Password change failed.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                    } else
                                                        Toast.makeText(context, "Password change failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else
                                            Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
                else Toast.makeText(context, "New password equals old password", Toast.LENGTH_SHORT).show();
            }
        });

        textViewDeleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SET TO DEFAULT AGAIN TO PREVENT VISUAL BUGS ----------
                imageViewDeleteBorder.setVisibility(View.INVISIBLE);
                imageViewDeleteBorder.setScaleX(0.f);
                imageViewDeleteBorder.setScaleY(0.f);
                imageViewDeleteBackground.setVisibility(View.INVISIBLE);
                imageViewDeleteBackground.setScaleX(0.f);
                imageViewDeleteBackground.setScaleY(0.f);
                textViewMsg2.setVisibility(View.INVISIBLE);
                textViewMsg2.setAlpha(0.f);
                textViewMsg3.setVisibility(View.INVISIBLE);
                textViewMsg3.setAlpha(0.f);
                textViewPassBackground.setVisibility(View.INVISIBLE);
                textViewPassBackground.setAlpha(0.f);
                textViewDeleteConfirm.setVisibility(View.INVISIBLE);
                textViewDeleteConfirm.setAlpha(0.f);
                editTextDeletePass.setVisibility(View.INVISIBLE);
                editTextDeletePass.setAlpha(0.f);
                imageViewDeleteBack.setVisibility(View.INVISIBLE);
                imageViewDeleteBack.setScaleX(0.f);
                imageViewDeleteBack.setScaleY(0.f);
                imageViewDeleteBack.setAlpha(0.f);

                editTextDeletePass.setText("");
                // ------------------------------------------------------
                DisplayDeleteBackgroundAnim();
            }
        });

        textViewDeleteConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmPassword = editTextDeletePass.getText().toString();

                if (confirmPassword != null)
                {
                    if (confirmPassword.equals(password))
                    {
                        Toast.makeText(context, "Deleting user...", Toast.LENGTH_SHORT).show();

                        if (currentUser != null) {
                            if (SignInActivity.loggedInWithEmail) {
                                AuthCredential credential = EmailAuthProvider.getCredential(email, password);

                                currentUser.reauthenticate(credential)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    currentUser.delete()
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()) {
                                                                        UserAccess.deleteUser(username, new UserAccess.DeleteUserCallback() {
                                                                            @Override
                                                                            public void onCallback(int status) {
                                                                                switch (status) {
                                                                                    case UserAccess.Constants.STATUS_OK:
                                                                                        Toast.makeText(context, "User deleted.", Toast.LENGTH_SHORT).show();
                                                                                        break;

                                                                                    case UserAccess.Constants.STATUS_KO:
                                                                                        Toast.makeText(context, "Error. User delete failed.", Toast.LENGTH_SHORT).show();
                                                                                        break;
                                                                                }
                                                                            }
                                                                        });
                                                                        Intent intent = new Intent(AccountSettingsActivity.this, MainActivity.class);
                                                                        startActivity(intent);
                                                                    } else
                                                                        Toast.makeText(context, "Error. User delete failed.", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                } else
                                                    Toast.makeText(context, "Error. Authentication failed.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            } else {
                                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
                                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                                currentUser.reauthenticate(credential)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    UserAccess.deleteUser(username, new UserAccess.DeleteUserCallback() {
                                                        @Override
                                                        public void onCallback(int status) {
                                                            switch (status) {
                                                                case UserAccess.Constants.STATUS_OK:
                                                                    currentUser.delete()
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if (task.isSuccessful()) {
                                                                                        Toast.makeText(context, "User deleted.", Toast.LENGTH_SHORT).show();
                                                                                        Intent intent = new Intent(AccountSettingsActivity.this, MainActivity.class);
                                                                                        startActivity(intent);
                                                                                    } else
                                                                                        Toast.makeText(context, "Error. User delete failed.", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                    break;
                                                                case UserAccess.Constants.STATUS_KO:
                                                                    Toast.makeText(context, "Error. Authentication failed.", Toast.LENGTH_SHORT).show();
                                                                    break;
                                                            }
                                                        }
                                                    });
                                                } else {
                                                    Toast.makeText(context, "Error. Reauthentication failed.", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        }
                        else Toast.makeText(context, "Error. Null user", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(context, "Passwords don't match", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(context, "Invalid password.", Toast.LENGTH_SHORT).show();
            }
        });

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountSettingsActivity.this, AppActivity.class);
                startActivity(intent);
            }
        });
    }

    private void DisplayDeleteBackgroundAnim()
    {
        imageViewDeleteBorder.setVisibility(View.VISIBLE);
        imageViewDeleteBackground.setVisibility(View.VISIBLE);

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.f, 1.f);

        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                imageViewDeleteBorder.setScaleX(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewDeleteBorder.setScaleY(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewDeleteBackground.setScaleX(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewDeleteBackground.setScaleY(0.f + (Float) valueAnimator.getAnimatedValue());
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                DisplayDeleteTextAnim();
            }
        });
    }

    private void UndisplayDeleteBackgroundAnim()
    {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.f, 1.f);

        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                imageViewDeleteBorder.setScaleX(1.f - (Float) valueAnimator.getAnimatedValue());
                imageViewDeleteBorder.setScaleY(1.f - (Float) valueAnimator.getAnimatedValue());
                imageViewDeleteBackground.setScaleX(1.f - (Float) valueAnimator.getAnimatedValue());
                imageViewDeleteBackground.setScaleY(1.f - (Float) valueAnimator.getAnimatedValue());
                imageViewDeleteBack.setScaleX(1.f - (Float) valueAnimator.getAnimatedValue());
                imageViewDeleteBack.setScaleY(1.f - (Float) valueAnimator.getAnimatedValue());
                imageViewDeleteBack.setAlpha(1.f - (Float) valueAnimator.getAnimatedValue());
                textViewMsg2.setAlpha(1.f - (Float) valueAnimator.getAnimatedValue());;
                textViewMsg3.setAlpha(1.f - (Float) valueAnimator.getAnimatedValue());
                textViewPassBackground.setAlpha(1.f - (Float) valueAnimator.getAnimatedValue());
                textViewDeleteConfirm.setAlpha(1.f - (Float) valueAnimator.getAnimatedValue());
                editTextDeletePass.setAlpha(1.f - (Float) valueAnimator.getAnimatedValue());
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                imageViewDeleteBorder.setVisibility(View.INVISIBLE);
                imageViewDeleteBackground.setVisibility(View.INVISIBLE);
                imageViewDeleteBack.setVisibility(View.INVISIBLE);
                textViewMsg2.setVisibility(View.INVISIBLE);
                textViewMsg3.setVisibility(View.INVISIBLE);
                textViewPassBackground.setVisibility(View.INVISIBLE);
                textViewDeleteConfirm.setVisibility(View.INVISIBLE);
                editTextDeletePass.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void DisplayDeleteTextAnim()
    {
        textViewMsg2.setVisibility(View.VISIBLE);
        textViewMsg3.setVisibility(View.VISIBLE);
        textViewPassBackground.setVisibility(View.VISIBLE);
        textViewDeleteConfirm.setVisibility(View.VISIBLE);
        editTextDeletePass.setVisibility(View.VISIBLE);
        imageViewDeleteBack.setVisibility(View.VISIBLE);

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.f, 1.f);

        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                textViewMsg2.setAlpha(0.f + (Float) valueAnimator.getAnimatedValue());
                textViewMsg3.setAlpha(0.f + (Float) valueAnimator.getAnimatedValue());
                textViewPassBackground.setAlpha(0.f + (Float) valueAnimator.getAnimatedValue());
                textViewDeleteConfirm.setAlpha(0.f + (Float) valueAnimator.getAnimatedValue());
                editTextDeletePass.setAlpha(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewDeleteBack.setScaleX(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewDeleteBack.setScaleY(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewDeleteBack.setAlpha(0.f + (Float) valueAnimator.getAnimatedValue());
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                SetEditTextListener();
                SetDeleteBackListener();
            }
        });
    }

    private void UndisplayDeleteTextAnim()
    {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.f, 1.f);

        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                imageViewDeleteBack.setScaleX(1.f - (Float) valueAnimator.getAnimatedValue());
                imageViewDeleteBack.setScaleY(1.f - (Float) valueAnimator.getAnimatedValue());
                imageViewDeleteBack.setAlpha(1.f - (Float) valueAnimator.getAnimatedValue());
                textViewMsg2.setAlpha(1.f - (Float) valueAnimator.getAnimatedValue());;
                textViewMsg3.setAlpha(1.f - (Float) valueAnimator.getAnimatedValue());
                textViewPassBackground.setAlpha(1.f - (Float) valueAnimator.getAnimatedValue());
                textViewDeleteConfirm.setAlpha(1.f - (Float) valueAnimator.getAnimatedValue());
                editTextDeletePass.setAlpha(1.f - (Float) valueAnimator.getAnimatedValue());
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                imageViewDeleteBack.setVisibility(View.INVISIBLE);
                textViewMsg2.setVisibility(View.INVISIBLE);
                textViewMsg3.setVisibility(View.INVISIBLE);
                textViewPassBackground.setVisibility(View.INVISIBLE);
                textViewDeleteConfirm.setVisibility(View.INVISIBLE);
                editTextDeletePass.setVisibility(View.INVISIBLE);
                UndisplayDeleteBackgroundAnim();
            }
        });
    }

    private void SetEditTextListener()
    {
        editTextDeletePass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textViewPassBackground.getVisibility() == View.VISIBLE) textViewPassBackground.setVisibility(View.INVISIBLE);
                else if (textViewPassBackground.getVisibility() == View.INVISIBLE && s.length() == 0) textViewPassBackground.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void SetDeleteBackListener()
    {
        imageViewDeleteBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UndisplayDeleteTextAnim();
            }
        });
    }

    private void DisplaySeparatorAnim()
    {

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.f, 1.f);

        valueAnimator.setDuration(90);
        valueAnimator.setStartDelay(500);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                imageViewBaseball.setScaleX(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewBaseball.setScaleY(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewBasketball.setScaleX(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewBasketball.setScaleY(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewBeachball.setScaleX(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewBeachball.setScaleY(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewCricketball.setScaleX(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewCricketball.setScaleY(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewFootball.setScaleX(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewFootball.setScaleY(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewPoolball.setScaleX(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewPoolball.setScaleY(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewTennisball.setScaleX(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewTennisball.setScaleY(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewVolleyball.setScaleX(0.f + (Float) valueAnimator.getAnimatedValue());
                imageViewVolleyball.setScaleY(0.f + (Float) valueAnimator.getAnimatedValue());
            }
        });
    }
}
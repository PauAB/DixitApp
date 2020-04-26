package com.example.dixitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    private Globals globals;

    private Guideline guidelineVerStart;
    private Guideline guidelineVerEnd;
    private Guideline guidelineHorStart;
    private Guideline guidelineHorEnd;

    static final float START_VER_POS = 0f;
    static final float END_VER_POS = 1f;
    static final float START_HOR_POS = 0f;
    static final float END_HOR_POS = 1f;

    private ImageView imageViewGoogleSignIn;
    private ImageView imageViewPhoneSignIn;
    private TextView textViewEmailBackground;
    private TextView textViewPasswordBackground;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewLogIn;
    private TextView textViewCreateAcc;
    private TextView textViewResetPass;

    // ANIM VIEWS ----------------------------------------------
    private ImageView imageViewBaseball;
    private ImageView imageViewBasketball;
    private ImageView imageViewBeachball;
    private ImageView imageViewCricketball;
    private ImageView imageViewFootball;
    private ImageView imageViewPoolball;
    private ImageView imageViewTennisball;
    private ImageView imageViewVolleyball;

    private ImageView imageViewTitleAnim;
    AnimationDrawable titleAnim;
    // ---------------------------------------------------------

    private String email;
    private String password;

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

        globals = (Globals)getApplication();

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
        imageViewPhoneSignIn = findViewById(R.id.imageViewPhoneSignIn);
        textViewEmailBackground = findViewById(R.id.textViewEmailBackground);
        textViewPasswordBackground = findViewById(R.id.textViewPasswordBackground);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        textViewLogIn = findViewById(R.id.textViewLogIn);
        textViewCreateAcc = findViewById(R.id.textViewCreateAcc);
        textViewResetPass = findViewById(R.id.textViewResetPass);

        // GET & SET ANIM DEFAULTS ---------------------------------------------------
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

        imageViewTitleAnim = findViewById(R.id.imageViewTitleAnim);
        imageViewTitleAnim.setBackgroundResource(R.drawable.title_anim);

        titleAnim = (AnimationDrawable) imageViewTitleAnim.getBackground();
        // ---------------------------------------------------------------------------

        titleAnim.start();
        DisplaySeparatorAnim();

        imageViewGoogleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loggedInWithEmail = false;

                Intent intent = new Intent(SignInActivity.this, SignInGoogleActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        imageViewPhoneSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loggedInWithEmail = false;

                Intent intent = new Intent(SignInActivity.this, CreatePhoneAccActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        textViewResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, ResetPassActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
                globals.setPassword(password);

                Toast.makeText(context, "Logging In...", Toast.LENGTH_SHORT).show();

                if (password != null && email != null)
                {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        mAuth.getCurrentUser().reload();

                                        if (mAuth.getCurrentUser().isEmailVerified())
                                        {
                                            Intent intent = new Intent(SignInActivity.this, AppActivity.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                        }
                                        else
                                        {
                                            Intent intent = new Intent(SignInActivity.this, EmailVerificationActivity.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                        }
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
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left); // Swipe to left (new activity coming from right)
            }
        });
    }

    private void DisplaySeparatorAnim()
    {
        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.f, 1.f);

        valueAnimator.setDuration(90);
        valueAnimator.setStartDelay(globals.getAnimDelay());
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

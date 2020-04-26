package com.example.dixitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class CreatePhoneAccActivity extends AppCompatActivity {

    private Globals globals;

    private FirebaseAuth mAuth;

    private Context context;

    private Guideline guidelineVerStart;
    private Guideline guidelineVerEnd;
    private Guideline guidelineHorStart;
    private Guideline guidelineHorEnd;

    static final float START_VER_POS = 0f;
    static final float END_VER_POS = 1f;
    static final float START_HOR_POS = 0f;
    static final float END_HOR_POS = 1f;

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

    private ImageView imageViewBack;
    private TextView textViewPhone;
    private TextView textViewConfirm;
    private TextView textViewSend;
    private EditText editTextPhone;

    private String phone;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private PhoneAuthProvider.ForceResendingToken resendToken;
    private String mVerificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_phone_acc);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        globals = (Globals)getApplication();

        mAuth = FirebaseAuth.getInstance();

        context = getApplicationContext();

        guidelineVerStart = findViewById(R.id.guidelineVerStart);
        guidelineVerEnd = findViewById(R.id.guidelineVerEnd);
        guidelineHorStart = findViewById(R.id.guidelineHorStart);
        guidelineHorEnd = findViewById(R.id.guidelineHorEnd);

        guidelineVerStart.setGuidelinePercent(START_VER_POS);
        guidelineVerEnd.setGuidelinePercent(END_VER_POS);
        guidelineHorStart.setGuidelinePercent(START_HOR_POS);
        guidelineHorEnd.setGuidelinePercent(END_HOR_POS);

        imageViewBack = findViewById(R.id.imageViewBack);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewConfirm = findViewById(R.id.textViewConfirm);
        textViewSend = findViewById(R.id.textViewSend);
        editTextPhone = findViewById(R.id.editTextPhone);

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
        titleAnim.start();
        // ---------------------------------------------------------------------------

        DisplaySeparatorAnim();

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreatePhoneAccActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        editTextPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (textViewPhone.getVisibility() == View.VISIBLE) textViewPhone.setVisibility(View.INVISIBLE);
                else if (textViewPhone.getVisibility() == View.INVISIBLE && s.length() == 0) textViewPhone.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                phone = editTextPhone.getText().toString();
            }
        });

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                Toast.makeText(context, "Verification complete.", Toast.LENGTH_SHORT).show();

                SignInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(context, "Verification failed.", Toast.LENGTH_SHORT).show();

                if (e instanceof FirebaseAuthInvalidCredentialsException)
                {
                    Toast.makeText(context, "Invalid phone number.", Toast.LENGTH_SHORT).show();
                    Log.i("User", "" + e);
                }
                else if (e instanceof FirebaseTooManyRequestsException)
                {
                    Toast.makeText(context, "Too many requests. Try again later", Toast.LENGTH_SHORT).show();
                    Log.i("User", "" + e);
                }
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                Toast.makeText(context, "Code sent.", Toast.LENGTH_SHORT).show();

                mVerificationId = verificationId;
                resendToken = token;
            }
        };

        textViewConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneNumberVerification();
                //VerifyPhoneNumberWithCode(mVerificationId, );
            }
        });

        textViewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResendVerificationCode(phone, resendToken);
            }
        });
    }

    private void PhoneNumberVerification()
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 60, TimeUnit.SECONDS, this, callbacks);
    }

    private void VerifyPhoneNumberWithCode(String verificationId, String code)
    {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        SignInWithPhoneAuthCredential(credential);
    }

    private void SignInWithPhoneAuthCredential(PhoneAuthCredential credential)
    {
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(context, "Success.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, "Failed.", Toast.LENGTH_SHORT).show();
                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                    {
                        Toast.makeText(context, "Invalid code.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void ResendVerificationCode(String phone, PhoneAuthProvider.ForceResendingToken resendingToken)
    {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                this,
                callbacks,
                resendingToken
        );
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
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

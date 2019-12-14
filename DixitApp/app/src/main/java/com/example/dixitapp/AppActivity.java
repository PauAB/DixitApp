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
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

public class AppActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private Guideline guidelineVerStart;
    private Guideline guidelineVerEnd;
    private Guideline guidelineHorStart;
    private Guideline guidelineHorEnd;
    private Guideline guidelineAccMenuEnd;

    static final float START_VER_POS = 0f;
    static final float END_VER_POS = 1f;
    static final float START_HOR_POS = 0f;
    static final float END_HOR_POS = 1f;
    static final float END_ACC_MENU_POS = 1.4f;

    private TextView textViewLogOutApp;
    private TextView textViewAccSettings;
    private TextView textViewAccName;
    private ImageView imageViewUser;
    private ImageView imageViewUserMenu;

    private String image;
    private String defaultImage = "https://www.voanews.com/themes/custom/voa/images/Author__Placeholder.png";
    private String username;
    private boolean accMenuDisplayed = false;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        context = getApplicationContext();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (!SignInGoogleActivity.signedOut)
        {
            Toast.makeText(context, "Signed In.", Toast.LENGTH_SHORT).show();
        }

        if (SignInActivity.loggedIn)
        {
            Toast.makeText(context, "Logged In.", Toast.LENGTH_SHORT).show();
        }

        // GET & SET GUIDELINES DEFAULTS -------------------------------------
        guidelineVerStart = findViewById(R.id.guidelineVerStart);
        guidelineVerEnd = findViewById(R.id.guidelineVerEnd);
        guidelineHorStart = findViewById(R.id.guidelineHorStart);
        guidelineHorEnd = findViewById(R.id.guidelineHorEnd);
        guidelineAccMenuEnd = findViewById(R.id.guidelineAccMenuEnd);

        guidelineVerStart.setGuidelinePercent(START_VER_POS);
        guidelineVerEnd.setGuidelinePercent(END_VER_POS);
        guidelineHorStart.setGuidelinePercent(START_HOR_POS);
        guidelineHorEnd.setGuidelinePercent(END_HOR_POS);
        guidelineAccMenuEnd.setGuidelinePercent(END_ACC_MENU_POS);
        // -------------------------------------------------------------------

        textViewLogOutApp = findViewById(R.id.textViewLogOutApp);
        textViewAccSettings = findViewById(R.id.textViewAccSettings);
        textViewAccName = findViewById(R.id.textViewAccName);
        imageViewUser = findViewById(R.id.imageViewUser);
        imageViewUserMenu = findViewById(R.id.imageViewUserMenu);

        username = user.getEmail().split("@")[0];

        textViewAccName.setText(username);

        if (user != null)
        {
            if (user.getPhotoUrl() != null) image = user.getPhotoUrl().toString();
            else image = defaultImage;
        }
        else image = defaultImage;

        Picasso.get().load(image).into(imageViewUser);
        Picasso.get().load(image).transform(new CircleTransform()).into(imageViewUser);

        Picasso.get().load(image).into(imageViewUserMenu);
        Picasso.get().load(image).transform(new CircleTransform()).into(imageViewUserMenu);

        imageViewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!accMenuDisplayed)
                {
                    final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.f, 0.4f);

                    valueAnimator.setDuration(300);
                    valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    valueAnimator.start();

                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            guidelineHorStart.setGuidelinePercent(START_HOR_POS - (Float) valueAnimator.getAnimatedValue());
                            guidelineHorEnd.setGuidelinePercent(END_HOR_POS - (Float) valueAnimator.getAnimatedValue());
                            guidelineAccMenuEnd.setGuidelinePercent(END_ACC_MENU_POS - (Float) valueAnimator.getAnimatedValue());
                        }
                    });

                    accMenuDisplayed = !accMenuDisplayed;
                }
                else
                {
                    final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.f, 0.4f);

                    valueAnimator.setDuration(300);
                    valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                    valueAnimator.start();

                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            guidelineHorStart.setGuidelinePercent(-0.4f + (Float) valueAnimator.getAnimatedValue());
                            guidelineHorEnd.setGuidelinePercent(0.6f + (Float) valueAnimator.getAnimatedValue());
                            guidelineAccMenuEnd.setGuidelinePercent(1.f + (Float) valueAnimator.getAnimatedValue());
                        }
                    });

                    accMenuDisplayed = !accMenuDisplayed;
                }
            }
        });

        textViewAccSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AppActivity.this, AccountSettingsActivity.class);
                startActivity(intent);
            }
        });

        textViewLogOutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInGoogleActivity.signedOut = true;

                Toast.makeText(context, "Logging Out...", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AppActivity.this, SignInGoogleActivity.class);
                startActivity(intent);
            }
        });
    }
}

/*
        textViewDeleteAccApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Authenticating user...", Toast.LENGTH_SHORT).show();

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
                                        Toast.makeText(context, "User deleted.", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(AppActivity.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                    else Toast.makeText(context, "Error. User NOT deleted.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else Toast.makeText(context, "Error. Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
 */

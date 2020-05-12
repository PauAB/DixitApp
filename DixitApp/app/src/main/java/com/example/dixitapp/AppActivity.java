package com.example.dixitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AppActivity extends AppCompatActivity implements ContentFragment.OnListFragmentInteractionListener {

    FirebaseAuth mAuth;
    FirebaseUser user;

    private Globals globals;

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

    // ANIM VIEWS --------------------------------
    private ImageView imageViewTitleAnim;
    AnimationDrawable titleAnim;
    // -------------------------------------------

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

    private String image;
    private String defaultImage = "https://www.voanews.com/themes/custom/voa/images/Author__Placeholder.png";
    private String username;
    private boolean accMenuDisplayed = false;

    private List<InterestEntity> interestList;

    InterestViewModel interestViewModel;
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
        globals = (Globals)getApplication();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        interestViewModel = new InterestViewModel(getApplication());

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

        imageViewTitleAnim = findViewById(R.id.imageViewTitleAnim);
        imageViewTitleAnim.setBackgroundResource(R.drawable.title_anim);

        titleAnim = (AnimationDrawable) imageViewTitleAnim.getBackground();
        titleAnim.start();

        DisplaySeparatorAnim();

        textViewLogOutApp = findViewById(R.id.textViewLogOutApp);
        textViewAccSettings = findViewById(R.id.textViewAccSettings);
        textViewAccName = findViewById(R.id.textViewAccName);
        imageViewUser = findViewById(R.id.imageViewUser);
        imageViewUserMenu = findViewById(R.id.imageViewUserMenu);

        user.reload();

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

        try
        {
            InputStream is = getAssets().open("interests.json");
            JsonInterestParser jsonInterestParser = new JsonInterestParser();
            interestList = jsonInterestParser.readJsonStream(is);
        } catch (Exception e)
        {
            interestList = new ArrayList<>();
        }

        for (InterestEntity interest : interestList)
        {
            interestViewModel.insertInterest(interest);
        }

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
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        textViewLogOutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInGoogleActivity.signedOut = true;

                Toast.makeText(context, "Logging Out...", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AppActivity.this, SignInGoogleActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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

    @Override
    public void onListFragmentInteraction(InterestEntity item) {

    }
}

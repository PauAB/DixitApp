package com.example.dixitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateInterestActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser user;

    private Globals globals;
    private Context context;

    private Guideline guidelineVerStart;
    private Guideline guidelineVerEnd;
    private Guideline guidelineHorStart;
    private Guideline guidelineHorEnd;

    static final float START_VER_POS = 0f;
    static final float END_VER_POS = 1f;
    static final float START_HOR_POS = 0f;
    static final float END_HOR_POS = 1f;

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

    private ImageView imageViewBack;
    private TextView textViewCreateInterest;

    private EditText editTextCategory;
    private EditText editTextContentText;

    private String username;
    private String defaultImage = "https://www.voanews.com/themes/custom/voa/images/Author__Placeholder.png";
    private String image;
    private String category;
    private String contentText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_interest);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        context = getApplicationContext();
        globals = (Globals)getApplication();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        // GET & SET GUIDELINES DEFAULTS -------------------------------------
        guidelineVerStart = findViewById(R.id.guidelineVerStart);
        guidelineVerEnd = findViewById(R.id.guidelineVerEnd);
        guidelineHorStart = findViewById(R.id.guidelineHorStart);
        guidelineHorEnd = findViewById(R.id.guidelineHorEnd);

        guidelineVerStart.setGuidelinePercent(START_VER_POS);
        guidelineVerEnd.setGuidelinePercent(END_VER_POS);
        guidelineHorStart.setGuidelinePercent(START_HOR_POS);
        guidelineHorEnd.setGuidelinePercent(END_HOR_POS);
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

        imageViewBack = findViewById(R.id.imageViewBack);
        textViewCreateInterest = findViewById(R.id.textViewCreateInterest);
        editTextCategory = findViewById(R.id.editTextCategory);
        editTextContentText = findViewById(R.id.editTextContentText);

        username = user.getEmail().split("@")[0];

        if (user != null)
        {
            if (user.getPhotoUrl() != null) image = user.getPhotoUrl().toString();
            else image = defaultImage;
        }
        else image = defaultImage;

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateInterestActivity.this, UserProfile.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        editTextCategory.setFilters(new InputFilter[] {new InputFilter.LengthFilter(24)});
        editTextCategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*if (textViewNameBackground.getVisibility() == View.VISIBLE) textViewNameBackground.setVisibility(View.INVISIBLE);
                else if (textViewNameBackground.getVisibility() == View.INVISIBLE && s.length() == 0) textViewNameBackground.setVisibility(View.VISIBLE);*/
            }

            @Override
            public void afterTextChanged(Editable s) {
                category = editTextCategory.getText().toString();
            }
        });

        editTextContentText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(240)});
        editTextContentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*if (textViewNameBackground.getVisibility() == View.VISIBLE) textViewNameBackground.setVisibility(View.INVISIBLE);
                else if (textViewNameBackground.getVisibility() == View.INVISIBLE && s.length() == 0) textViewNameBackground.setVisibility(View.VISIBLE);*/
            }

            @Override
            public void afterTextChanged(Editable s) {
                contentText = editTextContentText.getText().toString();
            }
        });

        textViewCreateInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category != null && contentText != null)
                {
                    Toast.makeText(context, "Creating interest...", Toast.LENGTH_SHORT).show();
                    InterestAccess.createNewInterest(image, username, category, contentText, new InterestAccess.CreateInterestCallback() {
                        @Override
                        public void onCallback(int status) {
                            if (status == InterestAccess.Constants.STATUS_OK)
                            {
                                Toast.makeText(context, "Interest created", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CreateInterestActivity.this, UserProfile.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            }
                            else if (status == InterestAccess.Constants.STATUS_KO)
                            {
                                Toast.makeText(context, "Interest creation failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
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

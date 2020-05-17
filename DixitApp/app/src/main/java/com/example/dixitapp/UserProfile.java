package com.example.dixitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity implements UserInterestsFragment.OnListFragmentInteractionListener{

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

    private ImageView imageViewBack;
    private TextView textViewCreateInterest;

    private static ImageView imageViewArrow;
    private static TextView textViewYouHaveNo;
    private static TextView textViewPublicationsYet;
    private static TextView textViewMakeOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

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

        imageViewTitleAnim = findViewById(R.id.imageViewTitleAnim);
        imageViewTitleAnim.setBackgroundResource(R.drawable.title_anim);

        titleAnim = (AnimationDrawable) imageViewTitleAnim.getBackground();
        titleAnim.start();

        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewArrow = findViewById(R.id.imageViewArrow);
        textViewCreateInterest = findViewById(R.id.textViewCreateInterest);
        textViewYouHaveNo = findViewById(R.id.textViewYouHaveNo);
        textViewPublicationsYet = findViewById(R.id.textViewPublicationsYet);
        textViewMakeOne = findViewById(R.id.textViewMakeOne);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, AppActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        textViewCreateInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, CreateInterestActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    public static void HideNoPublications()
    {
        imageViewArrow.setImageAlpha(0);
        textViewYouHaveNo.setAlpha(0f);
        textViewPublicationsYet.setAlpha(0f);
        textViewMakeOne.setAlpha(0f);
    }

    public static void ShowNoPublications()
    {
        imageViewArrow.setImageAlpha(1);
        textViewYouHaveNo.setAlpha(0.5f);
        textViewPublicationsYet.setAlpha(0.5f);
        textViewMakeOne.setAlpha(0.5f);
    }

    @Override
    public void onListFragmentInteraction(Interest item) {

    }
}
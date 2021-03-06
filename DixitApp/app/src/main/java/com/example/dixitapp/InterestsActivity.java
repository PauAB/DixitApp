package com.example.dixitapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class InterestsActivity extends AppCompatActivity implements InterestsFragment.OnListFragmentInteractionListener{

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

    private static TextView textViewYouHaveNo;
    private static TextView textViewInterestsYet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);

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

        textViewYouHaveNo = findViewById(R.id.textViewYouHaveNo);
        textViewInterestsYet = findViewById(R.id.textViewInterestsYet);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InterestsActivity.this, AppActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
    }

    public static void HideNoInterests()
    {
        textViewYouHaveNo.setAlpha(0f);
        textViewInterestsYet.setAlpha(0f);
    }

    public static void ShowNoInterests()
    {
        textViewYouHaveNo.setAlpha(1f);
        textViewInterestsYet.setAlpha(1f);
    }

    @Override
    public void onListFragmentInteraction(Interest item) {

    }
}

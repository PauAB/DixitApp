package com.example.dixitapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Guideline;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailVerificationActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    private Guideline guidelineVerStart;
    private Guideline guidelineVerEnd;
    private Guideline guidelineHorStart;
    private Guideline guidelineHorEnd;

    static final float START_VER_POS = 0f;
    static final float END_VER_POS = 1f;
    static final float START_HOR_POS = 0f;
    static final float END_HOR_POS = 1f;

    private TextView textViewEmailConf;
    private TextView textViewSendAgain;
    private TextView textViewConfirm;
    private ImageView imageViewBack;

    private String email;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        context = getApplicationContext();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        guidelineVerStart = findViewById(R.id.guidelineVerStart);
        guidelineVerEnd = findViewById(R.id.guidelineVerEnd);
        guidelineHorStart = findViewById(R.id.guidelineHorStart);
        guidelineHorEnd = findViewById(R.id.guidelineHorEnd);

        guidelineVerStart.setGuidelinePercent(START_VER_POS);
        guidelineVerEnd.setGuidelinePercent(END_VER_POS);
        guidelineHorStart.setGuidelinePercent(START_HOR_POS);
        guidelineHorEnd.setGuidelinePercent(END_HOR_POS);

        textViewEmailConf = findViewById(R.id.textViewEmailConf);
        textViewSendAgain = findViewById(R.id.textViewSendAgain);
        textViewConfirm = findViewById(R.id.textViewConfirm);
        imageViewBack = findViewById(R.id.imageViewBack);

        if (currentUser != null) email = currentUser.getEmail();

        textViewEmailConf.setText(email);

        SendEmail();

        textViewSendAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SendEmail();
            }
        });

        if(currentUser != null)
        {
            textViewConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    currentUser.reload();

                    if (currentUser.isEmailVerified())
                    {
                        Toast.makeText(context, "Email verified", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EmailVerificationActivity.this, AppActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(EmailVerificationActivity.this, SignInActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmailVerificationActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void SendEmail()
    {
        if (currentUser != null)
        {
            currentUser.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) Toast.makeText(context, "Email sent.", Toast.LENGTH_SHORT).show();
                            else Toast.makeText(context, "Error. Email could not be sent.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else Toast.makeText(context, "Error. User not found", Toast.LENGTH_SHORT).show();
    }
}

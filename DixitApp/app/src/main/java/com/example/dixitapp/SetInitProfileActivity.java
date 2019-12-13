package com.example.dixitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class SetInitProfileActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextEmail;
    private ImageView imageViewUserImage;
    private ImageView imageViewNext;

    private FirebaseAuth mAuth;

    private String username;
    private String image;
    private String name;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_init_profile);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        imageViewUserImage = findViewById(R.id.imageViewUserImage);
        imageViewNext = findViewById(R.id.imageViewNext);

        name = user.getDisplayName();
        email = user.getEmail();

        username = user.getEmail().split("@")[0];

        editTextName.setText(name);
        editTextEmail.setText(email);

        image = user.getPhotoUrl().toString();

        Picasso.get().load(image).into(imageViewUserImage);
        Picasso.get().load(image).transform(new CircleTransform()).into(imageViewUserImage);

        imageViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserAccess.checkUserExist(name, new UserAccess.ExistCallback() {
                    @Override
                    public void onCallback(int status) {
                        switch (status)
                        {
                            case UserAccess.Constants.STATUS_USER_EXIST:
                                // Error
                                break;

                            case UserAccess.Constants.STATUS_USER_NOT_EXIST:
                                UserAccess.checkEmailExist(email, new UserAccess.ExistCallback() {
                                    @Override
                                    public void onCallback(int status) {
                                        switch (status)
                                        {
                                            case UserAccess.Constants.STATUS_USER_EXIST:
                                                // Error
                                                break;

                                            case UserAccess.Constants.STATUS_USER_NOT_EXIST:
                                                UserAccess.createNewUser(username, name, email, new UserAccess.CreateNewUserCallback() {
                                                    @Override
                                                    public void onCallback(int status) {
                                                        if (status == UserAccess.Constants.STATUS_OK)
                                                        {
                                                            Intent intent = new Intent(SetInitProfileActivity.this, AppActivity.class);
                                                            startActivity(intent);
                                                        }
                                                        else if (status == UserAccess.Constants.STATUS_KO)
                                                        {
                                                            // Do not change the activity
                                                        }
                                                    }
                                                });
                                                break;
                                            case UserAccess.Constants.STATUS_KO:
                                                break;
                                        }
                                    }
                                });
                                break;
                            case UserAccess.Constants.STATUS_KO:
                                break;
                        }
                    }
                });
            }
        });
    }
}

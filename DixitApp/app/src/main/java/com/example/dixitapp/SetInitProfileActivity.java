package com.example.dixitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class SetInitProfileActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ImageView imageViewUserImage;
    private ImageView imageViewNext;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private String username;
    private String image;
    private String name;
    private String email;
    private String password = "";

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_init_profile);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        context = getApplicationContext();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        // Configure Google Sign In ----------------------------------------------------------------------
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // -----------------------------------------------------------------------------------------------

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
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
                password = editTextPassword.getText().toString();

                if (password.length() < 8) Toast.makeText(context, "Invalid password. Write at least 8 characters.", Toast.LENGTH_SHORT).show();
                else if (password.length() >= 8)
                {
                    Toast.makeText(context, "Checking if user exists...", Toast.LENGTH_SHORT).show();

                    name = editTextName.getText().toString();
                    email = editTextEmail.getText().toString();

                    UserAccess.checkUserExist(name, new UserAccess.ExistCallback() {
                        @Override
                        public void onCallback(int status) {
                            switch (status) {
                                case UserAccess.Constants.STATUS_USER_EXIST:
                                    Toast.makeText(context, "Invalid. Username already exists.", Toast.LENGTH_SHORT).show();
                                    break;

                                case UserAccess.Constants.STATUS_USER_NOT_EXIST:
                                    UserAccess.checkEmailExist(email, new UserAccess.ExistCallback() {
                                        @Override
                                        public void onCallback(int status) {
                                            switch (status) {
                                                case UserAccess.Constants.STATUS_USER_EXIST:
                                                    Toast.makeText(context, "Invalid. Email already in use.", Toast.LENGTH_SHORT).show();
                                                    break;

                                                case UserAccess.Constants.STATUS_USER_NOT_EXIST:
                                                    UserAccess.createNewUser(username, name, email, password, new UserAccess.CreateNewUserCallback() {
                                                        @Override
                                                        public void onCallback(int status) {
                                                            if (status == UserAccess.Constants.STATUS_OK) {
                                                                Toast.makeText(context, "User created.", Toast.LENGTH_SHORT).show();

                                                                Intent intent = new Intent(SetInitProfileActivity.this, AppActivity.class);
                                                                startActivity(intent);
                                                            } else if (status == UserAccess.Constants.STATUS_KO) {
                                                                Toast.makeText(context, "Error. User creation failed.", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                    break;
                                                case UserAccess.Constants.STATUS_KO:
                                                    Toast.makeText(context, "Error. Email authentication failed.", Toast.LENGTH_SHORT).show();
                                                    break;
                                            }
                                        }
                                    });
                                    break;
                                case UserAccess.Constants.STATUS_KO:
                                    Toast.makeText(context, "Error. User authentication failed", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    });
                }
            }
        });
    }
}

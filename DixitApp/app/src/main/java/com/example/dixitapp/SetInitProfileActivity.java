package com.example.dixitapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
    private ImageView imageViewUserImage;
    private ImageView imageViewNext;
    private TextView textViewLogOut;

    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private String username;
    private String image;
    private String name;
    private String email;

    Context context;
    private String toastText = "";
    private int toastDuration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_init_profile);

        context = getApplicationContext();

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        // Configure Google Sign In ----------------------------------------------------------------------
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        toastText = "Signed In.";
        Toast toast = Toast.makeText(context, toastText, toastDuration);
        toast.show();
        // -----------------------------------------------------------------------------------------------

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        imageViewUserImage = findViewById(R.id.imageViewUserImage);
        imageViewNext = findViewById(R.id.imageViewNext);
        textViewLogOut = findViewById(R.id.textViewLogOut);

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
                toastText = "Checking if user exists...";
                Toast toast = Toast.makeText(context, toastText, toastDuration);
                toast.show();

                name = editTextName.getText().toString();
                email = editTextEmail.getText().toString();

                UserAccess.checkUserExist(name, new UserAccess.ExistCallback() {
                    @Override
                    public void onCallback(int status) {
                        switch (status)
                        {
                            case UserAccess.Constants.STATUS_USER_EXIST:
                                Log.w("Next", "Invalid user, try another name");
                                toastText = "Invalid. Username already exists.";
                                Toast toast = Toast.makeText(context, toastText, toastDuration);
                                toast.show();
                                break;

                            case UserAccess.Constants.STATUS_USER_NOT_EXIST:
                                Log.w("Next", "Valid user, checking email");

                                UserAccess.checkEmailExist(email, new UserAccess.ExistCallback() {
                                    @Override
                                    public void onCallback(int status) {
                                        switch (status)
                                        {
                                            case UserAccess.Constants.STATUS_USER_EXIST:
                                                Log.w("Next", "Invalid. Email already in use.");
                                                toastText = "Invalid. Email already in use.";
                                                Toast toast = Toast.makeText(context, toastText, toastDuration);
                                                toast.show();
                                                break;

                                            case UserAccess.Constants.STATUS_USER_NOT_EXIST:
                                                Log.w("Next", "Valid email, creating user");

                                                UserAccess.createNewUser(username, name, email, new UserAccess.CreateNewUserCallback() {
                                                    @Override
                                                    public void onCallback(int status) {
                                                        if (status == UserAccess.Constants.STATUS_OK)
                                                        {
                                                            Log.w("Next", "User created");
                                                            toastText = "User created.";
                                                            Toast toast = Toast.makeText(context, toastText, toastDuration);
                                                            toast.show();

                                                            Intent intent = new Intent(SetInitProfileActivity.this, AppActivity.class);
                                                            startActivity(intent);
                                                        }
                                                        else if (status == UserAccess.Constants.STATUS_KO)
                                                        {
                                                            Log.w("Next", "User NOT created");
                                                            toastText = "Error. Can't create user.";
                                                            Toast toast = Toast.makeText(context, toastText, toastDuration);
                                                            toast.show();
                                                        }
                                                    }
                                                });
                                                break;
                                            case UserAccess.Constants.STATUS_KO:
                                                Log.w("Next", "Something went wrong while checking email");
                                                toastText = "Error. Can't check email.";
                                                Toast toast2 = Toast.makeText(context, toastText, toastDuration);
                                                toast2.show();
                                                break;
                                        }
                                    }
                                });
                                break;
                            case UserAccess.Constants.STATUS_KO:
                                Log.w("Next", "Something went wrong while checking user");
                                toastText = "Error. Can't check username.";
                                Toast toast2 = Toast.makeText(context, toastText, toastDuration);
                                toast2.show();
                                break;
                        }
                    }
                });
            }
        });

        textViewLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInGoogleActivity.signedOut = true;

                toastText = "Logging Out...";
                Toast toast = Toast.makeText(context, toastText, toastDuration);
                toast.show();

                Intent intent = new Intent(SetInitProfileActivity.this, SignInGoogleActivity.class);
                startActivity(intent);
            }
        });
    }
}

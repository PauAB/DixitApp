package com.example.dixitapp;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class UserAccess {

    public static void createNewUser(String username, String email, String name, final CreateNewUserCallback callback)
    {
        FirebaseFirestore.getInstance().collection("users").document(username)
                .set(new User(email, name).toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        callback.onCallback(Constants.STATUS_OK);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onCallback(Constants.STATUS_KO);
                    }
                });
    }

    public static void checkUserExist(String name, final ExistCallback callback)
    {
        FirebaseFirestore.getInstance().collection("users")
                .whereEqualTo("name", name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            if (task.getResult().getDocuments().size() == 0) callback.onCallback(Constants.STATUS_USER_NOT_EXIST);
                            else callback.onCallback(Constants.STATUS_USER_EXIST);
                        }
                        else callback.onCallback(Constants.STATUS_KO);
                    }
                });
    }

    public static void checkEmailExist(String email, final ExistCallback callback)
    {
        getUserByEmail(email, new UserCallback() {
            @Override
            public void onCallback(int status, String username, Map<String, Object> userData) {
                if (status == Constants.STATUS_OK)
                {
                    if (username == null) callback.onCallback(Constants.STATUS_USER_NOT_EXIST);
                    else callback.onCallback(Constants.STATUS_USER_EXIST);
                }
                else callback.onCallback(Constants.STATUS_KO);
            }
        });
    }

    public static void getUser(String name, final UserCallback callback)
    {
        FirebaseFirestore.getInstance().collection("users")
                .whereEqualTo("name", name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            if (task.getResult().getDocuments().size() > 0)
                            {
                                String username = task.getResult().getDocuments().get(0).getId();
                                Map<String,Object> data = task.getResult().getDocuments().get(0).getData();

                                callback.onCallback(Constants.STATUS_OK, username, data);
                            }
                            else callback.onCallback(Constants.STATUS_OK, null, null);
                        }
                        else callback.onCallback(Constants.STATUS_KO, null,null);
                    }
                });
    }

    public static void getUserByEmail(final String email, final UserCallback callback)
    {
        FirebaseFirestore.getInstance().collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            if (task.getResult().getDocuments().size() > 0)
                            {
                                String username = task.getResult().getDocuments().get(0).getId();
                                Map<String,Object> data = task.getResult().getDocuments().get(0).getData();

                                callback.onCallback(Constants.STATUS_OK, username, data);
                            }
                            else callback.onCallback(Constants.STATUS_OK, null, null);
                        }
                        else callback.onCallback(Constants.STATUS_KO, null, null);
                    }
                });
    }

    public static void deleteUser(String username, final DeleteUserCallback callback)
    {
        FirebaseFirestore.getInstance().collection("users")
                .document(username).delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) callback.onCallback(Constants.STATUS_OK);
                        else callback.onCallback(Constants.STATUS_KO);
                    }
                });
    }

    public static void changeField(String username, final String field, final String newValue, final ChangeFieldCallback callback)
    {
        FirebaseFirestore.getInstance().collection("users")
                .document(username)
                .update(field, newValue)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) callback.onCallback(Constants.STATUS_OK);
                        else callback.onCallback(Constants.STATUS_KO);
                    }
                });
    }

    public interface CreateNewUserCallback
    {
        void onCallback(int status);
    }

    public interface DeleteUserCallback
    {
        void onCallback(int status);
    }

    public interface ExistCallback
    {
        void onCallback(int status);
    }

    public interface UserCallback
    {
        void onCallback(int status, String username, Map<String, Object> userData);
    }

    public interface ChangeFieldCallback
    {
        void onCallback(int status);
    }

    public interface Constants
    {
        int STATUS_OK = 0;
        int STATUS_KO = 1;
        int STATUS_USER_EXIST = 2;
        int STATUS_USER_NOT_EXIST = 3;
    }
}

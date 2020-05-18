package com.example.dixitapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

public class InterestAccess {
    public static void createNewInterest(String image, String username, String category, String content, final CreateInterestCallback callback)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm '|' MM.dd.yyyy");
        String dateAndTime = sdf.format(new Date());

        String id = username + "-" + dateAndTime;

        FirebaseFirestore.getInstance().collection("interests").document(id)
                .set(new Interest(id, image, username, dateAndTime, category, content).toMap())
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

    public static void getInterest(String id, final InterestCallback callback)
    {
        FirebaseFirestore.getInstance().collection("interests")
                .whereEqualTo("id", id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful())
                        {
                            if (task.getResult().getDocuments().size() > 0)
                            {
                                callback.onCallback(new Interest(task.getResult().getDocuments().get(0).getData()), Constants.STATUS_OK);
                            }
                            else callback.onCallback(null, Constants.STATUS_OK);
                        }
                        else callback.onCallback(null, Constants.STATUS_KO);
                    }
                });
    }

    public static void getUserInterests(String username, final UserInterestsCallback callback)
    {
        FirebaseFirestore.getInstance().collection("interests")
                .whereEqualTo("username", username)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                        if (e != null)
                        {
                            Log.i("Access", "Failed", e);
                            callback.onCallback(null, Constants.STATUS_KO);
                        }

                        List<Interest> interests = new ArrayList<>();

                        for (QueryDocumentSnapshot document : value)
                        {
                            interests.add(new Interest(document.getData()));
                        }

                        callback.onCallback(interests, Constants.STATUS_OK);
                    }
                });
    }

    public static void getInterests(final InterestsCallback callback)
    {
        FirebaseFirestore.getInstance().collection("interests").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {
                if (e != null)
                {
                    Log.i("Access", "Failed", e);
                    callback.onCallback(null, Constants.STATUS_KO);
                }

                List<Interest> interestsList = new ArrayList<>();

                for (QueryDocumentSnapshot document : value)
                {
                    interestsList.add(new Interest(document.getData()));
                }

                callback.onCallback(interestsList, Constants.STATUS_OK);
            }
        });
    }

    public static void updateCounter(String id, final String field, final int value, final CounterCallback callback)
    {
        FirebaseFirestore.getInstance().collection("interests").document(id).update(field, value)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            callback.onCallback(Constants.STATUS_OK);
                        }
                        else
                        {
                            callback.onCallback(Constants.STATUS_KO);
                        }
                    }
                });
    }

    public interface CreateInterestCallback
    {
        void onCallback(int status);
    }

    public interface InterestCallback
    {
        void onCallback(Interest interest, int status);
    }

    public interface UserInterestsCallback
    {
        void onCallback(List<Interest> userInterests, int status);
    }

    public interface InterestsCallback
    {
        void onCallback(List<Interest> interests, int status);
    }

    public interface CounterCallback
    {
        void onCallback(int status);
    }

    public interface Constants
    {
        int STATUS_OK = 0;
        int STATUS_KO = 1;
    }
}

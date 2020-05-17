package com.example.dixitapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class InterestAccess {
    public static void createNewInterest(String ID, String image, String username, String dateAndTime, String category, String content, final CreateInterestCallback callback)
    {
        FirebaseFirestore.getInstance().collection("interests").document(ID)
                .set(new Interest(ID, image, username, dateAndTime, category, content, 0).toMap())
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
                    interestsList.add(new Interest(document.getId(), document.getData()));
                }
            }
        });
    }

    public interface CreateInterestCallback
    {
        void onCallback(int status);
    }

    public interface InterestsCallback
    {
        void onCallback(List<Interest> interests, int status);
    }

    public interface Constants
    {
        int STATUS_OK = 0;
        int STATUS_KO = 1;
    }
}

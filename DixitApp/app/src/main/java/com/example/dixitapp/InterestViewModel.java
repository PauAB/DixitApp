package com.example.dixitapp;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class InterestViewModel extends AndroidViewModel {
    InterestRepository interestRepository;

    public InterestViewModel(Application application)
    {
        super(application);
        interestRepository = new InterestRepository(application);
    }

    public LiveData<List<InterestEntity>> getAllInterests() {return interestRepository.getAll();}

    public void insertInterest(InterestEntity interestEntity) {interestRepository.insert(interestEntity);}
}

package com.example.dixitapp;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class UserInterestsFragment extends Fragment {


    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<Interest> interestsList;
    private String username;

    FirebaseAuth mAuth;
    FirebaseUser user;

    public UserInterestsFragment() {
    }

    @SuppressWarnings("unused")
    public static UserInterestsFragment newInstance(int columnCount) {
        UserInterestsFragment fragment = new UserInterestsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        interestsList = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        username = user.getEmail().split("@")[0];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_interests_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            InterestAccess.getUserInterests(username, new InterestAccess.UserInterestsCallback() {
                @Override
                public void onCallback(List<com.example.dixitapp.Interest> userInterests, int status) {
                    if (status == InterestAccess.Constants.STATUS_OK)
                    {
                        interestsList = userInterests;
                        if (interestsList.size() > 0)
                        {
                            UserProfile.HideNoPublications();

                            MyUserInterestsRecyclerViewAdapter adapter = new MyUserInterestsRecyclerViewAdapter(interestsList, mListener, getContext());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            UserProfile.ShowNoPublications();
                        }
                    }
                    else if (status == InterestAccess.Constants.STATUS_KO)
                    {
                        Toast.makeText(getContext(), "Failed to get interests", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Interest item);
    }
}

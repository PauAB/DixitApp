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

public class InterestsFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private List<Interest> interestsList;
    private String username;

    FirebaseAuth mAuth;
    FirebaseUser user;

    public InterestsFragment() {
    }

    @SuppressWarnings("unused")
    public static InterestsFragment newInstance(int columnCount) {
        InterestsFragment fragment = new InterestsFragment();
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
        View view = inflater.inflate(R.layout.fragment_interests_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            UserAccess.getAllInterests(user.getEmail(), new UserAccess.InterestsCallback() {
                @Override
                public void onCallback(List<Interest> interests, int status) {
                    if (status == UserAccess.Constants.STATUS_OK)
                    {
                        interestsList = interests;

                        if (interestsList.size() > 0)
                        {
                            InterestsActivity.HideNoInterests();

                            MyInterestsRecyclerViewAdapter adapter = new MyInterestsRecyclerViewAdapter(interestsList, mListener, getContext());
                            recyclerView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                        else
                        {
                            InterestsActivity.ShowNoInterests();
                        }
                    }
                    else if (status == UserAccess.Constants.STATUS_KO)
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
        void onListFragmentInteraction(Interest item);
    }
}

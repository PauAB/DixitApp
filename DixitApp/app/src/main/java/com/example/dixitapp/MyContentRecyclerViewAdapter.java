package com.example.dixitapp;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dixitapp.ContentFragment.OnListFragmentInteractionListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Interest} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyContentRecyclerViewAdapter extends RecyclerView.Adapter<MyContentRecyclerViewAdapter.ViewHolder> {

    private List<Interest> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context mContext;

    public MyContentRecyclerViewAdapter(List<Interest> items, OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        Picasso.get().load(holder.mItem.UserImage).into(holder.imageViewProfilePic);
        Picasso.get().load(holder.mItem.UserImage).transform(new CircleTransform()).into(holder.imageViewProfilePic);

        holder.textViewUsername.setText(holder.mItem.Username);
        holder.textViewHour.setText(holder.mItem.DateAndTime);
        holder.textViewCategory.setText(holder.mItem.Category);
        holder.textViewTextContent.setText(holder.mItem.Content);
        holder.textViewCounter.setText(Integer.toString(holder.mItem.Counter));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });

        holder.imageViewPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onPlusChecked(holder.mItem);
                }
            }
        });

        holder.imageViewCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onPlusUnchecked(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imageViewProfilePic;
        public final ImageView imageViewSeparationLine;
        public final ImageView imageViewPlus;
        public final ImageView imageViewCross;
        public final TextView textViewUsername;
        public final TextView textViewHour;
        public final TextView textViewCategory;
        public final TextView textViewTextContent;
        public final TextView textViewCounter;
        public Interest mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            imageViewProfilePic = view.findViewById(R.id.imageViewProfilePic);
            imageViewSeparationLine = view.findViewById(R.id.imageViewSeparationLine);
            imageViewPlus = view.findViewById(R.id.imageViewPlus);
            imageViewCross = view.findViewById(R.id.imageViewCross);
            textViewUsername = view.findViewById(R.id.textViewUsername);
            textViewHour = view.findViewById(R.id.textViewHour);
            textViewCategory = view.findViewById(R.id.textViewCategory);
            textViewTextContent = view.findViewById(R.id.textViewTextContent);
            textViewCounter = view.findViewById(R.id.textViewCounter);
        }
    }
}

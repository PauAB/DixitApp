package com.example.dixitapp;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dixitapp.ContentFragment.OnListFragmentInteractionListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link InterestEntity} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyContentRecyclerViewAdapter extends RecyclerView.Adapter<MyContentRecyclerViewAdapter.ViewHolder> {

    private List<InterestEntity> mValues;
    private final OnListFragmentInteractionListener mListener;
    private final Context mContext;

    public MyContentRecyclerViewAdapter(List<InterestEntity> items, OnListFragmentInteractionListener listener, Context context) {
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

        Picasso.get().load(holder.mItem.getUserImage()).into(holder.imageViewProfilePic);
        Picasso.get().load(holder.mItem.getUserImage()).transform(new CircleTransform()).into(holder.imageViewProfilePic);

        holder.textViewUsername.setText(holder.mItem.getUsername());
        holder.textViewHour.setText(holder.mItem.getDateAndTime());
        holder.textViewCategory.setText(holder.mItem.getCategory());
        holder.textViewTextContent.setText(holder.mItem.getText());

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
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setNewInterests(List<InterestEntity> newInterests)
    {
        mValues = newInterests;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView imageViewProfilePic;
        public final ImageView imageViewSeparationLine;
        public final TextView textViewUsername;
        public final TextView textViewHour;
        public final TextView textViewCategory;
        public final TextView textViewTextContent;
        public InterestEntity mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            imageViewProfilePic = view.findViewById(R.id.imageViewProfilePic);
            imageViewSeparationLine = view.findViewById(R.id.imageViewSeparationLine);
            textViewUsername = view.findViewById(R.id.textViewUsername);
            textViewHour = view.findViewById(R.id.textViewHour);
            textViewCategory = view.findViewById(R.id.textViewCategory);
            textViewTextContent = view.findViewById(R.id.textViewTextContent);
        }

        /*@Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }*/
    }
}

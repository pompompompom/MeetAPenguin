package com.penguin.meetapenguin.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.model.Contact;
import com.penguin.meetapenguin.model.InboxMessage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InboxFragmentAdapter extends RecyclerView.Adapter<InboxFragmentAdapter.ViewHolder> {

    private final ArrayList<InboxMessage> mValues;
    private Context mContext;

    public InboxFragmentAdapter(ArrayList<InboxMessage> items, Context context) {
        mValues = items;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getContact().getName());
        holder.mContentView.setText(mValues.get(position).getMessage());
        Picasso.with(mContext)
                .load(mValues.get(position).getContact().getPhotoUrl())
                .placeholder(R.drawable.placeholder)
                .into(holder.mPersonPhoto);
        holder.mTimestamp.setText(mValues.get(position).getTimeStamp());

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setDataSet(ArrayList<InboxMessage> dataSet) {
        mValues.clear();
        mValues.addAll(dataSet);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mPersonPhoto;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mTimestamp;
        public InboxMessage mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mPersonPhoto = (ImageView) view.findViewById(R.id.person_image);
            mTimestamp = (TextView) view.findViewById((R.id.timestamp));
        }

        @Override
        public String toString() {
            return super.toString() + " - '" + mContentView.getText() + "'";
        }
    }


}

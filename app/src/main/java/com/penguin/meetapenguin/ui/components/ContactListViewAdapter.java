package com.penguin.meetapenguin.ui.components;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.ui.fragments.ContactListFragment;
import com.penguin.meetapenguin.util.ZipCodeHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ContactListViewAdapter extends RecyclerView.Adapter<ContactListViewAdapter.ViewHolder> {

    private final ArrayList<Contact> mValues;
    private final ContactListFragment.OnListFragmentInteractionListener mListener;
    private Context mContext;

    public ContactListViewAdapter(ArrayList<Contact> items, ContactListFragment.OnListFragmentInteractionListener listener, Context context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getName());
        holder.mContentView.setText(mValues.get(position).getDescription());
        holder.mZipView.setText(ZipCodeHelper.getZipCodeFromPrefs(mValues.get(position)));

        int photoID = 0;
        try {
            photoID = Integer.parseInt(mValues.get(position).getPhotoUrl());
        }catch(Exception e){
            photoID = 0;
        }
            if(photoID != 0){
            holder.mPersonPhoto.setImageDrawable(mContext.getResources()
                    .getDrawable(photoID));
//            mToolBarImageProfile.setImageDrawable(inflater.getContext().getResources().getDrawable(mContact.getProfilePicResId()));
        }else {
            Picasso.with(mContext)
                    .load(mValues.get(position).getPhotoUrl())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.mPersonPhoto);
        }
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

    public void setDataSet(ArrayList<Contact> dataSet) {
        mValues.clear();
        mValues.addAll(dataSet);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mPersonPhoto;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mZipView;

        public Contact mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mPersonPhoto = (ImageView) view.findViewById(R.id.person_image);
            mZipView = (TextView)view.findViewById(R.id.zip);
        }

        @Override
        public String toString() {
            return super.toString() + " - '" + mContentView.getText() + "'";
        }
    }


}

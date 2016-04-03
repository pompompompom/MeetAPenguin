package com.penguin.meetapenguin.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.model.ContactInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PrepareShareContactViewAdapter extends RecyclerView.Adapter<PrepareShareContactViewAdapter.ViewHolder> {

    private final ArrayList<ContactInfo> mValues;
    private final PrepareShareFragment.OnShareFragmentInteraction mListener;
    private Context mContext;

    public PrepareShareContactViewAdapter(ArrayList<ContactInfo> items, PrepareShareFragment.OnShareFragmentInteraction listener, Context context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.share_contact_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContactInfo.setText(mValues.get(position).getAtrributeValue());
        Picasso.with(mContext)
                .load(R.drawable.ic_group_black_24dp)
                .placeholder(R.drawable.placeholder)
                .into(holder.mContactIcon);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setDataSet(ArrayList<ContactInfo> dataSet) {
        mValues.clear();
        mValues.addAll(dataSet);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mContactIcon;
        public final TextView mContactInfo;
        public final CheckBox mContactCheckBox;
        public ContactInfo mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContactInfo = (TextView) view.findViewById(R.id.contact_info);
            mContactCheckBox = (CheckBox) view.findViewById(R.id.share_checkbox);
            mContactIcon = (ImageView) view.findViewById(R.id.contact_info_icon);
        }

        @Override
        public String toString() {
            return super.toString() + " - '" + mContactCheckBox.getText() + "'";
        }
    }


}

package com.penguin.meetapenguin.ui.components;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.entities.ContactInfo;
import com.penguin.meetapenguin.ui.fragments.PrepareShareFragment;

import java.util.ArrayList;

public class ContactViewAdapter extends RecyclerView.Adapter<ContactViewAdapter.ViewHolder> {
    public static final int MODE_SHARE_CONTACT = 0;
    public static final int MODE_EDIT_CONTACT = 1;
    public static final int MODE_VIEW_CONTACT = 2;

    private final ArrayList<ContactInfo> mValues;
    private final PrepareShareFragment.OnShareFragmentInteraction mListener;
    int mMode;
    private Context mContext;

    public ContactViewAdapter(ArrayList<ContactInfo> items, PrepareShareFragment.OnShareFragmentInteraction listener,
                              Context context) {
        this(items, listener, context, MODE_SHARE_CONTACT);
    }

    public ContactViewAdapter(ArrayList<ContactInfo> items, PrepareShareFragment.OnShareFragmentInteraction listener,
                              Context context, int mode) {
        mValues = items;
        mListener = listener;
        mContext = context;
        mMode = mode;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (mMode == MODE_SHARE_CONTACT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.share_contact_item, parent, false);
        } else if (mMode == MODE_EDIT_CONTACT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.edit_contact_item, parent, false);
        } else if (mMode == MODE_VIEW_CONTACT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_contact_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        if (mMode == MODE_SHARE_CONTACT) {
            holder.mContactInfo.setText(mValues.get(position).getAtrributeValue());
            holder.mContactIcon.setImageDrawable(mContext.getDrawable(mValues.get(position).getIconResId(mContext)));
        } else if (mMode == MODE_EDIT_CONTACT) {
            holder.mEditContactInfo.setText(mValues.get(position).getAtrributeValue());
            holder.mContactIcon.setImageDrawable(mContext.getDrawable(mValues.get(position).getIconResId(mContext)));
        } else if (mMode == MODE_VIEW_CONTACT) {
            holder.mContactInfo.setText(mValues.get(position).getAtrributeValue());
            holder.mContactIcon.setImageDrawable(mContext.getDrawable(mValues
                    .get(position).getIconResId(mContext)));
        }
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
        public ImageView mContactIcon = null;
        public TextView mContactInfo = null;
        public CheckBox mContactCheckBox = null;
        public ContactInfo mItem = null;
        public EditText mEditContactInfo = null;
        public Button mSaveButton = null;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            if (mMode == MODE_SHARE_CONTACT) {
                mContactInfo = (TextView) view.findViewById(R.id.contact_info);
                mContactCheckBox = (CheckBox) view.findViewById(R.id.share_checkbox);
                mContactIcon = (ImageView) view.findViewById(R.id.contact_info_icon);
            } else if (mMode == MODE_EDIT_CONTACT) {
                mEditContactInfo = (EditText) view.findViewById(R.id.edit_contact_info);
                mContactIcon = (ImageView) view.findViewById(R.id.contact_info_icon);
            } else if (mMode == MODE_VIEW_CONTACT) {
                mContactInfo = (TextView) view.findViewById(R.id.contact_info);
                mContactIcon = (ImageView) view.findViewById(R.id.contact_info_icon);
            }
        }

        @Override
        public String toString() {
            return super.toString() + " - '" + mContactCheckBox != null ? mContactCheckBox.getText().toString() : "";
        }
    }


}

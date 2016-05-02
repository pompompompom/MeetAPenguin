package com.penguin.meetapenguin.ui.components;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.entities.Attribute;
import com.penguin.meetapenguin.entities.ContactInfo;
import com.penguin.meetapenguin.util.entitiesHelper.AttributesHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * A adapter that allows the user to select a specific contact information for a user.
 */
public class SelectContactInfoAdapter extends RecyclerView.Adapter<SelectContactInfoAdapter.ViewHolder> {
    public static final int MODE_SHARE_CONTACT = 0;
    public static final int MODE_EDIT_CONTACT = 1;
    public static final int MODE_VIEW_CONTACT = 2;
    private static final String TAG = SelectContactInfoAdapter.class.getSimpleName();
    private static final int FILLED_ITEM = 2;
    private static final int NEW_ITEM = 1;

    private final ArrayList<ContactInfo> mContactInfoList;
    private final Set<ContactInfo> mSelected;
    private final OnContactViewAdapterInteraction mListener;
    private final RecyclerView mRecycleView;
    int mMode;
    private Context mContext;

    public SelectContactInfoAdapter(RecyclerView recycleView, Set<ContactInfo> items, Set<ContactInfo> selected, OnContactViewAdapterInteraction listener,
                                    Context context) {
        mRecycleView = recycleView;
        mContactInfoList = new ArrayList<ContactInfo>(items);
        mSelected = selected;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (mMode == MODE_SHARE_CONTACT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.share_contact_item, parent, false);
        }
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: " + position);
        holder.mItem = mContactInfoList.get(position);
        holder.mContactInfo.setText(mContactInfoList.get(position).getAttributeValue());
        holder.mContactIcon.setImageDrawable(mContext.getDrawable(mContactInfoList.get(position).getIconResId(mContext)));

        if (mSelected.contains(mContactInfoList.get(position))) {
            holder.mContactCheckBox.setChecked(true);
        } else {
            holder.mContactCheckBox.setChecked(false);
        }

        holder.mContactCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mSelected.add(mContactInfoList.get(position));
                } else {
                    mSelected.remove(mContactInfoList.get(position));
                }
                mListener.onContacInfoSelected(mContactInfoList.get(position), isChecked);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mContactInfoList.size();
    }

    private List<Attribute> getAvailableAttributes(Attribute ignoreAttribute) {
        List<Attribute> attributesList = AttributesHelper.getAllAttributes();
        for (ContactInfo contactInfo : mContactInfoList) {
            if (contactInfo.getAttribute().equals(ignoreAttribute)) continue;
            attributesList.remove(contactInfo.getAttribute());
        }
        return attributesList;
    }

    public interface OnContactViewAdapterInteraction {
        void onContacInfoSelected(ContactInfo contactInfo, boolean isChecked);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public ImageView mContactIcon = null;
        public TextView mContactInfo = null;
        public CheckBox mContactCheckBox = null;
        public ContactInfo mItem = null;
        public int mType;

        public ViewHolder(View view, int type) {
            super(view);
            mView = view;
            mType = type;
            mContactInfo = (TextView) view.findViewById(R.id.contact_info);
            mContactCheckBox = (CheckBox) view.findViewById(R.id.share_checkbox);
            mContactIcon = (ImageView) view.findViewById(R.id.contact_info_icon);

        }

        @Override
        public String toString() {
            return super.toString() + " - '" + mContactCheckBox != null ? mContactCheckBox.getText().toString() : "";
        }
    }


}

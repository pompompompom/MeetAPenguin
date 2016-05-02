package com.penguin.meetapenguin.ui.components;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.entities.Attribute;
import com.penguin.meetapenguin.entities.ContactInfo;
import com.penguin.meetapenguin.util.entitiesHelper.AttributesHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * ContactViewAdater presents the user information for a RecycleView.
 */
public class ContactViewAdapter extends RecyclerView.Adapter<ContactViewAdapter.ViewHolder> {
    public static final int MODE_SHARE_CONTACT = 0;
    public static final int MODE_EDIT_CONTACT = 1;
    public static final int MODE_VIEW_CONTACT = 2;
    private static final String TAG = ContactViewAdapter.class.getSimpleName();
    private static final int FILLED_ITEM = 2;
    private static final int NEW_ITEM = 1;
    private final OnContactViewAdapterInteraction mListener;
    private final RecyclerView mRecycleView;
    int mMode;
    private ArrayList<ContactInfo> mContactInfoList;
    private Context mContext;

    public ContactViewAdapter(RecyclerView recycleView, Set<ContactInfo> items, OnContactViewAdapterInteraction listener,
                              Context context) {
        this(recycleView, items, listener, context, MODE_SHARE_CONTACT);
    }

    public ContactViewAdapter(RecyclerView recycleView, Set<ContactInfo> items, OnContactViewAdapterInteraction listener,
                              Context context, int mode) {
        mRecycleView = recycleView;
        mContactInfoList = new ArrayList<ContactInfo>(items);
        mListener = listener;
        mContext = context;
        mMode = mode;
    }

    @Override
    public int getItemViewType(int position) {
        if (mContactInfoList.get(position).isEditing()) {
            return NEW_ITEM;
        } else {
            return FILLED_ITEM;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (mMode == MODE_SHARE_CONTACT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.share_contact_item, parent, false);
        } else if (mMode == MODE_EDIT_CONTACT) {
            if (viewType == NEW_ITEM)
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.new_contact_item, parent, false);
            else
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.edit_contact_item, parent, false);
        } else if (mMode == MODE_VIEW_CONTACT) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_contact_item, parent, false);
        }
        return new ViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: " + position);
        holder.mItem = mContactInfoList.get(position);

        if (mMode == MODE_EDIT_CONTACT) {
            if (holder.mType == FILLED_ITEM) {
                holder.mEditContactInfo.setText(mContactInfoList.get(position).getAttributeValue());
                holder.mSelectedAttribute = mContactInfoList.get(position).getAttribute();
                holder.mContactIcon.setImageDrawable(mContext.getDrawable(mContactInfoList.get(position).getIconResId(mContext)));
            } else {
                List<Attribute> availableAttributes = getAvailableAttributes(mContactInfoList.get(position).getAttribute());
                AttributeSpinAdapter attributeSpinAdapter = (AttributeSpinAdapter) holder.mSpiner.getAdapter();
                attributeSpinAdapter.updateDataSet(availableAttributes);

                int selectedItem = availableAttributes.indexOf(mContactInfoList.get(position).getAttribute());
                holder.mSpiner.setSelection(selectedItem);
                holder.mSelectedAttribute = mContactInfoList.get(position).getAttribute();

                holder.mEditContactInfo.setText(mContactInfoList.get(position).getAttributeValue());
                holder.mEditContactInfo.setHint(holder.mSelectedAttribute.getName());
                holder.mSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        holder.mSelectedAttribute = ((Attribute) holder.mSpiner.getSelectedItem());
                        holder.mEditContactInfo.setHint(((Attribute) holder.mSpiner.getSelectedItem()).getName());
                        mContactInfoList.get(position).setAttribute(holder.mAttributesList.get(pos));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            holder.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onContactInfoDeleted(mContactInfoList.get(position));
                    }
                    mContactInfoList.remove(position);
                    notifyDataSetChanged();
                }
            });

        } else if (mMode == MODE_VIEW_CONTACT) {
            holder.mContactInfo.setText(mContactInfoList.get(position).getAttributeValue());
            holder.mContactIcon.setImageDrawable(AttributesHelper.getDrawable(mContactInfoList
                    .get(position).getAttribute().getId()));
        }
    }

    public void saveContact() {
        for (int i = 0; i < getItemCount(); i++) {
            ViewHolder holder = (ViewHolder) mRecycleView.findViewHolderForPosition(i);

            mContactInfoList.get(i).setEditing(false);

            if (holder != null) {
                if (mMode == MODE_EDIT_CONTACT) {
                    mContactInfoList.get(i).setAttribute(holder.mSelectedAttribute);
                    mContactInfoList.get(i).setAttributeValue(holder.mEditContactInfo.getText().toString());
                }
            }
        }
    }

    public void removeEmpty() {
        List<ContactInfo> toRemove = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            ViewHolder holder = (ViewHolder) mRecycleView.findViewHolderForPosition(i);
            if (mMode == MODE_EDIT_CONTACT) {
                if (mContactInfoList.get(i).getAttributeValue().isEmpty() || (holder != null && holder.mEditContactInfo.getText().toString().isEmpty())) {
                    toRemove.add(mContactInfoList.get(i));
                }
            }
        }
        for (ContactInfo contactInfo : toRemove) {
            mListener.onContactInfoDeleted(contactInfo);
        }
        mContactInfoList.removeAll(toRemove);
        notifyDataSetChanged();
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

    public boolean contactInfoAvaible() {
        return getAvailableAttributes(null).size() > 0;
    }

    public Attribute getAttributeAvailable() {
        return getAvailableAttributes(null).get(0);
    }

    public void updateDataSet(Set<ContactInfo> contactInfoList) {
        mContactInfoList = new ArrayList<ContactInfo>(contactInfoList);
        notifyDataSetChanged();
    }

    public interface OnContactViewAdapterInteraction {
        void onContactInfoDeleted(ContactInfo contactInfo);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public ImageView mContactIcon = null;
        public TextView mContactInfo = null;
        public CheckBox mContactCheckBox = null;
        public ContactInfo mItem = null;
        public EditText mEditContactInfo = null;
        public Spinner mSpiner = null;
        public ImageView mDelete = null;
        public int mType;
        public List<Attribute> mAttributesList;
        public Attribute mSelectedAttribute;

        public ViewHolder(View view, int type) {
            super(view);
            mView = view;
            mType = type;
            if (mMode == MODE_EDIT_CONTACT) {
                mEditContactInfo = (EditText) view.findViewById(R.id.edit_contact_info);
                mContactIcon = (ImageView) view.findViewById(R.id.contact_info_icon);
                mDelete = (ImageView) view.findViewById(R.id.delete_contact_info);
                if (type == NEW_ITEM) {
                    mDelete.setColorFilter(mContext.getResources().getColor(R.color.greysigh), PorterDuff.Mode.SRC_IN);

                    mAttributesList = getAvailableAttributes(null);
                    AttributeSpinAdapter adapter = new AttributeSpinAdapter(mContext, mAttributesList);
                    mSpiner = (Spinner) view.findViewById(R.id.spinner);
                    mSpiner.setAdapter(adapter);
                }
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

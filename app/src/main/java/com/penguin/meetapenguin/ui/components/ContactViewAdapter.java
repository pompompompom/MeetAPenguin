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
import com.penguin.meetapenguin.ui.fragments.PrepareShareFragment;
import com.penguin.meetapenguin.util.AttributesHelper;

import java.util.ArrayList;
import java.util.List;

public class ContactViewAdapter extends RecyclerView.Adapter<ContactViewAdapter.ViewHolder> {
    public static final int MODE_SHARE_CONTACT = 0;
    public static final int MODE_EDIT_CONTACT = 1;
    public static final int MODE_VIEW_CONTACT = 2;
    private static final String TAG = ContactViewAdapter.class.getSimpleName();
    private static final int FILLED_ITEM = 2;
    private static final int NEW_ITEM = 1;

    private final ArrayList<ContactInfo> mValues;
    private final PrepareShareFragment.OnShareFragmentInteraction mListener;
    private final RecyclerView mRecycleView;
    int mMode;
    private Context mContext;

    public ContactViewAdapter(RecyclerView recycleView, ArrayList<ContactInfo> items, PrepareShareFragment.OnShareFragmentInteraction listener,
                              Context context) {
        this(recycleView, items, listener, context, MODE_SHARE_CONTACT);
    }

    public ContactViewAdapter(RecyclerView recycleView, ArrayList<ContactInfo> items, PrepareShareFragment.OnShareFragmentInteraction listener,
                              Context context, int mode) {
        mRecycleView = recycleView;
        mValues = items;
        mListener = listener;
        mContext = context;
        mMode = mode;
    }

    @Override
    public int getItemViewType(int position) {
        if (mValues.get(position).isEditing()) {
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
        holder.mItem = mValues.get(position);

        if (mMode == MODE_SHARE_CONTACT) {
            holder.mContactInfo.setText(mValues.get(position).getAtrributeValue());
            holder.mContactIcon.setImageDrawable(mContext.getDrawable(mValues.get(position).getIconResId(mContext)));
        } else if (mMode == MODE_EDIT_CONTACT) {
            holder.mEditContactInfo.setText(mValues.get(position).getAtrributeValue());
            if (holder.mType == FILLED_ITEM) {
                holder.mContactIcon.setImageDrawable(mContext.getDrawable(mValues.get(position).getIconResId(mContext)));
            } else {
                mValues.get(position).setAttribute(holder.mAttributesList.get(holder.mSelectedAttribute));
                holder.mSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        holder.mSelectedAttribute = pos;
                        holder.mEditContactInfo.setHint(((Attribute) holder.mSpiner.getSelectedItem()).getName());
                        mValues.get(position).setAttribute(holder.mAttributesList.get(pos));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            holder.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mValues.remove(position);
                    notifyDataSetChanged();
                }
            });

        } else if (mMode == MODE_VIEW_CONTACT) {
            holder.mContactInfo.setText(mValues.get(position).getAtrributeValue());
            holder.mContactIcon.setImageDrawable(mContext.getDrawable(mValues
                    .get(position).getIconResId(mContext)));
        }
    }

    public void saveState() {
        for (int i = 0; i < getItemCount(); i++) {
            ViewHolder holder = (ViewHolder) mRecycleView.findViewHolderForPosition(i);

            if (holder != null) {
                if (mMode == MODE_EDIT_CONTACT) {
                    mValues.get(i).setAttributeValue(holder.mEditContactInfo.getText().toString());
                }
            }
        }
    }

    public void saveContact() {
        for (int i = 0; i < getItemCount(); i++) {
            ViewHolder holder = (ViewHolder) mRecycleView.findViewHolderForPosition(i);

            if (holder != null) {
                if (mMode == MODE_EDIT_CONTACT) {
                    mValues.get(i).setAttributeValue(holder.mEditContactInfo.getText().toString());
                    mValues.get(i).setEditing(false);
                }
            }
        }
    }

    public void removeEmpty() {
        List<ContactInfo> toRemove = new ArrayList<>();
        for (int i = 0; i < getItemCount(); i++) {
            ViewHolder holder = (ViewHolder) mRecycleView.findViewHolderForPosition(i);
            if (holder != null) {
                if (mMode == MODE_EDIT_CONTACT) {
                    if (mValues.get(i).getAtrributeValue().isEmpty() && holder.mEditContactInfo.getText().toString().isEmpty()) {
                        toRemove.add(mValues.get(i));
                    }
                }
            }
        }
        mValues.removeAll(toRemove);
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
        public Spinner mSpiner = null;
        public ImageView mDelete = null;
        public int mType;
        public List<Attribute> mAttributesList;
        public int mSelectedAttribute;

        public ViewHolder(View view, int type) {
            super(view);
            mView = view;
            mType = type;
            if (mMode == MODE_SHARE_CONTACT) {
                mContactInfo = (TextView) view.findViewById(R.id.contact_info);
                mContactCheckBox = (CheckBox) view.findViewById(R.id.share_checkbox);
                mContactIcon = (ImageView) view.findViewById(R.id.contact_info_icon);
            } else if (mMode == MODE_EDIT_CONTACT) {
                mEditContactInfo = (EditText) view.findViewById(R.id.edit_contact_info);
                mContactIcon = (ImageView) view.findViewById(R.id.contact_info_icon);
                mDelete = (ImageView) view.findViewById(R.id.delete_contact_info);
                if (type == NEW_ITEM) {
                    mDelete.setColorFilter(mContext.getResources().getColor(R.color.greysigh), PorterDuff.Mode.SRC_IN);

                    mAttributesList = AttributesHelper.getAllAttributes();
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

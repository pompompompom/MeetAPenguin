package com.penguin.meetapenguin.ui.components;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.entities.InboxMessage;
import com.penguin.meetapenguin.ui.fragments.InboxFragment;
import com.penguin.meetapenguin.util.DateFormatter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InboxFragmentAdapter extends RecyclerView.Adapter<InboxFragmentAdapter.ViewHolder> {

    private final ArrayList<InboxMessage> mValues;
    private final InboxFragment.OnListInboxFragmentInteractionListener
            mListener;
    private Context mContext;

    public InboxFragmentAdapter(ArrayList<InboxMessage> items, InboxFragment
            .OnListInboxFragmentInteractionListener listener, Context
                                        context) {
        mValues = items;
        mListener = listener;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_message, parent, false);
        return new ViewHolder(view);
    }

    //If using a layout that is too small, it can inflate a layout without picture profile.
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mValues.get(position).isDeleted()) {
            RecyclerView.LayoutParams param = (RecyclerView.LayoutParams) holder.mView.getLayoutParams();
            param.height = 0;
            param.width = 0;
            holder.mView.setLayoutParams(param);
        }

        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getContact().getName());
        holder.mContentView.setText(mValues.get(position).getMessage());
        if (holder.mPersonPhoto != null) {
            Picasso.with(mContext)
                    .load(mValues.get(position).getContact().getPhotoUrl())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.mPersonPhoto);
        }
        holder.mTimestamp.setText(DateFormatter.convertTimeStampToDate(mValues.get(position).getTimeStamp()));

        holder.mSurface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    if (holder.mSwipeLayout.getOpenStatus() == SwipeLayout.Status.Close) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListInboxFragmentInteraction(holder.mItem);
                    }
                }
            }
        });

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValues.get(position).setDeleted(true);
                mListener.onMessageDeleted();
                notifyDataSetChanged();
            }
        });

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
        public final LinearLayout mDelete;
        public final SwipeLayout mSwipeLayout;
        public final LinearLayout mSurface;
        public InboxMessage mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mPersonPhoto = (ImageView) view.findViewById(R.id.person_image);
            mTimestamp = (TextView) view.findViewById((R.id.timestamp));
            mDelete = (LinearLayout) view.findViewById(R.id.bottom_wrapper);
            mSurface = (LinearLayout) view.findViewById(R.id.surface_message);

            mSwipeLayout = (SwipeLayout) view.findViewById(R.id.line);
            mSwipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
            mSwipeLayout.addDrag(SwipeLayout.DragEdge.Left, view.findViewById(R.id.bottom_wrapper));
            mSwipeLayout.setLeftSwipeEnabled(false);
        }

        @Override
        public String toString() {
            return super.toString() + " - '" + mContentView.getText() + "'";
        }
    }


}

package com.penguin.meetapenguin.ui.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.ui.components.ContactViewAdapter;
import com.penguin.meetapenguin.ws.remote.RequestContactRenew;
import com.squareup.picasso.Picasso;

import java.util.Date;

/**
 * Fragment to mView a single contact, accessed from the Contact List.
 */
public class SingleContactFragment extends Fragment {

    private static final String TAG = SingleContactFragment.class.getSimpleName();
    private Contact mContact;
    private Toolbar mToolbar;
    private View mToolbarView;
    private CircularImageView mImageProfile;
    private TextView mName;
    private TextView mDescription;
    private RecyclerView mRecyclerView;
    private ContactViewAdapter mContactAdapter;
    private FloatingActionButton mFloatingActionButton;
    private RequestQueue mRequestQueue;
    private View mView;

    /**
     * Default no-args constructor.
     */
    public SingleContactFragment() {

    }

    @SuppressLint("ValidFragment")
    public SingleContactFragment(Toolbar toolbar) {
        this.mToolbar = toolbar;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_single_contact,
                container, false);

        inflater.inflate(R.layout.share_fragment_toolbar, mToolbar, true);
        mToolbarView = mToolbar.findViewById(R.id.share_fragment_toolbar);
        mRequestQueue = Volley.newRequestQueue(getContext());

        mImageProfile = (CircularImageView) mToolbar.findViewById(R.id.profile_picture);
        int photoID = 0;
        try {
            photoID = Integer.parseInt(mContact.getPhotoUrl());
        }catch(Exception e){
            photoID = 0;
        }

        if(photoID != 0){
            mImageProfile.setImageDrawable(inflater.getContext()
                    .getResources()
                    .getDrawable(photoID));
        }else {
            Picasso.with(getContext())
                    .load(mContact.getPhotoUrl())
                    .placeholder(R.drawable.placeholder)
                    .into(mImageProfile);
        }

        mName = (TextView) mToolbar.findViewById(R.id.name);
        mDescription = (TextView) mToolbar.findViewById(R.id.description);
        mName.setText(mContact.getName());
        mDescription.setText(mContact.getDescription());

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mContactAdapter = new ContactViewAdapter(mRecyclerView, mContact.getContactInfoArrayList(),
                null, getContext(), ContactViewAdapter.MODE_VIEW_CONTACT);
        mRecyclerView.setAdapter(mContactAdapter);


        mFloatingActionButton = (FloatingActionButton) mView
                .findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(inflater.getContext()).setMessage
                                ("Do you want to request the renew of this contact information?")
                                .setPositiveButton("Add", new
                                        DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                RequestContactRenew contactRenew = new RequestContactRenew(mContact.getId(), new Response.Listener<String>() {
                                                    @Override
                                                    public void onResponse(String response) {
                                                        Toast.makeText(SingleContactFragment.this.getContext(), "Request for renewing information sent with success", Toast.LENGTH_SHORT).show();
                                                    }
                                                }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        Toast.makeText(SingleContactFragment.this.getContext(), "Error while sending your renew request", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                                contactRenew.setTag(TAG);
                                                mRequestQueue.add(contactRenew);
                                            }
                                        }).show();
                    }
                });
        if (mContact.getExpiration() < new Date().getTime()) {
            mFloatingActionButton.setVisibility(View.VISIBLE);
        } else {
            mFloatingActionButton.setVisibility(View.GONE);
        }


        return mView;
    }

    private void SendRenewMessage() {

    }

    public void setContact(Contact contact) {
        this.mContact = contact;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //You added a lot of mView into the toolbar to customize it to this fragment. So remove it.
        mToolbar.removeView(mToolbarView);
        mRequestQueue.cancelAll(TAG);
    }
}

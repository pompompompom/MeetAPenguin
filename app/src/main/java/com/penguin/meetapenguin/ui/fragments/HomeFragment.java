package com.penguin.meetapenguin.ui.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.entities.ContactInfo;
import com.penguin.meetapenguin.ui.activities.MainActivity;
import com.penguin.meetapenguin.ui.components.ContactViewAdapter;
import com.penguin.meetapenguin.util.ProfileManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Fragment to display main screen.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private Contact mContact;
    private Toolbar toolbar;
    private View mToolbarView;
    private boolean dialogShown = false;
    private TextView mTVName;
    private TextView mTVDescription;
    private RecyclerView mRecyclerView;
    private ContactViewAdapter mContactAdapter;
    private View mFragmentRootView;
    private CircularImageView mToolBarImageProfile;
    private ArrayList<ContactInfo> contactInfoList;

    public HomeFragment() {
    }

    @SuppressLint("ValidFragment")
    public HomeFragment(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dialogShown = false;
        mFragmentRootView = inflater.inflate(R.layout.fragment_home, container, false);

        toolbar = ((MainActivity) getActivity()).getToolBar();
        mContact = ProfileManager.getInstance().getContact();
        inflater.inflate(R.layout.share_fragment_toolbar, toolbar, true);
        mToolbarView = toolbar.findViewById(R.id.share_fragment_toolbar);

        mToolBarImageProfile = (CircularImageView) toolbar.findViewById(R.id.profile_picture);
        Picasso.with(getContext())
                .load(mContact.getPhotoUrl())
                .placeholder(R.drawable.placeholder)
                .into(mToolBarImageProfile);

        mToolBarImageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dialogShown) {
                    new AlertDialog.Builder(inflater.getContext()).setMessage("Change Profile Picture?").setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO
                            dialogShown = false;
                        }
                    }).setNeutralButton("Gallery", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO
                            dialogShown = false;
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO
                            dialogShown = false;
                        }
                    }).show();
                    dialogShown = true;
                }
            }
        });

        mTVName = (TextView) toolbar.findViewById(R.id.name);
        mTVDescription = (TextView) toolbar.findViewById(R.id.description);
        mTVName.setText(mContact.getName());
        mTVDescription.setText(mContact.getDescription());

        mRecyclerView = (RecyclerView) mFragmentRootView.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        // TODO add interaction adapter
        contactInfoList = mContact.getContactInfoArrayList();
        mContactAdapter = new ContactViewAdapter(mRecyclerView, contactInfoList,
                null, getContext(), ContactViewAdapter.MODE_EDIT_CONTACT);
        mRecyclerView.setAdapter(mContactAdapter);

        final FloatingActionMenu floatingActionMenu = (FloatingActionMenu) mFragmentRootView.findViewById(R.id.fab);

        FloatingActionButton floatingActionButtonAddNew = (FloatingActionButton) mFragmentRootView
                .findViewById(R.id.add_new_contact_info);
        floatingActionButtonAddNew.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mContactAdapter.contactInfoAvaible()) {
                            mContactAdapter.saveContact();
                            ContactInfo emptyContactInfo = new ContactInfo(mContactAdapter.getAttributeAvailable(), "", "");
                            emptyContactInfo.setEditing(true);
                            contactInfoList.add(emptyContactInfo);
                            mContactAdapter.notifyDataSetChanged();
                            mRecyclerView.invalidate();
                            floatingActionMenu.close(true);
                        } else {

                        }
                    }
                });

        FloatingActionButton floatingActionButtonSave = (FloatingActionButton) mFragmentRootView
                .findViewById(R.id.save);
        floatingActionButtonSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContactAdapter.saveContact();
                        mContactAdapter.removeEmpty();
                        mContactAdapter.notifyDataSetChanged();
                        floatingActionMenu.close(true);
                        mRecyclerView.invalidate();
                    }
                });

        return mFragmentRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView() called with: " + "");
        //You added a lot of mFragmentRootView into the toolbar to customize it to this fragment. So remove it.
        toolbar.removeView(mToolbarView);
        mContactAdapter.removeEmpty();
    }
}

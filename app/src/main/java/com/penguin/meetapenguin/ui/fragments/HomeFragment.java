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
import android.widget.Button;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.entities.ContactInfo;
import com.penguin.meetapenguin.ui.activities.MainActivity;
import com.penguin.meetapenguin.ui.components.ContactViewAdapter;
import com.penguin.meetapenguin.util.DataUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Fragment to display main screen.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();
    private Contact contact;
    private Toolbar toolbar;
    private View toolbarView;
    private boolean dialogShown = false;
    private TextView name;
    private TextView description;
    private RecyclerView recyclerView;
    private ContactViewAdapter mContactAdapter;
    private Button saveButton;
    private View view;
    private CircularImageView imageProfile;
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
        view = inflater.inflate(R.layout.fragment_home, container, false);

        toolbar = ((MainActivity) getActivity()).getToolBar();
        contact = DataUtil.getMockContact();
        inflater.inflate(R.layout.share_fragment_toolbar, toolbar, true);
        toolbarView = toolbar.findViewById(R.id.share_fragment_toolbar);

        imageProfile = (CircularImageView) toolbar.findViewById(R.id.profile_picture);
        Picasso.with(getContext())
                .load(contact.getPhotoUrl())
                .placeholder(R.drawable.placeholder)
                .into(imageProfile);

        imageProfile.setOnClickListener(new View.OnClickListener() {
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

        name = (TextView) toolbar.findViewById(R.id.name);
        description = (TextView) toolbar.findViewById(R.id.description);
        name.setText(contact.getName());
        description.setText(contact.getDescription());

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        // TODO add interaction adapter
        contactInfoList = contact.getContactInfoArrayList();
        mContactAdapter = new ContactViewAdapter(recyclerView, contactInfoList,
                null, getContext(), ContactViewAdapter.MODE_EDIT_CONTACT);
        recyclerView.setAdapter(mContactAdapter);

        final FloatingActionMenu floatingActionMenu = (FloatingActionMenu) view.findViewById(R.id.fab);

        FloatingActionButton floatingActionButtonAddNew = (FloatingActionButton) view
                .findViewById(R.id.add_new_contact_info);
        floatingActionButtonAddNew.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mContactAdapter.contactInfoAvaible()) {
                            ContactInfo emptyContactInfo = new ContactInfo(mContactAdapter.getAttributeAvailable(), "", "");
                            emptyContactInfo.setEditing(true);
                            contactInfoList.add(emptyContactInfo);
                            mContactAdapter.saveContact();
                            mContactAdapter.notifyDataSetChanged();
                            recyclerView.invalidate();
                            floatingActionMenu.close(true);
                        } else {

                        }
                    }
                });

        FloatingActionButton floatingActionButtonSave = (FloatingActionButton) view
                .findViewById(R.id.save);
        floatingActionButtonSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mContactAdapter.saveContact();
                        mContactAdapter.removeEmpty();
                        mContactAdapter.notifyDataSetChanged();
                        floatingActionMenu.close(true);
                        recyclerView.invalidate();
                    }
                });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView() called with: " + "");
        //You added a lot of view into the toolbar to customize it to this fragment. So remove it.
        toolbar.removeView(toolbarView);
        mContactAdapter.removeEmpty();
    }
}

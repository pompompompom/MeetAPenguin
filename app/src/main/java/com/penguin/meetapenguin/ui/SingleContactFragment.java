package com.penguin.meetapenguin.ui;

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

import com.mikhaellopez.circularimageview.CircularImageView;
import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.model.Contact;
import com.penguin.meetapenguin.util.DataUtil;
import com.squareup.picasso.Picasso;

/**
 * Fragment to view a single contact, accessed from the Contact List.
 */
public class SingleContactFragment extends Fragment {

    private Contact contact;
    private Toolbar toolbar;
    private View toolbarView;
    private CircularImageView imageProfile;
    private TextView name;
    private TextView description;
    private RecyclerView recyclerView;
    private ContactViewAdapter contactAdapter;
    private FloatingActionButton floatingActionButton;

    private boolean dialogShown = false;

    /**
     * Default no-args constructor.
     */
    public SingleContactFragment() {

    }

    @SuppressLint("ValidFragment")
    public SingleContactFragment(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dialogShown = false;
        View view = inflater.inflate(R.layout.fragment_single_contact,
                container, false);

        inflater.inflate(R.layout.share_fragment_toolbar, toolbar, true);
        toolbarView = toolbar.findViewById(R.id.share_fragment_toolbar);

        imageProfile = (CircularImageView) toolbar.findViewById(R.id.profile_picture);
        Picasso.with(getContext())
                .load(contact.getPhotoUrl())
                .placeholder(R.drawable.placeholder)
                .into(imageProfile);


        name = (TextView) toolbar.findViewById(R.id.name);
        description = (TextView) toolbar.findViewById(R.id.description);
        name.setText(contact.getName());
        description.setText(contact.getDescription());

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        // TODO add interaction adapter
        contactAdapter = new ContactViewAdapter(contact.getContactInfoArrayList(),
                null, getContext(), ContactViewAdapter.MODE_VIEW_CONTACT);
        recyclerView.setAdapter(contactAdapter);

        floatingActionButton = (FloatingActionButton) view
                .findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO: add note taking feature here
                        if (!dialogShown) {
                            new AlertDialog.Builder(inflater.getContext()).setMessage
                                    ("TODO: Add note feature here!")
                                    .setPositiveButton("Add", new
                                    DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialogShown = false;
                                        }
                                    }).show();
                            dialogShown = true;
                        }
                    }
                });


        return view;
    }

    public void setContact(Contact contact){
        this.contact = contact;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //You added a lot of view into the toolbar to customize it to this fragment. So remove it.
        toolbar.removeView(toolbarView);
    }
}

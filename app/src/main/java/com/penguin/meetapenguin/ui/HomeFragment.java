package com.penguin.meetapenguin.ui;

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

import com.mikhaellopez.circularimageview.CircularImageView;
import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.util.DataUtil;
import com.squareup.picasso.Picasso;

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
    private ContactViewAdapter contactAdapter;
    private Button saveButton;

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
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        contact = DataUtil.getMockContact();
        inflater.inflate(R.layout.share_fragment_toolbar, toolbar, true);
        toolbarView = toolbar.findViewById(R.id.share_fragment_toolbar);

        CircularImageView imageProfile = (CircularImageView) toolbar.findViewById(R.id.profile_picture);
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

        recyclerView = (RecyclerView) v.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        // TODO add interaction adapter
        contactAdapter = new ContactViewAdapter(contact.getContactInfoArrayList(),
                null, getContext(), ContactViewAdapter.MODE_EDIT_CONTACT);
        recyclerView.setAdapter(contactAdapter);

        saveButton = (Button) v.findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(inflater.getContext()).setMessage
                        ("Changes saved!")
                        .setPositiveButton("Okay", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
            }
        });

        return v;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView() called with: " + "");
        //You added a lot of view into the toolbar to customize it to this fragment. So remove it.
        toolbar.removeView(toolbarView);
    }
}

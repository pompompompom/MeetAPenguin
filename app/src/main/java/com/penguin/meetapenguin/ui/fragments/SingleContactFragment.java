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
 * Fragment to view a single contact, accessed from the Contact List.
 */
public class SingleContactFragment extends Fragment {

    private static final String TAG = SingleContactFragment.class.getSimpleName();
    private Contact contact;
    private Toolbar toolbar;
    private View toolbarView;
    private CircularImageView imageProfile;
    private TextView name;
    private TextView description;
    private RecyclerView recyclerView;
    private ContactViewAdapter contactAdapter;
    private FloatingActionButton floatingActionButton;
    private RequestQueue mRequestQueue;

    private boolean dialogShown = false;
    private View view;

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
        view = inflater.inflate(R.layout.fragment_single_contact,
                container, false);

        inflater.inflate(R.layout.share_fragment_toolbar, toolbar, true);
        toolbarView = toolbar.findViewById(R.id.share_fragment_toolbar);
        mRequestQueue = Volley.newRequestQueue(getContext());

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
        contactAdapter = new ContactViewAdapter(recyclerView, contact.getContactInfoArrayList(),
                null, getContext(), ContactViewAdapter.MODE_VIEW_CONTACT);
        recyclerView.setAdapter(contactAdapter);


        floatingActionButton = (FloatingActionButton) view
                .findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(inflater.getContext()).setMessage
                                ("Do you want to request the renew of this contact information?")
                                .setPositiveButton("Add", new
                                        DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                RequestContactRenew contactRenew = new RequestContactRenew(contact.getId(), new Response.Listener<String>() {
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
        if (contact.getExpiration() < new Date().getTime()) {
            floatingActionButton.setVisibility(View.VISIBLE);
        } else {
            floatingActionButton.setVisibility(View.GONE);
        }


        return view;
    }

    private void SendRenewMessage() {

    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //You added a lot of view into the toolbar to customize it to this fragment. So remove it.
        toolbar.removeView(toolbarView);
        mRequestQueue.cancelAll(TAG);
    }
}

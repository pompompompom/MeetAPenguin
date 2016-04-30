package com.penguin.meetapenguin.ui.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.dblayout.ContactInfoController;
import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.entities.ContactInfo;
import com.penguin.meetapenguin.ui.activities.MainActivity;
import com.penguin.meetapenguin.ui.components.ContactViewAdapter;
import com.penguin.meetapenguin.util.ProfileManager;
import com.penguin.meetapenguin.util.ServerConstants;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Fragment to display main screen.
 */
public class HomeFragment extends Fragment implements ContactViewAdapter.OnContactViewAdapterInteraction {

    public static final int SELECT_PICTURE = 100;
    private static final String TAG = HomeFragment.class.getSimpleName();
    private static final int REQUEST_IMAGE_CAPTURE = 200;
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
    private RequestQueue mRequestQueue;
    private ProgressDialog mProgressDialog;

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

        updateProfileColor();

        mToolBarImageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dialogShown) {
                    new AlertDialog.Builder(inflater.getContext()).setMessage("Change Profile Picture?").setPositiveButton("Camera", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Assume thisActivity is the current activity
                            if (!((MainActivity) getActivity()).handleCameraPermission(mFragmentRootView)) {
                                dialogShown = false;
                                return;
                            }
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
                                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                            }
                            dialogShown = false;
                        }
                    }).setNeutralButton("Gallery", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                            photoPickerIntent.setType("image/*");
                            startActivityForResult(photoPickerIntent, SELECT_PICTURE);
                            dialogShown = false;
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialogShown = false;
                        }
                    }).show();
                    dialogShown = true;
                }
            }
        });

        mRequestQueue = Volley.newRequestQueue(getContext());

        mTVName = (TextView) toolbar.findViewById(R.id.name);
        mTVDescription = (TextView) toolbar.findViewById(R.id.description);
        mTVName.setText(mContact.getName());
        mTVDescription.setText(mContact.getDescription());

        mRecyclerView = (RecyclerView) mFragmentRootView.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));

        // TODO add interaction adapter
        contactInfoList = mContact.getContactInfoArrayList();
        mContactAdapter = new ContactViewAdapter(mRecyclerView, contactInfoList,
                this, getContext(), ContactViewAdapter.MODE_EDIT_CONTACT);
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
                        sendNewContactToCloud();
                    }
                });

        return mFragmentRootView;
    }

    private void updateProfileColor() {
        if (!ProfileManager.getInstance().isProfileUpdated()) {
            mToolBarImageProfile.setBorderColor(getResources().getColor(R.color.red));
        } else {
            mToolBarImageProfile.setBorderColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    private void sendNewContactToCloud() {
        mProgressDialog = new ProgressDialog(this.getActivity(), ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setTitle(getResources().getString(R.string.loading));
        mProgressDialog.setMessage(getResources().getString(R.string.please_wait));
        mProgressDialog.show();

        final Gson gson = new Gson();
        String json = gson.toJson(mContact);
        JSONObject object = null;
        try {
            object = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        /*Json Request*/
        String url = ServerConstants.SERVER_URL + "/contacts";
        JsonObjectRequest loginRequest = new JsonObjectRequest(Request.Method.POST, url, object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mContact = gson.fromJson(response.toString(), Contact.class);
                        ProfileManager.getInstance().saveContact(mContact);
                        ProfileManager.getInstance().setProfileOutDate(true);
                        updateProfileColor();
                        mProgressDialog.dismiss();
                        mProgressDialog = null;
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressDialog.dismiss();
                        mProgressDialog = null;
                        ProfileManager.getInstance().setProfileOutDate(false);
                        Toast.makeText(HomeFragment.this.getContext(), getResources().getString(R.string.error_update_profile_on_cloud), Toast.LENGTH_SHORT).show();
                        updateProfileColor();
                    }
                });

        loginRequest.setTag(TAG);
        mRequestQueue.add(loginRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO SEND THOSE IMAGES TO THE CLOUD, GET A LINK AND SAVE INTO THE PROFILE.
        if (requestCode == HomeFragment.SELECT_PICTURE && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            mContact.setPhotoUrl(selectedImage.getPath());
            ProfileManager.getInstance().saveContact(mContact);
        } else if (requestCode == HomeFragment.REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView() called with: " + "");
        mRequestQueue.cancelAll(TAG);

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        //You added a lot of mFragmentRootView into the toolbar to customize it to this fragment. So remove it.
        toolbar.removeView(mToolbarView);
        mContactAdapter.removeEmpty();
    }

    @Override
    public void onContactInfoDeleted(ContactInfo contactInfo) {
        ContactInfoController contactInfoController = new ContactInfoController(getContext());
        contactInfoController.delete(contactInfo);
    }
}

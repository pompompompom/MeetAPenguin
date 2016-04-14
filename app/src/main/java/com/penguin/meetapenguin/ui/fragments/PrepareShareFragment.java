package com.penguin.meetapenguin.ui.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.entities.ContactInfo;
import com.penguin.meetapenguin.ui.components.ContactViewAdapter;
import com.penguin.meetapenguin.ui.activities.MainActivity;
import com.penguin.meetapenguin.ui.activities.ShareActivity;
import com.penguin.meetapenguin.util.DataUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnShareFragmentInteraction}
 * interface.
 */
public class PrepareShareFragment extends Fragment {

    private static final String TAG = PrepareShareFragment.class.getSimpleName();
    private OnShareFragmentInteraction mListener;
    private ContactViewAdapter contactAdapter;
    private Contact mContact;
    private Toolbar toolbar;
    private View toolbarView;
    private View viewDescription;
    private View viewProfile;
    private CircularImageView imageProfile;
    private TextView name;
    private TextView description;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private Button shareBt;
    private RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PrepareShareFragment() {
    }

    @SuppressWarnings("unused")
    public static PrepareShareFragment newInstance(Contact contact) {
        PrepareShareFragment fragment = new PrepareShareFragment();
        Bundle args = new Bundle();
        args.putParcelable("Contact", contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mContact = savedInstanceState.getParcelable("Contact");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share_contact, container, false);
        mContact = DataUtil.getMockContact();
        setHasOptionsMenu(true);

        //You added a lot of view into the toolbar to customize it this fragment.
        toolbar = mListener.getToolBar();
        inflater.inflate(R.layout.share_fragment_toolbar, toolbar, true);
        toolbarView = toolbar.findViewById(R.id.share_fragment_toolbar);

        imageProfile = (CircularImageView) toolbar.findViewById(R.id.profile_picture);
        Picasso.with(getContext())
                .load(mContact.getPhotoUrl())
                .placeholder(R.drawable.placeholder)
                .into(imageProfile);

        name = (TextView) toolbar.findViewById(R.id.name);
        description = (TextView) toolbar.findViewById(R.id.description);
        name.setText(mContact.getName());
        description.setText(mContact.getDescription());

        drawer = ((MainActivity) getActivity()).getDrawereLayout();
        toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        shareBt = (Button) view.findViewById(R.id.share_button);
        shareBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchShareActivity(mContact);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        contactAdapter = new ContactViewAdapter(mContact.getContactInfoArrayList(), mListener, getContext());
        recyclerView.setAdapter(contactAdapter);

        return view;
    }

    private void launchShareActivity(Contact mContact) {
        Intent intent = new Intent(getActivity(), ShareActivity.class);
        intent.putExtra("Contact", mContact);
        startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnShareFragmentInteraction) {
            mListener = (OnShareFragmentInteraction) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnShareFragmentInteraction");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //You added a lot of view into the toolbar to customize it to this fragment. So remove it.
        toolbar.removeView(toolbarView);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnShareFragmentInteraction {
        void onContactInfoSelected(ContactInfo contactInfo);

        void onShare(Contact item, ArrayList<ContactInfo> selectedContactInfo);

        Toolbar getToolBar();
    }
}

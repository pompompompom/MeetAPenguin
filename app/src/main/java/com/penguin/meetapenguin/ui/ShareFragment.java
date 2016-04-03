package com.penguin.meetapenguin.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import com.penguin.meetapenguin.model.ContactInfo;
import com.penguin.meetapenguin.model.contactInfoImpl.FacebookInfo;
import com.penguin.meetapenguin.model.contactInfoImpl.LocationInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnShareFragmentInteraction}
 * interface.
 */
public class ShareFragment extends Fragment {

    private static final String TAG = ShareFragment.class.getSimpleName();
    private OnShareFragmentInteraction mListener;
    private ShareContactViewAdapter contactAdapter;
    private Contact mContact;
    private Toolbar toolbar;
    private View toolbarView;
    private View viewDescription;
    private View viewProfile;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShareFragment() {
    }

    @SuppressWarnings("unused")
    public static ShareFragment newInstance(Contact contact) {
        ShareFragment fragment = new ShareFragment();
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
        mContact = setupFakeData();
        setHasOptionsMenu(true);

        //You added a lot of view into the toolbar to customize it this fragment.
        toolbar = mListener.getToolBar();
        inflater.inflate(R.layout.share_fragment_toolbar, toolbar, true);
        toolbarView = toolbar.findViewById(R.id.share_fragment_toolbar);

        CircularImageView imageProfile = (CircularImageView) toolbar.findViewById(R.id.profile_picture);
        Picasso.with(getContext())
                .load(mContact.getPhotoUrl())
                .placeholder(R.drawable.placeholder)
                .into(imageProfile);

        TextView name = (TextView) toolbar.findViewById(R.id.name);
        TextView description = (TextView) toolbar.findViewById(R.id.description);
        name.setText(mContact.getName());
        description.setText(mContact.getDescription());

        DrawerLayout drawer = ((MainActivity) getActivity()).getDrawereLayout();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        contactAdapter = new ShareContactViewAdapter(mContact.getContactInfoArrayList(), mListener, getContext());
        recyclerView.setAdapter(contactAdapter);

        return view;
    }

    private Contact setupFakeData() {
        Contact contact = new Contact();
        contact.setName("Tom Brandy");
        contact.setDescription("Player");
        contact.setPhotoUrl("http://a.espncdn.com/combiner/i?img=/i/headshots/nfl/players/full/2330.png&w=350&h=254");
        ContactInfo contactInfo = new FacebookInfo();
        ContactInfo contactInfo2 = new LocationInfo();
        ArrayList<ContactInfo> contactInfoArrayList = new ArrayList<>();
        contactInfoArrayList.add(contactInfo);
        contactInfoArrayList.add(contactInfo2);
        contact.setContactInfoArrayList(contactInfoArrayList);
        return contact;
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
    public void onDetach() {
        super.onDetach();
        //You added a lot of view into the toolbar to customize it to this fragment. So remove it.
        toolbar.removeView(toolbarView);
        mListener = null;
    }

    public interface OnShareFragmentInteraction {
        void onContactInfoSelected(ContactInfo contactInfo);

        void onShare(Contact item, ArrayList<ContactInfo> selectedContactInfo);

        Toolbar getToolBar();
    }
}

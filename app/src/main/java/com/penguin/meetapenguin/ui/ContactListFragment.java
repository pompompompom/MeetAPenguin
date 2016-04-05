package com.penguin.meetapenguin.ui;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.model.Contact;
import com.penguin.meetapenguin.model.ContactInfo;
import com.penguin.meetapenguin.model.contactInfoImpl.FacebookInfo;
import com.penguin.meetapenguin.model.contactInfoImpl.LocationInfo;
import com.penguin.meetapenguin.util.DataUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ContactListFragment extends Fragment {

    private static final String TAG = ContactListFragment.class.getSimpleName();
    private OnListFragmentInteractionListener mListener;
    private ArrayList<Contact> original;
    private ContactListViewAdapter contactAdapter;
    private boolean searchController;
    private View searchTip;
    private TextView queryText;
    private Toolbar mToolbar;
    private View toolbarView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactListFragment() {
    }

    @SuppressWarnings("unused")
    public static ContactListFragment newInstance(int columnCount) {
        ContactListFragment fragment = new ContactListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        inflater.inflate(R.menu.contact_list_menu, menu);
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        SearchManager searchManager = (SearchManager) getContext().getSystemService(getActivity().SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG, "onMenuItemClick() called with: " + "item = [" + item + "]");
                performSearch("");
                searchController = true;
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit() called with: " + "query = [" + query + "]");
                performSearch(query);
                searchView.clearFocus();
                searchController = false;
                if (query.isEmpty()) {
                    searchTip.setVisibility(View.GONE);
                } else {
                    searchTip.setVisibility(View.VISIBLE);
                    queryText.setText(getResources().getString(R.string.query_filter) + " \"" + query + "\"");
                }
                MenuItemCompat.collapseActionView(item);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchController == false) return true;
                performSearch(newText);
                return true;
            }
        });
        return;
    }

    private void performSearch(String text) {
        Log.d(TAG, "performSearch() called with: " + "text = [" + text + "]");
        ArrayList<Contact> filterContact = new ArrayList<>();
        if (text.toString().isEmpty()) {
            filterContact.addAll(original);
        } else {
            for (Contact item : original) {
                if (item.getName().toLowerCase().contains(text.toString().toLowerCase()))
                    filterContact.add(item);
            }
        }
        contactAdapter.setDataSet(filterContact);
        contactAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        original = createFakeData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        setHasOptionsMenu(true);

        Contact contact = DataUtil.getMockContact();
        mToolbar = mListener.getToolBar();
        inflater.inflate(R.layout.share_fragment_toolbar, mToolbar, true);
        toolbarView = mToolbar.findViewById(R.id.share_fragment_toolbar);

        CircularImageView imageProfile = (CircularImageView) mToolbar.findViewById(R.id.profile_picture);
        Picasso.with(getContext())
                .load(contact.getPhotoUrl())
                .placeholder(R.drawable.placeholder)
                .into(imageProfile);

        TextView name = (TextView) mToolbar.findViewById(R.id.name);
        TextView description = (TextView) mToolbar.findViewById(R.id.description);
        name.setText(contact.getName());
        description.setText(contact.getDescription());

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
        final View closeSearchFilter = view.findViewById(R.id.close_search_filter);
        queryText = (TextView) view.findViewById(R.id.query_text);
        searchTip = view.findViewById(R.id.search_tip);
        closeSearchFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTip.setVisibility(View.GONE);
                performSearch("");
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        ArrayList<Contact> dataset = new ArrayList<>();
        dataset.addAll(original);
        contactAdapter = new ContactListViewAdapter(dataset, mListener, getContext());
        recyclerView.setAdapter(contactAdapter);

        return view;
    }

    private ArrayList<Contact> createFakeData() {
        ArrayList dummyData = new ArrayList<>();
        Contact contact1 = new Contact();
        contact1.setName("John John");
        contact1.setDescription("Student");
        contact1.setPhotoUrl("http://www.billybobproducts.com/sc_images/products/582_large_image.png");
        ContactInfo contactInfo = new FacebookInfo();
        ContactInfo contactInfo2 = new LocationInfo();
        ArrayList<ContactInfo> contactInfoArrayList = new ArrayList<>();
        contactInfoArrayList.add(contactInfo);
        contactInfoArrayList.add(contactInfo2);
        contact1.setContactInfoArrayList(contactInfoArrayList);
        dummyData.add(contact1);

        Contact contact2 = new Contact();
        contact2.setName("Stuart Little");
        contact2.setDescription("Rat");
        contact2.setPhotoUrl("http://www.thehindu.com/thehindu/yw/2002/10/26/images/2002102600050201.jpg");
        contact2.setContactInfoArrayList(contactInfoArrayList);
        dummyData.add(contact2);

        Contact contact3 = new Contact();
        contact3.setName("Tony Stark");
        contact3.setDescription("Super Hero");
        contact3.setPhotoUrl("http://vignette4.wikia.nocookie.net/marvelmovies/images/9/9a/Iron-man-site-tony-stark.jpg/revision/latest?cb=20120416023223");
        contact3.setContactInfoArrayList(contactInfoArrayList);
        dummyData.add(contact3);

        Contact contact4 = new Contact();
        contact4.setName("Zumbi");
        contact4.setDescription("Walking Dead");
        contact4.setPhotoUrl("http://geraligado.blog.br/wp-content/uploads/2013/01/As-melhores-maquiagens-de-zumbi-11.jpeg");
        contact4.setContactInfoArrayList(contactInfoArrayList);
        dummyData.add(contact4);

        Contact contact5 = new Contact();
        contact5.setName("Bill Gates");
        contact5.setDescription("Billionare");
        contact5.setPhotoUrl("https://static-secure.guim.co.uk/sys-images/Medaia/Pix/pictures/2011/3/1/1298983226609/Bill-Gates-007.jpg");
        contact5.setContactInfoArrayList(contactInfoArrayList);
        dummyData.add(contact5);

        Contact contact6 = new Contact();
        contact6.setName("Alcapone");
        contact6.setDescription("Mafia");
        contact6.setPhotoUrl("http://www.babyfacenelsonjournal.com/uploads/3/8/2/4/3824310/5721541_orig.jpg");
        contact6.setContactInfoArrayList(contactInfoArrayList);
        dummyData.add(contact6);

        Contact contact7 = new Contact();
        contact7.setName("Allan Grespan");
        contact7.setDescription("Economist");
        contact7.setPhotoUrl("http://www.washingtonspeakers.com/images/photos/sp1/2300.jpg");
        contact7.setContactInfoArrayList(contactInfoArrayList);
        dummyData.add(contact7);


        Contact contact8 = new Contact();
        contact8.setName("Wozniak");
        contact8.setDescription("Engineer");
        contact8.setPhotoUrl("http://www.landsnail.com/apple/local/profile/New_Folder/graphics/wozniak.gif");
        contact8.setContactInfoArrayList(contactInfoArrayList);
        dummyData.add(contact8);


        Contact contact9 = new Contact();
        contact9.setName("Tony Blair");
        contact9.setDescription("Prime Minister");
        contact9.setPhotoUrl("https://pbs.twimg.com/profile_images/1675579492/Tony_Blair_pic.JPG");
        contact9.setContactInfoArrayList(contactInfoArrayList);
        dummyData.add(contact9);


        Contact contact10 = new Contact();
        contact10.setName("Jude Law");
        contact10.setDescription("Actor");
        contact10.setPhotoUrl("http://a1.files.biography.com/image/upload/c_fill,cs_srgb,dpr_1.0,g_face,h_300,q_80,w_300/MTE1ODA0OTcxNjkzODY4NTU3.jpg");
        contact10.setContactInfoArrayList(contactInfoArrayList);
        dummyData.add(contact10);

        return dummyData;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnShareFragmentInteraction");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //You added a lot of view into the toolbar to customize it to this fragment. So remove it.
        mToolbar.removeView(toolbarView);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Contact item);

        Toolbar getToolBar();
    }
}

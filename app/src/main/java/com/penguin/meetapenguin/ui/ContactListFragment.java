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
    private CircularImageView imageProfile;
    private TextView name;
    private TextView description;

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
        original = DataUtil.createFakeData();
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

        imageProfile = (CircularImageView) mToolbar.findViewById(R.id.profile_picture);
        Picasso.with(getContext())
                .load(contact.getPhotoUrl())
                .placeholder(R.drawable.placeholder)
                .into(imageProfile);

        name = (TextView) mToolbar.findViewById(R.id.name);
        description = (TextView) mToolbar.findViewById(R.id.description);
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

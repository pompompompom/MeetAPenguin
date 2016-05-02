package com.penguin.meetapenguin.ui.fragments;

import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.dblayout.ContactController;
import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.ui.activities.MainActivity;
import com.penguin.meetapenguin.ui.components.ContactListViewAdapter;
import com.penguin.meetapenguin.util.ContactSyncronizationHelper;
import com.penguin.meetapenguin.util.ProfileManager;
import com.penguin.meetapenguin.util.ServerConstants;
import com.penguin.meetapenguin.util.testHelper.DataUtil;
import com.penguin.meetapenguin.ws.remote.RetrieveEntityRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ContactListFragment extends Fragment {

    private static final String TAG = ContactListFragment.class.getSimpleName();
    private static final String URL = ServerConstants.SERVER_URL + "/contacts";
    private OnListFragmentInteractionListener mListener;
    private ArrayList<Contact> mOriginal;
    private ContactListViewAdapter mContactAdapter;
    private boolean mSearchController;
    private View searchTip;
    private TextView mQueryText;
    private Toolbar mToolbar;
    private View mView;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeContainer;
    private RequestQueue mRequestQueue;
    private Response.ErrorListener mErrorWhenContactUpdate;
    private Response.Listener mOnReceiveContactUpdate;
    private SearchView mSearchView;
    private MenuItem mMenuItem;
    private View mCloseSearchFilter;


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
        mMenuItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) MenuItemCompat.getActionView(mMenuItem);
        SearchManager searchManager = (SearchManager) getContext().getSystemService(getActivity().SEARCH_SERVICE);
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        mMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG, "onMenuItemClick() called with: " + "item = [" + item + "]");
                performSearch("");
                mSearchController = true;
                return false;
            }
        });

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit() called with: " + "query = [" + query + "]");
                performSearch(query);
                performSearch(query);
                mSearchView.clearFocus();
                mSearchController = false;
                if (query.isEmpty()) {
                    searchTip.setVisibility(View.GONE);
                } else {
                    searchTip.setVisibility(View.VISIBLE);
                    mQueryText.setText(getResources().getString(R.string.query_filter) + " \"" + query + "\"");
                }
                MenuItemCompat.collapseActionView(mMenuItem);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!mSearchController) return true;
                performSearch(newText);
                return true;
            }
        });
    }

    private void performSearch(String text) {
        Log.d(TAG, "performSearch() called with: " + "text = [" + text + "]");
        ArrayList<Contact> filterContact = new ArrayList<>();
        if (text.toString().isEmpty()) {
            filterContact.addAll(mOriginal);
        } else {
            for (Contact item : mOriginal) {
                if (item.getName().toLowerCase().contains(text.toString().toLowerCase()))
                    filterContact.add(item);
            }
        }
        mContactAdapter.setDataSet(filterContact);
        mContactAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContactController contactController = new ContactController(getContext());
        mOriginal = (ArrayList<Contact>) contactController.readAll();
        mOriginal.remove(ProfileManager.getInstance().getContact());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_contact_list, container, false);
        setHasOptionsMenu(true);

        Contact contact = DataUtil.getMockContact();
        mRequestQueue = Volley.newRequestQueue(getContext());

        mToolbar = ((MainActivity) getActivity()).getToolBar();
        mToolbar.setTitle("Contact List");

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.list);
        mCloseSearchFilter = mView.findViewById(R.id.close_search_filter);
        mQueryText = (TextView) mView.findViewById(R.id.query_text);
        searchTip = mView.findViewById(R.id.search_tip);
        mSwipeContainer = (SwipeRefreshLayout) mView.findViewById(R.id.swipeContainer_contact_list);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateContactInfo();
            }
        });
        mCloseSearchFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchTip.setVisibility(View.GONE);
                performSearch("");
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mContactAdapter = new ContactListViewAdapter(mOriginal, mListener, getContext());
        mRecyclerView.setAdapter(mContactAdapter);

        return mView;
    }

    private void updateContactInfo() {
        final long currentTimeStamp = 0;

        mOnReceiveContactUpdate = new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                ContactSyncronizationHelper.setLastUpdateTime(currentTimeStamp);
                Log.d(TAG, "onResponse() called with: " + "response = [" + response + "]");
                mSwipeContainer.setRefreshing(false);

                ArrayList<Contact> contactList = (ArrayList<Contact>) response;
                ContactController contactController = new ContactController(getContext());
                for (Contact contact : contactList) {
                    contactController.update(contact);
                    if (mOriginal.contains(contact)) {
                        mOriginal.remove(contact);
                        mOriginal.add(contact);
                    }
                }

                mContactAdapter.notifyDataSetChanged();
            }
        };

        mErrorWhenContactUpdate = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mSwipeContainer.setRefreshing(false);
            }
        };

        Map<String, String> header = new HashMap<>();
        header.put("UserId", String.valueOf(ProfileManager.getInstance().getUserId()));
        header.put("timestamp", String.valueOf(ContactSyncronizationHelper.getLastUpdatedTime()));
        RetrieveEntityRequest request = new RetrieveEntityRequest(URL, Contact.class, header, mOnReceiveContactUpdate, mErrorWhenContactUpdate);
        mRequestQueue.add(request);
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

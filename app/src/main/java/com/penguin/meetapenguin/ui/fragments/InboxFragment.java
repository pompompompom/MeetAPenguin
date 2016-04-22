package com.penguin.meetapenguin.ui.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.entities.InboxMessage;
import com.penguin.meetapenguin.ui.components.InboxFragmentAdapter;
import com.penguin.meetapenguin.ws.remote.RetrieveEntityRequest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Fragment to display main screen.
 */
public class InboxFragment extends Fragment {

    private static final String TAG = InboxFragment.class.getSimpleName();
    private static final String URL = "http://10.0.3.2:8080/rest/messages";
    private ArrayList<InboxMessage> mMessages;
    private InboxFragmentAdapter mInboxAdapter;
    private OnListInboxFragmentInteractionListener mListener;
    private RecyclerView mRecyclerView;
    private View mView;
    private SwipeRefreshLayout mSwipeContainer;
    private RequestQueue mRequestQueue;
    private Response.Listener mOnReceiveNewMessage;
    private Response.ErrorListener mErrorWhenReceivingNewmessages;
    private TextView mAllMessageReader;

    /**
     * default no-args constructor.
     */
    public InboxFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRequestQueue = Volley.newRequestQueue(getContext());
        mMessages = createFakeData();
        mListener = new OnListInboxFragmentInteractionListener() {
            @Override
            public void onListInboxFragmentInteraction(InboxMessage message) {
                new AlertDialog.Builder(getContext()).setMessage
                        ("TODO: Do you allow this contact to receive your up to date " +
                                "information?")
                        .setPositiveButton("Renew", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).setNegativeButton("Cancel", null).show();
            }

            @Override
            public void onMessageDeleted() {
                for (InboxMessage message : mMessages) {
                    if (!message.isDeleted()) return;
                }
                mAllMessageReader.setVisibility(View.VISIBLE);
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_inbox, container, false);

        mAllMessageReader = (TextView) mView.findViewById(R.id.no_more_message);
        mSwipeContainer = (SwipeRefreshLayout) mView.findViewById(R.id.swipeContainer);
        mSwipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestNewMessages();
            }
        });

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.list);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mInboxAdapter = new InboxFragmentAdapter(mMessages, mListener, getContext());
        mRecyclerView.setAdapter(mInboxAdapter);

        return mView;
    }

    private void requestNewMessages() {

        mOnReceiveNewMessage = new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                List<InboxMessage> inboxMessageList = (List<InboxMessage>) response;
                boolean messageAdded = false;
                for (InboxMessage message : inboxMessageList) {
                    if (!mMessages.contains(message)) {
                        mMessages.add(message);
                        messageAdded = true;
                    }
                }
                if (messageAdded == true) {
                    mAllMessageReader.setVisibility(View.GONE);
                }
                mSwipeContainer.setRefreshing(false);
                mInboxAdapter.notifyDataSetChanged();
            }
        };

        mErrorWhenReceivingNewmessages = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mSwipeContainer.setRefreshing(false);
            }
        };

        RetrieveEntityRequest request = new RetrieveEntityRequest(URL, InboxMessage.class, null, mOnReceiveNewMessage, mErrorWhenReceivingNewmessages);
        mRequestQueue.add(request);
    }

    public ArrayList<InboxMessage> createFakeData() {

        ArrayList<InboxMessage> temp = new ArrayList<>();

        //Adding first face message
        InboxMessage tempMessage = new InboxMessage();
        Contact contact1 = new Contact();
        contact1.setName("John John");
        contact1.setDescription("Student");
        contact1.setPhotoUrl("http://www.billybobproducts.com/sc_images/products/582_large_image.png");
        tempMessage.setContact(contact1);
        tempMessage.setMessage("Email for this contact has expired.");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date fakeDate = cal.getTime();
        tempMessage.setTimeStamp(fakeDate.getTime());
        temp.add(tempMessage);

        //Adding second face message
        InboxMessage tempMessage2 = new InboxMessage();
        Contact contact8 = new Contact();
        contact8.setName("Wozniak");
        contact8.setDescription("Engineer");
        contact8.setPhotoUrl("http://www.landsnail.com/apple/local/profile/New_Folder/graphics/wozniak.gif");
        tempMessage2.setContact(contact8);
        tempMessage2.setMessage("Contact is requesting Email update.");

        cal.add(Calendar.MONTH, -3);
        Date fakeDate2 = cal.getTime();

        tempMessage2.setTimeStamp(fakeDate2.getTime());
        temp.add(tempMessage2);

        return temp;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListInboxFragmentInteractionListener {
        void onListInboxFragmentInteraction(InboxMessage message);

        void onMessageDeleted();
    }
}

package com.penguin.meetapenguin.ui.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.dblayout.ContactController;
import com.penguin.meetapenguin.dblayout.InboxMessageController;
import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.entities.InboxMessage;
import com.penguin.meetapenguin.ui.components.InboxFragmentAdapter;
import com.penguin.meetapenguin.ws.remote.AnswerInboxMessageRequest;
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
    private static final String CREATE_FAKE_DATA = "fakedata";
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
        createFakeData();
        mMessages = loadMessagesFromDB();
        mListener = new OnListInboxFragmentInteractionListener() {
            @Override
            public void onListInboxFragmentInteraction(final InboxMessage message) {
                new AlertDialog.Builder(getContext()).setMessage
                        ("Do you allow this contact to receive your up to date " +
                                "information?")
                        .setPositiveButton("Renew", new
                                DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        AnswerInboxMessageRequest answerInboxMessageRequest = new AnswerInboxMessageRequest(message, true, new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                message.setDeleted(true);
                                                onMessageDeleted();
                                                mInboxAdapter.notifyDataSetChanged();
                                                Toast.makeText(getContext(), getResources().getString(R.string.success_accepting_renew), Toast.LENGTH_LONG).show();
                                            }
                                        }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                Toast.makeText(getContext(), getResources().getString(R.string.error_accepting_renew), Toast.LENGTH_LONG).show();
                                            }
                                        });
                                        mRequestQueue.add(answerInboxMessageRequest);
                                    }
                                }).setNegativeButton("Cancel", null).show();
            }

            @Override
            public void onMessageDeleted() {
                saveMessages();
                for (InboxMessage message : mMessages) {
                    if (!message.isDeleted()) return;
                }
                mAllMessageReader.setVisibility(View.VISIBLE);
            }
        };
    }

    private ArrayList<InboxMessage> loadMessagesFromDB() {
        InboxMessageController inboxMessageController = new InboxMessageController(getContext());
        return inboxMessageController.readAll();
    }

    private void saveMessages() {
        InboxMessageController inboxMessageController = new InboxMessageController(getContext());
        for (InboxMessage inboxMessage : mMessages) {
            if (inboxMessage.getId() == null) {
                int id = (int) inboxMessageController.create(inboxMessage);
                if (id > 0) {
                    inboxMessage.setId(id);
                } else {
                    Log.e(TAG, "saveMessages: Error while saving a inboxMessage into the database");
                }
            } else {
                inboxMessageController.update(inboxMessage);
            }
        }
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
                saveMessages();
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

    public void createFakeData() {

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean alreadyCreated = sharedPref.getBoolean(CREATE_FAKE_DATA, false);
        if (alreadyCreated)
            return;

        //Adding first face message
        InboxMessage inboxMessage1 = new InboxMessage();
        Contact contact1 = new Contact();
        contact1.setName("John John");
        contact1.setDescription("Student");
        contact1.setId(1);
        contact1.setExpiration(new Date().getTime());
        contact1.setPhotoUrl("http://www.billybobproducts.com/sc_images/products/582_large_image.png");

        ContactController contactController = new ContactController(getContext());
        contactController.create(contact1);

        inboxMessage1.setId(1);
        inboxMessage1.setCloudId(1);
        inboxMessage1.setContact(contact1);
        inboxMessage1.setMessage("Email for this contact has expired.");

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date fakeDate = cal.getTime();
        inboxMessage1.setTimeStamp(fakeDate.getTime());

        InboxMessageController inboxMessageController = new InboxMessageController(getContext());
        inboxMessageController.create(inboxMessage1);

        //Adding second face message
        InboxMessage inboxMessage2 = new InboxMessage();
        Contact contact8 = new Contact();
        contact8.setName("Wozniak");
        contact8.setDescription("Engineer");
        contact8.setId(2);
        contact8.setExpiration(new Date().getTime());
        contact8.setPhotoUrl("http://www.landsnail.com/apple/local/profile/New_Folder/graphics/wozniak.gif");
        contactController.create(contact8);
        inboxMessage2.setContact(contact8);
        inboxMessage2.setId(2);
        inboxMessage1.setCloudId(2);
        inboxMessage2.setMessage("Contact is requesting Email update.");

        cal.add(Calendar.MONTH, -3);
        Date fakeDate2 = cal.getTime();

        inboxMessage2.setTimeStamp(fakeDate2.getTime());
        inboxMessageController.create(inboxMessage2);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(CREATE_FAKE_DATA, true);
        editor.commit();
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

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
import com.penguin.meetapenguin.dblayout.InboxMessageController;
import com.penguin.meetapenguin.entities.InboxMessage;
import com.penguin.meetapenguin.ui.components.InboxFragmentAdapter;
import com.penguin.meetapenguin.util.ProfileManager;
import com.penguin.meetapenguin.util.ServerConstants;
import com.penguin.meetapenguin.util.testHelper.DataUtil;
import com.penguin.meetapenguin.ws.remote.AnswerInboxMessageRequest;
import com.penguin.meetapenguin.ws.remote.RetrieveEntityRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Fragment to display the Inbox Message page. It contains renew request from other users.
 */
public class InboxFragment extends Fragment {

    private static final boolean DEBUG = false;
    private static final String TAG = InboxFragment.class.getSimpleName();
    private static final String URL = ServerConstants.SERVER_URL + "/messages";
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
        if (DEBUG) {
            DataUtil.createFakeDataForInboxMessage();
        }
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
        inboxMessageController.deleteAll();
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

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.list_message);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mInboxAdapter = new InboxFragmentAdapter(mMessages, mListener, getContext());
        mRecyclerView.setAdapter(mInboxAdapter);

        if (mMessages.size() == 0) {
            mAllMessageReader.setVisibility(View.VISIBLE);
        }

        return mView;
    }

    private void requestNewMessages() {

        mOnReceiveNewMessage = new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                List<InboxMessage> inboxMessageList = (List<InboxMessage>) response;
                mMessages = (ArrayList<InboxMessage>) inboxMessageList;

                if (!mMessages.isEmpty()) {
                    mAllMessageReader.setVisibility(View.GONE);
                }
                mSwipeContainer.setRefreshing(false);
                mInboxAdapter.setDataSet(mMessages);
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

        Map<String, String> headers = new HashMap<>();
        headers.put("userID", String.valueOf(ProfileManager.getInstance().getUserId()));
        RetrieveEntityRequest request = new RetrieveEntityRequest(URL, InboxMessage.class, headers, mOnReceiveNewMessage, mErrorWhenReceivingNewmessages);
        mRequestQueue.add(request);
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

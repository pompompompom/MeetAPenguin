package com.penguin.meetapenguin.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.model.Contact;
import com.penguin.meetapenguin.model.InboxMessage;

import java.util.ArrayList;

/**
 * Fragment to display main screen.
 */
public class InboxFragment extends Fragment {

    private ArrayList<InboxMessage> messages;
    private InboxFragmentAdapter inboxAdapter;
    private OnListInboxFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private View view;

    /**
     * default no-args constructor.
     */
    public InboxFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messages = createFakeData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_inbox, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        ArrayList<InboxMessage> dataset = new ArrayList<InboxMessage>();
        dataset.addAll(messages);
        inboxAdapter = new InboxFragmentAdapter(dataset, mListener, getContext
                ());
        recyclerView.setAdapter(inboxAdapter);

        return view;
    }

    public ArrayList<InboxMessage> createFakeData(){

        ArrayList<InboxMessage> temp = new ArrayList<>();

        InboxMessage tempMessage = new InboxMessage();

        Contact contact1 = new Contact();
        contact1.setName("John John");
        contact1.setDescription("Student");
        contact1.setPhotoUrl("http://www.billybobproducts.com/sc_images/products/582_large_image.png");

        tempMessage.setContact(contact1);
        tempMessage.setMessage("Email for this contact has expired.");
        tempMessage.setTimeStamp("4/1/2016");

        temp.add(tempMessage);

        Contact contact8 = new Contact();
        contact8.setName("Wozniak");
        contact8.setDescription("Engineer");
        contact8.setPhotoUrl("http://www.landsnail.com/apple/local/profile/New_Folder/graphics/wozniak.gif");
        tempMessage.setMessage("Contact is requesting Email update.");
        tempMessage.setTimeStamp("4/1/2016");

        return temp;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListInboxFragmentInteractionListener) {
            mListener = (OnListInboxFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnShareFragmentInteraction");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListInboxFragmentInteractionListener {
        void onListInboxFragmentInteraction(InboxMessage message);
    }

}

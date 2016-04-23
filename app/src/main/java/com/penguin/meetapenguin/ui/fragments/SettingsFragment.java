package com.penguin.meetapenguin.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.ui.components.ContactViewAdapter;
import com.penguin.meetapenguin.util.DataUtil;

/**
 * Fragment to display main screen.
 */
public class SettingsFragment extends Fragment {

    private Contact contact;
    private View toolbarView;
    private RecyclerView recyclerView;
    private ContactViewAdapter contactAdapter;
    private Button save;
    private View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_settings, container, false);

        contact = DataUtil.getMockContact();

        recyclerView = (RecyclerView) v.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        // TODO add interaction adapter
        contactAdapter = new ContactViewAdapter(contact.getContactInfoArrayList(),
                null, getContext());
        recyclerView.setAdapter(contactAdapter);

        save = (Button) v.findViewById(R.id.save_button);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Add settings functionality.
            }
        });

        return v;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}
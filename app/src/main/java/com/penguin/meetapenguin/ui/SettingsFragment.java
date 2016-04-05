package com.penguin.meetapenguin.ui;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.penguin.meetapenguin.model.Contact;
import com.penguin.meetapenguin.util.DataUtil;
import com.squareup.picasso.Picasso;

/**
 * Fragment to display main screen.
 */
public class SettingsFragment extends Fragment {

    private Contact contact;
    private View toolbarView;
    private RecyclerView recyclerView;
    private ContactViewAdapter contactAdapter;
    private Button save;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

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

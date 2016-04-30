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
import android.widget.TextView;
import android.widget.Toast;

import com.github.channguyen.rsv.RangeSliderView;
import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.entities.Contact;
import com.penguin.meetapenguin.entities.ContactInfo;
import com.penguin.meetapenguin.ui.components.SelectContactInfoAdapter;
import com.penguin.meetapenguin.util.ExpirationOptions;
import com.penguin.meetapenguin.util.ProfileManager;

import java.util.Set;

/**
 * Fragment to display main screen.
 */
public class SettingsFragment extends Fragment {

    private Contact contact;
    private View toolbarView;
    private RecyclerView recyclerView;
    private SelectContactInfoAdapter contactAdapter;
    private Button save;
    private View v;
    private RangeSliderView expirationSlider;
    private TextView expirationTv;
    private Set<ContactInfo> mSelectedContactInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_settings, container, false);
        contact = ProfileManager.getInstance().getContact();
        mSelectedContactInfo = ProfileManager.getInstance().getDefaultSharingPreferences();

        recyclerView = (RecyclerView) v.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        contactAdapter = new SelectContactInfoAdapter(recyclerView, contact.getContactInfoArrayList(), mSelectedContactInfo,
                new SelectContactInfoAdapter.OnContactViewAdapterInteraction() {
                    @Override
                    public void onContacInfoSelected(ContactInfo contactInfo, boolean isChecked) {
                        if (isChecked)
                            mSelectedContactInfo.add(contactInfo);
                        else
                            mSelectedContactInfo.remove(contactInfo);
                    }
                }, getContext());
        recyclerView.setAdapter(contactAdapter);

        save = (Button) v.findViewById(R.id.save_settings_button);
        expirationTv = (TextView) v.findViewById(R.id.expiration_date_tv);
        expirationSlider = (RangeSliderView) v.findViewById(R.id.rangeslide_expiration);
        int slidexIndex = ProfileManager.getInstance().getDefaultExpiration().getIndexValue();
        expirationSlider.setInitialIndex(slidexIndex);
        adjustSlider(slidexIndex);
        expirationSlider.setOnSlideListener(new RangeSliderView.OnSlideListener() {
            @Override
            public void onSlide(int index) {
                adjustSlider(index);
                ProfileManager.getInstance().setDefaultExpiration(ExpirationOptions.values()[index]);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileManager.getInstance().saveDefaultSharingPreferences(mSelectedContactInfo);
                Toast.makeText(SettingsFragment.this.getContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
    }

    private void adjustSlider(int index) {
        switch (index) {
            case 0:
                expirationTv.setText(getString(R.string.one_month));
                break;
            case 1:
                expirationTv.setText(getString(R.string.three_month));
                break;
            case 2:
                expirationTv.setText(getString(R.string.six_month));
                break;
            case 3:
                expirationTv.setText(getString(R.string.one_year));
                break;
            case 4:
                expirationTv.setText(getString(R.string.two_year));
                break;
            default:
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

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
 * Fragment to display the settings/configuration for the user profile.
 */
public class SettingsFragment extends Fragment {

    private Contact mContact;
    private RecyclerView mRecyclerView;
    private SelectContactInfoAdapter mContactAdapter;
    private Button mSaveButton;
    private View mView;
    private RangeSliderView mExpirationSlider;
    private TextView mExpirationTv;
    private Set<ContactInfo> mSelectedContactInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_settings, container, false);
        mContact = ProfileManager.getInstance().getContact();
        mSelectedContactInfo = ProfileManager.getInstance().getDefaultSharingPreferences();

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mRecyclerView.getContext()));
        mContactAdapter = new SelectContactInfoAdapter(mRecyclerView, mContact.getContactInfoArrayList(), mSelectedContactInfo,
                new SelectContactInfoAdapter.OnContactViewAdapterInteraction() {
                    @Override
                    public void onContacInfoSelected(ContactInfo contactInfo, boolean isChecked) {
                        if (isChecked)
                            mSelectedContactInfo.add(contactInfo);
                        else
                            mSelectedContactInfo.remove(contactInfo);
                    }
                }, getContext());
        mRecyclerView.setAdapter(mContactAdapter);

        mSaveButton = (Button) mView.findViewById(R.id.save_settings_button);
        mExpirationTv = (TextView) mView.findViewById(R.id.expiration_date_tv);
        mExpirationSlider = (RangeSliderView) mView.findViewById(R.id.rangeslide_expiration);
        int slidexIndex = ProfileManager.getInstance().getDefaultExpiration().getIndexValue();
        mExpirationSlider.setInitialIndex(slidexIndex);
        adjustSlider(slidexIndex);
        mExpirationSlider.setOnSlideListener(new RangeSliderView.OnSlideListener() {
            @Override
            public void onSlide(int index) {
                adjustSlider(index);
                ProfileManager.getInstance().setDefaultExpiration(ExpirationOptions.values()[index]);
            }
        });

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileManager.getInstance().saveDefaultSharingPreferences(mSelectedContactInfo);
                Toast.makeText(SettingsFragment.this.getContext(), "Saved", Toast.LENGTH_SHORT).show();
            }
        });

        return mView;
    }

    private void adjustSlider(int index) {
        switch (index) {
            case 0:
                mExpirationTv.setText(getString(R.string.one_month));
                break;
            case 1:
                mExpirationTv.setText(getString(R.string.three_month));
                break;
            case 2:
                mExpirationTv.setText(getString(R.string.six_month));
                break;
            case 3:
                mExpirationTv.setText(getString(R.string.one_year));
                break;
            case 4:
                mExpirationTv.setText(getString(R.string.two_year));
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

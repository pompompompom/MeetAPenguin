package com.penguin.meetapenguin.ui.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.ui.components.ImageGridAdapter;

/**
 * Fragment for choosing profile picture
 */
public class ChooseProfilePicFragment extends Fragment {

    private final Listener listener;

    public interface Listener {
        public void onProfilePicSelected(int resID);
    }

    public ChooseProfilePicFragment(Listener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_choose_profile_pic, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.grid);
        gridView.setAdapter(new ImageGridAdapter(inflater.getContext()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if (listener != null) {
                    if (v.getTag() != null) {
                        listener.onProfilePicSelected((Integer) v.getTag());
                    }
                }
            }
        });
        return view;
    }
}

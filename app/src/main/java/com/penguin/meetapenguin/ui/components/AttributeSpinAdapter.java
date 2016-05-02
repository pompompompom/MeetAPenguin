package com.penguin.meetapenguin.ui.components;


import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.entities.Attribute;

import java.util.List;

/**
 * THis is a attribute spin adapter to present the options of attribute that a user can choose.
 * Which contact info are available for him to choose.
 */
public class AttributeSpinAdapter extends ArrayAdapter<Attribute> {

    List<Attribute> values = null;
    LayoutInflater inflater;
    private Context mContext;


    public AttributeSpinAdapter(Context context, List<Attribute> objects) {
        super(context, R.layout.attribute_spinner, objects);

        mContext = context;
        values = objects;

        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateDataSet(List<Attribute> attributeList) {
        if (attributeList.equals(values)) {
            return;
        }
        values.clear();
        values.addAll(attributeList);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return values.size();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.attribute_spinner, parent, false);
        int resId = mContext.getResources().getIdentifier(values.get(position).getIconPath(), null, mContext.getPackageName());
        ImageView AttributeLogo = (ImageView) row.findViewById(R.id.attribute_option);
        AttributeLogo.setColorFilter(mContext.getResources().getColor(R.color.greysigh), PorterDuff.Mode.SRC_IN);
        AttributeLogo.setImageDrawable(mContext.getDrawable(resId));
        return row;
    }
}
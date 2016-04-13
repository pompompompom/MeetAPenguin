package com.penguin.meetapenguin.entities.contactInfoImpl;

import android.os.Parcel;
import android.os.Parcelable;

import com.penguin.meetapenguin.entities.Attribute;
import com.penguin.meetapenguin.entities.ContactInfo;

/**
 * Location contact info.
 */
public class LocationInfo extends ContactInfo implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<LocationInfo> CREATOR = new Parcelable.Creator<LocationInfo>() {
        @Override
        public LocationInfo createFromParcel(Parcel in) {
            return new LocationInfo(in);
        }

        @Override
        public LocationInfo[] newArray(int size) {
            return new LocationInfo[size];
        }
    };

    public LocationInfo() {

    }

    protected LocationInfo(Parcel in) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    @Override
    public Attribute getAttribute() {
        return new Attribute("Location", "drawable/ic_place_black_24dp");
    }

    @Override
    public String getExtraDescription() {
        return null;
    }

    @Override
    public String getAtrributeValue() {
        return "Cupertino - CA";
    }
}

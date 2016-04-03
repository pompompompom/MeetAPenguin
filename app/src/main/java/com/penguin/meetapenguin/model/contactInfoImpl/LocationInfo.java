package com.penguin.meetapenguin.model.contactInfoImpl;

import android.os.Parcel;
import android.os.Parcelable;

import com.penguin.meetapenguin.model.ContactInfo;

/**
 * Created by urbano on 4/2/16.
 */
public class LocationInfo implements ContactInfo, Parcelable {

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
    public String getAttribute() {
        return "Location";
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

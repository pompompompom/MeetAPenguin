package com.penguin.meetapenguin.model.contactInfoImpl;

import android.os.Parcel;
import android.os.Parcelable;

import com.penguin.meetapenguin.model.ContactInfo;

/**
 * Created by urbano on 4/2/16.
 */
public class FacebookInfo implements ContactInfo, Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FacebookInfo> CREATOR = new Parcelable.Creator<FacebookInfo>() {
        @Override
        public FacebookInfo createFromParcel(Parcel in) {
            return new FacebookInfo(in);
        }

        @Override
        public FacebookInfo[] newArray(int size) {
            return new FacebookInfo[size];
        }
    };

    protected FacebookInfo(Parcel in) {

    }

    public FacebookInfo() {
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
        return "Facebook";
    }

    @Override
    public String getExtraDescription() {
        return null;
    }

    @Override
    public String getAtrributeValue() {
        return "www.facebook.com/teste";
    }
}

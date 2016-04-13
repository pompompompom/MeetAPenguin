package com.penguin.meetapenguin.entities.contactInfoImpl;

import android.os.Parcel;
import android.os.Parcelable;

import com.penguin.meetapenguin.R;
import com.penguin.meetapenguin.entities.Attribute;
import com.penguin.meetapenguin.entities.ContactInfo;

/**
 * Facebook contact info
 */
public class FacebookInfo extends ContactInfo implements Parcelable {

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
    public Attribute getAttribute() {
        return new Attribute("Facebook", "drawable/facebook");
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

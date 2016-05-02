package com.penguin.meetapenguin.ui.activities;

import android.os.Parcel;
import android.os.Parcelable;

import com.penguin.meetapenguin.entities.Contact;

/**
 * This wrapper for the ShareActivity intent, specifying which object it expects.
 */
public class ShareActivityIntent implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<ShareActivityIntent> CREATOR = new Parcelable.Creator<ShareActivityIntent>() {
        @Override
        public ShareActivityIntent createFromParcel(Parcel in) {
            return new ShareActivityIntent(in);
        }

        @Override
        public ShareActivityIntent[] newArray(int size) {
            return new ShareActivityIntent[size];
        }
    };
    public static String ShareActivityIntentBundle = ShareActivityIntent.class.getSimpleName();
    private Contact contact;

    public ShareActivityIntent(Contact contact) {
        this.contact = contact;
    }

    protected ShareActivityIntent(Parcel in) {
        contact = (Contact) in.readValue(Contact.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(contact);
    }

    public Contact getContact() {
        return contact;
    }
}

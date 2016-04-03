package com.penguin.meetapenguin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by urbano on 4/2/16.
 */
public class Contact implements Parcelable {
    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
    private String name;
    private ArrayList<ContactInfo> contactInfoArrayList;
    private String description;
    private Date expiration;
    private String photoUrl;
    public Contact() {
    }

    protected Contact(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0x01) {
            contactInfoArrayList = new ArrayList<ContactInfo>();
            in.readList(contactInfoArrayList, ContactInfo.class.getClassLoader());
        } else {
            contactInfoArrayList = null;
        }
        description = in.readString();
        long tmpExpiration = in.readLong();
        expiration = tmpExpiration != -1 ? new Date(tmpExpiration) : null;
        photoUrl = in.readString();
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ContactInfo> getContactInfoArrayList() {
        return contactInfoArrayList;
    }

    public void setContactInfoArrayList(ArrayList<ContactInfo> contactInfoArrayList) {
        this.contactInfoArrayList = contactInfoArrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        if (contactInfoArrayList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(contactInfoArrayList);
        }
        dest.writeString(description);
        dest.writeLong(expiration != null ? expiration.getTime() : -1L);
        dest.writeString(photoUrl);
    }
}

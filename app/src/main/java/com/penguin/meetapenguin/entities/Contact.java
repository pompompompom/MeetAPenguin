package com.penguin.meetapenguin.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

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

    private Integer id;
    private String name;
    private ArrayList<ContactInfo> contactInfoArrayList;
    private String description;
    private long expiration;
    private String photoUrl;

    public Contact() {
    }

    protected Contact(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        if (in.readByte() == 0x01) {
            contactInfoArrayList = new ArrayList<ContactInfo>();
            in.readList(contactInfoArrayList, ContactInfo.class.getClassLoader());
        } else {
            contactInfoArrayList = null;
        }
        description = in.readString();
        expiration = in.readLong();
        photoUrl = in.readString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contact contact = (Contact) o;

        if (id != null ? !id.equals(contact.id) : contact.id != null) return false;
        if (name != null ? !name.equals(contact.name) : contact.name != null) return false;
        return description != null ? description.equals(contact.description) : contact.description == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getExpiration() {
        return expiration;
    }

    public void setExpiration(long expiration) {
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
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(name);
        if (contactInfoArrayList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(contactInfoArrayList);
        }
        dest.writeString(description);
        dest.writeLong(expiration);
        dest.writeString(photoUrl);
    }
}

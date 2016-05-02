package com.penguin.meetapenguin.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.penguin.meetapenguin.R;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by urbano on 4/2/16.
 */
public class Contact implements Serializable {
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
    private Set<ContactInfo> contactInfoArrayList;
    private String description;
    private long expiration;
    private String photoUrl;
    private String zipCode = "94043";
    private int profilePicResId = R.drawable.penguin;

    public Contact() {
    }

    protected Contact(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        name = in.readString();
        if (in.readByte() == 0x01) {
            contactInfoArrayList = (Set) in.readValue(Set.class.getClassLoader());
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

        return id != null ? id.equals(contact.id) : contact.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
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

    public Set<ContactInfo> getContactInfoArrayList() {
        return contactInfoArrayList;
    }

    public void setContactInfoArrayList(Set<ContactInfo> contactInfoArrayList) {
        this.contactInfoArrayList = contactInfoArrayList;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public int getProfilePicResId() {
        return profilePicResId;
    }

    public void setProfilePicResId(int profilePicResId) {
        this.profilePicResId = profilePicResId;
    }
}

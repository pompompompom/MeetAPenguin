package com.penguin.meetapenguin.util;

import com.penguin.meetapenguin.model.Contact;
import com.penguin.meetapenguin.model.ContactInfo;
import com.penguin.meetapenguin.model.contactInfoImpl.FacebookInfo;
import com.penguin.meetapenguin.model.contactInfoImpl.LocationInfo;

import java.util.ArrayList;

/**
 * Data Utilities
 */
public class DataUtil {
    public static Contact getMockContact() {
        Contact contact = new Contact();
        contact.setName("Tom Brady");
        contact.setDescription("Player");
        contact.setPhotoUrl("http://a.espncdn.com/combiner/i?img=/i/headshots/nfl/players/full/2330.png&w=350&h=254");
        ContactInfo contactInfo = new FacebookInfo();
        ContactInfo contactInfo2 = new LocationInfo();
        ArrayList<ContactInfo> contactInfoArrayList = new ArrayList<>();
        contactInfoArrayList.add(contactInfo);
        contactInfoArrayList.add(contactInfo2);
        contact.setContactInfoArrayList(contactInfoArrayList);
        return contact;
    }
}

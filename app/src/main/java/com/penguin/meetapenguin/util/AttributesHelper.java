package com.penguin.meetapenguin.util;

import com.penguin.meetapenguin.entities.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by urbano on 4/25/16.
 */
public class AttributesHelper {

    public static List<Attribute> getAllAttributes() {
        List<Attribute> attributeList = new ArrayList<>();

        Attribute attribute1 = new Attribute("Nickname", "drawable/ic_account_box_black_24dp");
        Attribute attribute2 = new Attribute("Email", "drawable/ic_mail_outline_black_24dp");
        Attribute attribute3 = new Attribute("Phone", "drawable/ic_phone_black_24dp");
        Attribute attribute4 = new Attribute("Facebook", "drawable/facebook");
        Attribute attribute5 = new Attribute("Twitter", "drawable/twitter_squared");
        Attribute attribute6 = new Attribute("LinkedIn", "drawable/facebook");
        Attribute attribute7 = new Attribute("Birthday", "drawable/ic_cake_black_24dp");
        Attribute attribute8 = new Attribute("Address", "drawable/ic_home_black_24dp");
        Attribute attribute9 = new Attribute("Website", "drawable/ic_public_black_24dp");
        Attribute attribute10 = new Attribute("Location", "drawable/ic_place_black_24dp");

        attributeList.add(attribute1);
        attributeList.add(attribute2);
        attributeList.add(attribute3);
        attributeList.add(attribute4);
        attributeList.add(attribute5);
        attributeList.add(attribute6);
        attributeList.add(attribute7);
        attributeList.add(attribute8);
        attributeList.add(attribute9);
        attributeList.add(attribute10);

        return attributeList;
    }
}


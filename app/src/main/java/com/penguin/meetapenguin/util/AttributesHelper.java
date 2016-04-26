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

        for (AttributeType type : AttributeType.values()) {
            attributeList.add(getAttribute(type));
        }

        return attributeList;
    }

    public static Attribute getAttribute(AttributeType type) {
        switch (type) {
            case Nickname:
                return new Attribute("Nickname", "drawable/ic_account_box_black_24dp");
            case Email:
                return new Attribute("Email", "drawable/ic_mail_outline_black_24dp");
            case Phone:
                return new Attribute("Phone", "drawable/ic_phone_black_24dp");
            case Facebook:
                return new Attribute("Facebook", "drawable/facebook");
            case Twitter:
                return new Attribute("Twitter", "drawable/twitter_squared");
            case LinkedIn:
                return new Attribute("LinkedIn", "drawable/facebook");
            case Birthday:
                return new Attribute("Birthday", "drawable/ic_cake_black_24dp");
            case Address:
                return new Attribute("Address", "drawable/ic_home_black_24dp");
            case Website:
                return new Attribute("Website", "drawable/ic_public_black_24dp");
            case Location:
                return new Attribute("Location", "drawable/ic_place_black_24dp");
            default:
                return null;
        }
    }

    public enum AttributeType {
        Nickname,
        Email,
        Phone,
        Facebook,
        Twitter,
        LinkedIn,
        Birthday,
        Address,
        Website,
        Location
    }
}


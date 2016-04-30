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
                return new Attribute(1, "Nickname", "drawable/ic_account_box_black_24dp");
            case Email:
                return new Attribute(2, "Email", "drawable/ic_mail_outline_black_24dp");
            case Phone:
                return new Attribute(3, "Phone", "drawable/ic_phone_black_24dp");
            case Facebook:
                return new Attribute(4, "Facebook", "drawable/facebook");
            case Twitter:
                return new Attribute(5, "Twitter", "drawable/twitter_squared");
            case LinkedIn:
                return new Attribute(6, "LinkedIn", "drawable/linkedin");
            case Birthday:
                return new Attribute(7, "Birthday", "drawable/ic_cake_black_24dp");
            case Address:
                return new Attribute(8, "Address", "drawable/ic_home_black_24dp");
            case Website:
                return new Attribute(9, "Website", "drawable/ic_public_black_24dp");
            case Location:
                return new Attribute(10, "Location", "drawable/ic_place_black_24dp");
            default:
                return null;
        }
    }

    public static Attribute getAttributeById(int id) {
        switch (id) {
            case 1:
                return new Attribute(1, "Nickname", "drawable/ic_account_box_black_24dp");
            case 2:
                return new Attribute(2, "Email", "drawable/ic_mail_outline_black_24dp");
            case 3:
                return new Attribute(3, "Phone", "drawable/ic_phone_black_24dp");
            case 4:
                return new Attribute(4, "Facebook", "drawable/facebook");
            case 5:
                return new Attribute(5, "Twitter", "drawable/twitter_squared");
            case 6:
                return new Attribute(6, "LinkedIn", "drawable/linkedin");
            case 7:
                return new Attribute(7, "Birthday", "drawable/ic_cake_black_24dp");
            case 8:
                return new Attribute(8, "Address", "drawable/ic_home_black_24dp");
            case 9:
                return new Attribute(9, "Website", "drawable/ic_public_black_24dp");
            case 10:
                return new Attribute(10, "Location", "drawable/ic_place_black_24dp");
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


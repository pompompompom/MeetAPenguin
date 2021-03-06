package com.penguin.meetapenguin.util.entitiesHelper;

import android.graphics.drawable.Drawable;

import com.penguin.meetapenguin.MeetAPenguim;
import com.penguin.meetapenguin.entities.Attribute;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that help to organize the attribute class. It hold its possible values.
 */
public class AttributesHelper {

    /**
     * Get a list of all possible attributes.
     *
     * @return A list of attributes.
     */
    public static List<Attribute> getAllAttributes() {
        List<Attribute> attributeList = new ArrayList<>();

        for (AttributeType type : AttributeType.values()) {
            attributeList.add(getAttribute(type));
        }

        return attributeList;
    }

    /**
     * It the specific attribute object based on its type.
     *
     * @param type The attribute type of the object that you want.
     * @return The attribute object.
     */
    public static Attribute getAttribute(AttributeType type) {
        switch (type) {
            case Nickname:
                return new Attribute(1, "Nickname", "drawable/ic_account_box_black_24dp");
            case Email:
                return new Attribute(2, "Email", "drawable/ic_mail_outline_black_24dp");
            case Phone:
                return new Attribute(3, "Phone Number", "drawable/ic_phone_black_24dp");
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

    /**
     * Get a attribute based on its id.
     *
     * @param id the id of the attribute that you want.
     * @return The attribute object.
     */
    public static Attribute getAttributeById(int id) {
        switch (id) {
            case 1:
                return new Attribute(1, "Nickname", "drawable/ic_account_box_black_24dp");
            case 2:
                return new Attribute(2, "Email", "drawable/ic_mail_outline_black_24dp");
            case 3:
                return new Attribute(3, "Phone Number", "drawable/ic_phone_black_24dp");
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

    /**
     * Get the icon of a specific attribute.
     *
     * @param id The Id of the attribute that you are interested on.
     * @return The drawable object.
     */
    public static Drawable getDrawable(int id) {
        String path = "";
        switch (id) {
            case 1:
                path = "drawable/ic_account_box_black_24dp";
                break;
            case 2:
                path = "drawable/ic_mail_outline_black_24dp";
                break;
            case 3:
                path = "drawable/ic_phone_black_24dp";
                break;
            case 4:
                path = "drawable/facebook";
                break;
            case 5:
                path = "drawable/twitter_squared";
                break;
            case 6:
                path = "drawable/linkedin";
                break;
            case 7:
                path = "drawable/ic_cake_black_24dp";
                break;
            case 8:
                path = "drawable/ic_home_black_24dp";
                break;
            case 9:
                path = "drawable/ic_public_black_24dp";
                break;
            case 10:
                path = "drawable/ic_place_black_24dp";
                break;
            default:
                return null;
        }
        return MeetAPenguim.getAppContext().getDrawable(MeetAPenguim.getAppContext().getResources().getIdentifier(path, null, MeetAPenguim.getAppContext().getPackageName()));
    }

    /**
     * List of possible attribute type.
     */
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


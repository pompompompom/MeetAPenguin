package com.penguin.meetapenguin.entities;

import android.content.Context;

import java.io.Serializable;

/**
 * Contact Info class.
 */
public class ContactInfo implements Serializable{

    private Attribute attribute;
    private String extraDescription;
    private String attributeValue;

    public ContactInfo(Attribute attribute, String extraDescription, String attributeValue) {

        this.attribute = attribute;
        this.extraDescription = extraDescription;
        this.attributeValue = attributeValue;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public String getExtraDescription() {
        return extraDescription;
    }

    public String getAtrributeName() {
        return getAttribute().getName();
    }

    public String getAtrributeValue() {
        return attributeValue;
    }

    public int getIconResId(Context context) {
        return context.getResources().getIdentifier(getAttribute().getIconPath(), null, context.getPackageName());
    }
}

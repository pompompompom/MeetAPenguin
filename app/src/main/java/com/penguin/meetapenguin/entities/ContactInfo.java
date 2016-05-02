package com.penguin.meetapenguin.entities;

import android.content.Context;

import java.io.Serializable;

/**
 * Contact Info class. It holds a specific information for a user. Like a email for a user.
 */
public class ContactInfo implements Serializable, Cloneable {

    private int id;
    private Attribute attribute;
    private String extraDescription;
    private String attributeValue;
    private boolean editing;

    public ContactInfo() {
    }

    public ContactInfo(Attribute attribute, String extraDescription, String attributeValue) {

        this.attribute = attribute;
        this.extraDescription = extraDescription;
        this.attributeValue = attributeValue;
        this.editing = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    public String getExtraDescription() {
        return extraDescription;
    }

    public void setExtraDescription(String extraDescription) {
        this.extraDescription = extraDescription;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public int getIconResId(Context context) {
        return context.getResources().getIdentifier(getAttribute().getIconPath(), null, context.getPackageName());
    }

    public boolean isEditing() {
        return editing;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactInfo that = (ContactInfo) o;

        if (id != that.id) return false;
        return attribute != null ? attribute.equals(that.attribute) : that.attribute == null;

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (attribute != null ? attribute.hashCode() : 0);
        return result;
    }
}

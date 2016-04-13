package com.penguin.meetapenguin.entities;

import android.content.Context;

/**
 * Contact Info class.
 */
public abstract class ContactInfo {
    public abstract Attribute getAttribute();

    public abstract String getExtraDescription();

    public String getAtrributeName() {
        return getAttribute().getName();
    }

    public abstract String getAtrributeValue();

    public int getIconResId(Context context) {
        return context.getResources().getIdentifier(getAttribute().getIconPath(), null, context.getPackageName());
    }
}

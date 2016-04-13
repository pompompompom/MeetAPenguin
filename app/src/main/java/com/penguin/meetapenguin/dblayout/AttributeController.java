package com.penguin.meetapenguin.dblayout;

import android.content.Context;

import com.penguin.meetapenguin.entities.Attribute;

/**
 * Attribute controller for Attribute CRUD.
 */
public class AttributeController {

    private final Context context;

    public AttributeController(Context context) {
        this.context = context;
    }

    public void create(Attribute attribute) {
        DatabaseConnector.getInstance(context).insertAttribute(attribute);
        DatabaseConnector.getInstance(context).printAllAttribute();
    }

    public Attribute read() {
        return null;
    }

    public void update() {

    }

    public void delete(Attribute attribute) {

    }
}

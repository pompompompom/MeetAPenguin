package com.penguin.meetapenguin.entities;

import java.io.Serializable;

/**
 * Attribute, a field of ContactInfo
 */
public class Attribute implements Serializable {

    private String name;
    private String iconPath;

    public Attribute(String name, String iconPath) {
        this.name = name;
        this.iconPath = iconPath;
    }

    public Attribute() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attribute attribute = (Attribute) o;

        return name != null ? name.equals(attribute.name) : attribute.name == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}

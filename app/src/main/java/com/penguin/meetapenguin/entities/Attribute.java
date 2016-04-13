package com.penguin.meetapenguin.entities;

/**
 * Attribute, a field of ContactInfo
 */
public class Attribute {

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
}

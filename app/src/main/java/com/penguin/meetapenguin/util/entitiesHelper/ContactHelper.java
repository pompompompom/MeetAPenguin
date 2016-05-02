package com.penguin.meetapenguin.util.entitiesHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.penguin.meetapenguin.entities.Contact;

import java.io.StringReader;

/**
 * THis class works a Contact helper to network requests.
 */
public class ContactHelper {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting().create();

    /**
     * Convert a Json object into a contact.
     *
     * @param json The json string that represents a contact object.
     * @return the contact object.
     */
    public static Contact fromJson(String json) {
        JsonObject jsonObject = new JsonParser().parse(new StringReader(json)).getAsJsonObject();
        return fromJson(jsonObject);
    }

    /**
     * Convert a JsonOBject into a Contact.
     *
     * @param jsonObject the json object that represent a contact object.
     * @return The contact object.
     */
    public static Contact fromJson(JsonObject jsonObject) {
        return GSON.fromJson(jsonObject, Contact.class);
    }

    /**
     * Convert a Contact into a Json String object.
     *
     * @param contact The contact object
     * @return The string result.
     */
    public static String toJson(Contact contact) {
        return GSON.toJson(contact);
    }
}

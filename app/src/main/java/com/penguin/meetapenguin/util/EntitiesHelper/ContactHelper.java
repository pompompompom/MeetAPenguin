package com.penguin.meetapenguin.util.EntitiesHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.penguin.meetapenguin.entities.Contact;

import java.io.StringReader;

/**
 * Created by urbano on 4/30/16.
 */
public class ContactHelper {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting().create();

    public static Contact fromJson(String json) {
        JsonObject jsonObject = new JsonParser().parse(new StringReader(json)).getAsJsonObject();
        return fromJson(jsonObject);
    }

    public static Contact fromJson(JsonObject jsonObject) {
        return GSON.fromJson(jsonObject, Contact.class);
    }

    public static String toJson(Contact contact) {
        return GSON.toJson(contact);
    }
}

package com.penguin.meetapenguin.util.EntitiesHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.penguin.meetapenguin.entities.ContactInfo;

import java.io.StringReader;
import java.util.Set;

/**
 * Created by urbano on 4/29/16.
 */
public class ContactInfoHelper {

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting().create();

    public static ContactInfo fromJson(String json) {
        JsonObject jsonObject = new JsonParser().parse(new StringReader(json)).getAsJsonObject();
        return fromJson(jsonObject);
    }

    public static ContactInfo fromJson(JsonObject jsonObject) {
        return GSON.fromJson(jsonObject, ContactInfo.class);
    }

    public static Set<ContactInfo> fromJsonList(JsonArray jsonArray) {
        return GSON.fromJson(jsonArray.toString(), new TypeToken<Set<ContactInfo>>() {
        }.getType());
    }

    public static Set<ContactInfo> fromJsonList(String json) {
        JsonArray jsonObject = new JsonParser().parse(new StringReader(json)).getAsJsonArray();
        return fromJsonList(jsonObject);
    }

    public static String toJson(ContactInfo contactInfo) {
        return GSON.toJson(contactInfo);
    }

    public static String toJson(Set<ContactInfo> contactInfo) {
        return GSON.toJson(contactInfo);
    }
}

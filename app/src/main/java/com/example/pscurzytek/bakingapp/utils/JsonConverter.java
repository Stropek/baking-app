package com.example.pscurzytek.bakingapp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public abstract class JsonConverter {

    public static <T> T convertToType(JSONObject jsonObject, Class<T> type) {
        if (jsonObject == null) {
            return null;
        }

        Gson gson = new Gson();
        return gson.fromJson(jsonObject.toString(), type);
    }

    public static <T> String convertToJson(T object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static <T> ArrayList<T> convertArrayToType(JSONArray array, Class<T> type) {
        if (array == null) {
            return null;
        }

        ArrayList<T> objects = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            T obj = convertToType(array.optJSONObject(i), type);
            objects.add(obj);
        }
        return objects;
    }
}

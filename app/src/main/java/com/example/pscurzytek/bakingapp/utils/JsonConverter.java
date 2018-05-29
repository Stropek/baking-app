package com.example.pscurzytek.bakingapp.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class JsonConverter {

    public static <T> T convertTo(JSONObject jsonObject, Class<T> type) {
        if (jsonObject == null) {
            return null;
        }

        Gson gson = new GsonBuilder().create();
        return gson.fromJson(jsonObject.toString(), type);
    }

    public static <T> List<T> convertListTo(JSONArray array, Class<T> type) {
        if (array == null) {
            return null;
        }

        List<T> objects = new ArrayList<>();

        for (int i = 0; i < array.length(); i++) {
            T obj = convertTo(array.optJSONObject(i), type);
            objects.add(obj);
        }
        return objects;
    }
}

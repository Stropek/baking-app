package com.example.pscurzytek.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RecipeBase implements Parcelable {

    private int id;
    private String name;
    private ArrayList<Ingredient> ingredients;

    RecipeBase(int id, String name, ArrayList<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
    }

    RecipeBase(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
    }

    public static final Creator<RecipeBase> CREATOR = new Creator<RecipeBase>() {
        @Override
        public RecipeBase createFromParcel(Parcel in) {
            return new RecipeBase(in);
        }

        @Override
        public RecipeBase[] newArray(int size) {
            return new RecipeBase[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(ingredients);
    }

    public int getId() {
        return id;
    }

    public void setId(int _id) {
        this.id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        this.name = _name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> _ingredients) {
        this.ingredients = _ingredients;
    }
}

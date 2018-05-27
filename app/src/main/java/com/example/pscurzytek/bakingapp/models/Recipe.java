package com.example.pscurzytek.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Recipe implements Parcelable {

    private int id;
    private String name;
    private List<String> ingredients;
    private List<String> steps;
    private int servings;
    private String image;

    public Recipe(int id, String name, List<String> ingredients, List<String> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    protected Recipe(Parcel in) {
        id = in.readInt();
        name = in.readString();
        in.readStringList(ingredients);
        in.readStringList(steps);
        servings = in.readInt();
        image = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeStringList(ingredients);
        dest.writeStringList(steps);
        dest.writeInt(servings);
        dest.writeString(image);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {

        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

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

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> _ingredients) {
        this.ingredients = _ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> _steps) {
        this.steps = _steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int _servings) {
        this.servings = _servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String _image) {
        this.image = _image;
    }
}

package com.example.pscurzytek.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Recipe extends RecipeBase
        implements Parcelable {

    private ArrayList<Step> steps;
    private int servings;
    private String image;

    public Recipe(int id, String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, int servings, String image) {
        super(id, name, ingredients);
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }

    private Recipe(Parcel in) {
        super(in);
        steps = in.createTypedArrayList(Step.CREATOR);
        servings = in.readInt();
        image = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(steps);
        dest.writeInt(servings);
        dest.writeString(image);
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> _steps) {
        this.steps = _steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int _servings) {
        this.servings = _servings;
    }

    public String getImage() {
        if (image.isEmpty()) {
            return "no-image";
        }
        return image;
    }

    public void setImage(String _image) {
        this.image = _image;
    }
}

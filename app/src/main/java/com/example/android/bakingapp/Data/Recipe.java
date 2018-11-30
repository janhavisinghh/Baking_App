package com.example.android.bakingapp.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Recipe implements Parcelable {

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("ingredients")
    private ArrayList<Ingredients> ingredients;
    @SerializedName("steps")
    private ArrayList<Steps> steps;
    @SerializedName("servings")
    private String servings;
    @SerializedName("image")
    private String image;

    public Recipe(String id, String name, ArrayList<Ingredients> ingredients, ArrayList<Steps> steps, String servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;

    }

    public Recipe(Parcel source) {
        this.id = source.readString();
        this.name = source.readString();
        this.ingredients = source.readArrayList(null);
        this.steps = source.readArrayList(null);
        this.servings = source.readString();
        this.image = source.readString();

    }

    public String getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    public ArrayList<Ingredients> getIngredients() {

        return ingredients;
    }
    public ArrayList<Steps> getSteps() {

        return steps;
    }
    public String getServings() {

        return servings;
    }
    public String getImage() {

        return image;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeList(ingredients);
        dest.writeList(steps);
        dest.writeString(servings);
        dest.writeString(image);
    }


}

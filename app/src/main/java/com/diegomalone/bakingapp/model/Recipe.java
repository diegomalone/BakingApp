package com.diegomalone.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Diego Malone on 06/01/18.
 */

public class Recipe implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("ingredients")
    private List<Ingredient> ingredientList;

    @SerializedName("steps")
    private List<Step> stepList;

    @SerializedName("servings")
    private int servings;

    @SerializedName("image")
    private String imageURL;

    public Recipe(int id, int servings, String name, String imageURL, List<Ingredient> ingredientList, List<Step> stepList) {
        this.id = id;
        this.servings = servings;
        this.name = name;
        this.imageURL = imageURL;
        this.ingredientList = ingredientList;
        this.stepList = stepList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public void setIngredientList(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public List<Step> getStepList() {
        return stepList;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", servings=" + servings +
                ", name='" + name + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", ingredientList=" + ingredientList +
                ", stepList=" + stepList +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.servings);
        dest.writeString(this.name);
        dest.writeString(this.imageURL);
        dest.writeTypedList(this.ingredientList);
        dest.writeTypedList(this.stepList);
    }

    protected Recipe(Parcel in) {
        this.id = in.readInt();
        this.servings = in.readInt();
        this.name = in.readString();
        this.imageURL = in.readString();
        this.ingredientList = in.createTypedArrayList(Ingredient.CREATOR);
        this.stepList = in.createTypedArrayList(Step.CREATOR);
    }

    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe(source);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
}

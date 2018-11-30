package com.example.android.bakingapp.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Steps implements Parcelable {

    public static final Creator<Steps> CREATOR = new Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel source) {
            return new Steps(source);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };
    @SerializedName("id")
    private String id;
    @SerializedName("shortDescription")
    private String shortDescription;
    @SerializedName("description")
    private String description;
    @SerializedName("videoURL")
    private String videoURL;
    @SerializedName("thumbnailURL")
    private String thumbnailURL;


    public Steps(String id, String shortDescription, String description, String videoURL, String thumbnailURL) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoURL = videoURL;
        this.thumbnailURL = thumbnailURL;
    }

    public Steps(Parcel source) {
        this.id = source.readString();
        this.shortDescription = source.readString();
        this.description = source.readString();
        this.videoURL = source.readString();
        this.thumbnailURL = source.readString();
    }

    public String getId() {

        return id;
    }

    public String getShortDescription() {

        return shortDescription;
    }

    public String getDescription() {

        return description;
    }
    public String getVideoURL() {

        return videoURL;
    }
    public String getThumbnailURL() {

        return thumbnailURL;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
    }


}

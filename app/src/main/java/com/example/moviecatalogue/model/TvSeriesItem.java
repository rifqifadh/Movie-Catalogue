package com.example.moviecatalogue.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class TvSeriesItem implements Parcelable {

    public TvSeriesItem(int id, String originalName, String posterPath, String overview) {
        this.id = id;
        this.originalName = originalName;
        this.posterPath = posterPath;
        this.overview = overview;
    }

    private  int id;

    @SerializedName("original_name")
    private String originalName;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("overview")
    private String overview;

    protected TvSeriesItem(Parcel in) {
        id = in.readInt();
        originalName = in.readString();
        posterPath = in.readString();
        overview = in.readString();
    }

    public static final Creator<TvSeriesItem> CREATOR = new Creator<TvSeriesItem>() {
        @Override
        public TvSeriesItem createFromParcel(Parcel in) {
            return new TvSeriesItem(in);
        }

        @Override
        public TvSeriesItem[] newArray(int size) {
            return new TvSeriesItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public String toString() {
        return "Tvseries{" +
                "id=" + id +
                ", originalName='" + originalName + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", overview='" + overview + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(originalName);
        dest.writeString(posterPath);
        dest.writeString(overview);
    }
}
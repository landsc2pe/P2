package com.jayjaylab.lesson.gallery.util.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by jjkim on 2016. 5. 17..
 */

public class Thumbnail implements Parcelable {
    int id;
    int imageId;
    String path;

    public Thumbnail(Parcel in) {
        readFromParcel(in);
    }

    public Thumbnail(int id, int imageId, String path) {
        this.id = id;
        this.imageId = imageId;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public Thumbnail setId(int id) {
        this.id = id;
        return this;
    }

    public int getImageId() {
        return imageId;
    }

    public Thumbnail setImageId(int imageId) {
        this.imageId = imageId;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Thumbnail setPath(String path) {
        this.path = path;
        return this;
    }

    @Override
    public String toString() {
        return Thumbnail.class.getSimpleName() +
                "{id : " + id +
                ", imageId : " + imageId +
                ", path : " + path +
                "}";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(imageId);
        dest.writeString(path);
    }

    protected void readFromParcel(Parcel in) {
        id = in.readInt();
        imageId = in.readInt();
        path = in.readString();
    }

    public static final Parcelable.Creator<Thumbnail> CREATOR =
            new Parcelable.Creator<Thumbnail>() {
                @Override
                public Thumbnail createFromParcel(Parcel source) {
                    return new Thumbnail(source);
                }

                @Override
                public Thumbnail[] newArray(int size) {
                    return new Thumbnail[size];
                }
            };
}

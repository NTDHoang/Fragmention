package me.yokeyword.ntdhoang.demo_zhihu.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YoKeyword on 16/2/1.
 */
public class Hero implements Parcelable {
    private String id;
    private String name;
    private int imgRes;

    public Hero(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Hero(String id, int imgRes) {
        this.id = id;
        this.imgRes = imgRes;
    }

    protected Hero(Parcel in) {
        id = in.readString();
        name = in.readString();
        imgRes = in.readInt();
    }

    public static final Creator<Hero> CREATOR = new Creator<Hero>() {
        @Override
        public Hero createFromParcel(Parcel in) {
            return new Hero(in);
        }

        @Override
        public Hero[] newArray(int size) {
            return new Hero[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeInt(imgRes);
    }
}

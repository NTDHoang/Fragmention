package me.yokeyword.ntdhoang.demo_zhihu.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YoKeyword on 16/2/1.
 */
@Entity
public class Hero implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String imgRes;
    private String type;
    private String link;

    public Hero(String name, String imgRes, String type, String link) {
        this.name = name;
        this.imgRes = imgRes;
        this.type = type;
        this.link = link;
    }

    @Ignore
    public Hero() {
    }



    protected Hero(Parcel in) {
        id = in.readInt();
        name = in.readString();
        imgRes = in.readString();
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgRes() {
        return imgRes;
    }

    public void setImgRes(String imgRes) {
        this.imgRes = imgRes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(imgRes);
    }
}

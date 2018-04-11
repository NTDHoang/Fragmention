package me.yokeyword.ntdhoang.demo_zhihu.data.entity;

import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YoKeyword on 16/2/1.
 */
@Entity
public class Item extends BaseEntity implements Parcelable {
    private String level;
    private String content;

    public Item(String level, String content) {
        this.level = level;
        this.content = content;
    }

    public Item() {

    }

    protected Item(Parcel in) {
        level = in.readString();
        content = in.readString();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}

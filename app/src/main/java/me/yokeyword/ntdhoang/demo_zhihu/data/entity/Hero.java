package me.yokeyword.ntdhoang.demo_zhihu.data.entity;

import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by YoKeyword on 16/2/1.
 */
@Entity
public class Hero extends BaseEntity implements Parcelable {

    public Hero() {
    }

    protected Hero(Parcel in) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}

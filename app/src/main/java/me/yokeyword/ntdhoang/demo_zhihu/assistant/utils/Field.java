package me.yokeyword.ntdhoang.demo_zhihu.assistant.utils;

/**
 * Created by hieupham on 21/02/2017.
 */

public class Field<T> {

    private T value;

    public Field(){}

    public Field(T value) {
        this.value = value;
    }

    public void set(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }
}

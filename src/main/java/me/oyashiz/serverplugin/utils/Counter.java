package me.oyashiz.serverplugin.utils;

public class Counter {
    int value;

    public Counter(int value) {
        this.value = value;
    }


    public Counter() {
        value = 0;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void addValue() {
        value++;
    }
}

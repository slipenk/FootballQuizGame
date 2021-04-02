package com.yurii_slipenkyi.quiz;

import java.util.Locale;

public class Category {

    public static final int UCL = 1;
    public static final int England = 2;


    private int id;
    private String name;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

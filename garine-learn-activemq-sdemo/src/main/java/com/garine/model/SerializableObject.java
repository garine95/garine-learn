package com.garine.model;

import java.io.Serializable;

public class SerializableObject  implements Serializable{

    private String name = "this is name";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
}

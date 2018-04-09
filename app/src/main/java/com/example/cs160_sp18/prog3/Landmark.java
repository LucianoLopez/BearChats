package com.example.cs160_sp18.prog3;

/**
 * Created by Luciano1 on 4/8/18.
 */

public class Landmark {

    public String name;
    public float distance;
    public String imgName;

    Landmark(String name, float distance, String imgName) {
        this.distance = distance;
        this.imgName = imgName;
        this.name = name;
    }
    protected void updateDistance(float newDistance) {
        this.distance = newDistance;
    }
}

package com.example.cs160_sp18.prog3;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Luciano1 on 4/8/18.
 */

public class Landmark {

    public String name;
    public Double[] coordinates;
    public float distance;
    public String fileName;

    Landmark(String name, Double[] coordinates, String fileName, float distance) {
        this.fileName = fileName;
        this.name = name;
        this.coordinates = coordinates;
        this.distance = distance;
    }


    protected void updateDistance(float newDistance) {
        this.distance = newDistance;
    }
}

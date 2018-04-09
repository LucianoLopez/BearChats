package com.example.cs160_sp18.prog3;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Luciano1 on 4/8/18.
 */

public class Landmark {

    public String name;
    public float distance;
    public Drawable icon;

    Landmark(String name, float distance, Drawable icon) {
        this.distance = distance;
        this.icon = icon;
        this.name = name;
    }
    protected void updateDistance(float newDistance) {
        this.distance = newDistance;
    }
}

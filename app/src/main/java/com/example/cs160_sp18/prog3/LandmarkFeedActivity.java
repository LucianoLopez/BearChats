package com.example.cs160_sp18.prog3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toolbar;

import java.util.ArrayList;
import org.json.*;

/**
 * Created by Luciano1 on 4/8/18.
 */

public class LandmarkFeedActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Landmark> mLandmarks = new ArrayList<>();

    EditText landmarkName;
    RelativeLayout layout;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark_feed);
        layout = findViewById(R.id.landmark_layout);
        mRecyclerView = findViewById(R.id.landmark_recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        setAdapterAndUpdateData();


    }

    private void fillLandmarks() {

    }

    private void setAdapterAndUpdateData() {
        mAdapter = new LandmarkAdapter(this, mLandmarks);
        mRecyclerView.setAdapter(mAdapter);

    }



}

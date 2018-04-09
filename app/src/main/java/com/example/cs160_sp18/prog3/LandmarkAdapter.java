package com.example.cs160_sp18.prog3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Luciano1 on 4/8/18.
 */

public class LandmarkAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<Landmark> mLandmarks;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.landmark_cell_layout, parent, false);
        return new CommentViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
    @Override
    public int getItemCount() {
        return mLandmarks.size();
    }
}
class LandmarkViewHolder extends RecyclerView.ViewHolder {
    public RelativeLayout mLandmarkLayout;
    public ImageView mLandmarkIcon;
    public TextView mLandmarkName;
    public TextView mLandmarkDistance;

    public LandmarkViewHolder(View itemView) {
        super(itemView);
        mLandmarkLayout = itemView.findViewById(R.id.landmark_cell_layout);
        mLandmarkIcon = mLandmarkLayout.findViewById(R.id.landmark_icon);
        mLandmarkName = mLandmarkLayout.findViewById(R.id.landmark_name);
        mLandmarkDistance = mLandmarkLayout.findViewById(R.id.landmark_distance);

    }

    void bind(Landmark landmark) {
        mLandmarkIcon.setImageDrawable(landmark.icon);
        mLandmarkName.setText(landmark.name);
        mLandmarkDistance.setText(String.valueOf(landmark.distance) + " meters away");
    }

    void updateDistance(float newDistance) {
        mLandmarkDistance.setText(String.valueOf(newDistance) + " meters away");
    }

}
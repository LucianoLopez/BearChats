package com.example.cs160_sp18.prog3;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Luciano1 on 4/8/18.
 */

public class LandmarkAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private ArrayList<Landmark> mLandmarks;
    private LandmarkViewHolder landmark;
//    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            Intent intent = new Intent(view.getContext(), CommentFeedActivity.class);
//            intent.putExtra("landmarkName", landmark.name);
//            mContext.startActivity(intent);
//
//        }
//    };

    LandmarkAdapter(Context context, ArrayList<Landmark> landmarks) {
        mContext = context;
        mLandmarks = landmarks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.landmark_cell_layout, parent, false);
//        view.setOnClickListener(mOnClickListener);
        landmark = new LandmarkViewHolder(view);
        return landmark;

    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Landmark landmark = mLandmarks.get(position);
        ((LandmarkViewHolder)holder).bind(landmark, mContext);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (landmark.distance <= 10) {
                    Intent intent = new Intent(v.getContext(), CommentFeedActivity.class);
                    intent.putExtra("landmarkName", landmark.name);
                    mContext.startActivity(intent);
                } else {
                    Toast errorToast = Toast.makeText(mContext, "You must be within 10 meters to view this chat.", Toast.LENGTH_SHORT);
                    errorToast.show();
                }
            }
        };
        ((LandmarkViewHolder) holder).mLandmarkLayout.setOnClickListener(listener);
        ((LandmarkViewHolder) holder).mLandmarkName.setOnClickListener(listener);
        ((LandmarkViewHolder) holder).mLandmarkIcon.setOnClickListener(listener);

    }
    @Override
    public int getItemCount() {
        return mLandmarks.size();
    }
}
class LandmarkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public RelativeLayout mLandmarkLayout;
    public ImageView mLandmarkIcon;
    public TextView mLandmarkName;
    public TextView mLandmarkDistance;
    public String stringName;
    public Context mContext;


    public LandmarkViewHolder(View itemView) {
        super(itemView);
        mLandmarkLayout = itemView.findViewById(R.id.landmark_cell_layout);
        mLandmarkIcon = mLandmarkLayout.findViewById(R.id.landmark_icon);
        mLandmarkName = mLandmarkLayout.findViewById(R.id.landmark_name);
        mLandmarkDistance = mLandmarkLayout.findViewById(R.id.landmark_distance);
        mLandmarkLayout.setOnClickListener(this);
        mLandmarkIcon.setOnClickListener(this);
        mLandmarkName.setOnClickListener(this);
        mLandmarkDistance.setOnClickListener(this);

    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), CommentFeedActivity.class);
        intent.putExtra("landmarkName", stringName);
        mContext.startActivity(intent);
    }

    void bind(Landmark landmark, Context context) {
        mContext = context;
        this.stringName = landmark.name;
        int resourceID = context.getResources().getIdentifier(landmark.fileName, "drawable", context.getPackageName());
        int backgroundID = context.getResources().getIdentifier("customborder", "drawable", context.getPackageName());
        mLandmarkIcon.setImageDrawable(context.getDrawable(resourceID));
        mLandmarkName.setText(landmark.name);
        mLandmarkDistance.setText(String.valueOf(landmark.distance) + " meters");
        if (landmark.distance <= 10) {
            mLandmarkLayout.setBackgroundColor(context.getResources().getColor(R.color.facebooklightblue));
        } else {
            mLandmarkLayout.setBackgroundColor(context.getResources().getColor(R.color.lightGray));
        }
//        mLandmarkLayout.setBackground(context.getDrawable(backgroundID));
    }


}

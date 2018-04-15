package com.example.cs160_sp18.prog3;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

// Adapter for the recycler view in CommentFeedActivity. You do not need to modify this file
public class CommentAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private ArrayList<Comment> mComments;
    private Layout mLayout;
    private String mCommentReference;
    private String mLandmark;

    public CommentAdapter(Context context, ArrayList<Comment> comments) {
        mContext = context;
        mComments = comments;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // here, we specify what kind of view each cell should have. In our case, all of them will have a view
        // made from comment_cell_layout
        View view = LayoutInflater.from(mContext).inflate(R.layout.comment_cell_layout, parent, false);
        return new CommentViewHolder(view);
    }


    // - get element from your dataset at this position
    // - replace the contents of the view with that element
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // here, we the comment that should be displayed at index `position` in our recylcer view
        // everytime the recycler view is refreshed, this method is called getItemCount() times (because
        // it needs to recreate every cell).
        final Comment comment = mComments.get(position);

        ((CommentViewHolder) holder).bind(comment);

        ((CommentViewHolder) holder).mVotes.setText(String.valueOf(comment.votes));
        if (comment.votes > 0) {
            ((CommentViewHolder) holder).mVotes.setTextColor(mContext.getResources().getColor(R.color.green));
        } else if (comment.votes < 0) {
            ((CommentViewHolder) holder).mVotes.setTextColor(mContext.getResources().getColor(R.color.red));

        }
        ((CommentViewHolder) holder).mLikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dbref = FirebaseDatabase.getInstance().getReference(comment.landmark);
                dbref.child(comment.commentReference).child("votes").setValue(comment.votes + 1);
                view.findViewById(R.id.like_button).setBackgroundColor(mContext.getResources().getColor(R.color.green));
//                RelativeLayout layout = view.findViewById(R.id.comment_cell_layout);
////                Button button = layout.findViewById(id);
//                view.findViewById(R.id.comment_cell_layout).findViewById(R.id.like_button).setBackgroundColor(mContext.getResources().getColor(R.color.green));
//                view.findViewById(R.id.comment_cell_layout).findViewById(view.getId()).setBackgroundColor(mContext.getResources().getColor(R.color.green));
            }
        });
        ((CommentViewHolder) holder).mDislikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference(comment.landmark).child(
                        comment.commentReference).child("votes").setValue(comment.votes - 1);
                view.findViewById(R.id.dislike_button).setBackgroundColor(mContext.getResources().getColor(R.color.red));
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mComments.size();
    }
}

class CommentViewHolder extends RecyclerView.ViewHolder {

    // each data item is just a string in this case
    public RelativeLayout mCommentBubbleLayout;
    public TextView mUsernameTextView;
    public TextView mDateTextView;
    public TextView mCommentTextView;
    public ImageButton mLikeButton;
    public ImageButton mDislikeButton;
    public TextView mVotes;
    public String mLandmark;
    public Object mCommentReference;

    public CommentViewHolder(View itemView) {
        super(itemView);
        mCommentBubbleLayout = itemView.findViewById(R.id.comment_cell_layout);
        mUsernameTextView = mCommentBubbleLayout.findViewById(R.id.username_text_view);
        mDateTextView = mCommentBubbleLayout.findViewById(R.id.date_text_view);
        mCommentTextView = mCommentBubbleLayout.findViewById(R.id.comment_text_view);
        mLikeButton = mCommentBubbleLayout.findViewById(R.id.like_button);
        mDislikeButton = mCommentBubbleLayout.findViewById(R.id.dislike_button);
        mVotes = mCommentBubbleLayout.findViewById(R.id.votes);
    }

    void bind(Comment comment) {
        mUsernameTextView.setText(comment.username);
        mDateTextView.setText(comment.elapsedTimeString());
        mCommentTextView.setText(comment.text);
        mCommentReference = comment.commentReference;
        mLandmark = comment.landmark;
    }
}

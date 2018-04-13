package com.example.cs160_sp18.prog3;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

// Displays a list of comments for a particular landmark.
public class CommentFeedActivity extends AppCompatActivity {

    private static final String TAG = CommentFeedActivity.class.getSimpleName();
    private String landmarkName;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Comment> mComments = new ArrayList<>();
    private DatabaseReference dbref;
    private boolean messageJustSent = false;
    private Comment lastComment;

    // UI elements
    EditText commentInputBox;
    RelativeLayout layout;
    Button sendButton;
    Toolbar mToolbar;

    /* TODO: right now mRecyclerView is using hard coded comments.
     * You'll need to add functionality for pulling and posting comments from Firebase
     */

    protected void writeToDatabase(Comment comment) {
        DatabaseReference chatRef = dbref.push();
        chatRef.setValue(comment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_feed);
        Bundle extras = getIntent().getExtras();

        // TODO: replace this with the name of the landmark the user chose
        landmarkName = extras.getString("landmarkName");

        // sets the app bar's title
        setTitle(landmarkName + ": Posts");

        // hook up UI elements
        layout = (RelativeLayout) findViewById(R.id.comment_layout);
        commentInputBox = (EditText) layout.findViewById(R.id.comment_input_edit_text);
        sendButton = (Button) layout.findViewById(R.id.send_button);

        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(landmarkName + " Chatroom");

        mRecyclerView = (RecyclerView) findViewById(R.id.comment_recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbref = FirebaseDatabase.getInstance().getReference(landmarkName);
        ValueEventListener myDataListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap> comments = (HashMap<String, HashMap>) dataSnapshot.getValue();
                if (comments == null) {
                    return;
                }
                mComments = parseComments(comments);
//                if (messageJustSent) {
//                    messageJustSent = false;
//                    mComments.add(lastComment);
//                }

                setAdapterAndUpdateData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("0", "cancelled");
            }
        };
        dbref.addValueEventListener(myDataListener);


        // create an onclick for the send button
        setOnClickForSendButton();

        // make some test comment objects that we add to the recycler view
//        makeTestComments();

        // use the comments in mComments to create an adapter. This will populate mRecyclerView
        // with a custom cell (with comment_cell_layout) for each comment in mComments
        setAdapterAndUpdateData();
    }
    private ArrayList<Comment> parseComments(HashMap<String, HashMap> hashComments) {
        //find how many new comments there are
        Object[] keySet = hashComments.keySet().toArray();
        Arrays.sort(keySet);
        ArrayList<Comment> newComments = new ArrayList<>();
        for (int i = 0; i < hashComments.size(); i++) {
//            Comment newComment = new Comment(text, username, date);
            HashMap<String, Object> hashComment = hashComments.get(keySet[i]);
//            Date date = new Date(year, month, date, hrs, min, sec);
            HashMap<String, Long> hashDate = (HashMap) hashComment.get("date");
            int year = hashDate.get("year").intValue();
            int month = hashDate.get("month").intValue();
            int date = hashDate.get("date").intValue();
            int hrs = hashDate.get("hours").intValue();
            int min = hashDate.get("minutes").intValue();
            int sec = hashDate.get("seconds").intValue();
            Date commentDate = new Date(year, month, date, hrs, min, sec);
            Comment newComment = new Comment((String) hashComment.get("text"), (String) hashComment.get("username"), commentDate);
            newComments.add(newComment);
        }
        return newComments;
    }

    // TODO: delete me
    private void makeTestComments() {
        String randomString = "hello world hello world ";
        Comment newComment = new Comment(randomString, "test_user1", new Date());
        Comment hourAgoComment = new Comment(randomString + randomString, "test_user2", new Date(System.currentTimeMillis() - (60 * 60 * 1000)));
        Comment overHourComment = new Comment(randomString, "test_user3", new Date(System.currentTimeMillis() - (2 * 60 * 60 * 1000)));
        Comment dayAgoComment = new Comment(randomString, "test_user4", new Date(System.currentTimeMillis() - (25 * 60 * 60 * 1000)));
        Comment daysAgoComment = new Comment(randomString + randomString + randomString, "test_user5", new Date(System.currentTimeMillis() - (48 * 60 * 60 * 1000)));
        mComments.add(newComment);mComments.add(hourAgoComment); mComments.add(overHourComment);mComments.add(dayAgoComment); mComments.add(daysAgoComment);

    }

    private void setOnClickForSendButton() {
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = commentInputBox.getText().toString();
                if (TextUtils.isEmpty(comment)) {
                    // don't do anything if nothing was added
                    commentInputBox.requestFocus();
                } else {
                    // clear edit text, post comment
                    commentInputBox.setText("");
                    postNewComment(comment);
                }
            }
        });
    }

    private void setAdapterAndUpdateData() {
        // create a new adapter with the updated mComments array
        // this will "refresh" our recycler view
        mAdapter = new CommentAdapter(this, mComments);
        mRecyclerView.setAdapter(mAdapter);

        // scroll to the last comment
        if (mComments.size() > 0) {
            mRecyclerView.smoothScrollToPosition(mComments.size() - 1);
        } else {
        mRecyclerView.smoothScrollToPosition(0);
        }
    }

    private void postNewComment(String commentText) {
        Comment newComment = new Comment(commentText, "one-sixty student", new Date());
//        mComments.add(newComment);
        writeToDatabase(newComment);
//        setAdapterAndUpdateData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

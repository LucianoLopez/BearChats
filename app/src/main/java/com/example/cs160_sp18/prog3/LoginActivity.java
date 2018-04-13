package com.example.cs160_sp18.prog3;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Luciano1 on 4/13/18.
 */

public class LoginActivity extends AppCompatActivity {
    private ConstraintLayout mLayout;
    private EditText mUsernameInput;
    private Button mLoginButton;
    private Button mCancelButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mLayout = findViewById(R.id.login_layout);
        mUsernameInput = mLayout.findViewById(R.id.username_input);
        mLoginButton = mLayout.findViewById(R.id.login_button);
        mCancelButton = mLayout.findViewById(R.id.cancel_button);
        setOnClickForLoginButton();
        setOnClickForCancelButton();


    }

    private void setOnClickForLoginButton() {
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = mUsernameInput.getText().toString();
                if (TextUtils.isEmpty(username)) {
                    Toast errorToast = Toast.makeText(view.getContext(), "Please provide a username", Toast.LENGTH_SHORT);
                    errorToast.show();
                    mUsernameInput.requestFocus();
                } else {
//                    mUsernameInput.setText("");
                    Intent intent = new Intent(view.getContext(), LandmarkFeedActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
            }
        });
    }
    private void setOnClickForCancelButton() {
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUsernameInput.setText("");
            }
        });
    }
}

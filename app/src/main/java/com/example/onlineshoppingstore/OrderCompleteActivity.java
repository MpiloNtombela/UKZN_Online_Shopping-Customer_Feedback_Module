package com.example.onlineshoppingstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.onlineshoppingstore.feedback.model.Feedback;
import com.example.onlineshoppingstore.feedback.repo.DBFeedback;
import com.example.onlineshoppingstore.feedback.repo.SessionManager;
import com.example.onlineshoppingstore.feedback.ui.FeedbackActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class OrderCompleteActivity extends AppCompatActivity implements View.OnClickListener {
    private SessionManager sessionManager;
    private DBFeedback feedbackRepo;
    private Feedback feedback;
    private TextView usernameTextView;
    private Button giveFeedbackButton;
    private Button logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complete);
        Objects.requireNonNull(getSupportActionBar()).hide();
        sessionManager = SessionManager.getInstance(this.getApplication());
        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(this, "Please login", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }

        usernameTextView = findViewById(R.id.usernameTextView);
        usernameTextView.setText(sessionManager.getUsername()); // set username of logged in user

        feedbackRepo = DBFeedback.getInstance(this);
        feedback = feedbackRepo.getFeedbackByAuthorId(sessionManager.getUid().toString());

        giveFeedbackButton = findViewById(R.id.giveFeedbackButton);
        giveFeedbackButton.setOnClickListener(this);

        logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(this);

        if (!feedback.isNull()){
            giveFeedbackButton.setText(R.string.update_feedback);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.giveFeedbackButton) {
            Intent intent = new Intent(this, FeedbackActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        } else if (id == R.id.logoutButton) {
            // are you sure you want to logout?
            Snackbar.make(v, "Are you sure you want to logout?", Snackbar.LENGTH_LONG)
                    .setAction("Yes", v1 -> {
                        sessionManager.logout();
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        finish();
                        startActivity(intent);
                    }).show();
        }
    }
}
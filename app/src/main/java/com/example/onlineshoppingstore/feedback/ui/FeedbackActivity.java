package com.example.onlineshoppingstore.feedback.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import com.example.onlineshoppingstore.MainActivity;
import com.example.onlineshoppingstore.R;
import com.example.onlineshoppingstore.feedback.repo.SessionManager;
import com.google.android.material.snackbar.Snackbar;

public class FeedbackActivity extends AppCompatActivity {
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        View parentLayout = findViewById(android.R.id.content);

        //This checks if the user is logged in. If not, it will send them back to the login page.
        sessionManager = SessionManager.getInstance(this.getApplication());
        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(this, "Login to give feedback", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }

        Toolbar toolbar = findViewById(R.id.feedbackToolbar);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_view_feedbacks) {
                CustomerFeedbacksFragment customerFeedbacksFragment = new CustomerFeedbacksFragment();
                customerFeedbacksFragment.show(getSupportFragmentManager(), "Customer Feedbacks");
                return true;
            } else if (item.getItemId() == R.id.action_logout) {
                Snackbar.make(parentLayout, "Are you sure you want to logout?", Snackbar.LENGTH_LONG)
                        .setAction("Yes", v1 -> {
                            sessionManager.logout();
                            Intent intent = new Intent(this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            finish();
                            startActivity(intent);
                        }).show();
            }
            return false;
        });
    }
}
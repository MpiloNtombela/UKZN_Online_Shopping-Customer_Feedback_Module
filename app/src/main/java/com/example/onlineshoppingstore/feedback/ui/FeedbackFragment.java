package com.example.onlineshoppingstore.feedback.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.onlineshoppingstore.R;
import com.example.onlineshoppingstore.feedback.model.Feedback;
import com.example.onlineshoppingstore.feedback.repo.DBFeedback;
import com.example.onlineshoppingstore.feedback.repo.SessionManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class FeedbackFragment extends Fragment implements View.OnClickListener {
    private SessionManager sessionManager;
    private DBFeedback feedbackRepo;
    private RatingBar overallRating;
    private Button submitFeedbackButton;
    private TextView ratingTextView;
    private TextInputLayout feedbackTextInputLayout;
    private LinearLayout servicesFeedbackLayout;
    private Button yesButton;
    private Feedback feedback;
    private TextView ratingRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = SessionManager.getInstance(requireActivity().getApplication());
        feedbackRepo = DBFeedback.getInstance(getContext());
        feedback = feedbackRepo.getFeedbackByAuthorId(sessionManager.getUid().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        overallRating = view.findViewById(R.id.overallRatingBar);
        submitFeedbackButton = view.findViewById(R.id.submitFeedbackButton);
        ratingTextView = view.findViewById(R.id.ratingMessage);
        feedbackTextInputLayout = view.findViewById(R.id.feedbackTextInputLayout);
        servicesFeedbackLayout = view.findViewById(R.id.servicesFeedbackLayout);
        yesButton = view.findViewById(R.id.yesButton);
        ratingRequest = view.findViewById(R.id.ratingRequest);

        overallRating.setRating(1); // default rating to 1

        if (!feedback.isNull()) {
            feedbackTextInputLayout.setHelperText("update your feedback here");
            submitFeedbackButton.setText(R.string.update_feedback);
            alreadyGivenFeedback();
        }
        overallRating.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            int ratingValue = (int) rating;
            if (ratingValue == 0) {
                ratingBar.setRating(1);
            }
            switch (ratingValue) {
                case 1:
                    ratingTextView.setText(R.string.fb_r1);
                    break;
                case 2:
                    ratingTextView.setText(R.string.fb_r2);
                    break;
                case 3:
                    ratingTextView.setText(R.string.fb_r3);
                    break;
                case 4:
                    ratingTextView.setText(R.string.fb_r4);
                    break;
                case 5:
                    ratingTextView.setText(R.string.fb_r5);
                    break;
            }
        });

        submitFeedbackButton.setOnClickListener(this);
        yesButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == submitFeedbackButton.getId()) {
            String feedbackMsg = Objects.requireNonNull(feedbackTextInputLayout.getEditText()).getText().toString();
            if (feedbackMsg.isEmpty()) {
                feedbackTextInputLayout.setError("Please enter your feedback");
                return;
            } else {
                feedbackTextInputLayout.setError(null);
            }
            int rating = (int) overallRating.getRating();
            Feedback fb = new Feedback(sessionManager.getUid().toString(), feedbackMsg, rating);
           // The code checks if the feedback is null or not,
            // and if it isn't, it sets the helper text to "update your feedback here" and
            // the submit feedback button text to "update feedback".
            // If the feedback isn't null, it means that the user has already given feedback,
            // so the alreadyGivenFeedback() method is called.
            if (!feedback.isNull()) {
                fb.setId(feedback.getId());
                fb.setAuthorId(feedback.getAuthorId());
                if (feedbackRepo.updateFeedback(fb)) {
                    Snackbar.make(v, "Feedback updated successfully", Snackbar.LENGTH_LONG).show();
                    feedbackSubmitted();
                } else {
                    Snackbar.make(v, "Feedback update failed", Snackbar.LENGTH_LONG).show();
                }
                return;
            }
            // if feedback is null, then it is a new feedback
            if (feedbackRepo.addFeedback(fb)) {
                Snackbar.make(v, "Feedback submitted", Snackbar.LENGTH_LONG).show();
                feedbackSubmitted();
            } else {
                Snackbar.make(v, R.string.fb_submit_error, Snackbar.LENGTH_LONG).show();
            }
        } else if (v.getId() == yesButton.getId()) {
            NavHostFragment.findNavController(FeedbackFragment.this)
                    .navigate(R.id.action_feedbackFragment_to_servicesRatingFragment);
        }
    }

    private void feedbackSubmitted() {
        submitFeedbackButton.setVisibility(View.GONE);
        feedbackTextInputLayout.setEnabled(false);
        overallRating.setIsIndicator(true);
        ratingTextView.setText(R.string.fb_thanks);
        servicesFeedbackLayout.setVisibility(View.VISIBLE);
    }

    /*
    * There are a few null-checks in this method but other than that,
    * it's pretty simple. It just sets the text of the text input
    * to whatever was saved in the database, displays a message
    * telling the user they're updating their feedback, and displays the rating bar.*/
    private void alreadyGivenFeedback() {
        Objects.requireNonNull(feedbackTextInputLayout.getEditText()).setText(feedback.getFeedback());
        overallRating.setRating(feedback.getOverall());
        ratingRequest.setText(R.string.rt_update_request);
        servicesFeedbackLayout.setVisibility(View.VISIBLE);
        Toast.makeText(getContext(), R.string.fb_update, Toast.LENGTH_LONG).show();
    }
}
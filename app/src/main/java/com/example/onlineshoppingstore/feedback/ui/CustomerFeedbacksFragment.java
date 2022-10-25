package com.example.onlineshoppingstore.feedback.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshoppingstore.R;
import com.example.onlineshoppingstore.feedback.adapter.GenAdapter;
import com.example.onlineshoppingstore.feedback.model.Feedback;
import com.example.onlineshoppingstore.feedback.model.ServicesRating;
import com.example.onlineshoppingstore.feedback.repo.DBFeedback;
import com.example.onlineshoppingstore.feedback.repo.DBServicesRating;
import com.example.onlineshoppingstore.feedback.repo.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;

public class CustomerFeedbacksFragment extends BottomSheetDialogFragment {
    private SessionManager sessionManager;
    private DBFeedback feedbackRepo;
    private DBServicesRating servicesRatingRepo;
    private List<Feedback> feedbacks;
    private GenAdapter<Feedback> feedbackAdapter;
    private RecyclerView feedbacksRecyclerView;
    private int count;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = SessionManager.getInstance(requireActivity().getApplication());
        feedbackRepo = DBFeedback.getInstance(getContext());
        feedbacks = feedbackRepo.getAllFeedback();
        servicesRatingRepo = DBServicesRating.getInstance(getContext());
        count = feedbacks.size();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_feedbacks, container, false);
        // set height of bottom sheet to 80% of screen height
        TextView feedbacksHeader = view.findViewById(R.id.feedbacksHeader);
        ChipGroup chipGroup = view.findViewById(R.id.serviceRatingsChipGroup);
        List<String> services = DBServicesRating.getRatingFields();
        for (String service : services) {
            Chip chip = new Chip(requireContext());
            double rating = servicesRatingRepo.getAverageServicesRatingByField(service);
            chip.setText(service.replace("_", " ") + " (" + rating + ")");
            chip.setChipIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_star));
            chip.setChipIconTintResource(R.color.primaryColor);
            chip.setClickable(false);
            chipGroup.addView(chip);
        }

        feedbacksHeader.setText(getString(R.string.feedback_count, count));
        feedbackAdapter = new GenAdapter<Feedback>(feedbacks, requireContext()) {
            @Override
            public GenAdapter<Feedback>.ViewHolder setViewHolder(ViewGroup parent) {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_feed, parent, false);
                return new GenAdapter<Feedback>.ViewHolder(view) {
                    @Override
                    public void bind(Feedback feedback) {
                        ImageView feedAuthorIcon = view.findViewById(R.id.feedAuthorIcon);
                        TextView authorName = view.findViewById(R.id.feedTitle);
                        TextView feedbackText = view.findViewById(R.id.feedBody);
                        TextView date = view.findViewById(R.id.feedDate);
                        RatingBar ratingBar = view.findViewById(R.id.feedRatingBar);

                        if (sessionManager.getUid().toString().equals(feedback.getAuthorId())) {
                            feedAuthorIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.primaryColor));
                        }
                        authorName.setText(feedback.getAuthorName());
                        feedbackText.setText(feedback.getFeedback());
                        date.setText(formatDateTime(feedback.getDate()));
                        ratingBar.setRating(feedback.getOverall());
                    }
                };
            }
        };
        feedbacksRecyclerView = view.findViewById(R.id.feedbacksRecyclerView);
        feedbacksRecyclerView.setAdapter(feedbackAdapter);
        feedbacksRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        return view;
    }

    private String formatDateTime(String date) {
        OffsetDateTime dateTime = OffsetDateTime.parse(date);
        return dateTime.getDayOfMonth() + "/" + dateTime.getMonthValue() + "/" + dateTime.getYear() + " " + dateTime.getHour() + ":" + dateTime.getMinute();
    }
}
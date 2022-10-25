package com.example.onlineshoppingstore.feedback.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshoppingstore.R;
import com.example.onlineshoppingstore.feedback.model.ServicesRating;
import com.example.onlineshoppingstore.feedback.repo.DBServicesRating;
import com.example.onlineshoppingstore.feedback.repo.SessionManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class ServicesRatingFragment extends BottomSheetDialogFragment implements View.OnClickListener {
    private SessionManager sessionManager;
    private DBServicesRating servicesRatingRepo;

    private TextView ratingMessage;
    private RatingBar pricingRatingBar;
    private RatingBar deliveryRatingBar;
    private RatingBar qualityRatingBar;
    private RatingBar productSelectionRatingBar;
    private RatingBar customerServiceRatingBar;
    private Button submitRatingButton;

    private ServicesRating servicesRating;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = SessionManager.getInstance(requireActivity().getApplication());
        servicesRatingRepo = DBServicesRating.getInstance(getContext());
        servicesRating = servicesRatingRepo.getServicesRatingByRaterId(sessionManager.getUid().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services_rating, container, false);

        ratingMessage = view.findViewById(R.id.ratingMessage);
        pricingRatingBar = view.findViewById(R.id.pricingRatingBar);
        deliveryRatingBar = view.findViewById(R.id.deliveryRatingBar);
        qualityRatingBar = view.findViewById(R.id.qualityRatingBar);
        productSelectionRatingBar = view.findViewById(R.id.productSelectionRatingBar);
        customerServiceRatingBar = view.findViewById(R.id.customerServiceRatingBar);
        submitRatingButton = view.findViewById(R.id.submitRatingButton);

        pricingRatingBar.setRating(1);
        deliveryRatingBar.setRating(1);
        qualityRatingBar.setRating(1);
        productSelectionRatingBar.setRating(1);
        customerServiceRatingBar.setRating(1);
        if (!servicesRating.isNull()) {
            hasUserRated();
        }

        submitRatingButton.setOnClickListener(this);

        // nothing interesting here :-)
        pricingRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            int ratingValue = (int) rating;
            if (ratingValue == 0) {
                ratingBar.setRating(1);
            }
            switch (ratingValue) {
                case 1:
                    ratingMessage.setText(R.string.fb_p1);
                    break;
                case 2:
                    ratingMessage.setText(R.string.fb_p2);
                    break;
                case 3:
                    ratingMessage.setText(R.string.fb_p3);
                    break;
                case 4:
                    ratingMessage.setText(R.string.fb_p4);
                    break;
                case 5:
                    ratingMessage.setText(R.string.fb_p5);
                    break;

                default:
                    ratingMessage.setText(R.string.default_rating_message);
                    break;
            }
        });

        deliveryRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            int ratingValue = (int) rating;
            if (ratingValue == 0) {
                ratingBar.setRating(1);
            }
            switch (ratingValue) {
                case 1:
                    ratingMessage.setText(R.string.fb_d1);
                    break;
                case 2:
                    ratingMessage.setText(R.string.fb_d2);
                    break;
                case 3:
                    ratingMessage.setText(R.string.fb_d3);
                    break;
                case 4:
                    ratingMessage.setText(R.string.fb_d4);
                    break;
                case 5:
                    ratingMessage.setText(R.string.fb_d5);
                    break;

                default:
                    ratingMessage.setText(R.string.default_rating_message);
                    break;
            }
        });

        qualityRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            int ratingValue = (int) rating;
            if (ratingValue == 0) {
                ratingBar.setRating(1);
            }
            switch (ratingValue) {
                case 1:
                    ratingMessage.setText(R.string.fb_q1);
                    break;
                case 2:
                    ratingMessage.setText(R.string.fb_q2);
                    break;
                case 3:
                    ratingMessage.setText(R.string.fb_q3);
                    break;
                case 4:
                    ratingMessage.setText(R.string.fb_q4);
                    break;
                case 5:
                    ratingMessage.setText(R.string.fb_q5);
                    break;

                default:
                    ratingMessage.setText(R.string.default_rating_message);
                    break;
            }
        });

        productSelectionRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            int ratingValue = (int) rating;
            if (ratingValue == 0) {
                ratingBar.setRating(1);
            }
            switch (ratingValue) {
                case 1:
                    ratingMessage.setText(R.string.fb_ps1);
                    break;
                case 2:
                    ratingMessage.setText(R.string.fb_ps2);
                    break;
                case 3:
                    ratingMessage.setText(R.string.fb_ps3);
                    break;
                case 4:
                    ratingMessage.setText(R.string.fb_ps4);
                    break;
                case 5:
                    ratingMessage.setText(R.string.fb_ps5);
                    break;

                default:
                    ratingMessage.setText(R.string.default_rating_message);
                    break;
            }
        });

        customerServiceRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            int ratingValue = (int) rating;
            if (ratingValue == 0) {
                ratingBar.setRating(1);
            }
            switch (ratingValue) {
                case 1:
                    ratingMessage.setText(R.string.fb_cs1);
                    break;
                case 2:
                    ratingMessage.setText(R.string.fb_cs2);
                    break;
                case 3:
                    ratingMessage.setText(R.string.fb_cs3);
                    break;
                case 4:
                    ratingMessage.setText(R.string.fb_cs4);
                    break;
                case 5:
                    ratingMessage.setText(R.string.fb_cs5);
                    break;

                default:
                    ratingMessage.setText(R.string.default_rating_message);
                    break;
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == submitRatingButton.getId()) {
            int pricingRating = (int) pricingRatingBar.getRating();
            int deliveryRating = (int) deliveryRatingBar.getRating();
            int qualityRating = (int) qualityRatingBar.getRating();
            int productSelectionRating = (int) productSelectionRatingBar.getRating();
            int customerServiceRating = (int) customerServiceRatingBar.getRating();

            ServicesRating sr = new ServicesRating(sessionManager.getUid().toString(), pricingRating, qualityRating, deliveryRating, productSelectionRating, customerServiceRating);
            if (!servicesRating.isNull()) {
                sr.setId(servicesRating.getId());
                sr.setRaterId(servicesRating.getRaterId());

                if (servicesRatingRepo.updateServicesRating(sr)) {
                    Toast.makeText(getContext(), "Ratings updated", Toast.LENGTH_SHORT).show();
                    ratingSubmitted();
                } else {
                    Toast.makeText(getContext(), "Ratings update failed", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            if (servicesRatingRepo.addServicesRating(sr)) {
                Toast.makeText(getContext(), "Ratings Submitted", Toast.LENGTH_LONG).show();
                ratingSubmitted();
            } else {
                Toast.makeText(getContext(), "Ratings Failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    // updates the UI to reflect the fact that the user has submitted their ratings
    private void ratingSubmitted() {
        submitRatingButton.setVisibility(View.GONE);
        ratingMessage.setText(R.string.rating_submitted);
        pricingRatingBar.setIsIndicator(true);
        deliveryRatingBar.setIsIndicator(true);
        qualityRatingBar.setIsIndicator(true);
        productSelectionRatingBar.setIsIndicator(true);
        customerServiceRatingBar.setIsIndicator(true);
    }

    // updates UI to reflect that the user has already submitted a rating
    private void hasUserRated() {
        submitRatingButton.setText(R.string.rt_update);
        ratingMessage.setText(R.string.rating_submitted);
        pricingRatingBar.setRating(servicesRating.getPricing());
        deliveryRatingBar.setRating(servicesRating.getDelivery());
        qualityRatingBar.setRating(servicesRating.getQuality());
        productSelectionRatingBar.setRating(servicesRating.getProductSelection());
        customerServiceRatingBar.setRating(servicesRating.getCustomerService());
    }
}
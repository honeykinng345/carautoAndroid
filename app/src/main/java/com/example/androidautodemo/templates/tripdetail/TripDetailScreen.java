package com.example.androidautodemo.templates.tripdetail;

import static androidx.car.app.CarToast.LENGTH_LONG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.car.app.CarContext;
import androidx.car.app.CarToast;
import androidx.car.app.Screen;
import androidx.car.app.model.CarIcon;
import androidx.car.app.model.ItemList;
import androidx.car.app.model.ListTemplate;
import androidx.car.app.model.Row;
import androidx.car.app.model.Template;
import androidx.core.graphics.drawable.IconCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.androidautodemo.CarScreen;
import com.example.androidautodemo.R;
import com.example.androidautodemo.nevigationtemplate.common.MyTrip;

public final class TripDetailScreen extends Screen implements DefaultLifecycleObserver {
    private static final int MAX_LIST_ITEMS = 100;
    private CarIcon ivTripStatus, ivPickUpNavigate, ivDropOffNavigate, ivAirPort, ivAmbulatory, ivWheelChair;
    private final MyTrip currentTrip;

    public TripDetailScreen(@NonNull CarContext carContext, MyTrip tripList) {
        super(carContext);
        this.currentTrip = tripList;
        getLifecycle().addObserver(this);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onCreate(owner);
        /*Resources resources = getCarContext().getResources();
        Bitmap statusBitmap = BitmapFactory.decodeResource(resources, R.drawable.status_default);
        IconCompat tripStatusIcon = IconCompat.createWithBitmap(statusBitmap);*/

        CarIcon appIcon = new CarIcon.Builder(CarIcon.APP_ICON).build(); // Replace with the actual image resource

        ivTripStatus = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.status_default)).build();
        ivAirPort = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.airport_1)).build();
        ivAmbulatory = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.ambulatory_1)).build();
        ivWheelChair = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.paratransit_1)).build();
        ivPickUpNavigate = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.pupin)).build();
        ivDropOffNavigate = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.dopin)).build();
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        // Concatenate the necessary information for each row
        String basicInfo = currentTrip.getPhoneNumber()
                + "\n" + currentTrip.getConfirmationNumber()
                + "\n" + currentTrip.getServiceId()
                + "\n" + currentTrip.getPickupTime()
                + "\nPassengers# " + currentTrip.getAmbulatoryPassengerCount()
                + "\nParatransit# " + currentTrip.getParatransitCount();
        String personName = "<font color='#f4f08c'>" + currentTrip.getPersonName() + "</font>"; // Example: Red color

        String pickUpRemarks = currentTrip.getPickUpUnit()
                + "\n" + currentTrip.getPickUpAddress()
                + "\n" + currentTrip.getPickUpRemarks();
        String dropOffRemarks = currentTrip.getDropOffUnit()
                + "\n" + currentTrip.getDropOffAddress()
                + "\n" + currentTrip.getDropOffRemarks();

        ItemList.Builder itemListBuilder = new ItemList.Builder()
                .addItem(createRow(ivTripStatus, currentTrip.getPersonName(), basicInfo))
                .addItem(createRow(ivPickUpNavigate, currentTrip.getDropOffPoiName(), pickUpRemarks))
                .addItem(createRow(ivDropOffNavigate, currentTrip.getDropOffPoiName(), dropOffRemarks))
                .addItem(createPaymentAndFundingRow())
                //.addItem(createRow(null, "Estimate", "$" + currentTrip.getEstimatedCost() + " and " + currentTrip.getEstimatedDistance() + "Mi"))
                .addItem(createEstimatesAndCopay());

        return new ListTemplate.Builder()
                .setSingleList(itemListBuilder.build())
                .setTitle("Trip Details")
                .build();
    }

    private Row createRow(@Nullable CarIcon icon, String title, String subTitle) {
        Row.Builder rowBuilder = new Row.Builder()
                .setTitle(title != null ? title : "Default Title")  // Set a non-null title here
                //.setImage(icon)
                .addText(subTitle)
                .setOnClickListener(() -> {
                    // Handle click event if needed
                });

        if (icon != null) {
            rowBuilder.setImage(icon);
        }

        return rowBuilder.build();
    }

    private Row createPaymentAndFundingRow() {
        String paymentType = "Payment Type: " + currentTrip.getPaymentType();
        String fundingSource = "Funding Source: " + currentTrip.getFundingSource();

        Row.Builder rowBuilder = new Row.Builder()
                .setTitle("Payment Type & Funding Source")
                .addText(paymentType)
                .addText(fundingSource);
        return rowBuilder.build();
    }

    private Row createEstimatesAndCopay() {
        String estimateCost = "$" + currentTrip.getEstimatedCost();
        String estimatedDistance = " and " + currentTrip.getEstimatedCost() + "Mi";
        String copayValue = "Copay: " + currentTrip.getCopay();

        Row.Builder rowBuilder = new Row.Builder()
                .setTitle("Estimates & Copay")
                .addText(estimateCost + estimatedDistance)
                .addText(copayValue);
        return rowBuilder.build();
    }

    private IconCompat getTripStatusIcon(MyTrip trip) {
        // Implement logic to get the appropriate icon based on trip status
        // Example: return IconCompat.createWithResource(getCarContext(), R.drawable.ic_trip_status);
        return null;
    }

    // Replace this method with your actual logic to get trip status image resource ID
    private int getTripStatusImageResourceId(String tripStatus) {
        // Replace with actual logic
        return R.drawable.banana;
    }

    private void onTripClick(MyTrip trip) {
        // Handle trip click
        // Example: show details, navigate to another screen, etc.
    }

    private void onClick(String text) {
        CarToast.makeText(getCarContext(), text, LENGTH_LONG).show();
    }

    private void onBackClicked() {
        CarScreen.screenManager.pop();
    }
}

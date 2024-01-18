package com.example.androidautodemo.templates.tripdetail;

import static androidx.car.app.CarToast.LENGTH_LONG;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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
    @Nullable
    private IconCompat mImage, mPaneImage;
    @Nullable
    private IconCompat mIcon;
    private final MyTrip currentTrip;

    public TripDetailScreen(@NonNull CarContext carContext, MyTrip tripList) {
        super(carContext);
        this.currentTrip = tripList;
        getLifecycle().addObserver(this);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onCreate(owner);
        Resources resources = getCarContext().getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_image_square);
        mImage = IconCompat.createWithBitmap(bitmap);
        mIcon = IconCompat.createWithResource(getCarContext(), R.drawable.ic_fastfood_white_48dp);

        Bitmap patioBitmap = BitmapFactory.decodeResource(resources, R.drawable.patio);
        mPaneImage = IconCompat.createWithBitmap(patioBitmap);
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        CarIcon tripStatusIcon = new CarIcon.Builder(CarIcon.APP_ICON).build(); // Replace with the actual image resource
        CarIcon passengerIcon = new CarIcon.Builder(CarIcon.ALERT).build(); // Replace with the actual image resource

        // Concatenate the necessary information for each row
        String passengerCountText = "Passenger Count: " + currentTrip.getAmbulatoryPassengerCount();
        String confirmationNoText = "Confirmation No: " + currentTrip.getConfirmationNumber();
        String pickupTimeText = "Pickup Time: " + currentTrip.getPickupTime();
        String pickupZoneText = "Pickup Zone: " + currentTrip.getPickUpZone();
        String estimatedCostText = "Estimated Cost: " + currentTrip.getEstimatedCost();
        String dropDateText = "Drop Date: " + currentTrip.getDropOffDate();
        String dropZoneText = "Drop Zone: " + currentTrip.getDropZone();
        String milesText = "Miles: " + currentTrip.getEstimatedDistance();

        ItemList.Builder itemListBuilder = new ItemList.Builder()
                .addItem(createRow(tripStatusIcon, currentTrip.getPersonName()))
                .addItem(createRow(tripStatusIcon, passengerCountText))
                .addItem(createRow(tripStatusIcon, confirmationNoText))
                .addItem(createRow(tripStatusIcon, pickupTimeText))
                .addItem(createRow(tripStatusIcon, pickupZoneText))
                .addItem(createRow(tripStatusIcon, estimatedCostText))
                .addItem(createRow(tripStatusIcon, dropDateText))
                .addItem(createRow(tripStatusIcon, dropZoneText))
                .addItem(createRow(tripStatusIcon, milesText));

        return new ListTemplate.Builder()
                .setSingleList(itemListBuilder.build())
                .setTitle("Trip Details")
                .build();
    }

    private Row createRow(CarIcon icon, String text) {
        Row.Builder rowBuilder = new Row.Builder()
                .setTitle("Title")  // Set a non-null title here
                .setImage(icon)
                .addText(text)
                .setOnClickListener(() -> {
                    // Handle click event if needed
                });

        return rowBuilder.build();
    }

    /*private Row createTripRow(MyTrip trip) {
        return new Row.Builder()
                .setOnClickListener(ParkedOnlyOnClickListener.create(() -> onTripClick(trip)))
                .setImage(new CarIcon.Builder(mIcon).build(), Row.IMAGE_TYPE_SMALL)
                .setTitle(trip.getPersonName())
                .setImage(new CarIcon.Builder(mIcon).build(), Row.IMAGE_TYPE_SMALL)
                .addText("Details: " + trip.getAmbulatoryPassengerCount()) // Add relevant details
                .build();
    }*/

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

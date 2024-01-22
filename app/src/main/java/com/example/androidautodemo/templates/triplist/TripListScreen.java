package com.example.androidautodemo.templates.triplist;

import static androidx.car.app.CarToast.LENGTH_LONG;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.car.app.CarContext;
import androidx.car.app.CarToast;
import androidx.car.app.Screen;
import androidx.car.app.model.Action;
import androidx.car.app.model.CarIcon;
import androidx.car.app.model.CarText;
import androidx.car.app.model.ItemList;
import androidx.car.app.model.ListTemplate;
import androidx.car.app.model.ParkedOnlyOnClickListener;
import androidx.car.app.model.Row;
import androidx.car.app.model.Template;
import androidx.core.graphics.drawable.IconCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.androidautodemo.CarScreen;
import com.example.androidautodemo.R;
import com.example.androidautodemo.nevigationtemplate.common.MyTrip;
import com.example.androidautodemo.templates.tripdetail.TripDetailScreen;

import java.util.List;

/**
 * Creates a screen that demonstrates usage of the full screen {@link ListTemplate} to display a
 * full-screen list.
 */
public final class TripListScreen extends Screen implements DefaultLifecycleObserver {
    private static final int MAX_LIST_ITEMS = 100;
    @Nullable
    private IconCompat mImage;
    @Nullable
    private IconCompat mIcon;
    private CarIcon ivTripStatus;
    private final List<MyTrip> tripList;

    public TripListScreen(@NonNull CarContext carContext, List<MyTrip> tripList) {
        super(carContext);
        this.tripList = tripList;
        getLifecycle().addObserver(this);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onCreate(owner);
        Resources resources = getCarContext().getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_image_square);
        mImage = IconCompat.createWithBitmap(bitmap);
        mIcon = IconCompat.createWithResource(getCarContext(), R.drawable.ic_fastfood_white_48dp);
        ivTripStatus = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.status_default)).build();
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        CarIcon passengerIcon = new CarIcon.Builder(CarIcon.ALERT).build(); // Replace with the actual image resource
        ItemList.Builder listBuilder = new ItemList.Builder();
        for (MyTrip trip : tripList) {
            // Concatenate the necessary information for each row
            String rowText = " Passenger#" + trip.getAmbulatoryPassengerCount()
                    + "  Conf#" + trip.getConfirmationNumber()
                    + "\n " + trip.getPickupTime()
                    + "       | " + trip.getPickUpZone()
                    + "       " + trip.getEstimatedDistance() + "mi"
                    + "\n" + trip.getPickupDate()
                    + " | " + trip.getDropZone();

            Row.Builder rowBuilder = new Row.Builder()
                    .setImage(ivTripStatus)
                    .setTitle(trip.getPersonName())
                    .addText(new CarText.Builder(rowText).build())
                    .setOnClickListener(ParkedOnlyOnClickListener.create(() -> onTripClick(trip)));
                    /*.setOnClickListener(ParkedOnlyOnClickListener.create(() -> CarToast.makeText(
                                    getCarContext(),
                                    "Trip details for " + trip.getPersonName(),
                                    CarToast.LENGTH_SHORT)
                            .show()));*/

            listBuilder.addItem(rowBuilder.build());

            /*Row.Builder rowBuilder = new Row.Builder()
                    .setImage(new CarIcon.Builder(mIcon).build(), Row.IMAGE_TYPE_SMALL)
                    .setTitle(trip.getPersonName()) // Assuming getPUPerson() exists in your Trip class
                    .addText(trip.getPickupTime())
                    .addText(trip.getPickUpZone())
                    .setOnClickListener(ParkedOnlyOnClickListener.create(() -> onTripClick(trip)));*/
                    /*.setOnParkedOnlyClickListener(
                            () -> CarToast.makeText(
                                            getCarContext(),
                                            "Trip details for " + trip.getPersonName(),
                                            CarToast.LENGTH_SHORT)
                                    .show());
            listBuilder.addItem(rowBuilder.build());*/
        }
        return new ListTemplate.Builder()
                .setSingleList(listBuilder.build())
                .setTitle("Trip List")
                .setHeaderAction(Action.BACK)
                .build();
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
        CarScreen.screenManager
                .push(new TripDetailScreen(CarScreen.carContextThis, trip));
        // Handle trip click
        // Example: show details, navigate to another screen, etc.
    }

    private void onClick(String text) {
        CarToast.makeText(getCarContext(), text, LENGTH_LONG).show();
    }
}

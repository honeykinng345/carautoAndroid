package com.example.androidautodemo.templates.tripdetail;

import static androidx.car.app.CarToast.LENGTH_LONG;
import static androidx.car.app.model.CarColor.YELLOW;

import android.text.SpannableString;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.car.app.CarContext;
import androidx.car.app.CarToast;
import androidx.car.app.Screen;
import androidx.car.app.model.Action;
import androidx.car.app.model.ActionStrip;
import androidx.car.app.model.CarColor;
import androidx.car.app.model.CarIcon;
import androidx.car.app.model.ItemList;
import androidx.car.app.model.ListTemplate;
import androidx.car.app.model.Row;
import androidx.car.app.model.SectionedItemList;
import androidx.car.app.model.Template;
import androidx.core.graphics.drawable.IconCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.androidautodemo.CarScreen;
import com.example.androidautodemo.R;
import com.example.androidautodemo.nevigationtemplate.common.MyTrip;
import com.example.androidautodemo.nevigationtemplate.common.Utils;

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

        if (currentTrip.getAmbulatoryPassengerCount() != 0) {
            ivAmbulatory = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.ambulatory_2)).build();
        }
        if (currentTrip.getParatransitCount() != 0) {
            ivWheelChair = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.paratransit_2)).build();
        }

        if (currentTrip.getPickUpAddress().toLowerCase().contains("airport") || currentTrip.getDropOffAddress().toLowerCase().contains("airport")) {
            ivAirPort = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.airport_2b)).build();
        }
    }

    @NonNull
    private Row buildRow(String label) {
        return new Row.Builder()
                .setTitle(label)
                .setOnClickListener(() -> {
                    if (label.equalsIgnoreCase(getCarContext().getString(R.string.trip_notes))) {
                        onClick("Trip Notes clicked");
                    } else if (label.equalsIgnoreCase(getCarContext().getString(R.string.next_trip))) {
                        onClick("Show Next Trip");
                    } else if (label.equalsIgnoreCase(getCarContext().getString(R.string.previous_trip))) {
                        onClick("Show Previous Trip");
                    } else if (label.equalsIgnoreCase(getCarContext().getString(R.string.trip_at_location))) {
                        onClick("At Location Performed");
                    } else if (label.equalsIgnoreCase(getCarContext().getString(R.string.trip_pick_up))) {
                        onClick("Picked Up Performed");
                    } else if (label.equalsIgnoreCase(getCarContext().getString(R.string.trip_no_show))) {
                        onClick("No Show requested");
                    } else if (label.equalsIgnoreCase(getCarContext().getString(R.string.trip_call_out))) {
                        onClick("Call Out performed");
                    }
                })
                .build();
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        /*NavigationTemplate.Builder builder = new NavigationTemplate.Builder();
        builder.setBackgroundColor(CarColor.SECONDARY);
        // Set the action strip.
        ActionStrip.Builder actionStripBuilder = new ActionStrip.Builder();
        actionStripBuilder.addAction(
                new Action.Builder()
                        .setIcon(
                                new CarIcon.Builder(
                                        IconCompat.createWithResource(
                                                getCarContext(),
                                                R.drawable.pupin))
                                        .build())
                        .setOnClickListener(this::callOut)
                        .build());
        actionStripBuilder.addAction(new Action.Builder()
                .setIcon(
                        new CarIcon.Builder(
                                IconCompat.createWithResource(
                                        getCarContext(), R.drawable.banana))
                                .build())
                .setOnClickListener(
                        () -> {
                            onClick("Settings");
                        })
                .build());
        actionStripBuilder.addAction(
                new Action.Builder()
                        .setTitle("Voice")
                        .setIcon(new CarIcon.Builder(
                                IconCompat.createWithResource(getCarContext(),
                                        R.drawable.dopin)).build()).setOnClickListener(
                                this::atLocation)
                        .build());
        actionStripBuilder.addAction(
                new Action.Builder()
                        .setTitle("Stop")
                        .setOnClickListener(this::atLocation)
                        .build());
        builder.setActionStrip(actionStripBuilder.build());*/

        // Set the action strip.
        ActionStrip myActions = new ActionStrip.Builder()
                .addAction(
                        new Action.Builder()
                                .setOnClickListener(this::atLocation)
                                .setTitle("AtLocation")
                                .build())
                .addAction(
                        new Action.Builder()
                                .setOnClickListener(() -> {
                                    onClick("No Show");
                                })
                                //.setTitle("No Show")
                                .setIcon(
                                        new CarIcon.Builder(
                                                IconCompat.createWithResource(
                                                        getCarContext(),
                                                        R.drawable.status_no_show_req))
                                                .setTint(CarColor.SECONDARY)
                                                .build())
                                .build())
                /*.addAction(
                        new Action.Builder()
                                .setOnClickListener(this::callOut)
                                .setIcon(
                                        new CarIcon.Builder(
                                                IconCompat.createWithResource(
                                                        getCarContext(),
                                                        R.drawable.pupin))
                                                .build())
                                .build())*/
                .build();

        // Concatenate the necessary information for each row
        String basicInfo = currentTrip.getPhoneNumber()
                + "\n" + currentTrip.getConfirmationNumber()
                + "\n" + currentTrip.getServiceId()
                + "\n" + currentTrip.getPickupTime()
                + "\nAmbulatory# " + currentTrip.getAmbulatoryPassengerCount()
                + "\nWheel Chair# " + currentTrip.getParatransitCount();

        String pickUpRemarks = currentTrip.getPickUpUnit()
                + "\n" + currentTrip.getPickUpAddress()
                + "\n" + currentTrip.getPickUpRemarks();
        String dropOffRemarks = currentTrip.getDropOffUnit()
                + "\n" + currentTrip.getDropOffAddress()
                + "\n" + currentTrip.getDropOffRemarks();

        ItemList.Builder itemListBuilder = new ItemList.Builder()
                .addItem(createRow(ivTripStatus, currentTrip.getPersonName(), basicInfo))
                //.addItem(createRow(ivAirPort, " ", ""))
                //.addItem(createRow(ivAmbulatory, String.valueOf(currentTrip.getAmbulatoryPassengerCount()), ""))
                //.addItem(createRow(ivWheelChair, String.valueOf(currentTrip.getParatransitCount()), ""))
                .addItem(createRow(ivPickUpNavigate, currentTrip.getDropOffPoiName(), pickUpRemarks))
                .addItem(createRow(ivDropOffNavigate, currentTrip.getDropOffPoiName(), dropOffRemarks))
                .addItem(createPaymentAndFundingRow())
                //.addItem(createRow(null, "Estimate", "$" + currentTrip.getEstimatedCost() + " and " + currentTrip.getEstimatedDistance() + "Mi"))
                .addItem(createEstimatesAndCopay());

        // Add Trip Actions
        ItemList.Builder tripActionsBuilder = new ItemList.Builder();
        tripActionsBuilder.addItem(buildRow(getCarContext().getString(R.string.trip_notes)));
        tripActionsBuilder.addItem(buildRow(getCarContext().getString(R.string.next_trip)));
        tripActionsBuilder.addItem(buildRow(getCarContext().getString(R.string.previous_trip)));
        tripActionsBuilder.addItem(buildRow(getCarContext().getString(R.string.trip_at_location)));
        tripActionsBuilder.addItem(buildRow(getCarContext().getString(R.string.trip_no_show)));
        tripActionsBuilder.addItem(buildRow(getCarContext().getString(R.string.trip_call_out)));

        return new ListTemplate.Builder()
                .addSectionedList(SectionedItemList.create(
                        itemListBuilder.build(),
                        " "))
                //.setSingleList(itemListBuilder.build())
                .setTitle("Trip Details")
                .setActionStrip(myActions)
                /*.setActionStrip( //Todo for single Action
                        new ActionStrip.Builder()
                                .addAction(
                                        new Action.Builder()
                                                .setTitle("AtLocation")
                                                .setOnClickListener(
                                                        () -> {
                                                            onClick("AtLocation");
                                                            invalidate();
                                                        })
                                                .build())
                                .build())*/
                .addSectionedList(SectionedItemList.create(
                        tripActionsBuilder.build(),
                        getCarContext().getString(R.string.trip_trip_actions)))
                .setHeaderAction(Action.BACK)
                .build();
    }

    private Row createRow(@Nullable CarIcon icon, String title, String subTitle) {
        Row.Builder rowBuilder = new Row.Builder()
                .setTitle(title != null ? title : "Default Title")  // Set a non-null title here
                .setOnClickListener(() -> {
                    // Handle click event if needed
                });

        if (!subTitle.isEmpty()) {
            rowBuilder.addText(subTitle);
        }

        if (icon != null) {
            rowBuilder.setImage(icon);
        }
        return rowBuilder.build();
    }

    private Row createPaymentAndFundingRow() {
        String paymentType = "Payment Type: " + currentTrip.getPaymentType();
        String fundingSource = "Funding Source: " + currentTrip.getFundingSource();

        Row.Builder rowBuilder = new Row.Builder()
                /*.setTitle(Utils.colorize(
                        getCarContext().getString(R.string.sign_in_with_google_title),
                        CarColor.createCustom(Color.BLACK, Color.BLACK), 0, 19))*/
                .setTitle("Payment Type & Funding Source")
                .addText(getColoredString(paymentType, true))
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

    private static CharSequence getColoredString(String str, boolean isEnabled) {
        if (isEnabled && !str.isEmpty()) {
            SpannableString ss = new SpannableString(str);
            Utils.colorize(ss, YELLOW, 0, str.length());
            return ss;
        }
        return str;
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

    private void atLocation() {
        CarToast.makeText(getCarContext(), "At location performed", LENGTH_LONG).show();
    }

    private void callOut() {
        CarToast.makeText(getCarContext(), "Call out performed", LENGTH_LONG).show();
    }

    private void onClick(String text) {
        CarToast.makeText(getCarContext(), text, LENGTH_LONG).show();
    }

    private void onBackClicked() {
        CarScreen.screenManager.pop();
    }
}

package com.example.androidautodemo.templates.tripdetail;

import static androidx.car.app.CarToast.LENGTH_LONG;
import static androidx.car.app.model.CarColor.YELLOW;

import android.graphics.Color;
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
import com.example.androidautodemo.templates.map.PlaceListNavigationTemplateDemoScreen;
import com.example.androidautodemo.templates.map.PlaceListTemplateBrowseDemoScreen;
import com.example.androidautodemo.templates.softmeter.FloatingSoftmeterOldScreen;

import java.util.Locale;

public final class TripDetailScreen extends Screen implements DefaultLifecycleObserver {
    private static final int MAX_LIST_ITEMS = 100;
    private CarIcon ivTripStatus, ivPickUpNavigate, ivDropOffNavigate, ivSoftmeter;
    private CarColor noTint;
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

        noTint = CarColor.createCustom(Color.TRANSPARENT, Color.TRANSPARENT);
        ivSoftmeter = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.softmeter)).build();
        ivTripStatus = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.status_default)).build();
        ivPickUpNavigate = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.pupin)).build();
        ivDropOffNavigate = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.dopin)).build();
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
        ActionStrip myActions = new ActionStrip.Builder()
                .addAction(new Action.Builder()
                        .setOnClickListener(this::tripAction)
                        .setTitle(currentTrip.getTripStatus().equalsIgnoreCase(getCarContext().getString(R.string.trip_picked_up)) ? getCarContext().getString(R.string.complete_trip) : currentTrip.getTripStatus())
                        .build())
                .addAction(new Action.Builder()
                        .setIcon(new CarIcon.Builder(ivSoftmeter)
                                .setTint(noTint)
                                .build())
                        .setOnClickListener(() -> CarScreen.screenManager.push(new FloatingSoftmeterOldScreen(CarScreen.carContextThis)))
                        .build())
                .build();

        // Concatenate the necessary information for each row
        String basicInfo = currentTrip.getConfirmationNumber() + "   " + currentTrip.getServiceId() + "   " + currentTrip.getPickupTime()
                + "\nAmbulatory# " + currentTrip.getAmbulatoryPassengerCount() + "    Wheel Chair# " + currentTrip.getParatransitCount();

        String pickUpRemarks = currentTrip.getPickUpUnit() + "   " + currentTrip.getPickUpAddress() + "\n" + currentTrip.getPickUpRemarks();
        String dropOffRemarks = currentTrip.getDropOffUnit() + "   " + currentTrip.getDropOffAddress() + "\n" + currentTrip.getDropOffRemarks();
        String pickupPoiName = currentTrip.getPickUpPoiName();
        String dropPoiName = currentTrip.getDropOffPoiName();

        if (pickupPoiName.isEmpty() || pickupPoiName.equalsIgnoreCase("poi")) {
            pickupPoiName = "";
            pickUpRemarks = currentTrip.getPickUpRemarks();
        }

        if (dropPoiName.isEmpty() || dropPoiName.equalsIgnoreCase("poi")) {
            dropPoiName = "";
            dropOffRemarks = currentTrip.getDropOffRemarks();
        }

        ItemList.Builder itemListBuilder = new ItemList.Builder()
                .addItem(createRow(ivTripStatus, currentTrip.getPersonName() + " (" + currentTrip.getPhoneNumber() + ")", basicInfo))
                .addItem(createRow(ivPickUpNavigate, pickupPoiName.isEmpty() ? currentTrip.getPickUpAddress() : currentTrip.getPickUpPoiName(), pickUpRemarks))
                .addItem(createRow(ivDropOffNavigate, dropPoiName.isEmpty() ? currentTrip.getDropOffAddress() : currentTrip.getDropOffPoiName(), dropOffRemarks))
                .addItem(createPaymentAndFundingRow())
                //.addItem(createRow(null, "Estimate", "$" + currentTrip.getEstimatedCost() + " and " + currentTrip.getEstimatedDistance() + "Mi"))
                .addItem(createEstimatesAndCopay());

        // Add Trip Actions
        ItemList.Builder tripActionsBuilder = new ItemList.Builder();
        //tripActionsBuilder.addItem(buildRow(getCarContext().getString(R.string.trip_notes)));

        if (!currentTrip.getTripStatus().equalsIgnoreCase(getCarContext().getString(R.string.trip_picked_up))) {
            //tripActionsBuilder.addItem(buildRow(currentTrip.getTripStatus()));
            tripActionsBuilder.addItem(buildRow(getCarContext().getString(R.string.trip_no_show)));
            tripActionsBuilder.addItem(buildRow(getCarContext().getString(R.string.trip_call_out)));
        }
        if (!currentTrip.getManifestNumber().isEmpty()) {
            tripActionsBuilder.addItem(buildRow(getCarContext().getString(R.string.previous_trip)));
            tripActionsBuilder.addItem(buildRow(getCarContext().getString(R.string.next_trip)));
        }

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
        rowBuilder.setOnClickListener(() -> {
            /*if (title != null && !(title.equalsIgnoreCase(currentTrip.getPickUpPoiName()) || title.equalsIgnoreCase(currentTrip.getPickUpAddress()))) {
                onClick("Navigate to Map");
                getScreenManager()
                        .push(
                                new NavigationTemplateDemoScreen(
                                        getCarContext()));
            } else */
            if (title != null && (title.equalsIgnoreCase(currentTrip.getDropOffPoiName()) || title.equalsIgnoreCase(currentTrip.getDropOffAddress()))) {
                onClick("Navigate to Map for Drop-OFF");
                CarScreen.screenManager.push(new PlaceListTemplateBrowseDemoScreen(CarScreen.carContextThis));
                //CarScreen.screenManager.push(new PlaceListNavigationTemplateDemoScreen(CarScreen.carContextThis));
            }
        });
        return rowBuilder.build();
    }

    private Row createPaymentAndFundingRow() {
        String typeAndSource = currentTrip.getPaymentType() + "                                   " + currentTrip.getFundingSource();
        Row.Builder rowBuilder = new Row.Builder()
                .setTitle("Payment Type            Funding Source")
                .addText(getColoredString(typeAndSource, YELLOW, true));
        return rowBuilder.build();
    }

    private Row createEstimatesAndCopay() {
        String estimateCostAndDistance = "$" + currentTrip.getEstimatedCost() + " and " + currentTrip.getEstimatedCost() + "Mi";
        String copayLabel = "";
        String copayValue = "";
        if (currentTrip.getCopay() > 0) {
            copayLabel = "                    Copay";
            copayValue = "           " + String.format(Locale.US, "%.2f", currentTrip.getCopay());
        }
        Row.Builder rowBuilder = new Row.Builder()
                .setTitle("Estimates" + copayLabel)
                //.addText(estimateCostAndDistance+ "          "+currentTrip.getCopay())
                .addText(getColoredString(estimateCostAndDistance + copayValue, YELLOW, true));
        return rowBuilder.build();
    }

    public static CharSequence getColoredString(String str, CarColor textColor, boolean isEnabled) {
        if (isEnabled && !str.isEmpty()) {
            SpannableString ss = new SpannableString(str);
            Utils.colorize(ss, textColor, 0, str.length());
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

    private void tripAction() {
        if (currentTrip.getTripStatus().equalsIgnoreCase(getCarContext().getString(R.string.trip_irtpu))) {
            CarToast.makeText(getCarContext(), "IRTPU performed", LENGTH_LONG).show();
        } else if (currentTrip.getTripStatus().equalsIgnoreCase(getCarContext().getString(R.string.trip_at_location))) {
            CarToast.makeText(getCarContext(), "At location performed", LENGTH_LONG).show();
        } else if (currentTrip.getTripStatus().equalsIgnoreCase(getCarContext().getString(R.string.trip_pick_up))) {
            CarToast.makeText(getCarContext(), "Pick-Up performed", LENGTH_LONG).show();
        } else if (currentTrip.getTripStatus().equalsIgnoreCase(getCarContext().getString(R.string.trip_picked_up))) {
            CarToast.makeText(getCarContext(), "Move to payment screen performed", LENGTH_LONG).show();
        }
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

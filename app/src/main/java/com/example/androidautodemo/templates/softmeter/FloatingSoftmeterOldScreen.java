
package com.example.androidautodemo.templates.softmeter;

import static androidx.car.app.CarToast.LENGTH_LONG;
import static androidx.car.app.CarToast.LENGTH_SHORT;
import static androidx.car.app.model.Action.FLAG_PRIMARY;
import static androidx.car.app.model.CarColor.RED;
import static com.example.androidautodemo.templates.tripdetail.TripDetailScreen.getColoredString;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.car.app.CarContext;
import androidx.car.app.CarToast;
import androidx.car.app.Screen;
import androidx.car.app.model.Action;
import androidx.car.app.model.ActionStrip;
import androidx.car.app.model.CarColor;
import androidx.car.app.model.CarIcon;
import androidx.car.app.model.Pane;
import androidx.car.app.model.PaneTemplate;
import androidx.car.app.model.Row;
import androidx.car.app.model.Template;
import androidx.car.app.versioning.CarAppApiLevels;
import androidx.core.graphics.drawable.IconCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.androidautodemo.R;

/**
 * Creates a screen that demonstrates usage of the full screen {@link PaneTemplate} to display a
 * details screen.
 */
public final class FloatingSoftmeterOldScreen extends Screen implements DefaultLifecycleObserver {
    @Nullable
    private IconCompat mPaneImage;
    private IconCompat speakerIcon;

    private CarIcon ivDecreaseExtra, ivIncreaseExtra;
    private CarIcon ivStartTimeTicks, ivStopTimeTicks, ivSpeakerON, ivSpeakerOFF;
    private boolean mFirstToggleState = false;

    public FloatingSoftmeterOldScreen(@NonNull CarContext carContext) {
        super(carContext);
        getLifecycle().addObserver(this);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Resources resources = getCarContext().getResources();
        Bitmap speakerBitmap = BitmapFactory.decodeResource(resources, R.drawable.status_default);
        speakerIcon = IconCompat.createWithBitmap(speakerBitmap);

        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.patio);
        mPaneImage = IconCompat.createWithBitmap(bitmap);

        ivIncreaseExtra = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.collapse_icon)).build();
        ivDecreaseExtra = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.expand_icon)).build();
        ivStopTimeTicks = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.checkbox_on)).build();
        ivStartTimeTicks = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.checkbox_off)).build();
        ivSpeakerON = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.ic_baseline_volume_up_24)).build();
        ivSpeakerOFF = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.ic_baseline_volume_off_24)).build();

    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        Pane.Builder paneBuilder = new Pane.Builder();
        CharSequence fareExtra = getColoredString("0.00                                                   0.00", RED, true);

        paneBuilder.addRow(new Row.Builder()
                .setTitle("Fare              For Hire              Extra")
                .addText(fareExtra)
                //.addText("0.00                                                   0.00")
                .setImage(new CarIcon.Builder(speakerIcon).build())
                .build());
        paneBuilder.addRow(new Row.Builder().setTitle("Time Ticks").setImage(ivStopTimeTicks).build());
        //paneBuilder.addRow(new Row.Builder().setTitle("DIST OFF").setOnClickListener(this::distOFF).build());
        paneBuilder.addRow(new Row.Builder().setTitle("0.00 Mi         0.00 Min").build());

        // Also set a large image outside of the rows.
        //paneBuilder.setImage(new CarIcon.Builder(mPaneImage).build());

        Action.Builder primaryActionBuilder = new Action.Builder()
                .setTitle(getCarContext().getString(R.string.meter_on))
                .setBackgroundColor(CarColor.BLUE)
                .setOnClickListener(
                        () -> CarToast.makeText(
                                        getCarContext(),
                                        getCarContext().getString(R.string.search_toast_msg),
                                        LENGTH_SHORT)
                                .show());
        if (getCarContext().getCarAppApiLevel() >= CarAppApiLevels.LEVEL_4) {
            primaryActionBuilder.setFlags(FLAG_PRIMARY);
        }

        paneBuilder
                .addAction(primaryActionBuilder.build())
                .addAction(
                        new Action.Builder()
                                .setTitle(getCarContext().getString(R.string.time_off))
                                .setBackgroundColor(CarColor.BLUE)
                                .setOnClickListener(
                                        () -> CarToast.makeText(
                                                        getCarContext(),
                                                        getCarContext().getString(
                                                                R.string.options_toast_msg),
                                                        LENGTH_SHORT)
                                                .show())
                                .build());

        return new PaneTemplate.Builder(paneBuilder.build())
                .setHeaderAction(Action.BACK)
                .setActionStrip(
                        new ActionStrip.Builder()
                                .addAction(new Action.Builder()
                                        .setTitle(getCarContext().getString(
                                                R.string.meter_extra))
                                        .setIcon(new CarIcon.Builder(ivDecreaseExtra)
                                                .build())
                                        .setOnClickListener(this::decreaseExtra)
                                        .build())
                                .addAction(
                                        new Action.Builder()
                                                .setOnClickListener(this::increaseExtra)
                                                .setIcon(
                                                        new CarIcon.Builder(
                                                                IconCompat.createWithResource(
                                                                        getCarContext(),
                                                                        R.drawable.status_default))
                                                                .setTint(CarColor.SECONDARY)
                                                                .build())
                                                //.setIcon(new CarIcon.Builder(ivIncreaseExtra).build())
                                                .build())
                                .build())
                .setTitle("Softmeter")
                .build();
    }

    private void increaseExtra() {
        CarToast.makeText(getCarContext(), "Increase Extra", LENGTH_LONG).show();
    }

    private void decreaseExtra() {
        CarToast.makeText(getCarContext(), "Decrease Extra", LENGTH_LONG).show();
    }

    private void distOFF() {
        CarToast.makeText(getCarContext(), "Dist OFF clicked", LENGTH_LONG).show();
    }
}


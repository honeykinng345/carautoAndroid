
package com.example.androidautodemo.templates.softmeter;

import static androidx.car.app.CarToast.LENGTH_LONG;
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
import androidx.car.app.model.CarIcon;
import androidx.car.app.model.ItemList;
import androidx.car.app.model.ListTemplate;
import androidx.car.app.model.PaneTemplate;
import androidx.car.app.model.Row;
import androidx.car.app.model.Template;
import androidx.core.graphics.drawable.IconCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.androidautodemo.R;

/**
 * Creates a screen that demonstrates usage of the full screen {@link PaneTemplate} to display a
 * details screen.
 */
public final class FloatingSoftmeterScreen extends Screen implements DefaultLifecycleObserver {
    @Nullable
    private IconCompat mPaneImage;

    private IconCompat ivDecreaseExtra, ivIncreaseExtra;
    private CarIcon ivStartTimeTicks, ivStopTimeTicks, ivSpeakerON, ivSpeakerOFF;
    private boolean isTimedTick = true;
    private boolean audioAnnouncementsON = true;

    public FloatingSoftmeterScreen(@NonNull CarContext carContext) {
        super(carContext);
        getLifecycle().addObserver(this);
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        Resources resources = getCarContext().getResources();
        Bitmap speakerBitmap = BitmapFactory.decodeResource(resources, R.drawable.status_default);
        //ivIncreaseExtra = IconCompat.createWithBitmap(speakerBitmap);

        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.patio);
        mPaneImage = IconCompat.createWithBitmap(bitmap);

        ivIncreaseExtra = IconCompat.createWithResource(getCarContext(), R.drawable.ic_baseline_increase);         //collapse_icon
        ivDecreaseExtra = IconCompat.createWithResource(getCarContext(), R.drawable.ic_baseline_decrease);         //expand_icon
        ivStopTimeTicks = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.checkbox_on)).build();
        ivStartTimeTicks = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.checkbox_off)).build();
        ivSpeakerON = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.ic_baseline_volume_up_24)).build();
        ivSpeakerOFF = new CarIcon.Builder(IconCompat.createWithResource(getCarContext(), R.drawable.ic_baseline_volume_off_24)).build();
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        Row MeterONBtn = new Row.Builder()
                .setTitle("Meter ON")
                .setOnClickListener(
                        () -> {
                            meterON();
                            invalidate();
                        })
                .build();
        Row TimeOFFBtn = new Row.Builder()
                .setTitle("Time OFF")
                .setOnClickListener(
                        () -> {
                            // Handle click for Button 2
                            invalidate();
                        })
                .build();
        Row DistOFFBtn = new Row.Builder()
                .setTitle("Dist OFF")
                .setOnClickListener(
                        () -> {
                            // Handle click for Button 3
                            invalidate();
                        })
                .build();


        Row timeTicksCheckbox = new Row.Builder()
                .setTitle("Time Ticks")
                .setImage(isTimedTick ? ivStartTimeTicks : ivStopTimeTicks)
                .setOnClickListener(
                        () -> {
                            isTimedTick = !isTimedTick;
                            invalidate();
                        })
                .build();
        CharSequence fareExtra = getColoredString("0.00                                                   0.00", RED, true);
        ItemList.Builder itemListBuilder = new ItemList.Builder();
        itemListBuilder.addItem(createHeader(new CarIcon.Builder(audioAnnouncementsON ? ivSpeakerOFF : ivSpeakerON).build(), "Fare              For Hire              Extra", fareExtra));
        itemListBuilder.addItem(timeTicksCheckbox);
        itemListBuilder.addItem(new Row.Builder().setTitle("0.00 Mi         0.00 Min").build());

        itemListBuilder.addItem(MeterONBtn);
        itemListBuilder.addItem(TimeOFFBtn);
        itemListBuilder.addItem(DistOFFBtn);

        // Set the action strip.
        ActionStrip myActions = new ActionStrip.Builder()
                .addAction(
                        new Action.Builder()
                                .setOnClickListener(this::decreaseExtra)
                                .setIcon(new CarIcon.Builder(ivDecreaseExtra).build())
                                .setTitle("Extra")
                                .build())
                .addAction(
                        new Action.Builder()
                                .setOnClickListener(this::increaseExtra)
                                .setIcon(new CarIcon.Builder(ivIncreaseExtra).build())
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

        return new ListTemplate.Builder()
                .setSingleList(itemListBuilder.build())
                .setHeaderAction(Action.BACK)
                .setActionStrip(myActions)
                .setTitle("Softmeter")
                .build();
    }

    private Row createHeader(CarIcon icon, String title, CharSequence subTitle) {
        Row.Builder rowBuilder = new Row.Builder()
                .setTitle(title)
                .addText(subTitle)
                .setImage(icon);
        rowBuilder.setOnClickListener(() -> {
            audioAnnouncementsON = !audioAnnouncementsON;
            audioAnnouncements(audioAnnouncementsON);
            invalidate();
        });
        return rowBuilder.build();
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

    private void meterON() {
        CarToast.makeText(getCarContext(), "Meter ON clicked", LENGTH_LONG).show();
    }

    private void timeOFF() {
        CarToast.makeText(getCarContext(), "Time ON clicked", LENGTH_LONG).show();
    }

    private void audioAnnouncements(boolean isEnabled) {
        CarToast.makeText(getCarContext(), isEnabled ? "Enable Announcements" : "Disable Announcements", LENGTH_LONG).show();
    }

    private void showToast(String message) {
        CarToast.makeText(getCarContext(), message, LENGTH_LONG).show();
    }
}


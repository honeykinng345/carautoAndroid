package com.example.androidautodemo.templates.map;

import static androidx.car.app.CarToast.LENGTH_LONG;
import static androidx.car.app.model.Action.BACK;

import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.car.app.CarContext;
import androidx.car.app.CarToast;
import androidx.car.app.HostException;
import androidx.car.app.Screen;
import androidx.car.app.model.Action;
import androidx.car.app.model.CarColor;
import androidx.car.app.model.Pane;
import androidx.car.app.model.PaneTemplate;
import androidx.car.app.model.Row;
import androidx.car.app.model.Template;

import com.example.androidautodemo.R;

/** A screen that displays the details of a place. */
public class PlaceDetailsScreen extends Screen {
    private final PlaceInfo mPlace;

    private PlaceDetailsScreen(@NonNull CarContext carContext, @NonNull PlaceInfo place) {
        super(carContext);
        mPlace = place;
    }

    /** Creates an instance of {@link PlaceDetailsScreen}. */
    @NonNull
    public static PlaceDetailsScreen create(
            @NonNull CarContext carContext, @NonNull PlaceInfo place) {
        return new PlaceDetailsScreen(carContext, place);
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        Pane.Builder paneBuilder =
                new Pane.Builder()
                        .addAction(
                                new Action.Builder()
                                        .setTitle(getCarContext().getString(R.string.navigate))
                                        .setBackgroundColor(CarColor.BLUE)
                                        .setOnClickListener(this::onClickNavigate)
                                        .build())
                        .addAction(
                                new Action.Builder()
                                        .setTitle(getCarContext().getString(R.string.dial))
                                        .setOnClickListener(this::onClickDial)
                                        .build())
                        .addRow(
                                new Row.Builder()
                                        .setTitle(getCarContext().getString(R.string.address))
                                        .addText(mPlace.address)
                                        .build())
                        .addRow(
                                new Row.Builder()
                                        .setTitle(getCarContext().getString(R.string.phone))
                                        .addText(mPlace.phoneNumber)
                                        .build());

        return new PaneTemplate.Builder(paneBuilder.build())
                .setTitle(mPlace.title)
                .setHeaderAction(BACK)
                .build();
    }

    private void onClickNavigate() {
        Uri uri = Uri.parse("geo:0,0?q=" + mPlace.address);
        Intent intent = new Intent(CarContext.ACTION_NAVIGATE, uri);

        try {
            getCarContext().startCarApp(intent);
        } catch (HostException e) {
            CarToast.makeText(
                            getCarContext(),
                            getCarContext().getString(R.string.fail_start_nav),
                            LENGTH_LONG)
                    .show();
        }
    }

    private void onClickDial() {
        Uri uri = Uri.parse("tel:" + mPlace.phoneNumber);
        Intent intent = new Intent(Intent.ACTION_DIAL, uri);

        try {
            getCarContext().startCarApp(intent);
        } catch (HostException e) {
            CarToast.makeText(
                            getCarContext(),
                            getCarContext().getString(R.string.fail_start_dialer),
                            LENGTH_LONG)
                    .show();
        }
    }
}

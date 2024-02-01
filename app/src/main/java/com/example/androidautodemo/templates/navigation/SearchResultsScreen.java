package com.example.androidautodemo.templates.navigation;

import static android.text.Spanned.SPAN_INCLUSIVE_INCLUSIVE;

import android.text.SpannableString;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.car.app.CarContext;
import androidx.car.app.Screen;
import androidx.car.app.model.Action;
import androidx.car.app.model.ActionStrip;
import androidx.car.app.model.CarLocation;
import androidx.car.app.model.Distance;
import androidx.car.app.model.DistanceSpan;
import androidx.car.app.model.Header;
import androidx.car.app.model.ItemList;
import androidx.car.app.model.Metadata;
import androidx.car.app.model.Place;
import androidx.car.app.model.Row;
import androidx.car.app.model.Template;
import androidx.car.app.navigation.model.PlaceListNavigationTemplate;

import com.example.androidautodemo.nevigationtemplate.common.DemoScripts;
import com.example.androidautodemo.nevigationtemplate.common.PlaceInfo;
import com.example.androidautodemo.templates.navigation.nav.SurfaceRenderer;

/**
 * Screen for showing a list of places from a search.
 */
public final class SearchResultsScreen extends Screen {
    @NonNull
    private final Action mSettingsAction;
    @NonNull
    private final SurfaceRenderer mSurfaceRenderer;
    @NonNull
    private final String mSearchText;

    public SearchResultsScreen(
            @NonNull CarContext carContext,
            @NonNull Action settingsAction,
            @NonNull SurfaceRenderer surfaceRenderer,
            @NonNull String searchText) {
        super(carContext);
        mSettingsAction = settingsAction;
        mSurfaceRenderer = surfaceRenderer;
        mSearchText = searchText;
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        mSurfaceRenderer.updateMarkerVisibility(
                /* showMarkers=*/ false, /* numMarkers=*/ 0, /* activeMarker=*/ -1);
        ItemList.Builder listBuilder = new ItemList.Builder();

        int numItems = ((int) (Math.random() * 6.0)) + 1;

        for (int i = 0; i < numItems; i++) {
            PlaceInfo place =
                    new PlaceInfo(
                            String.format("Result %d", i + 1),
                            String.format("%d Main Street.", (i + 1) * 10));

            SpannableString address = new SpannableString("  \u00b7 " + place.getDisplayAddress());
            DistanceSpan distanceSpan =
                    DistanceSpan.create(
                            Distance.create(
                                    /* displayDistance= */ i + 1, Distance.UNIT_KILOMETERS_P1));
            address.setSpan(distanceSpan, 0, 1, SPAN_INCLUSIVE_INCLUSIVE);
            listBuilder.addItem(
                    new Row.Builder()
                            .setTitle(place.getName())
                            .addText(address)
                            .setOnClickListener(() -> onClickItem())
                            .setMetadata(
                                    new Metadata.Builder()
                                            .setPlace(
                                                    new Place.Builder(CarLocation.create(1, 1))
                                                            .build())
                                            .build())
                            .build());
        }

        Header header = new Header.Builder()
                .setStartHeaderAction(Action.BACK)
                .setTitle("Search: " + mSearchText)
                .build();

        return new PlaceListNavigationTemplate.Builder()
                .setItemList(listBuilder.build())
                .setHeader(header)
                .setActionStrip(new ActionStrip.Builder().addAction(mSettingsAction).build())
                .build();
    }

    private void onClickItem() {
        getScreenManager()
                .pushForResult(
                        new RoutePreviewScreen(getCarContext(), mSettingsAction, mSurfaceRenderer),
                        this::onRoutePreviewResult);
    }

    private void onRoutePreviewResult(@Nullable Object previewResult) {
        int previewIndex = previewResult == null ? -1 : (int) previewResult;
        if (previewIndex < 0) {
            return;
        }
        // Start the same demo instructions. More will be added in the future.
        setResult(DemoScripts.getNavigateHome(getCarContext()));
        finish();
    }
}

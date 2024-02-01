
package com.example.androidautodemo.templates.map;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.HandlerThread;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.car.app.CarContext;
import androidx.car.app.CarToast;
import androidx.car.app.Screen;
import androidx.car.app.model.Action;
import androidx.car.app.model.CarLocation;
import androidx.car.app.model.ItemList;
import androidx.car.app.model.Place;
import androidx.car.app.model.PlaceListMapTemplate;
import androidx.car.app.model.Row;
import androidx.car.app.model.Template;
import androidx.core.location.LocationListenerCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.androidautodemo.R;

/**
 * Creates a screen using the {@link PlaceListMapTemplate}.
 *
 * <p>This screen shows the ability to anchor the map around the current location when there are
 * no other POI markers present.
 */
public final class PlaceListTemplateBrowseDemoScreen extends Screen {
    private static final int LOCATION_UPDATE_MIN_INTERVAL_MILLIS = 1000;
    private static final int LOCATION_UPDATE_MIN_DISTANCE_METER = 1;

    final LocationListenerCompat mLocationListener;
    final HandlerThread mLocationUpdateHandlerThread;
    boolean mHasPermissionLocation;

    @Nullable
    private Location mCurrentLocation;

    public PlaceListTemplateBrowseDemoScreen(@NonNull CarContext carContext) {
        super(carContext);

        mHasPermissionLocation = carContext.checkSelfPermission(ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || carContext.checkSelfPermission(ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;

        mLocationUpdateHandlerThread = new HandlerThread("LocationThread");
        mLocationListener = location -> {
            mCurrentLocation = location;
            invalidate();
        };

        getLifecycle().addObserver(new DefaultLifecycleObserver() {
            @Override
            public void onResume(@NonNull LifecycleOwner owner) {
                mHasPermissionLocation = carContext.checkSelfPermission(ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED
                        || carContext.checkSelfPermission(ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED;
                if (mHasPermissionLocation) {
                    LocationManager locationManager =
                            carContext.getSystemService(LocationManager.class);
                    locationManager.requestLocationUpdates(LocationManager.FUSED_PROVIDER,
                            LOCATION_UPDATE_MIN_INTERVAL_MILLIS,
                            LOCATION_UPDATE_MIN_DISTANCE_METER,
                            mLocationListener,
                            mLocationUpdateHandlerThread.getLooper());
                } else {
                    CarToast.makeText(carContext,
                            getCarContext().getString(R.string.grant_location_permission_toast_msg),
                            CarToast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onPause(@NonNull LifecycleOwner owner) {
                LocationManager locationManager =
                        getCarContext().getSystemService(LocationManager.class);
                locationManager.removeUpdates(mLocationListener);
            }
        });
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        PlaceListMapTemplate.Builder builder = new PlaceListMapTemplate.Builder()
                .setItemList(new ItemList.Builder()
                        .addItem(new Row.Builder()
                                .setTitle(getCarContext().getString(R.string.browse_places_title))
                                .setBrowsable(true)
                                .setOnClickListener(
                                        () -> getScreenManager().push(
                                                new PlaceListTemplateDemoScreen(
                                                        getCarContext()))).build())
                        .build())
                .setTitle(getCarContext().getString(R.string.place_list_template_demo_title))
                .setHeaderAction(Action.BACK)
                .setCurrentLocationEnabled(mHasPermissionLocation);

        if (mCurrentLocation != null) {
            builder.setAnchor(new Place.Builder(CarLocation.create(mCurrentLocation)).build());
        }

        return builder.build();
    }
}

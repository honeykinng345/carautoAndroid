package com.example.androidautodemo.templates.navigation;

import androidx.annotation.NonNull;
import androidx.car.app.CarContext;
import androidx.car.app.Screen;
import androidx.car.app.model.CarIcon;
import androidx.car.app.model.Distance;
import androidx.car.app.model.Template;
import androidx.car.app.navigation.model.NavigationTemplate;
import androidx.car.app.navigation.model.RoutingInfo;
import androidx.core.graphics.drawable.IconCompat;
import androidx.lifecycle.DefaultLifecycleObserver;

import com.example.androidautodemo.R;
import com.example.androidautodemo.nevigationtemplate.routing.RoutingDemoModels;

/**
 * A screen that shows the navigation template in routing state showing a junction image.
 */
public final class JunctionImageDemoScreen extends Screen implements DefaultLifecycleObserver {
    public JunctionImageDemoScreen(@NonNull CarContext carContext) {
        super(carContext);
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        CarContext carContext = getCarContext();
        return new NavigationTemplate.Builder()
                .setNavigationInfo(
                        new RoutingInfo.Builder()
                                .setCurrentStep(
                                        RoutingDemoModels.getCurrentStep(carContext),
                                        Distance.create(200, Distance.UNIT_METERS))
                                .setJunctionImage(
                                        new CarIcon.Builder(
                                                IconCompat.createWithResource(
                                                        carContext,
                                                        R.drawable.junction_image))
                                                .build())
                                .build())
                .setDestinationTravelEstimate(RoutingDemoModels.getTravelEstimate(carContext))
                .setActionStrip(RoutingDemoModels.getActionStrip(getCarContext(), this::finish))
                .build();
    }
}

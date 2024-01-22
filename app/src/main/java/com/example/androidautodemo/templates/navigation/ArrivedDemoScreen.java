package com.example.androidautodemo.templates.navigation;

import androidx.annotation.NonNull;
import androidx.car.app.CarContext;
import androidx.car.app.Screen;
import androidx.car.app.model.CarColor;
import androidx.car.app.model.CarIcon;
import androidx.car.app.model.Template;
import androidx.car.app.navigation.model.MessageInfo;
import androidx.car.app.navigation.model.NavigationTemplate;
import androidx.core.graphics.drawable.IconCompat;
import androidx.lifecycle.DefaultLifecycleObserver;

import com.example.androidautodemo.R;
import com.example.androidautodemo.nevigationtemplate.routing.RoutingDemoModels;

/**
 * A screen that shows the navigation template in arrived state.
 */
public final class ArrivedDemoScreen extends Screen implements DefaultLifecycleObserver {
    public ArrivedDemoScreen(@NonNull CarContext carContext) {
        super(carContext);
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        return new NavigationTemplate.Builder()
                .setNavigationInfo(
                        new MessageInfo.Builder(
                                getCarContext().getString(R.string.arrived_exclamation_msg))
                                .setText(getCarContext().getString(R.string.arrived_address_msg))
                                .setImage(
                                        new CarIcon.Builder(
                                                IconCompat.createWithResource(
                                                        getCarContext(),
                                                        R.drawable.ic_place_white_24dp))
                                                .build())
                                .build())
                .setActionStrip(RoutingDemoModels.getActionStrip(getCarContext(), this::finish))
                .setBackgroundColor(CarColor.SECONDARY)
                .build();
    }
}

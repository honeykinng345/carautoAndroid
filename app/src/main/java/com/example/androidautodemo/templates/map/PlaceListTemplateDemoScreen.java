
package com.example.androidautodemo.templates.map;

import androidx.annotation.NonNull;
import androidx.car.app.CarContext;
import androidx.car.app.Screen;
import androidx.car.app.model.Action;
import androidx.car.app.model.PlaceListMapTemplate;
import androidx.car.app.model.Template;

import com.example.androidautodemo.R;

/**
 * Creates a screen using the {@link PlaceListMapTemplate}
 */
public final class PlaceListTemplateDemoScreen extends Screen {
    private final SamplePlaces mPlaces;

    public PlaceListTemplateDemoScreen(@NonNull CarContext carContext) {
        super(carContext);
        mPlaces = SamplePlaces.create(this);
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        return new PlaceListMapTemplate.Builder()
                .setItemList(mPlaces.getPlaceList())
                .setTitle(getCarContext().getString(R.string.place_list_template_demo_title))
                .setHeaderAction(Action.BACK)
                .build();
    }
}

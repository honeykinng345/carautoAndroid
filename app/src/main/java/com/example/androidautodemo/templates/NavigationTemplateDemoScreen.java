package com.example.androidautodemo.templates;

import androidx.annotation.NonNull;
import androidx.car.app.CarContext;
import androidx.car.app.Screen;
import androidx.car.app.model.Action;
import androidx.car.app.model.ItemList;
import androidx.car.app.model.ListTemplate;
import androidx.car.app.model.Row;
import androidx.car.app.model.Template;

import com.example.androidautodemo.R;
import com.example.androidautodemo.nevigationtemplate.routing.ArrivedDemoScreen;
import com.example.androidautodemo.nevigationtemplate.routing.JunctionImageDemoScreen;
import com.example.androidautodemo.nevigationtemplate.routing.LoadingDemoScreen;
import com.example.androidautodemo.nevigationtemplate.routing.NavigatingDemoScreen;

/**
 * A screen showing a demos for the navigation template in different states.
 */
public final class NavigationTemplateDemoScreen extends Screen {
    public NavigationTemplateDemoScreen(@NonNull CarContext carContext) {
        super(carContext);
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        ItemList.Builder listBuilder = new ItemList.Builder();

        listBuilder.addItem(
                new Row.Builder()
                        .setTitle(getCarContext().getString(R.string.loading_demo_title))
                        .setOnClickListener(
                                () ->
                                        getScreenManager()
                                                .push(new LoadingDemoScreen(getCarContext())))
                        .build());

        listBuilder.addItem(
                new Row.Builder()
                        .setTitle(getCarContext().getString(R.string.navigating_demo_title))
                        .setOnClickListener(
                                () ->
                                        getScreenManager()
                                                .push(new NavigatingDemoScreen(getCarContext())))
                        .build());

        listBuilder.addItem(
                new Row.Builder()
                        .setTitle(getCarContext().getString(R.string.arrived_demo_title))
                        .setOnClickListener(
                                () ->
                                        getScreenManager()
                                                .push(new ArrivedDemoScreen(getCarContext())))
                        .build());

        listBuilder.addItem(
                new Row.Builder()
                        .setTitle(getCarContext().getString(R.string.junction_image_demo_title))
                        .setOnClickListener(
                                () ->
                                        getScreenManager()
                                                .push(new JunctionImageDemoScreen(getCarContext())))
                        .build());

        return new ListTemplate.Builder()
                .setSingleList(listBuilder.build())
                .setTitle(getCarContext().getString(R.string.nav_template_demos_title))
                .setHeaderAction(Action.BACK)
                .build();
    }
}

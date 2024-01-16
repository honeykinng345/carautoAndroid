package com.example.androidautodemo;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.car.app.CarContext;
import androidx.car.app.CarToast;
import androidx.car.app.Screen;
import androidx.car.app.ScreenManager;
import androidx.car.app.model.Action;
import androidx.car.app.model.CarIcon;
import androidx.car.app.model.ItemList;
import androidx.car.app.model.ListTemplate;
import androidx.car.app.model.Row;
import androidx.car.app.model.Template;
import androidx.core.graphics.drawable.IconCompat;

import com.example.androidautodemo.nevigationtemplate.NavigationDemosScreen;
import com.example.androidautodemo.nevigationtemplate.routing.NavigatingDemoScreen;
import com.example.androidautodemo.templates.GridTemplateDemoScreen;
import com.example.androidautodemo.templates.ListTemplateDemoScreen;

public class CarScreen extends Screen {

    static ScreenManager screenManager = null;
    static CarContext carContextThis = null;

    protected CarScreen(@NonNull CarContext carContext) {
        super(carContext);
        carContextThis = carContext;
        screenManager = getScreenManager();

    }

    @SuppressLint("RestrictedApi")
    @NonNull
    @Override
    public Template onGetTemplate() {


//        Row row = new Row.Builder().setTitle("Hello world!").build();
//
//
//        Pane pane = new Pane.Builder().addRow(row).build();
//         new PaneTemplate.Builder(pane)
//                .setHeaderAction(Action.APP_ICON)
//                .build();
//        NavigationTemplate.Builder builder = new NavigationTemplate.Builder();
//        builder.setBackgroundColor(CarColor.SECONDARY);
//        ActionStrip.Builder actionStripBuilder = new ActionStrip.Builder();
//       actionStripBuilder.addAction(
//                new Action.Builder()
//                        .setTitle("Voice")
//                        .setIcon(new CarIcon.Builder(
//                                IconCompat.createWithResource(getCarContext(),
//                                        R.drawable.ic_mic)).build()).setOnClickListener(this::showToast)
//                        .build());
//        actionStripBuilder.addAction(
//                new Action.Builder()
//                        .setTitle("Stop")
//                        .setOnClickListener(this::stopNavigation)
//                        .build());
//             builder.setActionStrip(actionStripBuilder.build());


//
////
//        GridTemplate.Builder gridBuilder = new GridTemplate.Builder();
//        Row row = new Row.Builder().setTitle("Hello world!").build();
//// Create an ItemList and add items to it
//        ItemList itemList = new ItemList.Builder()
//                .addItem(row).build();
//
//// Set the single list for the grid
//        gridBuilder.setSingleList(itemList);
//
//// Add an ActionStrip with an action (e.g., Voice command)
//        ActionStrip.Builder actionStripBuilder = new ActionStrip.Builder();
//        actionStripBuilder.addAction(
//                new Action.Builder()
//                        .setTitle("Voice")
//                        .setIcon(new CarIcon.Builder(
//                                IconCompat.createWithResource(getCarContext(), R.drawable.ic_mic))
//                                .build())
//                        .setOnClickListener(this::showToast)
//                        .build());
//
//        gridBuilder.setActionStrip(actionStripBuilder.build());
//
//// Build the final GridTemplate
//        GridTemplate gridTemplate = gridBuilder.build();
//        return  gridTemplate;

        ItemList.Builder listBuilder = new ItemList.Builder();
        listBuilder.addItem(
                new Row.Builder()
                        .setTitle("List Template")
                        .setOnClickListener(
                                () ->
                                        getScreenManager()
                                                .push(
                                                        new ListTemplateDemoScreen(getCarContext())))
                        .build());
        listBuilder.addItem(
                new Row.Builder()
                        .setTitle("Grid Template")
                        .setOnClickListener(
                                () -> getScreenManager().push(new GridTemplateDemoScreen(getCarContext())))
                        .build());
        listBuilder.addItem(
                new Row.Builder()
                        .setImage(
                                new CarIcon.Builder(
                                        IconCompat.createWithResource(
                                                getCarContext(),
                                                R.drawable.ic_face_24px))
                                        .build(),
                                Row.IMAGE_TYPE_ICON)
                        .setTitle("Navigation Screen")
                        .setOnClickListener(
                                () ->getScreenManager().push(new NavigationDemosScreen(getCarContext())) )
                        .setBrowsable(true)
                        .build());
        listBuilder.addItem(
                new Row.Builder()
                        .setImage(
                                new CarIcon.Builder(
                                        IconCompat.createWithResource(
                                                getCarContext(),
                                                R.drawable.ic_face_24px))
                                        .build(),
                                Row.IMAGE_TYPE_ICON)
                        .setTitle("Places List Template")
                        .setOnClickListener(
                                () -> click())
                        .setBrowsable(true)
                        .build());
        listBuilder.addItem(
                new Row.Builder()
                        .setTitle("Search Template")
                        .setOnClickListener(
                                () -> click())
                        .build());
        return new ListTemplate.Builder()
                .setSingleList(listBuilder.build())
                .setTitle("List Of Items")
                .setHeaderAction(Action.APP_ICON)
                .build();
    }

    private void click() {
        getScreenManager().push(new ListTemplateDemoScreen(getCarContext()));

    }

    private void stopNavigation() {
    }

    private void showToast() {
        CarToast.makeText(getCarContext(), "You Clicked ", CarToast.LENGTH_LONG).show();
    }
}

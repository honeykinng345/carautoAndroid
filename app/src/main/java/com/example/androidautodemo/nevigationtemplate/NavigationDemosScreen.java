package com.example.androidautodemo.nevigationtemplate;

import androidx.annotation.NonNull;
import androidx.car.app.CarContext;
import androidx.car.app.Screen;
import androidx.car.app.model.Action;
import androidx.car.app.model.ActionStrip;
import androidx.car.app.model.CarIcon;
import androidx.car.app.model.ItemList;
import androidx.car.app.model.ListTemplate;
import androidx.car.app.model.Row;
import androidx.car.app.model.Template;
import androidx.core.graphics.drawable.IconCompat;

import com.example.androidautodemo.R;
import com.example.androidautodemo.nevigationtemplate.routing.NavigationTemplateDemoScreen;
import com.example.androidautodemo.templates.map.PlaceListNavigationTemplateDemoScreen;

/** A screen showing a list of navigation demos */
public final class NavigationDemosScreen extends Screen {
    private static final int MAX_PAGES = 2;

    private final int mPage;

    public NavigationDemosScreen(@NonNull CarContext carContext) {
        this(carContext, /* page= */ 0);
    }

    public NavigationDemosScreen(@NonNull CarContext carContext, int page) {
        super(carContext);
        mPage = page;
    }

    @NonNull
    @Override
    public Template onGetTemplate() {
        ItemList.Builder listBuilder = new ItemList.Builder();

        switch (mPage) {
            case 0:
                listBuilder.addItem(
                        new Row.Builder()
                                .setImage(
                                        new CarIcon.Builder(
                                                IconCompat.createWithResource(
                                                        getCarContext(),
                                                        R.drawable.ic_explore_white_24dp))
                                                .build(),
                                        Row.IMAGE_TYPE_ICON)
                                .setTitle(getCarContext().getString(
                                        R.string.nav_template_demos_title))
                                .setOnClickListener(
                                        () ->
                                                getScreenManager()
                                                        .push(
                                                                new NavigationTemplateDemoScreen(
                                                                        getCarContext())))
                                .setBrowsable(true)
                                .build());
                listBuilder.addItem(createRow(
                        getCarContext().getString(R.string.place_list_nav_template_demo_title),
                        new PlaceListNavigationTemplateDemoScreen(getCarContext())));
                listBuilder.addItem(createRow(
                        getCarContext().getString(R.string.route_preview_template_demo_title),
                        new RoutePreviewDemoScreen(getCarContext())));
//                listBuilder.addItem(createRow(
//                        getCarContext().getString(R.string.notification_template_demo_title),
//                        new NavigationNotificationsDemoScreen(getCarContext())));
                listBuilder.addItem(
                        createRow(getCarContext().getString(R.string.nav_map_template_demo_title),
                                new NavigationMapOnlyScreen(getCarContext())));
//                listBuilder.addItem(
//                        createRow(getCarContext().getString(R.string.map_template_pane_demo_title),
//                                new MapTemplateWithPaneDemoScreen(getCarContext())));
                break;
            case 1:
//                listBuilder.addItem(
//                        createRow(getCarContext().getString(R.string.map_template_list_demo_title),
//                                new MapTemplateWithListDemoScreen(getCarContext())));
                break;
        }

        ListTemplate.Builder builder = new ListTemplate.Builder()
                .setSingleList(listBuilder.build())
                .setTitle(getCarContext().getString(R.string.nav_demos_title))
                .setHeaderAction(Action.BACK);

        if (mPage + 1 < MAX_PAGES) {
            builder.setActionStrip(new ActionStrip.Builder()
                    .addAction(new Action.Builder()
                            .setTitle(getCarContext().getString(R.string.more_action_title))
                            .setOnClickListener(() -> {
                                getScreenManager().push(
                                        new NavigationDemosScreen(getCarContext(), mPage + 1));
                            })
                            .build())
                    .build());
        }

        return builder.build();
    }

    private Row createRow(String title, Screen screen) {
        return new Row.Builder()
                .setTitle(title)
                .setOnClickListener(() -> getScreenManager().push(screen))
                .build();
    }
}

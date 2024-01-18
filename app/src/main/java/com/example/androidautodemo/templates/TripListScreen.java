//package com.example.androidautodemo.templates;
//
//import static androidx.car.app.CarToast.LENGTH_LONG;
//import static androidx.car.app.CarToast.LENGTH_SHORT;
//import static androidx.car.app.model.Action.BACK;
//
//import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.car.app.CarContext;
//import androidx.car.app.CarToast;
//import androidx.car.app.Screen;
//import androidx.car.app.constraints.ConstraintManager;
//import androidx.car.app.model.Action;
//import androidx.car.app.model.ActionStrip;
//import androidx.car.app.model.CarIcon;
//import androidx.car.app.model.CarText;
//import androidx.car.app.model.GridItem;
//import androidx.car.app.model.ItemList;
//import androidx.car.app.model.ListTemplate;
//import androidx.car.app.model.ParkedOnlyOnClickListener;
//import androidx.car.app.model.Row;
//import androidx.car.app.model.Template;
//import androidx.car.app.versioning.CarAppApiLevels;
//import androidx.core.graphics.drawable.IconCompat;
//import androidx.lifecycle.DefaultLifecycleObserver;
//import androidx.lifecycle.LifecycleOwner;
//
//import com.example.androidautodemo.R;
//
///**
// * Creates a screen that demonstrates usage of the full screen {@link ListTemplate} to display a
// * full-screen list.
// */
//public final class TripListScreen extends Screen implements DefaultLifecycleObserver {
//    private static final int MAX_LIST_ITEMS = 100;
//    @Nullable
//    private IconCompat mImage;
//    @Nullable
//    private IconCompat mIcon;
//
//    public TripListScreen(@NonNull CarContext carContext) {
//        super(carContext);
//        getLifecycle().addObserver(this);
//    }
//
//    @Override
//    public void onCreate(@NonNull LifecycleOwner owner) {
//        DefaultLifecycleObserver.super.onCreate(owner);
//        Resources resources = getCarContext().getResources();
//        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.test_image_square);
//        mImage = IconCompat.createWithBitmap(bitmap);
//        mIcon = IconCompat.createWithResource(getCarContext(), R.drawable.ic_fastfood_white_48dp);
//    }
//
//    @NonNull
//    @Override
//    public Template onGetTemplate() {
//        ItemList.Builder listBuilder = new ItemList.Builder();
//
//        // Grid item with an icon, a title, onClickListener and no text.
//        new GridItem.Builder()
//                .setImage(new CarIcon.Builder(mIcon).build(), GridItem.IMAGE_TYPE_ICON)
//                .setTitle(getCarContext().getString(R.string.second_item))
//                .setOnClickListener(
//                        () -> CarToast.makeText(
//                                        getCarContext(),
//                                        getCarContext()
//                                                .getString(R.string.second_item_toast_msg),
//                                        LENGTH_SHORT)
//                                .show())
//                .build();
//
//        listBuilder.addItem(
//                new Row.Builder()
//                        .setOnClickListener(
//                                ParkedOnlyOnClickListener.create(() -> onClick("Trip actions")))
//                        .setImage(new CarIcon.Builder(mIcon).build(), Row.IMAGE_TYPE_SMALL)
//                        .setTitle("Flagger")
//                        .setImage(new CarIcon.Builder(mImage).build())
//                        .addText("0860000123")
//                        .build());
//
//        Row.Builder secondRow = new Row.Builder()
//                .setOnClickListener(() -> onClick("Trip Item clicked"))
//                .setTitle("07:25 PM")
//                .addText("Jan-16-2024");
//        listBuilder.addItem(secondRow.build());
//
//        // Some hosts may allow more items in the list than others, so create more.
//        if (getCarContext().getCarAppApiLevel() > CarAppApiLevels.LEVEL_1) {
//            int listLimit =
//                    Math.min(MAX_LIST_ITEMS,
//                            getCarContext().getCarService(ConstraintManager.class).getContentLimit(
//                                    ConstraintManager.CONTENT_LIMIT_TYPE_LIST));
//
//            for (int i = 2; i <= listLimit; ++i) {
//                // For row text, set text variants that fit best in different screen sizes.
//                String secondTextStr = "Second Line Text";
//                CarText secondText =
//                        new CarText.Builder(
//                                "================= " + secondTextStr + " ================")
//                                .addVariant("--------------------- " + secondTextStr
//                                        + " ----------------------")
//                                .addVariant(secondTextStr)
//                                .build();
//                final String onClickText = "Car Row Prefix"
//                        + ": " + i;
//                Row.Builder rowBuilder = new Row.Builder()
//                        .setOnClickListener(() -> onClick(onClickText))
//                        .setTitle("Title Prefix " + i);
//                if (i % 2 == 0) {
//                    rowBuilder.addText("Long Line Text");
//                } else {
//                    rowBuilder
//                            .addText("First Line Text")
//                            .addText(secondText);
//                }
//                listBuilder.addItem(rowBuilder.build());
//            }
//        }
//
//        Action settings = new Action.Builder()
//                .setTitle(getCarContext().getString(
//                        R.string.settings_action_title))
//                .setOnClickListener(
//                        () -> CarToast.makeText(
//                                        getCarContext(),
//                                        getCarContext().getString(
//                                                R.string.settings_toast_msg),
//                                        LENGTH_LONG)
//                                .show())
//                .build();
//
//        return new ListTemplate.Builder()
//                .setSingleList(listBuilder.build())
//                .setTitle(getCarContext().getString(R.string.trip_list))
//                .setHeaderAction(BACK)
//                .setActionStrip(
//                        new ActionStrip.Builder()
//                                .addAction(settings)
//                                .build())
//                .build();
//    }
//
//    private void onClick(String text) {
//        CarToast.makeText(getCarContext(), text, LENGTH_LONG).show();
//    }
//}

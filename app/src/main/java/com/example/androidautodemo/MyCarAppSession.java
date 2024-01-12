package com.example.androidautodemo;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.car.app.CarContext;
import androidx.car.app.Screen;
import androidx.car.app.Session;
import androidx.car.app.model.Action;
import androidx.car.app.model.ItemList;
import androidx.car.app.model.Pane;
import androidx.car.app.model.PaneTemplate;
import androidx.car.app.model.Row;
import androidx.car.app.model.Template;


public class MyCarAppSession extends Session {



    @NonNull
    @Override
    public Screen onCreateScreen(@NonNull Intent intent) {
        return new CarScreen(getCarContext());

    }

    // Rest of your code...
}
package com.example.androidautodemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.car.app.connection.CarConnection;

import com.example.androidautodemo.nevigationtemplate.common.MyTrip;
import com.example.androidautodemo.templates.triplist.TripListScreen;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Hook up some manual navigation controls.
        Button startNavButton = findViewById(R.id.start_nav);
        startNavButton.setOnClickListener(this::startNavigation);
        Button stopNavButton = findViewById(R.id.stop_nav);
        stopNavButton.setOnClickListener(this::stopNavigation);
        Button btnTripList = findViewById(R.id.btn_trip_list);
        btnTripList.setOnClickListener(view -> {
            List<MyTrip> tripList = new ArrayList<>();
            // Add dummy trips to the list
            tripList.add(new MyTrip("John Doe", "01446051", "PickedUp", "Jan-14-2024", "Jan-14-2024", "07:25 PM", "Lahore(Punjab)", "Lahore(Punjab)", 1, 49.10, 3.1));
            tripList.add(new MyTrip("Jane Smith", "01446054", "AtLocation", "Jan-16-2024", "Jan-14-2024", "09:25 PM", "Lahore(Punjab)", "Lahore(Punjab)", 4, 32.9, 4.3));
            tripList.add(new MyTrip("Ruben Jimenez", "01446073", "IRTPU", "Jan-16-2024", "Jan-14-2024", "09:25 PM", "Lahore(Punjab)", "Lahore(Punjab)", 3, 32.6, 4.3));
            // Add more dummy trips as needed

            CarScreen.screenManager
                    .push(new TripListScreen(CarScreen.carContextThis, tripList));
            //.push(new ListTemplateDemoScreen(CarScreen.carContextThis));

//                Intent intent = new Intent(Intent.ACTION_VIEW)
//                        .setComponent(new ComponentName(MainActivity.this, MyCarAppService.class));
//
//                new CarAppExtender.Builder()
//                        .setImportance(NotificationManagerCompat.IMPORTANCE_HIGH)
//                        .setContentIntent(
//                                CarPendingIntent.getCarApp(MainActivity.this, intent.hashCode(),
//                                        intent,
//                                        0));
        });

        new CarConnection(this).getType().observe(this,
                this::onConnectionStateUpdate);


    }

    private void onConnectionStateUpdate(Integer connectionState) {
        String message;
        switch (connectionState) {
            case CarConnection.CONNECTION_TYPE_NOT_CONNECTED:
                message = "Not connected to a head unit";
                break;
            case CarConnection.CONNECTION_TYPE_NATIVE:
                message = "Connected to Android Automotive OS";
                break;
            case CarConnection.CONNECTION_TYPE_PROJECTION:
                message = "Connected to Android Auto";
                break;
            default:
                message = "Unknown car connection type";
                break;
        }
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void helloButtonClick(View view) {

    }

    private void stopNavigation(View view) {
    }

    private void startNavigation(View view) {
    }
}
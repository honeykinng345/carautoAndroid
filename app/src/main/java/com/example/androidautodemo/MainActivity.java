package com.example.androidautodemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.car.app.connection.CarConnection;

import com.example.androidautodemo.nevigationtemplate.common.MyTrip;
import com.example.androidautodemo.templates.softmeter.FloatingSoftmeterOldScreen;
import com.example.androidautodemo.templates.softmeter.FloatingSoftmeterScreen;
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
            MyTrip trip1 = new MyTrip("John Doe", "028004166", getString(R.string.trip_irtpu), "Jan-14-2024", "Jan-14-2024", "07:25 PM", "Lahore(Punjab)", "Lahore(Punjab)", 1, 49.10, 3.1);
            MyTrip trip2 = new MyTrip("Jane Smith", "028004167", getString(R.string.trip_at_location), "Jan-16-2024", "Jan-14-2024", "09:25 PM", "Lahore(Punjab)", "Lahore(Punjab)", 4, 32.9, 4.3);
            MyTrip trip3 = new MyTrip("Ruben Jimenez", "028004168", getString(R.string.trip_pick_up), "Jan-16-2024", "Jan-14-2024", "09:25 PM", "Lahore(Punjab)", "Lahore(Punjab)", 3, 32.6, 4.3);
            MyTrip trip4 = new MyTrip("Saqib Latif", "028004169", getString(R.string.trip_picked_up), "Jan-16-2024", "Jan-14-2024", "09:25 PM", "Lahore(Punjab)", "Lahore(Punjab)", 3, 32.6, 4.3);

            trip1.setPhoneNumber("03447161273");
            trip1.setServiceId("3617124");
            trip1.setManifestNumber("");
            trip1.setParatransitCount(1);
            trip1.setPickUpPoiName("poi");
            trip1.setPickUpAddress("airport Lahore, Punjab Pakistan");
            trip1.setPickUpUnit("Unit#");
            trip1.setPickUpRemarks("It is good place to visit");
            trip1.setDropOffPoiName("poi");
            trip1.setDropOffAddress("Model Town Lahore");
            trip1.setDropOffUnit("Unit#");
            trip1.setDropOffRemarks("Drop me near LA");
            trip1.setPaymentType("Cash");
            trip1.setFundingSource("Self");
            trip1.setCopay(10.00);

            trip2.setPhoneNumber("03447161273");
            trip2.setServiceId("3617125");
            trip2.setManifestNumber("");
            trip2.setParatransitCount(2);
            trip2.setPickUpPoiName("poi");
            trip2.setPickUpAddress("Lahore, Punjab Pakistan");
            trip2.setPickUpUnit("Unit#");
            trip2.setPickUpRemarks("It is good place to visit");
            trip2.setDropOffPoiName("poi");
            trip2.setDropOffAddress("Model Town Lahore");
            trip2.setDropOffUnit("Unit#");
            trip2.setDropOffRemarks("Drop me near LA");
            trip2.setPaymentType("Cash");
            trip2.setFundingSource("Self");
            trip2.setCopay(0.00);

            trip3.setPhoneNumber("03447161273");
            trip3.setServiceId("3617126");
            trip3.setManifestNumber("");
            trip3.setParatransitCount(3);
            trip3.setPickUpPoiName("poi");
            trip3.setPickUpAddress("Lahore, Punjab Pakistan");
            trip3.setPickUpUnit("Unit#");
            trip3.setPickUpRemarks("It is good place to visit");
            trip3.setDropOffPoiName("poi");
            trip3.setDropOffAddress("Model Town Lahore");
            trip3.setDropOffUnit("Unit#");
            trip3.setDropOffRemarks("Drop me near LA");
            trip3.setPaymentType("Cash");
            trip3.setFundingSource("Self");
            trip3.setCopay(5.00);

            trip4.setPhoneNumber("03447161273");
            trip4.setServiceId("3617127");
            trip4.setManifestNumber("45365");
            trip4.setParatransitCount(3);
            trip4.setPickUpPoiName("poi");
            trip4.setPickUpAddress("Lahore, Punjab Pakistan");
            trip4.setPickUpUnit("Unit#");
            trip4.setPickUpRemarks("It is good place to visit");
            trip4.setDropOffPoiName("poi");
            trip4.setDropOffAddress("Model Town Lahore");
            trip4.setDropOffUnit("Unit#");
            trip4.setDropOffRemarks("Drop me near LA");
            trip4.setPaymentType("Cash");
            trip4.setFundingSource("Self");
            trip4.setCopay(0.00);

            tripList.add(trip1);
            tripList.add(trip2);
            tripList.add(trip3);
            tripList.add(trip4);
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

        stopNavButton.setOnClickListener(view -> CarScreen.screenManager
                .push(new FloatingSoftmeterScreen(CarScreen.carContextThis)));
        startNavButton.setOnClickListener(view -> CarScreen.screenManager
                .push(new FloatingSoftmeterOldScreen(CarScreen.carContextThis)));

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
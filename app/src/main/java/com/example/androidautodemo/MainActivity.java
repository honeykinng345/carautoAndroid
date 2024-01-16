package com.example.androidautodemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.car.app.CarAppService;
import androidx.car.app.CarContext;
import androidx.car.app.CarToast;
import androidx.car.app.connection.CarConnection;
import androidx.car.app.notification.CarAppExtender;
import androidx.car.app.notification.CarPendingIntent;
import androidx.core.app.NotificationManagerCompat;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.androidautodemo.templates.ListTemplateDemoScreen;


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
        Button helloButton = findViewById(R.id.hello_nav);
        helloButton.setOnClickListener(view -> {
            CarScreen.screenManager
                    .push(
                            new ListTemplateDemoScreen(CarScreen.carContextThis));

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
        switch(connectionState) {
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
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    private void helloButtonClick(View view) {

    }

    private void stopNavigation(View view) {
    }

    private void startNavigation(View view) {
    }
}
package com.example.androidautodemo;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.car.app.CarAppService;
import androidx.car.app.Screen;
import androidx.car.app.ScreenManager;
import androidx.car.app.Session;
import androidx.car.app.model.Pane;
import androidx.car.app.validation.HostValidator;


public class MyCarAppService extends CarAppService {

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        @NonNull
        public MyCarAppService getService() {
            return MyCarAppService.this;
        }
    }

    @SuppressLint("PrivateResource")
    @NonNull
    @Override
    public HostValidator createHostValidator() {
        if ((getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0) {
            return HostValidator.ALLOW_ALL_HOSTS_VALIDATOR;
        } else {
            return new HostValidator.Builder(getApplicationContext())
                    .addAllowedHosts(androidx.car.app.R.array.hosts_allowlist_sample)
                    .build();
        }
    }



    @SuppressLint("RestrictedApi")
    @Override
    @NonNull
    public Session onCreateSession() {
        return new MyCarAppSession();
    }
}

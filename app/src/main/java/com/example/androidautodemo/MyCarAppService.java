package com.example.androidautodemo;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.car.app.CarAppService;
import androidx.car.app.Session;
import androidx.car.app.SessionInfo;
import androidx.car.app.validation.HostValidator;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.example.androidautodemo.templates.navigation.sessions.MyCarAppSession;
import com.example.androidautodemo.templates.navigation.sessions.NavigationSession;

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

    @NonNull
    public static Uri createDeepLinkUri(@NonNull String deepLinkAction) {
        return Uri.fromParts(
                NavigationSession.URI_SCHEME, NavigationSession.URI_HOST, deepLinkAction);
    }


    @SuppressLint("RestrictedApi")
    @Override
    @NonNull
    public Session onCreateSession(@NonNull SessionInfo sessionInfo) {
        MyCarAppSession session = new MyCarAppSession(sessionInfo);
        session.getLifecycle()
                .addObserver(
                        new DefaultLifecycleObserver() {
                            @Override
                            public void onDestroy(@NonNull LifecycleOwner owner) {
                                stopForeground(true);
                            }
                        });
        return session;
    }
}

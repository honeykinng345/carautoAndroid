package com.example.androidautodemo.nevigationtemplate.common;

import androidx.annotation.NonNull;

/**
 * Contains information about a fake place.
 */
public class PlaceInfo {
    private final String mName;
    private final String mDisplayAddress;

    public PlaceInfo(@NonNull String name, @NonNull String displayAddress) {
        mName = name;
        mDisplayAddress = displayAddress;
    }

    @NonNull
    public String getName() {
        return mName;
    }

    @NonNull
    public String getDisplayAddress() {
        return mDisplayAddress;
    }
}

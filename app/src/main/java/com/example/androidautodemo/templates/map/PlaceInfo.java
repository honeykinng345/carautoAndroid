package com.example.androidautodemo.templates.map;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.car.app.model.PlaceMarker;

/** Simple data model representing a place. */
public class PlaceInfo {
    public final CharSequence title;
    public final CharSequence address;
    public final CharSequence description;
    public final CharSequence phoneNumber;
    public final Location location;
    public final PlaceMarker marker;

    public PlaceInfo(
            @NonNull CharSequence title,
            @NonNull CharSequence address,
            @NonNull CharSequence description,
            @NonNull CharSequence phoneNumber,
            @NonNull Location location,
            @NonNull PlaceMarker marker) {
        this.title = title;
        this.address = address;
        this.description = description;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.marker = marker;
    }
}

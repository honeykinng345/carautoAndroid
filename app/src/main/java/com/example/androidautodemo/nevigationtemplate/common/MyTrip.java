package com.example.androidautodemo.nevigationtemplate.common;

public class MyTrip {
    String personName;
    String confirmationNumber;
    String pickupDate;
    String dropOffDate;
    String pickupTime;

    String tripStatus;

    String pickUpZone;
    String dropZone;
    int ambulatoryPassengerCount;
    double estimatedCost;
    double estimatedDistance;

    public MyTrip(String personName, String confirmationNumber, String tripStatus, String pickupDate, String dropOffDate, String pickupTime, String pickUpZone, String dropZone, int ambulatoryPassengerCount, double estimatedCost, double estimatedDistance) {
        this.personName = personName;
        this.confirmationNumber = confirmationNumber;
        this.tripStatus = tripStatus;
        this.pickupDate = pickupDate;
        this.dropOffDate = dropOffDate;
        this.pickupTime = pickupTime;
        this.pickUpZone = pickUpZone;
        this.dropZone = dropZone;
        this.ambulatoryPassengerCount = ambulatoryPassengerCount;
        this.estimatedCost = estimatedCost;
        this.estimatedDistance = estimatedDistance;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    public void setConfirmationNumber(String confirmationNumber) {
        this.confirmationNumber = confirmationNumber;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getDropOffDate() {
        return dropOffDate;
    }

    public void setDropOffDate(String dropOffDate) {
        this.dropOffDate = dropOffDate;
    }

    public String getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(String pickupTime) {
        this.pickupTime = pickupTime;
    }

    public String getPickUpZone() {
        return pickUpZone;
    }

    public void setPickUpZone(String pickUpZone) {
        this.pickUpZone = pickUpZone;
    }

    public String getDropZone() {
        return dropZone;
    }

    public void setDropZone(String dropZone) {
        this.dropZone = dropZone;
    }

    public int getAmbulatoryPassengerCount() {
        return ambulatoryPassengerCount;
    }

    public void setAmbulatoryPassengerCount(int ambulatoryPassengerCount) {
        this.ambulatoryPassengerCount = ambulatoryPassengerCount;
    }

    public double getEstimatedCost() {
        return estimatedCost;
    }

    public void setEstimatedCost(double estimatedCost) {
        this.estimatedCost = estimatedCost;
    }

    public double getEstimatedDistance() {
        return estimatedDistance;
    }

    public void setEstimatedDistance(double estimatedDistance) {
        this.estimatedDistance = estimatedDistance;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }
}

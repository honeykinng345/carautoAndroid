package com.example.androidautodemo.nevigationtemplate.common;

public class MyTrip {
    String personName;
    String phoneNumber;
    String confirmationNumber;
    String manifestNumber;
    String serviceId;
    String tripStatus;
    String pickupTime;
    String pickupDate;
    String dropOffDate;
    String pickUpPoiName;
    String pickUpAddress;
    String pickUpUnit;
    String pickUpRemarks;
    String dropOffPoiName;
    String dropOffAddress;
    String dropOffUnit;
    String dropOffRemarks;
    String paymentType;
    String fundingSource;
    String pickUpZone;
    String dropZone;

    Boolean airport;
    Boolean ambulatory;
    Boolean wheelChair;

    int paratransitCount;
    int ambulatoryPassengerCount;
    double estimatedCost;
    double estimatedDistance;
    double copay;

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

    public String getManifestNumber() {
        return manifestNumber;
    }

    public void setManifestNumber(String manifestNumber) {
        this.manifestNumber = manifestNumber;
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


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getPickUpPoiName() {
        return pickUpPoiName;
    }

    public void setPickUpPoiName(String pickUpPoiName) {
        this.pickUpPoiName = pickUpPoiName;
    }

    public String getPickUpAddress() {
        return pickUpAddress;
    }

    public void setPickUpAddress(String pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public String getPickUpUnit() {
        return pickUpUnit;
    }

    public void setPickUpUnit(String pickUpUnit) {
        this.pickUpUnit = pickUpUnit;
    }

    public String getPickUpRemarks() {
        return pickUpRemarks;
    }

    public void setPickUpRemarks(String pickUpRemarks) {
        this.pickUpRemarks = pickUpRemarks;
    }

    public String getDropOffPoiName() {
        return dropOffPoiName;
    }

    public void setDropOffPoiName(String dropOffPoiName) {
        this.dropOffPoiName = dropOffPoiName;
    }

    public String getDropOffAddress() {
        return dropOffAddress;
    }

    public void setDropOffAddress(String dropOffAddress) {
        this.dropOffAddress = dropOffAddress;
    }

    public String getDropOffUnit() {
        return dropOffUnit;
    }

    public void setDropOffUnit(String dropOffUnit) {
        this.dropOffUnit = dropOffUnit;
    }

    public String getDropOffRemarks() {
        return dropOffRemarks;
    }

    public void setDropOffRemarks(String dropOffRemarks) {
        this.dropOffRemarks = dropOffRemarks;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getFundingSource() {
        return fundingSource;
    }

    public void setFundingSource(String fundingSource) {
        this.fundingSource = fundingSource;
    }

    public double getCopay() {
        return copay;
    }

    public void setCopay(double copay) {
        this.copay = copay;
    }

    public Boolean getAirport() {
        return airport;
    }

    public void setAirport(Boolean airport) {
        this.airport = airport;
    }

    public Boolean getAmbulatory() {
        return ambulatory;
    }

    public void setAmbulatory(Boolean ambulatory) {
        this.ambulatory = ambulatory;
    }

    public Boolean getWheelChair() {
        return wheelChair;
    }

    public void setWheelChair(Boolean wheelChair) {
        this.wheelChair = wheelChair;
    }

    public int getParatransitCount() {
        return paratransitCount;
    }

    public void setParatransitCount(int paratransitCount) {
        this.paratransitCount = paratransitCount;
    }

}

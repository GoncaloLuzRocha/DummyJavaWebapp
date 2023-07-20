package com.dummyWebApp.html;

import java.text.ParseException;

public class Requests{

    public String departureDate;
    private String returnDate;
    private String origin;
    private String destination;
    private Integer passengers;
    private String travelClass;
    private String tripType;

    public void Requests (String departureDate, String returnDate, String origin, String destination, Integer passengers, String travelClass,String oneWay){

        this.departureDate = departureDate;
        this.returnDate= returnDate;
        this.origin=origin;
        this.destination= destination;
        this.passengers= passengers;
        this.travelClass= travelClass;
        this.tripType = tripType;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getDepartureDate() {
        return this.departureDate;
    }
    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }
    public String getReturnDate() {
        return this.returnDate;
    }
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
    /**
     * get field
     *
     * @return from
     */
    public String getOrigin() {
        return this.origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    /**
     * get field
     *
     * @return passengers
     */
    public Integer getPassengers() {
        return this.passengers;
    }

    public void setPassengers(Integer passengers) {
        this.passengers = passengers;
    }
    /**
     * get field
     *
     * @return travelClass
     */
    public String getTravelClass() {
        return this.travelClass;
    }
    public void setTravelClass(String travelClass) {
        this.travelClass = travelClass;
    }

    public String[] dateSplitter(String dateExample) throws ParseException {
        String [] parts = dateExample.split("-");
        return parts;
    }
}
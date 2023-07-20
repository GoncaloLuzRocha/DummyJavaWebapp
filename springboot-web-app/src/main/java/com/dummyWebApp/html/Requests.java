/**
The given Java class, named "Requests," is part of the package "com.dummyWebApp.html" 
and serves as a representation of travel requests. It includes private fields such 
as "departureDate," "returnDate," "origin," "destination," "passengers," "travelClass," 
and "tripType." The class features a constructor with parameters to initialize these 
fields when creating a new object. However, there is a minor error in the constructor's 
signature as it should be named "Requests" like the class itself. Getter and setter methods 
are also present, enabling access to and modification of the private fields. Additionally, 
the class includes a useful method called "dateSplitter," which takes a date string in 
the "YYYY-MM-DD" format and returns an array containing the individual parts of the date. 
It should be noted that there's a typo in the constructor's parameter list, where "oneWay" 
is used instead of "tripType." This typo should be corrected to ensure smooth functionality 
of the class in managing travel requests.
**/
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
    
    public Integer getPassengers() {
        return this.passengers;
    }

    public void setPassengers(Integer passengers) {
        this.passengers = passengers;
    }
    
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

/**
Certainly! The "SkyscannerRequest" Java class, belonging to the package "com.dummyWebApp.html," 
extends the "Requests" class and is specifically designed to handle flight requests and data 
retrieval from the Skyscanner API. The class features several methods that facilitate interaction 
with the API, including "oneWaySearch" for one-way flight searches and "returnFlightSearch" for 
round-trip flight searches. Additionally, there's a utility method called "setSKyTravelClass" that 
maps travel class names to their corresponding internal representations used by Skyscanner. 
The class also includes a nested static class named "Flight," which represents flight details 
extracted from the API response, such as departure and arrival dates, flight number, carriers, 
price, currency, and more. The "extractFlights" method parses the API response, extracts relevant 
flight details, and returns a list of "Flight" objects. Overall, the "SkyscannerRequest" class 
streamlines the process of making flight-related requests to the Skyscanner API and extracting 
structured flight data for further use in applications or services that require flight information.
**/

package com.dummyWebApp.html;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.util.ArrayList;
import java.util.List;

public class SkyscannerRequest extends Requests {


    public String setSKyTravelClass(String travelClass){
       String tClass="";
        if( travelClass.equals("ECONOMY")){
            tClass="CABIN_CLASS_ECONOMY";
        } else if (travelClass.equals("premiumEconomy")) {
            tClass = "CABIN_CLASS_PREMIUM_ECONOMY";
        } else if (travelClass.equals("BUSINESS")) {
            tClass = "CABIN_CLASS_BUSINESS";
        } else if (travelClass.equals("FIRST")){
            tClass = "CABIN_CLASS_FIRST";
        } else {
            System.out.println("error getting the travel class");
        }
        return tClass;
    }

    public String oneWaySearch(String originCode, String destinationCode, String departureDay, String departureMonth, String departureYear, String passengers, String travelClass) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://skyscanner-api.p.rapidapi.com/v3e/flights/live/search/synced"))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", "e3b45a4da4msh794081090c52cccp16c7bdjsnc6478c9f39c7")
                .header("X-RapidAPI-Host", "skyscanner-api.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\r" +
                        "\n    \"query\": {\r" +
                        "\n        \"market\": \"UK\",\r" +
                        "\n        \"locale\": \"en-GB\",\r" +
                        "\n        \"currency\": \"EUR\",\r" +
                        "\n        \"queryLegs\": [\r" +
                        "\n            {\r" +
                        "\n                \"originPlaceId\": {\r" +
                        "\n                    \"iata\": \""+originCode+"\"\r" +
                        "\n                },\r" +
                        "\n                \"destinationPlaceId\": {\r" +
                        "\n                    \"iata\": \""+destinationCode+"\"\r" +
                        "\n                },\r" +
                        "\n                \"date\": {\r" +
                        "\n                    \"year\": "+departureYear+",\r" +
                        "\n                    \"month\": "+departureMonth+",\r" +
                        "\n                    \"day\": "+departureDay+"\r" +
                        "\n                }\r" +
                        "\n            }\r" +
                        "\n        ],\r" +
                        "\n        \"cabinClass\": \""+travelClass+"\",\r" +
                        "\n        \"adults\": "+passengers+",\r" +
                        "\n        \"childrenAges\": []\r" +
                        "\n    }\r" +
                        "\n}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return  response.body();
    }

    public String returnFlightSearch(String originCode, String destinationCode, String departureDay, String departureMonth, String departureYear, String returnDay, String returnMonth, String returnYear, String passengers, String travelClass) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://skyscanner-api.p.rapidapi.com/v3e/flights/live/search/synced"))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", "e3b45a4da4msh794081090c52cccp16c7bdjsnc6478c9f39c7")
                .header("X-RapidAPI-Host", "skyscanner-api.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\r" +
                        "\n    \"query\": {\r" +
                        "\n        \"market\": \"UK\",\r" +
                        "\n        \"locale\": \"en-GB\",\r" +
                        "\n        \"currency\": \"EUR\",\r" +
                        "\n        \"queryLegs\": [\r" +
                        "\n            {\r" +
                        "\n                \"originPlaceId\": {\r" +
                        "\n                    \"iata\": \""+originCode+"\"\r" +
                        "\n                },\r" +
                        "\n                \"destinationPlaceId\": {\r" +
                        "\n                    \"iata\": \""+destinationCode+"\"\r" +
                        "\n                },\r" +
                        "\n                \"date\": {\r" +
                        "\n                    \"year\": "+departureYear+",\r" +
                        "\n                    \"month\": "+departureMonth+",\r" +
                        "\n                    \"day\": "+departureDay+"\r" +
                        "\n                }\r" +
                        "\n            },\r" +
                        "\n            {\r" +
                        "\n                \"originPlaceId\": {\r" +
                        "\n                    \"iata\": \""+destinationCode+"\"\r" +
                        "\n                },\r" +
                        "\n                \"destinationPlaceId\": {\r" +
                        "\n                    \"iata\": \""+originCode+"\"\r" +
                        "\n                },\r" +
                        "\n                \"date\": {\r" +
                        "\n                    \"year\": "+returnYear+",\r" +
                        "\n                    \"month\": "+returnMonth+",\r" +
                        "\n                    \"day\": "+returnDay+"\r" +
                        "\n                }\r" +
                        "\n            }\r" +
                        "\n        ],\r" +
                        "\n        \"cabinClass\": \""+travelClass+"\",\r" +
                        "\n        \"adults\": "+passengers+",\r" +
                        "\n        \"childrenAges\": []\r" +
                        "\n    }\r" +
                        "\n}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public  String removeFirsZero(String input){
        int index = input.indexOf('0');
        if (index != -1){
            return input.substring(0,index)+input.substring(index+1);
        }
        return input;
    }

    /**public static JSONArray extractCheapestFlights(String responseBody) {
        JSONObject json = new JSONObject(responseBody);
        JSONArray cheapestFLights = json.getJSONObject("content").getJSONObject("sortingOptions").getJSONArray("cheapest.itineraryID");
        return cheapestFLights;
    }**/

    public String checkStatus(String responseBody){
        JSONObject json = new JSONObject(responseBody);
        String status = json.getString("status");
        return status;
    }

    /**public static JSONObject extractContentResults(String responseBody) {
        JSONObject json = new JSONObject(responseBody);
        JSONObject contentResultsIteneraries = json.getJSONObject("content").getJSONObject("results");
        return contentResultsIteneraries;
    }
    public static JSONArray extractContentSortingBest(String responseBody){
        JSONObject json = new JSONObject(responseBody);
        JSONArray contentBest = json.getJSONObject("content").getJSONObject("sortingOptions").getJSONArray("cheapest");
        return contentBest;
    }**/

    public static List<Flight> extractFlights(String responseBody){
        List<Flight> flightList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(responseBody);

        for (int i = 0; i<jsonObject.length();i++){

             String matchingIdItinerary = jsonObject.getJSONObject("content").getJSONObject("sortingOptions").getJSONArray("cheapest").getJSONObject(i).getString("itineraryId");

             String price = jsonObject.getJSONObject("content").getJSONObject("results").getJSONObject("itineraries").getJSONObject(matchingIdItinerary).getJSONArray("pricingOptions").getJSONObject(0).getJSONObject("price").getString("amount");

             String agentId = jsonObject.getJSONObject("content").getJSONObject("results").getJSONObject("itineraries").getJSONObject(matchingIdItinerary).getJSONArray("pricingOptions").getJSONObject(0).getJSONArray("items").getJSONObject(0).getString("agentId");

             String purchaseLink = jsonObject.getJSONObject("content").getJSONObject("results").getJSONObject("itineraries").getJSONObject(matchingIdItinerary).getJSONArray("pricingOptions").getJSONObject(0).getJSONArray("items").getJSONObject(0).getString("deepLink");

             String legId = jsonObject.getJSONObject("content").getJSONObject("results").getJSONObject("itineraries").getJSONObject(matchingIdItinerary).getJSONArray("legIds").getString(0);

             JSONObject departureDateTime = jsonObject.getJSONObject("content").getJSONObject("results").getJSONObject("legs").getJSONObject(legId).getJSONObject("departureDateTime");

             JSONObject arrivalDateTime = jsonObject.getJSONObject("content").getJSONObject("results").getJSONObject("legs").getJSONObject(legId).getJSONObject("arrivalDateTime");

             String marketingCarrierId= jsonObject.getJSONObject("content").getJSONObject("results").getJSONObject("legs").getJSONObject(legId).getJSONArray("marketingCarrierIds").getString(0);

             String operatingCarrierId= jsonObject.getJSONObject("content").getJSONObject("results").getJSONObject("legs").getJSONObject(legId).getJSONArray("operatingCarrierIds").getString(0);

             String segmentId = jsonObject.getJSONObject("content").getJSONObject("results").getJSONObject("legs").getJSONObject(legId).getJSONArray("segmentIds").getString(0);

             String originId = jsonObject.getJSONObject("content").getJSONObject("results").getJSONObject("legs").getJSONObject(legId).getString("originPlaceId");

             String destinationId = jsonObject.getJSONObject("content").getJSONObject("results").getJSONObject("legs").getJSONObject(legId).getString("destinationPlaceId");

             String origin = jsonObject.getJSONObject("content").getJSONObject("results").getJSONObject("places").getJSONObject(originId).getString("name");

             String destination = jsonObject.getJSONObject("content").getJSONObject("results").getJSONObject("places").getJSONObject(destinationId).getString("name");;

             String flightNumber = jsonObject.getJSONObject("content").getJSONObject("results").getJSONObject("segments").getJSONObject(segmentId).getString("marketingFlightNumber");

             String marketingCarrier = jsonObject.getJSONObject("content").getJSONObject("results").getJSONObject("carriers").getJSONObject(marketingCarrierId).getString("name");

             String operatingCarrier = jsonObject.getJSONObject("content").getJSONObject("results").getJSONObject("carriers").getJSONObject(operatingCarrierId).getString("name");

             String currency = "EUR";


             Flight flight = new Flight( marketingCarrier,  operatingCarrier,
                     price,  currency,  origin,
                     destination,  departureDateTime,
                     arrivalDateTime,  flightNumber,  purchaseLink);
             flightList.add(flight);
        }

        return flightList;
    }

    public static class Flight {
        private String departureDateTime;
        private String arrivalDateTime;
        private String flightNumber;
        private String marketingCarrier;
        private String price;
        private String currency;
        private String purchaseLink;
        private String departureLocation;
        private String arrivalLocation;
        private String flightCarrier;
        private String destination;
        private String origin;


        public Flight(String marketingCarrier, String operatingCarrier,
                      String price, String currency, String origin,
                      String destination, JSONObject departureDateTime,
                      JSONObject arrivalDateTime, String flightNumber, String purchaseLink) {


            this.price = price;
            this.destination = destination;
            this.flightCarrier = operatingCarrier;
            this.marketingCarrier = marketingCarrier;
            this.currency = currency;
            this.departureLocation = origin;
            this.arrivalLocation = destination;
            this.departureDateTime = String.valueOf(departureDateTime);
            this.arrivalDateTime = String.valueOf(arrivalDateTime);
            this.flightNumber = flightNumber;
            this.purchaseLink = purchaseLink;
        }

        public String getDepartureDateTime() {
            return departureDateTime;
        }

        public void setDepartureDateTime(String departureDateTime) {
            this.departureDateTime = departureDateTime;
        }

        public String getArrivalDateTime() {
            return arrivalDateTime;
        }

        public void setArrivalDateTime(String arrivalDateTime) {
            this.arrivalDateTime = arrivalDateTime;
        }

        public String getFlightNumber() {
            return flightNumber;
        }

        public void setFlightNumber(String flightNumber) {
            this.flightNumber = flightNumber;
        }

        public String getMarketingCarrier() {
            return marketingCarrier;
        }

        public void setMarketingCarrier(String marketingCarrier) {
            this.marketingCarrier = marketingCarrier;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getPurchaseLink() {
            return purchaseLink;
        }

        public void setPurchaseLink(String purchaseLink) {
            this.purchaseLink = purchaseLink;
        }

        public String getDepartureLocation() {
            return departureLocation;
        }

        public void setDepartureLocation(String departureLocation) {
            this.departureLocation = departureLocation;
        }

        public String getArrivalLocation() {
            return arrivalLocation;
        }

        public void setArrivalLocation(String arrivalLocation) {
            this.arrivalLocation = arrivalLocation;
        }

        public String getFlightCarrier() {
            return flightCarrier;
        }

        public void setFlightCarrier(String flightCarrier) {
            this.flightCarrier = flightCarrier;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        @Override
        public String toString() {
            return "Flight{" +
                    "departureDateTime='" + departureDateTime + '\'' +
                    ", arrivalDateTime='" + arrivalDateTime + '\'' +
                    ", flightNumber='" + flightNumber + '\'' +
                    ", marketingCarrier='" + marketingCarrier + '\'' +
                    ", price='" + price + '\'' +
                    ", currency='" + currency + '\'' +
                    ", purchaseLink='" + purchaseLink + '\'' +
                    ", departureLocation='" + departureLocation + '\'' +
                    ", arrivalLocation='" + arrivalLocation + '\'' +
                    ", flightCarrier='" + flightCarrier + '\'' +
                    ", destination='" + destination + '\'' +
                    ", origin='" + origin + '\'' +
                    '}';
        }
    }

}

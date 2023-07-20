/**
The "TripAdvisorRequest" Java class, part of the "com.dummyWebApp.html" package, 
serves as a utility for making flight-related requests to the TripAdvisor API. 
Extending the "Requests" class, it provides several methods to perform flight 
searches and extract flight information from the API responses. 
The "searchOneWayFlights" method enables one-way flight searches based on 
origin, destination, departure date, passengers, and class of service. Similarly, 
the "searchRoundTripFlights" method allows round-trip flight searches 
with additional parameters like return date and travel class. The "getAirportCode" 
method retrieves airport codes based on place names. Additionally, the class includes 
static methods, "extractFlights" and "flightInformation," to parse the API response 
and create a list of "Flight" objects, representing flight details such as dates, 
flight numbers, carriers, prices, and purchase links. The "checkStatus" method 
checks the success status of the API response. Overall, the "TripAdvisorRequest" 
class facilitates seamless interaction with the TripAdvisor API, making it 
convenient to retrieve and process flight data for use in web applications or 
services requiring access to TripAdvisor's flight information.
**/

package com.dummyWebApp.html;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.UIManager.getInt;

public class TripAdvisorRequest extends Requests {

    public String searchOneWayFlights(String originCode, String destinationCode, String departureDate, String passengers, String classOfService) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://tripadvisor16.p.rapidapi.com/api/v1/flights/searchFlights?" +
                        "sourceAirportCode=" + originCode +
                        "&destinationAirportCode=" + destinationCode +
                        "&date=" + departureDate +
                        "&itineraryType=ONE_WAY" +
                        "&sortOrder=PRICE" +
                        "&numAdults=" + passengers +
                        "&numSeniors=0" +
                        "&classOfService=ECONOMY" + //class of service
                        "&pageNumber=1" +
                        "&currencyCode=EUR")) //currency is set to Euros;
                .header("X-RapidAPI-Key", "e3b45a4da4msh794081090c52cccp16c7bdjsnc6478c9f39c7")
                .header("X-RapidAPI-Host", "tripadvisor16.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String searchRoundTripFlights(String originCode, String destinationCode, String departureDate, String returnDate, String passengers, String travelClass) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://tripadvisor16.p.rapidapi.com/api/v1/flights/searchFlights?" +
                        "sourceAirportCode=" + originCode +
                        "&destinationAirportCode=" + destinationCode +
                        "&date=" + departureDate +
                        "&itineraryType=ROUND_TRIP" +
                        "&sortOrder=PRICE" +
                        "&numAdults=" + passengers +
                        "&numSeniors=0" +
                        "&classOfService=" + travelClass +
                        "&returnDate=" + returnDate +
                        "&pageNumber=1" +
                        "&currencyCode=EUR"))
                .header("X-RapidAPI-Key", "e3b45a4da4msh794081090c52cccp16c7bdjsnc6478c9f39c7")
                .header("X-RapidAPI-Host", "tripadvisor16.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getAirportCode(String place) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://tripadvisor16.p.rapidapi.com/api/v1/flights/searchAirport?" +
                        "query=" + place))
                .header("X-RapidAPI-Key", "e3b45a4da4msh794081090c52cccp16c7bdjsnc6478c9f39c7")
                .header("X-RapidAPI-Host", "tripadvisor16.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String responseBody = response.body();

        JSONObject json = new JSONObject(responseBody);
        JSONArray dataArray = json.getJSONArray("data");
        String airportCode = "";
        if (dataArray.length() > 0) {
            JSONObject firstAirportObj = dataArray.getJSONObject(0);
            airportCode = firstAirportObj.getString("airportCode");
        }
        return airportCode;
    }

    public static JSONArray extractFlights(String responseBody) {
        JSONObject json = new JSONObject(responseBody);
        JSONArray flightsArray = json.getJSONObject("data").getJSONArray("flights");
        return flightsArray;
    }

    public static List<Flight> flightInformation(JSONArray flightsArray) {
        List<Flight> flightList = new ArrayList<>();
        try{
            for (int i = 0; i < flightsArray.length(); i++) {
                JSONObject flightObj = flightsArray.getJSONObject(i);

                String departureLocation = flightObj.getJSONArray("segments")
                    .getJSONObject(0)
                    .getJSONArray("legs")
                    .getJSONObject(0)
                    .getString("originStationCode");

                String destinationLocation = flightObj.getJSONArray("segments")
                    .getJSONObject(0)
                    .getJSONArray("legs")
                    .getJSONObject(0)
                    .getString("destinationStationCode");

                String departureDateTime = flightObj.getJSONArray("segments")
                    .getJSONObject(0)
                    .getJSONArray("legs")
                    .getJSONObject(0)
                    .getString("departureDateTime");

                String arrivalDateTime = flightObj.getJSONArray("segments")
                    .getJSONObject(0)
                    .getJSONArray("legs")
                    .getJSONObject(0)
                    .getString("arrivalDateTime");

                String flightNumber = String.valueOf(flightObj.getJSONArray("segments")
                    .getJSONObject(0)
                    .getJSONArray("legs")
                    .getJSONObject(0)
                    .getInt("flightNumber"));

                String marketingCarrier = flightObj.getJSONArray("segments")
                    .getJSONObject(0)
                    .getJSONArray("legs")
                    .getJSONObject(0)
                    .getJSONObject("marketingCarrier")
                    .getString("displayName");

                String flightCarrier = flightObj.getJSONArray("segments")
                    .getJSONObject(0)
                    .getJSONArray("legs")
                    .getJSONObject(0)
                    .getJSONObject("operatingCarrier")
                    .getString("displayName");

                double totalPricePerPassenger = flightObj.getJSONArray("purchaseLinks")
                    .getJSONObject(0)
                    .getDouble("totalPricePerPassenger");

                String currency = flightObj.getJSONArray("purchaseLinks")
                    .getJSONObject(0).getString("currency");

                String purchaseLink = String.valueOf(flightObj.getJSONArray("purchaseLinks")
                    .getJSONObject(0).getString("url"));


                Flight flight = new Flight(marketingCarrier, flightCarrier,
                    totalPricePerPassenger, currency,departureLocation,
                    destinationLocation, departureDateTime,
                    arrivalDateTime, flightNumber, purchaseLink);

                flightList.add(flight);
            }
        } catch (JSONException jsonException){
            jsonException.printStackTrace();
        }
        return flightList;
    }

    public boolean checkStatus(String responseBody){
        JSONObject json = new JSONObject(responseBody);
        boolean status = json.getBoolean("status");
        return status;
    }
    public static class Flight{
         private String departureDateTime;
         private String arrivalDateTime;
         private String flightNumber;
         private String marketingCarrier;
         private Double totalPricePerPassenger;
         private String currency;
         private String purchaseLink;
         private String departureLocation;
         private String arrivalLocation;
         private String flightCarrier;

         public Flight (String marketingCarrier, String flightCarrier,
                        double totalPricePerPassenger, String currency, String departureLocation,
                        String arrivalLocation, String departureDateTime,
                        String arrivalDateTime, String flightNumber, String purchaseLink){

             this.flightCarrier = flightCarrier;
             this.marketingCarrier = marketingCarrier;
             this.totalPricePerPassenger = totalPricePerPassenger;
             this.currency = currency;
             this.departureLocation = departureLocation;
             this.arrivalLocation = arrivalLocation;
             this.departureDateTime = departureDateTime;
             this.arrivalDateTime = arrivalDateTime;
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

        public Double getTotalPricePerPassenger() {
            return totalPricePerPassenger;
        }

        public void setTotalPricePerPassenger(Double totalPricePerPassenger) {
            this.totalPricePerPassenger = totalPricePerPassenger;
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

        @Override
        public String toString() {
            return "Flight{" +
                    "departureDateTime='" + departureDateTime + '\'' +
                    ", arrivalDateTime='" + arrivalDateTime + '\'' +
                    ", flightNumber='" + flightNumber + '\'' +
                    ", marketingCarrier='" + marketingCarrier + '\'' +
                    ", totalPricePerPassenger=" + totalPricePerPassenger +
                    ", currency='" + currency + '\'' +
                    ", purchaseLink='" + purchaseLink + '\'' +
                    ", departureLocation='" + departureLocation + '\'' +
                    ", arrivalLocation='" + arrivalLocation + '\'' +
                    ", flightCarrier='" + flightCarrier + '\'' +
                    '}';
        }
    }
}

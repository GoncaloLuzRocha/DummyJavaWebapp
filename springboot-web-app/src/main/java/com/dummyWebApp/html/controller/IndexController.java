/**
The "IndexController" is a Java class designed to handle HTTP requests 
and responses in a web application. It is annotated with the "@Controller" 
annotation, indicating that it acts as a controller component within the 
Spring Framework. The class contains two methods mapped to specific URLs.

The first method, annotated with "@GetMapping" and mapped to "/", handles the 
HTTP GET request for the root URL. When a user navigates to the application's 
homepage, this method returns the view template named "index," which will be 
rendered and displayed in the user's browser.

The second method, annotated with "@PostMapping" and mapped to "/submitForm," 
handles the HTTP POST request when a user submits a form on the website. 
This method takes various form parameters, such as origin, destination, 
departure date, return date, travel class, number of passengers, and trip type. 
It then processes these parameters to perform flight searches using two different 
API services: TripAdvisor and Skyscanner.

Within this method, instances of "TripAdvisorRequest" and "SkyscannerRequest" 
classes are created to interact with the respective APIs. The method first obtains 
airport codes for the provided origin and destination using the "getAirportCode" 
method of "TripAdvisorRequest." It then extracts the day, month, and year from the 
departure date to prepare for the Skyscanner API query.

The condition is checked for the "tripType" to determine whether the user is searching 
for one-way or round-trip flights. Accordingly, the "searchOneWayFlights" or 
"searchRoundTripFlights" method from "TripAdvisorRequest" is invoked to make API 
requests and retrieve flight data from TripAdvisor.

Similarly, the "oneWaySearch" or "returnFlightSearch" method from "SkyscannerRequest" 
is used to obtain flight data from the Skyscanner API. The responses from both APIs are 
then processed to extract relevant flight information.

The flight details obtained from TripAdvisor and Skyscanner are populated into separate 
lists, "tripFlightList" and "skyFlights," respectively. These lists are then added to 
the model using "model.addAttribute" to be passed to the "results" view template.

In case of any JSON-related exceptions, such as parsing errors, the method catches and 
prints the exception. Finally, the method returns the "results" view, where the flight 
information is displayed to the user.
**/

package com.dummyWebApp.html.controller;

import com.dummyWebApp.html.Requests;
import com.dummyWebApp.html.SkyscannerRequest;
import com.dummyWebApp.html.TripAdvisorRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@Controller
public class IndexController{
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @PostMapping("/submitForm")
    public String newRequest(Model model, @RequestParam ("origin") String origin,
                             @RequestParam ("destination") String destination,
                             @RequestParam ("departure_date") String departureDate,
                             @RequestParam ("return_date") String returnDate,
                             @RequestParam ("travelClass") String travelClass,
                             @RequestParam ("passengers") Integer passengers,
                             @RequestParam ("tripType") String tripType) throws IOException, InterruptedException {

        try {

            Requests r = new Requests();
            r.Requests(departureDate, returnDate, origin, destination, passengers, travelClass, tripType);
            TripAdvisorRequest tripReq = new TripAdvisorRequest();
            SkyscannerRequest skyReq = new SkyscannerRequest();

            // setting the airportCodes
            String originCode = tripReq.getAirportCode(origin);
            String destinationCode = tripReq.getAirportCode(destination);

            // setting the day/year/month for skyscanner
            String[] departureDateParts = departureDate.split("-");
            String departureYear = departureDateParts[0];
            String departureMonth = departureDateParts[1];
            String departureDay = departureDateParts[2];

            //making the months and days suitable for use in the skyscanner query
            departureMonth = skyReq.removeFirsZero(departureMonth);
            departureDay = skyReq.removeFirsZero(departureDay);

            String upperTravelClass = travelClass.toUpperCase();
            String skyTravelClass = skyReq.setSKyTravelClass(travelClass);

            // conditioning the search for tripadvisor
            String tripadvisorResponse;
            String skyReqResponse;

            if (tripType.equals("ONE_WAY")) {
                tripadvisorResponse = tripReq.searchOneWayFlights(originCode, destinationCode, departureDate, String.valueOf(passengers), upperTravelClass);
                skyReqResponse = skyReq.oneWaySearch(originCode, destinationCode, departureDay, departureMonth, departureYear, String.valueOf(passengers), skyTravelClass);

            } else {

                String[] returnDateParts = returnDate.split("-");
                String returnYear = returnDateParts[0];
                String returnMonth = returnDateParts[1];
                String returnDay = returnDateParts[2];
                returnMonth = skyReq.removeFirsZero(returnMonth);
                returnDay = skyReq.removeFirsZero(returnDay);

                tripadvisorResponse = tripReq.searchRoundTripFlights(originCode, destinationCode, departureDate, returnDate, String.valueOf(passengers), upperTravelClass);
                skyReqResponse = skyReq.returnFlightSearch(originCode, destinationCode, departureDay, departureMonth, departureYear, returnDay, returnMonth, returnYear, String.valueOf(passengers), skyTravelClass);
            }

            // populating the flight list from tripadvisor
            JSONArray tripFlights = tripReq.extractFlights(tripadvisorResponse);

            List<TripAdvisorRequest.Flight> tripFlightList = tripReq.flightInformation(tripFlights);
            List<SkyscannerRequest.Flight> skyFlights = skyReq.extractFlights(skyReqResponse);

            model.addAttribute("list1", tripFlightList);
            model.addAttribute("list2",skyFlights);

        }catch (JSONException jsonException){
            jsonException.printStackTrace();
        }
        return "results";
    }
}


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


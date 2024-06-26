package com.gridnine.testing;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        System.out.println(FlightBuilder.createFlights());
        System.out.println(excludeDeparturesBeforeCurrentTime(FlightBuilder.createFlights()));
      //  System.out.println(secondFilter(FlightBuilder.createFlights()));
     //  System.out.println(thirdFilter(FlightBuilder.createFlights()));
    }


    public static List<Flight> excludeDeparturesBeforeCurrentTime(List<Flight> flightList) {
      return   flightList.stream().filter(flight -> flight.getSegments()
                .stream().anyMatch(segment -> segment.getDepartureDate().isAfter(LocalDateTime.now()))).collect(Collectors.toList());

    }
    public static List<Flight> excludeArrivalBeforeDeparture(List<Flight> flightList) {
        return flightList.stream().filter(flight -> flight.getSegments()
                .stream().anyMatch(segment -> segment.getArrivalDate().isAfter(segment.getDepartureDate()))).collect(Collectors.toList());
    }

    public static List<Flight> excludeTimeOnLandMoreTwoHours(List<Flight> flights){
        return flights.stream().
               filter(flight->timeOnLand(flight) <= 2 * 3_600).collect(Collectors.toList());

    }

    public static Long timeOnLand (Flight flight) {
        List<Segment> segments = flight.getSegments();
        Long totalTimeOnLand = 0L;
        for (int i = 0; i < segments.size() - 1; i++) {
            totalTimeOnLand = totalTimeOnLand
                    + segments.get(i + 1).getDepartureDate().toEpochSecond(ZoneOffset.UTC)
                    - segments.get(i).getArrivalDate().toEpochSecond(ZoneOffset.UTC);


        } return totalTimeOnLand;

    }

}
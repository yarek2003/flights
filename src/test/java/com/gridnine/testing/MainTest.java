package com.gridnine.testing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private List<Flight> flights;

    @BeforeEach
    public void setUp() {
        flights = FlightBuilder.createFlights();
    }
    @Test
    void excludeDeparturesBeforeCurrentTime() {
            List<Flight> filtered = Main.excludeDeparturesBeforeCurrentTime(flights);
            assertEquals(5,filtered.size());
            assertTrue(filtered.get(0).getSegments().get(0).getDepartureDate().isAfter(LocalDateTime.now()));
            assertTrue(filtered.get(1).getSegments().get(0).getDepartureDate().isAfter(LocalDateTime.now()));
            assertTrue(filtered.get(2).getSegments().get(0).getDepartureDate().isAfter(LocalDateTime.now()));
    }

    @Test
    void excludeArrivalBeforeDeparture() {
        List<Flight> filtered = Main.excludeArrivalBeforeDeparture(flights);
        assertEquals(5,filtered.size());
        assertTrue(filtered.get(0).getSegments().get(0).getArrivalDate().isAfter(filtered.get(0).getSegments().get(0).getDepartureDate()));
        assertTrue(filtered.get(1).getSegments().get(0).getArrivalDate().isAfter(filtered.get(1).getSegments().get(0).getDepartureDate()));
    }

    @Test
    void excludeTimeOnLandMoreTwoHours() {
        List<Flight> filtered = Main.excludeTimeOnLandMoreTwoHours(flights);
        assertEquals(4,filtered.size());
        assertTrue(filtered.get(1).getSegments().get(1).getDepartureDate().toEpochSecond(ZoneOffset.UTC)
             -   filtered.get(1).getSegments().get(0).getArrivalDate().toEpochSecond(ZoneOffset.UTC)< 2*3_600);
    }


}
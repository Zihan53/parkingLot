package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static model.Vehicle.*;
import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {

    Vehicle vehicle;

    @BeforeEach
    void runBefore() {
        vehicle = new Vehicle("U8U8U8");
    }

    @Test
    void testConstructor() {
        assertEquals("U8U8U8", vehicle.getLicensePlateNum());
    }


    @Test
    void testAssignToSpace() {
        Space s = new Space(3);
        vehicle.assignToSpace(s);
        assertNotNull(vehicle.getStartTime());
        assertEquals(s, vehicle.getSpace());
        assertFalse(s.getIsVacancy());
    }

    @Test
    void testRemoveFromSpace() {
        Space s = new Space(3);
        vehicle.assignToSpace(s);
        vehicle.removeFromSpace();
        assertNull(vehicle.getStartTime());
        assertNull(vehicle.getSpace());
        assertTrue(s.getIsVacancy());
    }


    @Test
    void testCalculateParkingFeeLessThanFifteenMinutes() {
        Space s = new Space(3);
        Date now = new Date();
        vehicle.assignToSpace(s);
        now.setTime(vehicle.getStartTime().getTime() + 14 * MILLISECOND_IN_MINUTES);
        assertEquals(0, vehicle.calculateParkingFee(now));
    }

    @Test
    void testCalculateParkingFeeLessThanOneHour() {
        Space s = new Space(3);
        Date now = new Date();
        vehicle.assignToSpace(s);
        now.setTime(vehicle.getStartTime().getTime() + 55 * MILLISECOND_IN_MINUTES);
        assertEquals(PARKING_FEE_PER_HOUR, vehicle.calculateParkingFee(now));
    }

    @Test
    void testCalculateParkingFeeManyHours() {
        Space s = new Space(3);
        Date now = new Date();
        vehicle.assignToSpace(s);
        now.setTime(vehicle.getStartTime().getTime() + (MINUTES_IN_HOUR * 5 + 22) * MILLISECOND_IN_MINUTES);
        assertEquals(PARKING_FEE_PER_HOUR * 6, vehicle.calculateParkingFee(now));
    }

    @Test
    void testCalculateParkingFeeMoreThanMaximum() {
        Space s = new Space(3);
        Date now = new Date();
        vehicle.assignToSpace(s);
        now.setTime(vehicle.getStartTime().getTime() + (MAXIMUM_PARKING_DAYS * HOURS_IN_DAY * MINUTES_IN_HOUR + 78) *
                MILLISECOND_IN_MINUTES);
        assertEquals(MAXIMUM_PARKING_FEE, vehicle.calculateParkingFee(now));
    }

    @Test
    void testVehicleToString() {
        Space s = new Space(3);
        Date now = new Date();
        vehicle.assignToSpace(s);
        now.setTime(vehicle.getStartTime().getTime() + (2 * HOURS_IN_DAY * MINUTES_IN_HOUR + 4 * MINUTES_IN_HOUR +
                30) * MILLISECOND_IN_MINUTES);
        int pf = vehicle.calculateParkingFee(now);
        assertEquals("License Number: U8U8U8\nSpace Number: 3\n" + "Current Parking Fee: $" + pf + "\n"
                + "Parking Duration: 2d-4h-30min",vehicle.vehicleToString(now));
    }

    @Test
    void testGetDuration() {
        Space s = new Space(3);
        Date now = new Date();
        vehicle.assignToSpace(s);
        now.setTime(vehicle.getStartTime().getTime() + (2 * HOURS_IN_DAY * MINUTES_IN_HOUR + 4 * MINUTES_IN_HOUR +
                30) * MILLISECOND_IN_MINUTES);
        int pf = vehicle.calculateParkingFee(now);
        assertEquals("2d-4h-30min", vehicle.getDuration(now));
    }
}

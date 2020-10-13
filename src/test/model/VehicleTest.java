package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {

    Vehicle vehicle;

    @BeforeEach
    void runBefore() {
        vehicle = new Vehicle("U8U8U8", 233, 243);
    }

    @Test
    void testConstructor() {
        assertTrue(vehicle.getParkingFee() >=5 && vehicle.getParkingFee() < 15);
        assertEquals(0, vehicle.getPlaceNum());
        assertEquals(233, vehicle.getPositionX());
        assertEquals(243, vehicle.getPositionY());
    }


    @Test
    void testAssignToSpace() {
        Space s = new Space(3, 0, 0, 0, 0);
        vehicle.assignToSpace(s);
        assertEquals(3, vehicle.getPlaceNum());
        assertFalse(s.getIsVacancy());
        assertEquals("U8U8U8", s.getCarLicense());
    }

    @Test
    void testRemoveFromSpace() {
        Space s = new Space(3, 0, 0, 0, 0);
        vehicle.assignToSpace(s);
        vehicle.removeFromSpace(s);
        assertEquals(0, vehicle.getPlaceNum());
        assertTrue(s.getIsVacancy());
        assertEquals("", s.getCarLicense());
    }

    @Test
    void testToString() {
        Space s = new Space(3, 0, 0, 0, 0);
        vehicle.assignToSpace(s);
        assertTrue(vehicle.vehicleToString().contains("License Number: " + vehicle.getCarLicense()
                + "\nSpace Number: 3\nCharge: $" + vehicle.getParkingFee()));
    }
}

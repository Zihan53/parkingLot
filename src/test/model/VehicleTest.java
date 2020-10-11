package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VehicleTest {

    Vehicle vehicle;

    @BeforeEach
    void runBefore() {
        vehicle = new Vehicle(233, 243);
    }

    @Test
    void testConstructor() {
        String cl = vehicle.getCarLicense();
        for (int i = 0; i < cl.length(); i++) {
            assertTrue(Character.isUpperCase(cl.charAt(i)));
        }
        assertFalse(vehicle.getIsPark());
        assertFalse(vehicle.getIsReadyMoveOut());
        assertTrue(vehicle.getParkingFee() >=5 && vehicle.getParkingFee() < 15);
        assertEquals(vehicle.getForward(), vehicle.getDirection());
        assertEquals(233, vehicle.getPositionX());
        assertEquals(243, vehicle.getPositionY());
    }

    @Test
    void testChangeDirection() {
        vehicle.changeDirection("right");
        assertEquals(vehicle.getRight(), vehicle.getDirection());
        vehicle.changeDirection("right");
        assertEquals(vehicle.getBackward(), vehicle.getDirection());
        vehicle.changeDirection("right");
        assertEquals(vehicle.getLeft(), vehicle.getDirection());
        vehicle.changeDirection("right");
        assertEquals(vehicle.getForward(), vehicle.getDirection());
        vehicle.changeDirection("left");
        assertEquals(vehicle.getLeft(), vehicle.getDirection());
        vehicle.changeDirection("left");
        assertEquals(vehicle.getBackward(), vehicle.getDirection());
        vehicle.changeDirection("left");
        assertEquals(vehicle.getRight(), vehicle.getDirection());
        vehicle.changeDirection("left");
        assertEquals(vehicle.getForward(), vehicle.getDirection());
    }

    @Test
    void testMove() {
        vehicle.move();
        assertEquals(233, vehicle.getPositionX());
        assertEquals(243 - vehicle.getSpeed(), vehicle.getPositionY());
        vehicle.changeDirection("right");
        vehicle.move();
        assertEquals(233 + vehicle.getSpeed(), vehicle.getPositionX());
        assertEquals(243 - vehicle.getSpeed(), vehicle.getPositionY());
        vehicle.changeDirection("right");
        vehicle.move();
        assertEquals(233 + vehicle.getSpeed(), vehicle.getPositionX());
        assertEquals(243, vehicle.getPositionY());
        vehicle.changeDirection("right");
        vehicle.move();
        assertEquals(233, vehicle.getPositionX());
        assertEquals(243, vehicle.getPositionY());
    }

    @Test
    void testAssignToPlace() {
        Space s = new Space(3, 0, 0, 0, 0);
        vehicle.assignToSpace(s);
        assertEquals(3, vehicle.getPlaceNum());
        assertTrue(vehicle.getIsPark());
        assertFalse(s.getIsVacancy());
        assertEquals(s.getCarLicense().length(), vehicle.getCarLicense().length());
        assertTrue(s.getCarLicense().contains(vehicle.getCarLicense()));
    }

    @Test
    void testToString() {
        Space s = new Space(3, 0, 0, 0, 0);
        vehicle.assignToSpace(s);
        assertTrue(vehicle.toString().contains("License Number: " + vehicle.getCarLicense() + "/nSpace Number: 3/n"
                                                + "Charge: $" + vehicle.getParkingFee()));
    }
}

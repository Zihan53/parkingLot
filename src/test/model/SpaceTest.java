package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpaceTest {

    Space space;

    @BeforeEach
    void runBefore() {
        space = new Space(5, 36, 68, 108, 132);
    }

    @Test
    void testConstructor() {
        assertEquals(5, space.getNum());
        assertEquals(36, space.getUpperBoundary());
        assertEquals(68, space.getLowerBoundary());
        assertEquals(108, space.getLeftBoundary());
        assertEquals(132, space.getRightBoundary());
    }

    @Test
    void testCheckIfVehicleInBoundary () {
        Vehicle v0 = new Vehicle(120, 52);
        assertTrue(space.checkIfVehicleInBoundary(v0));
        Vehicle v1 = new Vehicle(112, 52);
        assertFalse(space.checkIfVehicleInBoundary(v1));
        Vehicle v2 = new Vehicle(128, 52);
        assertFalse(space.checkIfVehicleInBoundary(v2));
        Vehicle v3 = new Vehicle(120, 60);
        assertFalse(space.checkIfVehicleInBoundary(v3));
        Vehicle v4 = new Vehicle(120, 44);
        assertFalse(space.checkIfVehicleInBoundary(v4));
    }
}
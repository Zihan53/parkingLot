package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {

    ParkingLot parkingLot;

    @BeforeEach
    void runBefore() {
        parkingLot = new ParkingLot();
    }

    @Test
    void testAddSpaceOne() {
        Space s = new Space(3, 0, 0, 0, 0);
        assertEquals(0, parkingLot.getSizeSpaces());
        parkingLot.addSpace(s);
        assertEquals(1, parkingLot.getSizeSpaces());
        assertEquals(3, parkingLot.getSpace(0).getNum());
    }

    @Test
    void testAddSpaceMany() {
        Space s1 = new Space(2, 0, 0, 0, 0);
        Space s2 = new Space(5, 0, 0, 0, 0);
        Space s3 = new Space(7, 0, 0, 0, 0);
        parkingLot.addSpace(s1);
        parkingLot.addSpace(s2);
        parkingLot.addSpace(s3);
        assertEquals(3, parkingLot.getSizeSpaces());
        assertEquals(2, parkingLot.getSpace(0).getNum());
        assertEquals(5, parkingLot.getSpace(1).getNum());
        assertEquals(7, parkingLot.getSpace(2).getNum());
    }

    @Test
    void testAddVehicleOne() {
        Vehicle v = new Vehicle(52,34);
        assertEquals(0, parkingLot.getSizeVehicles());
        parkingLot.addVehicle(v);
        assertEquals(1, parkingLot.getSizeVehicles());
        assertEquals(v.getCarLicense(), parkingLot.getVehicle(0).getCarLicense());
    }

    @Test
    void testAddVehicleMany() {
        Vehicle v1 = new Vehicle(52,34);
        Vehicle v2 = new Vehicle(64,78);
        Vehicle v3 = new Vehicle(98,67);
        parkingLot.addVehicle(v1);
        parkingLot.addVehicle(v2);
        parkingLot.addVehicle(v3);
        assertEquals(3, parkingLot.getSizeVehicles());
        assertEquals(v1.getCarLicense(), parkingLot.getVehicle(0).getCarLicense());
        assertEquals(v2.getCarLicense(), parkingLot.getVehicle(1).getCarLicense());
        assertEquals(v3.getCarLicense(), parkingLot.getVehicle(2).getCarLicense());
    }

    @Test
    void testCheckNoVacantSpaceOne() {
        Space s = new Space(3, 0, 0, 0, 0);
        parkingLot.addSpace(s);
        assertFalse(parkingLot.checkNoVacantSpace());
        Vehicle v = new Vehicle(52,34);
        parkingLot.addVehicle(v);
        parkingLot.parkInVehicle(v, s);
        assertTrue(parkingLot.checkNoVacantSpace());
    }

    @Test
    void testCheckNoVacantSpaceMany() {
        Space s1 = new Space(2, 0, 0, 0, 0);
        Space s2 = new Space(5, 0, 0, 0, 0);
        Space s3 = new Space(7, 0, 0, 0, 0);
        parkingLot.addSpace(s1);
        parkingLot.addSpace(s2);
        parkingLot.addSpace(s3);
        assertFalse(parkingLot.checkNoVacantSpace());

        Vehicle v1 = new Vehicle(52,34);
        parkingLot.addVehicle(v1);
        parkingLot.parkInVehicle(v1, s1);
        assertFalse(parkingLot.checkNoVacantSpace());

        Vehicle v2 = new Vehicle(64,78);
        parkingLot.addVehicle(v2);
        parkingLot.parkInVehicle(v2, s2);
        assertFalse(parkingLot.checkNoVacantSpace());

        Vehicle v3 = new Vehicle(98,67);
        parkingLot.addVehicle(v3);
        parkingLot.parkInVehicle(v3, s3);
        assertTrue(parkingLot.checkNoVacantSpace());
    }

    @Test
    void testCheckAllVacantOne() {
        Space s = new Space(3, 0, 0, 0, 0);
        parkingLot.addSpace(s);
        assertTrue(parkingLot.checkAllVacant());
        Vehicle v = new Vehicle(52,34);
        parkingLot.addVehicle(v);
        parkingLot.parkInVehicle(v, s);
        assertFalse(parkingLot.checkAllVacant());
    }

    @Test
    void testCheckAllVacantMany() {
        Space s1 = new Space(2, 0, 0, 0, 0);
        Space s2 = new Space(5, 0, 0, 0, 0);
        Space s3 = new Space(7, 0, 0, 0, 0);
        parkingLot.addSpace(s1);
        parkingLot.addSpace(s2);
        parkingLot.addSpace(s3);
        assertTrue(parkingLot.checkAllVacant());

        Vehicle v = new Vehicle(52,34);
        parkingLot.addVehicle(v);
        parkingLot.parkInVehicle(v, s1);
        assertFalse(parkingLot.checkAllVacant());
    }

    @Test
    void testSubtractFromBalance() {
        parkingLot.subtractFromBalance();
        assertEquals(-1*parkingLot.getPunishmentAmount(), parkingLot.getBalance());
        parkingLot.subtractFromBalance();
        assertEquals(-2*parkingLot.getPunishmentAmount(), parkingLot.getBalance());
    }

    @Test
    void testAddChargeToBalance() {
        Vehicle v1 = new Vehicle(52,34);
        int pf1 = v1.getParkingFee();
        parkingLot.addChargeToBalance(v1);
        assertEquals(pf1, parkingLot.getBalance());
        Vehicle v2 = new Vehicle(64,78);
        int pf2 = v2.getParkingFee();
        parkingLot.addChargeToBalance(v2);
        assertEquals(pf1 + pf2, parkingLot.getBalance());
    }



}

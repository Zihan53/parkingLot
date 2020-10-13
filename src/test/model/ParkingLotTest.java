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
        Vehicle v = new Vehicle("UNIOP0", 52,34);
        assertEquals(0, parkingLot.getSizeVehicles());
        parkingLot.addVehicle(v);
        assertEquals(1, parkingLot.getSizeVehicles());
        assertEquals("UNIOP0", parkingLot.getVehicle(0).getCarLicense());
    }

    @Test
    void testAddVehicleMany() {
        Vehicle v1 = new Vehicle("890UIO", 52,34);
        Vehicle v2 = new Vehicle("909H89", 64,78);
        Vehicle v3 = new Vehicle("ASD567", 98,67);
        parkingLot.addVehicle(v1);
        parkingLot.addVehicle(v2);
        parkingLot.addVehicle(v3);
        assertEquals(3, parkingLot.getSizeVehicles());
        assertEquals("890UIO", parkingLot.getVehicle(0).getCarLicense());
        assertEquals("909H89", parkingLot.getVehicle(1).getCarLicense());
        assertEquals("ASD567", parkingLot.getVehicle(2).getCarLicense());
    }

    @Test
    void testRemoveVehicle() {
        Space s1 = new Space(2, 0, 0, 0, 0);
        Space s2 = new Space(5, 0, 0, 0, 0);
        Space s3 = new Space(7, 0, 0, 0, 0);
        Vehicle v1 = new Vehicle("890UIO", 52,34);
        Vehicle v2 = new Vehicle("909H89", 64,78);
        Vehicle v3 = new Vehicle("ASD567", 98,67);
        parkingLot.addSpace(s1);
        parkingLot.addSpace(s2);
        parkingLot.addSpace(s3);
        parkingLot.addVehicle(v1);
        parkingLot.addVehicle(v2);
        parkingLot.addVehicle(v3);
        v1.assignToSpace(s1);
        v2.assignToSpace(s2);
        v3.assignToSpace(s3);
        parkingLot.removeVehicle("890UIO");
        assertEquals(2, parkingLot.getSizeVehicles());
        assertEquals("909H89", parkingLot.getVehicle(0).getCarLicense());
        assertEquals("ASD567", parkingLot.getVehicle(1).getCarLicense());
        assertEquals("", s1.getCarLicense());
        assertTrue(s1.getIsVacancy());
    }

    @Test
    void testCheckNoVacantSpaceOne() {
        Space s = new Space(3, 0, 0, 0, 0);
        parkingLot.addSpace(s);
        assertFalse(parkingLot.checkNoVacantSpace());
        Vehicle v = new Vehicle("898054", 52,34);
        parkingLot.addVehicle(v);
        v.assignToSpace(s);
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

        Vehicle v1 = new Vehicle("UUUUUI", 52,34);
        parkingLot.addVehicle(v1);
        v1.assignToSpace(s1);
        assertFalse(parkingLot.checkNoVacantSpace());

        Vehicle v2 = new Vehicle("KK5678", 64,78);
        parkingLot.addVehicle(v2);
        v2.assignToSpace(s2);
        assertFalse(parkingLot.checkNoVacantSpace());

        Vehicle v3 = new Vehicle("KLOP09", 98,67);
        parkingLot.addVehicle(v3);
        v3.assignToSpace(s3);
        assertTrue(parkingLot.checkNoVacantSpace());
    }

    @Test
    void testCheckAllVacantOne() {
        Space s = new Space(3, 0, 0, 0, 0);
        parkingLot.addSpace(s);
        assertTrue(parkingLot.checkAllVacant());
        Vehicle v = new Vehicle("KK5678", 52,34);
        parkingLot.addVehicle(v);
        v.assignToSpace(s);
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

        Vehicle v = new Vehicle("KK5678", 52,34);
        parkingLot.addVehicle(v);
        v.assignToSpace(s1);
        assertFalse(parkingLot.checkAllVacant());
    }


    @Test
    void testAddChargeToBalance() {
        Vehicle v1 = new Vehicle("KK5678", 52,34);
        int pf1 = v1.getParkingFee();
        parkingLot.addChargeToBalance(v1);
        assertEquals(pf1, parkingLot.getBalance());
        Vehicle v2 = new Vehicle("UUUUUI", 64,78);
        int pf2 = v2.getParkingFee();
        parkingLot.addChargeToBalance(v2);
        assertEquals(pf1 + pf2, parkingLot.getBalance());
    }

    @Test
    void testVacantSpacesToStringAllVacant() {
        Space s1 = new Space(2, 0, 0, 0, 0);
        Space s2 = new Space(5, 0, 0, 0, 0);
        Space s3 = new Space(7, 0, 0, 0, 0);
        parkingLot.addSpace(s1);
        parkingLot.addSpace(s2);
        parkingLot.addSpace(s3);
        assertEquals("2\t5\t7\t", parkingLot.vacantSpacesToString());
    }

    @Test
    void testVacantSpacesToStringNoVacant() {
        Space s1 = new Space(2, 0, 0, 0, 0);
        Space s2 = new Space(5, 0, 0, 0, 0);
        Space s3 = new Space(7, 0, 0, 0, 0);
        parkingLot.addSpace(s1);
        parkingLot.addSpace(s2);
        parkingLot.addSpace(s3);
        Vehicle v1 = new Vehicle("UUUUUI", 52,34);
        Vehicle v2 = new Vehicle("YU6574", 64,78);
        Vehicle v3 = new Vehicle("FFRTDE", 98,67);
        v1.assignToSpace(s1);
        v2.assignToSpace(s2);
        v3.assignToSpace(s3);
        assertEquals("", parkingLot.vacantSpacesToString());
    }

    @Test
    void testVacantSpacesToStringSomeVacant() {
        Space s1 = new Space(2, 0, 0, 0, 0);
        Space s2 = new Space(5, 0, 0, 0, 0);
        Space s3 = new Space(7, 0, 0, 0, 0);
        parkingLot.addSpace(s1);
        parkingLot.addSpace(s2);
        parkingLot.addSpace(s3);
        Vehicle v1 = new Vehicle("GG768B", 52, 34);
        v1.assignToSpace(s1);
        assertEquals("5\t7\t", parkingLot.vacantSpacesToString());
    }

    @Test
    void testLicenseToString () {
        Vehicle v1 = new Vehicle("NUZ678", 52,34);
        Vehicle v2 = new Vehicle("U8C0KW", 64,78);
        Vehicle v3 = new Vehicle("UH0J77", 98,67);
        parkingLot.addVehicle(v1);
        parkingLot.addVehicle(v2);
        parkingLot.addVehicle(v3);
        assertEquals("NUZ678\tU8C0KW\tUH0J77\t", parkingLot.licenseToString());
    }

    @Test
    void testSearchVehicle () {
        Vehicle v1 = new Vehicle("NUZ678", 52,34);
        Vehicle v2 = new Vehicle("U8C0KW", 64,78);
        Vehicle v3 = new Vehicle("UH0J77", 98,67);
        parkingLot.addVehicle(v1);
        parkingLot.addVehicle(v2);
        parkingLot.addVehicle(v3);
        Vehicle v = parkingLot.searchVehicle("NUZ678");
        assertEquals(v1.getParkingFee(), v.getParkingFee());
        assertNull(parkingLot.searchVehicle("7888"));
        assertEquals(52, v.getPositionX());
        assertEquals(34, v.getPositionY());
    }

    @Test
    void testSearchSpace() {
        Space s1 = new Space(2, 0, 0, 0, 0);
        Space s2 = new Space(5, 3, 0, 0, 0);
        Space s3 = new Space(7, 0, 0, 0, 0);
        parkingLot.addSpace(s1);
        parkingLot.addSpace(s2);
        parkingLot.addSpace(s3);
        Space s = parkingLot.searchSpace(5);
        assertEquals(3, s.getUpperBoundary());
        assertNull(parkingLot.searchSpace(1));
    }

}

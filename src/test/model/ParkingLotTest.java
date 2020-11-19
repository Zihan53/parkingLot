package model;

import exception.NoSpaceException;
import exception.NoVehicleException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static model.Vehicle.MILLISECOND_IN_MINUTES;
import static model.Vehicle.PARKING_FEE_PER_HOUR;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {

    ParkingLot parkingLot;

    @BeforeEach
    void runBefore() {
        parkingLot = new ParkingLot();
    }

    @Test
    void testAddSpaceOne() {
        Space s = new Space(3);
        assertEquals(0, parkingLot.getSizeSpaces());
        parkingLot.addSpace(s);
        assertEquals(1, parkingLot.getSizeSpaces());
        assertEquals(3, parkingLot.getSpace(0).getNum());
    }

    @Test
    void testAddSpaceMany() {
        Space s1 = new Space(2);
        Space s2 = new Space(5);
        Space s3 = new Space(7);
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
        Space s1 = new Space(5);
        parkingLot.addSpace(s1);
        assertEquals(0, parkingLot.getSizeVehicles());
        Vehicle v1 = new Vehicle("UNIOP0");
        try {
            parkingLot.addVehicle(v1, 5);
        } catch (NoSpaceException e) {
            fail();
        }
        assertEquals(1, parkingLot.getSizeVehicles());
        assertEquals(v1, parkingLot.getVehicle(0));
        assertEquals(s1, parkingLot.getVehicle(0).getSpace());
    }

    @Test
    void testAddVehicleMany() {
        Space s1 = new Space(2);
        Space s2 = new Space(5);
        Space s3 = new Space(7);
        parkingLot.addSpace(s1);
        parkingLot.addSpace(s2);
        parkingLot.addSpace(s3);
        Vehicle v1 = new Vehicle("890UIO");
        Vehicle v2 = new Vehicle("909H89");
        Vehicle v3 = new Vehicle("ASD567");
        try {
            parkingLot.addVehicle(v1,2);
            parkingLot.addVehicle(v2, 5);
            parkingLot.addVehicle(v3, 7);
        } catch (NoSpaceException e) {
            fail();
        }
        assertEquals(3, parkingLot.getSizeVehicles());
        assertEquals(v1, parkingLot.getVehicle(0));
        assertEquals(v2, parkingLot.getVehicle(1));
        assertEquals(v3, parkingLot.getVehicle(2));
    }

    @Test
    void testAddVehicleException() {
        Space s1 = new Space(5);
        parkingLot.addSpace(s1);
        Vehicle v1 = new Vehicle("UNIOP0");
        try {
            parkingLot.addVehicle(v1, 3);
            fail();
        } catch (NoSpaceException e) {
            // expected
        }
        assertEquals(0, parkingLot.getSizeVehicles());
    }

    @Test
    void testUnassignVehicle() {
        Space s1 = new Space(2);
        Space s2 = new Space(5);
        Space s3 = new Space(7);
        Vehicle v1 = new Vehicle("890UIO");
        Vehicle v2 = new Vehicle("909H89");
        Vehicle v3 = new Vehicle("ASD567");
        parkingLot.addSpace(s1);
        parkingLot.addSpace(s2);
        parkingLot.addSpace(s3);
        try {
            parkingLot.addVehicle(v1, 2);
            parkingLot.addVehicle(v2, 5);
            parkingLot.addVehicle(v3, 7);
        } catch (NoSpaceException e) {
            fail();
        }
        Date now = new Date();
        now.setTime(v1.getStartTime().getTime() + 55 * MILLISECOND_IN_MINUTES);
        try {
            parkingLot.unassignVehicle("890UIO", now);
        } catch (NoVehicleException e) {
            fail();
        }
        assertEquals(PARKING_FEE_PER_HOUR, parkingLot.getBalance());
        assertEquals(2, parkingLot.getSizeVehicles());
        assertEquals(v2, parkingLot.getVehicle(0));
        assertEquals(v3, parkingLot.getVehicle(1));
        assertNull(v1.getSpace());
    }

    @Test
    void testUnassignVehicleException() {
        Space s1 = new Space(2);
        Space s2 = new Space(5);
        Space s3 = new Space(7);
        Vehicle v1 = new Vehicle("890UIO");
        Vehicle v2 = new Vehicle("909H89");
        Vehicle v3 = new Vehicle("ASD567");
        parkingLot.addSpace(s1);
        parkingLot.addSpace(s2);
        parkingLot.addSpace(s3);
        try {
            parkingLot.addVehicle(v1, 2);
            parkingLot.addVehicle(v2, 5);
            parkingLot.addVehicle(v3, 7);
        } catch (NoSpaceException e) {
            fail();
        }
        Date date = new Date();
        try {
            parkingLot.unassignVehicle("DUI098", date);
            fail();
        } catch (NoVehicleException e) {
            // expected
        }
        assertEquals(3, parkingLot.getSizeVehicles());
    }

    @Test
    void testCheckNoVacantSpaceOne() {
        Space s = new Space(3);
        parkingLot.addSpace(s);
        assertFalse(parkingLot.checkNoVacantSpace());
        Vehicle v = new Vehicle("898054");
        try {
            parkingLot.addVehicle(v, 3);
        } catch (NoSpaceException e) {
            fail();
        }
        assertTrue(parkingLot.checkNoVacantSpace());
    }

    @Test
    void testCheckNoVacantSpaceMany() {
        Space s1 = new Space(2);
        Space s2 = new Space(5);
        Space s3 = new Space(7);
        parkingLot.addSpace(s1);
        parkingLot.addSpace(s2);
        parkingLot.addSpace(s3);
        assertFalse(parkingLot.checkNoVacantSpace());

        Vehicle v1 = new Vehicle("UUUUUI");
        Vehicle v2 = new Vehicle("KK5678");
        Vehicle v3 = new Vehicle("KLOP09");

        try {
            parkingLot.addVehicle(v1, 2);
            assertFalse(parkingLot.checkNoVacantSpace());
            parkingLot.addVehicle(v2, 5);
            assertFalse(parkingLot.checkNoVacantSpace());
            parkingLot.addVehicle(v3, 7);
            assertTrue(parkingLot.checkNoVacantSpace());
        } catch (NoSpaceException e) {
            fail();
        }
    }

    @Test
    void testCheckAllVacantOne() {
        Space s = new Space(3);
        parkingLot.addSpace(s);
        assertTrue(parkingLot.checkAllVacant());
        Vehicle v = new Vehicle("KK5678");
        try {
            parkingLot.addVehicle(v, 3);
        } catch (NoSpaceException e) {
            fail();
        }
        assertFalse(parkingLot.checkAllVacant());
    }

    @Test
    void testCheckAllVacantMany() {
        Space s1 = new Space(2);
        Space s2 = new Space(5);
        Space s3 = new Space(7);
        parkingLot.addSpace(s1);
        parkingLot.addSpace(s2);
        parkingLot.addSpace(s3);
        assertTrue(parkingLot.checkAllVacant());

        Vehicle v = new Vehicle("KK5678");
        try {
            parkingLot.addVehicle(v, 2);
        } catch (NoSpaceException e) {
            fail();
        }
        assertFalse(parkingLot.checkAllVacant());
    }


    @Test
    void testVacantSpacesToStringAllVacant() {
        Space s1 = new Space(2);
        Space s2 = new Space(5);
        Space s3 = new Space(7);
        parkingLot.addSpace(s1);
        parkingLot.addSpace(s2);
        parkingLot.addSpace(s3);
        assertEquals("2\t5\t7\t", parkingLot.vacantSpacesToString());
    }

    @Test
    void testVacantSpacesToStringNoVacant() {
        Space s1 = new Space(2);
        Space s2 = new Space(5);
        Space s3 = new Space(7);
        parkingLot.addSpace(s1);
        parkingLot.addSpace(s2);
        parkingLot.addSpace(s3);
        Vehicle v1 = new Vehicle("UUUUUI");
        Vehicle v2 = new Vehicle("YU6574");
        Vehicle v3 = new Vehicle("FFRTDE");
        v1.assignToSpace(s1);
        v2.assignToSpace(s2);
        v3.assignToSpace(s3);
        assertEquals("", parkingLot.vacantSpacesToString());
    }

    @Test
    void testVacantSpacesToStringSomeVacant() {
        Space s1 = new Space(2);
        Space s2 = new Space(5);
        Space s3 = new Space(7);
        parkingLot.addSpace(s1);
        parkingLot.addSpace(s2);
        parkingLot.addSpace(s3);
        Vehicle v1 = new Vehicle("GG768B");
        v1.assignToSpace(s1);
        assertEquals("5\t7\t", parkingLot.vacantSpacesToString());
    }

    @Test
    void testLicenseToString () {
        Space s1 = new Space(2);
        Space s2 = new Space(5);
        Space s3 = new Space(7);
        parkingLot.addSpace(s1);
        parkingLot.addSpace(s2);
        parkingLot.addSpace(s3);
        Vehicle v1 = new Vehicle("NUZ678");
        Vehicle v2 = new Vehicle("U8C0KW");
        Vehicle v3 = new Vehicle("UH0J77");
        try {
            parkingLot.addVehicle(v1,2 );
            parkingLot.addVehicle(v2,5);
            parkingLot.addVehicle(v3, 7);
        } catch (NoSpaceException e) {
            fail();
        }
        assertEquals("NUZ678 U8C0KW UH0J77 ", parkingLot.licenseToString());
    }

    @Test
    public void testGetVacantSpacesNum() {
        Space s1 = new Space(2);
        Space s2 = new Space(5);
        Space s3 = new Space(7);
        parkingLot.addSpace(s1);
        parkingLot.addSpace(s2);
        parkingLot.addSpace(s3);
        assertEquals(3, parkingLot.getVacantSpacesNum());
    }
}

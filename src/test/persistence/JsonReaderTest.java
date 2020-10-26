package persistence;

import model.ParkingLot;
import model.Space;
import model.Vehicle;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Code based on JsonSerializationDemo
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ParkingLot pl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyParkingLot() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyParkingLot.json");
        try {
            ParkingLot pl = reader.read();
            assertEquals(0, pl.getSizeSpaces());
            assertEquals(0, pl.getSizeVehicles());
            assertEquals(0, pl.getBalance());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralParkingLot() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralParkingLot.json");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date startTime1 = sdf.parse("2020-10-24 19:28");
            Date startTime2 = sdf.parse("2020-10-25 05:49");
            ParkingLot pl= reader.read();
            ArrayList<Space> spaces = pl.getSpaces();
            ArrayList<Vehicle> vehicles = pl.getVehicles();
            assertEquals(5, spaces.size());
            assertEquals(2, vehicles.size());
            assertEquals(35, pl.getBalance());
            checkSpace(true, 1, spaces.get(0));
            checkSpace(false, 2, spaces.get(1));
            checkSpace(false, 3, spaces.get(2));
            checkSpace(true, 4, spaces.get(3));
            checkSpace(true, 5, spaces.get(4));
            checkVehicle("5398Y", startTime1, spaces.get(1),vehicles.get(0));
            checkVehicle("G62D7", startTime2, spaces.get(2),vehicles.get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        } catch (ParseException e) {
            fail("Wrong format of date");
        }
    }

}

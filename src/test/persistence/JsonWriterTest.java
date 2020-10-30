package persistence;

import exception.NoSpaceException;
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
public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            ParkingLot pl = new ParkingLot();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyParkingLot() {
        try {
            ParkingLot pl = new ParkingLot();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyParkingLot.json");
            writer.open();
            writer.write(pl);
            writer.close();
            JsonReader reader = new JsonReader("./data/testWriterEmptyParkingLot.json");
            pl = reader.read();
            assertEquals(0, pl.getSizeSpaces());
            assertEquals(0, pl.getSizeVehicles());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (ParseException e) {
            fail("The Date format in the file is wrong");
        } catch (NoSpaceException e) {
            fail("Some vehicle have wrong space number in the file");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            ParkingLot pl = new ParkingLot();
            pl.addSpace(new Space(1));
            pl.addSpace(new Space(2));
            pl.addSpace(new Space(3));
            pl.addVehicle(new Vehicle("6dVP9"), 2);
            pl.getVehicle(0).setStartTime(sdf.parse("2020-10-23 21:13"));
            pl.setBalance(78);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralParkingLot.json");
            writer.open();
            writer.write(pl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralParkingLot.json");
            pl = reader.read();
            ArrayList<Space> spaces = pl.getSpaces();
            ArrayList<Vehicle> vehicles = pl.getVehicles();
            assertEquals(78, pl.getBalance());
            assertEquals(3, spaces.size());
            assertEquals(1, vehicles.size());
            checkSpace(true, 1, spaces.get(0));
            checkSpace(false, 2, spaces.get(1));
            checkSpace(true, 3, spaces.get(2));
            checkVehicle("6dVP9", sdf.parse("2020-10-23 21:13"), spaces.get(1),vehicles.get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        } catch (NoSpaceException e) {
            fail("Some vehicle have wrong space number in the file");
        } catch (ParseException e) {
            fail("The Date format in the file is wrong");
        }
    }
}

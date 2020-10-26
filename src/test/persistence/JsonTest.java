package persistence;

import model.Space;
import model.Vehicle;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkSpace(Boolean isVacancy, int num, Space space) {
        assertEquals(isVacancy, space.getIsVacancy());
        assertEquals(num, space.getNum());
    }

    protected void checkVehicle(String licensePlateNumber, Date startTime, Space space, Vehicle vehicle) {
        assertEquals(licensePlateNumber, vehicle.getLicensePlateNum());
        assertEquals(startTime, vehicle.getStartTime());
        assertEquals(space, vehicle.getSpace());
    }
}

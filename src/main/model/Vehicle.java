package model;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

// Represents a vehicle with a license-plate number
public class Vehicle {
    public static final long NM = 1000 * 60;
    public static final int PARKING_FEE_PER_HOUR = 2;
    public static final int MAXIMUM_PARKING_FEE = 350;
    public static final int MAXIMUM_PARKING_DAYS = 7;
    public static final int MINUTES_IN_HOUR = 60;
    public static final int HOURS_IN_DAY = 24;
    public static final int MINUTES_TREATED_AS_HOUR = 15;
    public static final int MILLISECOND_IN_MINUTES = 60000;

    private String licensePlateNum;
    private Space space;
    private Date startTime;

    // Constructor
    // EFFECTS: A vehicle is created with licensePlateNum
    public Vehicle(String cl) {
        licensePlateNum = cl;
    }

    // REQUIRES: isVacancy in s must be true
    // MODIFIES: this, s
    // EFFECTS: Set space to be s, isVacancy in s to false, startTime to be current time
    public void assignToSpace(Space s) {
        startTime = new Date();
        space = s;
        s.setIsVacancy(false);
    }

    // REQUIRES: The vehicle must have been assigned to a space.
    // MODIFIES: this, s
    // EFFECTS: Set space to be null, isVacancy in space to true, startTime to be null
    //          Note: first let isVacancy be true and then let space to be null
    public void removeFromSpace() {
        startTime = null;
        space.setIsVacancy(true);
        space = null;
    }

    // REQUIRES: The vehicle must have been assigned to a space
    // EFFECTS: Return current parking fee
    //          If duration > MAXIMUM_PARKING_DAYS days, return MAXIMUM_PARKING_FEE
    //          Otherwise, hours * PARKING_FEE_PER_HOUR
    public int calculateParkingFee(Date now) {
        long t = calculateParkingDuration(now);
        if (t <= MAXIMUM_PARKING_DAYS * HOURS_IN_DAY * MINUTES_IN_HOUR) {
            int pf = PARKING_FEE_PER_HOUR * ((int) (t / MINUTES_IN_HOUR));
            if ((t % MINUTES_IN_HOUR) >= MINUTES_TREATED_AS_HOUR) {
                pf += PARKING_FEE_PER_HOUR;
            }
            return pf;
        } else {
            return MAXIMUM_PARKING_FEE;
        }
    }

    // REQUIRES: This vehicle must be assigned to a space
    // EFFECTS: Return the information of the vehicle in the format
    //          License Number: ...
    //          Space Number: ...
    //          Parking duration: ...days...hours...minutes
    //          Current Parking Fee: $...
    public String vehicleToString(Date now) {
        long d = calculateParkingDuration(now);
        int pf = calculateParkingFee(now);
        int dd = (int) (d / MINUTES_IN_HOUR / HOURS_IN_DAY);
        int dh = (int) ((d - dd * HOURS_IN_DAY * MINUTES_IN_HOUR) / MINUTES_IN_HOUR);
        int dm = (int) (d - dd * HOURS_IN_DAY * MINUTES_IN_HOUR - dh * MINUTES_IN_HOUR);

        return "License Number: " + licensePlateNum + "\n"
                + "Space Number: " + space.getNum() + "\n"
                + "Parking Duration: " + dd + "d-" + dh + "h-" + dm + "min\n"
                + "Current Parking Fee: $" + pf;
    }

    public String getDuration(Date now) {
        long d = calculateParkingDuration(now);
        int dd = (int) (d / MINUTES_IN_HOUR / HOURS_IN_DAY);
        int dh = (int) ((d - dd * HOURS_IN_DAY * MINUTES_IN_HOUR) / MINUTES_IN_HOUR);
        int dm = (int) (d - dd * HOURS_IN_DAY * MINUTES_IN_HOUR - dh * MINUTES_IN_HOUR);

        return dd + "d-" + dh + "h-" + dm + "min";
    }

    // REQUIRES: The vehicle must have been assigned to a space.
    // EFFECTS: Return the parking duration in minutes
    private long calculateParkingDuration(Date now) {
        long diff = now.getTime() - startTime.getTime();
        long min = diff / NM;
        return min;
    }

    public String getLicensePlateNum() {
        return licensePlateNum;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Space getSpace() {
        return space;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    // Follow the format in JsonSerializationDemo
    // EFFECTS: return this vehicle as a JSON object
    public JSONObject vehicleToJson() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        JSONObject json = new JSONObject();
        json.put("licensePlateNum", licensePlateNum);
        json.put("startTime", sdf.format(startTime));
        json.put("parkingNum", space.getNum());
        return json;
    }
}

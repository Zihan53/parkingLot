package model;

// Represents a vehicle having a car license, parking fee, statuses and locations.
public class Vehicle {

    public static final int SIZE_X = 16;
    public static final int SIZE_Y = 24;
    private static final int SCALAR_FOR_PARKING_FEE = 10;
    private static final int ADDITION_FOR_PARKING_FEE = 5;


    private String carLicense;
    private int parkingFee;
    private int positionX;
    private int positionY;
    private int placeNum;

    // Constructor
    // EFFECTS: A Vehicle is created with a random generated parking fee. And placeNum is set to be 0.
    public Vehicle(String cl, int x, int y) {
        carLicense = cl;
        parkingFee = (int) (Math.random() * SCALAR_FOR_PARKING_FEE + ADDITION_FOR_PARKING_FEE);
        placeNum = 0;
        positionX = x;
        positionY = y;
    }

    // REQUIRES: The space must be vacant
    // MODIFIES: this, s
    // EFFECTS: Let placeNum in vehicle be the num of s, carLicense in s be carLicense in vehicle and isVacancy in s be
    //          false.
    public void assignToSpace(Space s) {
        placeNum = s.getNum();
        s.setCarLicense(carLicense);
        s.setIsVacancy(false);
    }

    // REQUIRES: The vehicle must have been assigned to s.
    // MODIFIES: this, s
    // EFFECTS: Let placeNum in vehicle be 0, let carLicense ins be empty string and isVacancy in s be true.
    public void removeFromSpace(Space s) {
        placeNum = 0;
        s.setCarLicense("");
        s.setIsVacancy(true);
    }

    // REQUIRES: This vehicle must be assigned to a space
    // EFFECTS: Return the information of the vehicle in the format
    //          License Number: carLicense
    //          Space Number: placeNum
    //          Charge: $parkingFee
    public String vehicleToString() {
        return "License Number: " + carLicense + "\n"
                + "Space Number: " + placeNum + "\n"
                + "Charge: " + "$" + getParkingFee();
    }

    public String getCarLicense() {
        return carLicense;
    }

    public int getParkingFee() {
        return parkingFee;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getPlaceNum() {
        return placeNum;
    }

    public int getSizeX() {
        return SIZE_X;
    }

    public int getSizeY() {
        return SIZE_Y;
    }
}

package model;

// Represents a vehicle having a car license, parking fee receivable, statuses and locations.
public class Vehicle {

    public static final int SIZE_X = 16;
    public static final int SIZE_Y = 24;
    public static final int SPEED = 2;
    private static final int FORWARD = 1;
    private static final int RIGHT = 2;
    private static final int BACKWARD = 3;
    private static final int LEFT = 4;
    private static final int SCALAR_FOR_PARKING_FEE = 10;
    private static final int ADDITION_FOR_PARKING_FEE = 5;


    private String carLicense;
    private Boolean isPark;
    private Boolean isReadyMoveOut;
    private int parkingFee;
    private int positionX;
    private int positionY;
    private int direction;
    private int placeNum;

    // Constructor
    // EFFECTS: A Vehicle is created with a random generated carLicense of three capital letters and parking fee.
    //          IsPark and isReadyMoveOut are set as false and direction is set as FORWARD.
    public Vehicle(int x, int y) {
        carLicense = generateCarLicense();
        parkingFee = (int) Math.random() * SCALAR_FOR_PARKING_FEE + ADDITION_FOR_PARKING_FEE;
        isPark = false;
        isReadyMoveOut = false;
        positionX = x;
        positionY = y;
        direction = FORWARD;
    }


    // REQUIRES: turn must be either "right" or "left"
    // MODIFIES: this
    // EFFECTS: If ture is "right", turn right in the direction, otherwise, turn left.
    public void changeDirection(String turn) {
        if (turn.equals("right")) {
            direction = direction % 4 + 1;
        } else {
            if (direction == FORWARD) {
                direction = LEFT;
            } else {
                direction -= 1;
            }
        }
    }

    // REQUIRES: the space must be vacant
    // MODIFIES: this, s
    // EFFECTS: Assign the num of the space to placeNum and let isPark be true
    //          Assign the carLicense to s and let s.isVacancy be false
    public void assignToSpace(Space s) {
        placeNum = s.getNum();
        isPark = true;
        s.setCarLicense(carLicense);
        s.setIsVacancy(false);
    }

    // REQUIRES: direction must be one of FORWARD, BACKWARD, LEFT and RIGHT
    // MODIFIES: this
    // EFFECTS: move one speed in this.direction
    public void move() {
        if (direction == FORWARD) {
            positionY -= SPEED;
        } else if (direction == RIGHT) {
            positionX += SPEED;
        } else if (direction == BACKWARD) {
            positionY += SPEED;
        } else {
            positionX -= SPEED;
        }
    }

    // REQUIRES: This vehicle must be assigned to a space
    // EFFECTS: Return the information of the vehicle in the format
    //          License Number: carLicense
    //          Space Number: placeNum
    //          Charge: parkingFee
    public String toString() {
        return "License Number: " + carLicense + "/n"
                + "Space Number: " + placeNum + "/n"
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

    public int getDirection() {
        return direction;
    }

    public Boolean getIsPark() {
        return isPark;
    }

    public Boolean getIsReadyMoveOut() {
        return isReadyMoveOut;
    }

    public int getPlaceNum() {
        return placeNum;
    }

    // EFFECTS: Return a random generated String with three capital letters
    private String generateCarLicense() {
        String cl = "";
        for (int i = 0; i < 3; i++) {
            int licenseNum = (int) Math.floor((Math.random() * 26 + 65));
            cl += (char) licenseNum;
        }
        return cl;
    }

    public int getForward() {
        return FORWARD;
    }

    public int getRight() {
        return RIGHT;
    }

    public int getBackward() {
        return BACKWARD;
    }

    public int getLeft() {
        return LEFT;
    }

    public int getSpeed() {
        return SPEED;
    }

    public int getSizeX() {
        return SIZE_X;
    }

    public int getSizeY() {
        return SIZE_Y;
    }
}

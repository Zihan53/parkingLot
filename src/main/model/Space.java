package model;

// Represents a space having a number, boundaries and status.
public class Space {

    private Boolean isVacancy;
    private String carLicense;
    private int num;
    private int upperBoundary;
    private int lowerBoundary;
    private int leftBoundary;
    private int rightBoundary;

    // Constructor
    // EFFECTS: Create a place with a num and a isVacancy assigned to be false
    //          and assign four boundaries to each corresponding variables
    public Space(int num, int ub, int lob, int leb, int rb) {
        isVacancy = true;
        this.num = num;
        upperBoundary = ub;
        lowerBoundary = lob;
        leftBoundary = leb;
        rightBoundary = rb;
    }

    //EFFECTS: Set isVacancy to be iv.
    public void setIsVacancy(Boolean iv) {
        isVacancy = iv;
    }

    // EFFECTS: Check if the upper boundary of vehicle >= upperBoundary,
    //                   the lower boundary of vehicle <= lowerBoundary,
    //                   the left boundary of vehicle >= leftBoundary,
    //                   the right boundary of vehicle <= rightBoundary
    public Boolean checkIfVehicleInBoundary(Vehicle v) {
        int vub = v.getPositionY() - v.getSizeY() / 2;
        int vlob = v.getPositionY() + v.getSizeY() / 2;
        int vleb = v.getPositionX() - v.getSizeX() / 2;
        int vrb = v.getPositionX() + v.getSizeX() / 2;
        return vub >= upperBoundary && vlob <= lowerBoundary && vleb >= leftBoundary && vrb <= rightBoundary;
    }

    public Boolean getIsVacancy() {
        return isVacancy;
    }

    public String getCarLicense() {
        return carLicense;
    }

    public void setCarLicense(String cl) {
        carLicense = cl;
    }

    public int getNum() {
        return num;
    }

    public int getUpperBoundary() {
        return upperBoundary;
    }

    public int getLowerBoundary() {
        return lowerBoundary;
    }

    public int getLeftBoundary() {
        return leftBoundary;
    }

    public int getRightBoundary() {
        return rightBoundary;
    }
}

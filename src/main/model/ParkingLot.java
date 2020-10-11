package model;

import java.util.ArrayList;

public class ParkingLot {

    private static final int PUNISHMENT_AMOUNT = 10;

    private ArrayList<Space> spaces;
    private ArrayList<Vehicle> vehicles;
    private int balance;

    // Constructor
    // EFFECTS: Create a new ParkingLot with two empty ArrayList for space and vehicle respectively
    public ParkingLot() {
        spaces = new ArrayList<>();
        vehicles = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: Add s to spaces
    public void addSpace(Space s) {
        spaces.add(s);
    }

    // MODIFIES: this
    // EFFECTS: ADD v to vehicles.
    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    // EFFECTS: Make vehicle with carLicense cl assigned to space with spaceNum sn.
    public void parkInVehicle(Vehicle v, Space s) {
        v.assignToSpace(s);
    }

    // REQUIRES: Spaces can not be empty.
    // EFFECTS: Return true if no vacant spaces left, false otherwise.
    public Boolean checkNoVacantSpace() {
        for (Space s : spaces) {
            if (s.getIsVacancy()) {
                return false;
            }
        }
        return true;
    }

    // REQUIRES: Vehicles can not be empty.
    // EFFECTS: Return true if all the spaces are vacant, false otherwise.
    public Boolean checkAllVacant() {
        for (Space s : spaces) {
            if (!s.getIsVacancy()) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: Return the vehicle which carLicense is equal to cl, if cl can not be found, return null.
    public Vehicle searchVehicle(String cl) {
        for (Vehicle v : vehicles) {
            if (v.getCarLicense().equals(cl)) {
                return v;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: Add v's parking fee to the balance
    public void addChargeToBalance(Vehicle v) {
        balance += v.getParkingFee();
    }

    // MODIFIES: this
    // EFFECTS: Subtract the punishment amount from the balance
    public void subtractFromBalance() {
        balance -= PUNISHMENT_AMOUNT;
    }

    public int getBalance() {
        return balance;
    }

    // EFFECTS: Return the ith element in spaces.
    public Space getSpace(int i) {
        return spaces.get(i);
    }

    // EFFECTS: Return the ith element in vehicles.
    public Vehicle getVehicle(int i) {
        return vehicles.get(i);
    }

    // EFFECTS: Return the number of elements in spaces
    public int getSizeSpaces() {
        return spaces.size();
    }

    // EFFECTS: Return the number of elements in vehicles
    public int getSizeVehicles() {
        return vehicles.size();
    }

    public int getPunishmentAmount() {
        return PUNISHMENT_AMOUNT;
    }
}



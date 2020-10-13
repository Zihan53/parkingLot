package model;

import java.util.ArrayList;

// Represents a parking lot having a list of spaces, list of vehicles and balance (in dollars)
public class ParkingLot {

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
    // EFFECTS: Add v to vehicles.
    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    // REQUIRE: There must be a vehicle with carLicense cl in vehicles.
    // MODIFIES: this
    // EFFECTS: Remove the vehicle with carLicense cl from vehicles.
    public void removeVehicle(String cl) {
        Vehicle v = searchVehicle(cl);
        vehicles.remove(v);
        v.removeFromSpace(searchSpace(v.getPlaceNum()));
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

    // EFFECTS: Return the number of vacant spaces in string.
    public String vacantSpacesToString() {
        String str = "";
        for (Space s: spaces) {
            if (s.getIsVacancy()) {
                str += s.getNum();
                str += "\t";
            }
        }
        return str;
    }

    // EFFECTS: Return the car license of all vehicles in string.
    public String licenseToString() {
        String str = "";
        for (Vehicle v: vehicles) {
            str += v.getCarLicense();
            str += "\t";
        }
        return str;
    }

    // MODIFIES: this
    // EFFECTS: Add v's parking fee to the balance
    public void addChargeToBalance(Vehicle v) {
        balance += v.getParkingFee();
    }

    //
    // EFFECTS: Return the vehicle which carLicense is equal to cl, if cl can not be found, return null.
    public Vehicle searchVehicle(String cl) {
        for (Vehicle v : vehicles) {
            if (v.getCarLicense().equals(cl)) {
                return v;
            }
        }
        return null;
    }


    // REQUIRES: There must be a space with num n.
    // EFFECTS: Return the space which num is equal to n.
    public Space searchSpace(int n) {
        for (Space s : spaces) {
            if (s.getNum() == n) {
                return s;
            }
        }
        return null;
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
}



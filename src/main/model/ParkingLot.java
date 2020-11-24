package model;

import exception.NoSpaceException;
import exception.NoVehicleException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Date;

// Represents a parking lot with a list of spaces, list of vehicles and balance (in dollars)
public class ParkingLot implements Writable {

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
    // EFFECTS: Add v to vehicles and assign the vehicle the space with number sn, if there is no space with number sn
    //          , throw a NoSpaceException
    public void addVehicle(Vehicle v, int sn) throws NoSpaceException {
        Space s = searchSpace(sn);
        vehicles.add(v);
        v.assignToSpace(s);
    }

    // MODIFIES: this
    // EFFECTS: Unassign the vehicle with licensePlateNumber cl and remove it from vehicles
    //          And add the corresponding parking fee to balance, if there is no vehicle with license plate number
    //          cl, throw a NoVehicleException
    public void unassignVehicle(String cl, Date now) throws NoVehicleException {
        Vehicle v = searchVehicle(cl);
        addChargeToBalance(v, now);
        v.removeFromSpace();
        vehicles.remove(v);
    }

    // EFFECTS: Return true if no vacant spaces left, false otherwise.
    public Boolean checkNoVacantSpace() {
        for (Space s : spaces) {
            if (s.getIsVacancy()) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: Return true if all the spaces are vacant, false otherwise.
    public Boolean checkAllVacant() {
        for (Space s : spaces) {
            if (!s.getIsVacancy()) {
                return false;
            }
        }
        return true;
    }

    // EFFECTS: Return the number of vacant spaces
    public int getVacantSpacesNum() {
        int num = 0;
        for (Space s: spaces) {
            if (s.getIsVacancy()) {
                num += 1;
            }
        }
        return num;
    }

    // EFFECTS: Return the space num of vacant spaces in string.
    public String vacantSpacesToString() {
        StringBuilder str = new StringBuilder();
        for (Space s: spaces) {
            if (s.getIsVacancy()) {
                str.append(s.getNum());
                str.append("\t");
            }
        }
        return str.toString();
    }

    // EFFECTS: Return the car license of all vehicles in string.
    public String licenseToString() {
        StringBuilder str = new StringBuilder();
        for (Vehicle v: vehicles) {
            str.append(v.getLicensePlateNum());
            str.append(" ");
        }
        return str.toString();
    }

    // MODIFIES: this
    // EFFECTS: Add v's parking fee to the balance
    private void addChargeToBalance(Vehicle v, Date now) {
        balance += v.calculateParkingFee(now);
    }

    // EFFECTS: Return the vehicle which carLicense is equal to cl, if cl can not be found, throw a NoVehicleException
    public Vehicle searchVehicle(String cl) throws NoVehicleException {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlateNum().equals(cl)) {
                return v;
            }
        }
        throw new NoVehicleException();
    }

    // EFFECTS: Return the space which num is equal to n, if n can not be found, throw a NoSpaceException
    public Space searchSpace(int n) throws NoSpaceException {
        for (Space s : spaces) {
            if (s.getNum() == n) {
                return s;
            }
        }
        throw new NoSpaceException();
    }

    public int getBalance() {
        return balance;
    }

    public ArrayList<Space> getSpaces() {
        return spaces;
    }

    public ArrayList<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setBalance(int b) {
        balance = b;
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

    // Follow the format in JsonSerializationDemo
    // EFFECTS: Return parkingLot as a Json object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("spaces", spacesToJson());
        json.put("vehicles", vehiclesToJson());
        json.put("balance", balance);
        return json;
    }

    // Follow the format in JsonSerializationDemo
    // EFFECTS: Return vehicles in this parkingLor as a JSON array
    private JSONArray vehiclesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Vehicle v : vehicles) {
            jsonArray.put(v.toJson());
        }

        return jsonArray;
    }

    // Follow the format in JsonSerializationDemo
    // EFFECTS: Return spaces in this parkingLor as a JSON array
    private JSONArray spacesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Space s : spaces) {
            jsonArray.put(s.toJson());
        }

        return jsonArray;
    }
}



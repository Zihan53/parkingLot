package ui;

import exception.NoSpaceException;
import exception.NoVehicleException;
import model.ParkingLot;
import model.Space;
import model.Vehicle;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

// Follow the format in TellerApp
// Parking Lot Simulation Application
public class MyParkingLotManagementSystem {

    private static final String JSON_STORE = "./data/parkingLot.json";
    private static final int SPACES_NUM = 5;
    private static final String DEFAULT_COMMAND = "arcvslq";

    private ParkingLot myParkingLot;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private boolean keepGoing;

    // EFFECTS: runs the management system
    public MyParkingLotManagementSystem() {
        runSystem();
    }

    // MODIFIES: this
    // EFFECTS: process user input
    private void runSystem() {
        String command;
        init();
        while (keepGoing) {
            displayMenu();
            while (true) {
                command = input.next();
                command.toLowerCase();
                if (DEFAULT_COMMAND.contains(command) && command.length() == 1) {
                    break;
                } else {
                    System.out.println("Invalid input. Please select once again.");
                }
            }
            processCommand(command);
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes parking lot and generate SPACES_NUM spaces.
    private void init() {
        myParkingLot = new ParkingLot();
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        keepGoing = true;

        System.out.println("Hi, Welcome to 'My Parking Lot'. The most efficient tool for parking lot management. "
                + "Follow the instructions to explore now.");

        for (int i = 1; i <= SPACES_NUM; i++) {
            Space space = new Space(i);
            myParkingLot.addSpace(space);
        }
    }


    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> Add a new vehicle");
        System.out.println("\tr -> Remove a vehicle");
        System.out.println("\tc -> Check the information of a vehicle in your parking lot");
        System.out.println("\tv -> View your current balance");
        System.out.println("\ts -> Save information in the parking lot to file");
        System.out.println("\tl -> Load previous parking lot information to file");
        System.out.println("\tq -> quit");
    }

    // REQUIRES: command must be one of a, g, c, v, q;
    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            if (myParkingLot.checkNoVacantSpace()) {
                System.out.println("Sorry! No vacant space.");
            } else {
                addNewVehicle();
            }
        } else if (command.equals("r")) {
            removeExistingVehicle();
        } else if (command.equals("c")) {
            checkInformationOfVehicle();
        } else if (command.equals("v")) {
            viewBalance();
        } else if (command.equals("s")) {
            saveParkingLot();
        } else if (command.equals("l")) {
            loadParkingLot();
        } else {
            keepGoing = false;
            System.out.println("Goodbye!");
        }
    }


    // MODIFIES: this
    // EFFECTS: Add a new vehicle and assign vehicle to a vacant space.
    private void addNewVehicle() {
        String command;
        System.out.println("Please enter the license-plate number.");
        command = input.next();
        try {
            myParkingLot.searchVehicle(command);
            System.out.println("The vehicle has already been in the parking lot.");
        } catch (NoVehicleException v) {
            Vehicle vehicle = new Vehicle(command);
            System.out.println("The followings are numbers of vacant places. Please enter the number you want"
                    + " to park in\n" + myParkingLot.vacantSpacesToString());

            while (true) {
                command = input.next();
                try {
                    myParkingLot.addVehicle(vehicle, Integer.parseInt(command));
                    System.out.println("The vehicle " + vehicle.getLicensePlateNum() + " has been successfully "
                            + "moved into space " + command + ".");
                    break;
                } catch (NoSpaceException s) {
                    System.out.println("Invalid input. Please select once again");
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: Remove a vehicle from the parking lot.
    private void removeExistingVehicle() {
        String command;
        if (myParkingLot.getSizeVehicles() == 0) {
            System.out.println("There is no vehicle in your parking lot");
        } else {
            String cls = myParkingLot.licenseToString();
            System.out.println("There are " + myParkingLot.getSizeVehicles() + " vehicles in your parking lot. "
                    + "They are " + cls + ". Enter a license-plate number to remove");
            while (true) {
                command = input.next();
                Date now = new Date();
                try {
                    myParkingLot.unassignVehicle(command, now);
                    System.out.println("Vehicle has been removed. You current balance is $"
                            + myParkingLot.getBalance());
                    break;
                } catch (NoVehicleException e) {
                    System.out.println("Invalid input. Please select once again");
                }
            }
        }
    }


    // EFFECTS: View an existing vehicle's information.
    private void checkInformationOfVehicle() {
        String command;
        if (myParkingLot.getSizeVehicles() == 0) {
            System.out.println("There is no vehicle in your parking lot");
        } else {
            String cls = myParkingLot.licenseToString();
            System.out.println("There are " + myParkingLot.getSizeVehicles() + " vehicles in your parking lot. "
                    + "They are " + cls + ". Enter a license-plate number to view the information");
            while (true) {
                command = input.next();
                try {
                    Date now = new Date();
                    System.out.println(myParkingLot.searchVehicle(command).vehicleToString(now));
                    break;
                } catch (NoVehicleException e) {
                    System.out.println("Invalid input. Please select once again");
                }
            }
        }
    }

    // EFFECTS: View current balance.
    private void viewBalance() {
        System.out.println("Your current balance is $" + myParkingLot.getBalance());
    }

    // Code based on JsonSerializationDemo
    // EFFECTS: Save the parking lot to file
    private void saveParkingLot() {
        try {
            jsonWriter.open();
            jsonWriter.write(myParkingLot);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // Code based on JsonSerializationDemo
    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadParkingLot() {
        try {
            myParkingLot = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        } catch (NoSpaceException e) {
            System.out.println("One or more space number for vehicles are wrong");
        } catch (ParseException e) {
            // pass
        }
    }
}




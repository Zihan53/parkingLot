package ui;

import model.ParkingLot;
import model.Space;
import model.Vehicle;

import java.util.Scanner;

// Parking Lot Simulation Application
public class MyParkingLotManagementSystem {

    private static final int SPACES_NUM = 10;
    private static final String DEFAULT_COMMAND = "arcvq";

    private ParkingLot myParkingLot;
    private Scanner input;
    private boolean keepGoing;


    public MyParkingLotManagementSystem() {
        runSystem();
    }

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


    private void init() {
        myParkingLot = new ParkingLot();
        input = new Scanner(System.in);
        keepGoing = true;

        System.out.println("Hi, Welcome to 'My Parking Lot'. The most efficient tool for parking lot management. "
                + "Follow the instructions to explore now.");

        for (int i = 1; i <= SPACES_NUM; i++) {
            Space space = new Space(i, 0, 0, 0, 0);
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
        System.out.println("\tq -> quit");
    }

    // REQUIRES: command must be one of g, c, v, q;
    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addNewVehicle();
        } else if (command.equals("r")) {
            removeCurrentVehicle();
        } else if (command.equals("c")) {
            checkInformationOfVehicle();
        } else if (command.equals("v")) {
            viewBalance();
        } else {
            keepGoing = false;
            System.out.println("Goodbye!");
        }
    }


    private void addNewVehicle() {
        String command;
        if (myParkingLot.checkNoVacantSpace()) {
            System.out.println("Sorry! No vacant space.");
        } else {
            System.out.println("Please enter the license-plate number.");
            Vehicle vehicle = new Vehicle(input.next(), 0, 0);
            myParkingLot.addVehicle(vehicle);
            System.out.println("The license-plate number is " + vehicle.getCarLicense() + " and its"
                    + " parking fee is $" + vehicle.getParkingFee() + ". The followings are numbers of vacant places. "
                    + "Please help the vehicle moving into a space by enter a number.");
            System.out.println("\n" + myParkingLot.vacantSpacesToString());
            while (true) {
                command = input.next();
                if (myParkingLot.vacantSpacesToString().contains(command)) {
                    vehicle.assignToSpace(myParkingLot.searchSpace(Integer.parseInt(command)));
                    System.out.println("The vehicle " + vehicle.getCarLicense() + " has been successfully moved into "
                            + "space " + command + ".");
                    break;
                } else {
                    System.out.println("Invalid input. Please select once again");
                }
            }
        }
    }

    private void removeCurrentVehicle() {
        String command;
        if (myParkingLot.getSizeVehicles() == 0) {
            System.out.println("There is no vehicle in your parking lot");
        } else {
            String cls = myParkingLot.licenseToString();
            System.out.println("There are " + myParkingLot.getSizeVehicles() + " vehicles in your parking lot. "
                    + "They are " + cls + ". Enter a license-plate number to remove");
            while (true) {
                command = input.next();
                if (cls.contains(command)) {
                    myParkingLot.addChargeToBalance(myParkingLot.searchVehicle(command));
                    myParkingLot.removeVehicle(command);
                    System.out.println("Vehicle has been removed. You current balance is $"
                            + myParkingLot.getBalance());
                    break;
                } else {
                    System.out.println("Invalid input. Please select once again");
                }
            }
        }
    }


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
                if (cls.contains(command)) {
                    System.out.println(myParkingLot.searchVehicle(command).vehicleToString());
                    break;
                } else {
                    System.out.println("Invalid input. Please select once again");
                }
            }
        }
    }

    private void viewBalance() {
        System.out.println("Your current balance is $" + myParkingLot.getBalance());
    }
}




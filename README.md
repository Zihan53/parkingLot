# Parking Lot Management System

## Introduction
Welcome to *My Parking Lot*, the most efficient parking lot management tool in the world. This application is designed 
for all parking lots. With this tool, the safety of parking lots will be improved. This tool can be used to update and 
view the all the vehicles' information in the parking lot in real time. And it can also be used to calculate and view 
the overall parking fees.

There is a multi-storey car park in my neighbourhood. I find there are neither guards, nor some device to record vehicle
entry or exit information. This may lead to some safety problem and cause inconvenience to residents(For example, no
reminder when the park is full). So, I design this application.
 
## User Stories
- As a user, I want to be able to add new vehicles to the parking lot and assign vehicles to vacant spaces.
- As a user, I want to be able to remove vehicles from the parking lot.
- As a user, I want to be able to view an existing vehicle's information.
- As a user, I want to be able to get a notification once there is some change in my balance and view my balance
whenever I want.
- As a user, I want to be able to view the charging standards.
- As a user, I want to be able to save the information in parkingLot in a file.
- As a user, I want to be able to load previous parkingLot's information.

## Phase 4: Task 2
I choose to Test and design a class in the model package that is robust. The class is ParkingLot and there are four
methods: addVehicle, unassignVehicle, searchVehicle and searchSpace. The addVehicle method calls the searchSpace method,
and the unassignVehicle method calls the searchVehicle method.

## Phase 4: Task 3
- There are too many couplings among Space, Vehicle and ParkingLot class. I will keep the coupling between ParkingLot 
and Space, Space and Vehicle, which means removing the coupling between Vehicle and ParkingLot because ParkingLot can 
get information of vehicle through Space.
- The four classes(CheckInPanel, CheckOutPanel, FeeStandardPanel and InformationPanel) representing panels in GUI are 
designed individually but there are a lot of duplicate codes so I will add an abstract class and these four classes
extend this abstract class.

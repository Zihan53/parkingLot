package ui;

import exception.NoSpaceException;
import exception.NoVehicleException;
import model.ParkingLot;
import model.Space;
import model.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static ui.MainGui.CARD;

// Represent the check in panel
public class CheckInPanel extends JPanel {
    private ParkingLot myParkingLot;
    private JPanel inputPanel;
    private JTextField licenseInput;
    private JComboBox spaceChoice;
    private JPanel confirmButtonPanel;
    private JButton confirmButton;
    private JTextArea notification;
    private JButton mainMenuButton;
    private JPanel mainMenuButtonPanel;
    private JPanel customerMode;
    private JTextArea note;

    public CheckInPanel(ParkingLot p, JPanel customerMode, JTextArea note) {
        super();
        setLayout(new GridLayout(4, 1));
        myParkingLot = p;
        this.customerMode = customerMode;
        this.note = note;

        createInputPanel();
        createNotificationPart();
        setConfirmButtonEvent();
        createMainMenuButton();
    }

    // MODIFIES: this
    // EFFECTS: Create the user input part with
    //          a writeable textField, a selectable comboBox and a confirm button.
    private void createInputPanel() {
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        this.add(inputPanel);

        inputPanel.add(new JLabel("License Plate:  "));
        licenseInput = new JTextField(10);
        inputPanel.add(licenseInput);

        inputPanel.add(new JLabel("     Space:  "));
        spaceChoice = new JComboBox();
        inputPanel.add(spaceChoice);

        confirmButtonPanel = new JPanel();
        confirmButton = new JButton("Confirm");
        confirmButtonPanel.add(confirmButton);
        this.add(confirmButtonPanel);
    }

    // MODIFIES: this
    // EFFECTS: Create a textField to show notification
    private void createNotificationPart() {
        notification = new JTextArea();
        notification.setEditable(false);
        notification.setOpaque(false);
        notification.setBorder(null);
        this.add(notification);
    }

    // MODIFIES: this
    // EFFECTS: Set the button event for confirm button.
    private void setConfirmButtonEvent() {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (spaceChoice.getSelectedItem() == " " || licenseInput.getText().equals("")) {
                    notification.setText("Please choose a valid space number and input a valid license plate number.");
                    Toolkit.getDefaultToolkit().beep();
                } else {
                    try {
                        myParkingLot.searchVehicle(licenseInput.getText());
                        notification.setText("The vehicle has already been in the parking lot.");
                        Toolkit.getDefaultToolkit().beep();
                    } catch (NoVehicleException exception) {
                        addVehicle();
                    }
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Add vehicle to the parkingLot
    private void addVehicle() {
        Vehicle vehicle = new Vehicle(licenseInput.getText());
        int currentPosition = spaceChoice.getSelectedIndex();
        try {
            myParkingLot.addVehicle(vehicle, (Integer) spaceChoice.getSelectedItem());
        } catch (NoSpaceException noSpaceException) {
            // pass
        }
        notification.setText("Vehicle " + licenseInput.getText() + " has been successfully moved into"
                + " space" + spaceChoice.getSelectedItem() + ".\nYou can add another one or get back "
                + "to the main menu.");
        spaceChoice.removeItemAt(currentPosition);
        licenseInput.setText("");
    }

    // MODIFIES: this
    // EFFECTS: Create a button to get back to the main menu and set the event of button.
    private void createMainMenuButton() {
        mainMenuButtonPanel = new JPanel();
        mainMenuButton = new JButton("Back To The Main Menu");
        mainMenuButtonPanel.add(mainMenuButton);
        this.add(mainMenuButtonPanel);
        mainMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CARD.show(customerMode, "Main Menu");
                note.setText("Total Spaces: " + myParkingLot.getSizeSpaces() + "\nRemaining Spaces: "
                        + myParkingLot.getVacantSpacesNum() + "\nBalance: $" + myParkingLot.getBalance());
                notification.setText("");
                licenseInput.setText("");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Set the comboBox for space choice
    public void setSpaceChoice() {
        spaceChoice.removeAllItems();
        spaceChoice.addItem(" ");
        for (Space space : myParkingLot.getSpaces()) {
            if (space.getIsVacancy()) {
                spaceChoice.addItem(space.getNum());
            }
        }
    }

    public void setMyParkingLot(ParkingLot myParkingLot) {
        this.myParkingLot = myParkingLot;
    }
}

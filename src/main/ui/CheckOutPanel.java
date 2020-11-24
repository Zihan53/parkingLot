package ui;

import exception.NoVehicleException;
import model.ParkingLot;
import model.Space;
import model.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import static ui.MainGui.CARD;

// Represent the check out panel
public class CheckOutPanel extends JPanel {
    private ParkingLot myParkingLot;
    private JPanel inputPanel;
    private JComboBox licenseChoice;
    private JPanel confirmButtonPanel;
    private JButton confirmButton;
    private JTextArea notification;
    private JButton mainMenuButton;
    private JPanel mainMenuButtonPanel;
    private JPanel customerMode;
    private JTextArea note;

    public CheckOutPanel(ParkingLot p, JPanel customerMode, JTextArea note) {
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
    //          a writeable textField and a confirm button.
    private void createInputPanel() {
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        this.add(inputPanel);

        inputPanel.add(new JLabel("License Plate:  "));
        licenseChoice = new JComboBox();
        inputPanel.add(licenseChoice);

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
        notification.setPreferredSize(new Dimension(10, 10));
        this.add(notification);
    }

    // MODIFIES: this
    // EFFECTS: Set the button event for confirm button.
    private void setConfirmButtonEvent() {
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (licenseChoice.getSelectedItem() == " ") {
                    notification.setText("Please select a vehicle");
                    Toolkit.getDefaultToolkit().beep();
                } else {
                    try {
                        myParkingLot.unassignVehicle((String) licenseChoice.getSelectedItem(), new Date());
                        notification.setText("Vehicle has been removed. You current balance is $"
                                + myParkingLot.getBalance());
                    } catch (NoVehicleException exception) {
                        // pass
                    }
                }
            }
        });
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
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Set the comboBox for license choice
    public void setLicenseChoice() {
        licenseChoice.removeAllItems();
        licenseChoice.addItem(" ");
        for (Vehicle vehicle: myParkingLot.getVehicles()) {
            licenseChoice.addItem(vehicle.getLicensePlateNum());
        }
    }

    public void setMyParkingLot(ParkingLot myParkingLot) {
        this.myParkingLot = myParkingLot;
    }
}

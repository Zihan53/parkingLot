package ui;

import model.ParkingLot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static model.Vehicle.*;
import static model.Vehicle.MAXIMUM_PARKING_FEE;
import static ui.MainGui.CARD;

public class FeeStandardPanel extends JPanel {
    private JTextArea feeStandard;
    private JPanel feeStandardPanel;
    private ParkingLot myParkingLot;
    private JPanel customerMode;
    private JButton mainMenuButton;
    private JPanel mainMenuButtonPanel;
    private JTextArea note;

    public FeeStandardPanel(ParkingLot p, JPanel customerMode, JTextArea note) {
        super();
        setLayout(new GridLayout(2, 1));
        myParkingLot = p;
        this.customerMode = customerMode;
        this.note = note;

        showFeeStandard();
        createMainMenuButton();
    }

    // MODIFIES:
    // EFFECTS：Create a textArea to show the fee standard
    public void showFeeStandard() {
        feeStandardPanel =  new JPanel();
        feeStandardPanel.setLayout(new GridBagLayout());
        feeStandard = new JTextArea();
        feeStandard.setEditable(false);
        feeStandard.setOpaque(false);
        feeStandard.setBorder(null);
        feeStandard.setText("The parking fee is $" + PARKING_FEE_PER_HOUR + " per hour.\nAnd more than "
                + MINUTES_TREATED_AS_HOUR + "min will be treated as one hour.\nIf you park for more than "
                + MAXIMUM_PARKING_DAYS + " days, the parking fee is always $" + MAXIMUM_PARKING_FEE + ".");
        feeStandardPanel.add(feeStandard);
        this.add(feeStandardPanel);
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
            }
        });
    }

    public void setMyParkingLot(ParkingLot myParkingLot) {
        this.myParkingLot = myParkingLot;
    }
}

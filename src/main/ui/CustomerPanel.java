package ui;

import model.ParkingLot;

import javax.swing.*;
import java.awt.*;

public class CustomerPanel extends JPanel {
    ParkingLot parkingLot;

    // Constructs a customer panel
    // effects: Sets size and background colour of the interface.
    //          updates this with the parkingLot to be displayed
    public CustomerPanel(ParkingLot p) {
        super();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.GRAY);
        parkingLot = p;
    }





}

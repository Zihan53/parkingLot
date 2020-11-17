package ui;

import exception.NoVehicleException;
import model.ParkingLot;
import model.Vehicle;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import static ui.MainGui.CARD;
import static ui.MainGui.SPACES_NUM;

public class InformationPanel extends JPanel {
    public static String[] HEADER = {"Plate Number", "Space Number", "Parking Duration", "Parking Fee"};

    private ParkingLot myParkingLot;
    private JPanel customerMode;
    private JPanel inputPanel;
    private JComboBox licenseChoice;
    private JPanel viewSelectButtonPanel;
    private JButton viewSelectButton;
    private JPanel viewAllButtonPanel;
    private JButton viewAllButton;
    private JTable table;
    private JButton mainMenuButton;
    private JPanel mainMenuButtonPanel;
    private String[][] data;

    public InformationPanel(ParkingLot p, JPanel customerMode) {
        super();
        setLayout(new GridLayout(3, 1));
        myParkingLot = p;
        this.customerMode = customerMode;

        createInputPanel();
        createTable();
        setViewSelectButtonEvent();
        setViewAllButtonEvent();
        createMainMenuButton();
    }

    // MODIFIES: this
    // EFFECTS: Create the user input part with
    //          a selectable comboBox and two confirm buttons.
    private void createInputPanel() {
        inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        this.add(inputPanel);

        inputPanel.add(new JLabel("License Plate:  "));
        licenseChoice = new JComboBox();
        licenseChoice.addItem(" ");
        inputPanel.add(licenseChoice);

        viewSelectButtonPanel = new JPanel();
        viewSelectButton = new JButton("View Selected");
        viewSelectButtonPanel.add(viewSelectButton);
        inputPanel.add(viewSelectButtonPanel);

        viewAllButtonPanel = new JPanel();
        viewAllButton = new JButton("View All");
        viewAllButtonPanel.add(viewAllButton);
        inputPanel.add(viewAllButtonPanel);
    }

    // MODIFIES: this
    // EFFECTS: Create the table to show information
    private void createTable() {
        table = new JTable(new DefaultTableModel(new String[SPACES_NUM][4], HEADER));
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(table);
        this.add(scrollPane);
    }

    // MODIFIES: this
    // EFFECTS: Set the button event for View Selected.
    private void setViewSelectButtonEvent() {
        viewSelectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (licenseChoice.getSelectedItem() != " ") {
                    try {
                        Vehicle v = myParkingLot.searchVehicle((String) licenseChoice.getSelectedItem());
                        String[][] info = new String[10][4];
                        info[0] = new String[]{v.getLicensePlateNum(), String.valueOf(v.getSpace().getNum()),
                                v.getDuration(new Date()), String.valueOf(v.calculateParkingFee(new Date()))};
                        DefaultTableModel select = new DefaultTableModel(info, HEADER);
                        table.setModel(select);
                    } catch (NoVehicleException exception) {
                        // pass
                    }
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Set the button event for View All.
    private void setViewAllButtonEvent() {
        viewAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = 0;
                for (Vehicle vehicle : myParkingLot.getVehicles()) {
                    data = new String[SPACES_NUM][4];
                    data[i] = new String[]{vehicle.getLicensePlateNum(), String.valueOf(vehicle.getSpace().getNum()),
                            vehicle.getDuration(new Date()), String.valueOf(vehicle.calculateParkingFee(new Date()))};
                    i++;
                }
                DefaultTableModel all = new DefaultTableModel(data, HEADER);
                table.setModel(all);
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
                table.setModel(new DefaultTableModel(new String[SPACES_NUM][4], HEADER));
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Set the comboBox for space choice
    public void setLicenseChoice() {
        licenseChoice.removeAllItems();
        licenseChoice.addItem(" ");
        for (Vehicle vehicle : myParkingLot.getVehicles()) {
            licenseChoice.addItem(vehicle.getLicensePlateNum());
        }
    }
}

package ui;

import exception.NoSpaceException;
import exception.NoVehicleException;
import model.ParkingLot;
import model.Space;
import model.Vehicle;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class MainGui extends JFrame {
    private static final String JSON_STORE = "./data/parkingLot.json";
    public static final int HEIGHT = 433;
    public static final int WIDTH = 700;
    public static final int xPosition = 500;
    public static final int yPosition = 230;
    private static final int SPACES_NUM = 10;
    private static final Color BACKGROUND_COLOR = new Color(255, 245, 238);
    private static final Font FONT = new Font("Verdana", Font.PLAIN, 16);

    private ParkingLot myParkingLot;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JPanel customerMode;
    private JPanel mainMenu;
    private JPanel checkInPanel;
    private JPanel checkOutPanel;
    private JPanel viewInformationPanel;
    private JPanel viewChargingStandardsPanel;

    private JButton checkInButton;
    private JButton checkOutButton;
    private JButton viewInformationButton;
    private JButton viewChargingStandardsButton;
    private CardLayout card;

    // Constructs main window
    // effects: sets up window in which the system will run.
    public MainGui() {
        super("Parking Lot Automatic Service System");

        setNimBus();
        setUIFont();

        init();
        createCustomerMode();

        this.getContentPane().add(customerMode);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLocation(xPosition, yPosition);
        this.pack();
        this.setVisible(true);
    }

    // EFFECTS: Create the main panel and show it.
    private void createCustomerMode() {
        customerMode = new JPanel();
        card = new CardLayout();
        customerMode.setLayout(card);
        this.getContentPane().add(customerMode);

        initMainMenu();
        initCheckIn();
        initCheckOut();
        initViewInformation();
        initViewChargingStandards();

        customerMode.add(mainMenu, "Main Menu");
        customerMode.add(checkInPanel, "Check In");
        customerMode.add(checkOutPanel, "Check Out");
        customerMode.add(viewInformationPanel, "View My Vehicle");
        customerMode.add(viewChargingStandardsPanel, "Fee Standard");
    }

    // MODIFIES: this
    // EFFECTS: Initialize the check in panel
    private void initCheckIn() {
        checkInPanel = new JPanel();
        checkInPanel.setLayout(new GridLayout(4, 1));

        createCheckInUp();

        checkInPanel.add(createMainMenuButtonPanel());
        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(customerMode, "Check In");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Create the upper part of the check in panel:
    //          A writeable textField, a selectable comboBox and a confirm button
    private void createCheckInUp() {
        JPanel up = new JPanel();
        up.setLayout(new GridBagLayout());
        JLabel licenseNumLabel = new JLabel("License Plate:  ");
        up.add(licenseNumLabel);
        JTextField licenseInput = new JTextField(10);
        up.add(licenseInput);

        JLabel spaceNumLabel = new JLabel("     Space:  ");
        JComboBox spaceChoice = new JComboBox();
        spaceChoice.addItem(" ");
        for (int i = 0; i < SPACES_NUM; i++) {
            if (myParkingLot.getSpace(i).getIsVacancy()) {
                spaceChoice.addItem(myParkingLot.getSpace(i).getNum());
            }
        }

        up.add(spaceNumLabel);
        up.add(spaceChoice);
        checkInPanel.add(up);
        createConfirmButtonCheckIn(licenseInput, spaceChoice);
    }

    // MODIFIES: this
    // EFFECTS: Create the confirm button.
    private void createConfirmButtonCheckIn(JTextField licenseInput, JComboBox spaceChoice) {
        JPanel confirmButtonPanel = new JPanel();
        JButton confirmButton = new JButton("Confirm");
        confirmButtonPanel.add(confirmButton);
        checkInPanel.add(confirmButtonPanel);
        setConfirmButtonEventCheckIn(confirmButton,licenseInput,spaceChoice);
    }

    // MODIFIES: this
    // EFFECTS: Set the button event for confirm button.
    private void setConfirmButtonEventCheckIn(JButton confirmButton, JTextField licenseInput, JComboBox spaceChoice) {
        JTextArea notification = createNotificationPart(checkInPanel);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    myParkingLot.searchVehicle(licenseInput.getText());
                } catch (NoVehicleException exception) {
                    notification.setText("The vehicle has already been in the parking lot.");
                }
                if (spaceChoice.getSelectedItem() == " ") {
                    notification.setText("Please choose a valid space number.");
                } else {
                    notification.setText("Vehicle " + licenseInput.getText() + " has been successfully moved into space"
                            + spaceChoice.getSelectedItem() + ".\nYou can add another one or get back to the main "
                            + "menu.");
                    addVehicle(licenseInput, spaceChoice);
                    licenseInput.setText("");
                    spaceChoice.getItemAt(0);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Add vehicle's to myParkingLot
    private void addVehicle(JTextField licenseInput, JComboBox spaceChoice) {
        Vehicle vehicle = new Vehicle(licenseInput.getText());
        try {
            myParkingLot.addVehicle(vehicle, (Integer) spaceChoice.getSelectedItem());
        } catch (NoSpaceException noSpaceException) {
            // pass
        }
    }

    private void initCheckOut() {
        checkOutPanel = new JPanel();
        checkOutPanel.setLayout(new GridLayout(4, 1));;

        createCheckOutUp();

        checkOutPanel.add(createMainMenuButtonPanel());
        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(customerMode, "Check Out");
            }
        });
    }

    private void createCheckOutUp() {
        JPanel up = new JPanel();
        up.setLayout(new GridBagLayout());
        JLabel licenseNumLabel = new JLabel("License Plate:  ");
        up.add(licenseNumLabel);
        JTextField licenseInput = new JTextField(10);
        up.add(licenseInput);

        checkOutPanel.add(up);
        createConfirmButtonCheckOut(licenseInput);
    }

    private void createConfirmButtonCheckOut(JTextField licenseInput) {
        JPanel confirmButtonPanel = new JPanel();
        JButton confirmButton = new JButton("Confirm");
        confirmButtonPanel.add(confirmButton);
        checkOutPanel.add(confirmButtonPanel);
        JTextArea notification = createNotificationPart(checkOutPanel);
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    myParkingLot.unassignVehicle(licenseInput.getText(), new Date());
                } catch (NoVehicleException exception) {
                    notification.setText("No vehicle has been found.");
                }
                notification.setText("Vehicle has been removed. You current balance is $"
                        + myParkingLot.getBalance());
                licenseInput.setText("");
            }
        });
    }

    private void setButtonEventCheckOut() {

    }

    // MODIFIES: this
    // EFFECTS: Create a textField to show notification
    private JTextArea createNotificationPart(JPanel panel) {
        JTextArea notification = new JTextArea();
        notification.setEditable(false);
        notification.setOpaque(false);
        notification.setBorder(null);
        notification.setPreferredSize(new Dimension(10, 10));
        panel.add(notification);
        return notification;
    }

    private void initViewInformation() {
        viewInformationPanel = new JPanel();
        viewInformationPanel.setBackground(BACKGROUND_COLOR);
        viewInformationPanel.add(new JLabel("View My Vehicle"));
        viewInformationPanel.add(createMainMenuButtonPanel());
        viewInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(customerMode, "View My Vehicle");
            }
        });
    }

    private void initViewChargingStandards() {
        viewChargingStandardsPanel = new JPanel();
        viewChargingStandardsPanel.setBackground(BACKGROUND_COLOR);
        viewChargingStandardsPanel.add(new JLabel("Fee Standard"));
        viewChargingStandardsPanel.add(createMainMenuButtonPanel());
        viewChargingStandardsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(customerMode, "Fee Standard");
            }
        });
    }

    private void initMainMenu() {
        mainMenu = new JPanel();
        mainMenu.setBackground(BACKGROUND_COLOR);
        checkInButton = new JButton("Check In");
        mainMenu.add(checkInButton);
        checkOutButton = new JButton("Check Out");
        mainMenu.add(checkOutButton);
        viewInformationButton = new JButton("View My Vehicle");
        mainMenu.add(viewInformationButton);
        viewChargingStandardsButton = new JButton("Fee Standard");
        mainMenu.add(viewChargingStandardsButton);
    }

    private JPanel createMainMenuButtonPanel() {
        JPanel mainButtonPanel = new JPanel();
        mainButtonPanel.setLayout(new GridBagLayout());
        JButton button = new JButton("Main Menu");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(customerMode, "Main Menu");
            }
        });
        mainButtonPanel.add(button);
        return mainButtonPanel;
    }

    private void init() {
        myParkingLot = new ParkingLot();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        for (int i = 1; i <= SPACES_NUM; i++) {
            Space space = new Space(i);
            myParkingLot.addSpace(space);
        }
    }

    private void setNimBus() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUIFont() {
        Font f = new Font("Didot", Font.PLAIN, 18);

        String[] names = {"Label", "CheckBox", "PopupMenu", "MenuItem", "CheckBoxMenuItem",
                "JRadioButtonMenuItem", "ComboBox", "Button", "Tree", "ScrollPane",
                "TabbedPane", "EditorPane", "TitledBorder", "Menu", "TextArea",
                "OptionPane", "MenuBar", "ToolBar", "ToggleButton", "ToolTip",
                "ProgressBar", "TableHeader", "Panel", "List", "ColorChooser",
                "PasswordField", "TextField", "Table", "Label", "Viewport",
                "RadioButtonMenuItem", "RadioButton", "DesktopPane", "InternalFrame"
        };
        for (String item : names) {
            UIManager.put(item + ".font", f);
        }
    }
}


//   JLabel welcomeLabel = new JLabel("Dear customer, ");
//        customerMode.add(welcomeLabel);
//
//        JLabel customerLabel =  new JLabel("Welcome to use the automatic service system for parking, please choose"
//                + " the service you need.");
//        customerMode.add(customerLabel);
//
//        JLabel totalLabel = new JLabel("Total Spaces: " + myParkingLot.getSizeSpaces());
//        customerMode.add(totalLabel);
//
//        JLabel remainingLabel = new JLabel("Remaining Spaces: " + myParkingLot.getVacantSpacesNum());
//        customerMode.add(remainingLabel);




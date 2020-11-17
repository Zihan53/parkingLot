package ui;

import model.ParkingLot;
import model.Space;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGui extends JFrame {
    private static final String JSON_STORE = "./data/parkingLot.json";
    public static final int HEIGHT = 433;
    public static final int WIDTH = 700;
    public static final int xPosition = 500;
    public static final int yPosition = 230;
    public static final int SPACES_NUM = 10;
    public static final CardLayout CARD = new CardLayout();
    private static final Color BACKGROUND_COLOR = new Color(255, 245, 238);
    private static final Font FONT = new Font("Verdana", Font.PLAIN, 16);

    private ParkingLot myParkingLot;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JPanel customerMode;
    private JPanel mainMenu;
    private CheckInPanel checkInPanel;
    private CheckOutPanel checkOutPanel;
    private InformationPanel viewInformationPanel;
    private FeeStandardPanel viewFeeStandardPanel;

    private JButton checkInButton;
    private JButton checkOutButton;
    private JButton viewInformationButton;
    private JButton viewFeeStandardButton;

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
        customerMode.setLayout(CARD);
        this.getContentPane().add(customerMode);

        initMainMenu();
        customerMode.add(mainMenu, "Main Menu");

        checkInPanel = new CheckInPanel(myParkingLot, customerMode);
        customerMode.add(checkInPanel, "Check In");

        checkOutPanel = new CheckOutPanel(myParkingLot,customerMode);
        customerMode.add(checkOutPanel, "Check Out");

        viewFeeStandardPanel = new FeeStandardPanel(myParkingLot,customerMode);
        customerMode.add(viewFeeStandardPanel, "Fee Standard");

        viewInformationPanel = new InformationPanel(myParkingLot, customerMode);
        customerMode.add(viewInformationPanel, "View Vehicles");
    }


    private void initMainMenu() {
        mainMenu = new JPanel();
        mainMenu.setBackground(BACKGROUND_COLOR);
        createCheckInButton();
        createCheckOutButton();
        createViewInformationButton();
        createViewFeeStandardButton();
    }

    private void createCheckInButton() {
        checkInButton = new JButton("Check In");
        mainMenu.add(checkInButton);
        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CARD.show(customerMode, "Check In");
                checkInPanel.setSpaceChoice(myParkingLot.getSpaces());
            }
        });
    }

    private void createCheckOutButton() {
        checkOutButton = new JButton("Check Out");
        mainMenu.add(checkOutButton);
        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CARD.show(customerMode, "Check Out");
                checkOutPanel.setLicenseChoice(myParkingLot.getVehicles());
            }
        });
    }

    private void createViewInformationButton() {
        checkOutButton = new JButton("View Vehicles");
        mainMenu.add(checkOutButton);
        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CARD.show(customerMode, "View Vehicles");
                viewInformationPanel.setLicenseChoice();
            }
        });
    }

    private void createViewFeeStandardButton() {
        checkInButton = new JButton("Fee Standard");
        mainMenu.add(checkInButton);
        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CARD.show(customerMode, "Fee Standard");
            }
        });
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





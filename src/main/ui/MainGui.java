package ui;

import exception.NoSpaceException;
import model.ParkingLot;
import model.Space;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

// Parking Lot application with Gui
public class MainGui extends JFrame {
    private static final String JSON_STORE = "./data/parkingLot.json";
    private static final String CHECK_IN_IMAGE = "./data/car-in-garage.png";
    //<div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from
    // <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
    private static final String CHECK_OUT_IMAGE = "./data/check-out.png";
    //<div>Icons made by <a href="https://www.flaticon.com/authors/icongeek26" title="Icongeek26">Icongeek26</a> from
    //<a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
    private static  final String VIEW_IMAGE = "./data/search.png";
    //<div>Icons made by <a href="http://www.freepik.com/" title="Freepik">Freepik</a> from
    // <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
    private static  final String FEE_IMAGE = "./data/fees.png";
    // <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from
    // <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div>
    public static final int HEIGHT = 433;
    public static final int WIDTH = 700;
    public static final int xPosition = 500;
    public static final int yPosition = 230;
    public static final int SPACES_NUM = 5;
    public static final CardLayout CARD = new CardLayout();
    private static final Font FONT = new Font("Didot", Font.PLAIN, 18);

    private ParkingLot myParkingLot;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JPanel customerMode;
    private JPanel mainMenu;
    private JTextArea note;
    private JPanel buttonPanelOne;
    private JPanel buttonPanelTwo;
    private CheckInPanel checkInPanel;
    private CheckOutPanel checkOutPanel;
    private InformationPanel viewInformationPanel;
    private FeeStandardPanel viewFeeStandardPanel;

    private JButton checkInButton;
    private JButton checkOutButton;
    private JButton viewInformationButton;
    private JButton viewFeeStandardButton;
    private JButton load;
    private JButton save;

    // Constructs main window
    // effects: sets up window in which the system will run.
    public MainGui() {
        super("Parking Lot Automatic Service System");

        setLook();
        setUIFont();

        init();
        createCard();

        this.getContentPane().add(customerMode);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setLocation(xPosition, yPosition);
        this.pack();
        this.setVisible(true);
    }


    // MODIFIES: this
    // EFFECTS: Create a panel to be card layout
    private void createCard() {
        customerMode = new JPanel();
        customerMode.setLayout(CARD);
        this.getContentPane().add(customerMode);

        initMainMenu();
        customerMode.add(mainMenu, "Main Menu");

        checkInPanel = new CheckInPanel(myParkingLot, customerMode, note);
        customerMode.add(checkInPanel, "Check In");

        checkOutPanel = new CheckOutPanel(myParkingLot, customerMode, note);
        customerMode.add(checkOutPanel, "Check Out");

        viewFeeStandardPanel = new FeeStandardPanel(myParkingLot, customerMode, note);
        customerMode.add(viewFeeStandardPanel, "Fee Standard");

        viewInformationPanel = new InformationPanel(myParkingLot, customerMode, note);
        customerMode.add(viewInformationPanel, "View Vehicles");
    }


    // MODIFIES: this
    // EFFECTS: Create all the things in the main menu
    private void initMainMenu() {
        mainMenu = new JPanel();
        mainMenu.setLayout(new GridLayout(3, 1));
        createNotificationPart();

        buttonPanelOne = new JPanel();
        createCheckInButton();
        createCheckOutButton();
        createViewInformationButton();
        createViewFeeStandardButton();
        mainMenu.add(buttonPanelOne);

        buttonPanelTwo = new JPanel();
        mainMenu.add(buttonPanelTwo);
        createLoadButton();
        createSaveButton();
    }

    // MODIFIES: this
    // EFFECTS: Create a textArea to show notification
    private void createNotificationPart() {
        note = new JTextArea();
        note.setEditable(false);
        note.setOpaque(false);
        note.setBorder(null);
        note.setText("Total Spaces: " + myParkingLot.getSizeSpaces() + "\nRemaining Spaces: "
                + myParkingLot.getVacantSpacesNum() + "\nBalance: $" + myParkingLot.getBalance());
        mainMenu.add(note);
    }

    // MODIFIES: this
    // EFFECTS: Create the check in button and set the event
    private void createCheckInButton() {
        checkInButton = new JButton("Check In");
        checkInButton.setIcon(new ImageIcon(CHECK_IN_IMAGE));
        buttonPanelOne.add(checkInButton);
        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CARD.show(customerMode, "Check In");
                checkInPanel.setSpaceChoice();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Create the check out button and set the event
    private void createCheckOutButton() {
        checkOutButton = new JButton("Check Out");
        checkOutButton.setIcon(new ImageIcon(CHECK_OUT_IMAGE));
        buttonPanelOne.add(checkOutButton);
        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CARD.show(customerMode, "Check Out");
                checkOutPanel.setLicenseChoice();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Create the view information button and set the event
    private void createViewInformationButton() {
        viewInformationButton = new JButton("View Vehicles");
        viewInformationButton.setIcon(new ImageIcon(VIEW_IMAGE));
        buttonPanelOne.add(viewInformationButton);
        viewInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CARD.show(customerMode, "View Vehicles");
                viewInformationPanel.setLicenseChoice();
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Create the view fee standard button and set the event
    private void createViewFeeStandardButton() {
        viewFeeStandardButton = new JButton("Fee Standard");
        viewFeeStandardButton.setIcon(new ImageIcon(FEE_IMAGE));
        buttonPanelOne.add(viewFeeStandardButton);
        viewFeeStandardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CARD.show(customerMode, "Fee Standard");
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: create load button and set the event
    private void createLoadButton() {
        load = new JButton("Load");
        buttonPanelTwo.add(load);
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    myParkingLot = jsonReader.read();
                    checkInPanel.setMyParkingLot(myParkingLot);
                    checkOutPanel.setMyParkingLot(myParkingLot);
                    viewInformationPanel.setMyParkingLot(myParkingLot);
                    viewFeeStandardPanel.setMyParkingLot(myParkingLot);
                    note.setText("Total Spaces: " + myParkingLot.getSizeSpaces() + "\nRemaining Spaces: "
                            + myParkingLot.getVacantSpacesNum() + "\nBalance: $" + myParkingLot.getBalance());
                } catch (IOException exception) {
                    System.out.println("Unable to read from file: " + JSON_STORE);
                    Toolkit.getDefaultToolkit().beep();
                } catch (ParseException exception) {
                    System.out.println("Check the Date in the file follows the format yyyy-MM-dd hh:mm");
                } catch (NoSpaceException exception) {
                    System.out.println("Some vehicle has wrong space number in the file");
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: create save button and set the event
    private void createSaveButton() {
        save = new JButton("save");
        buttonPanelTwo.add(save);
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(myParkingLot);
                    jsonWriter.close();
                    System.out.println("Saved to " + JSON_STORE);
                } catch (FileNotFoundException exception) {
                    System.out.println("Unable to write to file: " + JSON_STORE);
                }
            }
        });
    }

    // EFFECTS: init the parking lot
    private void init() {
        myParkingLot = new ParkingLot();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        for (int i = 1; i <= SPACES_NUM; i++) {
            Space space = new Space(i);
            myParkingLot.addSpace(space);
        }
    }

    // EFFECTS: Set NimbusLookAndFeel for the frame
    private void setLook() {
        try {
            UIManager.setLookAndFeel(com.sun.java.swing.plaf.windows.WindowsLookAndFeel.class.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: Set the font of the gui
    private void setUIFont() {
        String[] names = {"Label", "CheckBox", "PopupMenu", "MenuItem", "CheckBoxMenuItem",
                "JRadioButtonMenuItem", "ComboBox", "Button", "Tree", "ScrollPane",
                "TabbedPane", "EditorPane", "TitledBorder", "Menu", "TextArea",
                "OptionPane", "MenuBar", "ToolBar", "ToggleButton", "ToolTip",
                "ProgressBar", "TableHeader", "Panel", "List", "ColorChooser",
                "PasswordField", "TextField", "Table", "Label", "Viewport",
                "RadioButtonMenuItem", "RadioButton", "DesktopPane", "InternalFrame"
        };
        for (String item : names) {
            UIManager.put(item + ".font", FONT);
        }
    }
}





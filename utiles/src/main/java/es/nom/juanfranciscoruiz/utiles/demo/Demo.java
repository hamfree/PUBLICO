package es.nom.juanfranciscoruiz.utiles.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.nom.juanfranciscoruiz.utiles.*;
import es.nom.juanfranciscoruiz.utiles.demo.options.*;
import es.nom.juanfranciscoruiz.utiles.exceptions.MenuErrors;
import es.nom.juanfranciscoruiz.utiles.exceptions.MenuException;
import es.nom.juanfranciscoruiz.utiles.exceptions.MenuManagerException;
import es.nom.juanfranciscoruiz.utiles.helper.ObjectsGenerator;
import es.nom.juanfranciscoruiz.utiles.impl.TermCtlImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static es.nom.juanfranciscoruiz.utiles.Util.*;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.*;

/**
 * Class that demonstrates the functionalities of the "util" library
 *
 * @author Juan F. Ruiz
 */
public class Demo {

    /**
     * Logger for this class
     */
    public final static Logger logger = LoggerFactory.getLogger(Demo.class);

    private TermCtl tc;
    
    private Map<String, Integer> theMap;
    private Map<String, List<Integer>> theMapOfLists;
    
    /**
     * Default constructor for the Demo class.
     *
     * This constructor initializes the `tc` field, which is an instance of a
     * `TermCtl` implementation. The `tc` object represents a terminal control
     * utility or functionality that is pivotal for the operations of the Demo
     * class. The initialization of `tc` ensures the class is ready to utilize the
     * terminal-related features provided by the `TermCtlImpl` implementation.
     */
    public Demo() {
        tc = new TermCtlImpl();
        theMap = ObjectsGenerator.generateMap();
        theMapOfLists = ObjectsGenerator.generateMapOfLists(
            ObjectsGenerator.generateList(true),
            ObjectsGenerator.generateList(false));
    }
    
    public TermCtl getTc() {
        return tc;
    }
    
    public void setTc(TermCtl tc) {
        this.tc = tc;
    }
    
    public Map<String, Integer> getTheMap() {
        return theMap;
    }
    
    public void setTheMap(Map<String, Integer> theMap) {
        this.theMap = theMap;
    }
    
    public Map<String, List<Integer>> getTheMapOfLists() {
        return theMapOfLists;
    }
    
    public void setTheMapOfLists(Map<String, List<Integer>> theMapOfLists) {
        this.theMapOfLists = theMapOfLists;
    }
    
    /**
     * The entry point of the program.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)  {
        info(logger, "Starting demo application...");
        Demo demo = new Demo();
        try {
            demo.run();
        } catch (Exception e) {
            error(logger, e.getMessage());
            throw new RuntimeException(e);
        }
        info(logger, "Exiting demo application...");
        System.exit(0);
    }

    /**
     * Executes the main logic of the demo application for the Utiles library.
     * <p>
     * This method displays a menu to the user with various options to demonstrate
     * features and functionalities provided by the Utiles library. It handles user
     * interaction by repeatedly showing the menu, awaiting user responses, and
     * invoking corresponding actions based on the selected option.
     * <p>
     * The options presented to the user are:
     * 1. Show the sample objects.
     * 2. Convert a simple map into its string representation.
     * 3. Convert a complex map into its string representation.
     * 4. Perform type conversions.
     * 5. Access miscellaneous utilities.
     * <p>
     * The method relies on the `Menu` and `MenuManager` classes to handle the
     * creation and management of the menu system. It also catches exceptions
     * related to menu management and other unforeseen errors, rethrowing them
     * as runtime exceptions.
     * <p>
     * Note: This method operates in a loop until the user selects the exit option
     * or manually terminates the program.
     *
     * @throws RuntimeException if an error occurs during menu creation, menu management,
     *                          or other processing.
     */
    public void run() throws Exception {
        List<String> theOptions = new ArrayList<>();
        Menu theMenu;
        MenuManager mm;
        Long response = Menu.WRONG_OPTION;
        String msg = "";
        
        theOptions.add("Show the sample objects");
        theOptions.add("Convert a simple map into its string representation");
        theOptions.add("Convert a complex map into its string representation");
        theOptions.add("Converting types");
        theOptions.add("Miscellaneous utilities");
        theOptions.add("Getting and setting console size");

        try {
            theMenu = new Menu();
            theMenu.setIsRootMenu(true);
            theMenu.setTitle("Demo for Utiles library");
            theMenu.setMessage(msg);
            theMenu.setOptions(theOptions);
        } catch (MenuException e) {
            throw new RuntimeException(e);
        }

        try {
            mm = new MenuManager(theMenu);
        } catch (MenuManagerException e) {
            error(logger, e.getMessage());
            throw new RuntimeException(e);
        }
        
        do {
            try {
                getTc().clearScreen(true);
                mm.getMenu().setSelectedOption(Menu.WRONG_OPTION);
                mm.showMenu(false);
                response = mm.awaitResponse(msg);
            } catch (Exception e) {
                error(logger, e.getMessage());
                if (e instanceof MenuException) {
                    if (e.getMessage().contains(MenuErrors.ERR_BLANK_NULL) ||
                            e.getMessage().contains(MenuErrors.ERR_NO_NUMBER) ||
                            e.getMessage()
                                .contains(MenuErrors.ERR_SELECTED_OPTION_IS_OUTSIDE_THE_ALLOWED_RANGE)) {
                        theMenu.setMessage(e.getMessage());
                    }
                }
            }
            switch (response.intValue()) {
                case 1:
                    showSampleObjects();
                    break;
                case 2:
                    convertSimpleMapToString();
                    break;
                case 3:
                    convertComplexMapToString();
                    break;
                case 4:
                    convertTypes();
                    break;
                case 5:
                    miscellaneousUtilities();
                    break;
                case 6:
                    gettingAndSettingConsoleSize();
                    break;
                default:
                    break;
            }
        } while (response != 0);
    }

    private void gettingAndSettingConsoleSize() throws Exception {
        TerminalControl tc = new TerminalControl();
        tc.terminalControl();
    }
    
    /**
     * Provides functionality for miscellaneous utility operations within the application.
     * <p>
     * This method is invoked as an option in the main menu system of the application.
     * It is designed to showcase or execute less common, yet helpful, features or
     * tasks that do not fall under the other predefined categories. This can include
     * one-off processes, specialized utilities, or experimental functionality.
     * <p>
     * The method operates as a private helper and is intended to be used internally as
     * part of the application's workflow, particularly when option 5 in the menu is selected.
     */
    private void miscellaneousUtilities() throws Exception {
        MiscellaneousUtilities mu = MiscellaneousUtilities.getInstance();
        mu.miscellaneousUtilities();
    }

    /**
     * Provides functionality for type conversion operations within the application.
     * <p>
     * This method is invoked as an option in the main menu system of the application.
     * It is designed to showcase or execute type conversion operations that are not
     * directly related to the main functionality of the application.
     */
    private void convertTypes() throws Exception {
        ConvertTypes ct = ConvertTypes.getInstance();
        ct.convertTypes();
    }

    /**
     * Converts a complex map with String keys and List<Integer> values into its string representation.
     * <p>
     * This method utilizes the `generateMapOfLists` utility to create a map structure
     * from two lists of integers. The lists are generated using the `generateList` method,
     * with one constructed in ascending order and the other in descending order, based on the
     * boolean argument passed. The resulting map is then passed to the
     * `ConvertMapsToString.convertComplexMapToString` method for conversion into its string
     * representation.
     * <p>
     * The method serves as a private helper to demonstrate the functionality of converting
     * a complex map structure into a textual format.
     *
     * @throws Exception if an error occurs during map generation or conversion process
     */
    private void convertComplexMapToString() throws Exception {
       ConvertMapsToString cm2s = ConvertMapsToString.getInstance();
        cm2s.convertComplexMapToString(
            ObjectsGenerator.generateMapOfLists(
                ObjectsGenerator.generateList(true),
                ObjectsGenerator.generateList(false)
            )
        );
    }

    /**
     * Converts a simple map into its string representation.
     * <p>
     * This method serves as an internal helper to demonstrate the functionality
     * of converting a simple map of String keys and Integer values into a textual format.
     * It relies on the `ConvertMapsToString.convertSimpleMapToString` method to perform
     * the actual conversion. The map to be converted is generated using the `generateMap`
     * utility method.
     *
     * @throws Exception if an error occurs during the map generation or conversion process
     */
    private void convertSimpleMapToString() throws Exception {
        ConvertMapsToString cm2s = ConvertMapsToString.getInstance();
        cm2s.convertSimpleMapToString(ObjectsGenerator.generateMap());
    }

    /**
     * Displays sample objects and their structure using a defined format for output.
     * <p>
     * This method demonstrates the creation and representation of various data structures,
     * including maps and lists, constructed using the utility methods `generateMap`,
     * `generateList`, and `generateMapOfLists`. The generated sample objects are printed
     * using the `prtln` method, ensuring a standardized output format.
     * <p>
     * Specifically, the method:
     * - Prints an introductory title for the sample objects section.
     * - Creates and prints a simple map from the `generateMap` method.
     * - Creates and prints two lists: one in ascending order and one in descending order,
     * generated by calling `generateList` with appropriate arguments.
     * - Creates a map containing lists as values, using the `generateMapOfLists` method,
     * and prints its structure.
     * - Invokes a pause to allow observation of the output.
     *
     * @throws Exception if an error occurs during the generation or display of the sample objects.
     */
    private void showSampleObjects() throws Exception {
        ShowSampleObjects sso = ShowSampleObjects.getInstance();
        sso.showObjects();
    }

    //----------------------------------------------------------------------------
    //Utility methods for main()
    /**
     * Prints at the same time the string msg to standard output and to the logger
     *
     * @param msg the string to print
     */
    private static void print(String msg) {
        prtln(1, msg);
        dbg(logger, msg);
    }
}

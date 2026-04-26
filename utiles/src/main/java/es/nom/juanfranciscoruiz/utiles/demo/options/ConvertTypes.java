package es.nom.juanfranciscoruiz.utiles.demo.options;

import es.nom.juanfranciscoruiz.utiles.TermCtl;
import es.nom.juanfranciscoruiz.utiles.TypeConverter;
import es.nom.juanfranciscoruiz.utiles.exceptions.TypeConverterException;
import es.nom.juanfranciscoruiz.utiles.impl.TermCtlImpl;
import es.nom.juanfranciscoruiz.utiles.model.SampleObject;
import es.nom.juanfranciscoruiz.utiles.model.Using;

import java.util.*;
import java.util.random.RandomGenerator;

import static es.nom.juanfranciscoruiz.utiles.Stuff.*;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.*;

/**
 * The ConvertTypes class provides a placeholder for demonstrating type conversion
 * functionalities. The class includes a static method to simulate the conversion
 * of types with formatted messages and pauses for demonstration purposes.
 * <p>
 * This class does not currently implement any actual type conversion logic but
 * serves as a framework for future development of type conversion utilities.
 * <p>
 * Utility methods such as formatted printing, title generation, and pausing
 * are used to enhance the demonstration output.
 */
public class ConvertTypes {
    /**
     * Private constructor to make this class non-instantiable
     */
    private TermCtl tc;

    /**
     * Private constructor to make this class non-instantiable
     */
    private ConvertTypes() {
        tc = new TermCtlImpl();
    }

    /**
     * Singleton instance of the ConvertTypes class.
     */
    private static final ConvertTypes INSTANCE = new ConvertTypes();

    /**
     * Returns the singleton instance of the ConvertTypes class.
     * This ensures that only one instance of the class exists throughout
     * the application, following the Singleton design pattern.
     *
     * @return the singleton instance of the ConvertTypes class.
     */
    public static ConvertTypes getInstance() {
        return INSTANCE;
    }

    /**
     * Retrieves the TermCtl implementation associated with this class.
     * The TermCtl interface provides methods for interacting with the terminal,
     * such as getting or setting console dimensions and clearing the screen.
     *
     * @return the TermCtl instance used by this class.
     */
    public TermCtl getTc() {
        return tc;
    }

    /**
     * Sets the TermCtl implementation for this class.
     *
     * @param tc the TermCtl instance to be associated with this class.
     */
    public void setTc(TermCtl tc) {
        this.tc = tc;
    }

    /**
     * Simulates the process of type conversion by displaying placeholder messages
     * and pausing for demonstration purposes.
     * <p>
     * This method clears the screen, displays a formatted title message indicating
     * that types are being converted, logs that the conversion logic is not yet
     * implemented, and pauses for a predefined duration.
     * <p>
     * The method uses the following utility functionalities:
     * - Clears the terminal screen using `getInstance().getTc().clearScreen(true)`.
     * - Prints formatted messages with utility methods such as `prtln` and `title`.
     * - Pauses execution using the `pause` method.
     * <p>
     * Note: The actual implementation for converting types has not been provided
     * and is indicated by a placeholder comment within the method.
     *
     * @throws Exception if an error occurs during screen clearing or pausing.
     */
    public void run() throws Exception {

        String title = "Converting types";
        String subtitle = "This demo shows you several methods to convert types.";
        String message;
        String yourInput;
        String response;

        try {
            clearScreenAndPrintTitle(title, subtitle);
            arrayByte2StringDemo();
            pause(FOREVER, null);

            clearScreenAndPrintTitle(title, subtitle);
            extractLongFromStringDemo();
            pause(FOREVER, null);

            clearScreenAndPrintTitle(title, subtitle);
            extractDigitsDemo();
            pause(FOREVER, null);

            clearScreenAndPrintTitle(title, subtitle);
            extractDoubleFromStringDemo();
            pause(FOREVER, null);

            clearScreenAndPrintTitle(title, subtitle);
            map2StringDemo();
            pause(FOREVER, null);

            clearScreenAndPrintTitle(title, subtitle);
            byteToHexDemo();
            pause(FOREVER, null);

            clearScreenAndPrintTitle(title, subtitle);
            charToHexDemo();
            pause(FOREVER, null);

            clearScreenAndPrintTitle(title, subtitle);
            array2StringDemo();
            pause(FOREVER, null);

            clearScreenAndPrintTitle(title, subtitle);
            collection2ListDemo();
            pause(FOREVER, null);

            clearScreenAndPrintTitle(title, subtitle);
            map2ListDemo();
            pause(FOREVER, null);
        } catch (TypeConverterException e) {
            dbg(logger, e.getMessage());
            prtln(2, "Something went wrong: " + e.getMessage());
        }
        pause(FOREVER, "This demonstration has ended. Press <ENTER> to return to the menu.");
    }

    /**
     * Demonstrates the usage of the {@code arrayByte2String} method from the
     * {@code TypeConverter} class and displays a textual representation of
     * the contents of a byte array.
     * <p>
     * The textual representation includes details such as the length of the
     * array and the index of each byte, creating a human-readable format
     * of the byte array's contents.
     *
     */
    private void arrayByte2StringDemo()  {
        String message, yourInput, response;

        message = "The arrayByte2String() method returns a textual representation of the bytes in an array.";
        prtln(1, message);
        byte[] array = {
                84, 104, 101, 32, 98, 101, 115, 116, 32, 97, 112, 105,
                32, 108, 105, 98, 114, 97, 114, 121, 32, 111, 102, 32, 116,
                104, 101, 32, 119, 111, 114, 108, 100, 33
        };
        response = TypeConverter.arrayByte2String(array, true, true);
        prtln(2, "The array contains the following bytes (with toString() default method): " + SL +
                Arrays.toString(array) + SL +
                "The textual representation generated by the arrayByte2String() method showing the length and the index is: " + SL
                +  response);

    }

    /**
     * Demonstrates the process of extracting a {@code Long} value from a user-provided string input.
     * <p>
     * This method prompts the user to input a string containing digits and other characters.
     * The input is processed using the {@code TypeConverter.extractLongFromString} method
     * to extract the first valid {@code Long} value found within the string.
     */
    private void extractLongFromStringDemo() {
        String message, yourInput;

        message = "Please, type a string with digits and other chars. " + SL +
                "Press <ENTER> to send your input. ";
        prtln(1, message);
        prt("-> ");
        yourInput = read();
        Long longFromString = TypeConverter.extractLongFromString(yourInput);
        prtln(2, "* Your input was :" + SL + "'" + yourInput + "'.");
        prtln(2, "* The long number extracted by the 'extractLongFromString()' method in your input is:" + SL +
                "'" + longFromString + "'");
    }

    /**
     * Demonstrates the process of extracting a {@code Double} value from a user-provided string input.
     * <p>
     * This method prompts the user to enter a string that may contain numeric values along with
     * other characters. The input is then processed to extract the first valid {@code Double} value
     * found within the string. The extracted value, if any, is displayed to the user.
     */
    private void extractDoubleFromStringDemo(){
        String message;
        String yourInput;
        String response;
        Double doubleFromString;
        message = "Enter a string of characters that contains a double-precision decimal number but add and insert " + SL +
                "invalid characters such as letters and symbols (other than the minus sign and decimal point, " + SL +
                "which are valid)." + SL +
                "Press <ENTER> to send your input.";
        prtln(1, message);
        prt("-> ");
        yourInput = read();
        doubleFromString = TypeConverter.extractDoubleFromString(yourInput);
        prtln(2, "* Your input was :" + SL + "'" + yourInput + "'.");
        prtln(2, "* The double number extracted by the 'extractDoubleFromString()' method in your input was :"  + SL +
                "'" + doubleFromString + "'");

    }

    /**
     * Demonstrates the process of extracting digits from a user-provided string input.
     * <p>
     * This method prompts the user to enter a string containing digits alongside other
     * characters. The input string is processed using the {@code TypeConverter.extractDigits}
     * method to extract all numeric digits present in the string. The extracted digits
     * are then displayed back to the user, along with their original input.
     */
    private void extractDigitsDemo() {
        String message, yourInput, response;

        message = "Please, enter some text containing digits. The digits can be jumbled however you like. " + SL +
                "Press <ENTER> to send your input.";
        prtln(2, message);
        prt("-> ");
        yourInput = read();
        response = TypeConverter.extractDigits(yourInput);
        prtln(2, "* You entered :" + SL +
                "'" + yourInput);
        prtln(2,"* The method 'extractDigits()' extracted the next digits: " + SL +
                "'" + response + "'.");
    }

    /**
     * Demonstrates the conversion of a Map to a string representation using a custom utility method.
     * <p>
     * In this method:
     * - A Map with Integer keys and String values is initialized and populated.<br>
     * - The textual representation of the Map is displayed using both the default
     *   toString() method of the Map and a custom map2String() utility method.<br>
     * - Messages and results are printed using the prtln() method.<br>
     * <p>
     * This method assumes the existence of the TypeConverter.map2String() utility method
     * for string conversion of the Map and uses prtln() to perform message output.
     */
    private void map2StringDemo(){
        String message, response;
        Map<Integer, String> theMap = new HashMap<>();
        theMap.put(0, "ZERO");
        theMap.put(1, "ONE");
        theMap.put(2, "TWO");
        theMap.put(3, "THREE");
        theMap.put(4, "FOUR");
        theMap.put(5, "FIVE");
        theMap.put(6, "SIX");
        theMap.put(7, "SEVEN");
        theMap.put(8, "EIGHT");
        theMap.put(9, "NINE");
        theMap.put(10, "TEN");

        message = "This demo shows the textual representation of a map using the map2String() method.";
        prtln(2, message);
        response = TypeConverter.map2String(theMap);
        prtln(2, "* The values of the map with its default toString() method are: " + SL +  theMap);
        prtln(2, "* The values of the map with the 'map2String()' method are: " + SL + response);
    }

    /**
     * Demonstrates the conversion of a byte value to its hexadecimal representation.
     */
    private void byteToHexDemo(){
        String message, yourInput, response;
        byte aByte = 0;


        message = "Please, enter a number between 0 and 127 (this is the range of a byte value)." + SL +
                "Press <ENTER> to send your input or '-1' to end this demo.";
        while (true){
            prtln(2, message);
            prt("-> ");
            yourInput = read();

            try {
                aByte = !yourInput.isEmpty() ? Byte.parseByte(yourInput) : -1;
            } catch (NumberFormatException e){
                error(logger, "The input is not a number. Please, try again.");
                prtln(2, "The input is not a number. Please, try again.");
            }
            if (aByte == -1) {
                break;
            }
            prtln(1,"");
            response = TypeConverter.byteToHex(aByte);
            prtln(2, "* You entered :" + SL +
                    "'" + yourInput + "'");
            prtln(2,"The method 'byteToHex()' generated the next hexadecimal value: " + SL +
                    "'" + response + "'.");
        }


    }

    /**
     * Demonstrates the conversion of a char value to its hexadecimal representation.
     */
    private void charToHexDemo(){
        String message, response, text1, text2;
        char aChar;

        text1 = "* The value of the char '";
        text2 = "' converted to hexadecimal number by the 'charToHex()' method is: " + SL;
        message = "This demo use 'charToHex()' method to returns a string with the hexadecimal representation of the char c";

        prtln(2, message);
        aChar = '\u2700';
        response = TypeConverter.charToHex(aChar);
        prtln(2, text1 + aChar + text2 + response);
        aChar = '\u2708';
        response = TypeConverter.charToHex(aChar);
        prtln(2, text1 + aChar + text2 + response);
        aChar = '\u270c';
        response = TypeConverter.charToHex(aChar);
        prtln(2, text1 + aChar + text2 + response);
    }

    private void array2StringDemo(){
        String message, response, text1, text2;
        SampleObject[] sampleObjects = new SampleObject[10];

        sampleObjects[0] = generateRandomSampleObject();
        sampleObjects[1] = generateRandomSampleObject();
        sampleObjects[2] = generateRandomSampleObject();

        SampleObject[] insideSampleObjects = new SampleObject[5];


        text1 = "* The value of the array '";
        text2 = "' converted to a string by the 'array2String()' method is: " + SL;
        message = "This demo use 'array2String()' method to returns a different textual representation of an array";

        prtln(2, message);
        response = TypeConverter.array2String(sampleObjects);
        prtln(2, text1 + Arrays.toString(sampleObjects) + text2 + response);

    }

    private void collection2ListDemo(){
        String message, text1, text2;
        Collection<Integer> vector = new Vector<>();
        Stack<String> stack = new Stack<>();
        List<?> responseList = new ArrayList<>();

        vector.add(Integer.valueOf(1));
        vector.add(Integer.valueOf(2));
        vector.add(Integer.valueOf(3));


        stack.add("uno");
        stack.add("dos");
        stack.add("tres");
        stack.add("cuatro");
        stack.add("cinco");


        message = "This demo use 'collection2ListDemo()' method to convert a generic collection in a list";
        text1 = "* Thanks to the 'collection2List()' method, the values of different collection types are converted into lists.";

        prtln(2, message);
        responseList = TypeConverter.collection2List(Integer.class, vector);
        prtln(2, text1 + SL + responseList);
        responseList = TypeConverter.collection2List(String.class, stack);
        prtln(2, text1 + SL + responseList);
    }

    private void map2ListDemo(){
        String message, text1, text2;
        Map<String, Object> theMap = new HashMap<>();
        List<?> responseList = new ArrayList<>();

        theMap.put("ZERO", 0);
        theMap.put("ONE", 1);
        theMap.put("TWO", 2);
        theMap.put("THREE", 3);
        theMap.put("FOUR", 4);
        theMap.put("FIVE", 5);
        theMap.put("SIX", 6);
        theMap.put("SEVEN", 7);

        message = "This demo use 'map2list()' method to convert a map in a list";
        text1 = "* Thanks to the 'collection2List()' method, the values of different collection types are converted into values of a list:";

        prtln(2, message);
        responseList = TypeConverter.map2List(Integer.class, theMap);
        prtln(2, text1 + SL + responseList);
    }

    //Helper methods
    /**
     * Clears the terminal screen and prints a title and subtitle.
     * <p>
     * This method uses the singleton instance of the containing class to clear
     * the terminal screen using an ANSI escape sequence and then prints a
     * formatted title followed by a specified subtitle.
     *
     * @param title    the main title to be displayed, formatted with asterisks
     *                 (*) and centered within a line of 80 characters
     * @param subtitle the subtitle to be displayed immediately below the title
     */
    private void clearScreenAndPrintTitle(String title, String subtitle) {
        getInstance().getTc().clearScreen(Using.ANSI);
        prtln(2, title(title, '*', 80));
        prtln(2, subtitle);
    }

    /**
     * Displays a message indicating that a specific demonstration has not been implemented.
     * If a title is provided, it will be included in the message to specify which demo
     * is not yet available.
     *
     * @param title the title of the demonstration that is not implemented; if null,
     *              a generic message is displayed.
     */
    private void showDemoNotImplemented(String title){
        String msg;
        if (title == null) {
            msg = "Demo not yet implemented";
        } else {
            msg = "The demonstration '" + title + "' is not yet implemented";
        }
        prtln(2, msg);
    }

    /**
     * Generates a random instance of SampleObject, populating its fields with random values
     * utilizing a default random generator. The fields include various primitive and complex types.
     *
     * @return a SampleObject with fields initialized to random values.
     */
    private SampleObject generateRandomSampleObject(){
        SampleObject so = new SampleObject();
        so.setaBoolean(RandomGenerator.getDefault().nextBoolean());
        so.setaChar((char) RandomGenerator.getDefault().nextInt(255));
        so.setaDouble(RandomGenerator.getDefault().nextDouble());
        so.setaFloat(RandomGenerator.getDefault().nextFloat());
        so.setAnInt(RandomGenerator.getDefault().nextInt());
        so.setIntegers( generateRandomSampleObjects(so.getIntegers().length));
        return so;
    }

    /**
     * Generates an array of random Integer objects, where the size of the array is specified
     * by the provided parameter. Each element in the array is populated with a random integer
     * value generated using the default random generator.
     *
     * @param n the number of random Integer objects to generate. This defines the size of the
     *          resulting array.
     * @return an array of randomly generated Integer objects, with a length equal to the value of n.
     */
    private Integer[] generateRandomSampleObjects(int n){
        Integer[] integers = new Integer[n];
        for (int i = 0; i < n; i++) {
            integers[i] = RandomGenerator.getDefault().nextInt();
        }
        return integers;
    }
}

package es.nom.juanfranciscoruiz.utiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.nom.juanfranciscoruiz.utiles.impl.IO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that demonstrates the functionalities of the "util" library
 *
 * @author juanf
 */
public class Inicio {

    /**
     * Logger for this class
     */
    public final static Logger logger = LoggerFactory.getLogger(Inicio.class);

    /**
     * The entry point of the program.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String sl = System.lineSeparator();

        String msg = "1. Starting the application";
        print(msg);

        msg = "\tGenerating the sample objects...";
        print(msg);
        HashMap<String, Integer> map = (HashMap<String, Integer>) generateMap();
        List<Integer> list1 = generateList(true);
        List<Integer> list2 = generateList(false);
        HashMap<String, List<Integer>> map2 =
                (HashMap<String, List<Integer>>) generateMapOfLists(list1, list2);

        msg = "\tThese are the objects generated...";
        print(msg);
        msg = "\tA simple map:\n\t" + map;
        print(msg);
        print(sl);
        msg = "\tA list of integers from 0 to 10:\n\t" + list1;
        print(msg);
        print(sl);
        msg = "\tAnother list of integer from 10 to 0:\n\t" + list2;
        print(msg);
        print(sl);
        msg = "\tA little bit complex map : a map of Strings as keys and " +
                "List of Integers as values:\n\t" +
                map2;
        print(msg);
        print(sl);

        msg = "2. Converting a simple map into its string representation.";
        print(msg);
        print(TypeConverter.map2String(map));

        print(sl);
        msg = "3. Converting a map whose key/value pair " + sl
                + "consists of a string as key and a list of integers " + sl
                + "as values to its textual representation." + sl;
        print(msg);
        print(TypeConverter.map2String(map2));

        print(sl);
        msg = "4. End of application" + sl;
        print(msg);

        System.exit(0);
    }

    //Utility methods for main()
    /**
     * Generates a map of String and Integer key/value pairs
     *
     * @return a map of String and Integer key/value pairs
     */
    private static Map<String, Integer> generateMap() {
        HashMap<String, Integer> theMap = new HashMap<>();
        theMap.put("One", 1);
        theMap.put("Two", 2);
        theMap.put("Three", 3);
        theMap.put("Four", 4);
        theMap.put("Five", 5);
        theMap.put("Six", 6);
        return theMap;
    }

    /**
     * Generates a list of Integer values
     *
     * @param order If it is true the order of the integers is ascending,
     *              otherwise the order will be descending
     * @return a list of Integer values in ascending or descending order.
     */
    private static List<Integer> generateList(boolean order) {
        int i, end, step;
        List<Integer> theList = new ArrayList<>();
        if (order) {
            i = 0;
            end = 10;
            step = 1;
            for (int c = i; c <= end; c += step) {
                theList.add(c);
            }
        } else {
            i = 10;
            end = 0;
            step = -1;
            for (int c = i; c >= end; c += step) {
                theList.add(c);
            }
        }
        return theList;
    }

    /**
     * Generate a little bit complex data structure, a map of Strings as keys and
     * List of Integers as values.
     *
     * @param list1 A list of integers for the first pair of the map
     * @param list2 A list of integers for the second pair of the map
     * @return a map of Strings and Lists of Integers
     */
    private static Map<String, List<Integer>> generateMapOfLists(List<Integer> list1, List<Integer> list2) {
        HashMap<String, List<Integer>> theMap = new HashMap<>();
        theMap.put("list1", list1);
        theMap.put("list2", list2);

        return theMap;
    }

    /**
     * Prints at the same time the string msg to standard output and to the logger
     *
     * @param msg the string to print
     */
    private static void print(String msg) {
        if(logger.isDebugEnabled()) logger.debug(msg);
        IO.prt(msg);
    }
}

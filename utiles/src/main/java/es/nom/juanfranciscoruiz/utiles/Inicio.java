package es.nom.juanfranciscoruiz.utiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that demonstrates the functionalities of the "util" library
 *
 * @author juanf
 */
public class Inicio {

    public final static Logger logger = LoggerFactory.getLogger(Inicio.class);

    public static void main(String[] args) {
        logger.info("Starting the application");

        HashMap<String, Integer> map = (HashMap<String, Integer>) generateMap();

        List<Integer> list1 = generateList(true);

        List<Integer> list2 = generateList(false);

        HashMap<String, List<Integer>> map2 = (HashMap<String, List<Integer>>) generateMapOfLists(list1, list2);

        System.out.println("Converting a simple map into its string representation.");
        System.out.println(TypeConverter.map2String(map));
        System.out.println();
        System.out.println("Converting a map whose key/value pair consists of a "
                + "string as key and a list of integers as values"
                + " ​​to its textual representation.");
        System.out.println(TypeConverter.map2String(map2));

        logger.debug(TypeConverter.map2String(map));

        logger.info("End of application");

    }

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
     * otherwise the order will be descending
     * @return a list of Integer values in ascending or descending order.
     */
    private static List<Integer> generateList(boolean order) {
        int i, end, step;
        List<Integer> theList = new ArrayList<>();
        if (order) {
            i = 0;
            end = 10;
            step = 1;
            for (int c = i; c <= end; c += step){
                theList.add(c);
            }
        } else {
            i = 10;
            end = 0;
            step = -1;
            for (int c = i; c >= end; c += step){
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
     * @return 
     */
    private static Map<String, List<Integer>> generateMapOfLists(List<Integer> list1, List<Integer> list2) {
        HashMap<String, List<Integer>> theMap = new HashMap<>();
        theMap.put("list1", list1);
        theMap.put("list2", list2);

        return theMap;
    }
}

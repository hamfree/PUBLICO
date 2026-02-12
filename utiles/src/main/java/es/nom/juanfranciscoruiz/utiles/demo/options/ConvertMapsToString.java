package es.nom.juanfranciscoruiz.utiles.demo.options;

import es.nom.juanfranciscoruiz.utiles.TypeConverter;
import es.nom.juanfranciscoruiz.utiles.impl.IOimpl;

import static es.nom.juanfranciscoruiz.utiles.Util.*;
import static es.nom.juanfranciscoruiz.utiles.Util.pause;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.clearScreen;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.title;

/**
 * The ConvertMapsToString class provides methods to convert maps into their string
 * representations, handling both simple maps and maps with more complex structures.
 * It relies on external utilities for formatting and type conversion.
 * <p>
 * Performs the the second and third options of the menu shown in Demo class.
 */
public class ConvertMapsToString {
    /**
     * Private constructor to make this class non-instantiable
     */
    private ConvertMapsToString() {
    }


    /**
     * Converts a simple map with String keys and Integer values into its string representation.
     * The method formats the map output using external utility methods for display purposes.
     *
     * @param map the simple map to be converted, where the keys are of type String and the values are of type Integer
     * @throws Exception if an error occurs during the conversion process
     */
    public static void convertSimpleMapToString(java.util.Map<String, Integer> map) throws Exception {
        clearScreen(false);
        String msg = "Converting a simple map into its string representation.";
        IOimpl.prtln(2,title(msg,'*',80));
        IOimpl.prtln(1,"Map: ");
        IOimpl.prtln(2,map);
        IOimpl.prtln(1,"String representation: ");
        IOimpl.prtln(2,TypeConverter.map2String(map));
        pause(FOREVER,"");
    }

    /**
     * Converts a complex map with String keys and List<Integer> values into its string representation.
     * The method formats the map output using external utility methods for display purposes.
     *
     * @param map2 the complex map to be converted, where the keys are of type String and the values are of type List<Integer>
     * @throws Exception if an error occurs during the conversion process
     */
    public static void convertComplexMapToString(java.util.Map<String, java.util.List<Integer>> map2) throws Exception {
        clearScreen(false);
        String msg = "Converting a complex map to its textual representation.";
        IOimpl.prtln(2,title(msg,'*',80));
        IOimpl.prtln(1,"A little bit complex Map: ");
        IOimpl.prtln(2,map2);
        IOimpl.prtln(2, TypeConverter.map2String(map2));
        pause(FOREVER,"");
    }

}

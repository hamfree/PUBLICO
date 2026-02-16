package es.nom.juanfranciscoruiz.utiles.demo.options;

import es.nom.juanfranciscoruiz.utiles.TermCtl;
import es.nom.juanfranciscoruiz.utiles.TypeConverter;
import es.nom.juanfranciscoruiz.utiles.impl.IOimpl;
import es.nom.juanfranciscoruiz.utiles.impl.TermCtlImpl;

import static es.nom.juanfranciscoruiz.utiles.Util.*;
import static es.nom.juanfranciscoruiz.utiles.Util.pause;
import static es.nom.juanfranciscoruiz.utiles.impl.IOimpl.title;

/**
 * The ConvertMapsToString class provides methods to convert maps into their string
 * representations, handling both simple maps and maps with more complex structures.
 * It relies on external utilities for formatting and type conversion.
 * <p>
 * Performs the the second and third options of the menu shown in Demo class.
 */
public class ConvertMapsToString {
    private TermCtl tc;
    
    /**
     * Private constructor to make this class non-instantiable
     */
    private ConvertMapsToString() {
        tc = new TermCtlImpl();
    }
    
    /**
     * Singleton instance of the ConvertMapsToString class.
     */
    public static final ConvertMapsToString INSTANCE = new ConvertMapsToString();
    
    /**
     * Provides access to the singleton instance of the ConvertMapsToString class.
     * This method ensures that only one instance of ConvertMapsToString exists
     * and provides a global point of access to it.
     *
     * @return the singleton instance of the ConvertMapsToString class
     */
    public static ConvertMapsToString getInstance() {
        return INSTANCE;
    }
    
    /**
     * Retrieves the current instance of the TermCtl interface.
     * The TermCtl instance provides various methods for interacting
     * with terminal properties, such as obtaining or modifying
     * console dimensions and clearing the screen.
     *
     * @return the TermCtl instance associated with the class.
     */
    public TermCtl getTc() {
        return tc;
    }
    
    /**
     * Sets the TermCtl instance for this class. The TermCtl instance provides
     * various methods for interacting with terminal properties, such as obtaining
     * or modifying console dimensions and clearing the screen.
     *
     * @param tc the TermCtl instance to be associated with this class
     */
    public void setTc(TermCtl tc) {
        this.tc = tc;
    }
    
    /**
     * Converts a simple map with String keys and Integer values into its string representation.
     * The method formats the map output using external utility methods for display purposes.
     *
     * @param map the simple map to be converted, where the keys are of type String and the values are of type Integer
     * @throws Exception if an error occurs during the conversion process
     */
    public void runConversionSimpleMap(java.util.Map<String, Integer> map) throws Exception {
        this.getTc().clearScreen(true);
        String msg = "Converting a simple map into its string representation.";
        IOimpl.prtln(2,title(msg,'*',80));
        IOimpl.prtln(1,"Map: ");
        IOimpl.prtln(2,map);
        IOimpl.prtln(1,"String representation: ");
        IOimpl.prtln(2,TypeConverter.map2String(map));
        pause(FOREVER,"");
    }

    /**
     * Converts a complex map with String keys and 'List&lt;Integer&gt;' values into its string representation.
     * The method formats the map output using external utility methods for display purposes.
     *
     * @param map2 the complex map to be converted, where the keys are of type String and the values are of type List&lt;Integer&gt;
     * @throws Exception if an error occurs during the conversion process
     */
    public void runConversionComplexMap(java.util.Map<String, java.util.List<Integer>> map2) throws Exception {
        this.getTc().clearScreen(true);
        String msg = "Converting a complex map to its textual representation.";
        IOimpl.prtln(2,title(msg,'*',80));
        IOimpl.prtln(1,"A little bit complex Map: ");
        IOimpl.prtln(2,map2);
        IOimpl.prtln(2, TypeConverter.map2String(map2));
        pause(FOREVER,"");
    }

}

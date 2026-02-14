package es.nom.juanfranciscoruiz.utiles.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A utility class for generating various types of objects, such as maps, lists,
 * and more complex data structures.
 */
public class ObjectsGenerator {
  /**
   * Singleton instance of the ObjectsGenerator class.
   */
  private static final ObjectsGenerator INSTANCE = new ObjectsGenerator();

  /**
   * Private constructor to prevent instantiation from outside the class.
   */
  private ObjectsGenerator() { }

  /**
   * Provides access to the singleton instance of the ObjectsGenerator class.
   *
   * @return the singleton instance of the ObjectsGenerator class
   */
  public static ObjectsGenerator getInstance() {
    return INSTANCE;
  }

  /**
   * Generates a map of String and Integer key/value pairs
   *
   * @return a map of String and Integer key/value pairs
   */
  public static Map<String, Integer> generateMap() {
    Map<String, Integer> theMap = new java.util.HashMap<>();
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
  public static List<Integer> generateList(boolean order) {
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
  public static Map<String, List<Integer>> generateMapOfLists(List<Integer> list1, List<Integer> list2) {
    Map<String, List<Integer>> theMapOfLists = new java.util.HashMap<>();
    theMapOfLists.put("list1", list1);
    theMapOfLists.put("list2", list2);
    
    return theMapOfLists;
  }
}

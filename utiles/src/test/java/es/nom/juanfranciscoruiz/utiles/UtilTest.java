package es.nom.juanfranciscoruiz.utiles;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.junit.jupiter.api.Test;

import static es.nom.juanfranciscoruiz.utiles.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Testing the Util class
 *
 * @author Juan F. Ruiz
 */
public class UtilTest {
  
  public final static Logger logger = LoggerFactory.getLogger(UtilTest.class);
  
  /**
   * Tests that features are returned as map
   */
  @Test
  public void testGetFeaturesAsMap() {
    printTitletoLogAndConsole("testGetFeaturesAsMap()", logger);
    
    Map<String, String> expectedValue = new HashMap<>();
    Map<String, String> actualValue;
    
    Runtime rt = Runtime.getRuntime();
    NumberFormat numberFormatter = NumberFormat.getNumberInstance(Locale.getDefault());
    
    System.gc();
    String freeMemory = numberFormatter.format(rt.freeMemory());
    String maxMemory = numberFormatter.format(rt.maxMemory());
    String totalMemmory = numberFormatter.format(rt.totalMemory());
    
    expectedValue.put("Processor Cores", String.valueOf(rt.availableProcessors()));
    expectedValue.put("Free Memory", freeMemory);
    expectedValue.put("Available Memory", maxMemory);
    expectedValue.put("Total Memory", totalMemmory);
    actualValue = Util.getFeaturesAsMap();
    printResultsToLogAndConsole(expectedValue, actualValue, logger);
    assertEquals(expectedValue.size(),
        actualValue.size(), "It should return a map with four specific keys");
    assertEquals(expectedValue.get("Available Memory"),
        actualValue.get("Available Memory"), "Available memory must be the same");
  }
  
  /**
   * Tests that system properties are returned as map
   */
  @Test
  public void testGetSystemPropertiesAsMap() {
    printTitletoLogAndConsole("testGetSystemPropertiesAsMap()", logger);
    
    HashMap<String, String> expectedValue = new HashMap<>();
    HashMap<String, String> actualValue;
    Object key;
    Object value;
    String sKey = null;
    String sValue = null;
    Class<?> clazz;
    
    Properties p = System.getProperties();
    for (Map.Entry<Object, Object> entry : p.entrySet()) {
      key = entry.getKey();
      value = entry.getValue();
      
      clazz = key.getClass();
      if (clazz.isAssignableFrom(String.class)) {
        sKey = (String) key;
      }
      clazz = value.getClass();
      if (clazz.isAssignableFrom(String.class)) {
        sValue = (String) value;
      }
      expectedValue.put(sKey, sValue);
    }
    actualValue = (HashMap<String, String>) Util.getSystemPropertiesAsMap();
    printResultsToLogAndConsole(expectedValue, actualValue, logger);
    assertEquals(expectedValue, actualValue, "They must have the same keys and values");
  }
  
  /**
   * Test of getAllCharsets method, of class Util
   */
  @Test
  public void testGetAllCharsets() {
  }
  
  /**
   * Test of CollectionToString method, of class Util.
   */
  @Test
  public void testCollectionAsString() {
    printTitletoLogAndConsole("testCollectionToString()", logger);
    
    String[] array = generateArrayOfStrings();
    
    List<String> lista = new ArrayList<>(Arrays.asList(array));
    
    // Tests CollectionToString method with lists and maps
    assertAll(
        () -> {
          logger.debug("Displays the first 10 items in the list...");
          String expResult = stringExpectedFromList(lista, 10);
          String result = Util.CollectionToString(lista, true, 10);
          printResultsToLogAndConsole(expResult, result, logger);
          assertEquals(expResult, result, "It should display at most 10 list items.");
        },
        () -> {
          logger.debug("It should show the type and number of elements.");
          String expResult = "java.util.ArrayList 36 items.";
          String result = Util.CollectionToString(lista, false, 1);
          printResultsToLogAndConsole(expResult, result, logger);
          assertEquals(expResult, result, "It should show the type and number of elements.");
        },
        () -> {
          logger.debug("Shows the first 5 elements of the map...");
          Map<String, String> mapa = new HashMap<>();
          
          mapa.put("0", array[0]);
          mapa.put("1", array[1]);
          mapa.put("2", array[2]);
          mapa.put("3", array[3]);
          mapa.put("4", array[4]);
          mapa.put("5", array[5]);
          mapa.put("6", array[6]);
          mapa.put("7", array[7]);
          String expResult = stringExpectedFromMap(mapa, 5);
          String result = Util.CollectionToString(mapa, true, 5);
          printResultsToLogAndConsole(expResult, result, logger);
          assertEquals(expResult, result, "It should display at most 5 pairs of map values.");
        },
        () -> {
          logger.debug("Displays the type of collection (map) and the number of items it contains...");
          Map<String, String> mapa = new HashMap<>();
          
          mapa.put("0", array[0]);
          mapa.put("1", array[1]);
          mapa.put("2", array[2]);
          mapa.put("3", array[3]);
          mapa.put("4", array[4]);
          mapa.put("5", array[5]);
          mapa.put("6", array[6]);
          mapa.put("7", array[7]);
          String expResult = "java.util.HashMap 8 items.";
          String result = Util.CollectionToString(mapa, false, 2);
          printResultsToLogAndConsole(expResult, result, logger);
          assertEquals(expResult, result, "It should show the type and number of elements.");
        },
        () -> {
          logger.debug("It should display at most 7 pairs of map values.");
          Map<Integer, Integer> mapa = generateIntegerMap(15, 5, 10);
          String expResult = stringExpectedFromMap(mapa, 7);
          String result = Util.CollectionToString(mapa, true, 7);
          printResultsToLogAndConsole(expResult, result, logger);
          assertEquals(expResult, result, "It should display at most 7 pairs of map values.");
        }
    );
    
  }
  //------------------------------------------------------------------------------------
  // Helper methods for test execution testToString()
  
  /**
   * Creates array of strings by adding executables
   */
  private String[] generateArrayOfStrings() {
    return new String[]{
        "jabswitch.exe",
        "jaccessinspector.exe",
        "jaccesswalker.exe",
        "jar.exe",
        "jarsigner.exe",
        "java.exe",
        "javac.exe",
        "javadoc.exe",
        "javap.exe",
        "javaw.exe",
        "jcmd.exe",
        "jconsole.exe",
        "jdb.exe",
        "jdeprscan.exe",
        "jdeps.exe",
        "jfr.exe",
        "jhsdb.exe",
        "jimage.exe",
        "jinfo.exe",
        "jlink.exe",
        "jmap.exe",
        "jmod.exe",
        "jpackage.exe",
        "jps.exe",
        "jrunscript.exe",
        "jshell.exe",
        "jstack.exe",
        "jstat.exe",
        "jstatd.exe",
        "jwebserver.exe",
        "keytool.exe",
        "kinit.exe",
        "klist.exe",
        "ktab.exe",
        "rmiregistry.exe",
        "serialver.exe"
    };
  }
  
  /**
   * Creates list of strings by adding executables
   */
  private List<String> generateListOfStrings() {
    List<String> lista = new ArrayList<>();
    
    lista.add("jabswitch.exe");
    lista.add("jaccessinspector.exe");
    lista.add("jaccesswalker.exe");
    lista.add("jar.exe");
    lista.add("jarsigner.exe");
    lista.add("java.exe");
    lista.add("javac.exe");
    lista.add("javadoc.exe");
    lista.add("javap.exe");
    lista.add("javaw.exe");
    lista.add("jcmd.exe");
    lista.add("jconsole.exe");
    lista.add("jdb.exe");
    lista.add("jdeprscan.exe");
    lista.add("jdeps.exe");
    lista.add("jfr.exe");
    lista.add("jhsdb.exe");
    lista.add("jimage.exe");
    lista.add("jinfo.exe");
    lista.add("jlink.exe");
    lista.add("jmap.exe");
    lista.add("jmod.exe");
    lista.add("jpackage.exe");
    lista.add("jps.exe");
    lista.add("jrunscript.exe");
    lista.add("jshell.exe");
    lista.add("jstack.exe");
    lista.add("jstat.exe");
    lista.add("jstatd.exe");
    lista.add("jwebserver.exe");
    lista.add("keytool.exe");
    lista.add("kinit.exe");
    lista.add("klist.exe");
    lista.add("ktab.exe");
    lista.add("rmiregistry.exe");
    lista.add("serialver.exe");
    
    return lista;
  }
  
  private Map<Integer, Integer> generateIntegerMap(int items, int start, int increase) {
    Integer[] keyArray = generateKeyIntegerArray(items);
    Integer[] valuesArray = generateValueIntegerArray(items, start, increase);
    Map<Integer, Integer> theMap = new HashMap<>();
    for (int i = 0; i < items; i++) {
      theMap.put(keyArray[i], valuesArray[i]);
    }
    
    return theMap;
  }
  
  /**
   * Returns string representation of map contents
   */
  private String stringExpectedFromMap(Map<?, ?> theMap, int maximumElements) {
    StringBuilder sb = new StringBuilder();
    
    sb.append("[");
    if (theMap.size() < maximumElements) {
      for (Map.Entry<?, ?> e : theMap.entrySet()) {
        sb.append("{'").append(e.getKey()).append("'->'").append(e.getValue()).append("'}").append(" ");
      }
    } else {
      int i = 0;
      for (Iterator<?> it = theMap.entrySet().iterator(); it.hasNext(); ) {
        if (i < maximumElements) {
          Map.Entry<?, ?> e = (Map.Entry<?, ?>) it.next();
          sb.append("{'").append(e.getKey()).append("'->'").append(e.getValue()).append("'}").append(" ");
          i++;
        } else {
          sb.append(" ...");
          break;
        }
      }
    }
    sb.append("]");
    return sb.toString();
  }
  
  private String stringExpectedFromList(List<?> theList, int maximumElements) {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    if (theList.size() < maximumElements) {
      for (Object o : theList.toArray()) {
        sb.append("'").append(o).append("'").append(" ");
      }
    } else {
      for (int i = 0; i < maximumElements; i++) {
        sb.append("'").append(theList.get(i)).append("'").append(" ");
      }
      sb.append(" ...");
    }
    sb.append("]");
    return sb.toString();
  }
  
  private Integer[] generateKeyIntegerArray(int items) {
    Integer[] array = new Integer[items];
    for (int i = 0; i < items; i++) {
      array[i] = i;
    }
    return array;
  }
  
  private Integer[] generateValueIntegerArray(int items, int start, int increase) {
    Integer[] array = new Integer[items];
    for (int i = 0; i < items; i++) {
      array[i] = start;
      start = increase;
    }
    return array;
  }

  @Test
  void pause() {
  }

  @Test
  void pauseWithoutMessage() {
  }

  @Test
  void warn() {
  }

  @Test
  void info() {
  }

  @Test
  void error() {
  }
}

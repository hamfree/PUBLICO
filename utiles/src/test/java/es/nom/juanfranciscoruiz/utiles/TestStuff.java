package es.nom.juanfranciscoruiz.utiles;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.function.Consumer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

import static es.nom.juanfranciscoruiz.utiles.TestUtils.*;
import static es.nom.juanfranciscoruiz.utiles.Stuff.dbg;
import static org.junit.jupiter.api.Assertions.*;
import static com.github.stefanbirkner.systemlambda.SystemLambda.withTextFromSystemIn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Testing the Stuff class
 *
 * @author Juan F. Ruiz
 */
public class TestStuff {
  
  public final static Logger logger = LoggerFactory.getLogger(TestStuff.class);

  @BeforeAll
  static void beforeAll() {
    printMsgToLogAndConsole(System.lineSeparator() + LocalDateTime.now() + " - Starting UtilTest" + System.lineSeparator(), logger);
  }

  @AfterAll
  static void afterAll() {
    printMsgToLogAndConsole(System.lineSeparator() + LocalDateTime.now() + " - Ending UtilTest" + System.lineSeparator(), logger);
  }

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
    actualValue = Stuff.getFeaturesAsMap();
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
    actualValue = (HashMap<String, String>) Stuff.getSystemPropertiesAsMap();
    printResultsToLogAndConsole(expectedValue, actualValue, logger);
    assertEquals(expectedValue, actualValue, "They must have the same keys and values");
  }
  
  /**
   * Test of getAllCharsets method, of class Util
   */
  @Test
  public void testGetAllCharsets() {
    printTitletoLogAndConsole("testGetAllCharsets()", logger);

    List<java.nio.charset.Charset> actualValue = Stuff.getAllCharsets();
    printMsgToLogAndConsole("Charsets found: " + actualValue.size(), logger);

    assertAll(
        () -> assertNotNull(actualValue, "The list of charsets must not be null"),
        () -> assertFalse(actualValue.isEmpty(), "It must return at least one charset"),
        () -> assertTrue(actualValue.contains(java.nio.charset.Charset.defaultCharset()),
            "It must include the JVM's default charset")
    );
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
          String result = Stuff.CollectionToString(lista, true, 10);
          printResultsToLogAndConsole(expResult, result, logger);
          assertEquals(expResult, result, "It should display at most 10 list items.");
        },
        () -> {
          logger.debug("It should show the type and number of elements.");
          String expResult = "java.util.ArrayList 36 items.";
          String result = Stuff.CollectionToString(lista, false, 1);
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
          String result = Stuff.CollectionToString(mapa, true, 5);
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
          String result = Stuff.CollectionToString(mapa, false, 2);
          printResultsToLogAndConsole(expResult, result, logger);
          assertEquals(expResult, result, "It should show the type and number of elements.");
        },
        () -> {
          logger.debug("It should display at most 7 pairs of map values.");
          Map<Integer, Integer> mapa = generateIntegerMap(15, 5, 10);
          String expResult = stringExpectedFromMap(mapa, 7);
          String result = Stuff.CollectionToString(mapa, true, 7);
          printResultsToLogAndConsole(expResult, result, logger);
          assertEquals(expResult, result, "It should display at most 7 pairs of map values.");
        }
    );
    
  }


  @Test
  void pause() throws Exception {
    printTitletoLogAndConsole("pause()", logger);

    assertAll(
        () -> assertDoesNotThrow(() -> Stuff.pause(1, "Short wait for testing"),
            "It shouldn't make an exception for short breaks"),
        () -> assertDoesNotThrow(() -> withTextFromSystemIn("").execute(() -> Stuff.pause(Stuff.FOREVER, "Press ENTER")),
            "It should not block or throw an exception when receiving ENTER in FOREVER.")
    );
  }

  @Test
  void pauseWithoutMessage() throws Exception {
    printTitletoLogAndConsole("pauseWithoutMessage()", logger);

    assertAll(
        () -> assertDoesNotThrow(() -> Stuff.pauseWithoutMessage(1),
            "It shouldn't make an exception for short pauses without a message."),
        () -> assertDoesNotThrow(() -> withTextFromSystemIn("").execute(() -> Stuff.pauseWithoutMessage(Stuff.FOREVER)),
            "It should not block or throw an exception in FOREVER when simulating ENTER.")
    );
  }

  @Test
  void warn() {
    printTitletoLogAndConsole("warn()", logger);

    List<ILoggingEvent> events = captureLogs(Level.DEBUG, log -> Stuff.warn(log, "  notice \n\t with   spaces  "));

    assertAll(
        () -> assertEquals(1, events.size(), "You must register a single event"),
        () -> assertEquals(Level.WARN, events.get(0).getLevel(), "The level should be WARN"),
        () -> assertEquals("notice with spaces", events.get(0).getFormattedMessage(),
            "The message must be sanitized.")
    );
  }

  @Test
  void info() {
    printTitletoLogAndConsole("info()", logger);

    List<ILoggingEvent> events = captureLogs(Level.DEBUG, log -> Stuff.info(log, "  test \n\t info  "));

    assertAll(
        () -> assertEquals(1, events.size(), "You must register a single event"),
        () -> assertEquals(Level.INFO, events.get(0).getLevel(), "The level should be INFO"),
        () -> assertEquals("test info", events.get(0).getFormattedMessage(),
            "The message must be sanitized.")
    );
  }

  @Test
  void error() {
    printTitletoLogAndConsole("error()", logger);

    List<ILoggingEvent> events = captureLogs(Level.DEBUG, log -> Stuff.error(log, "  test \n\t   error  "));

    assertAll(
        () -> assertEquals(1, events.size(), "You must register a single event"),
        () -> assertEquals(Level.ERROR, events.get(0).getLevel(), "The level should be ERROR"),
        () -> assertEquals("test error", events.get(0).getFormattedMessage(),
            "The message must be sanitized.")
    );
  }

  //------------------------------------------------------------------------------------
  // Helper methods for test execution testToString()

  /**
   * Creates array of strings by adding executables
   */
  private String[] generateArrayOfStrings() {
    return new String[]{
            "jabswitch.exe","jaccessinspector.exe","jaccesswalker.exe","jar.exe",
            "jarsigner.exe","java.exe","javac.exe","javadoc.exe","javap.exe",
            "javaw.exe","jcmd.exe","jconsole.exe","jdb.exe",
            "jdeprscan.exe","jdeps.exe","jfr.exe","jhsdb.exe",
            "jimage.exe","jinfo.exe","jlink.exe","jmap.exe",
            "jmod.exe","jpackage.exe","jps.exe","jrunscript.exe",
            "jshell.exe","jstack.exe","jstat.exe","jstatd.exe",
            "jwebserver.exe","keytool.exe","kinit.exe","klist.exe",
            "ktab.exe","rmiregistry.exe","serialver.exe"
    };
  }

  /**
   * Creates list of strings by adding executables
   */
  private List<String> generateListOfStrings() {
    List<String> lista = new ArrayList<>();

    lista = Arrays.asList(String.valueOf(generateListOfStrings()));
    /*
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
    */
    return lista;
  }

  /**
   * Creates a map of integers where the keys and values are generated based on the input parameters.
   * The keys are generated sequentially starting from 0, and the values are generated with
   * a specified starting value and an increment pattern.
   *
   * @param items the number of key-value pairs to generate for the map
   * @param start the starting value for generating the values in the map
   * @param increase the value to use as the next incremental starting point for generating values
   * @return a map containing the generated key-value pairs
   */
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
   * Generates a string representation of the contents of the given map, with consideration for a maximum
   * number of elements to include. If the map contains more elements than the specified maximum, an ellipsis
   * ("...") is appended to indicate truncation.
   *
   * @param theMap the map whose contents will be represented as a string
   * @param maximumElements the maximum number of elements to include in the string representation
   * @return a string representation of the map's contents, formatted as a list of key-value pairs
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

  /**
   * Generates a string representation of the given list, with a specified maximum number of elements to include.
   * If the list contains fewer elements than the specified maximum, all elements are included.
   * If the list contains more elements than the maximum, only the first 'maximumElements' elements are included,
   * followed by an ellipsis ("...") to indicate truncation.
   *
   * @param theList the list whose contents will be represented as a string
   * @param maximumElements the maximum number of elements to include in the string representation
   * @return a string representation of the list's contents, formatted as a list of quoted elements
   */
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

  /**
   * Generates an array of integers where each element is initialized to its index value.
   *
   * @param items the number of elements in the array to generate
   * @return an array of integers with each element initialized to its respective index value
   */
  private Integer[] generateKeyIntegerArray(int items) {
    Integer[] array = new Integer[items];
    for (int i = 0; i < items; i++) {
      array[i] = i;
    }
    return array;
  }

  /**
   * Generates an array of integers where each element is calculated based on the provided
   * starting value and increment value.
   *
   * @param items the number of elements in the array to generate
   * @param start the starting value for the first element in the array
   * @param increase the value to use as the next starting point for subsequent elements
   * @return an array of integers with values generated based on the starting value and increment pattern
   */
  private Integer[] generateValueIntegerArray(int items, int start, int increase) {
    Integer[] array = new Integer[items];
    for (int i = 0; i < items; i++) {
      array[i] = start;
      start = increase;
    }
    return array;
  }

  /**
   * Captures log events at the specified logging level during the execution of a provided action.
   *
   * @param level the logging level to capture (e.g., DEBUG, INFO, WARN, ERROR)
   * @param action a consumer that performs operations on the logger, generating log events
   * @return a list of captured logging events generated during the execution of the provided action
   */
  private List<ILoggingEvent> captureLogs(Level level, Consumer<Logger> action) {
    ch.qos.logback.classic.Logger testLogger =
        (ch.qos.logback.classic.Logger) LoggerFactory.getLogger("es.nom.juanfranciscoruiz.utiles.TestStuff.capture");
    testLogger.setLevel(level);
    testLogger.setAdditive(false);

    ListAppender<ILoggingEvent> appender = new ListAppender<>();
    appender.start();
    testLogger.addAppender(appender);

    try {
      action.accept(testLogger);
      return new ArrayList<>(appender.list);
    } finally {
      testLogger.detachAppender(appender);
      appender.stop();
    }
  }
}

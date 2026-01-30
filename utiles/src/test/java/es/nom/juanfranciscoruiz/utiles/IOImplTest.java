package es.nom.juanfranciscoruiz.utiles;

import es.nom.juanfranciscoruiz.utiles.impl.IOImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junitpioneer.jupiter.StdIn;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

/**
 * Test class for the IO utility class.
 * <p>
 * This class contains unit tests to verify the functionality of IO operations
 * including printing, reading, title generation, and character repetition.
 * Tests cover both normal operation and error handling scenarios.
 * </p>
 *
 * @author Juan F. Ruiz
 */
public class IOImplTest {
    
    private static final String ERR_PARAM = "Null or incorrect parameters!";
    private static final String ERR_NULL = "Null parameters!";
    private static final String ERR_SOME_NULL = "One of the parameters is null!";
    private static final String ERR_LONG = "The line length is less than the message length";

    /**
     * Tests the prt method with multiple string arguments.
     * Verifies that arguments are concatenated without separators.
     */
    @Test
    @StdIo()
    public void testPrt(StdOut out) {
        IOImpl.prt("one", "two", "three");
        String actualValue = out.capturedString();
        String expectedValue = "onetwothree";
        assertEquals(expectedValue, actualValue, "The values should be the same");
    }

    /**
     * Tests that prt method throws IllegalArgumentException when passed a null argument.
     */
    @Test
    public void testPrtNull() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOImpl.prt((Object) null));
        assertEquals(ERR_SOME_NULL, ex.getMessage(),"An IllegalArgumentException should be thrown with the message " + ERR_NULL);
    }

    /**
     * Tests that prt method throws IllegalArgumentException when the first varargs item is null.
     */
    @Test
    public void testPrtNullInFirstItemVarArgs() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOImpl.prt((Object) null));
        assertEquals(ERR_SOME_NULL, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_SOME_NULL);
    }


    /**
     * Tests that prt method throws IllegalArgumentException when any varargs item is null.
     */
    @Test
    public void testPrtSomeNullInVarArgs() {
        Object[] args = {new Object(),null,new Object(), new Object()};
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOImpl.prt(args));
        assertEquals(ERR_SOME_NULL, ex.getMessage(), "An IllegalArgumentException should be thrown with the message" + ERR_SOME_NULL);
    }

    /**
     * Tests the prtln method with multiple arguments and line separators.
     * Verifies that arguments are concatenated with the specified number of line separators appended.
     */
    @Test
    @StdIo()
    public void testPrtln(StdOut out) {
        String sl = System.lineSeparator();
        IOImpl.prtln(2, "one", "two", "three");
        String actualValue = out.capturedString();
        String expectedValue = "onetwothree".concat(sl).concat(sl);
        assertEquals(expectedValue, actualValue, "The values ​​should be the same");
    }

    /**
     * Tests that prtln method throws IllegalArgumentException when passed a null argument.
     */
    @Test
    public void testPrtlnNull(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOImpl.prtln(1, (Object) null));
        assertEquals(ERR_SOME_NULL, ex.getMessage(), "An IllegalArgumentException should be thrown with the message" + ERR_NULL);
    }

    /**
     * Tests that prtln method throws IllegalArgumentException when the first varargs item is null.
     */
    @Test
    public void testPrtlnNullInFirtsArgInVarArgs(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOImpl.prtln(1,(Object) null));
        assertEquals(ERR_SOME_NULL, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_SOME_NULL);
    }

    /**
     * Tests that prtln method throws IllegalArgumentException when any varargs item is null.
     */
    @Test
    public void testPrtlnSomeNullInVarArgs(){
        Object[] args = {new Object(),null,new Object(), new Object()};
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOImpl.prtln(3,args));
        assertEquals(ERR_SOME_NULL, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_SOME_NULL);
    }


    /**
     * Tests the read method to verify it correctly reads input from standard input.
     */
    @Test
    @StdIo({"one"})
    public void testRead(StdIn in, StdOut out) {
        String actualValue = IOImpl.read();
        String expectedValue = "one";
        assertEquals(expectedValue, actualValue, "The values should be the same");
    }

    /**
     * Tests the title method to verify it generates a properly formatted title
     * with the message centered and surrounded by specified characters.
     */
    @Test
    public void testTitle() {
        String expectedValue = System.lineSeparator()
                .concat("********************")
                .concat(System.lineSeparator())
                .concat("      MESSAGE      ")
                .concat(System.lineSeparator())
                .concat("********************");
        String actualValue = IOImpl.title("MESSAGE", '*', 20);
        assertEquals(expectedValue, actualValue, "The values should be the same");
    }

    /**
     * Tests that title method throws IllegalArgumentException when message parameter is null.
     */
    @Test
    public void testTitleMessageNull(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOImpl.title(null, '*', 20));
        assertEquals(ERR_PARAM, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_PARAM);
    }

    /**
     * Tests that title method throws IllegalArgumentException when character parameter is null.
     */
    @Test
    public void testTitleNullCharacter(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOImpl.title("MESSAGE", null, 20));
        assertEquals(ERR_PARAM, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_PARAM);
    }

    /**
     * Tests that title method throws IllegalArgumentException when line length is less than message length.
     */
    @Test
    public void testTitleLengthLessThanLengthMessage(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOImpl.title("MESSAGE", '*', 3));
        assertEquals(ERR_LONG, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_LONG);
    }

    /**
     * Tests the line method to verify it generates a line of specified length using the given character.
     */
    @Test
    public void testLine() {
        String expectedValue = "**********";
        String actualValue = IOImpl.line('*', 10);
        assertEquals(expectedValue, actualValue,"The line() method should generate a string with 10 asterisks (*)");
    }

    /**
     * Tests that line method throws IllegalArgumentException when character parameter is null.
     */
    @Test
    public void testLineNullCharacter(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOImpl.line(null, 10));
        assertEquals(ERR_PARAM, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_PARAM);
    }

    /**
     * Tests that line method throws IllegalArgumentException when length parameter is invalid (zero or negative).
     */
    @Test
    public void testLineWrongLength(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOImpl.line('#', 0));
        assertEquals(ERR_PARAM, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_PARAM);
    }


    /**
     * Tests the repeatCharacter method to verify it repeats a character the specified number of times.
     */
    @Test
    public void testRepeatCharacter() {
        String expectedValue = "---------";
        String actualValue = IOImpl.repeatCharacter('-', 9);
        assertEquals(expectedValue, actualValue,"The repeatCharacter() method should generate a string with 9 dashes (-)");
    }

    /**
     * Tests that repeatCharacter method throws IllegalArgumentException when character parameter is null.
     */
    @Test
    public void testRepeatCharacterNullCharacterParameter(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOImpl.repeatCharacter(null, 3));
        assertEquals(ERR_PARAM, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_PARAM);
    }

    /**
     * Tests that repeatCharacter method throws IllegalArgumentException when times parameter is negative.
     */
    @Test
    public void testRepeatCharacterWrongTimesParameter(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> IOImpl.repeatCharacter('a', -4));
        assertEquals(ERR_PARAM, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_PARAM);
    }
}

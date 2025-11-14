package es.nom.juanfranciscoruiz.utiles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junitpioneer.jupiter.StdIn;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

public class IOTest {
    
    private static final String ERR_PARAM = "Null or incorrect parameters!";
    private static final String ERR_NULL = "Null parameters!";
    private static final String ERR_SOME_NULL = "One of the parameters is null!";
    private static final String ERR_LONG = "The line length is less than the message length";

    @Test
    @StdIo()
    public void testPrt(StdOut out) {
        IO.prt("one", "two", "three");
        String actualValue = out.capturedString();
        String expectedValue = "onetwothree";
        assertEquals(expectedValue, actualValue, "The values ​​should be the same");
    }

    @Test
    public void testPrtNull() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.prt((Object) null);
        });
        assertEquals(ERR_SOME_NULL, ex.getMessage(),"An IllegalArgumentException should be thrown with the message " + ERR_NULL);
    }
    
    @Test
    public void testPrtNullInFirstItemVarArgs() {

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.prt((Object) null);
        });
        assertEquals(ERR_SOME_NULL, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_SOME_NULL);
    }
    
    
    @Test
    public void testPrtSomeNullInVarArgs() {
        Object[] args = {new Object(),null,new Object(), new Object()};
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.prt(args);
        });
        assertEquals(ERR_SOME_NULL, ex.getMessage(), "An IllegalArgumentException should be thrown with the message" + ERR_SOME_NULL);
    }

    @Test
    @StdIo()
    public void testPrtln(StdOut out) {
        String sl = System.lineSeparator();
        IO.prtln(2, "one", "two", "three");
        String actualValue = out.capturedString();
        String expectedValue = "onetwothree".concat(sl).concat(sl);
        assertEquals(expectedValue, actualValue, "The values ​​should be the same");
    }
    
    @Test
    public void testPrtlnNull(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.prtln(1, (Object) null);
        });
        assertEquals(ERR_SOME_NULL, ex.getMessage(), "An IllegalArgumentException should be thrown with the message" + ERR_NULL);
    }
    
    public void testPrtlnNullInFirtsArgInVarArgs(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.prtln(1,(Object) null);
        });
        assertEquals(ERR_SOME_NULL, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_SOME_NULL);
    }
    
    public void testPrtlnSomeNullInVarArgs(){
        Object[] args = {new Object(),null,new Object(), new Object()};
        
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.prtln(3,args);
        });
        assertEquals(ERR_SOME_NULL, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_SOME_NULL);
    }
    

    @Test
    @StdIo({"one"})
    public void testRead(StdIn in, StdOut out) throws Exception {
        String actualValue = IO.read();
        String expectedValue = "one";
        assertEquals(expectedValue, actualValue, "The values ​​should be the same");
    }

    @Test
    public void testTitle() {
        String expectedValue = System.lineSeparator()
                .concat("********************")
                .concat(System.lineSeparator())
                .concat("      MESSAGE      ")
                .concat(System.lineSeparator())
                .concat("********************");
        String actualValue = IO.title("MESSAGE", '*', 20);
        assertEquals(expectedValue, actualValue, "The values ​​should be the same");
    }
    
    @Test
    public void testTitleMessageNull(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.title(null, '*', 20);
        });
        assertEquals(ERR_PARAM, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_PARAM);
    }

    @Test
    public void testTitleNullCharacter(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.title("MESSAGE", null, 20);
        });
        assertEquals(ERR_PARAM, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_PARAM);
    }
    
    @Test
    public void testTitleLengthLessThanLengthMessage(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.title("MESSAGE", '*', 3);
        });
        assertEquals(ERR_LONG, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_LONG);
    }
    
    @Test
    public void testLine() {
        String expectedValue = "**********";
        String actualValue = IO.line('*', 10);
        assertEquals(expectedValue, actualValue,"The line() method should generate a string with 10 asterisks (*)");
    }
    
    @Test
    public void testLineNullCharacter(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.line(null, 10);
        });
        assertEquals(ERR_PARAM, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_PARAM);
    }
    
    @Test
    public void testLineWrongLength(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.line('#', 0);
        });
        assertEquals(ERR_PARAM, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_PARAM);
    }
    

    @Test
    public void testRepeatCharacter() {
        String expectedValue = "---------";
        String actualValue = IO.repeatCharacter('-', 9);
        assertEquals(expectedValue, actualValue,"The repeatCharacter() method should generate a string with 9 dashes (-)");
    }
    
    @Test
    public void testRepeatCharacterNullCharacterParameter(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.repeatCharacter(null, 3);
        });
        assertEquals(ERR_PARAM, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_PARAM);
    }
    
    @Test
    public void testRepeatCharacterWrongTimesParameter(){
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            IO.repeatCharacter('a', -4);
        });
        assertEquals(ERR_PARAM, ex.getMessage(), "An IllegalArgumentException should be thrown with the message " + ERR_PARAM);
    }
}

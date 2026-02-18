package es.nom.juanfranciscoruiz.ansiterm.app.options;

import es.nom.juanfranciscoruiz.ansiterm.ANSITerm;

import static es.nom.juanfranciscoruiz.ansiterm.utiles.Util.pauseWithMessage;

/**
 * Demonstrates the use of multiple text styles combined.
 *
 * @author Juan F. Ruiz
 */
public class MultipleStylesText {
  
  /**
   * Constructs a new MultipleStylesText.
   */
  public MultipleStylesText() {}
  
  /**
   * Performs the multiple styles demonstration.
   * 
   * @param term The ANSITerm object to use for styling.
   * @throws Exception If an error occurs during execution.
   */
  public void perform(ANSITerm term) throws Exception {
    term.clearTerminal();
    term.moveCursorToBegin();
    term.printAt("------------ Multiple styles applicable to the text ------------", 1, 1);
    
    // Bold with other styles
    String mensaje = "Bold phrase with various styles";
    term.printAt("The following sentence will be in bold and dimmed", 2, 5);
    term.printAt(term.setStyles(true, true, false, false, false, false, false, mensaje), 3, 10);
    term.printAt("The following sentence will be in bold and italics", 4, 5);
    term.printAt(term.setStyles(true, false, true, false, false, false, false, mensaje), 5, 10);
    term.printAt("The following sentence will be in bold and underlined", 6, 5);
    term.printAt(term.setStyles(true, false, false, true, false, false, false, mensaje), 7, 10);
    term.printAt("The following sentence will be in bold and flashing", 8, 5);
    term.printAt(term.setStyles(true, false, false, false, true, false, false, mensaje), 9, 10);
    term.printAt("The following sentence will be in bold and with inverted colors.", 10, 5);
    term.printAt(term.setStyles(true, false, false, false, false, true, false, mensaje), 11, 10);
    term.printAt("The following sentence will be in bold and struck through", 12, 5);
    term.printAt(term.setStyles(true, false, false, false, false, false, true, mensaje), 13, 10);
    
    // Dim with other styles
    mensaje = "ATTENUATED phrase with various styles";
    term.printAt("The following sentence will be in bold and attenuated.", 14, 5);
    term.printAt(term.setStyles(true, true, false, false, false, false, false, mensaje), 15, 10);
    term.printAt("The following sentence will be attenuated and in italics", 16, 5);
    term.printAt(term.setStyles(false, true, true, false, false, false, false, mensaje), 17, 10);
    term.printAt("The following sentence will be highlighted and underlined", 18, 5);
    term.printAt(term.setStyles(false, true, false, true, false, false, false, mensaje), 19, 10);
    term.printAt("The following sentence will be dimmed and intermittent.", 20, 5);
    term.printAt(term.setStyles(false, true, false, false, true, false, false, mensaje), 21, 10);
    term.printAt("The following sentence will be dimmed and with inverted colors.", 22, 5);
    term.printAt(term.setStyles(false, true, false, false, false, true, false, mensaje), 23, 10);
    term.printAt("The following sentence will be grayed out and crossed out", 24, 5);
    term.printAt(term.setStyles(false, true, false, false, false, false, true, mensaje), 25, 10);
    
    pauseWithMessage(0, null);
    term.clearTerminal();
    term.printAt("------------ Multiple styles applicable to the text ------------", 1, 1);
    
    // Italic with other styles
    mensaje = "Phrase in CURSIVE with various styles";
    term.printAt("The following sentence will be in italics and attenuated.", 2, 5);
    term.printAt(term.setStyles(false, true, true, false, false, false, false, mensaje), 3, 10);
    term.printAt("The following sentence will be in italics and bold.", 4, 5);
    term.printAt(term.setStyles(true, false, true, false, false, false, false, mensaje), 5, 10);
    term.printAt("The following sentence will be in italics and underlined", 6, 5);
    term.printAt(term.setStyles(false, false, true, true, false, false, false, mensaje), 7, 10);
    term.printAt("The following sentence will be in italics and blinking.", 8, 5);
    term.printAt(term.setStyles(false, false, true, false, true, false, false, mensaje), 9, 10);
    term.printAt("The following sentence will be in italics and with inverted colors.", 10, 5);
    term.printAt(term.setStyles(false, false, true, false, false, true, false, mensaje), 11, 10);
    term.printAt("The following sentence will be in italics and crossed out", 12, 5);
    term.printAt(term.setStyles(false, false, true, false, false, false, true, mensaje), 13, 10);
    
    // Underline with other styles
    mensaje = "UNDERLINED phrase with various styles";
    term.printAt("The following sentence will be underlined and in bold.", 14, 5);
    term.printAt(term.setStyles(true, false, false, true, false, false, false, mensaje), 15, 10);
    term.printAt("The following sentence will be underlined and in italics", 16, 5);
    term.printAt(term.setStyles(false, false, true, true, false, false, false, mensaje), 17, 10);
    term.printAt("The following sentence will be underlined and highlighted.", 18, 5);
    term.printAt(term.setStyles(false, true, false, true, false, false, false, mensaje), 19, 10);
    term.printAt("The following sentence will be underlined and flashing.", 20, 5);
    term.printAt(term.setStyles(false, false, false, true, true, false, false, mensaje), 21, 10);
    term.printAt("The following sentence will be underlined and with inverted colors.", 22, 5);
    term.printAt(term.setStyles(false, false, false, true, false, true, false, mensaje), 23, 10);
    term.printAt("The following sentence will be underlined and crossed out.", 24, 5);
    term.printAt(term.setStyles(false, false, false, true, false, false, true, mensaje), 25, 10);
    
    pauseWithMessage(0, null);
    
    term.clearTerminal();
    term.printAt("------------ Multiple styles applicable to the text ------------", 1, 1);
    
    // Blinking with other styles
    mensaje = "INTERMITTENT phrase with various styles";
    term.printAt("The following sentence will be intermittent and dimmed.", 2, 5);
    term.printAt(term.setStyles(false, true, false, false, true, false, false, mensaje), 3, 10);
    term.printAt("The following sentence will be flashing and bold", 4, 5);
    term.printAt(term.setStyles(true, false, false, false, true, false, false, mensaje), 5, 10);
    term.printAt("The following sentence will be intermittent and underlined", 6, 5);
    term.printAt(term.setStyles(false, false, false, true, true, false, false, mensaje), 7, 10);
    term.printAt("The following phrase will be intermittent and intermittent", 8, 5);
    term.printAt(term.setStyles(false, false, false, false, true, false, false, mensaje), 9, 10);
    term.printAt("The following phrase will be flashing and with inverted colors.", 10, 5);
    term.printAt(term.setStyles(false, false, false, false, true, true, false, mensaje), 11, 10);
    term.printAt("The following sentence will be intermittent and crossed out", 12, 5);
    term.printAt(term.setStyles(false, false, false, false, true, false, true, mensaje), 13, 10);
    
    // Inverse with other styles
    mensaje = "INVERTED phrase with various styles";
    term.printAt("The following sentence will be reversed and in bold.", 14, 5);
    term.printAt(term.setStyles(true, false, false, false, false, true, false, mensaje), 15, 10);
    term.printAt("The following sentence will be inverted and in italics", 16, 5);
    term.printAt(term.setStyles(false, false, true, false, false, true, false, mensaje), 17, 10);
    term.printAt("The following sentence will be inverted and attenuated", 18, 5);
    term.printAt(term.setStyles(false, true, false, false, false, true, false, mensaje), 19, 10);
    term.printAt("The following sentence will be inverted and intermittent", 20, 5);
    term.printAt(term.setStyles(false, false, false, false, true, true, false, mensaje), 21, 10);
    term.printAt("The following sentence will be reversed", 22, 5);
    term.printAt(term.setStyles(false, false, false, false, false, true, false, mensaje), 23, 10);
    term.printAt("The following sentence will be reversed and crossed out", 24, 5);
    term.printAt(term.setStyles(false, false, false, false, false, true, true, mensaje), 25, 10);
    
    pauseWithMessage(0, null);
    
    term.clearTerminal();
    term.printAt("------------ Multiple styles applicable to the text ------------", 1, 1);
    
    // Strikethrough with other styles
    mensaje = "Phrase CROSSED out in various styles";
    term.printAt("The following sentence will be crossed out and dimmed.", 2, 5);
    term.printAt(term.setStyles(false, true, false, false, false, false, true, mensaje), 3, 10);
    term.printAt("The following sentence will be crossed out and in bold.", 4, 5);
    term.printAt(term.setStyles(true, false, false, false, false, false, true, mensaje), 5, 10);
    term.printAt("The following sentence will be crossed out and underlined.", 6, 5);
    term.printAt(term.setStyles(false, false, false, true, false, false, true, mensaje), 7, 10);
    term.printAt("The following sentence will be crossed out and intermittent.", 8, 5);
    term.printAt(term.setStyles(false, false, false, false, true, false, true, mensaje), 9, 10);
    term.printAt("The following sentence will be crossed out and with the colors inverted.", 10, 5);
    term.printAt(term.setStyles(false, false, false, false, false, true, true, mensaje), 11, 10);
    term.printAt("The following sentence will be crossed out", 12, 5);
    term.printAt(term.setStyles(false, false, false, false, false, false, true, mensaje), 13, 10);
    
    pauseWithMessage(0, "Press <ENTER> to return to menu");
  }
}

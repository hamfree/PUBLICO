package es.nom.juanfranciscoruiz.ansiterm.codes;

import static es.nom.juanfranciscoruiz.ansiterm.codes.CSI.ESC;

/*
Sequence 	Code 	Description 	Behavior
ESC [ <n> S 	SU 	Scroll Up 	Scroll text up by <n>. Also known as pan down, new lines fill in from the bottom of the screen
ESC [ <n> T 	SD 	Scroll Down 	Scroll down by <n>. Also known as pan up, new lines fill in from the top of the screen

The text is moved starting with the line the cursor is on. If the cursor is on the middle row of the viewport, then scroll up would move the bottom half of the viewport, and insert blank lines at the bottom. Scroll down would move the top half of the viewport’s rows, and insert new lines at the top.

Also important to note is scroll up and down are also affected by the scrolling margins. Scroll up and down won’t affect any lines outside the scrolling margins.

The default value for <n> is 1, and the value can be optionally omitted.
 */

/**
 * Class for handling viewport positioning codes in ANSI terminal
 * The text is moved starting with the line the cursor is on. If the cursor is
 * on the middle row of the viewport, then scroll up would move the bottom half
 * of the viewport, and insert blank lines at the bottom. Scroll down would move
 * the top half of the viewport’s rows and insert new lines at the top.
 * <p>
 * Also, important to note is scroll up and down are also affected by the
 * scrolling margins. Scroll up and down won’t affect any lines outside the
 * scrolling margins.
 * <p>
 * The default value for <n> is 1, and the value can be optionally omitted.
 */
public class ViewportPositioningCodes {
  
  /**
   * Private constructor. Class can't be instantiated by the user
   */
  private ViewportPositioningCodes() {}
  
  /**
   * Sequence......: ESC [ <n> S]
   * Code..........: SU
   * Description...: Scroll Up
   * Behavior......: Scroll text up by <n>. Also known as pan down, new lines
   * fill in from the bottom of the screen
   *
   * @param lines the number of lines to scroll
   */
  public void moveTextUp(int lines) {
    String sec_ansi = ESC + "[" + lines + "S";
    System.out.print(sec_ansi);
  }
  
  /**
   * Sequence......: ESC [ <n> T]
   * Code..........: SD
   * Description...: Scroll Down
   * Behavior......: Scroll down by <n>. Also known as pan up, new lines fill
   * in from the top of the screen
   *
   * @param lines the number of lines to scroll
   */
  public void moveTextDown(int lines) {
    String sec_ansi = ESC + "[" + lines + "T";
    System.out.print(sec_ansi);
  }
}

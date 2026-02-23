package es.nom.juanfranciscoruiz.ansiterm.model;

/**
 * Represents a rectangle with position, dimensions, and a character for drawing.
 */
public class Rectangle {
    /**
     * The x-coordinate of the rectangle's starting position.
     */
    private int x;
    /**
     * The y-coordinate of the rectangle's starting position.
     */
    private int y;
    /**
     * The width of the rectangle.
     */
    private int width;
    /**
     * The height of the rectangle.
     */
    private int height;
    /**
     * The character used to draw the rectangle.
     */
    private String character;

    /**
     * Constructs a new Rectangle with specified dimensions and position.
     *
     * @param x         The starting column (x-coordinate).
     * @param y         The starting line (y-coordinate).
     * @param width     The width of the rectangle.
     * @param height    The height of the rectangle.
     * @param character The character used to draw the rectangle.
     */
    public Rectangle(int x, int y, int width, int height, String character) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.character = character;
    }
    /**
     * Returns the x-coordinate of the rectangle's starting position.
     *
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x-coordinate of the rectangle's starting position.
     *
     * @param x The new x-coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Returns the y-coordinate of the rectangle's starting position.
     *
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y-coordinate of the rectangle's starting position.
     *
     * @param y The new y-coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Returns the width of the rectangle.
     *
     * @return The width.
     */
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Returns the height of the rectangle.
     *
     * @return The height.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Sets the height of the rectangle.
     *
     * @param height The new height.
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Returns the character used to draw the rectangle.
     *
     * @return The character.
     */
    public String getCharacter() {
        return character;
    }

    /**
     * Sets the character used to draw the rectangle.
     *
     * @param character The new character.
     */
    public void setCharacter(String character) {
        this.character = character;
    }

    @Override
    public String toString() {
        return "Rectangle{" + "x=" + x + ", y=" + y + ", width=" + width + ", height=" + height + ", character='" + character + '\'' + '}';
    }

}

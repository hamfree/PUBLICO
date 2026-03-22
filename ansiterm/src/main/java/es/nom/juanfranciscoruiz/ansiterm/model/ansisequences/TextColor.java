package es.nom.juanfranciscoruiz.ansiterm.model.ansisequences;

import es.nom.juanfranciscoruiz.ansiterm.model.BGColor;
import es.nom.juanfranciscoruiz.ansiterm.model.Color;

/**
 * Represents the text color configuration, including a foreground color and
 * a background color, for use in terminal output or other color-coded text systems.
 * <p>
 * The foreground color is represented using the {@link Color} enumeration,
 * and the background color is represented using the {@link BGColor} enumeration.
 */
public class TextColor {
    /**
     * The foreground color of the text.
     * <p>
     * This variable represents the color used for the text in terminal output
     * or other color-coded text systems. It is defined using the {@link Color} enumeration,
     * which provides a series of predefined ANSI color options.
     */
    private Color color;
    /**
     * The background color of the text.
     * <p>
     * This variable represents the ANSI color used as the background for text in terminal
     * output or other color-coded text systems. It is defined using the {@link BGColor}
     * enumeration, which provides a set of predefined options for terminal background colors,
     * including standard and bright variations.
     */
    private BGColor bgColor;

    /**
     * Default constructor for the TextColor class.
     * <p>
     * Initializes the text color configuration with default values:
     * the foreground color is set to {@link Color#BLACK} and the background color
     * is set to {@link BGColor#WHITE}.
     */
    public TextColor() {
        this.color = Color.BLACK;
        this.bgColor = BGColor.WHITE;
    }

    /**
     * Constructs a new TextColor instance with the specified foreground and background colors.
     *
     * @param color   The foreground color of the text, represented by the {@link Color} enumeration.
     * @param bgColor The background color of the text, represented by the {@link BGColor} enumeration.
     */
    public TextColor(Color color, BGColor bgColor) {
        this.color = color;
        this.bgColor = bgColor;
    }

    /**
     * Retrieves the current foreground color of the text.
     *
     * @return The foreground color of the text as a {@link Color} enumeration value.
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets the foreground color for the text.
     *
     * @param color The foreground color to set, represented by the {@link Color} enumeration.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Retrieves the current background color of the text.
     *
     * @return The background color of the text as a {@link BGColor} enumeration value.
     */
    public BGColor getBgColor() {
        return bgColor;
    }

    /**
     * Sets the background color for the text.
     *
     * @param bgColor The background color to be set, represented by the {@link BGColor} enumeration.
     */
    public void setBgColor(BGColor bgColor) {
        this.bgColor = bgColor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        TextColor textColor = (TextColor) o;
        return color == textColor.color && bgColor == textColor.bgColor;
    }

    @Override
    public int hashCode() {
        int result = color.hashCode();
        result = 31 * result + bgColor.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "TextColor{" +
                "color=" + color +
                ", bgColor=" + bgColor +
                '}';
    }
}

package es.nom.juanfranciscoruiz.utiles.model;

/**
 * Represents the dimensions of a two-dimensional structure characterized
 * by the number of rows and columns.
 * <p>
 * This record is immutable and ensures that both the rows and columns
 * cannot be negative by validating the input during construction.
 * <p>
 * Constructors:
 * <ul>
 * <li>A no-arguments constructor initializes the dimensions to 0 rows and 0 columns.</li>
 * <li>A single-argument constructor initializes with the specified rows and 0 columns.</li>
 * <li>A primary constructor validates the provided row and column values.</li>
 * </ul>
 * <p>
 * Records:
 * <ul>
 * <li>`rows`: The number of rows in the structure. Cannot be negative.</li>
 * <li>`columns`: The number of columns in the structure. Cannot be negative.</li>
 *</ul>
 *
 * @param rows The number of rows in the structure.
 * @param columns The number of columns in the structure.
 * Throws:
 * - IllegalArgumentException if rows or columns are negative.
 */
public record Dimensions(int rows, int columns) {
    /**
     * Default no-argument constructor for the Dimensions record.
     * <p>
     * Initializes a new instance of Dimensions with default values of 0 rows and 0 columns.
     * This represents a structure with no rows and no columns.
     */
    public Dimensions() {
        this(0, 0);
    }

    /**
     * Constructs a new instance of Dimensions with the specified number of rows
     * and a default of 0 columns.
     *
     * @param rows The number of rows in the structure. Must be non-negative.
     * @throws IllegalArgumentException If the number of rows is negative.
     */
    public Dimensions(int rows) {
        this(rows, 0);
    }

    /**
     * Constructs a new instance of the Dimensions record, representing the dimensions
     * of a two-dimensional structure defined by its number of rows and columns.
     *
     * @param rows The number of rows in the structure. Must be non-negative.
     * @param columns The number of columns in the structure. Must be non-negative.
     * @throws IllegalArgumentException If rows or columns are negative.
     */
    public Dimensions {
        if (rows < 0) {
            throw new IllegalArgumentException("Rows cannot be negative");
        }
        if (columns < 0) {
            throw new IllegalArgumentException("Columns cannot be negative");
        }
    }
}
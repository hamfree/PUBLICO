package es.nom.juanfranciscoruiz.utiles.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Dimensions class.
 */
class DimensionsTest {

    /**
     * Tests the default constructor of the Dimensions class.
     */
    @Test
    void testDefaultConstructor() {
        Dimensions d = new Dimensions();
        assertEquals(0, d.rows());
        assertEquals(0, d.columns());
    }

    /**
     * Tests the single-parameter constructor of the Dimensions class.
     */
    @Test
    void testSingleParamConstructor() {
        Dimensions d = new Dimensions(10);
        assertEquals(10, d.rows());
        assertEquals(0, d.columns());
    }

    /**
     * Tests the full constructor of the Dimensions class.
     */
    @Test
    void testFullConstructor() {
        Dimensions d = new Dimensions(25, 80);
        assertEquals(25, d.rows());
        assertEquals(80, d.columns());
    }

    /**
     * Verifies that an attempt to create a {@code Dimensions} instance with a negative
     * number of rows throws an {@code IllegalArgumentException}.
     * <p>
     * This test validates the following scenarios:
     * 1. Using the primary constructor with a negative value for rows and a positive value for columns.
     * 2. Using the single-parameter constructor with a negative value for rows.
     * <p>
     * Ensures that the {@code Dimensions} class enforces input validation and does
     * not allow negative row values.
     * <p>
     * Throws:
     * - IllegalArgumentException if the rows parameter is negative.
     */
    @Test
    void testNegativeRowsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Dimensions(-1, 10));
        assertThrows(IllegalArgumentException.class, () -> new Dimensions(-5));
    }

    /**
     * Verifies that an attempt to create a {@code Dimensions} instance with a negative
     * number of columns throws an {@code IllegalArgumentException}.
     * <p>
     * This test validates the behavior of the {@code Dimensions} class when initialized
     * with invalid column values while ensuring that rows are positive.
     * <p>
     * Ensures that the {@code Dimensions} class correctly enforces input validation and
     * does not allow negative column values.
     * <p>
     * Throws:
     * - IllegalArgumentException if the columns parameter is negative.
     */
    @Test
    void testNegativeColumnsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new Dimensions(10, -1));
    }

    /**
     * Tests the equals() and hashCode() methods of the Dimensions class.
     */
    @Test
    void testEqualsAndHashCode() {
        Dimensions d1 = new Dimensions(20, 40);
        Dimensions d2 = new Dimensions(20, 40);
        Dimensions d3 = new Dimensions(10, 10);

        assertEquals(d1, d2);
        assertNotEquals(d1, d3);
        assertEquals(d1.hashCode(), d2.hashCode());
    }

    /**
     * Tests the toString() method of the Dimensions class.
     */
    @Test
    void testToString() {
        Dimensions d = new Dimensions(5, 15);
        String toString = d.toString();
        assertTrue(toString.contains("rows=5"));
        assertTrue(toString.contains("columns=15"));
    }

}
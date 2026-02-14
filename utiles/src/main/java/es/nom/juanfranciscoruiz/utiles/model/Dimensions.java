package es.nom.juanfranciscoruiz.utiles.model;

/**
 * A record representing dimensions with a specified number of rows and columns.
 * <p>
 * This record can be used to encapsulate the concept of a two-dimensional structure,
 * where each dimension is defined by the number of rows and columns.
 *
 * @param rows    The number of rows in the dimension.
 * @param columns The number of columns in the dimension.
 */
public record Dimensions(int rows, int columns) {

}

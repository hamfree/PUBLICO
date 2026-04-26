package es.nom.juanfranciscoruiz.utiles.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents a sample object with various data types and nested objects.
 * <p>
 * This class includes multiple fields of different data types, providing
 * a structure for storing and manipulating various types of data. It also
 * supports nested objects through a `SampleObject` array.
 */
public class SampleObject {
    /**
     * A constant representing the default size.
     *
     * <ul>
     * <li>Declared as {@code public static final}, indicating that it is a shared,
     *   unmodifiable value accessible at the class level.</li>
     * <li>Typically used to define fixed-capacity settings, limits, or default values
     *   within the context of the containing class.</li>
     * </ul>
     * <p>
     * Value:
     * - The constant is initialized to {@code 5}.
     */
    public static final int SIZE = 5;

    /**
     * Represents a string property of the SampleObject class that can store textual data.
     * This field is private, and its value can be accessed or modified through getter
     * and setter methods provided in the class.
     */
    private String aString;
    /**
     * Represents a floating-point number associated with the SampleObject class.
     * This variable is used to store a `Double` value and can be accessed or modified
     * through the getter and setter methods provided in the class.
     * <p>
     * It may represent any double-precision numeric value relevant to the context of
     * the SampleObject instance.
     */
    private Double aDouble;
    /**
     * Represents a floating-point number used as a class field.
     * <p>
     * This variable can store fractional or decimal values in a single-precision
     * 32-bit IEEE 754 format. It is typically used for scenarios that require
     * approximate or non-integer numeric representation.
     */
    private float aFloat;
    /**
     * Represents an integer value associated with the containing object.
     * <p>
     * This field typically stores an integral numerical value that could be
     * used for various purposes, such as configuration, computation, or identification,
     * depending on the context of the containing class.
     */
    private int anInt;
    /**
     * Indicates whether a specific boolean condition or state is true or false.
     * This variable is used to represent a binary state or flag that can be toggled.
     */
    private boolean aBoolean;
    /**
     * Represents a single character field in the class.
     * This field is encapsulated and can be accessed or modified
     * through the corresponding getter and setter methods.
     */
    private char aChar;
    /**
     * An array of SampleObject instances representing a collection of sample objects.
     * <p>
     * This field can be used to manage, store, or process multiple SampleObject elements
     * within the containing class. Each element in the array is expected to be of type
     * SampleObject, which encapsulates various fields and functionalities related to the sample domain.
     * <p>
     * The field is private, ensuring controlled access and modification through
     * appropriate getter and setter methods defined in the class.
     */
    private Integer[] integers;

    /**
     * Default constructor for the SampleObject class.
     * <p>
     * Initializes a new instance of the SampleObject class
     * without setting any field values. This constructor can
     * be used to create an object with default settings.
     */
    public SampleObject() {
        this.integers = new Integer[SIZE];
    }

    /**
     * Constructs a new instance of the SampleObject class with the specified parameters.
     *
     * @param aString  A string value to initialize the aString field.
     * @param aDouble  A Double value to initialize the aDouble field.
     * @param aFloat   A float value to initialize the aFloat field.
     * @param anInt    An integer value to initialize the anInt field.
     * @param aBoolean A boolean value to initialize the aBoolean field.
     * @param aChar    A char value to initialize the aChar field.
     * @param integers An array of SampleObject instances to initialize the sampleObjects field.
     */
    public SampleObject(String aString, Double aDouble, float aFloat, int anInt, boolean aBoolean, char aChar, Integer[] integers) {
        this.aString = aString;
        this.aDouble = aDouble;
        this.aFloat = aFloat;
        this.anInt = anInt;
        this.aBoolean = aBoolean;
        this.aChar = aChar;
        this.integers = integers;
    }

    /**
     * Retrieves the value of the `aString` field.
     *
     * @return The current value of the `aString` field.
     */
    public String getaString() {
        return aString;
    }

    /**
     * Sets the value of the `aString` field.
     *
     * @param aString The new value to assign to the `aString` field.
     */
    public void setaString(String aString) {
        this.aString = aString;
    }

    /**
     * Retrieves the value of the `aDouble` field.
     *
     * @return The current value of the `aDouble` field, or {@code null} if it has not been initialized.
     */
    public Double getaDouble() {
        return aDouble;
    }

    /**
     * Sets the value of the `aDouble` field.
     *
     * @param aDouble The new value to assign to the `aDouble` field. It may be null,
     *                indicating that the field should not hold any Double value.
     */
    public void setaDouble(Double aDouble) {
        this.aDouble = aDouble;
    }

    /**
     * Retrieves the value of the `aFloat` field.
     *
     * @return The current value of the `aFloat` field.
     */
    public float getaFloat() {
        return aFloat;
    }

    /**
     * Sets the value of the `aFloat` field.
     *
     * @param aFloat The new value to assign to the `aFloat` field.
     */
    public void setaFloat(float aFloat) {
        this.aFloat = aFloat;
    }

    /**
     * Retrieves the value of the `anInt` field.
     *
     * @return The current value of the `anInt` field.
     */
    public int getAnInt() {
        return anInt;
    }

    /**
     * Sets the value of the `anInt` field.
     *
     * @param anInt The new integer value to assign to the `anInt` field.
     */
    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    /**
     * Determines whether the `aBoolean` field is set to true or false.
     *
     * @return The current value of the `aBoolean` field.
     */
    public boolean isaBoolean() {
        return aBoolean;
    }

    /**
     * Sets the value of the `aBoolean` field.
     *
     * @param aBoolean The new boolean value to assign to the `aBoolean` field.
     */
    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    /**
     * Retrieves the value of the `aChar` field.
     *
     * @return The current value of the `aChar` field.
     */
    public char getaChar() {
        return aChar;
    }

    /**
     * Sets the value of the `aChar` field.
     *
     * @param aChar The new character value to assign to the `aChar` field.
     */
    public void setaChar(char aChar) {
        this.aChar = aChar;
    }

    /**
     * Retrieves the array of `SampleObject` instances.
     *
     * @return An array of `SampleObject` instances, or {@code null} if the array has not been initialized.
     */
    public Integer[] getIntegers() {
        return integers;
    }

    /**
     * Sets the array of {@code SampleObject} instances for this object.
     *
     * @param integers An array of {@code SampleObject} instances to be assigned.
     *                 This array may be {@code null}, indicating that the field should not hold any values.
     */
    public void setIntegers(Integer[] integers) {
        this.integers = integers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        SampleObject that = (SampleObject) o;
        return Float.compare(aFloat, that.aFloat) == 0 && anInt == that.anInt && aBoolean == that.aBoolean && aChar == that.aChar && Objects.equals(aString, that.aString) && Objects.equals(aDouble, that.aDouble) && Arrays.equals(integers, that.integers);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(aString);
        result = 31 * result + Objects.hashCode(aDouble);
        result = 31 * result + Float.hashCode(aFloat);
        result = 31 * result + anInt;
        result = 31 * result + Boolean.hashCode(aBoolean);
        result = 31 * result + aChar;
        result = 31 * result + Arrays.hashCode(integers);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SampleObject{");
        sb.append("aString='").append(aString).append('\'');
        sb.append(", aDouble=").append(aDouble);
        sb.append(", aFloat=").append(aFloat);
        sb.append(", anInt=").append(anInt);
        sb.append(", aBoolean=").append(aBoolean);
        sb.append(", aChar=").append(aChar);
        sb.append(", integers=").append(integers == null ? "null" : Arrays.asList(integers).toString());
        sb.append('}');
        return sb.toString();
    }
}

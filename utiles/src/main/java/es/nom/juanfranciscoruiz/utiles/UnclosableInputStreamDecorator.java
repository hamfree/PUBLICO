package es.nom.juanfranciscoruiz.utiles;

import java.io.IOException;
import java.io.InputStream;

/**
 * It is the same as an InputStream, which it extends, and using the Decorator pattern 
 * we make its close() method do nothing.
 * @author Juan F. Ruiz
 */
public class UnclosableInputStreamDecorator extends InputStream {

    private final InputStream inputStream;

    /**
     * Generates an UnclosableInputStreamDecorator object from the InputStream object
     * passed as a parameter.
     * 
     * @param inputStream An InputStream object
     */
    public UnclosableInputStreamDecorator(InputStream inputStream) {
        this.inputStream = inputStream;
    }
    
    /**
     * Reads the next byte of data from the underlying input stream.
     * The value byte is returned as an int in the range 0 to 255.
     * If no byte is available because the end of the stream has been reached, the value -1 is returned.
     *
     * @return the next byte of data, or -1 if the end of the stream is reached
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read() throws IOException {
        return inputStream.read();
    }
    
    /**
     * Reads a sequence of bytes from the underlying input stream into the specified array.
     * This method blocks until input data is available, the end of the stream is detected,
     * or an exception is thrown.
     *
     * @param b the byte array into which the data is read
     * @return the total number of bytes read into the array, or -1 if the end of the stream is reached
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read(byte[] b) throws IOException {
        return inputStream.read(b);
    }
    
    /**
     * Reads a portion of bytes from the underlying input stream into the specified array.
     * This method starts storing bytes into the array at the specified offset
     * and attempts to read up to the specified number of bytes.
     * This method blocks until input data is available, the end of the stream is detected,
     * or an exception is thrown.
     *
     * @param b   the byte array into which the data is read
     * @param off the offset in the array at which the data is stored
     * @param len the maximum number of bytes to read
     * @return the total number of bytes read into the array, or -1 if the end of the stream is reached
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return inputStream.read(b, off, len);
    }
    
    /**
     * Skips over and discards n bytes of data from this input stream.
     * The skip method may, for a variety of reasons, end up skipping over some smaller number of bytes,
     * possibly 0. The actual number of bytes skipped is returned.
     *
     * @param n   the number of bytes to be skipped.
     * @return the number of bytes actually skipped
     * @throws IOException if an I/O error occurs
     */
    @Override
    public long skip(long n) throws IOException {
        return inputStream.skip(n);
    }
    
    /**
     * Returns an estimate of the number of bytes that can be read (or skipped over)
     * from this input stream without blocking by the next invocation of a method
     * for this input stream. The actual number of bytes available may be smaller
     * than the estimate. This method should be used as a rough indicator of
     * whether a later read is likely to block or not.
     *
     * @return an estimate of the number of bytes that can be read from the input stream
     *         without blocking
     * @throws IOException if an I/O error occurs
     */
    @Override
    public int available() throws IOException {
        return inputStream.available();
    }
    
    /**
     * Sets a mark position in this input stream. The parameter {@code readlimit}
     * indicates how many bytes can be read before the mark position becomes invalid.
     *
     * @param readlimit   the maximum limit of bytes that can be read before
     *                      the mark position becomes invalid.
     */
    @Override
    public synchronized void mark(int readlimit) {
        inputStream.mark(readlimit);
    }
    
    /**
     * Repositions this stream to the position at the time the mark method was last called on this input stream.
     * @throws IOException if an I/O error occurs
     */
    @Override
    public synchronized void reset() throws IOException {
        inputStream.reset();
    }
    
    /**
     * Checks if this input stream supports the mark and reset methods.
     * @return true if this input stream supports the mark and reset methods, false otherwise
     */
    @Override
    public boolean markSupported() {
        return inputStream.markSupported();
    }

    /**
     * DON'T closes this input stream for using this inputstream in console multiple times.
     */
    @Override
    public void close() {
        // Do nothing
    }
}

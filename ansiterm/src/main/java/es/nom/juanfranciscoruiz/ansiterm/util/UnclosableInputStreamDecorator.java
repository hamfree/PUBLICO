package es.nom.juanfranciscoruiz.ansiterm.util;

import java.io.IOException;
import java.io.InputStream;

/**
 * Es igual que un InputStream, del que extiende y usando el patrón Decorator 
 * hacemos que su método close() no haga nada.
 * 
 * @author juanf
 */
public class UnclosableInputStreamDecorator extends InputStream {

    private final InputStream inputStream;

    /**
     * Genera un objeto UnclosableInputStreamDecorator a partir del objeto InputStream
     * que se le pasa como parámetro.
     * 
     * @param inputStream Un objeto InputStream
     */
    public UnclosableInputStreamDecorator(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    @Override
    public int read() throws IOException {
        return inputStream.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        return inputStream.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        return inputStream.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
        return inputStream.skip(n);
    }

    @Override
    public int available() throws IOException {
        return inputStream.available();
    }

    @Override
    public synchronized void mark(int readlimit) {
        inputStream.mark(readlimit);
    }

    @Override
    public synchronized void reset() throws IOException {
        inputStream.reset();
    }

    @Override
    public boolean markSupported() {
        return inputStream.markSupported();
    }

    @Override
    public void close() throws IOException {
        // Do nothing
    }
}

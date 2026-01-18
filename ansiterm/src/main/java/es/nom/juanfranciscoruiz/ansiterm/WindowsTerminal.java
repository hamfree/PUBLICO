package es.nom.juanfranciscoruiz.ansiterm;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.IntByReference;

/**
 * Provides access to the raw and cooked modes of the Windows console as well as
 * getting the console size using ANSI escape sequences and setting the
 * raw mode so that the ANSI code does not appear on the screen.
 * @author Juan F. Ruiz
 */
public class WindowsTerminal implements ITerminal {
    private final int STD_OUTPUT_HANDLE = Kernel32.STD_OUTPUT_HANDLE;
    private final int STD_INPUT_HANDLE = Kernel32.STD_INPUT_HANDLE;
    private final int ENABLE_VIRTUAL_TERMINAL_PROCESSING = Kernel32.ENABLE_VIRTUAL_TERMINAL_PROCESSING;
    private final int ENABLE_VIRTUAL_TERMINAL_INPUT = Kernel32.ENABLE_VIRTUAL_TERMINAL_INPUT;
    private final int ENABLE_ECHO_INPUT = Kernel32.ENABLE_ECHO_INPUT;
    private final int ENABLE_LINE_INPUT = Kernel32.ENABLE_LINE_INPUT;
    private final int ENABLE_MOUSE_INPUT = Kernel32.ENABLE_MOUSE_INPUT;
    private final int ENABLE_WINDOW_INPUT = Kernel32.ENABLE_WINDOW_INPUT;
    private final int ENABLE_PROCESSED_INPUT = Kernel32.ENABLE_PROCESSED_INPUT;
    private final int ENABLE_PROCESSED_OUTPUT = Kernel32.ENABLE_PROCESSED_OUTPUT;
    private final int ENABLE_WRAP_AT_EOL_OUTPUT = Kernel32.ENABLE_WRAP_AT_EOL_OUTPUT;
    

    private IntByReference inMode;
    private IntByReference outMode;

    /**
     * Instantiates a WindowsTerminal object
     */
    public WindowsTerminal() {
    }

    /**
     * Enables the 'raw' mode of the Windows console
     */
    @Override
    public void enableRawMode() {
        WinNT.HANDLE inHandle = Kernel32.INSTANCE.GetStdHandle(STD_INPUT_HANDLE);

        inMode = new IntByReference();
        Kernel32.INSTANCE.GetConsoleMode(inHandle, inMode);

        int intInMode = this.inMode.getValue() & ~(ENABLE_ECHO_INPUT
                | ENABLE_LINE_INPUT
                | ENABLE_MOUSE_INPUT
                | ENABLE_WINDOW_INPUT
                | ENABLE_PROCESSED_INPUT);

        intInMode |= ENABLE_VIRTUAL_TERMINAL_INPUT;

        Kernel32.INSTANCE.SetConsoleMode(inHandle, intInMode);

        WinNT.HANDLE outHandle = Kernel32.INSTANCE.GetStdHandle(STD_OUTPUT_HANDLE);
        outMode = new IntByReference();
        Kernel32.INSTANCE.GetConsoleMode(outHandle, outMode);

        int intOutMode = this.outMode.getValue();
        intOutMode |= ENABLE_VIRTUAL_TERMINAL_PROCESSING;
        intOutMode |= ENABLE_PROCESSED_OUTPUT;
        Kernel32.INSTANCE.SetConsoleMode(outHandle, intOutMode);

    }

    /**
     * Disables the 'raw' mode and enables the normal mode of the Windows console
     */
    @Override
    public void disableRawMode() {
        WinNT.HANDLE inHandle = Kernel32.INSTANCE.GetStdHandle(STD_INPUT_HANDLE);
        Kernel32.INSTANCE.SetConsoleMode(inHandle, inMode.getValue());

        WinNT.HANDLE outHandle = Kernel32.INSTANCE.GetStdHandle(STD_OUTPUT_HANDLE);
        Kernel32.INSTANCE.SetConsoleMode(outHandle, outMode.getValue());
    }

    /**
     * Gets the console size
     * @return A TerminalSize object with the current lines and columns of the 
     * console
     * 
     * @see es.nom.juanfranciscoruiz.ansiterm.TerminalSize
     */
    @Override
    public TerminalSize getTerminalSize() {
        final Kernel32.CONSOLE_SCREEN_BUFFER_INFO info = new Kernel32.CONSOLE_SCREEN_BUFFER_INFO();
        final Kernel32 instance = Kernel32.INSTANCE;
        final WinNT.HANDLE handle = Kernel32.INSTANCE.GetStdHandle(STD_OUTPUT_HANDLE);
        instance.GetConsoleScreenBufferInfo(handle, info);
        return new TerminalSize(info.dwSize.X, info.dwSize.Y);
    }

}

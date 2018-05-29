package ru.spbau.cli;

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Writer wrapper
 */
class UserOutputWriter {
    private static final char INVITATION_SYMBOL = '>';
    private final PrintWriter writer;

    UserOutputWriter(OutputStream outputStream) {
        writer = new PrintWriter(outputStream);
    }

    void writeInvitation() {
        writer.print(INVITATION_SYMBOL);
        writer.flush();
    }

    void write(String s) {
        writer.println(s);
        writer.flush();
    }
}

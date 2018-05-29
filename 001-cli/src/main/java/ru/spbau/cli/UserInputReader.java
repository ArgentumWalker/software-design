package ru.spbau.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static ru.spbau.cli.Parser.QUOTE_MARKS;

/**
 * Reader wrapper
 */
class UserInputReader {
    private final BufferedReader reader;

    UserInputReader(InputStream inputStream) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        reader = new BufferedReader(inputStreamReader);
    }

    String read() throws IOException {
        boolean escaping = false;
        StringBuilder input = new StringBuilder();
        do {
            String currentLine = reader.readLine();
            input.append(currentLine);
            input.append('\n');
            escaping = recalcEscaping(currentLine, escaping);
        } while (escaping);
        return input.toString();
    }

    private boolean recalcEscaping(String line, boolean escaping) {
        for (int i = 0; i < line.length(); i++) {
            char currentChar = line.charAt(i);
            if (QUOTE_MARKS.contains(currentChar) && (i == 0 || line.charAt(i - 1) != '\'')) {
                escaping = !escaping;
            }
        }
        return escaping;
    }

}

package ru.spbau.cli.commandrunner;

import ru.spbau.cli.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * cat command runner
 */
public class CatCommandRunner implements CommandRunner {
    public static final String STRING_VALUE = "cat";

    /**
     * Writes the content of a file
     * @param environment Environment state
     * @param argument File path
     */
    @Override
    public void run(Environment environment, String argument) {
        if (argument.isEmpty()) {
            return;
        }
        try {
            List<String> lines = Files.readAllLines(environment.getFilePath(argument));
            environment.writeResult("");
            StringBuilder result = new StringBuilder();
            for (String line: lines) {
                result.append(line);
                result.append('\n');
            }
            environment.writeResult(result.toString());
        } catch (IOException e) {
            environment.writeResult("IO exception at " + argument);
        }
    }

    /**
     * An execution shouldn't stop
     * @return False
     */
    @Override
    public boolean shouldExit() {
        return false;
    }
}

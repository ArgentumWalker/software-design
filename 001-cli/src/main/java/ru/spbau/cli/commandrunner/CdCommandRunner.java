package ru.spbau.cli.commandrunner;

import ru.spbau.cli.Environment;

import java.io.IOException;

/**
 * Runs change directory command
 * @author ArgentumWalker
 */
public class CdCommandRunner implements CommandRunner {
    public static final String STRING_VALUE = "cd";

    @Override
    public void run(Environment environment, String argument) {
        try {
            environment.changeDirectory(argument);
        } catch (IOException e) {
            //Not changed
        }
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}

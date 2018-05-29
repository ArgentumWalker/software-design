package ru.spbau.cli.commandrunner;

import ru.spbau.cli.Environment;

public class PwdCommandRunner implements CommandRunner {
    public static final String STRING_VALUE = "pwd";

    /**
     * Prints the name of the current directory
     * @param environment Environment state
     * @param argument Is ignored
     */
    @Override
    public void run(Environment environment, String argument) {
        String currentDirectory = environment.getCurrentDirectory().toString();
        environment.writeResult(currentDirectory);
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

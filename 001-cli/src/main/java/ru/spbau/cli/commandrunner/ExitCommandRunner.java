package ru.spbau.cli.commandrunner;

import ru.spbau.cli.Environment;

public class ExitCommandRunner implements CommandRunner {
    public static final String STRING_VALUE = "exit";

    /**
     * Doesn't affect the environment
     * @param environment is ignored
     * @param argument is ignored
     */
    @Override
    public void run(Environment environment, String argument) {}

    /**
     * An Execution should stop
     * @return False
     */
    @Override
    public boolean shouldExit() {
        return true;
    }
}
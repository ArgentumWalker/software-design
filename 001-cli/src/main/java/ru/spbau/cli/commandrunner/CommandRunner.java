package ru.spbau.cli.commandrunner;

import ru.spbau.cli.Environment;

/**
 * Command runner interface
 */
public interface CommandRunner {

    /**
     * Runs the command modifying the environment
     * @param environment Environment state
     * @param argument Command argument
     */
    void run(Environment environment, String argument);

    /**
     * Boolean flag show if an execution should stop after running the command
     * @return True if an execution should stop after running the command; False else
     */
    boolean shouldExit();
}

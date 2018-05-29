package ru.spbau.cli.commandrunner;

import ru.spbau.cli.Environment;

/**
 * echo command runner
 */
public class EchoCommandRunner implements CommandRunner {
    public static final String STRING_VALUE = "echo";

    /**
     * Writes an argument as a result
     * @param environment Environment state
     * @param argument Command argument
     */
    @Override
    public void run(Environment environment, String argument) {
        environment.writeResult(argument);
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

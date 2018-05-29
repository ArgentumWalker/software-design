package ru.spbau.cli.commandrunner;

import ru.spbau.cli.Environment;

import java.io.IOException;

/**
 * Custom command runner
 */
public class CustomCommandRunner implements CommandRunner {
    private final String stringValue;

    public CustomCommandRunner(String stringValue) {
        this.stringValue = stringValue;
    }

    /**
     * Just redirecting to "exec"
     * @param environment Environment state
     * @param argument Command argument
     */
    @Override
    public void run(Environment environment, String argument) {
        Runtime r = Runtime.getRuntime();
        try {
            if (argument.isEmpty()) {
                r.exec(stringValue + " " + environment.getResult());
            } else {
                r.exec(stringValue + " " + argument);
            }
        } catch (IOException e) {
            environment.writeResult(e.getMessage());
        }
    }

    /**
     * An Execution shouldn't stop
     * @return False
     */
    @Override
    public boolean shouldExit() {
        return false;
    }
}

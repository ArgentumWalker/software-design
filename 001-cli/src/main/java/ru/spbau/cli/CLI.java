package ru.spbau.cli;

import java.io.*;

/**
 * Command line interpreter
 */
public class CLI {
    private final Environment environment;
    private final UserInputReader userInputReader;
    private final UserOutputWriter userOutputWriter;

    public CLI(InputStream inputStream, OutputStream outputStream) {
        environment = new Environment();
        this.userInputReader = new UserInputReader(inputStream);
        this.userOutputWriter = new UserOutputWriter(outputStream);
    }

    /**
     * Runs the command by reading it from user, executing and printing the output
     * @throws IOException IO problem
     */
    public void run() throws IOException {
        boolean shouldExit = false;
        while (!shouldExit) {
            userOutputWriter.writeInvitation();
            String input = userInputReader.read();
            Command command = new Command(input);
            shouldExit = command.execute(environment);
            String result = environment.getResult();
            userOutputWriter.write(result);
        }
    }
}

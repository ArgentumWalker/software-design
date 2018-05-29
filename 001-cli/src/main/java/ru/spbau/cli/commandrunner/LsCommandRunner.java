package ru.spbau.cli.commandrunner;

import javafx.util.Pair;
import ru.spbau.cli.Environment;

import java.io.IOException;
import java.util.Set;

/**
 * List files and directories, output to stdout
 * @author ArgentumWalker
 */
public class LsCommandRunner implements CommandRunner {
    public static final String STRING_VALUE = "ls";

    @Override
    public void run(Environment environment, String argument) {
        try {
            Pair<Set<String>, Set<String>> directoriesAndFiles = environment.getDirectoriesAndFiles(argument);
            StringBuilder resultBuider = new StringBuilder();
            resultBuider.append("Directories:\n");
            for (String dir : directoriesAndFiles.getKey()) {
                resultBuider.append("    ").append(dir).append("\n");
            }
            resultBuider.append("Files:\n");
            for (String file : directoriesAndFiles.getValue()) {
                resultBuider.append("    ").append(file).append("\n");
            }
            environment.writeResult(resultBuider.toString());
        } catch (IOException e) {
            environment.writeResult("Unable to list files");
        }
    }

    @Override
    public boolean shouldExit() {
        return false;
    }
}

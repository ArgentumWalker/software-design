package ru.spbau.cli;

import javafx.util.Pair;
import ru.spbau.cli.commandrunner.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Environment class that stores variables, command runners and the previous command result
 */
public class Environment {
    private final Map<String, String> variables;
    private final Map<String, CommandRunner> commandRunners;
    private String previousResult;
    private Path currentDirectory = Paths.get("").normalize().toAbsolutePath();


    public Environment() {
        variables = new HashMap<>();
        commandRunners = new HashMap<>();
        writeResult("");
        initializeCommandMapWithBuiltinCommands();
    }

    private void initializeCommandMapWithBuiltinCommands() {
        commandRunners.put(CdCommandRunner.STRING_VALUE, new CdCommandRunner());
        commandRunners.put(LsCommandRunner.STRING_VALUE, new LsCommandRunner());
        commandRunners.put(CatCommandRunner.STRING_VALUE, new CatCommandRunner());
        commandRunners.put(EchoCommandRunner.STRING_VALUE, new EchoCommandRunner());
        commandRunners.put(ExitCommandRunner.STRING_VALUE, new ExitCommandRunner());
        commandRunners.put(PwdCommandRunner.STRING_VALUE, new PwdCommandRunner());
        commandRunners.put(WcCommandRunner.STRING_VALUE, new WcCommandRunner());
    }

    String getVariableValue(String name) {
        return variables.getOrDefault(name, "");
    }

    CommandRunner getOrCreateCommandRunner(String name) {
        if (commandRunners.containsKey(name)) {
            return commandRunners.get(name);
        }
        CustomCommandRunner customCommandRunner = new CustomCommandRunner(name);
        commandRunners.put(name, customCommandRunner);
        return customCommandRunner;
    }

    public String getResult() {
        String result = previousResult;
        previousResult = "";
        return result;
    }

    public Path getFilePath(String file) {
        return currentDirectory.resolve(file);
    }

    public void changeDirectory(String s) throws IOException {
        Path newPath = currentDirectory.resolve(s).normalize().toAbsolutePath();
        if (Files.exists(newPath) && Files.isDirectory(newPath)) {
            currentDirectory = newPath;
        }
    }

    public Pair<Set<String>, Set<String>> getDirectoriesAndFiles(String s) throws IOException {
        return new Pair<>(
                Files.walk(currentDirectory.resolve(s), 1)
                        .filter(Files::isDirectory)
                        .map(p -> currentDirectory.relativize(p))
                        .map(Path::toString)
                        .filter(str -> !str.isEmpty())
                        .collect(Collectors.toSet()),
                Files.walk(currentDirectory.resolve(s), 1)
                        .filter(Files::isRegularFile)
                        .map(p -> currentDirectory.relativize(p))
                        .map(Path::toString)
                        .collect(Collectors.toSet())
        );
    }

    public Path getCurrentDirectory() {
        return currentDirectory;
    }

    public void writeResult(String s) {
        previousResult = s;
    }

    void assignVariable(String name, String value) {
        variables.put(name, value);
    }
}

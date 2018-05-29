package ru.spbau.cli;

import ru.spbau.cli.commandrunner.CommandRunner;
import ru.spbau.cli.utils.Pair;

import java.util.List;
import java.util.Optional;

import static ru.spbau.cli.ParserImpl.DOLLAR_SIGN_SYMBOL;

/**
 * Command class
 */
class Command {
    private final Parser parser;

    Command(String commandAsString) {
        parser = new ParserImpl(commandAsString.trim());
    }

    /**
     * Executes the command: splits the command by pipeline symbols,
     * then substitutes every identifier with its value,
     * then executes every command
     * and checks if the execution should stop after any command in the sequence
     * @param environment Environment state
     * @return True if the execution should stop; False else
     */
    boolean execute(Environment environment) {
        Optional<Boolean> shouldExit = parser.splitByPipeline().stream()
                .map(Command::new)
                .map(command -> command.eliminateDollarSigns(environment))
                .map(command -> command.executeWithoutPipelines(environment))
                .filter(x -> x)
                .findFirst();
        return shouldExit.isPresent();
    }

    /**
     * Finds every identifier in the command and replaces it with its value
     * @param environment Environment state
     * @return Command without dollar signs and identifiers
     */
    private Command eliminateDollarSigns(Environment environment) {
        List<String> commandPartsAsString = parser.splitByIdentifiers();
        StringBuilder commandWithoutDollarSignsAsStringBuilder = new StringBuilder();
        for (String commandPartAsString: commandPartsAsString) {
            if (commandPartAsString.charAt(0) == DOLLAR_SIGN_SYMBOL) {
                String name = commandPartAsString.substring(1);
                String value = environment.getVariableValue(name);
                commandWithoutDollarSignsAsStringBuilder.append(value);
            } else {
                commandWithoutDollarSignsAsStringBuilder.append(commandPartAsString);
            }
        }
        return new Command(commandWithoutDollarSignsAsStringBuilder.toString());
    }

    /**
     * If the assignment is presented, then the variable assignment is executed
     * Else command name and the arguments are extracted, then the command is executed
     * @param environment Environment state
     * @return True if the execution should stop; False else
     */
    private boolean executeWithoutPipelines(Environment environment) {
        List<String> splitByAssignment = parser.splitByAssignment();
        if (splitByAssignment.size() >= 2) {
            String name = splitByAssignment.get(0);
            String value = splitByAssignment.get(1);
            environment.assignVariable(name, value);
            environment.writeResult("");
            return false;
        }
        Pair<String, String> nameAndArguments = parser.findNameAndArgument();
        String name = nameAndArguments.getFirst();
        String argument = nameAndArguments.getSecond();
        CommandRunner commandRunner = environment.getOrCreateCommandRunner(name);
        commandRunner.run(environment, argument);
        return commandRunner.shouldExit();
    }
}

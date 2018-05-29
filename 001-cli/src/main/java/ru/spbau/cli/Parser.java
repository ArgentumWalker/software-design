package ru.spbau.cli;

import ru.spbau.cli.utils.Pair;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Parser interface
 */
public interface Parser {

    Set<Character> QUOTE_MARKS = Stream.of('\'', '"').collect(Collectors.toSet());
    Set<Character> SINGLE_QUOTE_MARKS = Stream.of('\'').collect(Collectors.toSet());
    char PIPELINE_SYMBOL = '|';
    char DOLLAR_SIGN_SYMBOL = '$';
    Set<Character> IDENTIFIER_STOPPERS = Stream.of('$', '"', '\'', ' ', '\n', '\r', '\t')
            .collect(Collectors.toSet());
    char ASSIGNMENT_SYMBOL = '=';
    Set<Character> SPACE_SYMBOLS = Stream.of(' ', '\n', '\r', '\t').collect(Collectors.toSet());


    /**
     * Splits the string by identifiers
     * @return Parts of the string
     */
    List<String> splitByIdentifiers();

    /**
     * Splits the string by pipeline symbol
     * @return Parts of the string
     */
    List<String> splitByPipeline();

    /**
     * Finds the command name and the command argument in the string
     * @return Name and argument pair
     */
    Pair<String,String> findNameAndArgument();

    /**
     * Splits the string by assignment symbol
     * @return Parts of the string
     */
    List<String> splitByAssignment();
}

package ru.spbau.cli;

import ru.spbau.cli.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.emptySet;

/**
 * Parser
 */
public class ParserImpl implements Parser {
    private final String commandAsString;

    ParserImpl(String commandAsString) {
        this.commandAsString = commandAsString;
    }

    private int findSymbolPosition(Set<Character> symbols,
                                   int startPosition,
                                   Set<Character> escapers) {
        boolean escaping = false;
        for (int i = startPosition; i < commandAsString.length(); i++) {
            char currentChar = commandAsString.charAt(i);
            if (escapers.contains(currentChar)
                    && (i == 0 || commandAsString.charAt(i - 1) != '\'')) {
                escaping = !escaping;
            }
            if (!escaping && symbols.contains(currentChar)) {
                return i;
            }
        }
        return commandAsString.length();
    }

    private int findSymbolPosition(char symbol,
                                   int startPosition,
                                   Set<Character> escapers) {
        Set<Character> symbols = Stream.of(symbol).collect(Collectors.toSet());
        return findSymbolPosition(symbols, startPosition, escapers);
    }

    private List<String> split(char symbol, Set<Character> escapers) {
        List<String> result = new ArrayList<>();
        int l;
        int r = 0;
        do {
            l = r;
            r = findSymbolPosition(symbol, l, escapers);
            result.add(commandAsString.substring(l, r));
            r++;
        } while (r < commandAsString.length());
        return result;
    }

    /**
     * Splits the string by identifiers
     * @return Parts of the string
     */
    @Override
    public List<String> splitByIdentifiers() {
        List<String> commandPartsAsString = new ArrayList<>();
        int lastPosition;
        int l = 0;
        int r;
        do {
            lastPosition = l;
            l = findSymbolPosition(DOLLAR_SIGN_SYMBOL, l, SINGLE_QUOTE_MARKS);
            r = findSymbolPosition(IDENTIFIER_STOPPERS, l + 1, emptySet());
            if (lastPosition != l) {
                commandPartsAsString.add(commandAsString.substring(lastPosition, l));
            }
            if (l >= commandAsString.length()) {
                break;
            }
            commandPartsAsString.add(commandAsString.substring(l, r));
            l = r;
        } while (l < commandAsString.length());
        return commandPartsAsString;
    }

    private String eliminateQuoteMarks(String s) {
        s = s.trim();
        if (s.isEmpty()) {
            return s;
        }
        if (QUOTE_MARKS.contains(s.charAt(0)) && QUOTE_MARKS.contains(s.charAt(s.length() - 1))) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

    /**
     * Splits the string by pipeline symbol
     * @return Parts of the string
     */
    @Override
    public List<String> splitByPipeline() {
        return split(PIPELINE_SYMBOL, QUOTE_MARKS);
    }

    /**
     * Finds the command name and the command argument in the string
     * @return Name and argument pair
     */
    @Override
    public Pair<String, String> findNameAndArgument() {
        int spacePosition = findSymbolPosition(SPACE_SYMBOLS, 0, QUOTE_MARKS);
        String name = commandAsString.substring(0, spacePosition);
        String argument = eliminateQuoteMarks(commandAsString.substring(spacePosition).trim());
        return new Pair<>(name, argument);
    }

    /**
     * Splits the string by assignment symbol
     * @return Parts of the string
     */
    @Override
    public List<String> splitByAssignment() {
        return split(ASSIGNMENT_SYMBOL, QUOTE_MARKS).stream()
                .map(this::eliminateQuoteMarks)
                .collect(Collectors.toList());
    }
}

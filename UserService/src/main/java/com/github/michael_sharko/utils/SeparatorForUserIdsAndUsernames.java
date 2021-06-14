package com.github.michael_sharko.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SeparatorForUserIdsAndUsernames {
    private static final String PATTERN = "([a-zA-Z0-9_]{4,16}|[0-9]+)";

    List<Integer> identifiers = new ArrayList<>();
    List<String> names = new ArrayList<>();

    private static Matcher createMatcher(String source) {
        return Pattern.compile(PATTERN).matcher(source);
    }

    private void fillIdentifiersAndNamesLists(Matcher matcher) {
        while (matcher.find()) {
            String coincidence = matcher.group();
            if (isNumeric(coincidence)) {
                identifiers.add(Integer.parseInt(coincidence));
            } else {
                names.add(coincidence);
            }
        }
    }

    public static boolean isNumeric(String source) {
        try {
            Integer.parseInt(source);
            return true;
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
    }

    public void insert(String user_ids) {
        Matcher matcher = createMatcher(user_ids);
        fillIdentifiersAndNamesLists(matcher);
    }

    public SeparatorForUserIdsAndUsernames(String user_ids) {
        insert(user_ids);
    }

    public SeparatorForUserIdsAndUsernames() {
    }

    public List<Integer> getIdentifiers() {
        return identifiers;
    }

    public List<String> getNames() {
        return names;
    }

    public String getIdentifiersString(String splitter) {
        return Arrays.toString(identifiers.toArray())
                .replace("[", "")
                .replace("]", "")
                .replace(", ", splitter);
    }

    public String getNamesString(String splitter) {
        return Arrays.toString(names.toArray())
                .replace("[", "")
                .replace("]", "")
                .replace(", ", splitter);
    }

    public boolean hasIdentifiers() {
        return identifiers.size() != 0;
    }

    public boolean hasNames() {
        return names.size() != 0;
    }

    public boolean isEmpty() {
        return !hasIdentifiers() && !hasNames();
    }

    public boolean hasIdentifiersAndNames() {
        return hasIdentifiers() && hasNames();
    }
}

package org.justgive.util;

import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Donor: peter
 * Date: 1/30/13
 * Time: 3:33 PM
 */
public class StringCleaner {
    private static Logger jgLog = LoggerFactory.getLogger(StringCleaner.class);

    private boolean removeSymbols = false;
    private boolean removeSpaces = false;
    private boolean removeNumbers = false;
    private boolean removeLetters = false;
    private List<Character> exceptions = new ArrayList<Character>();
    private List<Character> toRemove = new ArrayList<Character>();

    public StringCleaner removeSymbols() {
        this.removeSymbols = true;
        return this;
    }

    public StringCleaner removeSpaces() {
        this.removeSpaces = true;
        return this;
    }

    public StringCleaner removeNumbers() {
        this.removeNumbers = true;
        return this;
    }

    public StringCleaner removeLetters() {
        this.removeLetters = true;
        return this;
    }

    public StringCleaner remove(Character... characters) {
        List<Character> characterList = Arrays.asList(characters);
        toRemove.addAll(characterList);
        exceptions.removeAll(characterList);
        return this;
    }

    public StringCleaner except(Character characters) {
        List<Character> characterList = Arrays.asList(characters);
        exceptions.addAll(characterList);
        toRemove.removeAll(characterList);
        return this;
    }

    /**
     * Removes specified character types from input
     *
     * @return String   The input String trimmed of non-numbers
     */
    public String clean(String originalString) {
        //make sure they entered SOMETHING
        if (StringUtil.isEmpty(originalString)) return "";

        StringBuilder cleanString = new StringBuilder(originalString.length()).append("");

        for (char currentChar : originalString.toCharArray()) {
            if (exceptions.contains(currentChar)) cleanString.append(currentChar);

            if (toRemove.contains(currentChar)) continue;
            if (removeSymbols && isSymbol(currentChar)) continue;
            if (removeSpaces && Character.isSpaceChar(currentChar)) continue;
            if (removeNumbers && Character.isDigit(currentChar)) continue;
            if (removeLetters && Character.isLetter(currentChar)) continue;

            cleanString.append(currentChar);
        }
        jgLog.debug("original String: " + originalString);
        jgLog.debug("clean String: " + cleanString);
        return cleanString.toString();
    }

    private boolean isSymbol(char currentChar) {
        return !(Character.isLetterOrDigit(currentChar) || Character.isSpaceChar(currentChar));
    }


}

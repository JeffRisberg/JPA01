package org.justgive.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.validator.EmailValidator;
import org.justgive.exceptions.JustGiveException;
import org.justgive.logger.Logger;
import org.justgive.logger.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Static class containing various String utilities.
 * <p/>
 * Donor: curtis
 * Date: Oct 22, 2007
 * Time: 9:57:07 AM
 */
public class StringUtil {
    private static Logger jgLog = LoggerFactory.getLogger(StringUtil.class);

    private final static String ENCODING = "UTF-8";

    private static StringCleaner nonNumberCleaner = new StringCleaner().removeSymbols().removeLetters().removeSpaces();

    private StringUtil() {

    }

    /**
     * Checks whether a String is null or empty.
     *
     * @param string The String to check
     * @return boolean
     */
    public static boolean isEmpty(String string) {
        return (string == null || string.equals(""));
    }

    /**
     * Checks whether a String is null or contains only whitespace
     *
     * @param string The String to check
     * @return boolean
     */
    public static boolean isBlank(String string) {
        return (string == null || string.matches("\\s*"));
    }

    /**
     * Determines if an array of Strings contains any null or empty elements
     *
     * @param strings String  array to test
     * @return <code>true</code> if any of the String elements is empty or null;
     *         <code>false</code> otherwise
     */
    public static boolean containsEmpty(String[] strings) {
        if (strings == null || strings.length == 0) return true;

        for (String currentElement : strings) {
            if (isEmpty(currentElement)) return true;
        }
        return false;
    }

    /**
     * Determines if an array of Strings contains any non null or empty elements
     *
     * @param strings String  array to test
     * @return <code>true</code> if any of the String elements is empty or null;
     *         <code>false</code> otherwise
     */
    public static boolean containsNonNull(String[] strings) {
        if (strings == null || strings.length == 0) return false;

        for (String currentElement : strings) {
            if (!isEmpty(currentElement)) return true;
        }
        return false;
    }

    /**
     * Returns an empty String if the argument value is null, otherwise returns the String argument.
     *
     * @param string The String to test
     * @return an empty String if the argument is null, otherwise the argument.
     */
    public static String nullToString(String string) {
        return string == null ? "" : string;
    }

    /**
     * Returns the int value for a String, or 0 if not an int.
     *
     * @param string The String to check
     * @return int
     */
    public static int getInteger(String string) {
        return getInteger(string, 0);
    }

    /**
     * Returns the int value for a String, or 0 if not an int.
     *
     * @param string The String to check
     * @return int
     */
    public static Integer getInteger(String string, Integer defaultValue) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Checks whether a String value is an integer.
     *
     * @param string The String to check
     * @return boolean
     */
    public static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    /**
     * Checks whether a String value is a number.
     *
     * @param n The String to check
     * @return boolean
     */
    public static boolean isNumber(String n) {
        try {
            Float.parseFloat(n);
        } catch (NumberFormatException e1) {
            jgLog.debug(n + " is not a number");
            return false;

        }

        return true;
    }

    /* use above methodi, this is left here for
          * backwards compatibility with codemagi tools
          * for now
          */
    public static boolean isValidInteger(String i) {
        return StringUtil.isInteger(i);
    }

    /**
     * Boolean values can be true/false case-insensitive, t/f, or 1/0.
     *
     * @param booleanValue The value to evaluate.
     * @return The evaluation
     */
    public static boolean getBoolean(String booleanValue) {
        return booleanValue != null &&
                (booleanValue.equals("1") || booleanValue.equalsIgnoreCase("t") || booleanValue.equalsIgnoreCase(
                        "true"));
    }

    /**
     * Checks for a valid email address.
     *
     * @param token string to join chars
     * @param chars chars to join
     * @return joins character array
     */

    public static String join(String token, char[] chars) {
        StringBuffer sb = new StringBuffer();

        for (int x = 0; x < (chars.length - 1); x++) {
            sb.append(chars[x]);
            sb.append(token);
        }
        sb.append(chars[chars.length - 1]);

        return (sb.toString());
    }

    public static String join(String token, String[] strings) {
        StringBuffer sb = new StringBuffer();

        for (int x = 0; x < (strings.length - 1); x++) {
            sb.append(strings[x]);
            sb.append(token);
        }
        sb.append(strings[strings.length - 1]);

        return (sb.toString());
    }

    /**
     * Turns a camel case word into a list of words:
     * <p/>
     * "camelCaseWord"
     * becomes
     * {"camel", "Case", "Word"}
     *
     * @param camelCaseWord
     * @return String[]
     */
    public static String[] splitCamelCase(String camelCaseWord) {
        //split the String on uppercase letters into a String[]
        String[] camelCaseWords = camelCaseWord.split("(?=[A-Z])");

        jgLog.debug("split camel case word: " + camelCaseWord + " to array " + camelCaseWords.length);
        return camelCaseWords;
    }

    /**
     * Replaces uppercase letters in a camel case word with
     * a special character followed by the lowercase equivalent.
     * <p/>
     * "camelCaseWord", "_"
     * becomes
     * "camel_case_word"
     *
     * @param camelCaseWord
     * @param wordSeparator
     * @return
     */
    public static String flattenCamelCase(String camelCaseWord, String wordSeparator) {
        String[] camelCaseWords = splitCamelCase(camelCaseWord);

        StringBuffer flatCamelCase = new StringBuffer();
        int length = camelCaseWords.length;
        for (int i = 0; i < length; i++) {
            String word = camelCaseWords[i];
            flatCamelCase.append(word);
            if (i != length - 1) flatCamelCase.append(wordSeparator);
        }
        return flatCamelCase.toString().toLowerCase();
    }

    public static String toCamelCase(String word, String wordSeparator) {

        jgLog.debug("toCamelCase: " + word);

        String camelCaseWord = word.toLowerCase();
        int index = camelCaseWord.indexOf(wordSeparator);
        while (index > 0) {
            camelCaseWord = camelCaseWord.substring(0, index) + StringUtil.initCaps(camelCaseWord.substring(index + 1));
            index = camelCaseWord.indexOf(wordSeparator);
            jgLog.debug("step: " + camelCaseWord);
        }
        return StringUtil.initLowercase(camelCaseWord);
    }

    public static String toCamelCase(String word) {
        return toCamelCase(word.replaceAll(" +", " ").trim(), " ");
    }

    /**
     * Checks for a valid email address.
     *
     * @param toCheck email to check
     * @return true if email passes test
     */
    public static boolean isValidEmail(String toCheck) {
        if (isEmpty(toCheck)) {
            return false;
        }

        // Trim the value
        toCheck = toCheck.trim();

        EmailValidator emailValidator = EmailValidator.getInstance();
        boolean isValid = emailValidator.isValid(toCheck);
        jgLog.debug(toCheck + " is valid? " + isValid);
        return isValid;
    }

    /**
     * Removes everything except digits [0-9] from input
     *
     * @param valueIn The String to trim
     * @return String   The input String trimmed of non-numbers
     */
    public static String trimNonNumbers(String valueIn) {

        return nonNumberCleaner.clean(valueIn);
    }

    /**
     * Removes everything except digits [0-9] and decimals from input.
     * removes decimals too if they ~ 0
     *
     * @param valueIn The String to trim
     * @return String   The input String trimmed of non-numbers
     */
    public static String cleanDecimalNumber(String valueIn) {

        //make sure they entered SOMETHING
        if (StringUtil.isEmpty(valueIn)) return "";

        int decimalIndex = valueIn.indexOf(".");

        if (decimalIndex >= 0) {
            jgLog.debug("dealing with decimal number");

            String num = trimNonNumbers(valueIn.substring(0, decimalIndex));
            String decimal = trimNonNumbers(valueIn.substring(decimalIndex));
            StringBuffer wholeNum = new StringBuffer(num);

            return (decimal.matches("0+"))
                   ? wholeNum.toString()
                   : wholeNum.append(".").append(decimal).toString();

        } else {
            return trimNonNumbers(valueIn);
        }
    }

    public static String escapeHTML(String valueIn) {
        if (isEmpty(valueIn)) return "";

        return StringEscapeUtils.escapeHtml(valueIn);
    }

    public static String escapeQuotes(String valueIn) {
        if (valueIn == null) return null;

        return valueIn.replaceAll("\"", "&quot;");
    }

    /**
     * convert "'" to "''" for database inserts
     *
     * @param valueIn String to check
     * @return String  String with single quotes escaped for DB
     */
    public static String escapeSingleQuotes(String valueIn) {
        if (valueIn == null) return "";

        return valueIn.replaceAll("'", "''");
    }

    /**
     * convert String to Javascript safe strings
     *
     * @param valueIn String to check
     * @return String  String with single quotes escaped for DB
     */
    public static String escapeJavascript(String valueIn) {
        if (valueIn == null) return "";

        return StringEscapeUtils.escapeJavaScript(valueIn);
    }

    /**
     * convert Javascript safe strings back to normal
     *
     * @param valueIn String to check
     * @return String  String with single quotes escaped for DB
     */
    public static String unescapeJavascript(String valueIn) {
        if (valueIn == null) return "";

        return StringEscapeUtils.unescapeJavaScript(valueIn);
    }

    /**
     * Removes html tags from a string.
     *
     * @param string String input
     * @return String without html tags
     */
    public static String removeTags(String string) {
        if (StringUtil.isEmpty(string)) {
            return "";
        }

        return string.replaceAll("\\<.*?\\>", "");
    }

    /**
     * Alters a string as follows:
     * <p/>
     * 1) calls StringEscapeUtils.unescapeHtml() to translate some html entities to tags
     * 2) removes div tags
     *
     * @param string String input
     * @return String without html tags
     */
    public static String removeEntities(String string) {
        if (StringUtil.isEmpty(string)) {
            return "";
        }

        string = StringEscapeUtils.unescapeHtml(string);
        // fix single quotes
        string = string.replaceAll("''", "'");
        // remove div tags
        return string.replaceAll("\\<.*?div\\>", "");

    }

    /**
     * Make a valid vanity url path, eg:
     * http://www.justgive.org/vanity-url_123
     *
     * @param requestPath
     * @return
     */
    public static boolean isValidVanityUrl(String requestPath) {
        //make sure they entered SOMETHING
        if (StringUtil.isEmpty(requestPath)) {
            return false;
        }
        //loop through each char, checking if it is valid
        char[] charsIn = requestPath.toCharArray();

        for (char currentChar : charsIn) {
            if (!Character.isLetterOrDigit(currentChar) && '-' != currentChar && '_' != currentChar) {
                return false;
            }
        }
        return true;
    }

    /**
     * extract a Youtube video id from any of the myriad URL types that Youtube uses
     *
     * @param Url String
     * @return String
     */
    public static String extractVideoId(String Url) {
        if (Url != null) {
            Pattern pattern = Pattern.compile(
                    "^.*?(((youtu.be/)|(v/)|(/u/\\w/)|(embed/))|(watch\\?(v=|([^&=]+=[^&]&)+?v=)))([\\w-]+).*");
            Matcher matcher = pattern.matcher(Url);
            if (matcher.matches()) {
                return matcher.group(10);
            }
        }
        return "";
    }


    /**
     * Capitalize the first letter of the String
     *
     * @param str String to capitalize first letter
     * @return init capitalized String
     */
    public static String initCaps(String str) {
        if (str == null) return null;
        if (isEmpty(str)) return "";
        String upper = str.substring(0, 1).toUpperCase();
        if (str.length() > 1) {
            return upper + str.substring(1);
        } else {
            return upper;
        }
    }

    /**
     * Capitalize the first letter of the String
     *
     * @param str String to capitalize first letter
     * @return init capitalized String
     */
    public static String initLowercase(String str) {
        if (str == null) return null;
        if (isEmpty(str)) return "";
        String upper = str.substring(0, 1).toLowerCase();
        if (str.length() > 1) {
            return upper + str.substring(1);
        } else {
            return upper;
        }
    }

    /**
     * Capitalize the first letter of each word in a String
     *
     * @param str String to capitalize first letter of each word
     * @return init capitalized String
     */
    public static String initCapsAll(String str) {
        if (str == null) return null;
        if (isEmpty(str)) return "";
        StringBuffer result = new StringBuffer();
        StringTokenizer tokenizer = new StringTokenizer(str.toLowerCase(), " ");
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            result.append(initCaps(token)).append(" ");
        }
        return result.toString().trim();
    }

    /*
      * @deprecated
      * @see Form
      */
    public static void checkRequired(String[] requiredFields)
            throws JustGiveException {
        if (requiredFields.length == 0) {
            throw new JustGiveException("MSG_MISSING_REQUIRED");
        } else {
            for (String s : requiredFields) {
                jgLog.debug("Required value: " + s);
                if (StringUtil.isEmpty(s)) {
                    jgLog.debug("Empty required field: " + s);
                    throw new JustGiveException("MSG_MISSING_REQUIRED");
                }
            }
        }
    }

    /**
     * Checks for a valid email address.
     *
     * @param toCheck email to validate
     * @throws org.justgive.exceptions.JustGiveException If the email address is invalid
     */
    public static void checkValidEmail(String toCheck)
            throws JustGiveException {
        if (!StringUtil.isValidEmail(toCheck)) {
            throw new JustGiveException("MSG_INVALID_EMAIL");
        }
    }

    public static Integer checkValidInteger(String intStr)
            throws JustGiveException {
        try {
            return Integer.parseInt(intStr);
        } catch (NumberFormatException nfe) {
            throw new JustGiveException("MSG_INVALID_INTEGER");
        }
    }

    public static void checkValidIntegers(String[] ints)
            throws JustGiveException {
        for (String anInt : ints) {
            checkValidInteger(anInt);
        }
    }

    public static void checkValidNumber(String number)
            throws JustGiveException {
        if (!isNumber(number)) throw new JustGiveException("MSG_INVALID_AMOUNT");
    }

    public static void checkValidNumbers(String[] numbers)
            throws JustGiveException {
        for (String aNum : numbers) {
            if (!isNumber(aNum)) throw new JustGiveException("MSG_INVALID_AMOUNT");
        }
    }

    public static String getQueryParam(String key, String val, boolean prependSeparator) {
        // key argument to URLEncoder must be not null
        if (StringUtil.isEmpty(key)) {
            return "";
        } else if (val == null) {
            val = "";
        }

        StringBuffer param = (prependSeparator) ? new StringBuffer("&") : new StringBuffer("?");
        param.append(encode(key)).append("=").append(encode(val));
        jgLog.debug(param);
        return param.toString();
    }

    public static String getQueryParam(String key, String val) {
        return StringUtil.getQueryParam(key, val, true);
    }

    public static String encode(String input) {
        try {
            return URLEncoder.encode(input, ENCODING);
        } catch (UnsupportedEncodingException e) {
            jgLog.debug(e);
            return input;
        }
    }

    public static String decode(String input) {
        try {
            return URLDecoder.decode(input, ENCODING);
        } catch (UnsupportedEncodingException e) {
            jgLog.debug(e);
            return input;
        }
    }

    /**
     * Replaces all matching strings instances in a buffer.
     *
     * @param buffer The buffer to work with
     * @param from   The replaced text
     * @param to     The replacing text
     */
    public static void replaceBufferText(StringBuffer buffer, String from, String to) {
        int index = buffer.indexOf(from);

        if (index == -1) {
            return;
        }

        buffer.replace(index, index + from.length(), to);

        // Recursive call to get all replace instances
        replaceBufferText(buffer, from, to);
    }

    public static String[] captureGroups(String replaceString, String regex) {
        Pattern regexPattern = Pattern.compile(regex);
        Matcher regexMatcher = regexPattern.matcher(replaceString);
        String[] groups = new String[regexMatcher.groupCount()];
        for (int i = 1; i <= groups.length; i++) {
            groups[i] = regexMatcher.group(i);
        }
        return groups;
    }

    public static String captureFirstGroup(String replaceString, String regex) {
        String[] groups = captureGroups(replaceString, regex);
        return (groups.length > 0) ? groups[1] : null;
    }

    /*
     * Removes the protocol (http or https only) from the beginning of a URL
     */
    public static String removeProtocol(String url) {
        url = url.replaceFirst("^https?://", "");
        return url;
    }

}

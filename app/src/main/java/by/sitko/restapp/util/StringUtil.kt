package by.sitko.restapp.util

import android.text.TextUtils
import java.nio.charset.Charset
import java.util.*
import java.util.regex.Pattern

const val EMPTY = ""

/**
 * This regex for validating email used by BL
 * Locate at assets/common/utils.js
 */
const val CIRCUIT_EMAIL_PATTERN =
      "^[_a-z0-9-]+?(?:\\.[_a-z0-9-]+?)*?@[a-z0-9-]+?(?:\\.[a-z0-9-]+?)*?(?:\\.[a-z][a-z]+?)$"
const val COMMA = ","
const val COMMA_WITH_WHITESPACE = ", "
const val COMMERCIAL_AT = "@"
const val COLON = ":"
const val DOT = "."
const val HYPHEN = "-"
const val INVISIBLE_SYMBOL = "\u200B"
const val NEWLINE = "\n"
const val NON_BREAKING_SPACE_CODE = "&#160;"
const val PLUS = "+"
const val QUOTE = "\""
const val SEPARATOR = " · "
const val SEPARATOR_COLON = ": "
const val SLASH = "/"
const val DOUBLE_SLASH = "//"
const val TAB = "\t"
const val THREE_DOTS = "..."
const val UNDERLINE = "_"
const val WHITESPACE = " "

/**
 * Returns a string containing the tokens joined without delimiters.
 *
 * @param tokens an array objects to be joined. Strings will be formed from
 * the objects by calling object.toString().
 */
fun join(tokens: Iterable<*>): String {
    val sb = StringBuilder()
    for (token in tokens) {
        sb.append(token)
    }
    return sb.toString()
}

/**
 * Adding a double quote at the first and at the end of the string.
 *
 * @param string The string representation
 * @return Converting ***something*** to ***"something"***
 * @deprecated Use JsonUtil.toJsonString instead
 */
fun surroundWithQuote(string: String?): String {
    // Well, we should have something generic for this.
    val surrounded = StringBuilder()
    surrounded.append(QUOTE)
    surrounded.append(string)
    surrounded.append(QUOTE)

    return surrounded.toString()
}

/**
 * Striping surrounding quotes from a string.
 *
 * @param string The string representation
 * @return Converting ***"something"** to ***something**
 */
fun stripSurroundingQuotes(string: String): String = string.replace("^\"|\"$".toRegex(), EMPTY)

/**
 * Converts a string array to a string presentation with brackets.
 *
 * @param string The string representation
 * @return the string representation of string array.
 */
fun surroundWithBrackets(string: Array<String>?): String = Arrays.deepToString(string)

/**
 * Compares two CharSequences, returning 'true' if they represent
 * equal sequences of characters.
 * <p>
 * The implementation of this method is borrowed from Android's TextUtils.
 *
 * @param a first CharSequence to check
 * @param b second CharSequence to check
 * @return true if a and b are equal, including if they are both null
 */
fun equals(a: CharSequence?, b: CharSequence?): Boolean {
    if (a === b) return true
    if (a != null && b != null) {
        val length = a.length
        if (length == b.length) {
            return if (a is String && b is String) {
                a == b
            } else {
                (0 until length).none { a[it] != b[it] }
            }
        }
    }
    return false
}

/**
 * Check if typed email mathes to email pattern
 */
fun String?.isEmailValid(): Boolean {
    return this != null && Pattern.compile(
          CIRCUIT_EMAIL_PATTERN,
          Pattern.CASE_INSENSITIVE
    ).matcher(this.trim { it <= ' ' }).matches()
}

/**
 * Extract all values from array which was retrieved as one string
 */
fun parseStringArray(str: String): Array<String?> {
    if (TextUtils.isEmpty(str)) {
        return arrayOfNulls(0)
    }
    val newStr = str.substring(1, str.length - 1)
    return newStr.split(",".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()
}

fun escapeDotsforRegexp(inputString: String): String = inputString.replace("\\.".toRegex(), "\\\\.")

fun removeInvisibleCharacters(str: String?): String? = str?.replace(INVISIBLE_SYMBOL, "")

/**
 * Replace for [TextUtils.isEmpty] because
 * [it's impossible to use it in Unit tests]
 * (http://tools.android.com/tech-docs/unit-testing-support#TOC-Method-...-not-mocked.-).
 *
 * @param str string for check
 * @return true is string == 0 or empty
 */
fun String?.isEmpty(): Boolean = this.isNullOrEmpty()

/**
 * Check that string is not null, empty or contain "null" value
 *
 * @param str string for validation
 * @return true if not null, empty ot contains "null" value
 */
fun notNullValue(str: String?): Boolean =
      !str.isNullOrEmpty()

/**
 * Returns a string containing the tokens joined by delimiters.
 * @param tokens an array objects to be joined. Strings will be formed from
 * *     the objects by calling object.toString().
 */
fun join(delimiter: CharSequence, tokens: Iterable<*>): String {
    val sb = StringBuilder()
    val it = tokens.iterator()
    if (it.hasNext()) {
        sb.append(it.next())
        while (it.hasNext()) {
            sb.append(delimiter)
            sb.append(it.next())
        }
    }
    return sb.toString()
}

/**
 * Returns the sum of bytes in an UTF-8 string.
 * Meant to be used as a very simple hash, with many collisions, to protect the actual string,
 * when privacy is needed. Same simple hash used on Android and IOS to allow comparison.
 * @param s String to have simple hash created
 * @return Int with the sum of bytes in an UTF-8 string.
 **/
fun sumOfBytes(s: String?): Int {
    return s?.toByteArray(Charset.forName("UTF-8"))?.sumBy(Byte::toInt) ?: 0
}

fun String.directory() = this.substringBeforeLast("/")
fun String.fullName() = this.substringAfterLast("/")
fun String.fileName() = this.substringBeforeLast(".")
fun String.extension() = this.substringAfterLast(".")

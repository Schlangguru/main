package edu.hm.hafner.util;

import java.util.HashSet;
import java.util.Set;

/**
 * Several useful utility methods that work on {@link String} instances.
 *
 * @author Ulli Hafner
 */
public final class StringUtils {

    /**
     * Creates a new instance of {@link StringUtils}.
     *
     * @author Ulli Hafner
     */
    private StringUtils() {
        // prevents instantiation
    }

    /**
     * Prüft, ob der übergebene String leer ist, d.h. kein Zeichen enthält.
     *
     * @param value der zu prüfende String
     * @return true falls der String kein Zeichen enthält oder null ist, false sonst.
     *
     * @author Sebastian Seidl
     */
    public static boolean isBlank(final String value) {
        return value == null || value.isEmpty();
    }

    /**
     * Verkettet die übergebenen Strings zu einen neuen String.
     * Die einzelenen Elemente werden durch ein Komma getrennt.
     * Mindestens ein Element muss übergeben werden. Der Wert NULL
     * wird durch "(null)" dargestellt.
     *
     * @param elements zu verkettende Strings
     * @return String der aus allen Parameterelementen besteht
     *
     * @author Sebastian Seidl
     */
    public static String join(final String... elements) {
        return null;
    }

    /**
     * Prüft ob es sich bei dem eingegebenn String um eine gültige
     * 10-Stellige ISBN Nummer handelt.
     *
     * @param isbnEingabe Zu prüfende Eingabe
     * @return true wenn Eingabe gültige 10-Stellige ISBN / false sonst
     *
     * @author Sebastian Seidl
     */
    public static boolean isValidISBN10(final String isbnEingabe) {
        return false;
    }

    /**
     * Entfernt in einem String alle vorkommenden Zeichen aus einem anderen String.
     * Beispiel: Wenn man "Hallo-ollaH" mit "Halo" kürzt, ergibt das "-".
     *
     * @param eingabe Zu kürzender String
     * @param toBeRemoved String mit den Zeichen, die entfernt werden sollen
     * @return Gekürzter String
     *
     * @author Felix Dürrwanger
     */
    public static String strip(final String eingabe, final String toBeRemoved) {

        if (eingabe == null || toBeRemoved == null) {
            throw new IllegalArgumentException("Null is not a valid parameter");
        }

        final StringBuilder stringBuilder = new StringBuilder();

        for (char character : eingabe.toCharArray()) {
            if (!toBeRemoved.contains(String.valueOf(character))) {
                stringBuilder.append(character);
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Prüf ein Passwort auf seine Sicherheit. Ein sicheres Passwort muss mindestens
     * 20 Zeichen lang sein, davon 10 unterschiedliche. Es müssen je eine Zahl, ein
     * kleiner Buchstabe, ein großer Buchstabe sowie ein Sonderzeichen vorkommen. Als
     * Sonderzeichen werden alle Zeichen akzeptiert, die nicht in eine der anderen
     * Kategorien fallen.
     *
     * @param passwordEingabe Zu prüfendes Passwort
     * @return Passwort ist sicher
     *
     * @author Felix Dürrwanger
     */
    public static boolean isSecure(final String passwordEingabe) {

        if (passwordEingabe == null || passwordEingabe.length() < 20) {
            return false;
        }

        // Set speichert keine Duplikate
        final Set<Character> characterSet = new HashSet<Character>();

        for (char character : passwordEingabe.toCharArray()) {
            characterSet.add(character);
        }

        // Länge des Arrays = Anzahl der verscheidenen Zeichen
        if (characterSet.size() < 10) {
            return false;
        }

        boolean numberFound = false;
        boolean uppercaseFound = false;
        boolean lowercaseFound = false;
        boolean specialFound = false;

        for (char character : characterSet) {
            switch (Character.getType(character)) {
                case Character.DECIMAL_DIGIT_NUMBER:
                    numberFound = true;
                    break;
                case Character.UPPERCASE_LETTER:
                    uppercaseFound = true;
                    break;
                case Character.LOWERCASE_LETTER:
                    lowercaseFound = true;
                    break;
                default:
                    specialFound = true;
                    break;
            }
        }

        return numberFound && uppercaseFound && lowercaseFound && specialFound;
    }

    /**
     * Überprüft, ob der String eine gültige ISBN-13 ist. Eine ISBN-13 ist gültig,
     * wenn die letzte Ziffer mit der Prüfsumme der ersten 12 übereinstimmt. Die
     * Prüfsumme wird mit folgender Formel berechnet:
     *
     * (10 - ((z1 + z3 + z5 + z7 + z9 + z11 + 3 * (z0 + z2 + z4 + z6 + z8 + z10 + z12)) mod10)) mod10
     *
     * @param isbnEingabe Zu überprüfender String
     * @return String ist gültige ISBN13
     *
     * @author Felix Dürrwanger
     */
    public static boolean isValidIsbn13(final String isbnEingabe) {

        if (isbnEingabe == null) {
            return false;
        }

        final char[] characters = removeBlanksAndHyphens(isbnEingabe).toCharArray();

        if (characters.length != 13) {
            return false;
        }

        final int[] numbers = new int[13];

        for (int i = 0; i < 13; i++) {
            if (Character.getType(characters[i]) != Character.DECIMAL_DIGIT_NUMBER) {
                return false;
            }
            numbers[i] = Character.getNumericValue(characters[i]);
        }

        // Berechnung der Prüfsumme
        final int checksum = (10 - ((numbers[0] + numbers[2] + numbers[4] + numbers[6] + numbers[8] + numbers[10]
                + 3 * (numbers[1] + numbers[3] + numbers[5] + numbers[7] + numbers[9] + numbers[11])) % 10)) % 10;

        return checksum == numbers[12];
    }

    /**
     * Entfernt aus einem String alle Leerzeichen und Bindestriche.
     *
     * @param string String, aus dem die Leerzeichen und Bidnestriche entfernt werden
     * @return String ohne Leerzeichen und Bindestriche
     *
     * @author Felix Dürrwanger
     */
    private static String removeBlanksAndHyphens(final String string) {

        final StringBuilder stringBuilder = new StringBuilder();

        for (char character : string.toCharArray()) {
            final int type = Character.getType(character);
            if (type != Character.SPACE_SEPARATOR && type != Character.DASH_PUNCTUATION) {
                stringBuilder.append(String.valueOf(character));
            }
        }
        return stringBuilder.toString();
    }

}

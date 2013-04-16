package edu.hm.hafner.util;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Testet die Klasse {@link StringUtils}.
 *
 * @author Ulli Hafner
 */
public class StringUtilsTest {

    /**
     * Prüft, ob die beiden gültigen String Eingaben korrekt verarbeitet werden.
     */
    @Test
    public void testIsBlank_WithEmptyStrings() {
        assertTrue("null wird nicht als leer erkannt", StringUtils.isBlank(null));
        assertTrue("\"\" wird nicht als leer erkannt", StringUtils.isBlank(""));
    }

    /**
     * Prüft, ob ungültige String Eingaben korrekt erkannt werden.
     */
    @Test
    public void testIsBlank_WithoutEmptyStrings() {
        assertFalse("\" \" sollte nicht als leer erkannt werden", StringUtils.isBlank(" "));
        assertFalse("Tabulator sollte nicht als leer erkannt werden", StringUtils.isBlank(" "));
        assertFalse("Tabulator sollte nicht als leer erkannt werden", StringUtils.isBlank("\t"));
        assertFalse("Zeilenumbruch sollte nicht als leer erkannt werden", StringUtils.isBlank("\n"));
        assertFalse("\"abc\" sollte nicht als leer erkannt werden", StringUtils.isBlank("abc"));
    }

    @Test
    public void testJoin() {
        String msg = "Übergebe Werte \"Hallo\", null, \"du\"";

        String str1 = "Hallo";
        String str2 = null;
        String str3 = "du";

        String result = "Hallo,(null),du";

        assertEquals(msg, result, StringUtils.join(str1, str2, str3));
    }

    @Test
    public void testisValidISBN10() {
        fail("Noch nicht implementiert");
    }

    /**
     * Testet, ob die Zeichen alle aus dem String entfernt wurden.
     *
     * @author Felix Duerrwanger
     */
    @Test
    public void testeEntfernenVonZeichen() {

        assertEquals("Ein leerer String kann nicht gekürzt werden und bleibt leer", StringUtils.strip("", "ABCabc123"), "");
        assertEquals("Ein String, aus dem keine Zeichen entfernt werden sollen, ändert sich nicht", StringUtils.strip("ABCabc123", ""), "ABCabc123");
        assertEquals("Haben die beiden Strings keine gleichen Zeichen, ändert sich der zu kürzende String nicht", StringUtils.strip("ABCabc123", "DEFdef456"), "ABCabc123");
        assertEquals("Werden alle Zeichen entfernt, sollte der String leer sein", StringUtils.strip("ABCabc123", "ABCabc123"), "");
        assertEquals("Nur das erste Zeichen sollte entfernt werden", StringUtils.strip("ABCabc123", "XAZ"), "BCabc123");
        assertEquals("Nur das letzte Zeichen sollte entfernt werden", StringUtils.strip("ABCabc123", "X3Z"), "ABCabc12");
        assertEquals("Nur die drei Zeichen in der Mitte sollten entfernt werden", StringUtils.strip("ABCabc123", "4a5b6c7"), "ABC123");
        assertEquals("Nur die Leerzeichen sollten entfernt werden", StringUtils.strip("ABC abc 123", " "), "ABCabc123");
    }

    /**
     * Testet, ob die Eingabe von null als ersten Parameter beim kürzen von Strings eine Exception wirft.
     *
     * @author Felix Duerrwanger
     */
    @Test(expected = IllegalArgumentException.class)
    public void testeErstenParameterAufExceptionBeiNull() {
        StringUtils.strip(null, "123ABCabc");
    }

    /**
     * Testet, ob die Eingabe von null als zweiten Parameter beim Kürzen von Strings eine Exception wirft.
     *
     * @author Felix Duerrwanger
     */
    @Test(expected = IllegalArgumentException.class)
    public void testeZweitenParameterAufExceptionBeiNull() {
        StringUtils.strip("123ABCabc", null);
    }

    /**
     * Testet, ob die Passwörter richtig auf ihre Sicherheit überprüft werden.
     *
     * @author Felix Duerrwanger
     */
    @Test
    public void testePasswortSicherheit() {
        assertFalse("Null sollte ein unsicheres Passwort sein", StringUtils.isSecure(null));
        assertFalse("Der Leerstring ist zu kurz und sollte ein unsicheres Passwort sein", StringUtils.isSecure(""));
        assertFalse("Ein Password der Länge 19 ist zu kurz und sollte nicht sicher sein", StringUtils.isSecure("0123456789abcABC#%&"));
        assertFalse("Ein Password ohne Zahl sollte unsicher sein", StringUtils.isSecure("abcdefghijABCDEFGHIJ#%&"));
        assertFalse("Ein Password ohne Kleibuchstabe sollte unsicher sein", StringUtils.isSecure("1234567890ABCDEFGHIJ#%&"));
        assertFalse("Ein Password ohne Großbuchstabe sollte unsicher sein", StringUtils.isSecure("1234567890abcdefghij#%&"));
        assertFalse("Ein Password ohne Sonderzeichen sollte unsicher sein", StringUtils.isSecure("abcdefghijABCDEFGHI123"));
        assertFalse("Ein Password mit 9 verschiedenen Zeichen sollte unsicher sein", StringUtils.isSecure("12abAB#%&&&&&&&&&&&&"));
        assertTrue("Ein Password, das den Anforderungen entspricht, sollte sicher sein", StringUtils.isSecure("123456abcdefABCDEF#%&"));
    }

    /**
     * Testet, ob ISBN-13-Nummern auch mit Leerzeichen und Bidnestrichen richtig erkannt werden.
     *
     * @author Felix Duerrwanger
     */
    @Test
    public void testeIsbn13MitLeerzeichenUndBindestrichen() {
        assertTrue("Die ISBN-13 sollte mit Leerzeichen am Anfang gültig sein", StringUtils.isValidIsbn13(" 9780321356680"));
        assertTrue("Die ISBN-13 sollte mit Leerzeichen am Ende gültig sein", StringUtils.isValidIsbn13("9780321356680 "));
        assertTrue("Die ISBN-13 sollte mit Bindestrich am Anfang gültig sein", StringUtils.isValidIsbn13("-9780321356680"));
        assertTrue("Die ISBN-13 sollte mit Bindestrich am Ende gültig sein", StringUtils.isValidIsbn13("9780321356680-"));
        assertTrue("Die ISBN-13 sollte mit Leerzeichen an beliebiger Stelle gültig sein", StringUtils.isValidIsbn13("9 7 8 0 3 2 1 3 5 6 6 8 0"));
        assertTrue("Die ISBN-13 sollte mit Bindestrichen an beliebiger Stelle gültig sein", StringUtils.isValidIsbn13("9-7-8-0-3-2-1-3-5-6-6-8-0"));
    }

    /**
     * Testet, ob ISBN-13-Nummern mit falschen Zeichen als ungültig erkannt werden.
     *
     * @author Felix Duerrwanger
     */
    @Test
    public void testeIsbn13MitFalschenZeichen() {
        assertFalse("Die ISBN-13 darf keine Buchstaben enthalten", StringUtils.isValidIsbn13("978A321356680"));
        assertFalse("Die ISBN-13 darf keine Sonderzeichen enthalten", StringUtils.isValidIsbn13("978%321356680"));
    }

    /**
     * Testet, ob ISBN-13-Nummern mit falscher Länge als ungültig erkannt werden.
     *
     * @author Felix Duerrwanger
     */
    @Test
    public void testeIsbnDreizehnMitFalscherLaenge() {
        assertFalse("Die ISBN-13 darf nicht leer sein", StringUtils.isValidIsbn13(""));
        assertFalse("Die ISBN-13 darf nicht null sein", StringUtils.isValidIsbn13(null));
        assertFalse("Die ISBN-13 darf nicht zwölfstellig sein", StringUtils.isValidIsbn13("978032135668"));
        assertFalse("Die ISBN-13 darf nicht vierzehnstellig sein", StringUtils.isValidIsbn13("97803213566801"));
    }

    /**
     * Testet, ob ISBN-13-Nummern nur mit der richtigen Prüfsumme als gültig erkannt werden.
     *
     * @author Felix Duerrwanger
     */
    @Test
    public void testeIsbn13MitFalscherPruefsumme() {

        assertTrue("Die letzte Stelle der ISBN-13 muss mit der Prüfsumme übereinstimmen", StringUtils.isValidIsbn13("9780321356680"));

        String isbnTrunk = "978032135668";

        // Alle möglichen Prüfziffern testen
        for (int i = 9; i > 0; i--) {
            assertFalse("Die letzte Stelle der ISBN-13 muss mit der Prüfsumme übereinstimmen", StringUtils.isValidIsbn13(isbnTrunk + i));
        }
    }
}


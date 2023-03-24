package validador;


public class ValidadorCharsUTF8GPT {

    private static final char MIN_BASIC_LATIN = 0x20;
    private static final char MAX_BASIC_LATIN = 0x7E;
    private static final char MIN_LATIN_SUPPLEMENT = 0xA0;
    private static final char MAX_LATIN_SUPPLEMENT = 0xFF;
    private static final char TAB = 0x09;
    private static final char LF = 0x0A;
    private static final char CR = 0x0D;
    private static final char NEL = 0x85;
    private static final char[] VALID_EXTRA_CHARS = {TAB, LF, CR, NEL};
    private static final int CHAR_TABLE_SIZE = 256;

    public void validateXmlContent(byte[] xmlBytes, int length) {
        char[] xmlChars = new char[length];
        for (int i = 0; i < length; i++) {
            xmlChars[i] = (char) (xmlBytes[i] & 0xFF);
        }
        validateXmlContent(xmlChars, length);
    }


    public void validateXmlContent(char[] xmlChars, int length) {
        for (int i = 0; i < length; i++) {
            char xmlChar = xmlChars[i];
            if (!isValidXmlChar(xmlChar)) {
                throw new RuntimeException("Invalid character at position " + (i + 1));
            }
        }
    }
    private static final boolean[] VALID_CHARS = new boolean[CHAR_TABLE_SIZE];

    static {
        for (char i = MIN_BASIC_LATIN; i <= MAX_BASIC_LATIN; i++) {
            VALID_CHARS[i] = true;
        }
        for (char i = MIN_LATIN_SUPPLEMENT; i <= MAX_LATIN_SUPPLEMENT; i++) {
            VALID_CHARS[i] = true;
        }
        for (char i : VALID_EXTRA_CHARS) {
            VALID_CHARS[i] = true;
        }
    }
    private static boolean isValidXmlChar(char xmlChar) {
        return xmlChar < CHAR_TABLE_SIZE && VALID_CHARS[xmlChar];
    }
}
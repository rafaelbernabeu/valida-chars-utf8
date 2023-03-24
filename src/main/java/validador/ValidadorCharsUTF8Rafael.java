package validador;


public class ValidadorCharsUTF8Rafael {

    private static final char[] CHAR_VALIDOS_INTERVALO_BASIC_LATIN = {0x20, 0x7E};
    private static final char[] CHAR_VALIDOS_INTERVALO_LATIN_SUPPLEMENT = {0xA0, 0xFF};
    private static final char[] CHAR_VALIDOS_EXTRAS = {0x09, 0x0A, 0x0D, 0x85};


    public void validaCharsConteudo(char[] xmlChars) {
        for (int i = 0; i < xmlChars.length; i++) {
            char xmlChar = xmlChars[i];
            if (!(validaCharsBasicLatin(xmlChar) || validaCharsLatinSupplement(xmlChar) || validaCharsExtras(xmlChar))) {
                throw new RuntimeException("Posição do caractere: " + (++i));
            }
        }
    }


    private boolean validaCharsLatinSupplement(char b) {
        return b >= CHAR_VALIDOS_INTERVALO_BASIC_LATIN[0] && b <= CHAR_VALIDOS_INTERVALO_BASIC_LATIN[1];
    }


    private boolean validaCharsBasicLatin(char b) {
        return b >= CHAR_VALIDOS_INTERVALO_LATIN_SUPPLEMENT[0] && b <= CHAR_VALIDOS_INTERVALO_LATIN_SUPPLEMENT[1];
    }


    private boolean validaCharsExtras(char b) {
        return b == CHAR_VALIDOS_EXTRAS[0] || b == CHAR_VALIDOS_EXTRAS[1] || b == CHAR_VALIDOS_EXTRAS[2] || b == CHAR_VALIDOS_EXTRAS[3];
    }
}
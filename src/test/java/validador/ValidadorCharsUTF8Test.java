package validador;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class ValidadorCharsUTF8Test {

    private static final char MIN_BASIC_LATIN = 0x20;
    private static final char MAX_BASIC_LATIN = 0x7E;
    private static final char MIN_LATIN_SUPPLEMENT = 0xA0;
    private static final char MAX_LATIN_SUPPLEMENT = 0xFF;
    private static final char TAB = 0x09;
    private static final char LF = 0x0A;
    private static final char CR = 0x0D;
    private static final char NEL = 0x85;

    private static Stream<Character> basicLatin = Stream.iterate(MIN_BASIC_LATIN, c -> (char) (c + 1)).limit(MAX_BASIC_LATIN + 1);
    private static Stream<Character> latinSupplement = Stream.iterate(MIN_LATIN_SUPPLEMENT, c -> (char) (c + 1)).limit(MAX_LATIN_SUPPLEMENT + 1);
    private static Stream<Character> extraChars = Stream.of(TAB, LF, CR, NEL);
    private static List<Character> todosValidos = Stream.of(basicLatin, latinSupplement, extraChars).flatMap(Function.identity()).toList();
    private static List<Character> todosNAOValidos = Stream.iterate((char) 0x00, n -> (char) (n + 1)).limit(0xFFFF).filter(c -> !todosValidos.contains(c)).toList();

    private static ValidadorCharsUTF8Rafael validadorIcom = new ValidadorCharsUTF8Rafael();
    private static ValidadorCharsUTF8GPT validadorGpt = new ValidadorCharsUTF8GPT();

    @Test
    public void testeDeVelocidade() {
        char[] charNaoValidos = new char[todosNAOValidos.size()];
        int i = 0;
        for (var naoValido : todosNAOValidos) {
            charNaoValidos[i++] = naoValido;
        }

        Assert.assertThrows(RuntimeException.class, () -> {
            for (int j = 0; j < 10_000; j++) {
                validadorIcom.validaCharsConteudo(charNaoValidos);
            }
        });

        long t0 = System.nanoTime();
        try {
            validadorIcom.validaCharsConteudo(charNaoValidos);
        } catch (Exception e) {}
        System.out.println("ICOM Demorou " + (System.nanoTime() - t0) + "ns");





        Assert.assertThrows(RuntimeException.class, () -> {
            for (int j = 0; j < 10_000; j++) {
                validadorGpt.validateXmlContent(charNaoValidos, charNaoValidos.length);
            }
        });

        t0 = System.nanoTime();
        try {
            validadorGpt.validateXmlContent(charNaoValidos, charNaoValidos.length);
        } catch (Exception e) {}
        System.out.println("GPT Demorou  " + (System.nanoTime() - t0) + "ns");
    }
}

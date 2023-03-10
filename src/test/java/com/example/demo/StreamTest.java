package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "test")
public class StreamTest {

    private char returnMaxChar(String text) {

        if (text == null) {
            throw new IllegalArgumentException("Tekst nie może być null");
        }

        if (text.isEmpty()) {
            throw new IllegalArgumentException("Tekst nie może być pusty");
        }


        return text.chars()
                .mapToObj(c -> (char) c)
                //zamiana stringa na listę znaków
//                .collect(Collectors.toList())
//                .stream()
                //zamiana na mapę <znak, liczbaWystąpień>
                .collect(Collectors.groupingBy(n -> n, Collectors.counting()))
                //zamiana na set
                .entrySet()
                .stream()
                //para <key, maxVal>
                .max(Map.Entry.comparingByValue())
                .map(entry -> entry.getKey())
                .orElseThrow();

//        char character = maxEntry.get().getKey();
//        Long nr = maxEntry.get().getValue();

//        System.out.println("Dla Stringa " + text + " najczęściej występujący znak to '" + character + "' który wsytąpił " + nr + " razy.");


    }


    @Test
    public void shouldReturnCharWhenString() {
        //given
        String text1 = "SomeLongStringForTestingAStream";
        String text2 = "s";
        String text3 = "32156vgfc^%$&^^";
        String text4 = " a b c d e ";
        //when
        char ch1 = returnMaxChar(text1);
        char ch2 = returnMaxChar(text2);
        char ch3 = returnMaxChar(text3);
        char ch4 = returnMaxChar(text4);
        //then
        assertEquals('e', ch1);
        assertEquals('s', ch2);
        assertEquals('^', ch3);
        assertEquals(' ', ch4);
    }

    @ParameterizedTest
    @MethodSource("provideStringsForTest")
    public void shouldReturnMaxChar(String text, char ch) {
        // when
        assertEquals(ch, returnMaxChar(text));
    }

    private static Stream<Arguments> provideStringsForTest() {
        return Stream.of(
                Arguments.of("SomeLongStringForTestingAStream", 'e'),
                Arguments.of("s", 's'),
                Arguments.of("32156vgfc^%$&^^", '^'),
                Arguments.of(" a b c d e ", " ")
        );
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    public void shouldThrowExceptionWhenNull(String text) {
        // then
        assertThrows(IllegalArgumentException.class, () -> returnMaxChar(text));
    }

//    @ParameterizedTest
//    public void shouldThrowExceptionWhenEmpty(String text) {
//        assertThrows(IllegalArgumentException.class, () -> returnMaxChar(text));
//    }



//    @Test
//    public void shouldReturnCharWhenNotString() {
//        //given
//        int text1 = 5;
//        char text2 = 'd';
//        boolean text3 = true;
//        float[] text4 = {1.0f, 2.5f, 3.14f};
//
//        //when
////        char ch1 = returnMaxChar(text1);
////        char ch2 = returnMaxChar(text2);
////        char ch3 = returnMaxChar(text3);
////        char ch4 = returnMaxChar(text4);
//        //when then
//        assertThrows(IllegalArgumentException.class, () -> returnMaxChar(null));
////        assertEquals('e', ch1);
////        assertEquals('s', ch2);
////        assertEquals('^', ch3);
////        assertEquals(' ', ch4);
//    }
}


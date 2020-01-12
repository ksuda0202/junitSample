package test.java.example;

import main.java.example.Calculator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.time.LocalDate;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TestSample {

    @BeforeAll
    void initAll() {
        // 実施前に処理があれば書く
    }

    @BeforeEach
    void init() {
        // 各テスト実施前に共通的な処理があれば書く
    }

    @Test
    void test() {
        Throwable exception = assertThrows(IOException.class, () -> {
            throw new IOException("message");
        });
        assertEquals("message", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("japaneseDateProvider")
    void japaneseEra(JapaneseDate date, String expected) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("G").withLocale(Locale.JAPAN);
        assertEquals(expected, formatter.format(date));
    }

    static Stream<Arguments> japaneseDateProvider() {
        return Stream.of(
                arguments(JapaneseDate.of(1989, 1, 7),  "昭和"),
                arguments(JapaneseDate.of(1989, 1, 8),  "平成"),
                arguments(JapaneseDate.of(2019, 4, 30), "平成")
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"2016-01-01", "2020-01-01", "2020-12-31"})
    void leapYear(LocalDate date) {
        assertTrue(date.isLeapYear());
    }

    @ParameterizedTest
    @ValueSource(strings = {"first", "second", "third"})
    /*
     * @ValueSource(bytes, ints, booleans, classes, etc.)
     */
    void paramTest(String str) {
        System.out.println(str);
    }

    @ParameterizedTest
    @CsvSource({
            "1, 2",
            "3, 4",
            "5, 6"
    })
    void csvParamTest(int first, int second) {
        System.out.println(first + "+" + second + "=" + Calculator.add(first, second));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "test.csv", numLinesToSkip = 1)
    void csvFileTest(int first, int second) {
        assertNotEquals(0, Calculator.add(first, second));
    }

    @AfterEach
    void tearDown() {
        // 各テスト実施後に共通的な処理があれば書く
    }

    @AfterAll
    void tearDownAll() {
        // 実施後に処理があれば書く
    }
}

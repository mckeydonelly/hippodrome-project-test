package com.javarush.hippodrome;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    public static final String NAME_CANNOT_BE_BLANK = "Name cannot be blank.";
    public static final String NAME_CANNOT_BE_NULL = "Name cannot be null.";
    public static final String SPEED_CANNOT_BE_NEGATIVE = "Speed cannot be negative.";
    public static final String DISTANCE_CANNOT_BE_NEGATIVE = "Distance cannot be negative.";
    public static final String HORSE_NAME = "Horse";

    @Test
    void should_exception_when_name_is_null() {
        Exception exceptionNull = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 5.2));
        assertEquals(NAME_CANNOT_BE_NULL, exceptionNull.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "\t", "\n"})
    void should_exception_when_name_is_empty(String name) {
        Exception exceptionEmpty = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 5.2));
        assertEquals(NAME_CANNOT_BE_BLANK, exceptionEmpty.getMessage());
    }

    @Test
    void should_exception_when_speed_is_negative_number() {
        Exception exceptionNegative = assertThrows(IllegalArgumentException.class, () -> new Horse(HORSE_NAME, -5.2));
        assertEquals(SPEED_CANNOT_BE_NEGATIVE, exceptionNegative.getMessage());
    }

    @Test
    void should_exception_when_distance_is_negative_number() {
        Exception exceptionNegative = assertThrows(IllegalArgumentException.class, () -> new Horse(HORSE_NAME, 5.2, -100.00));
        assertEquals(DISTANCE_CANNOT_BE_NEGATIVE, exceptionNegative.getMessage());
    }

    @Test
    void should_return_name() {
        Horse horse = new Horse(HORSE_NAME, 5.00);
        assertEquals(HORSE_NAME, horse.getName());
    }

    @Test
    void should_return_speed() {
        Horse horse = new Horse(HORSE_NAME, 5.00);
        assertEquals(5.00, horse.getSpeed());
    }

    @Test
    void should_return_distance() {
        Horse horse = new Horse(HORSE_NAME, 5.00, 10.00);
        assertEquals(10.00, horse.getDistance());
    }

    @Test
    void should_return_zero_distance_by_default() {
        Horse horse = new Horse(HORSE_NAME, 5.00);
        assertEquals(0, horse.getDistance());
    }

    @Test
    void should_invoke_getRandomDouble() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            new Horse(HORSE_NAME, 5.00).move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 3, 5, 10.4, 15})
    void should_return_correct_response_getRandomDouble(double random) {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            Horse horse = new Horse(HORSE_NAME, 5, 10);
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            horse.move();

            assertEquals(10 + 5 * random, horse.getDistance());
        }
    }

}
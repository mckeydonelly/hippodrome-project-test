package com.javarush.hippodrome;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    @Test
    void should_exception_when_first_param_constructor_null() {
        Exception exceptionNull = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 5.2));
        assertEquals("Name cannot be null.", exceptionNull.getMessage());

        Exception exceptionEmpty = assertThrows(IllegalArgumentException.class, () -> new Horse("", 5.2));
        assertEquals("Name cannot be blank.", exceptionEmpty.getMessage());

        Exception exceptionSpace = assertThrows(IllegalArgumentException.class, () -> new Horse(" ", 5.2));
        assertEquals("Name cannot be blank.", exceptionSpace.getMessage());

        Exception exceptionTab = assertThrows(IllegalArgumentException.class, () -> new Horse("  ", 5.2));
        assertEquals("Name cannot be blank.", exceptionTab.getMessage());
    }

    @Test
    void should_exception_when_second_param_constructor_is_negative_number() {
        Exception exceptionNegative = assertThrows(IllegalArgumentException.class, () -> new Horse("Horse", -5.2));
        assertEquals("Speed cannot be negative.", exceptionNegative.getMessage());
    }

    @Test
    void should_exception_when_third_param_constructor_is_negative_number() {
        Exception exceptionNegative = assertThrows(IllegalArgumentException.class, () -> new Horse("Horse", 5.2, -100.00));
        assertEquals("Distance cannot be negative.", exceptionNegative.getMessage());
    }

    @Test
    void should_return_name_getName() {
        Horse horse = new Horse("Horse", 5.00);
        assertEquals("Horse", horse.getName());
    }

    @Test
    void should_return_speed_getSpeed() {
        Horse horse = new Horse("Horse", 5.00);
        assertEquals(5.00, horse.getSpeed());
    }

    @Test
    void should_return_distance_getDistance() {
        Horse horse = new Horse("Horse", 5.00, 10.00);
        assertEquals(10.00, horse.getDistance());
    }

    @Test
    void should_invoke_getRandomDouble() {
        Horse horse = new Horse("Horse", 5.00);

        try(MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(anyDouble(), anyDouble()));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "5.00",
            "10.00",
            "15.00"
    })
    void should_return_correct_response_getRandomDouble(double getRandomDoubleReturn) {
        try(MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(anyDouble(), anyDouble())).thenReturn(getRandomDoubleReturn);
            assertEquals(Horse.getRandomDouble(anyDouble(), anyDouble()), getRandomDoubleReturn);
        }
    }

}
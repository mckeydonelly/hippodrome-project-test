package com.javarush.hippodrome;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    public static final String HORSES_LIST_CANNOT_BE_NULL = "Horses cannot be null.";
    public static final String HORSES_LIST_CANNOT_BE_EMPTY = "Horses cannot be empty.";

    @Test
    void should_exception_when_list_of_horses_is_null() {
        Exception exceptionNull = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals(HORSES_LIST_CANNOT_BE_NULL, exceptionNull.getMessage());

        Exception exceptionEmpty = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        assertEquals(HORSES_LIST_CANNOT_BE_EMPTY, exceptionEmpty.getMessage());
    }

    @Test
    void should_return_initial_list_of_horses() {
        List<Horse> horseList = new ArrayList<>();
        IntStream.range(0, 30).forEach(value -> horseList.add(new Horse(String.valueOf(value), value)));

        Hippodrome hippodrome = new Hippodrome(horseList);
        assertEquals(horseList, hippodrome.getHorses());
    }

    @Test
    void should_run_move_for_every_horse() {
        List<Horse> horseList = new ArrayList<>();
        IntStream.range(0, 50).forEach(value -> horseList.add(mock(Horse.class)));

        new Hippodrome(horseList).move();

        for (Horse horse : horseList) {
            verify(horse).move();
        }
    }

    @Test
    void should_return_horse_with_max_distance() {
        List<Horse> horseList = new ArrayList<>();
        IntStream.range(0, 50).forEach(value -> horseList.add(new Horse(String.valueOf(value), value)));

        Horse winnerHorse = horseList.stream()
                .max(Comparator.comparing(Horse::getDistance))
                .orElseThrow();

        Hippodrome hippodrome = new Hippodrome(horseList);

        assertEquals(winnerHorse, hippodrome.getWinner());
    }
}
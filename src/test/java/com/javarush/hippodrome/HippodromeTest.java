package com.javarush.hippodrome;

import org.apache.logging.log4j.core.util.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    @Test
    void should_exception_when_first_param_constructor_null() {
        Exception exceptionNull = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exceptionNull.getMessage());

        List<Horse> horseList = new ArrayList<>();
        Exception exceptionEmpty = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horseList));
        assertEquals("Horses cannot be empty.", exceptionEmpty.getMessage());
    }

    @Test
    void should_return_the_same_list() {
        List<Horse> horseList = new ArrayList<>();
        IntStream.range(0, 30).forEach(value -> horseList.add(new Horse(String.valueOf(value), value)));

        Hippodrome hippodrome = new Hippodrome(horseList);
        assertEquals(horseList, hippodrome.getHorses());
    }

    @Test
    void should_run_move_for_every_horse() {
        List<Horse> horseList = new ArrayList<>();
        IntStream.range(0, 50).forEach(value -> horseList.add(Mockito.spy(new Horse(String.valueOf(value), value))));

        Hippodrome hippodrome = new Hippodrome(horseList);
        hippodrome.move();

        for (Horse horse : horseList) {
            Mockito.verify(horse, Mockito.atLeastOnce()).move();
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
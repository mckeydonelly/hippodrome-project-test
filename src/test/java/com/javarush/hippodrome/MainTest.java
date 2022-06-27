package com.javarush.hippodrome;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

class MainTest {

    @Test
    @Timeout(value = 22)
    void should_complete_not_above_22_seconds() {
        try {
            Main.main(new String[0]);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
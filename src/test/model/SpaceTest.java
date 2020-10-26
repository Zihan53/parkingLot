package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpaceTest {

    Space space;

    @BeforeEach
    void runBefore() {
        space = new Space(5);
    }

    @Test
    void testConstructor() {
        assertEquals(5, space.getNum());
        assertTrue(space.getIsVacancy());
    }
}
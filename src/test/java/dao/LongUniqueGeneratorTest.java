package dao;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LongUniqueGeneratorTest {

    private LongUniqueGenerator sut;

    @Before
    public void setUp() throws Exception {
        sut = new LongUniqueGenerator();
    }

    @Test
    public void getNext() {
        assertEquals(1, (long) sut.getNext());
        assertEquals(2, (long) sut.getNext());
    }
}
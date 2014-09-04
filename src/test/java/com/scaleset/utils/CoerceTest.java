package com.scaleset.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class CoerceTest extends Assert {

    @Test
    public void testNumbers() {
        int num = Coerce.toInteger("5");
        assertEquals(5, num);
        double fNum = Coerce.toDouble("5");
        assertEquals(5.0, num, 0.00001);

        // check null
        Integer n = Coerce.toInteger("");
        assertEquals(null, n);

    }

    @Test
    public void testDates() {
        Date date = Coerce.toDate("2010-01-05");
        assertNotNull(date);
    }

    @Test
    public void testStrings() {
        assertEquals("s1", Coerce.toString("s1"));
        assertEquals("1", Coerce.toString(1));
        String sd = Coerce.toString(new Date());
        assertNotNull(sd);
    }

}


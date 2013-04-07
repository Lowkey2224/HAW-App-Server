package com.hawserver.mensaplan.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateMidnight;
import org.junit.Before;
import org.junit.Test;

public class MenuParserTest {

    /*
     * @TODO: Create test for whole parsing  
     */
    
    private MenuParser parser;
    
    @Before
    public void setUp() throws Exception {
        parser=new MenuParser();
    }

    @Test
    public void testGetPricesFromString() {
        List<Integer> expected=new ArrayList<Integer>();
        assertEquals(expected,parser.getPricesFromString("1,85 €"));
        expected.add(330);
        expected.add(390);
        assertEquals(expected,parser.getPricesFromString("3,30 € / 3,90 €"));
    }
    
    @Test
    public void testGetDaysForWeek(){
        List<DateMidnight> expected=new ArrayList<DateMidnight>();
        DateMidnight firstDayOfWeekOne=new DateMidnight(2012, 12, 31);
        for (int i = 0; i <= 5; i++) {
            expected.add(firstDayOfWeekOne.plusDays(i));
        }
        assertEquals(expected,parser.getDaysForWeek(1));
    }

}

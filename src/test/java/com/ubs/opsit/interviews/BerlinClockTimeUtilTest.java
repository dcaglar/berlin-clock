package com.ubs.opsit.interviews;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;


/*
 * Developed by Dogan Caglar in 04.09.2014
 */
public class BerlinClockTimeUtilTest {
	
	BerlinClockTimeUtils timeUtils;
	
	@Before
	public void setUp() {
		timeUtils = new BerlinClockTimeUtils();
	}

    @Test(expected=IllegalArgumentException.class)
    public void throwExceptionWhenTimeIsNull() {
    	timeUtils.convertTime(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void throwExceptionWhenTimeIsEmpty() {
    	timeUtils.convertTime("");
    }
    
    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void throwExceptionWhenFormatIsInvalid() {
    	timeUtils.fetchArrayFromStandardTime("33");
    }
    
    @Test(expected=NumberFormatException.class)
    public void shouldThrowExceptionWhenTimeIsNotInNumberFormat() {
    	timeUtils.fetchArrayFromStandardTime("aa:aa:aa");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void throwExceptionWhenHourIsGreaterThan24() {
    	timeUtils.fetchArrayFromStandardTime("43:12:01");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void throwExceptionWhenMinuteIsGreaterThan59() {
    	timeUtils.fetchArrayFromStandardTime("12:71:21");
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void throwExceptionWhenSecondIsGreaterThan59() {
    	timeUtils.fetchArrayFromStandardTime("22:02:91");
    }
    
    @Test
    public void shouldReturnCorrectIntArrayWhenCorrectTime() {
    	int[] timeIntArr = timeUtils.fetchArrayFromStandardTime("11:22:33");
    	assertEquals(timeIntArr[0], 11);
    	assertEquals(timeIntArr[1], 22);
    	assertEquals(timeIntArr[2], 33);
    }
    
    @Test
    public void shouldBeTurnedOnYellowLampOnTopWhenSecondsIsZero(){
    	assertEquals(BerlinClockTimeUtils.YELLOW_LAMP_SYMBOL, timeUtils.generateSecondPart(0));
    }

    @Test
    public void shouldBeTurnedOnYellowLampOnTopWhenSecondIsEven(){
    	assertEquals(BerlinClockTimeUtils.YELLOW_LAMP_SYMBOL, timeUtils.generateSecondPart(10));
    	assertEquals(BerlinClockTimeUtils.YELLOW_LAMP_SYMBOL, timeUtils.generateSecondPart(24));
    	assertEquals(BerlinClockTimeUtils.YELLOW_LAMP_SYMBOL, timeUtils.generateSecondPart(56));

    }
    
    @Test
    public void shouldBeTurnedOffYellowLampOnTopWhenSecondIsOdd(){
    	assertEquals(BerlinClockTimeUtils.CLOSED_LAMP_SYMBOL, timeUtils.generateSecondPart(11));
    	assertEquals(BerlinClockTimeUtils.CLOSED_LAMP_SYMBOL, timeUtils.generateSecondPart(25));
    	assertEquals(BerlinClockTimeUtils.CLOSED_LAMP_SYMBOL, timeUtils.generateSecondPart(57));

    }
    
    @Test
    public void shouldBeFourTurnOffFirstHourRowWhenHourIsLessThanFive(){
    	assertEquals("OOOO", timeUtils.generateFirstHourPart(0));
    	assertEquals("OOOO", timeUtils.generateFirstHourPart(1));
    	assertEquals("OOOO", timeUtils.generateFirstHourPart(2));
    	assertEquals("OOOO", timeUtils.generateFirstHourPart(3));
    	assertEquals("OOOO", timeUtils.generateFirstHourPart(4));

    }
    
    
    @Test
    public void shouldBeRedFirstHourRowWhenHourIsFivefold(){
    	assertEquals("ROOO", timeUtils.generateFirstHourPart(5));
    	assertEquals("RROO", timeUtils.generateFirstHourPart(10));
    	assertEquals("RRRO", timeUtils.generateFirstHourPart(15));
    	assertEquals("RRRR", timeUtils.generateFirstHourPart(20));

    }
    
    @Test
    public void shouldBeTwoRedOnSecondHourRowWhenRemainingHourIsTwo(){
    	assertEquals("RROO", timeUtils.generateSecondHourPart(2));

    }
    @Test
    public void shouldBeThreeRedOnSecondHourRowWhenRemainingHourIsThree(){
    	assertEquals("RRRO", timeUtils.generateSecondHourPart(3));

    }
    @Test
    public void shouldBeFourRedOnSecondHourRowWhenRemainingHourIsOne(){
    	assertEquals("RRRR", timeUtils.generateSecondHourPart(4));

    }
    
    
    @Test
    public void shouldBeFourTurnOffFirstMinuteRowWhenMinuteIsLessThanFive(){
    	assertEquals("OOOOOOOOOOO", timeUtils.generateFirstMinutePart(0));
    	assertEquals("OOOOOOOOOOO", timeUtils.generateFirstMinutePart(1));
    	assertEquals("OOOOOOOOOOO", timeUtils.generateFirstMinutePart(2));
    	assertEquals("OOOOOOOOOOO", timeUtils.generateFirstMinutePart(3));
    	assertEquals("OOOOOOOOOOO", timeUtils.generateFirstMinutePart(4));

    }
    
    @Test
    public void shouldBeTurnedOnFirstMinuteRowWhenMinuteIsThreeFold(){
    	assertEquals("YYROOOOOOOO", timeUtils.generateFirstMinutePart(15));
    	assertEquals("YYRYYROOOOO", timeUtils.generateFirstMinutePart(30));
    	assertEquals("YYRYYRYYROO", timeUtils.generateFirstMinutePart(45));


    }
    
    @Test
    public void shouldBeTurnedOnFirstMinuteRowWhenMinuteIsFiveFold(){
    	assertEquals("YOOOOOOOOOO", timeUtils.generateFirstMinutePart(5));
    	assertEquals("YYRYYOOOOOO", timeUtils.generateFirstMinutePart(25));
    	assertEquals("YYRYYRYYOOO", timeUtils.generateFirstMinutePart(40));


    }
    
    @Test
    public void shouldBeOneYellowOnSecondMinuteRowWhenRemainingMinuteIsOne(){
    	assertEquals("YOOO", timeUtils.generateSecondMinutePart(1));

    }
  
    @Test
    public void shouldBeTwoYellowOnSecondMinuteRowWhenRemainingMinuteIsTwo(){
    	assertEquals("YYOO", timeUtils.generateSecondMinutePart(2));

    }
    @Test
    public void shouldBeThreeYellowOnSecondMinuteRowWhenRemainingMinuteIsThree(){
    	assertEquals("YYYO", timeUtils.generateSecondMinutePart(3));

    }
    
    @Test
    public void shouldBeFourYellowOnSecondMinuteRowWhenRemainingMinuteIsFour(){
    	assertEquals("YYYY", timeUtils.generateSecondMinutePart(4));

    }
    
    @Test
    public void shouldPassFromAcceptanceTest(){
    	String newLine = BerlinClockTimeUtils.NEW_LINE;
    	String answer = "Y" + newLine + "OOOO" + newLine + "OOOO" + newLine + "OOOOOOOOOOO" + newLine + "OOOO";
    	assertEquals(answer, timeUtils.convertTime("00:00:00"));
    	
    	 answer = "O" + newLine + "RROO" + newLine + "RRRO" + newLine + "YYROOOOOOOO" + newLine + "YYOO";
     	assertEquals(answer, timeUtils.convertTime("13:17:01"));

     	 answer = "O" + newLine + "RRRR" + newLine + "RRRO" + newLine + "YYRYYRYYRYY" + newLine + "YYYY";
     	assertEquals(answer, timeUtils.convertTime("23:59:59"));
     	
     	 answer = "Y" + newLine + "RRRR" + newLine + "RRRR" + newLine + "OOOOOOOOOOO" + newLine + "OOOO";
     	assertEquals(answer, timeUtils.convertTime("24:00:00"));
    }
    
    
    
    
}

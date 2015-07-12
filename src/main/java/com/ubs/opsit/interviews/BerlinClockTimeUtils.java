package com.ubs.opsit.interviews;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BerlinClockTimeUtils implements TimeConverter {

	private static final Logger BerlinLogger = LoggerFactory.getLogger(BerlinClockTimeUtils.class);

	public static final String NEW_LINE = "\r\n";
	private static final String TIME_SEPERATOR = ":";

	
	private static final int HOURS_ARRAY_INDEX = 0;
	private static final int MINUTES_ARRAY_INDEX = 1;
	private static final int SECONDS_ARRAY_INDEX = 2;


	public static final int FIRST_HOURS_ROW_TIME_UNIT = 5; //Every lamp represents 5 hours in first row of hour
	public static final int FIRST_HOURS_ROW_LENGTH = 4;     //There are 4 lamps in first hour or row
	
	public static final int SECOND_HOURS_ROW_TIME_UNIT = 1;     //Every lamp represents 1 hour in 2nd hour of row
	public static final int SECOND_HOURS_ROW_LENGTH = 4;   //There are 4 lamps in 2nd hours of row

	public static final int FIRST_MINUTES_ROW_TIME_UNIT = 5;  //Every lamp represents 5 minutes in first  row of minute
	public static final int FIRST_MINUTES_ROW_LENGTH = 11;     //There are 11 lamps in first row of minute
	public static final int FIRST_MINUTES_ROW_RED_LAMP_MODE = 3;   //3rd 6th and 9th lamps are red in first row of minutes
	
	
	
	public static final int SECOND_MINUTES_ROW_TIME_UNIT = 1;    //Every lamp represents 1 minute in 2n row of minute
	public static final int SECOND_MINUTES_ROW_LENGTH = 4;   //There are 4 lamps in second row of minute
	
	public static final String CLOSED_LAMP_SYMBOL = "O";   //This indicates that lamp is closed
	public static final String RED_LAMP_SYMBOL = "R";      //this indicates that lamp is open and red
	public static final String YELLOW_LAMP_SYMBOL = "Y";   //this indicates that lamp is open and yellow
	
	@Override
	public String convertTime(String standardTime) {  //our main method that transforms standart time to BerlinClock time format
		BerlinLogger.debug("Input standard time is : " + standardTime);
		if (StringUtils.isEmpty(standardTime)) {
			throw new IllegalArgumentException("Input standard time can not be empty");
		}
		
		StringBuilder result = new StringBuilder();
		
		int[] timeArray = fetchArrayFromStandardTime(standardTime);   //we divide standard time into 3 parts(hour,minute,second)  
		BerlinLogger.debug("hour : " + timeArray[0] + "| minute: " + timeArray[1] + " | second: " + timeArray[2]);
		
		result.append(generateSecondPart(timeArray[SECONDS_ARRAY_INDEX]) + NEW_LINE);  //this method prints second(sec) part of clock
		BerlinLogger.debug("result: " + result.toString());
		
		result.append(generateHourPart(timeArray[HOURS_ARRAY_INDEX]) + NEW_LINE); //this method prints the  hour  part of clock
		BerlinLogger.debug("result: " + result.toString());
		
		result.append(generateMinutePart(timeArray[MINUTES_ARRAY_INDEX]));  //this method prints the minute part of clock
		BerlinLogger.debug("result: " + result.toString());
		
		return result.toString();
	}
	
	
	protected int[] fetchArrayFromStandardTime(String standardTime) {  //this methods divide standart string into 3 parts
		int[] timeIntArr = new int[3];  //hour minute and second parts
		
		String[] timeStringArr = standardTime.split(TIME_SEPERATOR);
		
		timeIntArr[BerlinClockTimeUtils.HOURS_ARRAY_INDEX] = Integer.parseInt(timeStringArr[BerlinClockTimeUtils.HOURS_ARRAY_INDEX]);
		timeIntArr[BerlinClockTimeUtils.MINUTES_ARRAY_INDEX] = Integer.parseInt(timeStringArr[BerlinClockTimeUtils.MINUTES_ARRAY_INDEX]);
		timeIntArr[BerlinClockTimeUtils.SECONDS_ARRAY_INDEX] = Integer.parseInt(timeStringArr[BerlinClockTimeUtils.SECONDS_ARRAY_INDEX]);
		
		if(timeIntArr[BerlinClockTimeUtils.HOURS_ARRAY_INDEX] < 0 || timeIntArr[BerlinClockTimeUtils.HOURS_ARRAY_INDEX] > 24)
			throw new IllegalArgumentException("Hour should be between 0 and 24");
		
		if(timeIntArr[BerlinClockTimeUtils.MINUTES_ARRAY_INDEX] < 0 || timeIntArr[BerlinClockTimeUtils.MINUTES_ARRAY_INDEX] > 59)
			throw new IllegalArgumentException("Minutes should be between 0 and 59");
		
		if(timeIntArr[BerlinClockTimeUtils.SECONDS_ARRAY_INDEX] < 0 || timeIntArr[BerlinClockTimeUtils.SECONDS_ARRAY_INDEX] > 59)
			throw new IllegalArgumentException("Seconds should be between 0 and 59");
		
		return timeIntArr;
	}

	
	protected String generateHourPart(int hours) {  //this method returns 2 rows of hour in Berlin Clock Format
		assert(hours <= 24) : "Hour should be less than or equal to 24";
		
		StringBuilder result = new StringBuilder();
		result.append(generateFirstHourPart(hours)+NEW_LINE);
		result.append(generateSecondHourPart(hours%FIRST_HOURS_ROW_TIME_UNIT));
		return result.toString();
	}

	protected String generateFirstHourPart(int hours) {   
		assert(hours <= 24) : "Hour should be less than or equal to 24";
		
		return generateRow(hours, FIRST_HOURS_ROW_TIME_UNIT, FIRST_HOURS_ROW_LENGTH, RED_LAMP_SYMBOL);
	}

	protected String generateSecondHourPart(int hours) {
		assert(hours <= 4) : "Hour should be less than or equal to 4";
		
		return generateRow(hours, SECOND_HOURS_ROW_TIME_UNIT, SECOND_HOURS_ROW_LENGTH, RED_LAMP_SYMBOL);
	}
	
	
	
	protected String generateMinutePart(int minutes) {
		assert(minutes <= 60) : "Minutes should be less than or equal to 60";
		
		StringBuilder result = new StringBuilder();
		result.append(generateFirstMinutePart(minutes) + "\r\n");
		result.append(generateSecondMinutePart(minutes%FIRST_MINUTES_ROW_TIME_UNIT));
		return result.toString();
	}

	protected String generateSecondMinutePart(int minutes) {
		assert(minutes < 5) : "Second minute row should be lower than 5";
		
		return generateRow(minutes, SECOND_MINUTES_ROW_TIME_UNIT, SECOND_MINUTES_ROW_LENGTH, YELLOW_LAMP_SYMBOL);
	}

	protected String generateFirstMinutePart(int minutes) {
		assert(minutes <= 60) : "We have only 60 minutes in a hour";
		
			return generateFirstMinuteRow(minutes);
	}
	
	
	
	protected String generateSecondPart(int seconds) { //if second part is even number then colour should be yellow
		if (seconds %2 == 0) {
			return YELLOW_LAMP_SYMBOL;
		}
		return CLOSED_LAMP_SYMBOL;
	}




	private String generateRow(int number, int timeUnit, int rowLength, String turnOnSymbol) { // generic method to display related part of clock
		StringBuilder result = new StringBuilder();
		
		int counter = 0;
		for (int i = 0; i < rowLength; i++) {
			counter += timeUnit;
			if (counter <=  number) {
				result.append(turnOnSymbol);
			} else {
				result.append(CLOSED_LAMP_SYMBOL);
			}
		}
		
		return result.toString();
	}
	
	private String generateFirstMinuteRow(int minutes) {  //this method execute only when we generate first minute
		StringBuilder result = new StringBuilder();
		
		int counter = 0;
		for (int i = 0; i < FIRST_MINUTES_ROW_LENGTH; i++) {
			counter += FIRST_MINUTES_ROW_TIME_UNIT;
			if ( counter  <= minutes ) {
				if ( ((i+1) % FIRST_MINUTES_ROW_RED_LAMP_MODE) == 0) {
					result.append(RED_LAMP_SYMBOL);
				} else {
					result.append(YELLOW_LAMP_SYMBOL);
				}
			} else {
				result.append(CLOSED_LAMP_SYMBOL);
			}
		}
		
		return result.toString();
	}


	
}

package org.rockholla.date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.rockholla.number.NumberUtility;


/**
 * Helper class for Dates (utilizes Joda date time classes)
 * 
 * @author Patrick Force <patrickforce@gmail.com>
 * @see org.joda.time.DateTime
 *
 */
public class DateUtility 
{

	/**
	 * Formats a datetime
	 * 
	 * @param dateTime	the org.joda.time.DateTime value to format
	 * @param format	the string format instruction
	 * @return			the formatted datetime
	 */
	public static String getFormattedDate(DateTime dateTime, String format) 
	{
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
		return formatter.print(dateTime);
		
	}
	
	/**
	 * Formats the current date time
	 * 
	 * @param format	the string format instruction
	 * @return			the formatted datetime
	 */
	public static String getFormattedDate(String format) 
	{
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
		return formatter.print(new DateTime());
		
	}
	
	/**
	 * Formats a datetime for use in a MySQL datetime field
	 * 
	 * @param dateTime	the org.joda.time.DateTime value to format
	 * @return			the formatted datetime
	 */
	public static String getMySqlDate(DateTime dateTime) 
	{
		return getMySqlDate(dateTime, 0);
	}
	
	/**
	 * Formats a datetime for use in a MySQL datetime field
	 * 
	 * @param dateTime	the org.joda.time.DateTime value to format
	 * @param offset	offset the input datetime by this amount of hours
	 * @return			the formatted datetime
	 */
	public static String getMySqlDate(DateTime dateTime, int offset) 
	{
		
		if(offset > 0 || offset < 0) 
		{
			dateTime = dateTime.plusHours(offset);
		}
		
		String result = 
			String.valueOf(dateTime.getYear()) + "-" +
			NumberUtility.format(dateTime.getMonthOfYear(), NumberUtility.FORMAT.TWO_CHARACTER_INTEGER) + "-" +
			NumberUtility.format(dateTime.getDayOfMonth(), NumberUtility.FORMAT.TWO_CHARACTER_INTEGER) + " " +
			NumberUtility.format(dateTime.getHourOfDay(), NumberUtility.FORMAT.TWO_CHARACTER_INTEGER) + ":" +
			NumberUtility.format(dateTime.getMinuteOfHour(), NumberUtility.FORMAT.TWO_CHARACTER_INTEGER) + ":" +
			NumberUtility.format(dateTime.getSecondOfMinute(), NumberUtility.FORMAT.TWO_CHARACTER_INTEGER);
		
		return result;
		
	}
	
	/**
	 * Determines if the input CharSequence is a valid date representation
	 * 
	 * @param date	a CharSequence
	 * @return		true indicates the input sequence is a valid date
	 */
	public static Boolean isDate(CharSequence date) 
	{

		// some regular expression
		String time = "(\\s(([01]?\\d)|(2[0123]))[:](([012345]\\d)|(60))"
		+ "[:](([012345]\\d)|(60)))?"; // with a space before, zero or one time

		// no check for leap years (Schaltjahr)
		// and 31.02.2006 will also be correct
		String day = "(([12]\\d)|(3[01])|(0?[1-9])|([1-31]))"; // 01 up to 31 or 1 to 31
		String month = "((1[012])|(0\\d)|([1-12]))"; // 01 up to 12 or 1 to 12
		String year = "\\d{4}";

		// define here all date format
		ArrayList<Pattern> patterns = new ArrayList<Pattern>();
		patterns.add(Pattern.compile(day + "[\\-\\.]" + month + "[\\-\\.]" + year + time));
		patterns.add(Pattern.compile(year + "\\-" + month + "\\-" + day + time));
		// here you can add more date formats if you want
		patterns.add(Pattern.compile(day + "\\/" + month + "\\/" + year));

		// check dates
		for(Pattern p : patterns)
		{
			if(p.matcher(date).matches())
			{
				return true;
			}
		}
		return false;

	}
	
	/**
	 * Gets the calendar date from a Julian day of a particular year
	 * 
	 * @param dayOfYear	the julian day
	 * @param year		the year
	 * @return			the org.joda.time.DateTime
	 */
	public static DateTime getDateFromDayOfYear(int dayOfYear, int year) 
	{
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
	    calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
	    return new DateTime(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0, 0);
		
	}
	
	/**
	 * Returns a unique string based on a timestamp (DateTime must be precise to the second
	 * to ensure uniqueness)
	 * 
	 * @param dateTime	the org.joda.time.DateTime to use to create the ID
	 * @return			a unique string representation of the date
	 */
	public static String getTimestampId(DateTime dateTime)
	{
		
		String id = "";
		
		id = 	NumberUtility.format(dateTime.getYear(), NumberUtility.FORMAT.TWO_CHARACTER_INTEGER) + 
				NumberUtility.format(dateTime.getMonthOfYear(), NumberUtility.FORMAT.TWO_CHARACTER_INTEGER) +  
				NumberUtility.format(dateTime.getDayOfMonth(), NumberUtility.FORMAT.TWO_CHARACTER_INTEGER) +  
				NumberUtility.format(dateTime.getHourOfDay(), NumberUtility.FORMAT.TWO_CHARACTER_INTEGER) +
				NumberUtility.format(dateTime.getMinuteOfHour(), NumberUtility.FORMAT.TWO_CHARACTER_INTEGER) +
				NumberUtility.format(dateTime.getSecondOfMinute(), NumberUtility.FORMAT.TWO_CHARACTER_INTEGER) +
				NumberUtility.format(dateTime.getMillisOfSecond(), NumberUtility.FORMAT.TWO_CHARACTER_INTEGER);
		
		return id;
		
	}
	
	/*public static void main(String[] args)
	{
		System.out.println(isDate("12-1-2034"));
	}*/
	
}

/* ====================================================================================
 * FourthDimension : a time and numerical sequential library for the Java(tm) platform
 * ====================================================================================
 *
 * (C) Copyright 2000-2005, by DataGenic Limited and Contributors.
 *
 * Project Info:  http://www.datagenic.co.uk/fourthdimension/index.html
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
 *
 */
package com.datagenic.fourthdimension.dates;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.Period;
import org.joda.time.PeriodType;

public class Daily extends CalendarFrequency {

	private static final long serialVersionUID = 1430177614126208353L;

	private static final int DAYS_IN_WEEK = 7;

	/**
	 * Calculated as hr * min * sec * millisec i.e. 24 * 60 * 60 * 1000
	 */
	private static final int MILLISECONDS_IN_DAY = 86400000;

	/**
	 * @param pattern
	 * @roseuid 415139CB02DE
	 */
	public Daily(final boolean[] pattern) {

		// The pattern array starts from Sunday. This is so that it makes it easy to align the
		// Calendar.get(Calenday.DAY_OF_WEEK)
		// with the array values
		final boolean[] dailyPattern = new boolean[DAYS_IN_WEEK];
		System.arraycopy(pattern, 0, dailyPattern, 0, DAYS_IN_WEEK);
		this.pattern = dailyPattern;

	}

	/**
	 * @roseuid 414708AB007D
	 */
	public Daily() {

	}

	/**
	 * @param index
	 * @return long
	 * @roseuid 41513A14033C
	 */
	@Override
	public long startsOnBefore(final long index) {

		return startsOnBefore(new Date(index)).getTime();
	}

	/**
	 * @param index
	 * @return java.util.Date
	 * @roseuid 4157F20E036B
	 */
	@Override
	public Date startsOnBefore(final Date index) {

		final MutableDateTime dateTime = new MutableDateTime(index, this.getJodaTimezone());
		dateTime.setHourOfDay(0);
		dateTime.setMinuteOfHour(0);
		dateTime.setSecondOfDay(0);
		dateTime.setMillisOfDay(0);

		// Check if there is a pattern involved.
		if (pattern != null) {

			int day = DateHelper.javaDayOfWeek(dateTime.getDayOfWeek());
			// as the Calendar.DAY_OF_WEEK starts at 1, subtract 1 from this value to get the index into the pattern
			// array
			while (!pattern[day - 1]) {
				dateTime.addDays(-1);
				day = DateHelper.javaDayOfWeek(dateTime.getDayOfWeek());
			}

			// index = dateTime.toDate();
		}

		return dateTime.toDate();
	}

	/**
	 * @param index
	 * @return long
	 * @roseuid 41513A3601D4
	 */
	@Override
	public long startsAfter(final long index) {

		return startsAfter(new Date(index)).getTime();
	}

	/**
	 * @param index
	 * @return java.util.Date
	 * @roseuid 4157F227034B
	 */
	@Override
	public Date startsAfter(final Date index) {

		final MutableDateTime dateTime = new MutableDateTime(index, this.getJodaTimezone());
		dateTime.setHourOfDay(0);
		dateTime.setMinuteOfHour(0);
		dateTime.setSecondOfDay(0);
		dateTime.setMillisOfDay(0);

		dateTime.addDays(1);

		// Check if there is a pattern involved.
		if (pattern != null) {
			int day = DateHelper.javaDayOfWeek(dateTime.getDayOfWeek());
			// as the Calendar.DAY_OF_WEEK starts at 1, subtract 1 from this value to get the index into the pattern
			// array
			while (!pattern[day - 1]) {
				dateTime.addDays(1);
				day = DateHelper.javaDayOfWeek(dateTime.getDayOfWeek());
			}
		}

		return dateTime.toDate();
	}

	/**
	 * @param startIndex
	 * @param endIndex
	 * @return long[]
	 * @throws com.datagenic.fourthdimension.dates.IndexAlignmentException
	 * @roseuid 415170A402EE
	 */
	@Override
	public long[] getIndexes(final long startIndex, final long endIndex) throws IndexAlignmentException {

		return getIndexes(new Date(startIndex), new Date(endIndex));
	}

	/**
	 * @param startIndex
	 * @param endIndex
	 * @return long[]
	 * @roseuid 4157F234002E
	 */
	@Override
	public long[] getIndexes(final Date startIndex, final Date endIndex) {

		final DateTime endDate = new DateTime(endIndex, this.getJodaTimezone());

		if (!aligns(startIndex.getTime())) {
			throw new IndexAlignmentException(getDateFormatter().format(startIndex) + " is not an index at "
					+ getCalendarFrequencyName() + " frequency.");
		}

		if (!aligns(endIndex.getTime())) {
			throw new IndexAlignmentException(getDateFormatter().format(endIndex) + " is not an index at "
					+ getCalendarFrequencyName() + " frequency.");
		}

		final MutableDateTime dateTime = new MutableDateTime(startIndex, this.getJodaTimezone());

		long indexesBetween = periodsBetween(startIndex, endIndex);
		if (indexesBetween > Integer.MAX_VALUE) {
			throw new IndexRangeTooLargeException();
		}

		// want to include the end index so add 1 to this value
		indexesBetween += 1;

		final long[] indexes = new long[(int) indexesBetween];

		if (pattern == null) {
			for (int i = 0; i < indexesBetween; i++) {
				indexes[i] = dateTime.getMillis();
				dateTime.addDays(1); // add one day
			}
		} else {
			int indexCount = 0;
			while (!dateTime.isAfter(endDate)) {
				if (pattern[DateHelper.javaDayOfWeek(dateTime.getDayOfWeek()) - 1]) {
					indexes[indexCount++] = dateTime.getMillis();
				}
				dateTime.addDays(1); // add one day
			}
		}

		return indexes;
	}

	/**
	 * @param startIndex
	 * @param endIndex
	 * @return long
	 * @roseuid 4152CCBC006D
	 */
	@Override
	public long periodsBetween(final long startIndex, final long endIndex) {

		return periodsBetween(new Date(startIndex), new Date(endIndex));
	}

	/**
	 * @param startIndex
	 * @param endIndex
	 * @return long
	 * @roseuid 4157F24501B5
	 */
	@Override
	public long periodsBetween(final Date startIndex, final Date endIndex) {

		MutableDateTime startDateTime = new MutableDateTime(startIndex, this.getJodaTimezone());
		startDateTime.setHourOfDay(0);
		startDateTime.setMinuteOfHour(0);
		startDateTime.setSecondOfMinute(0);
		startDateTime.setMillisOfSecond(0);

		MutableDateTime endDateTime = new MutableDateTime(endIndex, this.getJodaTimezone());
		endDateTime.setHourOfDay(0);
		endDateTime.setMinuteOfHour(0);
		endDateTime.setSecondOfMinute(0);
		endDateTime.setMillisOfSecond(0);

		/**
		 * If we are dealing with a calendar that has no pattern, then we can assume that it is a daily calendar.
		 * Standard Joda time calculations can be used.
		 */
		if (pattern == null) {
			final Period period = new Period(startDateTime, endDateTime, PeriodType.days());
			return period.getDays();
		}

		// This was not acceptable when dealing with Daylight saving.
		// The periods between 28Mar2004 00:00:00 GMT and 29Mar2004 00:00:00 BST should have return 1 day
		// however it was returning zero days due to the daylight saving

		final long millisecondsBetween = Math.abs(endDateTime.getMillis() - startDateTime.getMillis());

		final int totalIndexes = (int) (millisecondsBetween / MILLISECONDS_IN_DAY);

		// if totalIndexes is close to the size of the pattern, then it shouldbe just as quick to run through all the
		// patterns
		// between the date ranges. To be honest, there was a bug in the calc for those dates that are less than a
		// pattern
		// length apart, so this should fix it.
		int count = 0;
		if (totalIndexes <= pattern.length) {

			if (startDateTime.isAfter(endDateTime)) {
				final MutableDateTime tmp = startDateTime;
				startDateTime = endDateTime;
				endDateTime = tmp;
			}

			while (startDateTime.isBefore(endDateTime)) {
				if (pattern[DateHelper.javaDayOfWeek(startDateTime.getDayOfWeek()) - 1]) {
					count++;
				}
				startDateTime.addDays(1);
			}

			return count;
		}

		// exclude the last day. Doesn't need to be excluded in non pattern days cause calculation above does it for us
		// simple minus 1 day even though it may be not in the calendar.The pattern will take this into consideration
		// for us.
		endDateTime.addDays(-1);

		// Calculate the number of instances in the pattern that are true
		int truePatternCount = 0;
		for (final boolean element : pattern) {
			if (element) {
				truePatternCount++;
			}
		}

		int remainingStartPattern = 0;
		int remainingEndPattern = 0;

		// As Calendar.DAY_OF_WEEK is offset from 1, then subtract 1 to find the correct index
		// int startPatternIndex = start.get(Calendar.DAY_OF_WEEK);
		final int startPatternIndex = DateHelper.javaDayOfWeek(startDateTime.getDayOfWeek());

		// count the number of true patterns until the end of the pattern array
		for (int i = startPatternIndex; i < pattern.length; i++) {
			if (pattern[i - 1]) {
				remainingStartPattern++;
			}
		}

		// As Calendar.DAY_OF_WEEK is offset from 1, then subtract 1 to find the correct index
		// int endPatternIndex = end.get(Calendar.DAY_OF_WEEK);
		final int endPatternIndex = DateHelper.javaDayOfWeek(endDateTime.getDayOfWeek());
		// count the number of true patterns until the start of the pattern array
		for (int i = endPatternIndex; i > 0; i--) {
			if (pattern[i - 1]) {
				remainingEndPattern++;
			}
		}

		final int indexesBetween = (totalIndexes - (pattern.length - startPatternIndex) - endPatternIndex)
				/ pattern.length * truePatternCount;
		return indexesBetween + remainingStartPattern + remainingEndPattern;

	}

	/**
	 * @return java.lang.String
	 * @roseuid 416505DA007D
	 */
	@Override
	public String getCalendarFrequencyName() {

		return "Daily";
	}

	/**
	 * @param index
	 * @param periods
	 * @return long
	 * @roseuid 42287B3A0222
	 */
	@Override
	public long getPeriodFrom(final long index, int periods) {

		final MutableDateTime startDateTime = new MutableDateTime(index, this.getJodaTimezone());
		startDateTime.setHourOfDay(0);
		startDateTime.setMinuteOfHour(0);
		startDateTime.setSecondOfMinute(0);
		startDateTime.setMillisOfSecond(0);

		if (pattern == null) {

			final MutableDateTime calculatedDateTime = new MutableDateTime(startDateTime.getMillis(),
					this.getJodaTimezone());
			calculatedDateTime.addDays(periods);
			return calculatedDateTime.getMillis();

		} else {

			final int addDays = periods < 0 ? -1 : 1;
			periods = Math.abs(periods);

			for (int i = 0; i < periods;) {

				startDateTime.addDays(addDays);
				final int dayofweek = DateHelper.javaDayOfWeek(startDateTime.getDayOfWeek());

				if (pattern[dayofweek - 1]) {
					i++;
				}
			}

			return startDateTime.getMillis();

		}
	}

	/**
	 * @return Object
	 * @roseuid 425E8B29023D
	 */
	@Override
	public Object clone() {

		final Daily d = new Daily();
		d.setCalendarPattern(this.pattern);

		return d;
	}
}
/**
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * long[] Daily.getIndexes(long,long){ return null; }
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

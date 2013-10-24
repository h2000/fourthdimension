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

public abstract class Intraday extends CalendarFrequency {

	private static final long serialVersionUID = 4594824581796786271L;

	protected long calendarPeriodicity;

	protected int frequency;

	/**
	 * @roseuid 4151523E00AB
	 */
	public Intraday() {

	}

	/**
	 * @param startIndex
	 * @param endIndex
	 * @return long[]
	 * @roseuid 4151705400BB
	 */
	@Override
	public long[] getIndexes(final long startIndex, final long endIndex) {

		if (!aligns(startIndex)) {
			throw new IndexAlignmentException(startIndex + " is not an index at " + getCalendarFrequencyName()
					+ " frequency.");
		}
		if (!aligns(endIndex)) {
			throw new IndexAlignmentException(endIndex + " is not an index at " + getCalendarFrequencyName()
					+ " frequency.");
		}

		long rollingCalendar = startIndex;
		long indexesBetween = periodsBetween(startIndex, endIndex);
		if (indexesBetween > Integer.MAX_VALUE) {
			throw new IndexRangeTooLargeException();
		}

		// want to include the end index so add 1 to this value
		indexesBetween += 1;
		final long[] indexes = new long[(int) indexesBetween];
		for (int i = 0; i < indexesBetween; i++) {
			indexes[i] = rollingCalendar;
			rollingCalendar += calendarPeriodicity;

		}
		return indexes;
	}

	/**
	 * @param startIndex
	 * @param endIndex
	 * @return long[]
	 * @roseuid 41810FB502DE
	 */
	@Override
	public long[] getIndexes(final Date startIndex, final Date endIndex) {

		return getIndexes(startIndex.getTime(), endIndex.getTime());
	}

	/**
	 * @param index
	 * @return long
	 * @roseuid 4151AA6201C5
	 */
	@Override
	public long startsAfter(final long index) {

		long alignedIndex = startsOnBefore(index);
		alignedIndex = alignedIndex + this.calendarPeriodicity;
		return alignedIndex;
	}

	/**
	 * @param index
	 * @return java.util.Date
	 * @roseuid 41810DFF00CB
	 */
	@Override
	public Date startsAfter(final Date index) {

		final long time = startsAfter(index.getTime());
		return new Date(time);
	}

	/**
	 * @param index
	 * @return long
	 * @roseuid 4151AA7303D8
	 */
	@Override
	public long startsOnBefore(final long index) {

		final long milliseconds = millisecondsFromOrigin(index);
		long alignedMillisecondIndex = milliseconds / this.calendarPeriodicity * this.calendarPeriodicity;
		if (alignedMillisecondIndex > milliseconds) {
			alignedMillisecondIndex = alignedMillisecondIndex - this.calendarPeriodicity;
		}

		return alignedMillisecondIndex;
		// return getIntradayOrigin() + alignedMillisecondIndex;
		// return getOrigin().getTime() + alignedMillisecondIndex;
	}

	/**
	 * @param index
	 * @return java.util.Date
	 * @roseuid 41810E2103B9
	 */
	@Override
	public Date startsOnBefore(final Date index) {

		final long time = startsOnBefore(index.getTime());
		return new Date(time);
	}

	/**
	 * @param startIndex
	 * @param endIndex
	 * @return long
	 * @roseuid 415358F00242
	 */
	@Override
	public long periodsBetween(final long startIndex, final long endIndex) {

		if (!aligns(startIndex)) {
			throw new IndexAlignmentException(startIndex + " is not an index at " + getCalendarFrequencyName()
					+ " frequency.");
		}
		if (!aligns(endIndex)) {
			throw new IndexAlignmentException(endIndex + " is not an index at " + getCalendarFrequencyName()
					+ " frequency.");
		}

		final long startOriginOffset = millisecondsFromOrigin(startIndex);
		final long endOriginOffset = millisecondsFromOrigin(endIndex);
		final long delta = endOriginOffset - startOriginOffset;

		if (delta / this.calendarPeriodicity > Integer.MAX_VALUE) {
			throw new IndexRangeTooLargeException();
		} else {
			return (int) (delta / this.calendarPeriodicity);
		}
	}

	/**
	 * @param startIndex
	 * @param endIndex
	 * @return long
	 * @roseuid 41810F5402CE
	 */
	@Override
	public long periodsBetween(final Date startIndex, final Date endIndex) {

		return periodsBetween(startIndex.getTime(), endIndex.getTime());
	}

	/**
	 * @return java.lang.String
	 * @roseuid 416508130177
	 */
	@Override
	public String getCalendarFrequencyName() {

		return "Intraday";
	}

	/**
	 * @return java.util.Date
	 * @roseuid 41810FD601D4
	 */
	protected Date getOrigin() {

		// Calendar origin = Calendar.getInstance(getTimezone());
		// origin.clear();
		// return origin;
		return new Date(0);
	}

	/**
	 * @param index
	 * @return long
	 * @roseuid 41810FFB0177
	 */
	public long millisecondsFromOrigin(final Date index) {

		return millisecondsFromOrigin(index.getTime());
	}

	/**
	 * @param index
	 * @return long
	 * @roseuid 418110120261
	 */
	public long millisecondsFromOrigin(final long index) {

		// As we are dealing in milliseconds, the orgin is 0, therefore simply return the index
		return index;
		// return index - getOrigin().getTime();
	}

	/**
	 * @param index
	 * @param periods
	 * @return long
	 * @roseuid 42287A3B0109
	 */
	@Override
	public long getPeriodFrom(final long index, final int periods) {

		if (!aligns(index)) {
			throw new IndexAlignmentException(index + " is not an index at " + getCalendarFrequencyName()
					+ " frequency.");
		}

		return index + (this.calendarPeriodicity * periods);
	}

	/**
	 * @return Object
	 * @roseuid 425E8B550328
	 */
	@Override
	public Object clone() {

		return null;
	}
}
/**
 * long Intraday.getIntradayOrigin(){ // Calendar origin = Calendar.getInstance(getTimezone()); // origin.clear(); //
 * return origin; return 0; }
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
 *
 *
 *
 * long Intraday.millisecondsFromOrigin(java.util.Calendar){ return millisecondsFromOrigin(cal.getTimeInMillis()); }
 *
 *
 *
 *
 *
 *
 *
 *
 *
 * long[] Intraday.getIndexes(java.util.Calendar,java.util.Calendar){ return null; }
 */

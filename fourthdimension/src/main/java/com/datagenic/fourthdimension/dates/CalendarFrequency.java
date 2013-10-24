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

import java.beans.PropertyVetoException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

import org.joda.time.DateTimeZone;

/**
 * CalendarFrequency is the basis of all supported frequencies in Fourth
 * Dimension. If a new customised frequency is required then it must extend this
 * class.
 * <p>
 * The CalendarFrequency provides the logic required to produce a series at
 * graduated intervals, aligning to a particular frequency.
 */
public abstract class CalendarFrequency implements Serializable {

	private static final long serialVersionUID = 1572646914411889138L;

	protected boolean[] pattern;
	private SimpleDateFormat dateFormatter;
	private TimeZone timezone = TimeZone.getDefault();
	private DateTimeZone jodaTimezone = DateTimeZone.forTimeZone(timezone);;

	// The name of the timezone property
	public static final String TIMEZONE_PROPERTY = "timezone";

	/**
	 * Access method for the dateFormatter property.
	 *
	 * @return the current value of the dateFormatter property
	 */
	public SimpleDateFormat getDateFormatter() {

		if (dateFormatter == null) {
			dateFormatter = new SimpleDateFormat("d MMM yyyy HH:mm:ss z");
			dateFormatter.setTimeZone(getTimezone());
		}

		return dateFormatter;
	}

	/**
	 * Sets the value of the dateFormatter property.
	 *
	 * @param aDateFormatter
	 *            the new value of the dateFormatter property
	 */
	public void setDateFormatter(final SimpleDateFormat aDateFormatter) {
		dateFormatter = aDateFormatter;
	}

	/**
	 * Provides an iterator of period indexes between a start and end index. The
	 * indexes are inclusive. Note. The value returned in a Long value.
	 *
	 * @param startIndex -
	 *            The starting index (inclusive)
	 * @param endIndex -
	 *            The ending index (inclusive)
	 * @return Iterator
	 * @roseuid 4145996900DA
	 */
	public Iterator<Long> iterator(final long startIndex, final long endIndex) {
		final long[] indexes = this.getIndexes(startIndex, endIndex);

		final Iterator<Long> iterator = new Iterator<Long>() {
			int nextPosition = 0;

			/**
			 * remove
			 */
			@Override
			public void remove() {
				throw new UnsupportedOperationException(
						"Elements cannot be removed from this iterator.");
			}

			/**
			 * hasNext
			 *
			 * @return boolean
			 */
			@Override
			public boolean hasNext() {
				return nextPosition < indexes.length;
			}

			/**
			 * next
			 *
			 * @return Object
			 */
			@Override
			public Long next() {
				if (nextPosition >= indexes.length) {
					throw new java.util.NoSuchElementException(nextPosition
							+ " > " + indexes.length);
				}
				final Long value = new Long(indexes[nextPosition++]);
				return value;
			}

		};
		return iterator;
	}

	/**
	 * Provides an index which starts on or before the specified index.
	 * <p>
	 * e.g in a Business frequency, if the index equivalent of Sunday was
	 * provided, it will return the previous Friday. If a Monday was specified,
	 * it will return the Monday.
	 *
	 * @param index -
	 *            The index period
	 * @return long
	 * @roseuid 41459A4002DE
	 */
	public abstract long startsOnBefore(long index);

	/**
	 * Provides an index which starts on or before the specified index.
	 * <p>
	 * e.g in a Business frequency, if the index equivalent of Sunday was
	 * provided, it will return the previous Friday. If a Monday was specified,
	 * it will return the Monday.
	 * <p>
	 * Note: Some frequencies may throw an UnsupportedOperationException in the
	 * event they don't support this operation.
	 *
	 * @param index -
	 *            The period index
	 * @return java.util.Date
	 * @roseuid 4157F4D200FA
	 */
	public Date startsOnBefore(final Date index) {
		throw new UnsupportedOperationException(
				"Calendar indexes are not supported for this frequency.");
	}

	/**
	 * Provides an index which starts after the specified index.
	 * <p>
	 * e.g in a Business frequency, if the index equivalent of Sunday was
	 * provided, it will return the following Monday. If a Monday was specified,
	 * it will return the Tuesday.
	 *
	 * @param index
	 * @return long
	 * @roseuid 41459A69003E
	 */
	public abstract long startsAfter(long index);

	/**
	 * Provides an index which starts after the specified index.
	 * <p>
	 * e.g in a Business frequency, if the index equivalent of Sunday was
	 * provided, it will return the following Monday. If a Monday was specified,
	 * it will return the Tuesday.
	 * <p>
	 * Note: Some frequencies may throw an UnsupportedOperationException in the
	 * event they don't support this operation.
	 *
	 * @param index
	 * @return java.util.Date
	 * @roseuid 4157F4E7031C
	 */
	public Date startsAfter(final Date index) {
		throw new UnsupportedOperationException(
				"Calendar indexes are not supported for this frequency.");
	}

	/**
	 * Indicates whether the index period specified aligns to the calendar
	 *
	 * @param index -
	 *            The period index
	 * @return boolean
	 * @roseuid 4151A37401C5
	 */
	public boolean aligns(final long index) {
		return startsOnBefore(index) == index;
	}

	/**
	 * Indicates whether the index period specified aligns to the calendar Note:
	 * Some frequency implementations may throw an UnsupportedOperationException
	 * in the event this operatrion is not applicable for the frequency.
	 *
	 * @param index
	 * @return boolean
	 * @roseuid 4157F4FC02EE
	 */
	public boolean aligns(final Date index) {

		return aligns(index.getTime());
	}

	/**
	 * Returns an array of period indexes between a start and end index
	 * inclusive.
	 * <p>
	 *
	 * @param startIndex
	 * @param endIndex
	 * @return long[]
	 * @throws com.datagenic.fourthdimension.dates.IndexAlignmentException
	 * @roseuid 41459A860177
	 */
	public abstract long[] getIndexes(long startIndex, long endIndex)
			throws IndexAlignmentException;

	/**
	 * Returns an array of period indexes between a start and end index
	 * inclusive.
	 * <p>
	 * Note: Some frequency implementations may throw an
	 * UnsupportedOperationException in the event this operatrion is not
	 * applicable for the frequency.
	 *
	 * @param startIndex
	 * @param endIndex
	 * @return long[]
	 * @throws com.datagenic.fourthdimension.dates.IndexAlignmentException
	 * @roseuid 4157F50A029F
	 */
	public long[] getIndexes(final Date startIndex, final Date endIndex)
			throws IndexAlignmentException {
		throw new UnsupportedOperationException(
				"Calendar indexes are not supported for this frequency.");
	}

	/**
	 * Indicates the number of periods at the calendar frequency which lie
	 * between the start and end index. It is not inclusive of the end index.
	 * <p>
	 * Note: Some frequency implementations may throw an
	 * UnsupportedOperationException in the event this operatrion is not
	 * applicable for the frequency.
	 *
	 * @param startIndex -
	 *            The start index which is aligned to the calendar frequency
	 * @param endIndex -
	 *            The end index which is aligned to the calendar frequency,
	 *            which designates the start of the next period. Note the end
	 *            index is not excluded in the number of indexes.
	 * @return long
	 * @throws com.datagenic.fourthdimension.dates.IndexAlignmentException
	 * @roseuid 415171490222
	 */
	public abstract long periodsBetween(long startIndex, long endIndex)
			throws IndexAlignmentException;

	/**
	 * Indicates the number of periods at the calendar frequency which lie
	 * between the start and end index. It is not inclusive of the end index.
	 * <p>
	 * Note: Some frequency implementations may throw an
	 * UnsupportedOperationException in the event this operatrion is not
	 * applicable for the frequency.
	 *
	 * @param startIndex -
	 *            The start index which is aligned to the calendar frequency
	 * @param endIndex -
	 *            The end index which is aligned to the calendar frequency
	 * @return long
	 * @throws com.datagenic.fourthdimension.dates.IndexAlignmentException
	 * @roseuid 4157F51B005D
	 */
	public long periodsBetween(final Date startIndex, final Date endIndex)
			throws IndexAlignmentException {
		throw new UnsupportedOperationException(
				"Calendar indexes are not supported for this frequency.");
	}

	/**
	 * Specifies a boolean array that may be used in determining the periods of
	 * a frequency. This boolean array may optional be used by the implementing
	 * calendar frequency.
	 *
	 * @param pattern -
	 *            A pattern of boolean values indicating valid or invalid
	 *            periods.
	 * @roseuid 4145A178030D
	 */
	public void setCalendarPattern(final boolean[] pattern) {
		this.pattern = pattern;
	}

	public void setTimezone(final TimeZone zone) {
		try {
			// First give the listeners a chance to reject this change
			vetoPropertyChangeSupport.fireVetoableChange(TIMEZONE_PROPERTY,
					timezone, zone);
			timezone = zone;
			jodaTimezone = DateTimeZone.forTimeZone(zone);

		} catch (final PropertyVetoException pve) {
			pve.printStackTrace();
		}

	}

	public TimeZone getTimezone() {
		return timezone;
	}

	public DateTimeZone getJodaTimezone() {
		return jodaTimezone;
	}

	/**
	 * Displays the name of the Calendar and if applicable the reference month.
	 *
	 * @return java.lang.String
	 * @roseuid 4151B4B102DE
	 */
	public abstract String getCalendarFrequencyName();

	public abstract long getPeriodFrom(long index, int periods);

	@Override
	public abstract Object clone();

	@Override
	public boolean equals(final Object other) {

		if (!(other instanceof CalendarFrequency)) {
			return false;
		}
		return getCalendarFrequencyName().equals(
				((CalendarFrequency) other).getCalendarFrequencyName());
	}

	@Override
	public int hashCode() {

		return getCalendarFrequencyName().hashCode();
	}
}

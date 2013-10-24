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

/**
 * FIX Finish this class.
 */
public class Weekly extends CalendarFrequency {

	private static final long serialVersionUID = -6494310877575691421L;

	public int MONDAY;

	public int TUESDAY;

	/**
	 * @roseuid 414708AD035B
	 */
	public Weekly() {

	}

	/**
	 * @param periods
	 * @param referenceDate
	 * @param pattern
	 * @roseuid 4145A04E02FD
	 */
	public Weekly(final int periods, final int referenceDate, final boolean[] pattern) {

	}

	/**
	 * @param calendar
	 * @return java.util.Date
	 * @roseuid 4151526503B9
	 */
	public Date indexBefore(final Date calendar) {

		return null;
	}

	/**
	 * @param calendar
	 * @return java.util.Date
	 * @roseuid 41515266008C
	 */
	public Date indexAfter(final Date calendar) {

		return null;
	}

	/**
	 * @param startIndex
	 * @param endIndex
	 * @return long[]
	 * @roseuid 415170AE00FA
	 */
	@Override
	public long[] getIndexes(final long startIndex, final long endIndex) {

		return null;
	}

	/**
	 * @param startCalendar
	 * @param endCalendar
	 * @return long[]
	 * @roseuid 415170AE029F
	 */
	@Override
	public long[] getIndexes(final Date startCalendar, final Date endCalendar) {

		return null;
	}

	/**
	 * @param calendar
	 * @return java.util.Date
	 * @roseuid 41519B74007D
	 */
	public Date indexStartsOnBefore(final Date calendar) {

		return null;
	}

	/**
	 * @param calendar
	 * @return java.util.Date
	 * @roseuid 41519B740203
	 */
	public Date indexStartsAfter(final Date calendar) {

		return null;
	}

	/**
	 * @param startCalendar
	 * @param endCalendar
	 * @return int
	 * @roseuid 415359EE0203
	 */
	public int indexesStartingBetween(final Date startCalendar, final Date endCalendar) {

		return 0;
	}

	/**
	 * @param index
	 * @return long
	 * @roseuid 4157E186007D
	 */
	@Override
	public long startsOnBefore(final long index) {

		return 0;
	}

	/**
	 * @param index
	 * @return long
	 * @roseuid 4157E18600DA
	 */
	@Override
	public long startsAfter(final long index) {

		return 0;
	}

	/**
	 * @param startIndex
	 * @param endIndex
	 * @return long
	 * @throws com.datagenic.fourthdimension.dates.IndexAlignmentException
	 * @roseuid 4157E1860148
	 */
	@Override
	public long periodsBetween(final long startIndex, final long endIndex) throws IndexAlignmentException {

		return 0;
	}

	/**
	 * @return java.lang.String
	 * @roseuid 4165095C02EE
	 */
	@Override
	public String getCalendarFrequencyName() {

		return null;
	}

	/**
	 * @param index
	 * @param periods
	 * @return long
	 * @roseuid 42287B000271
	 */
	@Override
	public long getPeriodFrom(final long index, final int periods) {

		return 0;
	}

	/**
	 * @return Object
	 * @roseuid 425E8C0E0124
	 */
	@Override
	public Object clone() {

		return null;
	}
}

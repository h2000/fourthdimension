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

/**
 * An Observation frequency can be used where a DataPoint is recorded against a sequential event unrelated to time. It
 * is most similar to an array of DataPoint objects.
 */
public class Observation extends CalendarFrequency {

	private static final long serialVersionUID = 8537443307808305389L;

	@Override
	public long[] getIndexes(long startIndex, final long endIndex) {

		final long periods = periodsBetween(startIndex, endIndex);
		if (periods > Integer.MAX_VALUE) {
			throw new IndexRangeTooLargeException();
		}

		final long[] tmp = new long[(int) periods + 1];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = startIndex++;
		}

		return tmp;
	}

	@Override
	public long startsOnBefore(final long index) {

		return index;
	}

	@Override
	public long startsAfter(final long index) {

		return index + 1;
	}

	@Override
	public long periodsBetween(final long startIndex, final long endIndex) throws IndexAlignmentException {

		return endIndex - startIndex;
	}

	@Override
	public String getCalendarFrequencyName() {

		return "Observation";
	}

	@Override
	public long getPeriodFrom(final long index, final int periods) {

		return index + periods;
	}

	@Override
	public Object clone() {

		return new Observation();
	}
}

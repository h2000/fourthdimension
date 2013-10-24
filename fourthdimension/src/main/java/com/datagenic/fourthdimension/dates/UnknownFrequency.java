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

public class UnknownFrequency extends CalendarFrequency {

	private static final long serialVersionUID = 3977411290411361461L;

	public UnknownFrequency() {

		super();
	}

	@Override
	public long startsOnBefore(final long index) {

		return index;
	}

	@Override
	public long startsAfter(final long index) {

		return index;
	}

	@Override
	public long[] getIndexes(final long startIndex, final long endIndex) throws IndexAlignmentException {

		return new long[0];
	}

	@Override
	public long periodsBetween(final long startIndex, final long endIndex) throws IndexAlignmentException {

		return 0;
	}

	@Override
	public String getCalendarFrequencyName() {

		return "Unknown";
	}

	@Override
	public long getPeriodFrom(final long index, final int periods) {

		return 0;
	}

	@Override
	public Object clone() {

		return new UnknownFrequency();
	}
}

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


public class Minutely extends Intraday {

	private static final long serialVersionUID = 710896659752715322L;

	private final long DEFAULT_PERIODICITY = 1000 * 60;

	public Minutely() {

		this.calendarPeriodicity = DEFAULT_PERIODICITY;
		this.frequency = 1;
	}

	public Minutely(final int frequency) {

		this.calendarPeriodicity = DEFAULT_PERIODICITY * frequency;
		this.frequency = frequency;
	}

	@Override
	public String getCalendarFrequencyName() {

		if (frequency > 1) {
			return "Minutely(" + frequency + ")";
		} else {
			return "Minutely";
		}
	}

	@Override
	public Object clone() {

		return new Minutely(this.frequency);
	}
}

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

public class Hourly extends Intraday {

	private static final long serialVersionUID = 7276324781069197953L;

	private final long DEFAULT_PERIODICITY = 1000 * 60 * 60;

	/**
	 * @roseuid 414708AB0222
	 */
	public Hourly() {

		this.calendarPeriodicity = DEFAULT_PERIODICITY;
		this.frequency = 1;
	}

	/**
	 * @param frequency
	 * @roseuid 4145A1A70109
	 */
	public Hourly(final int frequency) {

		this.calendarPeriodicity = DEFAULT_PERIODICITY * frequency;
		this.frequency = frequency;
	}

	/**
	 * @return java.lang.String
	 * @roseuid 418222430031
	 */
	@Override
	public String getCalendarFrequencyName() {

		if (frequency > 1) {
			return "Hourly(" + frequency + ")";
		} else {
			return "Hourly";
		}
	}

	/**
	 * @return Object
	 * @roseuid 425E8B6A023D
	 */
	@Override
	public Object clone() {

		return new Hourly(this.frequency);
	}
}

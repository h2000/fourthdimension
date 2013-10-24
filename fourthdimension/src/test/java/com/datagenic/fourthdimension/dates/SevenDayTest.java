package com.datagenic.fourthdimension.dates;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class SevenDayTest {

	/**
	 * @param args
	 * @roseuid 425E8C9A0318
	 */
	public static void main(final String[] args) {

		final SevenDay day = new SevenDay(Calendar.MONDAY, 2, 0, 0);
		day.setTimezone(TimeZone.getTimeZone("GMT"));

		final SimpleDateFormat sdf = new SimpleDateFormat("E dd MMM yyyy HH:mm:ss z");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

		final Calendar c = new GregorianCalendar(2005, Calendar.JANUARY, 2, 2, 0, 0);
		c.setTimeZone(TimeZone.getTimeZone("GMT"));

		for (int i = 0; i < 100; i++) {

			final long indexStartsBefore = day.startsOnBefore(c.getTimeInMillis());
			final long indexStartsAfter = day.startsAfter(c.getTimeInMillis());

			System.out.println(sdf.format(c.getTime()) + ": Before:"
					+ sdf.format(new java.util.Date(indexStartsBefore)) + " :After:"
					+ sdf.format(new java.util.Date(indexStartsAfter)));
			// System.out.println(c.getTime().toGMTString() + ":" + new java.util.Date(index).toGMTString());

			c.add(Calendar.DAY_OF_YEAR, 1);

		}
		System.exit(-1);

		final Calendar start = new GregorianCalendar(1970, Calendar.JANUARY, 1, 0, 0, 0);
		final Calendar end = new GregorianCalendar(1971, Calendar.JANUARY, 1, 0, 0, 0);

		final long startIndex = day.startsOnBefore(start.getTimeInMillis());
		final long endIndex = day.startsOnBefore(end.getTimeInMillis());

		System.out.println("Start Index:" + sdf.format(new java.util.Date(startIndex)));
		System.out.println("End Index:" + sdf.format(new java.util.Date(endIndex)));

		final long[] indexes = day.getIndexes(day.startsOnBefore(start.getTimeInMillis()),
				day.startsOnBefore(end.getTimeInMillis()));
		for (final long indexe : indexes) {
			System.out.println(sdf.format(new java.util.Date(indexe)));
		}

		final long periods = day.periodsBetween(day.startsOnBefore(start.getTimeInMillis()),
				day.startsOnBefore(end.getTimeInMillis()));
		System.out.println(periods);

		final long period = day.getPeriodFrom(day.startsOnBefore(start.getTimeInMillis()), 1);
		System.out.println(sdf.format(new java.util.Date(period)));
	}

}
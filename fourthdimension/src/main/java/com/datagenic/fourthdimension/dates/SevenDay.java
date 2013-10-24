//Source file: C:\\Data\\DataGenic\\Projects\\FourthDimension\\src\\com\\datagenic\\fourthdimension\\dates\\SevenDay.java

package com.datagenic.fourthdimension.dates;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;

public class SevenDay extends CalendarFrequency {

	private static final long serialVersionUID = -4016256383793509701L;

	private static final int DEFAULT_WEEKDAY_REF = Calendar.MONDAY;

	private static final int DEFAULT_HOUR_REF = 0;

	private static final int DEFAULT_MINUTE_REF = 0;

	private static final int DEFAULT_SECOND_REF = 0;

	private int reference_weekday = DEFAULT_WEEKDAY_REF;

	private int reference_hour = DEFAULT_HOUR_REF;

	private int reference_minute = DEFAULT_MINUTE_REF;

	private int reference_second = DEFAULT_SECOND_REF;

	private static final int DEFAULT_PERIODICITY = 7;

	private final int periodicity = DEFAULT_PERIODICITY;

	private final DateTime calculatedReferenceTime;

	private DateTime originDateTime = null;

	public SevenDay(final int referenceWeekday, final int referenceHour, final int referenceMinute,
			final int referenceSecond) {

		super();

		this.reference_weekday = referenceWeekday;
		this.reference_hour = referenceHour;
		this.reference_minute = referenceMinute;
		this.reference_second = referenceSecond;

		// This calculates the reference time as a time from the origin. It is better than using the TimeOfDay class in
		// Joda
		final MutableDateTime tmp = new MutableDateTime(getOrigin(), getJodaTimezone());
		tmp.setTime(referenceHour, referenceMinute, reference_second, 0);
		calculatedReferenceTime = tmp.toDateTime();
		// calculatedReferenceTime = new TimeOfDay(reference_hour,reference_minute,reference_second,0);
	}

	public SevenDay() {

		this(DEFAULT_WEEKDAY_REF, DEFAULT_HOUR_REF, DEFAULT_MINUTE_REF, DEFAULT_SECOND_REF);
	}

	/**
	 * Displays the name of the Calendar and if applicable the reference date/time.
	 *
	 * @return java.lang.String
	 * @todo Implement this com.datagenic.fourthdimension.dates.CalendarFrequency method
	 * @roseuid 425E8C990328
	 */
	@Override
	public String getCalendarFrequencyName() {

		// Was using a simple dateformat here, but it was taken unnecessary amounts of memory. Do we have an issue with
		// timezones?
		final String hr = reference_hour < 10 ? "0" + reference_hour : reference_hour + "";
		final String min = reference_minute < 10 ? "0" + reference_minute : reference_minute + "";
		final String sec = reference_second < 10 ? "0" + reference_second : reference_second + "";

		return "SevenDay [" + DateHelper.getDowName(reference_weekday) + " " + hr + ":" + min + ":" + sec + "]";
	}

	/**
	 * Returns an array of period indexes between a start and end index inclusive.
	 *
	 * @param startIndex
	 *            long
	 * @param endIndex
	 *            long
	 * @return long[]
	 * @throws IndexAlignmentException
	 * @throws com.datagenic.fourthdimension.dates.IndexAlignmentException
	 * @todo Implement this com.datagenic.fourthdimension.dates.CalendarFrequency method
	 * @roseuid 425E8C990329
	 */
	@Override
	public long[] getIndexes(final long startIndex, final long endIndex) throws IndexAlignmentException {

		return getIndexes(new java.util.Date(startIndex), new java.util.Date(endIndex));
	}

	@Override
	public long[] getIndexes(final Date startIndex, final Date endIndex) {

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
		for (int i = 0; i < indexesBetween; i++) {
			indexes[i] = dateTime.getMillis();
			dateTime.addDays(periodicity);
		}
		return indexes;
	}

	/**
	 * @param index
	 *            long
	 * @param periods
	 *            int
	 * @return long
	 * @todo Implement this com.datagenic.fourthdimension.dates.CalendarFrequency method
	 * @roseuid 425E8C9A0088
	 */
	@Override
	public long getPeriodFrom(final long index, final int periods) {

		if (!aligns(index)) {
			throw new IndexAlignmentException(index + " is not an index at " + getCalendarFrequencyName()
					+ " frequency.");
		}

		final MutableDateTime dateTime = new MutableDateTime(index, this.getJodaTimezone());
		dateTime.addDays(periodicity * periods);
		return dateTime.getMillis();
	}

	@Override
	public long periodsBetween(final Date startIndex, final Date endIndex) throws IndexAlignmentException {

		return periodsBetween(startIndex.getTime(), endIndex.getTime());
	}

	/**
	 * Indicates the number of periods at the calendar frequency which lie between the start and end index.
	 *
	 * @param startIndex
	 *            - The start index which is aligned to the calendar frequency
	 * @param endIndex
	 *            - The end index which is aligned to the calendar frequency, which designates the start of the next
	 *            period. Note the end index is not excluded in the number of indexes.
	 * @return long
	 * @throws IndexAlignmentException
	 * @throws com.datagenic.fourthdimension.dates.IndexAlignmentException
	 * @todo Implement this com.datagenic.fourthdimension.dates.CalendarFrequency method
	 * @roseuid 425E8C9A01E0
	 */
	@Override
	public long periodsBetween(final long startIndex, final long endIndex) throws IndexAlignmentException {

		if (!aligns(startIndex)) {
			throw new IndexAlignmentException(startIndex + " is not an index at " + getCalendarFrequencyName()
					+ " frequency.");
		}
		if (!aligns(endIndex)) {
			throw new IndexAlignmentException(endIndex + " is not an index at " + getCalendarFrequencyName()
					+ " frequency.");
		}
		final int startOriginOffset = daysFromOrigin(startIndex);
		final int endOriginOffset = daysFromOrigin(endIndex);
		final int delta = endOriginOffset - startOriginOffset;
		return Math.abs(delta / periodicity);
	}

	/**
	 * Provides an index which starts after the specified index.
	 *
	 * @param index
	 *            long
	 * @return long
	 * @todo Implement this com.datagenic.fourthdimension.dates.CalendarFrequency method
	 * @roseuid 425E8C9A027C
	 */
	@Override
	public long startsAfter(final long index) {

		final long indexStartsOnBefore = startsOnBefore(index);
		final MutableDateTime dateTime = new MutableDateTime(indexStartsOnBefore, this.getJodaTimezone());
		dateTime.addDays(periodicity);
		return dateTime.getMillis();
	}

	/**
	 * Provides an index which starts on or before the specified index.
	 *
	 * @param index
	 *            - The index period
	 * @return long
	 * @todo Implement this com.datagenic.fourthdimension.dates.CalendarFrequency method
	 * @roseuid 425E8C9A02AC
	 */
	@Override
	public long startsOnBefore(final long index) {

		final DateTime indexDate = new DateTime(index, this.getJodaTimezone());

		final int daysFromOrigin = daysFromOrigin(index);

		final int alignedDay = (daysFromOrigin / periodicity) * periodicity - (Calendar.THURSDAY - reference_weekday);

		// get the origin
		final MutableDateTime dt = new MutableDateTime(getOrigin());
		dt.addDays(alignedDay);

		if (alignedDay > daysFromOrigin) {
			dt.addDays(-periodicity);
		}

		if (reference_weekday < Calendar.THURSDAY) {
			if (DateHelper.javaDayOfWeek(indexDate.getDayOfWeek()) >= reference_weekday) {
				dt.addDays(periodicity);
			}
		}

		if (dt.getMillis() > index) {
			dt.addDays(-periodicity);
		}

		// align the time part. If the timepart is less than the reference time then subtract the periodicity
		// TimeOfDay indexTime = indexDate.toTimeOfDay();

		final MutableDateTime indexTime = new MutableDateTime(getOrigin());
		indexTime.setTime(indexDate.getHourOfDay(), indexDate.getMinuteOfHour(), indexDate.getSecondOfMinute(), 0);

		if (indexTime.isBefore(calculatedReferenceTime)) {
			dt.addDays(-periodicity);
		}

		// Everything should be aligned to that of the reference times
		dt.setHourOfDay(reference_hour);
		dt.setMinuteOfHour(reference_minute);
		dt.setSecondOfMinute(reference_second);
		dt.setMillisOfSecond(0);

		return dt.getMillis();
	}

	/**
	 * Calculates the number of days from the origin point
	 *
	 * @param index
	 *            long
	 * @return int
	 * @roseuid 425E8C9A02DA
	 */
	private int daysFromOrigin(final long index) {

		final int daysDifference = getOrigin().getChronology().days().getDifference(getOrigin().getMillis(), index);

		return -daysDifference;
	}

	/**
	 * Creates an origin DateTime that is used as a reference point in calculations.
	 *
	 * @return DateTime
	 * @roseuid 425E8C9A0309
	 */
	private DateTime getOrigin() {

		if (this.originDateTime == null) {
			this.originDateTime = new DateTime(0, this.getJodaTimezone());
		}
		return this.originDateTime;
	}

	@Override
	public Object clone() {

		return new SevenDay(this.reference_weekday, this.reference_hour, this.reference_minute, this.reference_second);
		}
}
/*
 * long SevenDay.periodsBetween(java.util.Date,java.util.Date){ return
 * periodsBetween(startIndex.getTime(),endIndex.getTime()); }
 */
/*
 * long SevenDay.getIndexes(java.util.Date,java.util.Date){ if (!aligns(startIndex.getTime())) { throw new
 * IndexAlignmentException(getDateFormatter().format( startIndex) + " is not an index at " + getCalendarFrequencyName()
 * + " frequency."); } if (!aligns(endIndex.getTime())) { throw new IndexAlignmentException(getDateFormatter().format(
 * endIndex) + " is not an index at " + getCalendarFrequencyName() + " frequency."); } MutableDateTime dateTime = new
 * MutableDateTime(startIndex,this.getJodaTimezone()); long indexesBetween = periodsBetween(startIndex, endIndex); if
 * (indexesBetween > Integer.MAX_VALUE) throw new IndexRangeTooLargeException(); //want to include the end index so add
 * 1 to this value indexesBetween += 1; long[] indexes = new long[(int)indexesBetween]; for (int i = 0; i <
 * indexesBetween; i++) { indexes[i] = dateTime.getMillis(); dateTime.addDays(periodicity); } return indexes; }
 */

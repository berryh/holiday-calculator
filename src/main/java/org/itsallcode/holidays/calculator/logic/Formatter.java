/**
 * holiday-calculator
 * Copyright (C) 2022 itsallcode <github@kuhnke.net>
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.itsallcode.holidays.calculator.logic;

import java.time.DayOfWeek;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.annotation.Nonnull;

import org.itsallcode.holidays.calculator.logic.variants.FloatingHoliday.Day;

public class Formatter
{
	private static final DateTimeFormatter DAY_OF_WEEK_FORMATTER_SHORT = DateTimeFormatter.ofPattern("EEE", Locale.US);
	private static final DateTimeFormatter DAY_OF_WEEK_FORMATTER_LONG = DateTimeFormatter.ofPattern("EEEE", Locale.US);
	private static final DateTimeFormatter MONTH_DAY_FORMATTER = DateTimeFormatter.ofPattern("MMM d", Locale.US);
	private static final DateTimeFormatter MONTH_FORMATTER = DateTimeFormatter.ofPattern("MMM", Locale.US);

	private Formatter()
	{
	}

	public static String format(@Nonnull final DayOfWeek dayOfWeek)
	{
		return DAY_OF_WEEK_FORMATTER_SHORT.format(dayOfWeek);
	}

	public static String formatLong(@Nonnull final DayOfWeek dayOfWeek)
	{
		return DAY_OF_WEEK_FORMATTER_LONG.format(dayOfWeek);
	}

	public static String format(@Nonnull final MonthDay monthDay)
	{
		return MONTH_DAY_FORMATTER.format(monthDay).toUpperCase();
	}

	@Nonnull
	public static String format(@Nonnull final MonthDay monthDay, final Day dayInterpretation)
	{
		switch (dayInterpretation)
		{
		case LAST:
			return MONTH_FORMATTER.format(monthDay.getMonth()).toUpperCase() + " last-day";
		case FIRST:
			return MONTH_DAY_FORMATTER.format(monthDay.withDayOfMonth(1));
		default:
			return MONTH_DAY_FORMATTER.format(monthDay).toUpperCase();
		}
	}

	@Nonnull
	public static String offset(final int offset)
	{
		return String.format("%d day%s %s",
				Math.abs(offset),
				(Math.abs(offset) == 1 ? "" : "s"),
				(offset < 0 ? "before" : "after"));
	}

	public static String offsetWithoutZero(final int offset)
	{
		return (offset == 0 ? "" : offset(offset) + " ");
	}

	@Nonnull
	public static String ordinal(final int offset)
	{
		final String[] suffix = { "", "st", "nd", "rd" };
		return String.format("%d%s", offset,
				(offset < 4 ? suffix[offset] : "th"));
	}
}

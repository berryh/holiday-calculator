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
package org.itsallcode.holidays.calculator.logic.variants;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.temporal.TemporalAdjusters;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.itsallcode.holidays.calculator.logic.Formatter;

public class FloatingHoliday extends Holiday
{

	public enum Direction
	{
		BEFORE,
		AFTER;

		@Nonnull
		public static Direction parse(final String s)
		{
			return valueOf(s.toUpperCase());
		}
	}

	public enum Day
	{
		AS_SPECIFIED,
		FIRST,
		LAST;
	}

	private final int offset;
	private final DayOfWeek dayOfWeek;
	private final Direction direction;
	private final MonthDay monthDay;
	private final Day dayInterpretation;

	/**
	 * Holiday called &lt;name&gt; on the &lt;offset&gt; &lt;dayOfWeek&gt;
	 * after/before first or last day of &lt;month&gt;.
	 *
	 * @param category          category
	 * @param name              name
	 * @param offset            offset
	 * @param dayOfWeek         day of week
	 * @param direction         direction
	 * @param month             number of month
	 * @param dayInterpretation FIRST, LAST or AS_SPECIFIED
	 */
	public FloatingHoliday(@Nonnull final String category, @Nonnull final String name, final int offset, @Nonnull final DayOfWeek dayOfWeek, @Nonnull final Direction direction, final int month, @Nonnull final Day dayInterpretation)
	{
		this(category, name, offset, dayOfWeek, direction, MonthDay.of(month, 1), dayInterpretation);
	}

	/**
	 * Holiday called &lt;name&gt; on the &lt;offset&gt; &lt;dayOfWeek&gt;
	 * after/before pivot date &lt;monthDay&gt;.
	 *
	 * @param category  category
	 * @param name      name
	 * @param offset    offset
	 * @param dayOfWeek day of week
	 * @param direction direction
	 * @param monthDay  pivot date
	 */
	public FloatingHoliday(@Nonnull final String category, @Nonnull final String name, final int offset, @Nonnull final DayOfWeek dayOfWeek, @Nonnull final Direction direction, @Nonnull final MonthDay monthDay)
	{
		this(category, name, offset, dayOfWeek, direction, monthDay, Day.AS_SPECIFIED);
	}

	private FloatingHoliday(@Nonnull final String category, @Nonnull final String name, final int offset, @Nonnull final DayOfWeek dayOfWeek, @Nonnull final Direction direction, @Nonnull final MonthDay monthDay, @Nonnull final Day dayInterpretation)
	{
		super(category, name);

		if (offset < 0)
		{
			throw new IllegalArgumentException("Argument offset must be >= 0, but was " + offset);
		}
		this.offset = offset;
		this.dayOfWeek = dayOfWeek;
		this.direction = direction;
		this.dayInterpretation = dayInterpretation;
		this.monthDay = monthDay;
	}

	@Nonnull
	@Override
	public LocalDate of(final int year)
	{
		final LocalDate pivotDay = pivotDay(year).with(TemporalAdjusters.previousOrSame(dayOfWeek));
		final int delta = (direction == Direction.AFTER ? offset - 1 : 1 - offset);
		return pivotDay.plusDays(7L * delta + offsetInDays);
	}

	@Nonnull
	private LocalDate pivotDay(final int year)
	{
		if (direction == Direction.AFTER)
		{
			return monthDay.atYear(year).plusDays(6);
		}

		if (dayInterpretation == Day.LAST)
		{
			return monthDay.atYear(year).with(TemporalAdjusters.lastDayOfMonth());
		}

		return monthDay.atYear(year);
	}

	@Nonnull
	@Override
	public String toString()
	{
		return String.format("%s(%s %s: %s%s %s %s %s)",
				this.getClass().getSimpleName(),
				getCategory(),
				getName(),
				Formatter.offsetWithoutZero(offsetInDays),
				Formatter.ordinal(offset),
				Formatter.formatLong(dayOfWeek),
				direction.toString().toLowerCase(),
				Formatter.format(monthDay, dayInterpretation));
	}

	@Nonnull
	@Override
	public MonthDay getMonthDay()
	{
		return this.monthDay;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((dayInterpretation == null) ? 0 : dayInterpretation.hashCode());
		result = prime * result + ((dayOfWeek == null) ? 0 : dayOfWeek.hashCode());
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((monthDay == null) ? 0 : monthDay.hashCode());
		result = prime * result + offset;
		return result;
	}

	@Override
	public boolean equals(@Nullable final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (!super.equals(obj))
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final FloatingHoliday other = (FloatingHoliday) obj;
		if (dayInterpretation != other.dayInterpretation)
		{
			return false;
		}
		if (dayOfWeek != other.dayOfWeek)
		{
			return false;
		}
		if (direction != other.direction)
		{
			return false;
		}
		if (monthDay == null)
		{
			if (other.monthDay != null)
			{
				return false;
			}
		}
		else if (!monthDay.equals(other.monthDay))
		{
			return false;
		}
		return (offset == other.offset);
	}
}

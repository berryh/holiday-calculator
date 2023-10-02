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

import java.time.LocalDate;
import java.time.MonthDay;
import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.itsallcode.holidays.calculator.logic.Formatter;

public class FixedDateHoliday extends Holiday
{

	private final MonthDay monthDay;

	public FixedDateHoliday(@Nonnull final String category, @Nonnull final String name, @Nonnull final MonthDay monthDay)
	{
		super(category, name);
		this.monthDay = monthDay;
	}

	public FixedDateHoliday(@Nonnull final Holiday other, @Nonnull final MonthDay monthDay)
	{
		super(other.getCategory(), other.getName());
		this.monthDay = monthDay;
	}

	@Override
	@Nonnull
	public LocalDate of(final int year)
	{
		return monthDay.atYear(year);
	}

	@Override
	@Nonnull
	public String toString(@Nonnull final Holiday pivot)
	{
		if (pivot instanceof FixedDateHoliday
		    && pivot.getName().equals(getName())
		    && pivot.getCategory().equals(getCategory()))
		{
			return Formatter.format(monthDay);
		}
		return toString();
	}

	@Override
	@Nonnull
	public String toString(@Nonnull final String condition)
	{
		return String.format("%s(%s %s: %s%s)", this.getClass().getSimpleName(),
				getCategory(),
				getName(),
				Formatter.format(monthDay),
				condition);
	}

	@Override
	@Nonnull
	public MonthDay getMonthDay()
	{
		return monthDay;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((monthDay == null) ? 0 : monthDay.hashCode());
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
		final FixedDateHoliday other = (FixedDateHoliday) obj;
		if (monthDay == null && (other.monthDay != null))
		{
			return false;
		}
		return Objects.equals(monthDay, other.monthDay);
	}
}

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
import java.time.Year;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class Holiday
{
	@Nonnull
	public abstract LocalDate of(int year);

	private final String category;
	private final String name;
	protected int offsetInDays = 0;

	/**
	 * @param category Arbitrary category that may be evaluated by the application
	 *                 processing the holiday.
	 * @param name     Name of holiday.
	 */
	protected Holiday(@Nonnull final String category, @Nonnull final String name)
	{
		this.category = category;
		this.name = name;
	}

	@CheckForNull
	public MonthDay getMonthDay()
	{
		return null;
	}

	@Nonnull
	public Holiday withOffsetInDays(final int offsetInDays)
	{
		this.offsetInDays = offsetInDays;
		return this;
	}

	@Nonnull
	public String getCategory()
	{
		return category;
	}

	@Nonnull
	public String getName()
	{
		return name;
	}

	@Nonnull
	public LocalDate of(@Nonnull final Year year)
	{
		return of(year.getValue());
	}

	@Nonnull
	@Override
	public String toString()
	{
		return toString("");
	}

	/**
	 * @param pivot pivot holiday, for which the current holiday is an alternative
	 * @return string representation of current holiday as alternative to pivot
	 * holiday
	 */
	@Nonnull
	protected String toString(@Nonnull final Holiday pivot)
	{
		return "";
	}

	@Nonnull
	protected String toString(@Nonnull final String condition)
	{
		return "";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + category.hashCode();
		result = prime * result + name.hashCode();
		result = prime * result + offsetInDays;
		return result;
	}

	@Override
	public boolean equals(@Nullable final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final Holiday other = (Holiday) obj;
		if (!category.equals(other.category))
		{
			return false;
		}
		if (!name.equals(other.name))
		{
			return false;
		}
		return (offsetInDays == other.offsetInDays);
	}
}

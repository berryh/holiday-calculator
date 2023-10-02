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

import javax.annotation.Nonnull;

import org.itsallcode.holidays.calculator.logic.Formatter;

public abstract class PivotDateBasedHoliday extends Holiday
{
	protected final String pivotDateName;
	protected final int offsetInDays;

	protected PivotDateBasedHoliday(@Nonnull final String pivotDateName, @Nonnull final String category, @Nonnull final String name, final int offsetInDays)
	{
		super(category, name);
		this.offsetInDays = offsetInDays;
		this.pivotDateName = pivotDateName;
	}

	@Nonnull
	@Override
	public String toString()
	{
		return String.format("%s(%s %s: %s %s)",
				this.getClass().getSimpleName(),
				getCategory(),
				getName(),
				Formatter.offset(offsetInDays),
				pivotDateName);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + offsetInDays;
		result = prime * result + ((pivotDateName == null) ? 0 : pivotDateName.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj)
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
		final PivotDateBasedHoliday other = (PivotDateBasedHoliday) obj;
		if (offsetInDays != other.offsetInDays)
		{
			return false;
		}
		if (pivotDateName == null)
		{
			return other.pivotDateName == null;
		}
		return pivotDateName.equals(other.pivotDateName);
	}
}

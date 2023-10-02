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
package org.itsallcode.holidays.calculator.logic.conditions;

import java.time.Year;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ConstantCondition extends Condition
{

	private final boolean value;

	public ConstantCondition(final boolean value)
	{
		this.value = value;
	}

	@Override
	public boolean applies(@Nonnull final Year year)
	{
		return value;
	}

	public boolean isValue()
	{
		return value;
	}

	@Nonnull
	@Override
	public String toString(@Nonnull final String prefix, final boolean negated)
	{
		return ((negated != value) ? "" : "never");
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (value ? 1231 : 1237);
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
		final ConstantCondition other = (ConstantCondition) obj;
		return (value == other.value);
	}
}

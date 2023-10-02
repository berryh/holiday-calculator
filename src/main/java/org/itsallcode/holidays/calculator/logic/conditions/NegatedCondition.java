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

public class NegatedCondition extends Condition
{
	private final Condition other;

	public NegatedCondition(@Nonnull final Condition other)
	{
		this.other = other;
	}

	@Override
	public boolean applies(@Nonnull final Year year)
	{
		return !other.applies(year);
	}

	@Nonnull
	@Override
	public String toString(@Nonnull final String prefix)
	{
		return other.toString(prefix, true);
	}

	@Nonnull
	@Override
	public String toString(@Nonnull final String prefix, final boolean negated)
	{
		return other.toString(prefix, !negated);
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + other.hashCode();
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
		final NegatedCondition otherNegatedCondition = (NegatedCondition) obj;
		return (this.other.equals(otherNegatedCondition.other));
	}
}

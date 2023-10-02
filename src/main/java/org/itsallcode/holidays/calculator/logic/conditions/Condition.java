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

import java.time.MonthDay;
import java.time.Year;

import javax.annotation.Nonnull;

public abstract class Condition
{
	public static final Condition APPLIES_ALWAYS = new ConstantCondition(true);

	public static Condition not(@Nonnull final Condition other)
	{
		return new NegatedCondition(other);
	}

	public abstract boolean applies(@Nonnull Year year);

	@Nonnull
	public abstract String toString(@Nonnull String prefix, boolean negated);

	@Nonnull
	public Condition withPivotDate(@Nonnull final MonthDay pivot)
	{
		return this;
	}

	@Nonnull
	@Override
	public String toString()
	{
		return toString("");
	}

	@Nonnull
	public String toString(@Nonnull final String prefix)
	{
		return toString(prefix, false);
	}
}

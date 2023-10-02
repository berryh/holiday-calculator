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
package org.itsallcode.holidays.calculator.logic.conditions.builder;

import java.time.DayOfWeek;
import java.time.MonthDay;

import javax.annotation.Nonnull;

import org.itsallcode.holidays.calculator.logic.conditions.Condition;
import org.itsallcode.holidays.calculator.logic.conditions.DayOfWeekCondition;
import org.itsallcode.holidays.calculator.logic.conditions.NegatedCondition;
import org.itsallcode.holidays.calculator.logic.variants.Holiday;

public class ConditionBuilder
{

	private boolean negated = false;
	private DayOfWeek[] daysOfWeek;
	private MonthDay pivotDate;

	@Nonnull
	public Condition build()
	{
		if (pivotDate == null)
		{
			throw new IllegalStateException("Cannot build DayOfWeekCondition with unspecified pivot date.");
		}
		final DayOfWeekCondition condition = new DayOfWeekCondition(pivotDate, daysOfWeek);
		if (negated)
		{
			return new NegatedCondition(condition);
		}
		else
		{
			return condition;
		}
	}

	@Nonnull
	public ConditionBuilder withDaysOfWeek(@Nonnull final DayOfWeek... daysOfWeek)
	{
		this.daysOfWeek = daysOfWeek;
		return this;
	}

	@Nonnull
	public ConditionBuilder withPivotDate(@Nonnull final MonthDay monthDay)
	{
		this.pivotDate = monthDay;
		return this;
	}

	@Nonnull
	public ConditionBuilder withOptionalPivotDateFrom(@Nonnull final Holiday holiday)
	{
		if (pivotDate == null)
		{
			this.pivotDate = holiday.getMonthDay();
		}
		return this;
	}

	@Nonnull
	public ConditionBuilder negated()
	{
		this.negated = !this.negated;
		return this;
	}
}

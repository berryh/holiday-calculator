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
package org.itsallcode.holidays.calculator.logic.parser.matcher;

import java.time.MonthDay;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import org.itsallcode.holidays.calculator.logic.conditions.builder.ConditionBuilder;
import org.itsallcode.holidays.calculator.logic.variants.ConditionalHoliday;
import org.itsallcode.holidays.calculator.logic.variants.FixedDateHoliday;
import org.itsallcode.holidays.calculator.logic.variants.Holiday;
import org.itsallcode.holidays.calculator.logic.variants.HolidayWithAlternative;

class FixedDateMatcher extends HolidayMatcher
{
	FixedDateMatcher()
	{
		super(Patterns.FIXED_HOLIDAY);
	}

	@Nonnull
	@Override
	FixedDateHoliday createHoliday(@Nonnull final Matcher matcher)
	{
		return new FixedDateHoliday(
				matcher.group(Patterns.CATEGORY_GROUP),
				matcher.group(Patterns.NAME_GROUP),
				monthDay(matcher.group(Patterns.MONTH_GROUP), matcher.group(Patterns.DAY_GROUP)));
	}

	static class Conditional extends HolidayMatcher
	{
		Conditional()
		{
			super(new FixedDateMatcher(), Patterns.CONDITIONAL_FIXED_HOLIDAY);
		}

		@Nonnull
		@Override
		Holiday createHoliday(@Nonnull final Matcher matcher)
		{
			return new ConditionalHoliday( //
					createConditionBuilder(matcher),
					createOriginalHoliday(matcher));
		}
	}

	static class Alternative extends HolidayMatcher
	{
		private final boolean negated;

		Alternative(@Nonnull final Pattern pattern)
		{
			super(new FixedDateMatcher(), pattern);
			this.negated = (pattern == Patterns.ALTERNATIVE_DATE_HOLIDAY_NEGATED_DAY_OF_WEEK);
		}

		@Nonnull
		@Override
		Holiday createHoliday(@Nonnull final Matcher matcher)
		{
			final MonthDay alternateDate = monthDay( //
					matcher.group(Patterns.MONTH_GROUP_2),
					matcher.group(Patterns.DAY_GROUP_2));
			final ConditionBuilder conditionBuilder = createConditionBuilder(matcher);
			return new HolidayWithAlternative( //
					createOriginalHoliday(matcher),
					(negated ? conditionBuilder.negated() : conditionBuilder),
					alternateDate);
		}
	}
}
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

import java.time.DayOfWeek;
import java.time.Month;
import java.time.MonthDay;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.itsallcode.holidays.calculator.logic.conditions.builder.ConditionBuilder;
import org.itsallcode.holidays.calculator.logic.parser.AbbreviationParser;
import org.itsallcode.holidays.calculator.logic.variants.Holiday;

public abstract class HolidayMatcher
{

	public static HolidayMatcher[] matchers()
	{
		return new HolidayMatcher[] {
				new NegatedConditionMatcher(new FixedDateMatcher(),
						Patterns.FIXED_HOLIDAY_CONDITIONAL_NEGATED),
				new FixedDateMatcher.Conditional(),
				new FixedDateMatcher(),
				new FixedDateMatcher.Alternative(Patterns.ALTERNATIVE_DATE_HOLIDAY_NEGATED_DAY_OF_WEEK),
				new FixedDateMatcher.Alternative(Patterns.ALTERNATIVE_DATE_HOLIDAY),
				new FloatingDateMatcher.OffsetMatcher(),
				new FloatingDateMatcher(),
				new EasterBasedMatcher(),
				new OrthodoxEasterBasedMatcher()
		};
	}

	@CheckForNull
	abstract Holiday createHoliday(@Nonnull Matcher matcher);

	private static final Pattern MONTH_NAME_PATTERN = Pattern.compile(
			Patterns.NAME_REGEXP, Pattern.CASE_INSENSITIVE);

	private final AbbreviationParser<Month> monthNameParser = new AbbreviationParser<>(Month.class);
	private final AbbreviationParser<DayOfWeek> dayOfWeekParser = new AbbreviationParser<>(DayOfWeek.class);
	private final Pattern pattern;

	private final HolidayMatcher originalMatcher;

	protected HolidayMatcher(@Nonnull final Pattern pattern)
	{
		this(null, pattern);
	}

	protected HolidayMatcher(@Nullable final HolidayMatcher originalMatcher, @Nonnull final Pattern pattern)
	{
		this.originalMatcher = originalMatcher;
		this.pattern = pattern;
	}

	@CheckForNull
	protected Holiday createOriginalHoliday(@Nonnull final Matcher matcher)
	{
		if (originalMatcher == null)
		{
			return null;
		}
		return originalMatcher.createHoliday(matcher);
	}

	@CheckForNull
	public Holiday createHoliday(@Nonnull final String line)
	{
		final Matcher matcher = pattern.matcher(line);
		if (!matcher.matches())
		{
			return null;
		}
		return createHoliday(matcher);
	}

	/**
	 * @param arg (abbreviated) name of month or number as String
	 * @return number of month as integer
	 */
	protected int monthNumber(@Nonnull final String arg)
	{
		if (MONTH_NAME_PATTERN.matcher(arg).matches())
		{
			return monthNameParser.getEnumFor(arg).getValue();
		}
		else
		{
			return Integer.parseInt(arg);
		}
	}

	@Nonnull
	protected MonthDay monthDay(@Nonnull final String month, @Nonnull final String day)
	{
		return MonthDay.of(monthNumber(month), Integer.parseInt(day));
	}

	@Nonnull
	protected DayOfWeek dayOfWeek(@Nonnull final String prefix)
	{
		return dayOfWeekParser.getEnumFor(prefix);
	}

	@Nonnull
	protected DayOfWeek[] daysOfWeek(@Nonnull final String commaSeparatedList)
	{
		return Arrays.stream(commaSeparatedList.split(","))
				.map(this::dayOfWeek)
				.toArray(DayOfWeek[]::new);
	}

	@Nonnull
	public ConditionBuilder createConditionBuilder(@Nonnull final Matcher matcher)
	{
		return new ConditionBuilder()
				.withDaysOfWeek(daysOfWeek(matcher.group(Patterns.PIVOT_DAYS_OF_WEEK_GROUP)))
				.withPivotDate(monthDay(
						matcher.group(Patterns.MONTH_GROUP_2), matcher.group(Patterns.DAY_GROUP_2)));
	}
}

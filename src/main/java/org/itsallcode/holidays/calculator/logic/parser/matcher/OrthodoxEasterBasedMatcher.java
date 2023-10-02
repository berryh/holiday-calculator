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

import java.util.regex.Matcher;

import javax.annotation.Nonnull;

import org.itsallcode.holidays.calculator.logic.variants.Holiday;
import org.itsallcode.holidays.calculator.logic.variants.OrthodoxEasterBasedHoliday;

class OrthodoxEasterBasedMatcher extends HolidayMatcher
{
	OrthodoxEasterBasedMatcher()
	{
		super(Patterns.ORTHODOX_EASTER_BASED_HOLIDAY);
	}

	@Override
	@Nonnull
	Holiday createHoliday(@Nonnull final Matcher matcher)
	{
		return new OrthodoxEasterBasedHoliday(
				matcher.group(Patterns.CATEGORY_GROUP),
				matcher.group(Patterns.NAME_GROUP),
				Integer.parseInt(matcher.group(Patterns.OFFSET_GROUP)));
	}
}
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
package org.itsallcode.holidays.calculator.logic.parser;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;

import org.itsallcode.holidays.calculator.logic.parser.matcher.HolidayMatcher;
import org.itsallcode.holidays.calculator.logic.variants.Holiday;

public class HolidayParser
{

	private final HolidayMatcher[] matchers = HolidayMatcher.matchers();

	@CheckForNull
	public Holiday parse(@Nonnull final String line)
	{
		final String trimmed = line.trim();
		for (final HolidayMatcher m : matchers)
		{
			final Holiday holiday = m.createHoliday(trimmed);
			if (holiday != null)
			{
				return holiday;
			}
		}

		return null;
	}
}

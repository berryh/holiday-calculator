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

import javax.annotation.Nonnull;

import org.itsallcode.holidays.calculator.logic.Easter;

public class EasterBasedHoliday extends PivotDateBasedHoliday
{

	public EasterBasedHoliday(@Nonnull final String category, @Nonnull final String name, final int offsetInDays)
	{
		super("Easter", category, name, offsetInDays);
	}

	@Override
	@Nonnull
	public LocalDate of(final int year)
	{
		return Easter.gauss(year).plusDays(offsetInDays);
	}
}
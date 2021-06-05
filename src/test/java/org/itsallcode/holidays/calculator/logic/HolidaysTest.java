/**
 * holiday-calculator
 * Copyright (C) 2021 itsallcode <github@kuhnke.net>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.itsallcode.holidays.calculator.logic;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Hashtable;
import java.util.List;

import org.itsallcode.holidays.calculator.logic.FloatingHoliday.Direction;
import org.itsallcode.holidays.calculator.logic.parser.HolidaysFileParser;
import org.junit.jupiter.api.Test;

class HolidaysTest {

	@Test
	void illegalLine() throws IOException {
		final HolidaysFileParser parser = new HolidaysFileParser("illegal_input");
		parser.parse(new ByteArrayInputStream("#\n\nillegal line".getBytes()));
		assertThat(parser.getErrors().size()).isEqualTo(1);
		assertThat(parser.getErrors().get(0).lineNumber).isEqualTo(3);
	}

	@Test
	void comment() throws IOException {
		final HolidaysFileParser parser = new HolidaysFileParser("comment line");
		final List<Holiday> actual = parser.parse(new ByteArrayInputStream(" # comment\n".getBytes()));
		assertThat(parser.getErrors().size()).isEqualTo(0);
		assertThat(actual.isEmpty());
	}

	@Test
	void holidayWithEolComment() throws IOException {
		final HolidaysFileParser parser = new HolidaysFileParser("holiday with eol comment");
		final List<Holiday> actual = parser
				.parse(new ByteArrayInputStream(" birthday fixed 7 31 My Birthday # comment \n".getBytes()));
		assertThat(parser.getErrors().size()).isEqualTo(0);
		assertThat(actual).containsExactly(new FixedDateHoliday("birthday", "My Birthday", 7, 31));
	}

	@Test
	void allBarbarianHolidays() throws IOException {
		final Holiday[] expected = expectedBavarianHolidays();
		assertThat(readBavarianHolidays().getDefinitions()).containsExactlyInAnyOrder(expected);
	}

	@Test
	void bavarianHolidays_2021_04() throws IOException {
		final int year = 2021;
		final int month = 4;

		final Hashtable<Integer, String> expected = new Hashtable<>();
		expected.put(2, "Karfreitag");
		expected.put(4, "Ostersonntag");
		expected.put(5, "Ostermontag");

		assertHolidays(year, month, expected);
	}

	@Test
	void bavarianHolidays_2021_05() throws IOException {
		final int year = 2021;
		final int month = 5;

		final Hashtable<Integer, String> expected = new Hashtable<>();
		expected.put(1, "1. Mai");
		expected.put(13, "Christi Himmelfahrt");
		expected.put(23, "Pfingstsonntag");
		expected.put(24, "Pfingstmontag");

		assertHolidays(year, month, expected);
	}

	private void assertHolidays(final int year, final int month, final Hashtable<Integer, String> expected)
			throws IOException {
		final Holidays service = readBavarianHolidays();
		final int n = LocalDate.of(year, month, 1).with(TemporalAdjusters.lastDayOfMonth()).getDayOfMonth();
		for (int i = 1; i <= n; i++) {
			final String expectedName = expected.get(i);
			final LocalDate date = LocalDate.of(year, month, i);
			if (expectedName == null) {
				assertThat(service.instances(date)).isEmpty();
			} else {
				final List<Holiday> list = service.instances(date);
				assertThat(list.size()).isEqualTo(1);
				final Holiday actual = list.get(0);
				assertThat(actual.getName()).isEqualTo(expectedName);
			}
		}
	}

	private Holidays readBavarianHolidays() throws IOException {
		final HolidaysFileParser parser = new HolidaysFileParser("bavaria.txt");
		final List<Holiday> list = parser.parse(HolidaysTest.class.getResourceAsStream("bavaria.txt"));
		return new Holidays(list);
	}

	private Holiday[] expectedBavarianHolidays() {
		return new Holiday[] {
				new FixedDateHoliday("holiday", "Neujahr", 1, 1),
				new FixedDateHoliday("holiday", "Heilige Drei Könige", 1, 6),
				new FixedDateHoliday("holiday", "1. Mai", 5, 1),
				new FixedDateHoliday("holiday", "Tag der Deutschen Einheit", 10, 3),

				new FloatingHoliday("holiday", "1. Advent", 4, DayOfWeek.SUNDAY, Direction.BEFORE, 12, 24),
				new FloatingHoliday("holiday", "2. Advent", 3, DayOfWeek.SUNDAY, Direction.BEFORE, 12, 24),
				new FloatingHoliday("holiday", "3. Advent", 2, DayOfWeek.SUNDAY, Direction.BEFORE, 12, 24),
				new FloatingHoliday("holiday", "4. Advent", 1, DayOfWeek.SUNDAY, Direction.BEFORE, 12, 24),
				new FixedDateHoliday("holiday", "1. Weihnachtstag", 12, 25),
				new FixedDateHoliday("holiday", "2. Weihnachtstag", 12, 26),

				new EasterBasedHoliday("holiday", "Rosenmontag", -48),
				new EasterBasedHoliday("holiday", "Karfreitag", -2),
				new EasterBasedHoliday("holiday", "Ostersonntag", 0),
				new EasterBasedHoliday("holiday", "Ostermontag", +1),
				new EasterBasedHoliday("holiday", "Christi Himmelfahrt", +39),
				new EasterBasedHoliday("holiday", "Pfingstsonntag", +49),
				new EasterBasedHoliday("holiday", "Pfingstmontag", +50),
				new EasterBasedHoliday("holiday", "Fronleichnam", +60),
				new FixedDateHoliday("holiday", "Mariä Himmelfahrt", 8, 15),
				new FixedDateHoliday("holiday", "Allerheiligen", 11, 1),
				new FloatingHoliday("holiday", "Totensonntag", 1, DayOfWeek.SUNDAY, Direction.AFTER, 11, 20) };
	}
}
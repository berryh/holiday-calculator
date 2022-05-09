/**
 * holiday-calculator
 * Copyright (C) 2022 itsallcode <github@kuhnke.net>
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

import java.time.LocalDate;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EasterTest {

	/**
	 * sources:
	 * <ul>
	 * <li>http://www.maa.clell.de/StarDate/feiertage.html
	 * <li>https://de.wikipedia.org/wiki/Osterdatum
	 * <li>http://5ko.free.fr/en/easter.php
	 * <li>http://www.beckerswelt.de/griechisches_ostern.htm
	 * <li>http://www.russian-online.net/de_start/box/boxtext.php?auswahl=ostern
	 * </ul>
	 */
	@ParameterizedTest(name = "In year ''{0}'' Gauss Easter is {1} and Orthodox Easter is {2}")
	@CsvSource(value = {
			"1901, 1901-04-07, 1901-04-14", "1902, 1902-03-30, 1902-04-27", "1903, 1903-04-12, 1903-04-19",
			"1904, 1904-04-03, 1904-04-10", "1905, 1905-04-23, 1905-04-30", "1906, 1906-04-15, 1906-04-15",
			"1907, 1907-03-31, 1907-05-05", "1908, 1908-04-19, 1908-04-26", "1909, 1909-04-11, 1909-04-11",
			"1910, 1910-03-27, 1910-05-01", "1911, 1911-04-16, 1911-04-23", "1912, 1912-04-07, 1912-04-07",
			"1913, 1913-03-23, 1913-04-27", "1914, 1914-04-12, 1914-04-19", "1915, 1915-04-04, 1915-04-04",
			"1916, 1916-04-23, 1916-04-23", "1917, 1917-04-08, 1917-04-15", "1918, 1918-03-31, 1918-05-05",
			"1919, 1919-04-20, 1919-04-20", "1920, 1920-04-04, 1920-04-11", "1921, 1921-03-27, 1921-05-01",
			"1922, 1922-04-16, 1922-04-16", "1923, 1923-04-01, 1923-04-08", "1924, 1924-04-20, 1924-04-27",
			"1925, 1925-04-12, 1925-04-19", "1926, 1926-04-04, 1926-05-02", "1927, 1927-04-17, 1927-04-24",
			"1928, 1928-04-08, 1928-04-15", "1929, 1929-03-31, 1929-05-05", "1930, 1930-04-20, 1930-04-20",
			"1931, 1931-04-05, 1931-04-12", "1932, 1932-03-27, 1932-05-01", "1933, 1933-04-16, 1933-04-16",
			"1934, 1934-04-01, 1934-04-08", "1935, 1935-04-21, 1935-04-28", "1936, 1936-04-12, 1936-04-12",
			"1937, 1937-03-28, 1937-05-02", "1938, 1938-04-17, 1938-04-24", "1939, 1939-04-09, 1939-04-09",
			"1940, 1940-03-24, 1940-04-28", "1941, 1941-04-13, 1941-04-20", "1942, 1942-04-05, 1942-04-05",
			"1943, 1943-04-25, 1943-04-25", "1944, 1944-04-09, 1944-04-16", "1945, 1945-04-01, 1945-05-06",
			"1946, 1946-04-21, 1946-04-21", "1947, 1947-04-06, 1947-04-13", "1948, 1948-03-28, 1948-05-02",
			"1949, 1949-04-17, 1949-04-24", "1950, 1950-04-09, 1950-04-09", "1951, 1951-03-25, 1951-04-29",
			"1952, 1952-04-13, 1952-04-20", "1953, 1953-04-05, 1953-04-05", "1954, 1954-04-18, 1954-04-25",
			"1955, 1955-04-10, 1955-04-17", "1956, 1956-04-01, 1956-05-06", "1957, 1957-04-21, 1957-04-21",
			"1958, 1958-04-06, 1958-04-13", "1959, 1959-03-29, 1959-05-03", "1960, 1960-04-17, 1960-04-17",
			"1961, 1961-04-02, 1961-04-09", "1962, 1962-04-22, 1962-04-29", "1963, 1963-04-14, 1963-04-14",
			"1964, 1964-03-29, 1964-05-03", "1965, 1965-04-18, 1965-04-25", "1966, 1966-04-10, 1966-04-10",
			"1967, 1967-03-26, 1967-04-30", "1968, 1968-04-14, 1968-04-21", "1969, 1969-04-06, 1969-04-13",
			"1970, 1970-03-29, 1970-04-26", "1971, 1971-04-11, 1971-04-18", "1972, 1972-04-02, 1972-04-09",
			"1973, 1973-04-22, 1973-04-29", "1974, 1974-04-14, 1974-04-14", "1975, 1975-03-30, 1975-05-04",
			"1976, 1976-04-18, 1976-04-25", "1977, 1977-04-10, 1977-04-10", "1978, 1978-03-26, 1978-04-30",
			"1979, 1979-04-15, 1979-04-22", "1980, 1980-04-06, 1980-04-06", "1981, 1981-04-19, 1981-04-26",
			"1982, 1982-04-11, 1982-04-18", "1983, 1983-04-03, 1983-05-08", "1984, 1984-04-22, 1984-04-22",
			"1985, 1985-04-07, 1985-04-14", "1986, 1986-03-30, 1986-05-04", "1987, 1987-04-19, 1987-04-19",
			"1988, 1988-04-03, 1988-04-10", "1989, 1989-03-26, 1989-04-30", "1990, 1990-04-15, 1990-04-15",
			"1991, 1991-03-31, 1991-04-07", "1992, 1992-04-19, 1992-04-26", "1993, 1993-04-11, 1993-04-18",
			"1994, 1994-04-03, 1994-05-01", "1995, 1995-04-16, 1995-04-23", "1996, 1996-04-07, 1996-04-14",
			"1997, 1997-03-30, 1997-04-27", "1998, 1998-04-12, 1998-04-19", "1999, 1999-04-04, 1999-04-11",
			"2000, 2000-04-23, 2000-04-30", "2001, 2001-04-15, 2001-04-15", "2002, 2002-03-31, 2002-05-05",
			"2003, 2003-04-20, 2003-04-27", "2004, 2004-04-11, 2004-04-11", "2005, 2005-03-27, 2005-05-01",
			"2006, 2006-04-16, 2006-04-23", "2007, 2007-04-08, 2007-04-08", "2008, 2008-03-23, 2008-04-27",
			"2009, 2009-04-12, 2009-04-19", "2010, 2010-04-04, 2010-04-04", "2011, 2011-04-24, 2011-04-24",
			"2012, 2012-04-08, 2012-04-15", "2013, 2013-03-31, 2013-05-05", "2014, 2014-04-20, 2014-04-20",
			"2015, 2015-04-05, 2015-04-12", "2016, 2016-03-27, 2016-05-01", "2017, 2017-04-16, 2017-04-16",
			"2018, 2018-04-01, 2018-04-08", "2019, 2019-04-21, 2019-04-28", "2020, 2020-04-12, 2020-04-19",
			"2021, 2021-04-04, 2021-05-02", "2022, 2022-04-17, 2022-04-24", "2023, 2023-04-09, 2023-04-16",
			"2024, 2024-03-31, 2024-05-05", "2025, 2025-04-20, 2025-04-20", "2026, 2026-04-05, 2026-04-12",
			"2027, 2027-03-28, 2027-05-02", "2028, 2028-04-16, 2028-04-16", "2029, 2029-04-01, 2029-04-08",
			"2030, 2030-04-21, 2030-04-28", "2031, 2031-04-13, 2031-04-13", "2032, 2032-03-28, 2032-05-02",
			"2033, 2033-04-17, 2033-04-24", "2034, 2034-04-09, 2034-04-09", "2035, 2035-03-25, 2035-04-29",
			"2036, 2036-04-13, 2036-04-20", "2037, 2037-04-05, 2037-04-05", "2038, 2038-04-25, 2038-04-25",
			"2039, 2039-04-10, 2039-04-17", "2040, 2040-04-01, 2040-05-06", "2041, 2041-04-21, 2041-04-21",
			"2042, 2042-04-06, 2042-04-13", "2043, 2043-03-29, 2043-05-03", "2044, 2044-04-17, 2044-04-24",
			"2045, 2045-04-09, 2045-04-09", "2046, 2046-03-25, 2046-04-29", "2047, 2047-04-14, 2047-04-21",
			"2048, 2048-04-05, 2048-04-05", "2049, 2049-04-18, 2049-04-25", "2050, 2050-04-10, 2050-04-17",
			"2051, 2051-04-02, 2051-05-07", "2052, 2052-04-21, 2052-04-21", "2053, 2053-04-06, 2053-04-13",
			"2054, 2054-03-29, 2054-05-03", "2055, 2055-04-18, 2055-04-18", "2056, 2056-04-02, 2056-04-09",
			"2057, 2057-04-22, 2057-04-29", "2058, 2058-04-14, 2058-04-14", "2059, 2059-03-30, 2059-05-04",
			"2060, 2060-04-18, 2060-04-25", "2061, 2061-04-10, 2061-04-10", "2062, 2062-03-26, 2062-04-30",
			"2063, 2063-04-15, 2063-04-22", "2064, 2064-04-06, 2064-04-13", "2065, 2065-03-29, 2065-04-26",
			"2066, 2066-04-11, 2066-04-18", "2067, 2067-04-03, 2067-04-10", "2068, 2068-04-22, 2068-04-29",
			"2069, 2069-04-14, 2069-04-14", "2070, 2070-03-30, 2070-05-04", "2071, 2071-04-19, 2071-04-19",
			"2072, 2072-04-10, 2072-04-10", "2073, 2073-03-26, 2073-04-30", "2074, 2074-04-15, 2074-04-22",
			"2075, 2075-04-07, 2075-04-07", "2076, 2076-04-19, 2076-04-26", "2077, 2077-04-11, 2077-04-18",
			"2078, 2078-04-03, 2078-05-08", "2079, 2079-04-23, 2079-04-23", "2080, 2080-04-07, 2080-04-14",
			"2081, 2081-03-30, 2081-05-04", "2082, 2082-04-19, 2082-04-19", "2083, 2083-04-04, 2083-04-11",
			"2084, 2084-03-26, 2084-04-30", "2085, 2085-04-15, 2085-04-15", "2086, 2086-03-31, 2086-04-07",
			"2087, 2087-04-20, 2087-04-27", "2088, 2088-04-11, 2088-04-18", "2089, 2089-04-03, 2089-05-01",
			"2090, 2090-04-16, 2090-04-23", "2091, 2091-04-08, 2091-04-08", "2092, 2092-03-30, 2092-04-27",
			"2093, 2093-04-12, 2093-04-19", "2094, 2094-04-04, 2094-04-11", "2095, 2095-04-24, 2095-04-24",
			"2096, 2096-04-15, 2096-04-15", "2097, 2097-03-31, 2097-05-05", "2098, 2098-04-20, 2098-04-27",
			"2099, 2099-04-12, 2099-04-12"
	}, nullValues = "NULL")
	void easterDaters(int year, LocalDate expectedGaussEaster, LocalDate expectedOrthodoxEaster) {
		assertThat(Easter.gauss(year)).isEqualTo(expectedGaussEaster);
		assertThat(Easter.orthodox(year)).isEqualTo(expectedOrthodoxEaster);
	}

}

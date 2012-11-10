/*
 * Copyright 2012 Asset Data (info--at--assetdata.it)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.assetdata.lole.it;

import it.assetdata.lole.common.Sex;

import java.util.Arrays;
import java.util.Locale;

import org.joda.time.LocalDate;

/**
 * Builder of {@link FiscalCode} instances.<br/>
 * Does NOT accept {@code null} parameters.<br/>
 * NOT thread-safe.
 */
public class FiscalCodeBuilder {
	
	/**
	 * {@link #controlCharacterValue} starting index.
	 */
	private static final int controlCharacterValueIndex = 15;
	
	/**
	 * Efficient {@code int} to {@code char[]} converter.
	 */
	private static final IntegerRadix10ToCharArrayConverter converter = new IntegerRadix10ToCharArrayConverter();
	/**
	 * {@link #birthDate} day value starting index.
	 */
	private static final int dayValueIndex = 9;
	/**
	 * Allowed digits number, it is from 1 to 9.
	 */
	private static final int digitsLength = 9;
	/**
	 * {@link #value} even characters coding table to calculate {@link #controlCharacterValue}.
	 */
	private static final int[] evenCharactersValueTable = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
			13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25 };
	
	/**
	 * Females add this value to their date of birth day of month.
	 */
	private static final int femaleDayValueAddend = 40;
	/**
	 * Leading '0' for days of month from 1 to 9.
	 */
	private static final char leadingZero = '0';
	
	/**
	 * {@link #birthDate} month value coding table.
	 */
	private static final char[] monthsValueTable = { 'a', 'b', 'c', 'd', 'e', 'h', 'l', 'm', 'p', 'r', 's', 't' };
	
	/**
	 * {@link #birthDate} month value starting index.
	 */
	private static final int monthValueIndex = 8;
	
	/**
	 * {@link #name} value starting index.
	 */
	private static final int nameValueIndex = 3;
	
	/**
	 * {@link #value} odd characters coding table to calculate {@link #controlCharacterValue}.
	 */
	private static final int[] oddCharactersValueTable = { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2,
			4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };
	
	/**
	 * {@link #surname} value starting index.
	 */
	private static final int surnameValueIndex = 0;
	
	/**
	 * UTF-16 latin lower case letters "before the first" code number.
	 */
	private static final int utf16LowercaseLettersOffset = 39;
	
	/**
	 * UTF-16 numbers "before the first" code number.
	 */
	private static final int utf16NumberOffset = 48;
	
	/**
	 * Fiscal code value length.
	 */
	private static final int valueLength = 16;
	
	/**
	 * Character used to fill {@link #name} or {@link #surname} value if shorter then {@link #xameValueLength}.
	 */
	private static final char xameFillCharacter = 'x';
	
	/**
	 * {@link #name} or {@link #surname} value length.
	 */
	private static final int xameValueLength = 3;
	
	/**
	 * {@link #birthDate} year value starting index.
	 */
	private static final int yearValueIndex = 6;
	
	/**
	 * Referred person's date of birth used to create fiscal code {@link #value}.
	 */
	private LocalDate birthDate;
	
	/**
	 * Control character.
	 */
	private Character controlCharacterValue;
	
	/**
	 * Referred person's name used to create fiscal code {@link #value}.
	 */
	private String name;
	
	/**
	 * Referred person's place of birth used to create fiscal code {@link #value}.
	 */
	private String placeCode;
	
	/**
	 * Place code starting index.
	 */
	private final int placeCodeValueIndex = 11;
	
	/**
	 * Referred person's sex used to create fiscal code {@link #value}.
	 */
	private Sex sex;
	
	/**
	 * Referred person's surname used to create fiscal code {@link #value}.
	 */
	private String surname;
	
	/**
	 * Fiscal code value.
	 */
	private final char[] value = new char[FiscalCodeBuilder.valueLength];
	
	/**
	 * Add the control character to fiscal code {@link #value} and save it in {@link #controlCharacterValue}.
	 */
	private void addControlCharacterValue() {
		// Convert every value's character in a number: numbers map to
		// themselves (ie. 0=0, 1=1) and letters start after them (ie. a=11, b=12).
		int controlCharacterIndex = 0;
		for (int i = 0; i < (this.value.length - 1); i++) {
			int character = this.value[i];
			character -= FiscalCodeBuilder.utf16NumberOffset;
			if (FiscalCodeBuilder.digitsLength < character) {
				character -= (FiscalCodeBuilder.utf16LowercaseLettersOffset);
			}
			// Encode every number-converted value's character based on it's position.
			if ((i % 2) == 0) { // Inverted cause zero-based!
				controlCharacterIndex += FiscalCodeBuilder.oddCharactersValueTable[character];
			} else {
				controlCharacterIndex += FiscalCodeBuilder.evenCharactersValueTable[character];
			}
		}
		// Convert the total sum in a letter.
		controlCharacterIndex %= Alphabet.ALPHABET.length;
		final char controlCharacter = Alphabet.ALPHABET[controlCharacterIndex];
		// Save it.
		this.value[FiscalCodeBuilder.controlCharacterValueIndex] = controlCharacter;
		this.controlCharacterValue = Character.valueOf(controlCharacter);
	}
	
	/**
	 * Add {@link #birthDate}'s day value to fiscal code {@link #value}.
	 */
	private void addDayValue() {
		int day = this.birthDate.getDayOfMonth();
		if (this.sex == Sex.FEMALE) { // Females sum a special addend.
			day += FiscalCodeBuilder.femaleDayValueAddend;
		}
		final char[] dayCharArray = FiscalCodeBuilder.converter.convert(Integer.valueOf(day));
		if (dayCharArray.length == 1) { // Day of month from 1 to 9, so add leading 0.
			this.value[FiscalCodeBuilder.dayValueIndex] = FiscalCodeBuilder.leadingZero;
			this.value[FiscalCodeBuilder.dayValueIndex + 1] = dayCharArray[0];
		} else { // Day of month from 10 to 31.
			this.value[FiscalCodeBuilder.dayValueIndex] = dayCharArray[0];
			this.value[FiscalCodeBuilder.dayValueIndex + 1] = dayCharArray[1];
		}
	}
	
	/**
	 * Add {@link #birthDate}'s month value to fiscal code {@link #value}.
	 */
	private void addMonthValue() {
		this.value[FiscalCodeBuilder.monthValueIndex] = FiscalCodeBuilder.monthsValueTable[this.birthDate.getMonthOfYear() - 1];
	}
	
	/**
	 * Add name value to fiscal code {@link #value}.
	 */
	private void addNameValue() {
		this.addXameValue(this.name, FiscalCodeBuilder.nameValueIndex);
	}
	
	/**
	 * Add place code value to fiscal code {@link #value}.
	 */
	private void addPlaceCodeValue() {
		final char[] placeCodeValue = this.placeCode.toCharArray();
		System.arraycopy(placeCodeValue, 0, this.value, this.placeCodeValueIndex, placeCodeValue.length); // Simply copy char by char.
	}
	
	/**
	 * Add {@link #surname} value to fiscal code {@link #value}.
	 */
	private void addSurnameValue() {
		this.addXameValue(this.surname, FiscalCodeBuilder.surnameValueIndex);
	}
	
	/**
	 * Add {@link #name} or {@link #surname} value to fiscal code {@link #value}.
	 * 
	 * @param xame
	 *            {@link #name} or {@link #surname}
	 * @param xameValueIndex
	 *            {@link #nameValueIndex} or {@link #surnameValueIndex}
	 */
	private void addXameValue(final String xame, final int xameValueIndex) {
		int xameValueIndexLocal = 0;
		for (int i = 0; (i < xame.length()) && (xameValueIndexLocal < FiscalCodeBuilder.xameValueLength); i++) { // Look for at most xameIndexLimit consonants.
			final char character = xame.charAt(i);
			final int consonantIndex = Arrays.binarySearch(Alphabet.CONSONANTS, character);
			if (-1 < consonantIndex) {
				this.value[xameValueIndex + xameValueIndexLocal] = character;
				xameValueIndexLocal++;
			}
		}
		for (; xameValueIndexLocal < FiscalCodeBuilder.xameValueLength; xameValueIndexLocal++) { // Fill with xameFillCharacter if less then xameValueLength consonants were found.
			this.value[xameValueIndexLocal] = FiscalCodeBuilder.xameFillCharacter;
		}
	}
	
	/**
	 * Add {@link #birthDate}'s year value to fiscal code {@link #value}.
	 */
	private void addYearValue() {
		final int year = this.birthDate.getYear();
		final char[] yearCharArray = FiscalCodeBuilder.converter.convert(Integer.valueOf(year));
		this.value[FiscalCodeBuilder.yearValueIndex] = yearCharArray[yearCharArray.length - 2];
		this.value[FiscalCodeBuilder.yearValueIndex + 1] = yearCharArray[yearCharArray.length - 1];
	}
	
	/**
	 * Build a new {@link FiscalCode} using parameters given by setters methods.
	 * 
	 * @return new {@link FiscalCode}
	 * @throws IllegalStateException
	 *             if any parameters isn't setted yet
	 */
	public FiscalCode build() throws IllegalStateException {
		this.addSurnameValue();
		this.addNameValue();
		this.addYearValue();
		this.addMonthValue();
		this.addDayValue();
		this.addPlaceCodeValue();
		this.addControlCharacterValue();
		// build
		return new FiscalCode(this.birthDate, this.controlCharacterValue, this.name, this.placeCode, this.sex, this.surname, String.valueOf(this.value));
	}
	
	/**
	 * Return the date of birth used to create fiscal code.
	 * 
	 * @return date of birth
	 */
	public LocalDate getBirthDate() {
		return this.birthDate;
	}
	
	/**
	 * Return the name used to create fiscal code.
	 * 
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Return the place code used to create fiscal code.
	 * 
	 * @return place code
	 */
	public String getPlaceCode() {
		return this.placeCode;
	}
	
	/**
	 * Return the sex used to create fiscal code.
	 * 
	 * @return sex
	 */
	public Sex getSex() {
		return this.sex;
	}
	
	/**
	 * Return the surname used to create fiscal code.
	 * 
	 * @return surname
	 */
	public String getSurname() {
		return this.surname;
	}
	
	/**
	 * Set the date of birth used to create fiscal code.
	 * 
	 * @param birthDate
	 *            date of birth to set
	 */
	public void setBirthDate(final LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	/**
	 * Set the name used to create fiscal code.
	 * 
	 * @param name
	 *            to set
	 */
	public void setName(final String name) {
		this.name = name.toLowerCase(Locale.ITALIAN);
	}
	
	/**
	 * Set the place code used to create fiscal code.
	 * 
	 * @param placeCode
	 *            place code to set
	 */
	public void setPlaceCode(final String placeCode) {
		this.placeCode = placeCode.toLowerCase(Locale.ITALIAN);
	}
	
	/**
	 * Set the sex used to create fiscal code.
	 * 
	 * @param sex
	 *            to set
	 */
	public void setSex(final Sex sex) {
		this.sex = sex;
	}
	
	/**
	 * Set the surname used to create fiscal code.
	 * 
	 * @param surname
	 *            to set
	 */
	public void setSurname(final String surname) {
		this.surname = surname.toLowerCase(Locale.ITALIAN);
	}
	
}

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

import it.assetdata.convert.Converter;
import it.assetdata.convert.IntegerToRadix10CharArrayConverter;
import it.assetdata.lole.common.Sex;
import it.assetdata.valid.Conditions;

import java.util.Random;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.LocalDate;

import com.google.common.collect.ImmutableList;

/**
 * Builder of {@link FiscalCode} instances.<br/>
 * Note that there is NO REAL/OFFICIAL generator for Italian fiscal codes as there are no reproducible rules or data to rely on.<br/>
 * <br/>
 * Does NOT accept {@code null} parameters.<br/>
 * NOT thread-safe.
 */
public class FiscalCodeBuilder {
	
	/**
	 * Conditions check utility.
	 */
	private static final Conditions conditions = new Conditions();
	
	/**
	 * {@link #controlCharacterValue} starting index.
	 */
	private static final int controlCharacterValueIndex = 15;
	
	/**
	 * Efficient {@code Integer} to {@code char[]} converter.
	 */
	private static final Converter<Integer, char[]> converter = new IntegerToRadix10CharArrayConverter();
	
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
	private static final int[] evenCharactersValueTable = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25 };
	
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
	private static final char[] monthsValueTable = { 'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T' };
	
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
	private static final int[] oddCharactersValueTable = { 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };
	
	/**
	 * Place code starting index.
	 */
	private static final int placeCodeValueIndex = 11;
	
	/**
	 * {@link #surname} value starting index.
	 */
	private static final int surnameValueIndex = 0;
	
	/**
	 * Temporary fiscal code value length.
	 */
	private static final int temporaryValueLength = 11;
	
	/**
	 * Reference upper case alphabet.
	 */
	private static final ImmutableList<Character> upperCaseAlphabet = ItalianAlphabet.getInstance().getUpperCaseAlphabet();
	
	/**
	 * Reference upper case vowels.
	 */
	private static final ImmutableList<Character> upperCaseVowels = ItalianAlphabet.getInstance().getUpperCaseVowels();
	
	/**
	 * UTF-16 numbers "before the first" code number.
	 */
	private static final int utf16NumberOffset = 48;
	
	/**
	 * UTF-16 latin upper case letters "before the first" code number.
	 */
	private static final int utf16UpperCaseLettersOffset = 7;
	
	/**
	 * Fiscal code value length.
	 */
	private static final int valueLength = 16;
	
	/**
	 * Character used to fill {@link #name} or {@link #surname} value if shorter then {@link #xameValueLength}.
	 */
	private static final char xameFillCharacter = 'X';
	
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
	private final char[] value = new char[valueLength];
	
	/**
	 * Constructor.
	 */
	public FiscalCodeBuilder() {
	}
	
	/**
	 * Adds the control character to fiscal code {@link #value} and saves it in {@link #controlCharacterValue}.
	 */
	private void addControlCharacterValue() {
		// Convert every value's character in a number: numbers map to themselves (ie. 0=0, 1=1) and letters start after them (ie. a=11, b=12).
		int controlCharacterIndex = 0;
		for (int i = 0; i < (value.length - 1); i++) {
			int character = value[i];
			character -= utf16NumberOffset;
			if (digitsLength < character) {
				character -= (utf16UpperCaseLettersOffset);
			}
			// Encode every number-converted value's character based on it's position.
			if ((i % 2) == 0) { // Inverted cause zero-based!
				controlCharacterIndex += oddCharactersValueTable[character];
			} else {
				controlCharacterIndex += evenCharactersValueTable[character];
			}
		}
		// Convert the total sum in a letter.
		controlCharacterIndex %= upperCaseAlphabet.size();
		final char controlCharacter = upperCaseAlphabet.get(controlCharacterIndex).charValue();
		// Save it.
		value[controlCharacterValueIndex] = controlCharacter;
		controlCharacterValue = Character.valueOf(controlCharacter);
	}
	
	/**
	 * Adds the control character to temporary fiscal code {@link #value} and saves it in {@link #controlCharacterValue}.
	 */
	private void addControlCharacterValueTemporary() {
		// Convert every value's character in a number: numbers map to themselves (ie. 0=0, 1=1).
		int evenSum = 0;
		int oddSum = 0;
		for (int i = 0; i < (value.length - 1); i++) {
			int character = value[i];
			character -= utf16NumberOffset;
			// Encode every number-converted value's character based on it's position.
			if ((i % 2) == 0) { // Inverted cause zero-based!
				oddSum += character;
			} else {
				// Double.
				character = character * 2;
				if (10 < character) { // If two digits sum each other.
					final char[] characterArray = converter.convert(Integer.valueOf(character));
					character = evenCharactersValueTable[characterArray[0]] + evenCharactersValueTable[characterArray[1]];
				}
				evenSum += character;
			}
		}
		int totalSum = evenSum + oddSum;
		// Convert the total sum in a character.
		final char[] totalSumArray = converter.convert(Integer.valueOf(totalSum));
		totalSum = 10 - evenCharactersValueTable[totalSumArray[0]];
		final char controlCharacter = converter.convert(Integer.valueOf(totalSum))[0];
		// Save it.
		value[controlCharacterValueIndex] = controlCharacter;
		controlCharacterValue = Character.valueOf(controlCharacter);
	}
	
	/**
	 * Adds {@link #birthDate}'s day value to fiscal code {@link #value}.
	 */
	private void addDayValue() {
		int day = birthDate.getDayOfMonth();
		if (sex == Sex.FEMALE) { // Females sum a special addend.
			day += femaleDayValueAddend;
		}
		final char[] dayCharArray = converter.convert(Integer.valueOf(day));
		if (dayCharArray.length == 1) { // Day of month from 1 to 9, so add leading 0.
			value[dayValueIndex] = leadingZero;
			value[dayValueIndex + 1] = dayCharArray[0];
		} else { // Day of month from 10 to 31.
			value[dayValueIndex] = dayCharArray[0];
			value[dayValueIndex + 1] = dayCharArray[1];
		}
	}
	
	/**
	 * Adds {@link #birthDate}'s month value to fiscal code {@link #value}.
	 */
	private void addMonthValue() {
		value[monthValueIndex] = monthsValueTable[birthDate.getMonthOfYear() - 1];
	}
	
	/**
	 * Adds name value to fiscal code {@link #value}.
	 */
	private void addNameValue() {
		addXameValue(name, nameValueIndex);
	}
	
	/**
	 * Adds place code value to fiscal code {@link #value}.
	 */
	private void addPlaceCodeValue() {
		final char[] placeCodeValue = placeCode.toCharArray();
		System.arraycopy(placeCodeValue, 0, value, placeCodeValueIndex, placeCodeValue.length); // Simply copy char by char.
	}
	
	/**
	 * Adds {@link #surname} value to fiscal code {@link #value}.
	 */
	private void addSurnameValue() {
		addXameValue(surname, surnameValueIndex);
	}
	
	/**
	 * Adds {@link #name} or {@link #surname} value to fiscal code {@link #value}.
	 * 
	 * @param xame
	 *            {@link #name} or {@link #surname}
	 * @param xameValueIndex
	 *            {@link #nameValueIndex} or {@link #surnameValueIndex}
	 */
	private void addXameValue(final String xame, final int xameValueIndex) {
		int xameValueIndexLocal = 0;
		for (int i = 0; (i < xame.length()) && (xameValueIndexLocal < xameValueLength); i++) { // Look for at most xameIndexLimit consonants.
			final char character = xame.charAt(i);
			final int consonantIndex = upperCaseVowels.indexOf(Character.valueOf(character));
			if (consonantIndex == -1) {
				value[xameValueIndex + xameValueIndexLocal] = character;
				xameValueIndexLocal++;
			}
		}
		for (int i = 0; (i < xame.length()) && (xameValueIndexLocal < xameValueLength); i++) { // Look for at most xameIndexLimit vowels.
			final char character = xame.charAt(i);
			final int consonantIndex = upperCaseVowels.indexOf(Character.valueOf(character));
			if (consonantIndex != -1) {
				value[xameValueIndex + xameValueIndexLocal] = character;
				xameValueIndexLocal++;
			}
		}
		for (; xameValueIndexLocal < xameValueLength; xameValueIndexLocal++) { // Fill with xameFillCharacter if less then xameValueLength consonants were found.
			value[xameValueIndexLocal] = xameFillCharacter;
		}
	}
	
	/**
	 * Adds {@link #birthDate}'s year value to fiscal code {@link #value}.
	 */
	private void addYearValue() {
		final int year = birthDate.getYear();
		final char[] yearCharArray = converter.convert(Integer.valueOf(year));
		value[yearValueIndex] = yearCharArray[yearCharArray.length - 2];
		value[yearValueIndex + 1] = yearCharArray[yearCharArray.length - 1];
	}
	
	/**
	 * Builds a new {@link FiscalCode} using parameters given by setters methods.
	 * 
	 * @return new {@link FiscalCode}
	 * @throws NullPointerException
	 *             if any parameters isn't set yet
	 */
	public FiscalCode build() throws NullPointerException {
		conditions.checkNotNull(birthDate);
		conditions.checkNotNull(controlCharacterValue);
		conditions.checkNotNull(name);
		conditions.checkNotNull(placeCode);
		conditions.checkNotNull(sex);
		conditions.checkNotNull(surname);
		addSurnameValue();
		addNameValue();
		addYearValue();
		addMonthValue();
		addDayValue();
		addPlaceCodeValue();
		addControlCharacterValue();
		return new FiscalCode(birthDate, controlCharacterValue, name, placeCode, sex, surname, String.valueOf(value));
	}
	
	/**
	 * Builds the fiscal code control character of {@code value}.
	 * 
	 * @param value
	 *            of the fiscal code
	 * @return control character
	 */
	public Character buildControlCharacter(@SuppressWarnings("hiding") final char[] value) {
		conditions.checkNotNull(value);
		if (value.length < valueLength) {
			conditions.checkInRange(value.length, temporaryValueLength - 1, temporaryValueLength);
			System.arraycopy(value, 0, this.value, 0, value.length);
			addControlCharacterValueTemporary();
		} else {
			conditions.checkInRange(value.length, valueLength - 1, valueLength);
			System.arraycopy(value, 0, this.value, 0, value.length);
			addControlCharacterValue();
		}
		return controlCharacterValue;
	}
	
	/**
	 * Builds a new temporary {@link FiscalCode}, useful for test purposes.
	 * 
	 * @return new {@link FiscalCode}
	 * @throws NullPointerException
	 *             if any parameters isn't set yet
	 */
	public FiscalCode buildTemporary() {
		conditions.checkNotNull(birthDate);
		conditions.checkNotNull(controlCharacterValue);
		conditions.checkNotNull(name);
		conditions.checkNotNull(placeCode);
		conditions.checkNotNull(sex);
		conditions.checkNotNull(surname);
		final Random random = new Random();
		for (int i = 0; i < 11; i++) {
			final int nextInt = random.nextInt(9);
			value[i] = converter.convert(Integer.valueOf(nextInt))[0];
		}
		addControlCharacterValueTemporary();
		return new FiscalCode(birthDate, controlCharacterValue, name, placeCode, sex, surname, String.valueOf(value));
	}
	
	/**
	 * Returns the date of birth used to create fiscal code.
	 * 
	 * @return date of birth
	 */
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	/**
	 * Returns the name used to create fiscal code.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the place code used to create fiscal code.
	 * 
	 * @return place code
	 */
	public String getPlaceCode() {
		return placeCode;
	}
	
	/**
	 * Returns the sex used to create fiscal code.
	 * 
	 * @return sex
	 */
	public Sex getSex() {
		return sex;
	}
	
	/**
	 * Returns the surname used to create fiscal code.
	 * 
	 * @return surname
	 */
	public String getSurname() {
		return surname;
	}
	
	/**
	 * Sets the date of birth used to create fiscal code.
	 * 
	 * @param birthDate
	 *            date of birth to set
	 * @throws IllegalArgumentException
	 *             if {@code birthDate} is {@code null}
	 */
	public void setBirthDate(final LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	
	/**
	 * Sets the name used to create fiscal code.
	 * 
	 * @param name
	 *            to set
	 * @throws IllegalArgumentException
	 *             if {@code birthDate} is {@code null}
	 */
	public void setName(final String name) throws IllegalArgumentException {
		this.name = conditions.checkNotNull(name);
	}
	
	/**
	 * Sets the place code used to create fiscal code.
	 * 
	 * @param placeCode
	 *            place code to set
	 * @throws IllegalArgumentException
	 *             if {@code birthDate} is {@code null}
	 */
	public void setPlaceCode(final String placeCode) throws IllegalArgumentException {
		this.placeCode = conditions.checkNotNull(placeCode);
	}
	
	/**
	 * Sets the sex used to create fiscal code.
	 * 
	 * @param sex
	 *            to set
	 * @throws IllegalArgumentException
	 *             if {@code birthDate} is {@code null}
	 */
	public void setSex(final Sex sex) throws IllegalArgumentException {
		this.sex = conditions.checkNotNull(sex);
	}
	
	/**
	 * Sets the surname used to create fiscal code.
	 * 
	 * @param surname
	 *            to set
	 * @throws IllegalArgumentException
	 *             if {@code birthDate} is {@code null}
	 */
	public void setSurname(final String surname) throws IllegalArgumentException {
		this.surname = conditions.checkNotNull(surname);
	}
	
	/**
	 * {@inheritDoc}<br/>
	 * <br/>
	 * This implementation uses reflection.
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}

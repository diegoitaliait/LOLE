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
package it.assetdata.lole.it.fiscalCode;

import it.assetdata.convert.Converter;
import it.assetdata.lole.it.SexIt;
import it.assetdata.valid.Conditions;

import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.LocalDate;

import com.google.common.collect.ImmutableList;

/**
 * This implementation is really fast and builds standard Italian fiscal codes
 * according to current Italian law as per 1/1/2012.
 */
@Immutable
public class FastFiscalCodeBuilder implements FiscalCodeBuilder {
	
	/**
	 * Control character starting index.
	 */
	private static final int controlCharacterValueIndex = 15;
	
	/**
	 * Day of birth value starting index.
	 */
	private static final int dayValueIndex = 9;
	
	/**
	 * Allowed digits number, it is from 1 to 9.
	 */
	private static final int digitsLength = 9;
	
	/**
	 * {@link #value} even characters coding table to calculate control
	 * character.
	 */
	private static final int[] evenCharactersValueTable = {
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
			13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25 };
	
	/**
	 * Leading "0" for days of month from 1 to 9.
	 */
	private static final char leadingZero = '0';
	
	/**
	 * Month of birth value coding table.
	 */
	private static final char[] monthsValueTable = {
			'A', 'B', 'C', 'D', 'E', 'H', 'L', 'M', 'P', 'R', 'S', 'T' };
	
	/**
	 * Month of birth value starting index.
	 */
	private static final int monthValueIndex = 8;
	
	/**
	 * Name value starting index.
	 */
	private static final int nameValueIndex = 3;
	
	/**
	 * {@link #value} odd characters coding table to calculate control
	 * character.
	 */
	private static final int[] oddCharactersValueTable = {
			1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13, 15, 17, 19, 21,
			2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };
	
	/**
	 * Place code starting index.
	 */
	private static final int placeCodeValueIndex = 11;
	
	/**
	 * Surname value starting index.
	 */
	private static final int surnameValueIndex = 0;
	
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
	 * Character used to fill name or surname value if shorter then
	 * {@link #xameValueLength}.
	 */
	private static final char xameFillCharacter = 'X';
	
	/**
	 * Name or surname value length.
	 */
	private static final int xameValueLength = 3;
	
	/**
	 * Year of birth value starting index.
	 */
	private static final int yearValueIndex = 6;
	
	/**
	 * Conditions check utility.
	 */
	private final Conditions conditions;
	
	/**
	 * {@code Integer} to {@code char[]} converter.
	 */
	private final Converter<Integer, char[]> converter;
	
	/**
	 * Utility to validate fiscal code parts.
	 */
	private final StandardFiscalCodeValidator fiscalCodeValidator;
	
	/**
	 * Reference upper case alphabet.
	 */
	private final ImmutableList<Character> upperCaseAlphabet;
	
	/**
	 * Reference upper case vowels.
	 */
	private final ImmutableList<Character> upperCaseVowels;
	
	/**
	 * Fiscal code value.
	 */
	private final char[] value;
	
	/**
	 * Constructor.
	 * 
	 * @param conditions
	 *            conditions check utility
	 * @param converter
	 *            {@code Integer} to {@code char[]} converter
	 * @param fiscalCodeValidator
	 *            utility to validate fiscal code parts
	 * @param upperCaseAlphabet
	 *            reference upper case alphabet
	 * @param upperCaseVowels
	 *            reference upper case vowels
	 */
	public FastFiscalCodeBuilder(
			final Conditions conditions,
			final Converter<Integer, char[]> converter,
			final StandardFiscalCodeValidator fiscalCodeValidator,
			final ImmutableList<Character> upperCaseAlphabet,
			final ImmutableList<Character> upperCaseVowels) {
		this.conditions = conditions;
		this.converter = converter;
		this.fiscalCodeValidator = fiscalCodeValidator;
		this.upperCaseAlphabet = upperCaseAlphabet;
		this.upperCaseVowels = upperCaseVowels;
		value = new char[valueLength];
	}
	
	/**
	 * Adds the control character to fiscal code {@link #value}.
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
	}
	
	/**
	 * Adds day of birth value to fiscal code {@link #value}.
	 * 
	 * @param birthDate
	 *            date of birth of the fiscal code referenced person
	 * @param sex
	 *            of the fiscal code referenced person
	 */
	private void addDayValue(final LocalDate birthDate, final SexIt sex) {
		int day = birthDate.getDayOfMonth();
		day += sex.getFiscalCodeDayAddend(); // Females sum a special addend.
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
	 * Adds month of birth value value to fiscal code {@link #value}.
	 * 
	 * @param birthDate
	 *            date of birth of the fiscal code referenced person
	 */
	private void addMonthValue(final LocalDate birthDate) {
		value[monthValueIndex] = monthsValueTable[birthDate.getMonthOfYear() - 1];
	}
	
	/**
	 * Adds name value to fiscal code {@link #value}.
	 * 
	 * @param name
	 *            of the fiscal code referenced person
	 */
	private void addNameValue(final String name) {
		addXameValue(name, nameValueIndex);
	}
	
	/**
	 * Adds place code value to fiscal code {@link #value}.
	 * 
	 * @param placeCode
	 *            of the fiscal code referenced person
	 */
	private void addPlaceCodeValue(final String placeCode) {
		final char[] placeCodeValue = placeCode.toCharArray();
		System.arraycopy(placeCodeValue, 0, value, placeCodeValueIndex, placeCodeValue.length); // Simply copy char by char.
	}
	
	/**
	 * Adds surname value to fiscal code {@link #value}.
	 * 
	 * @param surname
	 *            of the fiscal code referenced person
	 */
	private void addSurnameValue(final String surname) {
		addXameValue(surname, surnameValueIndex);
	}
	
	/**
	 * Adds name or surname value to fiscal code {@link #value}.
	 * 
	 * @param xame
	 *            name or surname of the fiscal code referenced person
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
	 * Adds year of birth value to fiscal code {@link #value}.
	 * 
	 * @param birthDate
	 *            date of birth of the fiscal code referenced person
	 */
	private void addYearValue(final LocalDate birthDate) {
		final int year = birthDate.getYear();
		final char[] yearCharArray = converter.convert(Integer.valueOf(year));
		value[yearValueIndex] = yearCharArray[yearCharArray.length - 2];
		value[yearValueIndex + 1] = yearCharArray[yearCharArray.length - 1];
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public FiscalCode build(
			final LocalDate birthDate,
			final Conditions conditions,
			final String name,
			final String placeCode,
			final SexIt sex,
			final String surname
			) throws IllegalArgumentException {
		// Validate parameters.
		this.conditions.expression(fiscalCodeValidator.validateBirthDate(birthDate));
		this.conditions.expression(fiscalCodeValidator.validateName(name));
		this.conditions.expression(fiscalCodeValidator.validatePlaceCode(placeCode));
		this.conditions.expression(fiscalCodeValidator.validateSex(sex));
		this.conditions.expression(fiscalCodeValidator.validateSurname(surname));
		// Build value.
		addSurnameValue(surname);
		addNameValue(name);
		addYearValue(birthDate);
		addMonthValue(birthDate);
		addDayValue(birthDate, sex);
		addPlaceCodeValue(placeCode);
		addControlCharacterValue();
		// Build result and return.
		final FiscalCode fiscalCode = new FiscalCode(
				birthDate,
				conditions,
				Character.valueOf(value[controlCharacterValueIndex]),
				name,
				placeCode,
				sex,
				surname,
				String.valueOf(value));
		return fiscalCode;
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

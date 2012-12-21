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

import it.assetdata.lole.it.SexIt;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.joda.time.LocalDate;

import com.google.common.collect.ImmutableList;

/**
 * This implementation validates standard, so no temporary or bad built,
 * Italian fiscal codes according to current Italian law as per 1/1/2012.
 */
@Immutable
public class TemporaryFiscalCodeValidator implements FiscalCodeValidator {
	
	/**
	 * Control character starting index.
	 */
	private static final int controlCharacterIndex = 10;
	
	/**
	 * Allowed digits number, it is from 1 to 9.
	 */
	private static final int digitsLength = 9;
	
	/**
	 * Fiscal code even characters coding table to calculate control
	 * character.
	 */
	private static final int[] evenCharactersValueTable = {
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
			13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25 };
	
	/**
	 * Fiscal code value length.
	 */
	private static final int fiscalCodeLength = 11;
	
	/**
	 * Fiscal code odd characters coding table to calculate control
	 * character.
	 */
	private static final int[] oddCharactersValueTable = {
			1, 0, 5, 7, 9, 13, 15, 17, 19, 21, 1, 0, 5, 7, 9, 13, 15, 17, 19, 21,
			2, 4, 18, 20, 11, 3, 6, 8, 12, 14, 16, 10, 22, 25, 24, 23 };
	
	/**
	 * {@link FiscalCode} place code length.
	 */
	private static final int placeCodeLength = 2;
	
	/**
	 * UTF-16 numbers "before the first" code number.
	 */
	private static final int utf16NumberOffset = 48;
	
	/**
	 * UTF-16 latin upper case letters "before the first" code number.
	 */
	private static final int utf16UpperCaseLettersOffset = 7;
	
	/**
	 * Reference upper case alphabet.
	 */
	private final ImmutableList<Character> upperCaseAlphabet;
	
	/**
	 * Constructor.
	 * 
	 * @param upperCaseAlphabet
	 *            reference upper case alphabet
	 */
	public TemporaryFiscalCodeValidator(final ImmutableList<Character> upperCaseAlphabet) {
		this.upperCaseAlphabet = upperCaseAlphabet;
	}
	
	/**
	 * Calculate the control character of a fiscal code.
	 * 
	 * @param fiscalCode
	 *            fiscal code to use, must be left untouched until this method
	 *            returns to avoid unpredictable behavior
	 * @return control character
	 */
	private char buildControlCharacter(final CharSequence fiscalCode) {
		// Convert every value's character in a number: numbers map to themselves (ie. 0=0, 1=1) and letters start after them (ie. a=11, b=12).
		int controlCharacterIndex = 0;
		for (int i = 0; i < (fiscalCode.length() - 1); i++) {
			int character = fiscalCode.charAt(i);
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
		// Return.
		return controlCharacter;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean validate(@Nullable final String fiscalCode) {
		return (fiscalCode != null) && (fiscalCode.length() == fiscalCodeLength) && (validateControlCharacter(fiscalCode));
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean validateBirthDate(@SuppressWarnings("unused") final @Nullable LocalDate birthDate) {
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean validateControlCharacter(final @Nullable CharSequence fiscalCode) {
		boolean result = false;
		if (fiscalCode != null) {
			final char controlCharacter = buildControlCharacter(fiscalCode);
			final char otherControlCharacter = fiscalCode.charAt(controlCharacterIndex);
			if (controlCharacter == otherControlCharacter) {
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean validateName(@SuppressWarnings("unused") final @Nullable String name) {
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean validatePlaceCode(final @Nullable String placeCode) {
		return (placeCode != null) && (placeCode.length() == placeCodeLength);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean validateSex(@SuppressWarnings("unused") final @Nullable SexIt sex) {
		return true;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean validateSurname(@SuppressWarnings("unused") final @Nullable String surname) {
		return true;
	}
	
}

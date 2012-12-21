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

import java.util.Random;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.LocalDate;

/**
 * This implementation builds temporary Italian fiscal codes according to
 * current Italian law as per 1/1/2012 using number generator.
 */
@Immutable
public class TemporaryFiscalCodeBuilder implements FiscalCodeBuilder {
	
	/**
	 * Control character starting index.
	 */
	private static final int controlCharacterValueIndex = 10;
	
	/**
	 * {@link #value} even characters coding table to calculate control
	 * character.
	 */
	private static final int[] evenCharactersValueTable = {
			0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
			13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25 };
	
	/**
	 * UTF-16 numbers "before the first" code number.
	 */
	private static final int utf16NumberOffset = 48;
	
	/**
	 * Temporary fiscal code value length.
	 */
	private static final int valueLength = 11;
	
	/**
	 * {@code Integer} to {@code char[]} converter.
	 */
	private final Converter<Integer, char[]> converter;
	
	/**
	 * Numbers generator.
	 */
	private final @Nullable
	Random numberGenerator;
	
	/**
	 * Fiscal code value.
	 */
	private final char[] value;
	
	/**
	 * Constructor.
	 * 
	 * @param converter
	 *            {@code Integer} to {@code char[]} converter
	 * @param numberGenerator
	 *            to generate fiscal code value
	 */
	public TemporaryFiscalCodeBuilder(final Converter<Integer, char[]> converter, final @Nullable Random numberGenerator) {
		this.converter = converter;
		this.numberGenerator = numberGenerator;
		value = new char[valueLength];
	}
	
	/**
	 * Adds the control character to temporary fiscal code {@link #value}.
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
		for (int i = 0; i < valueLength; i++) {
			final int nextInt = numberGenerator.nextInt(9);
			value[i] = converter.convert(Integer.valueOf(nextInt))[0];
		}
		addControlCharacterValueTemporary();
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

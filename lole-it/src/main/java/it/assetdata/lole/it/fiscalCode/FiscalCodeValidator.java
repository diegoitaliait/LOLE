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

/**
 * Validate {@link FiscalCode} correctness.<br/>
 * Note that there is NO REAL/OFFICIAL validator for Italian fiscal codes as
 * there are no reproducible rules or data to rely on.
 */
@Immutable
public interface FiscalCodeValidator {
	
	/**
	 * Checks if {@code fiscalCode} is valid.
	 * 
	 * @param fiscalCode
	 *            to validate
	 * @return {@code true} if it is valid, {@code false} otherwise
	 */
	public boolean validate(final @Nullable String fiscalCode);
	
	/**
	 * Checks if {@code birthDate} is valid.
	 * 
	 * @param birthDate
	 *            to validate
	 * @return {@code true} if it is valid, {@code false} otherwise
	 */
	public boolean validateBirthDate(final @Nullable LocalDate birthDate);
	
	/**
	 * Checks if {@code fiscalCode} has a valid control character.
	 * 
	 * @param fiscalCode
	 *            to check
	 * @return {@code true} if it is valid, {@code false} otherwise
	 */
	public boolean validateControlCharacter(final @Nullable CharSequence fiscalCode);
	
	/**
	 * Checks if {@code name} is valid.
	 * 
	 * @param name
	 *            to validate
	 * @return {@code true} if it is valid, {@code false} otherwise
	 */
	public boolean validateName(final @Nullable String name);
	
	/**
	 * Checks if {@code placeCode} is valid.
	 * 
	 * @param placeCode
	 *            to validate
	 * @return {@code true} if it is valid, {@code false} otherwise
	 */
	public boolean validatePlaceCode(final @Nullable String placeCode);
	
	/**
	 * Checks if {@code sex} is valid.
	 * 
	 * @param sex
	 *            to validate
	 * @return {@code true} if it is valid, {@code false} otherwise
	 */
	public boolean validateSex(final @Nullable SexIt sex);
	
	/**
	 * Checks if {@code surname} is valid.
	 * 
	 * @param surname
	 *            to validate
	 * @return {@code true} if it is valid, {@code false} otherwise
	 */
	public boolean validateSurname(final @Nullable String surname);
	
}

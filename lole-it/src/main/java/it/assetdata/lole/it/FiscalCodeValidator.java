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

/**
 * Validate {@link FiscalCode} correctness and {@link FiscalCodeBuilder} fields.
 */
public class FiscalCodeValidator {
	
	/**
	 * Checks if {@code fiscalCode} has a valid control character.
	 * 
	 * @param fiscalCode
	 *            to check
	 * @return {@code true} if it is valid, {@code false} otherwise
	 */
	public boolean validateControlCharacter(final FiscalCode fiscalCode) {
		final FiscalCodeBuilder fiscalCodeBuilder = new FiscalCodeBuilder();
		final Character controlCharacter = fiscalCodeBuilder.buildControlCharacter(fiscalCode.getValue().toCharArray());
		boolean result = false;
		if (controlCharacter.equals(fiscalCode.getControlCharacter())) {
			result = true;
		}
		return result;
	}
	
}

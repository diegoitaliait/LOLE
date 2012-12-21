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
import it.assetdata.valid.Conditions;

import javax.annotation.concurrent.Immutable;

import org.joda.time.LocalDate;

/**
 * Interface of {@link FiscalCode} instances builders.<br/>
 * Note that there is NO REAL/OFFICIAL generator for Italian fiscal codes as
 * there are no reproducible rules or data to rely on.<br/>
 */
@Immutable
public interface FiscalCodeBuilder {
	
	/**
	 * Builds a new {@link FiscalCode} using parameters given by setters
	 * methods.
	 * 
	 * @param birthDate
	 *            date of birth of the fiscal code referenced person
	 * @param conditions
	 *            check utility
	 * @param name
	 *            of the fiscal code referenced person
	 * @param placeCode
	 *            place of birth's code of the fiscal code referenced person
	 * @param sex
	 *            of the fiscal code referenced person
	 * @param surname
	 *            of the person
	 * @return new {@link FiscalCode}
	 * @throws IllegalArgumentException
	 *             if any parameter is not valid
	 */
	public FiscalCode build(
			LocalDate birthDate,
			Conditions conditions,
			String name,
			String placeCode,
			SexIt sex,
			String surname
			) throws IllegalArgumentException;
	
}

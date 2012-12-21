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

import java.io.Serializable;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.LocalDate;

import com.google.common.collect.ComparisonChain;

/**
 * Represents an Italian fiscal code (Codice fiscale) which refers to a physical
 * person.
 */
@Immutable
public class FiscalCode implements Serializable, Cloneable, Comparable<FiscalCode> {
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Referred person's date of birth used to create {@link #value}.
	 */
	private final LocalDate birthDate;
	
	/**
	 * Conditions check utility.
	 */
	private final Conditions conditions;
	
	/**
	 * A control character used to validate the whole fiscal code and added to
	 * its end.
	 */
	private final Character controlCharacter;
	
	/**
	 * Referred person's name used to create {@link #value}.
	 */
	private final String name;
	
	/**
	 * Referred person's place of birth used to create {@link #value}.
	 */
	private final String placeCode;
	
	/**
	 * Referred person's sex used to create {@link #value}.
	 */
	private final SexIt sex;
	
	/**
	 * Referred person's surname used to create {@link #value}.
	 */
	private final String surname;
	
	/**
	 * A {@link String} representation of the fiscal code value.
	 */
	private final String value;
	
	/**
	 * Constructor.<br/>
	 * Does NOT accept {@code null} parameters.
	 * 
	 * @param birthDate
	 *            referred person's date of birth
	 * @param conditions
	 *            check utility
	 * @param controlCharacter
	 *            control character
	 * @param name
	 *            of the referred person
	 * @param placeCode
	 *            referred person's place of birth code
	 * @param sex
	 *            of the referred person
	 * @param surname
	 *            of the referred person
	 * @param value
	 *            representation of the whole fiscal code
	 */
	public FiscalCode(
			final LocalDate birthDate,
			final Conditions conditions,
			final Character controlCharacter,
			final String name,
			final String placeCode,
			final SexIt sex,
			final String surname,
			final String value) {
		this.birthDate = birthDate;
		this.conditions = conditions;
		this.controlCharacter = controlCharacter;
		this.name = name;
		this.placeCode = placeCode;
		this.sex = sex;
		this.surname = surname;
		this.value = value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public FiscalCode clone() {
		try {
			return (FiscalCode) super.clone();
		} catch (final CloneNotSupportedException e) {
			throw new InternalError(); // This shouldn't happen, since we are Cloneable.
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int compareTo(@Nullable final FiscalCode o) {
		final FiscalCode other = conditions.notNull(o);
		return ComparisonChain.start()
				.compare(value, other.value)
				.compare(name, other.name)
				.compare(surname, other.surname)
				.result();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final @Nullable Object obj) {
		if (obj == null) {
			return false;
		} else if (obj == this) {
			return true;
		} else if (!(obj instanceof FiscalCode)) {
			return false;
		} else {
			final FiscalCode o = (FiscalCode) obj;
			return java.util.Objects.deepEquals(birthDate, o.birthDate)
					&& java.util.Objects.deepEquals(controlCharacter, o.controlCharacter)
					&& java.util.Objects.deepEquals(name, o.name)
					&& java.util.Objects.deepEquals(placeCode, o.placeCode)
					&& java.util.Objects.deepEquals(sex, o.sex)
					&& java.util.Objects.deepEquals(surname, o.surname)
					&& java.util.Objects.deepEquals(value, o.value);
		}
	}
	
	/**
	 * Returns the referred person's date of birth used to create the value
	 * returned by {@link #getValue()}.
	 * 
	 * @return date of birth
	 */
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	/**
	 * Returns the control character used to validate the whole fiscal code
	 * value and placed to its end.
	 * 
	 * @return control character
	 */
	public Character getControlCharacter() {
		return controlCharacter;
	}
	
	/**
	 * Returns the referred person's name used to create the value returned by
	 * {@link #getValue()}.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the referred person's place of birth used to create the value
	 * returned by {@link #getValue()}.
	 * 
	 * @return place code
	 */
	public String getPlaceCode() {
		return placeCode;
	}
	
	/**
	 * Returns the referred person's sex used to create the value returned by
	 * {@link #getValue()}.
	 * 
	 * @return sex
	 */
	public SexIt getSex() {
		return sex;
	}
	
	/**
	 * Returns the referred person's surname used to create the value returned
	 * by {@link #getValue()}.
	 * 
	 * @return surname
	 */
	public String getSurname() {
		return surname;
	}
	
	/**
	 * Returns the {@link String} representation of the fiscal code value.
	 * 
	 * @return value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return java.util.Objects.hash(birthDate, controlCharacter, name, placeCode, sex, surname, value);
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

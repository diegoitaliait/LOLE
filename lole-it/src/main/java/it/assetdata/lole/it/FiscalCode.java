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

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.LocalDate;

import com.google.common.base.Preconditions;
import com.google.common.collect.ComparisonChain;

/**
 * Represents an Italian fiscal code (Codice fiscale) which refers to a physical person.
 */
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
	 * A control character used to validate the whole fiscal code and added to its end.
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
	private final Sex sex;
	
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
	 * @throws NullPointerException
	 *             if any parameter is {@code null}
	 * 
	 */
	public FiscalCode(final LocalDate birthDate, final Character controlCharacter, final String name, final String placeCode, final Sex sex, final String surname, final String value) {
		this.birthDate = Preconditions.checkNotNull(birthDate);
		this.controlCharacter = Preconditions.checkNotNull(controlCharacter);
		this.name = Preconditions.checkNotNull(name);
		this.placeCode = Preconditions.checkNotNull(placeCode);
		this.sex = Preconditions.checkNotNull(sex);
		this.surname = Preconditions.checkNotNull(surname);
		this.value = Preconditions.checkNotNull(value);
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
	public int compareTo(final FiscalCode o) {
		return ComparisonChain.start()
				.compare(value, o.value)
				.compare(name, o.name)
				.compare(surname, o.surname)
				.result();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
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
	 * Returns the referred person's date of birth used to create the value returned by {@link #getValue()}.
	 * 
	 * @return date of birth
	 */
	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	/**
	 * Returns the control character used to validate the whole fiscal code value and placed to its end.
	 * 
	 * @return control character
	 */
	public Character getControlCharacter() {
		return controlCharacter;
	}
	
	/**
	 * Returns the referred person's name used to create the value returned by {@link #getValue()}.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the referred person's place of birth used to create the value returned by {@link #getValue()}.
	 * 
	 * @return place code
	 */
	public String getPlaceCode() {
		return placeCode;
	}
	
	/**
	 * Returns the referred person's sex used to create the value returned by {@link #getValue()}.
	 * 
	 * @return sex
	 */
	public Sex getSex() {
		return sex;
	}
	
	/**
	 * Returns the referred person's surname used to create the value returned by {@link #getValue()}.
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

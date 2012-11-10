/*
 * Copyright 2012 Asset Data (info--at--assetdata.it)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.assetdata.lole.it;

import it.assetdata.lole.common.Sex;

import java.io.Serializable;
import java.util.Objects;

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
	 * A control character used to validate the whole fiscal code ad added to its end.
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
	 * Does not accept {@code null} parameters.
	 * 
	 * @param birthDate
	 *            date of birth
	 * @param controlCharacter
	 *            control character
	 * @param name
	 *            name
	 * @param placeCode
	 *            place code
	 * @param sex
	 *            sex
	 * @param surname
	 *            surname
	 * @param value
	 *            representation of the whole fiscal code
	 * @throws NullPointerException
	 *             if any parameter is {@code null}
	 */
	public FiscalCode(final LocalDate birthDate, final Character controlCharacter, final String name, final String placeCode, final Sex sex,
			final String surname, final String value) throws NullPointerException {
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
	protected FiscalCode clone() {
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
				.compare(this.value, o.value)
				.compare(this.name, o.name)
				.compare(this.surname, o.surname)
				.result();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj == null) {
			return false;
		} else if (this == obj) {
			return true;
		} else if (!(obj instanceof FiscalCode)) {
			return false;
		} else {
			final FiscalCode o = (FiscalCode) obj;
			return Objects.deepEquals(this.birthDate, o.birthDate)
					&& Objects.deepEquals(this.controlCharacter, o.controlCharacter)
					&& Objects.deepEquals(this.name, o.name)
					&& Objects.deepEquals(this.placeCode, o.placeCode)
					&& Objects.deepEquals(this.sex, o.sex)
					&& Objects.deepEquals(this.surname, o.surname)
					&& Objects.deepEquals(this.value, o.value);
		}
	}
	
	/**
	 * @return date of birth
	 */
	public LocalDate getBirthDate() {
		return this.birthDate;
	}
	
	/**
	 * @return control character
	 */
	public Character getControlCharacter() {
		return this.controlCharacter;
	}
	
	/**
	 * @return name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return place code
	 */
	public String getPlaceCode() {
		return this.placeCode;
	}
	
	/**
	 * @return sex
	 */
	public Sex getSex() {
		return this.sex;
	}
	
	/**
	 * @return surname
	 */
	public String getSurname() {
		return this.surname;
	}
	
	/**
	 * @return value
	 */
	public String getValue() {
		return this.value;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.birthDate, this.controlCharacter, this.name, this.placeCode, this.sex, this.surname, this.value);
	}
	
}

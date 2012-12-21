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
package it.assetdata.lole.common;

import java.io.Serializable;

import javax.annotation.Nullable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * People sex.
 */
public abstract class Sex implements Serializable {
	
	/**
	 * Serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This implementation returns {@code true} if {@code obj == this} or
	 * {@code obj.getClass().isAssignableFrom(this.getClass()}.
	 */
	@Override
	public boolean equals(@Nullable Object obj) {
		if (obj == null) {
			return false;
		} else if (obj == this) {
			return true;
		} else if (obj.getClass().isAssignableFrom(this.getClass())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
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

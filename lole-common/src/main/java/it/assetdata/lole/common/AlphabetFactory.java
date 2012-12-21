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

import java.util.Locale;

import com.google.common.collect.ImmutableMap;

/**
 * {@link Alphabet} implementations factory.
 */
public class AlphabetFactory {
	
	private static ImmutableMap<String, Alphabet> repository; // TODO Initialize this somehow.
	
	/**
	 * @return {@code locale} language's {@link Alphabet} implementation
	 */
	public static Alphabet getInstance(Locale locale) throws NullPointerException {
		return repository.get(locale.getLanguage());
	}
	
}

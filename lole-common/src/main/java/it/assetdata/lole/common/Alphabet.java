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

import com.google.common.collect.ImmutableList;

/**
 * Languages' alphabet set and subsets.
 */
public interface Alphabet {
	
	/**
	 * @return lower case full alphabet set
	 */
	ImmutableList<Character> getLowerCaseAlphabet();
	
	/**
	 * @return lower case alphabet consonants subset
	 */
	ImmutableList<Character> getLowerCaseConsonants();
	
	/**
	 * @return lower case alphabet vowels subset
	 */
	ImmutableList<Character> getLowerCaseVowels();
	
	/**
	 * @return title case full alphabet set
	 */
	ImmutableList<Character> getTitleCaseAlphabet();
	
	/**
	 * @return title case alphabet consonants subset
	 */
	ImmutableList<Character> getTitleCaseConsonants();
	
	/**
	 * @return title case alphabet vowels subset
	 */
	ImmutableList<Character> getTitleCaseVowels();
	
	/**
	 * @return upper case full alphabet set
	 */
	ImmutableList<Character> getUpperCaseAlphabet();
	
	/**
	 * @return upper case alphabet consonants subset
	 */
	ImmutableList<Character> getUpperCaseConsonants();
	
	/**
	 * @return upper case alphabet vowels subset
	 */
	ImmutableList<Character> getUpperCaseVowels();
	
}

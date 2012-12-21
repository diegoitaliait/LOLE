package it.assetdata.lole.it;

import it.assetdata.lole.common.Alphabet;

import java.util.Locale;

import com.google.common.collect.ImmutableList;

/**
 * Represents the Italian ({@link Locale#ITALIAN} alphabet and various subset.
 */
public class ItalianAlphabet implements Alphabet {
	
	/**
	 * Singleton instance.
	 */
	private static ItalianAlphabet instance;
	
	/**
	 * Lower case full alphabet.
	 */
	@SuppressWarnings("boxing")
	private static final ImmutableList<Character> lowerCaseAlphabet = ImmutableList.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
	
	/**
	 * Lower case consonants subset.
	 */
	@SuppressWarnings("boxing")
	private static final ImmutableList<Character> lowerCaseConsonants = ImmutableList.of('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'y', 'z');
	
	/**
	 * Upper case vowels subset.
	 */
	@SuppressWarnings("boxing")
	private static final ImmutableList<Character> lowerCaseVowels = ImmutableList.of('a', 'e', 'i', 'o', 'u');
	
	/**
	 * Title case full alphabet.
	 */
	@SuppressWarnings("boxing")
	private static final ImmutableList<Character> titleCaseAlphabet = ImmutableList.of('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
	
	/**
	 * Title case consonants subset.
	 */
	@SuppressWarnings("boxing")
	private static final ImmutableList<Character> titleCaseConsonants = ImmutableList.of('B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z');
	
	/**
	 * Title case vowels subset.
	 */
	@SuppressWarnings("boxing")
	private static final ImmutableList<Character> titleCaseVowels = ImmutableList.of('A', 'E', 'I', 'O', 'U');
	
	/**
	 * Upper case full alphabet.
	 */
	private static final ImmutableList<Character> upperCaseAlphabet = titleCaseAlphabet;
	
	/**
	 * Upper case consonants subset.
	 */
	private static final ImmutableList<Character> upperCaseConsonants = titleCaseConsonants;
	
	/**
	 * Upper case vowels subset.
	 */
	private static final ImmutableList<Character> upperCaseVowels = titleCaseVowels;
	
	/**
	 * @return singleton instance
	 */
	public static ItalianAlphabet getInstance() {
		if (instance == null) {
			instance = new ItalianAlphabet();
		}
		return instance;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImmutableList<Character> getLowerCaseAlphabet() {
		return lowerCaseAlphabet;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImmutableList<Character> getLowerCaseConsonants() {
		return lowerCaseConsonants;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImmutableList<Character> getLowerCaseVowels() {
		return lowerCaseVowels;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImmutableList<Character> getTitleCaseAlphabet() {
		return titleCaseAlphabet;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImmutableList<Character> getTitleCaseConsonants() {
		return titleCaseConsonants;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImmutableList<Character> getTitleCaseVowels() {
		return titleCaseVowels;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImmutableList<Character> getUpperCaseAlphabet() {
		return upperCaseAlphabet;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImmutableList<Character> getUpperCaseConsonants() {
		return upperCaseConsonants;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImmutableList<Character> getUpperCaseVowels() {
		return upperCaseVowels;
	}
	
}

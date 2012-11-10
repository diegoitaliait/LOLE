package it.assetdata.lole.it;

import it.assetdata.lole.common.Sex;

import java.util.Calendar;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.LocalDate;

/**
 * Test of {@link it.assetdata.aventinushr.util.FiscalCodeBuilder} class.
 */
@SuppressWarnings("nls")
public class FiscalCodeBuilderTest {
	
	private static char[] chars = "QWERTYUIOPASDFGHJKLZXCVBNM ".toCharArray();
	private static char[] nameChars = "QWERTYUIOPASDFGHJKLZXCVBNM ".toCharArray();
	private static Pattern pattern = Pattern.compile("[QWRTYPSDFGHJKLZXCVBNM]{6}[0-9]{2}[A-Z]{1}[0-9]{2}[A-Z]{1}[0-9]{3}[A-Z]{1}");
	private static Random random = new Random();
	
	private static LocalDate generateBirthDate() {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(FiscalCodeBuilderTest.random.nextInt(2000) + 1000, FiscalCodeBuilderTest.random.nextInt(11), FiscalCodeBuilderTest.random.nextInt(31));
		return LocalDate.fromCalendarFields(calendar);
	}
	
	private static String generatePlaceCode() {
		return RandomStringUtils.random(1, 0, FiscalCodeBuilderTest.chars.length, true, false, FiscalCodeBuilderTest.chars, FiscalCodeBuilderTest.random) + String.valueOf(FiscalCodeBuilderTest.random.nextInt(9) + 1)
				+ String.valueOf(FiscalCodeBuilderTest.random.nextInt(9) + 1) + String.valueOf(FiscalCodeBuilderTest.random.nextInt(9) + 1);
	}
	
	private static Sex generateSex() {
		return FiscalCodeBuilderTest.random.nextInt(1) == 0 ? Sex.FEMALE : Sex.MALE;
	}
	
	private static String genereateName() {
		return RandomStringUtils.random(FiscalCodeBuilderTest.random.nextInt(21) + 2, 0, FiscalCodeBuilderTest.nameChars.length, true, false, FiscalCodeBuilderTest.nameChars, FiscalCodeBuilderTest.random);
	}
	
	private static String genereateSurname() {
		return RandomStringUtils.random(FiscalCodeBuilderTest.random.nextInt(21) + 2, 0, FiscalCodeBuilderTest.nameChars.length, true, false, FiscalCodeBuilderTest.nameChars, FiscalCodeBuilderTest.random);
	}
	
	/**
	 * @param args
	 *            are unused
	 */
	public static void main(final String[] args) {
		final FiscalCodeBuilder fiscalCodeBuilder = new FiscalCodeBuilder();
		
		// Perform some sample tests.
		for (int i = 0; i < 100; i++) {
			
			// Create sample data.
			final String surname = FiscalCodeBuilderTest.genereateSurname();
			final String name = FiscalCodeBuilderTest.genereateName();
			final LocalDate birthDate = FiscalCodeBuilderTest.generateBirthDate();
			final String placeCode = FiscalCodeBuilderTest.generatePlaceCode();
			final Sex sex = FiscalCodeBuilderTest.generateSex();
			
			// Build a new FiscalCode.
			fiscalCodeBuilder.setBirthDate(birthDate);
			fiscalCodeBuilder.setName(name);
			fiscalCodeBuilder.setPlaceCode(placeCode);
			fiscalCodeBuilder.setSex(sex);
			fiscalCodeBuilder.setSurname(surname);
			final FiscalCode fiscalCode = fiscalCodeBuilder.build();
			
			// Check if it matches.
			final String value = fiscalCode.getValue().toUpperCase();
			final Matcher matcher = FiscalCodeBuilderTest.pattern.matcher(value);
			final boolean matches = matcher.matches();
			
			// Print result.
			System.out.println();
			System.out.println(surname + " " + name + " " + birthDate + " " + placeCode + " = " + value + " : " + matches);
			System.out.println();
			System.out.flush();
		}
	}
	
}

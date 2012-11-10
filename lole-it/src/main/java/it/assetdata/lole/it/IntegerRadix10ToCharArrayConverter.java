package it.assetdata.lole.it;

/**
 * Places characters representing an {@link Integer} into a {@code char[]}.<br/>
 * The characters are placed into the buffer backwards starting with the least significant digit, and working backwards from there.
 */
public class IntegerRadix10ToCharArrayConverter implements Converter<Integer, char[]> {
	
	/**
	 * Digits used by "ones".
	 */
	private final static char[] DigitOnes = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1',
			'2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1',
			'2', '3', '4', '5', '6', '7', '8', '9', };
	
	/**
	 * All possible chars for representing a number as a {@code char[]}.
	 */
	final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
			'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	
	/**
	 * Digits used by "tens".
	 */
	private final static char[] DigitTens = { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '1', '1', '1', '1', '1', '1', '1',
			'1', '1', '1', '2', '2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3', '3', '3', '3', '3', '3', '3', '4', '4',
			'4', '4', '4', '4', '4', '4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5', '6', '6', '6', '6', '6', '6', '6',
			'6', '6', '6', '7', '7', '7', '7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8', '8', '8', '8', '8', '9', '9',
			'9', '9', '9', '9', '9', '9', '9', '9', };
	
	/**
	 * Used to check resulting {@code char[]} size.
	 */
	private final static int[] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999, Integer.MAX_VALUE };
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public char[] convert(final Integer s) {
		// Use the "invariant division by multiplication" trick to
		// accelerate. In particular we want to
		// avoid division by 10.
		//
		// The "trick" has roughly the same performance characteristics
		// as the "classic" Integer.toString code on a non-JIT VM.
		// The trick avoids .rem and .div calls but has a longer code
		// path and is thus dominated by dispatch overhead. In the
		// JIT case the dispatch overhead doesn't exist and the
		// "trick" is considerably faster than the classic code.
		//
		// TODO-FIXME: convert (x * 52429) into the equiv shift-add
		// sequence.
		//
		// RE: Division by Invariant Integers using Multiplication
		// T Gralund, P Montgomery
		// ACM PLDI 1994
		//
		int i = s.intValue();
		if (i == Integer.MIN_VALUE) {
			return "-2147483648".toCharArray(); //$NON-NLS-1$
		}
		final int size = (i < 0) ? this.stringSize(-i) + 1 : this.stringSize(i);
		final char[] buf = new char[size];
		int q, r;
		int charPos = size;
		char sign = 0;
		if (i < 0) {
			sign = '-';
			i = -i;
		}
		while (i >= 65536) { // Generate two digits per iteration
			q = i / 100;
			r = i - ((q << 6) + (q << 5) + (q << 2)); // really: r = i - (q * 100);
			i = q;
			buf[--charPos] = IntegerRadix10ToCharArrayConverter.DigitOnes[r];
			buf[--charPos] = IntegerRadix10ToCharArrayConverter.DigitTens[r];
		}
		for (;;) { // Fall thru to fast mode for smaller numbers
			q = (i * 52429) >>> (16 + 3);
			r = i - ((q << 3) + (q << 1)); // r = i-(q*10) ...
			buf[--charPos] = IntegerRadix10ToCharArrayConverter.digits[r];
			i = q;
			if (i == 0) {
				break;
			}
		}
		if (sign != 0) {
			buf[--charPos] = sign;
		}
		return buf;
	}
	
	/**
	 * {@code x} {@link String} size.
	 * 
	 * @param x
	 *            must be positive
	 * @return size
	 */
	private int stringSize(final int x) {
		for (int i = 0;; i++) {
			if (x <= IntegerRadix10ToCharArrayConverter.sizeTable[i]) {
				return i + 1;
			}
		}
	}
	
}

package it.assetdata.lole.it;

/**
 * Implementations must be able to cast/convert or somehow return an instance of type {@code T} from an instance of type {@code T}.
 * 
 * @param <S>
 *            source type
 * @param <T>
 *            destination type.
 */
public interface Converter<S, T> {
	
	/**
	 * Convert {@code s} an instance of type {@code T}
	 * 
	 * @param s
	 *            instance to convert
	 * @return converted instance
	 */
	public T convert(S s);
	
}

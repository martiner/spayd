package cz.geek.spayd;

import static org.apache.commons.lang.Validate.notNull;

/**
 * Wrapped non-null value
 */
public abstract class SpaydWrappedValue<T> implements SpaydValue {

	protected final T value;

	public SpaydWrappedValue(T value) {
		notNull(value);
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value.toString();
	}
}

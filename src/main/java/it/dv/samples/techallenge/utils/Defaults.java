package it.dv.samples.techallenge.utils;

/**
 * Optional values
 *
 * @author davidvotino
 */
public abstract class Defaults {

    /**
     * Returns the given value if not null, the default value otherwise
     *
     * @param <T>
     * @param value
     * @param defaultValue
     * @return
     */
    public static <T> T or(T value, T defaultValue) {
        if (null != value) {
            return value;
        }

        return defaultValue;
    }
}

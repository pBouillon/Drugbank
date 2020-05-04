package util;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Basic lazy value implementation
 * Inspired by: https://dzone.com/articles/be-lazy-with-java-8
 * @param <T> Type on which apply the lazy evaluation
 */
public final class Lazy<T> {

    /**
     * Value to be lazy evaluated
     */
    private T _value;

    /**
     * Value supplier to call on query
     */
    private Supplier<T> _supplier;

    /**
     * Set the supplier of this lazy value for evaluation
     * @param supplier Supplier of the concrete value (may be a lambda)
     */
    public void setSupplier(Supplier<T> supplier) {
        _supplier = supplier;
    }

    /**
     * Get the value if available; apply the provided method to evaluate it otherwise
     * @return The concrete value
     */
    public T getOrCompute() {
        return _value == null
            ? compute()
            : _value;
    }

    /**
     * Perform the lazy assignment
     * @return The value with its final value
     */
    private T compute() {
        _value = Objects.requireNonNull(_supplier.get());
        return _value;
    }

}

package com.company.common;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * Simple implementation of a mutable holder class, which wraps a value
 * for use cases like the ones of the {@code javax.xml.ws.Holder} class.
 * This class is similar to the non-atomic parts of
 * {@code java.util.concurrent.atomic.AtomicReference}, instead of
 * extending the {@code java.lang.ref.Reference<T>} class, e.g. as
 * {@code java.lang.ref.WeakReference} does. For utility purposes,
 * {@code ValueReference} also implements the {@code Supplier<?>} interface.
 *
 * @param <V> The type of the stored value
 */
public final class ValueReference<V> implements Supplier<V>, Serializable {

  private static final long serialVersionUID = -8766443361965739367L;

  private V value; // The referent

  public ValueReference() {
    this(null);
  }

  public ValueReference(V initialValue) {
    this.value = initialValue;
  }

  @Override
  public V get() {
    return this.value;
  }

  public void set(V newValue) {
    this.value = newValue;
  }

  public String toString() {
    if (this.value == null) {
      return "null";
    }
    return this.value.toString();
  }
}

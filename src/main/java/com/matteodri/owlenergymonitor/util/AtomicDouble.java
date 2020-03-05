package com.matteodri.owlenergymonitor.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * AtomicDouble implementation to fill the missing AtomicDouble in the standard library. A floating point value that
 * supports thread-safe access.
 */
public class AtomicDouble extends Number {

    private AtomicLong bits;

    public AtomicDouble() {
        this(0f);
    }

    public AtomicDouble(double initialValue) {
        bits = new AtomicLong(Double.doubleToLongBits(initialValue));
    }

    public final boolean compareAndSet(double expect, double update) {
        return bits.compareAndSet(Double.doubleToLongBits(expect), Double.doubleToLongBits(update));
    }

    public final void set(double newValue) {
        bits.set(Double.doubleToLongBits(newValue));
    }

    public final double get() {
        return Double.longBitsToDouble(bits.get());
    }

    public float floatValue() {
        return (float) get();
    }

    public double doubleValue() {
        return get();
    }

    public final double getAndSet(double newValue) {
        return Double.longBitsToDouble(bits.getAndSet(Double.doubleToLongBits(newValue)));
    }

    public boolean weakCompareAndSet(double expect, double update) {
        return bits.weakCompareAndSet(Double.doubleToLongBits(expect), Double.doubleToLongBits(update));
    }

    public int intValue() {
        return (int) get();
    }

    public long longValue() {
        return (long) get();
    }

    @Override
    public String toString() {
        return AtomicDouble.class.getSimpleName() + "{" + "double=" + doubleValue() + "}";
    }
}

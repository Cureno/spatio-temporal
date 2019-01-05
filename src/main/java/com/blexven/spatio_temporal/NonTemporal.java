package com.blexven.spatio_temporal;

public abstract class NonTemporal<T> {

    private T value;

    public NonTemporal(T value) {
        this.value = value;
    }

    public boolean isDefined() {
        return value != null;
    }


}

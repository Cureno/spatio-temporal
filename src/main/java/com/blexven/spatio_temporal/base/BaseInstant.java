package com.blexven.spatio_temporal.base;

import com.blexven.spatio_temporal.NonTemporal;

public class BaseInstant extends NonTemporal<Double> {

    /**
     * We expect a bounded time domain at the discrete level
     */
    public static final com.blexven.spatio_temporal.base.BaseInstant MIN_INSTANT = new com.blexven.spatio_temporal.base.BaseInstant(Double.MIN_VALUE);

    /**
     * We expect a bounded time domain at the discrete level
     */
    public static final com.blexven.spatio_temporal.base.BaseInstant MAX_INSTANT = new com.blexven.spatio_temporal.base.BaseInstant(Double.MAX_VALUE);

    public BaseInstant(Double value) {
        super(value);
    }

}

package com.yzp.utils.lambda;

import java.math.BigDecimal;

@FunctionalInterface
public interface ToBigDecimalFunction<T> {

    BigDecimal applyAsBigDecimal(T value);

}


package com.yzp.utils.lambda;


import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.*;
import java.util.stream.Collector;


public class CollectorsUtil {

    private static final Set<Collector.Characteristics> CHARACTERISTICS = Collections.emptySet();

    private CollectorsUtil() {
    }

    @SuppressWarnings("unchecked")
    private static <I, R> Function<I, R> castingIdentity() {
        return i -> (R) i;
    }

    /**
     * Simple implementation class for {@code Collector}.
     *
     * @param <T> the type of elements to be collected
     * @param <R> the type of the result
     */
    static class CollectorImpl<T, A, R> implements Collector<T, A, R> {
        private final Supplier<A> supplier;
        private final BiConsumer<A, T> accumulator;
        private final BinaryOperator<A> combiner;
        private final Function<A, R> finisher;
        private final Set<Characteristics> characteristics;

        CollectorImpl(Supplier<A> supplier,
                      BiConsumer<A, T> accumulator,
                      BinaryOperator<A> combiner,
                      Function<A, R> finisher,
                      Set<Characteristics> characteristics) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.finisher = finisher;
            this.characteristics = characteristics;
        }

        CollectorImpl(Supplier<A> supplier,
                      BiConsumer<A, T> accumulator,
                      BinaryOperator<A> combiner,
                      Set<Characteristics> characteristics) {
            this(supplier, accumulator, combiner, castingIdentity(), characteristics);
        }

        @Override
        public BiConsumer<A, T> accumulator() {
            return accumulator;
        }

        @Override
        public Supplier<A> supplier() {
            return supplier;
        }

        @Override
        public BinaryOperator<A> combiner() {
            return combiner;
        }

        @Override
        public Function<A, R> finisher() {
            return finisher;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    }

    /**
     * ????????????
     *
     */
    public static <T> Collector<T, ?, BigDecimal> summingBigDecimal(ToBigDecimalFunction<? super T> mapper) {
        return new CollectorImpl<>(
                () -> new BigDecimal[]{new BigDecimal(0)},
                (a, t) -> a[0] = a[0].add(mapper.applyAsBigDecimal(t), MathContext.DECIMAL32),
                (a, b) -> {
                    a[0] = a[0].add(b[0], MathContext.DECIMAL32);
                    return a;
                },
                a -> a[0], CHARACTERISTICS);
    }

    /**
     * ?????????,???????????????MIN?????????????????????????????????????????????????????????????????????????????????????????????????????????Long.MIN_VALUE??????Double.MIN_VALUE
     *
     * @param mapper mapper
     * @param <T> <T>
     * @return Collector<T, ?, BigDecimal>
     */
    public static <T> Collector<T, ?, BigDecimal> maxBy(ToBigDecimalFunction<? super T> mapper) {
        return new CollectorImpl<>(
                () -> new BigDecimal[]{new BigDecimal(Integer.MIN_VALUE)},
                (a, t) -> a[0] = a[0].max(mapper.applyAsBigDecimal(t)),
                (a, b) -> {
                    a[0] = a[0].max(b[0]);
                    return a;
                },
                a -> a[0], CHARACTERISTICS);
    }

    /**
     * ???????????????????????????MAX?????????????????????????????????????????????????????????????????????????????????????????????????????????Long.MAX_VALUE??????Double.MAX_VALUE
     *
     */
    public static <T> Collector<T, ?, BigDecimal> minBy(ToBigDecimalFunction<? super T> mapper) {
        return new CollectorImpl<>(
                () -> new BigDecimal[]{new BigDecimal(Integer.MAX_VALUE)},
                (a, t) -> a[0] = a[0].min(mapper.applyAsBigDecimal(t)),
                (a, b) -> {
                    a[0] = a[0].min(b[0]);
                    return a;
                },
                a -> a[0], CHARACTERISTICS);
    }

    /**
     * ??????????????????????????????????????????????????????
     *
     * @param newScale     ??????????????????
     * @param roundingMode ??????????????????
     *                     #ROUND_UP ???1
     *                     #ROUND_DOWN ???1
     *                     #ROUND_CEILING  ???1??????????????????ROUND_UP????????????ROUND_DOWN
     *                     #ROUND_FLOOR  ???1??????????????????ROUND_DOWN????????????ROUND_UP
     *                     #ROUND_HALF_UP >=0.5???1
     *                     #ROUND_HALF_DOWN >0.5???1
     *                     #ROUND_HALF_EVEN
     *                     #ROUND_UNNECESSARY
     */
    public static <T> Collector<T, ?, BigDecimal> averagingBigDecimal(ToBigDecimalFunction<? super T> mapper, int newScale, RoundingMode roundingMode) {
        return new CollectorImpl<>(
                () -> new BigDecimal[]{new BigDecimal(0), new BigDecimal(0)},
                (a, t) -> {
                    a[0] = a[0].add(mapper.applyAsBigDecimal(t));
                    a[1] = a[1].add(BigDecimal.ONE);
                },
                (a, b) -> {
                    a[0] = a[0].add(b[0]);
                    return a;
                },
                a -> a[0].divide(a[1], MathContext.DECIMAL32).setScale(newScale, roundingMode), CHARACTERISTICS);
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static <T> Predicate<T> repeatByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) != null;
    }
}



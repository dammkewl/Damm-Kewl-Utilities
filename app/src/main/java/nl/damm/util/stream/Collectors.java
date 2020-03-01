package nl.damm.util.stream;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class Collectors {

    public static <T, A> Collector<T, A, A> toIdentity(A ident, BiConsumer<A, T> collect) {
        return new CollectorImpl<T, A, A>(
                () -> ident,
                collect,
                (l, r) -> l,
                col -> col
        ) {
            @Override
            public Set<Characteristics> characteristics() {
                return EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED);
            }
        };
    }

    public static <T, A extends Collection<T>> Collector<T, A, A> toCollection(A ident) {
        return new CollectorImpl<T, A, A>(
                () -> ident,
                Collection::add,
                (l, r) -> l,
                col -> col
        ) {
            @Override
            public Set<Characteristics> characteristics() {
                return EnumSet.of(Characteristics.IDENTITY_FINISH, Characteristics.UNORDERED);
            }
        };
    }

    public static <T, A extends List<T>> Collector<T, A, A> toList(A ident) {
        return new CollectorImpl<T, A, A>(
                () -> ident,
                List::add,
                (l, r) -> l,
                col -> col
        ) {
            @Override
            public Set<Characteristics> characteristics() {
                return EnumSet.of(Characteristics.IDENTITY_FINISH);
            }
        };
    }


    private static class CollectorImpl<T, A, R> implements Collector<T, A, R> {

        private Supplier<A> supplier;
        private BiConsumer<A, T> accumulator;
        private BinaryOperator<A> combiner;
        private Function<A, R> finisher;

        private CollectorImpl(
                Supplier<A> supplier,
                BiConsumer<A, T> accumulator,
                BinaryOperator<A> combiner,
                Function<A, R> finisher) {
            this.supplier = supplier;
            this.accumulator = accumulator;
            this.combiner = combiner;
            this.finisher = finisher;
        }

        @Override
        public Supplier<A> supplier() {
            return supplier;
        }

        @Override
        public BiConsumer<A, T> accumulator() {
            return accumulator;
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
            return Collections.emptySet();
        }
    }

}

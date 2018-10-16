package buildtools.service;

import java.util.Collections;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;

public class Practice2 {

    public static void main(String args[]){

        String s = Stream.of("Mike", "Nicki", "John").parallel().collect(new
                MyCollector());
        System.out.println(s);
    }

    private static class MyCollector implements
            Collector<String, StringBuilder, String> {

        @Override
        public Supplier<StringBuilder> supplier () {
            Supplier<StringBuilder> supplier = new Supplier<StringBuilder>() {
                @Override
                public StringBuilder get() {
                    return new StringBuilder();
                }
            };
            return supplier;
        }

        @Override
        public BiConsumer<StringBuilder, String> accumulator () {
            BiConsumer<StringBuilder, String> biConsumer = new BiConsumer<StringBuilder, String>() {
                @Override
                public void accept(StringBuilder stringBuilder, String s) {
                    System.out.println(stringBuilder.toString()+"::"+s);
                    stringBuilder.append(" ").append(s);
                }
            };
            return biConsumer;
        }

        @Override
        public BinaryOperator<StringBuilder> combiner () {
            BinaryOperator<StringBuilder> stringBuilderBinaryOperator = new BinaryOperator<StringBuilder>() {
                @Override
                public StringBuilder apply(StringBuilder stringBuilder, StringBuilder stringBuilder2) {
                    System.out.println(stringBuilder.toString()+"--"+stringBuilder2.toString());
                    return stringBuilder.append(stringBuilder2);
                }
            };
            return stringBuilderBinaryOperator;
        }

        @Override
        public Function<StringBuilder, String> finisher () {
            Function<StringBuilder, String> funciton  = new Function<StringBuilder, String>() {
                @Override
                public String apply(StringBuilder stringBuilder) {
                    return stringBuilder.toString();
                }
            };


            return funciton;
        }

        @Override
        public Set<Characteristics> characteristics () {
            return Collections.emptySet();
        }
    }

}
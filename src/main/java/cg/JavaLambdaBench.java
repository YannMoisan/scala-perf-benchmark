package cg;

import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1)
@Threads(1)
@Warmup(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
public class JavaLambdaBench {
    Integer[] arr = new Integer[20];

    @Setup
    public void setup() {
        Random random = new Random(); // create a Random object

        for (int i = 0; i < arr.length; i++) {
            arr[i] = random.nextInt(100); // assign a random integer between 0 and 99 to each element in the array
        }
    }

    @Benchmark
    public Optional<Integer> foreach() {
        return Arrays.stream(arr).reduce((accumulator, element) ->
                {
                    Integer tmp;
                    if (accumulator > element)
                        tmp = element;
                    else
                        tmp = accumulator;
                    return tmp;
                }
        );
    }

    @Benchmark
    public Integer min() {
        Integer min = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            }
        }
        return min;
    }
}

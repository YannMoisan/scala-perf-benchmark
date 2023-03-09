package foo

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations.{
  Benchmark,
  BenchmarkMode,
  Fork,
  Measurement,
  Mode,
  OutputTimeUnit,
  Scope,
  Setup,
  State,
  Threads,
  Warmup
}

//[info] Benchmark                                                    Mode  Cnt     Score     Error   Units
//[info] OptionBench.foldInt                                          avgt    5     5,048 ±   0,988   ns/op
//[info] OptionBench.foldInt:·gc.alloc.rate                           avgt    5    ≈ 10⁻⁴            MB/sec
//[info] OptionBench.foldInt:·gc.alloc.rate.norm                      avgt    5    ≈ 10⁻⁶              B/op
//[info] OptionBench.foldInt:·gc.count                                avgt    5       ≈ 0            counts
//[info] OptionBench.foldString                                       avgt    5    16,455 ±   0,312   ns/op
//[info] OptionBench.foldString:·gc.alloc.rate                        avgt    5  2382,823 ±  47,949  MB/sec
//[info] OptionBench.foldString:·gc.alloc.rate.norm                   avgt    5    48,000 ±   0,001    B/op
//[info] OptionBench.foldString:·gc.churn.G1_Eden_Space               avgt    5  2385,784 ± 129,556  MB/sec
//[info] OptionBench.foldString:·gc.churn.G1_Eden_Space.norm          avgt    5    48,058 ±   1,865    B/op
//[info] OptionBench.foldString:·gc.churn.G1_Old_Gen                  avgt    5     0,007 ±   0,008  MB/sec
//[info] OptionBench.foldString:·gc.churn.G1_Old_Gen.norm             avgt    5    ≈ 10⁻⁴              B/op
//[info] OptionBench.foldString:·gc.count                             avgt    5   202,000            counts
//[info] OptionBench.foldString:·gc.time                              avgt    5   105,000                ms
//[info] OptionBench.mapGetOrElseInt                                  avgt    5     4,802 ±   0,144   ns/op
//[info] OptionBench.mapGetOrElseInt:·gc.alloc.rate                   avgt    5    ≈ 10⁻⁴            MB/sec
//[info] OptionBench.mapGetOrElseInt:·gc.alloc.rate.norm              avgt    5    ≈ 10⁻⁶              B/op
//[info] OptionBench.mapGetOrElseInt:·gc.count                        avgt    5       ≈ 0            counts
//[info] OptionBench.mapGetOrElseString                               avgt    5    16,267 ±   0,264   ns/op
//[info] OptionBench.mapGetOrElseString:·gc.alloc.rate                avgt    5  2410,626 ±  34,159  MB/sec
//[info] OptionBench.mapGetOrElseString:·gc.alloc.rate.norm           avgt    5    48,000 ±   0,001    B/op
//[info] OptionBench.mapGetOrElseString:·gc.churn.G1_Eden_Space       avgt    5  2420,693 ±   7,826  MB/sec
//[info] OptionBench.mapGetOrElseString:·gc.churn.G1_Eden_Space.norm  avgt    5    48,201 ±   0,798    B/op
//[info] OptionBench.mapGetOrElseString:·gc.churn.G1_Old_Gen          avgt    5     0,007 ±   0,016  MB/sec
//[info] OptionBench.mapGetOrElseString:·gc.churn.G1_Old_Gen.norm     avgt    5    ≈ 10⁻⁴              B/op
//[info] OptionBench.mapGetOrElseString:·gc.count                     avgt    5   205,000            counts
//[info] OptionBench.mapGetOrElseString:·gc.time                      avgt    5   107,000                ms

// conclustion : l'option intermédiaire n'est pas instancié avec le map, surement escape analysis
@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1, jvmArgsAppend = Array())
@Threads(1)
@Warmup(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
class OptionBench {
  var someString: Option[String] = _

  var someInt: Option[Int] = _

  @Setup
  def setUp(): Unit = {
    someString = Some("42")
    someInt = Some(42)
  }

  @Benchmark
  def mapGetOrElseString(): String =
    someString.map(_ + "!").getOrElse("default")

  @Benchmark
  def foldString(): String =
    someString.fold("default")(_ + "!")

  @Benchmark
  def mapGetOrElseInt(): Int =
    someInt.map(_ + 1).getOrElse(0)

  @Benchmark
  def foldInt(): Int =
    someInt.fold(0)(_ + 1)

}

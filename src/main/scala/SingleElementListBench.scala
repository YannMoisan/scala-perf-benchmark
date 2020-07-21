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

import scala.util.Random

//[info] Benchmark                                                           Mode  Cnt     Score     Error   Units
//[info] SingleElementListBench.applyPrimitive                               avgt    5    16,284 ±   0,354   ns/op
//[info] SingleElementListBench.applyPrimitive:·gc.alloc.rate                avgt    5  3210,545 ±  74,120  MB/sec
//[info] SingleElementListBench.applyPrimitive:·gc.alloc.rate.norm           avgt    5    64,000 ±   0,001    B/op
//[info] SingleElementListBench.applyPrimitive:·gc.churn.G1_Eden_Space       avgt    5  3215,583 ± 122,768  MB/sec
//[info] SingleElementListBench.applyPrimitive:·gc.churn.G1_Eden_Space.norm  avgt    5    64,100 ±   1,503    B/op
//[info] SingleElementListBench.applyPrimitive:·gc.churn.G1_Old_Gen          avgt    5     0,010 ±   0,012  MB/sec
//[info] SingleElementListBench.applyPrimitive:·gc.churn.G1_Old_Gen.norm     avgt    5    ≈ 10⁻⁴              B/op
//[info] SingleElementListBench.applyPrimitive:·gc.count                     avgt    5   236,000            counts
//[info] SingleElementListBench.applyPrimitive:·gc.time                      avgt    5   126,000                ms
//[info] SingleElementListBench.applyRef                                     avgt    5     8,328 ±   0,837   ns/op
//[info] SingleElementListBench.applyRef:·gc.alloc.rate                      avgt    5  2353,524 ± 228,910  MB/sec
//[info] SingleElementListBench.applyRef:·gc.alloc.rate.norm                 avgt    5    24,000 ±   0,001    B/op
//[info] SingleElementListBench.applyRef:·gc.churn.G1_Eden_Space             avgt    5  2359,926 ± 297,111  MB/sec
//[info] SingleElementListBench.applyRef:·gc.churn.G1_Eden_Space.norm        avgt    5    24,062 ±   0,771    B/op
//[info] SingleElementListBench.applyRef:·gc.churn.G1_Old_Gen                avgt    5     0,004 ±   0,011  MB/sec
//[info] SingleElementListBench.applyRef:·gc.churn.G1_Old_Gen.norm           avgt    5    ≈ 10⁻⁴              B/op
//[info] SingleElementListBench.applyRef:·gc.count                           avgt    5   207,000            counts
//[info] SingleElementListBench.applyRef:·gc.time                            avgt    5   107,000                ms
//[info] SingleElementListBench.consPrimitive                                avgt    5     9,009 ±   0,166   ns/op
//[info] SingleElementListBench.consPrimitive:·gc.alloc.rate                 avgt    5  3625,744 ±  67,532  MB/sec
//[info] SingleElementListBench.consPrimitive:·gc.alloc.rate.norm            avgt    5    40,000 ±   0,001    B/op
//[info] SingleElementListBench.consPrimitive:·gc.churn.G1_Eden_Space        avgt    5  3635,158 ± 123,543  MB/sec
//[info] SingleElementListBench.consPrimitive:·gc.churn.G1_Eden_Space.norm   avgt    5    40,104 ±   1,226    B/op
//[info] SingleElementListBench.consPrimitive:·gc.churn.G1_Old_Gen           avgt    5     0,007 ±   0,020  MB/sec
//[info] SingleElementListBench.consPrimitive:·gc.churn.G1_Old_Gen.norm      avgt    5    ≈ 10⁻⁴              B/op
//[info] SingleElementListBench.consPrimitive:·gc.count                      avgt    5   254,000            counts
//[info] SingleElementListBench.consPrimitive:·gc.time                       avgt    5   140,000                ms
//[info] SingleElementListBench.consRef                                      avgt    5     6,835 ±   0,459   ns/op
//[info] SingleElementListBench.consRef:·gc.alloc.rate                       avgt    5  2868,930 ± 193,414  MB/sec
//[info] SingleElementListBench.consRef:·gc.alloc.rate.norm                  avgt    5    24,000 ±   0,001    B/op
//[info] SingleElementListBench.consRef:·gc.churn.G1_Eden_Space              avgt    5  2877,165 ± 264,731  MB/sec
//[info] SingleElementListBench.consRef:·gc.churn.G1_Eden_Space.norm         avgt    5    24,067 ±   1,017    B/op
//[info] SingleElementListBench.consRef:·gc.churn.G1_Old_Gen                 avgt    5     0,005 ±   0,012  MB/sec
//[info] SingleElementListBench.consRef:·gc.churn.G1_Old_Gen.norm            avgt    5    ≈ 10⁻⁴              B/op
//[info] SingleElementListBench.consRef:·gc.count                            avgt    5   238,000            counts
//[info] SingleElementListBench.consRef:·gc.time                             avgt    5   122,000                ms

// conclusion : primitive : faster because less allocations
// reference faster for unknown reason
@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1, jvmArgsAppend = Array())
@Threads(1)
@Warmup(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
class SingleElementListBench {
  var someString: String = _

  var someInt: Int = _

  @Setup
  def setUp(): Unit = {
    someString = "42"
    someInt = Random.nextInt
  }

  @Benchmark
  def applyPrimitive(): List[Int] = List(someInt)

  @Benchmark
  def applyRef(): List[String] = List(someString)

  @Benchmark
  def consPrimitive(): List[Int] = someInt :: Nil

  @Benchmark
  def consRef(): List[String] = someString :: Nil
}

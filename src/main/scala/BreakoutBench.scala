package foo

import java.util.concurrent.TimeUnit
import scala.collection.breakOut

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
//[info] Benchmark                                             Mode  Cnt      Score     Error   Units
//[info] BreakoutBench.baseline                                avgt    5   5259,404 ±  88,608   ns/op
//[info] BreakoutBench.baseline:·gc.alloc.rate                 avgt    5   1579,506 ±  25,600  MB/sec
//[info] BreakoutBench.baseline:·gc.alloc.rate.norm            avgt    5  10176,001 ±   0,001    B/op
//[info] BreakoutBench.baseline:·gc.churn.G1_Eden_Space        avgt    5   1585,562 ± 109,928  MB/sec
//[info] BreakoutBench.baseline:·gc.churn.G1_Eden_Space.norm   avgt    5  10214,893 ± 653,692    B/op
//[info] BreakoutBench.baseline:·gc.churn.G1_Old_Gen           avgt    5      0,003 ±   0,006  MB/sec
//[info] BreakoutBench.baseline:·gc.churn.G1_Old_Gen.norm      avgt    5      0,021 ±   0,041    B/op
//[info] BreakoutBench.baseline:·gc.count                      avgt    5    152,000            counts
//[info] BreakoutBench.baseline:·gc.time                       avgt    5     76,000                ms
//[info] BreakoutBench.breakout2                               avgt    5    844,197 ±  17,184   ns/op
//[info] BreakoutBench.breakout2:·gc.alloc.rate                avgt    5   1207,116 ±  24,932  MB/sec
//[info] BreakoutBench.breakout2:·gc.alloc.rate.norm           avgt    5   1248,000 ±   0,001    B/op
//[info] BreakoutBench.breakout2:·gc.churn.G1_Eden_Space       avgt    5   1210,756 ±  91,231  MB/sec
//[info] BreakoutBench.breakout2:·gc.churn.G1_Eden_Space.norm  avgt    5   1251,778 ±  94,328    B/op
//[info] BreakoutBench.breakout2:·gc.churn.G1_Old_Gen          avgt    5      0,003 ±   0,003  MB/sec
//[info] BreakoutBench.breakout2:·gc.churn.G1_Old_Gen.norm     avgt    5      0,003 ±   0,003    B/op
//[info] BreakoutBench.breakout2:·gc.count                     avgt    5    116,000            counts
//[info] BreakoutBench.breakout2:·gc.time                      avgt    5     56,000                ms

// Conclusion : pas de structurein termédiraire instanc↑w
@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1, jvmArgsAppend = Array())
@Threads(1)
@Warmup(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
class BreakoutBench {
  var set: Set[Int] = _

  @Setup
  def setUp(): Unit = {
    set = (1 to 100).map(Random.nextInt).toSet
  }

  @Benchmark
  def baseline(): List[Int] = set.map(_ + 1).toList

  @Benchmark
  def breakout2(): List[Int] = set.map(_ + 1)(breakOut)
//
//  @Benchmark
//  def consPrimitive(): List[Int] = 1 :: Nil
//
//  @Benchmark
//  def consRef(): List[String] = "foo" :: Nil
}

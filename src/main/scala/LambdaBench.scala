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

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1, jvmArgsAppend = Array())
@Threads(1)
@Warmup(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
class LambdaBench {
  var constant: Int = _
  var l: List[Int] = _
  var range: Seq[Int] = _

  @Setup
  def setUp(): Unit = {
    constant = 1
    l = (1 to 100).toList
    range = (2 to 101)
  }

  @Benchmark
  def baseline(): List[Int] = range.toList

  @Benchmark
  def lambda(): List[Int] = l.map(_ + 1)

  @Benchmark
  def closure(): List[Int] = l.map(_ + constant)
  //
  //  @Benchmark
  //  def consPrimitive(): List[Int] = 1 :: Nil
  //
  //  @Benchmark
  //  def consRef(): List[String] = "foo" :: Nil
}

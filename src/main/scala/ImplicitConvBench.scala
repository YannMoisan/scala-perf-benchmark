package foo

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._
import scala.util.Random

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1, jvmArgsAppend = Array())
@Threads(1)
@Warmup(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
class ImplicitConvBench {
  var ints: List[Int] = _

  def f(i: Int): Option[String] = if (i > 50) Some(i.toString) else None

  @Setup
  def setUp(): Unit = {
    ints = (1 to 100).map(Random.nextInt).toList
  }

  @Benchmark
  def baseline(): List[Int] = ints

  @Benchmark
  def flatMap(): List[String] = ints.flatMap(f)

  @Benchmark
  def collect(): List[String] = ints.collect {
    Function.unlift(f)
  }
}

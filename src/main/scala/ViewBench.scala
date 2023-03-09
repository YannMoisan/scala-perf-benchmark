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
class ViewBench {
  var set: Set[Int] = _

  @Setup
  def setUp(): Unit = {
    set = (1 to 100).map(Random.nextInt).toSet
  }

  @Benchmark
  def baseline(): List[Int] = set.map(_ + 1).toList

  @Benchmark
  def view(): List[Int] = set.view.map(_ + 1).toList
}

/*
* Initially, the aim was to bench the usage of breakOut.
* This instruction has been removed in scala 2.13 and the same result can be achieved with views.
*
* The intermediate collection is not instantiated.
*
*   */

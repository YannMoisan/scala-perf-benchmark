package foo

import org.openjdk.jmh.annotations._

import java.util.concurrent.TimeUnit
import scala.util.Random

@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1, jvmArgsAppend = Array("-Djmh.stack.lines=3"))
@Threads(1)
@Warmup(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
class IterationArrayReference {
  var array: Array[Integer] = _

  @Setup
  def setup(): Unit = {
    array = Array.fill(20)(Integer.valueOf(Random.nextInt))
  }

  @Benchmark
  def min(): Integer = {
    array.min
  }

  @Benchmark
  def foreach(): Integer = {
    var min: Integer = Int.MaxValue
    array.foreach(current => if (current < min) min = current)
    min
  }

  @Benchmark
  def indices_foreach(): Integer = {
    var min: Integer = Int.MaxValue
    array.indices.foreach { i =>
      val current = array(i)
      if (current < min) min = current
    }
    min
  }

  @Benchmark
  def while_(): Integer = {
    var min: Integer = Int.MaxValue
    var i = 0
    while (i < array.size) {
      val current = array(i)
      if (current < min) min = current
      i += 1
    }
    min
  }
}

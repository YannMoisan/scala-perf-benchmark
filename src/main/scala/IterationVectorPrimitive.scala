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
class IterationVectorPrimitive {
  var vector: Vector[Int] = _

  @Setup
  def setup(): Unit = {
    vector = Vector.fill(20)(Random.nextInt)
  }

  @Benchmark
  def min(): Int = {
    vector.min
  }

  @Benchmark
  def foreach(): Int = {
    var min = Int.MaxValue
    vector.foreach(current => if (current < min) min = current)
    min
  }

  @Benchmark
  def indices_foreach(): Int = {
    var min = Int.MaxValue
    vector.indices.foreach { i =>
      val current = vector(i)
      if (current < min) min = current
    }
    min
  }

  @Benchmark
  def while_(): Int = {
    var min = Int.MaxValue
    var i = 0
    while (i < vector.size) {
      val current = vector(i)
      if (current < min) min = current
      i += 1
    }
    min
  }
}

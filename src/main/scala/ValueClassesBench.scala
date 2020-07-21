package foo

import java.util.concurrent.TimeUnit

import org.openjdk.jmh.annotations._

import scala.util.Random

//[info] Benchmark                                             Mode  Cnt     Score     Error   Units
//[info] ValueClassesBench.baseline                            avgt    5     2,301 ±   0,089   ns/op
//[info] ValueClassesBench.baseline:·gc.alloc.rate             avgt    5    ≈ 10⁻⁴            MB/sec
//[info] ValueClassesBench.baseline:·gc.alloc.rate.norm        avgt    5    ≈ 10⁻⁶              B/op
//[info] ValueClassesBench.baseline:·gc.count                  avgt    5       ≈ 0            counts
//[info] ValueClassesBench.map                                 avgt    5   959,469 ±  70,198   ns/op
//[info] ValueClassesBench.map:·gc.alloc.rate                  avgt    5  2043,751 ± 148,426  MB/sec
//[info] ValueClassesBench.map:·gc.alloc.rate.norm             avgt    5  2400,000 ±   0,001    B/op
//[info] ValueClassesBench.map:·gc.churn.G1_Eden_Space         avgt    5  2047,705 ± 148,994  MB/sec
//[info] ValueClassesBench.map:·gc.churn.G1_Eden_Space.norm    avgt    5  2404,747 ±  95,730    B/op
//[info] ValueClassesBench.map:·gc.churn.G1_Old_Gen            avgt    5     0,007 ±   0,004  MB/sec
//[info] ValueClassesBench.map:·gc.churn.G1_Old_Gen.norm       avgt    5     0,008 ±   0,005    B/op
//[info] ValueClassesBench.map:·gc.count                       avgt    5   190,000            counts
//[info] ValueClassesBench.map:·gc.time                        avgt    5    92,000                ms
//[info] ValueClassesBench.mapVC                               avgt    5  1114,873 ±  18,555   ns/op
//[info] ValueClassesBench.mapVC:·gc.alloc.rate                avgt    5  2928,949 ±  50,627  MB/sec
//[info] ValueClassesBench.mapVC:·gc.alloc.rate.norm           avgt    5  4000,000 ±   0,001    B/op
//[info] ValueClassesBench.mapVC:·gc.churn.G1_Eden_Space       avgt    5  2935,540 ± 130,424  MB/sec
//[info] ValueClassesBench.mapVC:·gc.churn.G1_Eden_Space.norm  avgt    5  4009,054 ± 186,523    B/op
//[info] ValueClassesBench.mapVC:·gc.churn.G1_Old_Gen          avgt    5     0,010 ±   0,018  MB/sec
//[info] ValueClassesBench.mapVC:·gc.churn.G1_Old_Gen.norm     avgt    5     0,013 ±   0,024    B/op
//[info] ValueClassesBench.mapVC:·gc.count                     avgt    5   243,000            counts
//[info] ValueClassesBench.mapVC:·gc.time                      avgt    5   127,000                ms
@State(Scope.Benchmark)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value = 1, jvmArgsAppend = Array())
@Threads(1)
@Warmup(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 3, timeUnit = TimeUnit.SECONDS)
class ValueClassesBench {
  var foosVC: List[FooVC] = _
  var foos: List[Foo] = _

  @Setup
  def setUp(): Unit = {
    foosVC = (0 to 99).map(_ => FooVC(IdVC(Random.nextInt()))).toList
    foos = (0 to 99).map(_ => Foo(Id(Random.nextInt()))).toList
  }

  @Benchmark
  def baseline(): Boolean = true

  @Benchmark
  def mapVC(): Boolean =
    foosVC.map(_.id).contains(IdVC(123))

  @Benchmark
  def map(): Boolean =
    foos.map(_.id).contains(Id(123))
}

case class IdVC(value: Int) extends AnyVal
case class FooVC(id: IdVC)

case class Id(value: Int)
case class Foo(id: Id)

import org.http4s.server.blaze.BlazeServerBuilder
import zio._
import service._
import zio.interop.catz._
import zio.interop.catz.implicits._

object Main2 extends App {

  val server = ZIO.runtime[Environment]
    .flatMap {
      implicit rts =>
        BlazeServerBuilder[Task]
          .bindHttp(8080,"localhost")
          .withHttpApp(helloService.service)
          .serve
          .compile
          .drain
    }

  override def run(args: List[String]): ZIO[Main2.Environment, Nothing, Int] =
    server.fold(_ => 1, _ => 0)
}

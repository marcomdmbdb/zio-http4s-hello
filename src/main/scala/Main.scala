import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits.http4sKleisliResponseSyntax
import org.http4s.server.blaze.BlazeServerBuilder
import zio._
import zio.interop.catz._
import zio.interop.catz.implicits._

object Main extends App {
  def run(args: List[String]): ZIO[Environment, Nothing, Int] =
    server.fold(_ => 1, _ => 0)

  val dsl = Http4sDsl[Task]
  import dsl._

  val helloService = HttpRoutes.of[Task] {
    case GET -> Root => Ok("Hello!")
  }.orNotFound

  val server = ZIO.runtime[Environment]
    .flatMap {
      implicit rts =>
        BlazeServerBuilder[Task]
          .bindHttp(8080,"localhost")
          .withHttpApp(helloService)
          .serve
          .compile
          .drain
    }
}

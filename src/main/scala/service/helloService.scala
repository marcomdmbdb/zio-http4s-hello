package service

import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits._
import zio.Task
import zio.interop.catz._

object helloService {
  private val dsl = Http4sDsl[Task]
  import dsl._
  val service = HttpRoutes.of[Task] {
    case GET -> Root => Ok("hello!")
    case GET -> Root / "hello" / name => Ok(s"hello, $name!")
  }.orNotFound
}

import scala.concurrent.ExecutionContext

trait Context {
   implicit val exc: ExecutionContext = ExecutionContext.global
}

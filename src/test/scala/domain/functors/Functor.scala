package domain.functors

trait Functor[F[_]] {
  def map[A, B](a: F[A])(f: A => B): F[B]
}

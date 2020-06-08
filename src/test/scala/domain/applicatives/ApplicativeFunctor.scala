package domain.applicatives

import domain.functors.Functor

trait ApplicativeFunctor[F[_]] extends Functor[F] {
  // known as ap function
  def apply[A, B](f: F[A => B])(fa: F[A]): F[B]

  //  def apply2[A, B, C](f: (A, B) => C)(fa: F[A], fb: F[B]): F[C]
  // def apply3[A, B, C, D](f: (A, B, C) => D)(fa: F[A], fb: F[B], fc: F[C]): F[D]

  def map2[A, B, C](fa: F[A], fb: F[B])(f: (A, B) => C): F[C]

  //lift function
  // also know as 'pure'
  def unit[A](a: A): F[A]
}

package domain.functors

trait ListFunctor extends Functor[List] {
  override def map[A, B](a: List[A])(f: A => B): List[B] = a.map(f)
}

object ListFunctor extends ListFunctor

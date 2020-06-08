package domain.applicatives

trait OptionApplicativeFunctor extends ApplicativeFunctor[Option] {
  override def apply[A, B](f: Option[A => B])(fa: Option[A]): Option[B] = {
    (f, fa) match {
      case (Some(function), Some(value)) => Some(function(value))
      case _ => None
    }
  }

  /*
  * curring: técnica de traducir la evaluación de una función que toma múltiples argumentos para
  * evaluar una secuencia de funciones, cada una con un solo argumento.
  * ejm:
  * (income, bonus) => income + bonus  ==  (income)(bonus) => income + bonus
  *
  * (income)(bonus) => income + bonus == Int => Int => Int
  * */

  override def map2[A, B, C](fa: Option[A], fb: Option[B])(f: (A, B) => C): Option[C] = {
    apply(apply(unit(f.curried))(fa))(fb)

    /*
    * KELLY PERO QUÉ MONDÁAAAAAA
    * vamos a explicarlo de adentro para afuera.
    *
    * f.curried convierte la función f a la siguiente forma: A => B => C
    * unit hace el lift de esa función
    *
    * apply(unit(f.curried))(fa)) esto es lo más complicado de entender basta con que mentalmente se haga
    * el tipo parametrico del apply igual a la función B => C y el resultado sería el sig.
    * val a: Option[B => C] = apply(unit(f.curried))(fa)
    *
    * finalmente queda el apply más extremo
    *  apply(apply(unit(f.curried))(fa))(fb)
    * */
  }


  // lifting
  override def unit[A](a: A): Option[A] = Option(a)

  // map puede ser escrito entonces en términos de las operaciones basicas de Applicative.
  override def map[A, B](a: Option[A])(f: A => B): Option[B] = apply(unit(f))(a)

}

object OptionApplicativeFunctor extends OptionApplicativeFunctor

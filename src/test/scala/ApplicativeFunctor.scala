import cats.Applicative
import cats.instances.future._
import cats.instances.option._
import domain.applicatives.OptionApplicativeFunctor
import domain.functors.Functor
import org.scalatest.funsuite.AnyFunSuite
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class ApplicativeFunctor extends AnyFunSuite with Context {

   test("Un Applicative es un functor"){
     // Comparación con mi implementación
     assert(OptionApplicativeFunctor.isInstanceOf[Functor[Option]])

     // Comparación con la librería cats.
     assert(Applicative[Option].isInstanceOf[cats.Functor[Option]])
   }

  test("Un Applivative functor permite aplicar una función de 2 argumentos"){
    val optNum = OptionApplicativeFunctor.unit(5)
    val optNum2 = OptionApplicativeFunctor.unit(8)
    val funtion =  (x: Int, y: Int) => x * y
    assert(OptionApplicativeFunctor.map2(optNum, optNum2)(funtion).contains(40))
    // podríámos hacer validaciones más robustas usando si usamos map3, map4...
  }

   test("Apply de Applicative se puede lograr el mismo resultado que map de functor"){
     val optVal: Option[Int] = OptionApplicativeFunctor.unit(5)
     val optFuntion: Option[Int => Int] = OptionApplicativeFunctor.unit((x: Int) => x * 5)
     assert(optVal.map(_ * 5) == OptionApplicativeFunctor.apply(optFuntion)(optVal))
   }

  test("Applicative functor permite secuencias efectos que son independientes"){
    case class Result(user: String, data: String)

    def getUser: Future[String] =  Future.successful("User")
    def getData: Future[String] =  Future.successful("Data")

    //Nótese que getUser y getData no dependen entre sí en nada!!

    // Sin Azúcar!!!!
    val result: Future[Result] = getUser.flatMap {
      user => getData.map{
        data => Result(user, data)
      }
    }

    // flujo monádito un tanto imperativo. (con Azúcar!!!)
    val resultSugar: Future[Result] = for {
      user <- getUser
      data <- getData
    } yield Result(user, data)

    // utilizando Applicative functor de Cats para Futuros
    val wrappedFunction: Future[String => Result] =
      getData.map(data => { user: String => Result(user, data)})

    //La función ap, es la función apply antes vista.
    val apResult: Future[Result] = Applicative[Future].ap(wrappedFunction)(getUser)

    val map2Result: Future[Result] = Applicative[Future].map2(getUser, getData)(Result.apply)

    assert(Await.result(apResult, Duration.Inf) == Await.result(map2Result, Duration.Inf))

    //validamos que también dé lo mismo con las anteriores implementaciones
    assert(
      Await.result(resultSugar, Duration.Inf) == Await.result(map2Result, Duration.Inf)
    )
  }

  test("Un applicative functor puede ser utilizado para hacer validaciones"){
    /*  Debido a que en Applicative functor no hay fail fast! por no ser una mónada,
     *  podemos utilizarlo para hacer validaciones.
     */

    
  }

}

import domain.functors.ListFunctor
import org.scalatest.funsuite.AnyFunSuite

class Functor extends AnyFunSuite {
   test("Un Functor nos permite aplicar una función pura a un conjunto de objetos"){
     val list = List(1, 2, 3)
     val func = (x: Int) => x * 2
     val result = ListFunctor.map(list)(func)
     assert(result == List(2, 4, 6))
   }

  test("Un functor no soporta una función de dos argumentos"){
    val func = (x: Int, y: Int) => x * y
    assertDoesNotCompile("ListFunctor.map(list)(func)")
  }


  test("Puedo componer funciones antes de aplicarlas con un Functor"){
    val list = List(1, 2, 3)
    val func: Int => Int = (x: Int) => x * 2
    val func2: Int => Int = (x: Int) => x + 2
    val composedFunc: Int => Int = func andThen func2
    val resultWithComposeFunction = ListFunctor.map(list)(composedFunc)
    assert(resultWithComposeFunction == List(4, 6, 8))
  }







}

package app.components

import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._

object Todos {

  case class Props(todos: Seq[String])

  val Todos =
    ScalaFnComponent[Seq[String]] { todos: Seq[String] =>
      <.ul(todos.toVdomArray(s => Todo.Todo(s)))
    }

}

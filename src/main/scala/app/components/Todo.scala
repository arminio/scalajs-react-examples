package app.components

import japgolly.scalajs.react.{CtorType, _}
import japgolly.scalajs.react.component.ScalaFn.Component
import japgolly.scalajs.react.vdom.html_<^._

object Todo {

  case class Props(todo: String)

  val Todo: Component[String, CtorType.Props] =
    ScalaFnComponent[String] { props =>
      <.li(props)
    }

}

package app

import app.components.Todos.Todos
import app.model.data.BucketAndDirs
import japgolly.scalajs.react.vdom.html_<^._
import japgolly.scalajs.react.{BackendScope, CallbackTo, ReactEventFromInput, ScalaComponent}
import org.scalajs.dom
import org.scalajs.dom.html

object CheckApp {

  case class Props(bucketsAndDirs: List[BucketAndDirs])

  case class State(selectedBucket: String, selectedDir: String, bucketText: String, dirText: String)

  object State {
    val empty = State("", "", "", "")
  }

  class Backend($ : BackendScope[Props, State]) {
    def onBucketTextChange(e: ReactEventFromInput): CallbackTo[Unit] = {
      val newValue = e.target.value
      $.modState(_.copy(bucketText = newValue))
    }

    def onDirTextChange(e: ReactEventFromInput): CallbackTo[Unit] = {
      val newValue = e.target.value
      $.modState(_.copy(dirText = newValue))
    }

    //
    def handleSubmit(e: ReactEventFromInput): CallbackTo[Unit] = ???

    //      e.preventDefaultCB >>
    //        $.modState(s => State(s.items :+ s.text, ""))

    def render(props: Props, state: State) = {
      val bucketListFieldId = "bucket-list"
      val dirListFieldId    = "dir-list"

      <.div(
        <.h3("Check"),
        <.div(
          <.label(^.`for` := "bucket-input", "Choose a bucket:"),
          <.input(^.id := "bucket-input", ^.list := bucketListFieldId, ^.onChange ==> onBucketTextChange, ^.value := state.bucketText),
          <.datalist(^.id := bucketListFieldId, props.bucketsAndDirs.map(bucketAndDir ⇒ <.option(bucketAndDir.bucket)).toVdomArray)
        ),
        <.div(
          <.label(^.`for` := "dir-input", "Choose a dir:"),
          <.input(^.id := "dir-input", ^.list := dirListFieldId, ^.onChange ==> onDirTextChange, ^.value := state.dirText),
          <.datalist(
            ^.id := dirListFieldId,
            getDirs(props, state.bucketText).map(dir ⇒ <.option(dir)).toVdomArray
          )
        ),
        <.form(^.onSubmit ==> handleSubmit, <.button("Check S3 keys"))
      )
    }
  }

  val CheckApp = ScalaComponent
    .builder[Props]
    .initialState(State.empty)
    .renderBackend[Backend]
    .build

  def main(args: Array[String]): Unit = {
    val sampleData = List(
      BucketAndDirs("palmwine2-writable", List("pw2-dir1", "pw2-dir2", "pw2-dir3")),
      BucketAndDirs("almond-writable", List("almond-dir1", "almond-dir2", "almond-dir3")),
      BucketAndDirs("fredhandel-writable", List("fredhandel-dir1", "fredhandel-dir2", "fredhandel-dir3"))
    )

    CheckApp(Props(sampleData)).renderIntoDOM(dom.document.getElementById("app"))
  }

  def getDirs(props: Props, selectedBucket: String): List[String] =
    props.bucketsAndDirs
      .find(bucketAndDir ⇒ bucketAndDir.bucket == selectedBucket)
      .map(bucketsAndDirs ⇒ bucketsAndDirs.dirs)
      .getOrElse(Nil)

}

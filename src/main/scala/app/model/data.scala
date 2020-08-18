package app.model

object data {

  case class BucketAndDirs(bucket:String, dirs: List[String])
//  object BucketAndDirs{
//    implicit val rw: RW[BucketAndDirs] = macroRW
//  }

}

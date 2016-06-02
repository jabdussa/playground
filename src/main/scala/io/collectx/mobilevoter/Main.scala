package io.collectx.mobilevoter

object Main {

  def main(args: Array[String]): Unit = {
    akka.Main.main(Array(classOf[Load].getName))
  }

}
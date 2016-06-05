package io.collectx.mobilevoter

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

//import spray.routing.HttpService

//import io.collectx.mobilevoter.services.PicAddService
//import io.collectx.mobilevoter.DropBoxMockService
//import io.collectx.mobilevoter.servicesservices._ //{PicAddService, DropBoxMockService}



object Main
  extends App
    with PicAddService
    with DropBoxMockService {

  override implicit val sys = ActorSystem()
  override implicit val exe = sys.dispatcher
  override implicit val mat = ActorMaterializer()

  override val conf = ConfigFactory.load()
  override val log  = Logging(sys, getClass)

  Http().bindAndHandle(
    //io.collectx.mobilevoter.services.  PicAddService.route ~ DropBoxMockService.route,
    (PicAddService.route(mat) ~ DropBoxMockService.route(mat)),
    conf.getString("listener.host"),
    conf.getInt("listener.port")
  )

}


import akka.actor.Actor
import spray.routing.HttpService

class SampleServiceActor extends Actor with SampleRoute {
  def actorRefFactory = context
  def receive = runRoute(route)
}

trait SampleRoute extends HttpService {
  val route = {
    get {
      complete("I exist!")
    }
  }
}
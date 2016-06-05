package io.collectx.mobilevoter


import akka.actor.ActorSystem
import akka.event.LoggingAdapter
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Flow
import com.typesafe.config.{ConfigFactory, Config}
import io.collectx.mobilevoter.model.{PicAddRequest, ServiceJsonProtocol}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutor}

/*
import spray.routing._
import spray.http._
*/


trait DropBoxMockService
  extends ServiceJsonProtocol {

  implicit val sys: ActorSystem
  implicit val mat: ActorMaterializer

  implicit def exe: ExecutionContextExecutor


  // could not find implicit value for parameter um: akka.http.scaladsl.unmarshalling.FromRequestUnmarshaller

  def conf: Config

  val log: LoggingAdapter

  //val routes = Routes.get

  lazy val flow: Flow[HttpRequest, HttpResponse, Any] =
    Http().outgoingConnection(
      conf.getString("dropbox.host"),
      conf.getInt("dropbox.port")
    )

  //def route (implicit exe: ExecutionContext, mat: Materializer) = {
  // def route(x: Materializer) = {
  def routed (implicit mat: ActorMaterializer) = {
    logRequestResult("DROPBOX-MOCK-SERVICE")
    path(conf.getString("dropbox.host")) {
      (post & entity(as[PicAddRequest])) { pic =>
        complete {
          s"We Got your pic. Thanks for using The DropBoxMockService"
        }
      }
    }
  }












}

/*
object DropBoxMockServiceRoute extends DropBoxMockService
  (implicit exe: ExecutionContext, mat: Materializer) = {

  override val route: Route = {
    logRequestResult("DROPBOX-MOCK-SERVICE") {
      path(conf.getString("dropbox.host")) {
        (post & entity(as[PicAddRequest])) { pic =>
          complete {
            s"We Got your pic. Thanks for using The DropBoxMockService"
          }
        }
      }
    }
  }

}

*/







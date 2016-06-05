package io.collectx.mobilevoter.services

/**
  * Created by anwarabdus-samad on 6/5/16.
  *
  * See http://stackoverflow.com/questions/32149040/how-to-combine-different-routes-for-http-bindandhandle
  *
  *
  * http://www.lightbend.com/activator/template/akka-http-microservice
  */


import akka.actor.ActorSystem
import akka.event.{LoggingAdapter, Logging}
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.{HttpResponse, HttpRequest}
import akka.http.scaladsl.model.StatusCodes._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.{ActorMaterializer, Materializer}
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import java.io.IOException
import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.math._
import spray.json.DefaultJsonProtocol


trait DropBoxMockService extends ModelsToJsonProtocols {

  implicit val materializer: Materializer
  private val ecStatusResponse = ECStatus("ok", "Welcome to Server")

  val emailRoutes =
    logRequestResult("email-service") {
      pathPrefix("ec") {
        (post & entity(as[ECFailure])) { ecFailure =>
          complete {
            Postman.send(TenantFailure(ecFailure.symbolicName) getMessage)
            "OK"
          }
        }
      } ~
        path("") {
          get {
            complete {
              ecStatusResponse
            }
          }
        }
    }
}
package io.collectx.mobilevoter.services

/**
  * Created by anwarabdus-samad on 6/5/16.
  *
  * See http://stackoverflow.com/questions/32149040/how-to-combine-different-routes-for-http-bindandhandle
  *
  *
  * http://www.lightbend.com/activator/template/akka-http-microservice
  *
  * https://github.com/adhoclabs/developer/blob/master/README.md/
  *
  *
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

// POST https:\\mysite.com\my-powerful-burner-events -d '{ "type": "inboundMedia", "payload": "<picture url>", "fromNumber": "+12222222222", "toNumber": "+ 13333333333" }'
case class MultiMediaEvent(kind: String, payload: String, fromNumber: String, toNumber: String)

// POST https:\\mysite.com\my-powerful-burner-events -d '{ "type": "inboundText", "payload": "Hello", "fromNumber": "+12222222222", "toNumber": "+13333333333" }'
case class TextEvent(kind: String, payload: String, fromNumber: String, toNumber: String)


trait Protocols
  extends DefaultJsonProtocol {

  implicit val mat: Materializer
  implicit val multiMediaJson = jsonFormat4(MultiMediaEvent.apply)
  implicit val textJson = jsonFormat4(TextEvent.apply)

}

trait EventListenerService extends Protocols {
  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val mat: Materializer

  def config: Config
  val logger: LoggingAdapter

  /**
    * Dropbox request & response lazily instantiated and wrapped in a Flow
    *
    * "A Flowing Request & Response" :)
    * 
    */

  lazy val dropBoxResponse: Flow[HttpRequest, HttpResponse, Any] =
    Http().outgoingConnection(
      config.getString("dropbox.host"),
      config.getInt("dropbox.port")
    )

  /**
    * NON Blocking call to DropBox API the response is wrapped in a future
    *
   */

  def dropBoxCall(request: HttpRequest): Future[HttpResponse] =
    Source.single(request).via(dropBoxResponse).runWith(Sink.head)

 // fetchIpInfo

  /**
    * Entry point method called from Service
    *
    */

  def go(ip: String): Future[Either[String, IpInfo]] = {
    ipApiRequest(RequestBuilding.Get(s"/json/$ip")).flatMap { response =>
      response.status match {
        case OK => Unmarshal(response.entity).to[IpInfo].map(Right(_))
        case BadRequest => Future.successful(Left(s"$ip: incorrect IP format"))
        case _ => Unmarshal(response.entity).to[String].flatMap { entity =>
          val error = s"FreeGeoIP request failed with status code ${response.status} and entity $entity"
          logger.error(error)
          Future.failed(new IOException(error))
        }
      }
    }
  }




package io.collectx.mobilevoter.model

import akka.http.scaladsl.server.Route
import spray.json.DefaultJsonProtocol

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

/**
  *
  * Our objects
  *
  */



// POST https:\\mysite.com\my-powerful-burner-events -d
// '{ "type": "inboundMedia", "payload": "<picture url>", "fromNumber": "+12222222222", "toNumber": "+ 13333333333" }'
case class PicAddRequest(kind: String, payload: String, fromNumber: String, toNumber: String)

//POST https:\\mysite.com\my-powerful-burner-events -d
// '{ "type": "inboundText", "payload": "Hello", "fromNumber": "+12222222222", "toNumber": "+13333333333" }'
case class VoteRequest(kind: String, payload: String, fromNumber: String, toNumber: String)


/**
  *
  * Our objects JSON-able\
  *
  */

trait ServiceJsonProtocol extends DefaultJsonProtocol {
  implicit val picAddRequestJson = jsonFormat4(PicAddRequest.apply)
  implicit val voteRequestJson = jsonFormat4(VoteRequest.apply)
  //implicit val route: Route
}

/*

trait MyJsonProtocol extends DefaultJsonProtocol {
  implicit val templateFormat = jsonFormat4(Template)
}

*/




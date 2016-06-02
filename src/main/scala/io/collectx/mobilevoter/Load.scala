package io.collectx.mobilevoter
  
import akka.actor.Actor
import akka.actor.ActorLogging
import akka.stream.ActorMaterializer
import akka.stream.ActorMaterializerSettings

import scala.io.Source
import scala.concurrent.Future
import com.typesafe.config.ConfigFactory

import io.collectx.mobilevoter.models._


class Load extends Actor 
  with ActorLogging {

  import akka.pattern.pipe
  import context.dispatcher
 
  final implicit val materializer: ActorMaterializer = ActorMaterializer(ActorMaterializerSettings(context.system))
 
  val conf = ConfigFactory.load()
  val data = Source.fromFile(conf.getString("gimme.parse.html")).getLines.mkString
  
  override def preStart() = {
    log.info(conf.getString("gimme.hello"))
    log.info("Load Settings")
    log.info("Manufacturers CSS Selector: {}", conf.getString("gimme.parse.manu"))
    log.info("Sample HTML Data: {}", conf.getString("gimme.parse.html"))     
    Future(HtmlMsg(data)).pipeTo(self)
  }
 
  def receive = {
    case h: HtmlMsg if ! h.data.isEmpty =>
      log.info("Received HtmlMsg {} bytes", + h.data.length())
      parse(h.data, conf.getString("gimme.parse.manu"))
    case _  =>
      log.info("I got's nada...")
  }
 
  
   def parse(html: String, selector: String) = {
     
     /*
	
				export JAR=/Users/anwarabdus-samad/Development/gimme/target/scala-2.11/gimme-assembly-1.0.jar
				scala -cp $JAR


      */
      
   }
  
}
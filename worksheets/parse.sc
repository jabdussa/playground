import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import scala.io.Source
import com.typesafe.config.ConfigFactory

import scala.collection.JavaConversions._

import us.codecraft.xsoup.Xsoup
 
object ParseWs {

  val conf 	= ConfigFactory.load()            //> conf  : com.typesafe.config.Config = Config(SimpleConfigObject({"akka":{"act
                                                  //| or":{"creation-timeout":"20s","debug":{"autoreceive":"off","event-stream":"o
                                                  //| ff","fsm":"off","lifecycle":"off","receive":"off","router-misconfiguration":
                                                  //| "off","unhandled":"off"},"default-dispatcher":{"attempt-teamwork":"on","defa
                                                  //| ult-executor":{"fallback":"fork-join-executor"},"executor":"default-executor
                                                  //| ","fork-join-executor":{"parallelism-factor":3,"parallelism-max":64,"paralle
                                                  //| lism-min":8,"task-peeking-mode":"FIFO"},"mailbox-requirement":"","shutdown-t
                                                  //| imeout":"1s","thread-pool-executor":{"allow-core-timeout":"on","core-pool-si
                                                  //| ze-factor":3,"core-pool-size-max":64,"core-pool-size-min":8,"fixed-pool-size
                                                  //| ":"off","keep-alive-time":"60s","max-pool-size-factor":3,"max-pool-size-max"
                                                  //| :64,"max-pool-size-min":8,"task-queue-size":-1,"task-queue-type":"linked"},"
                                                  //| throughput":5,"throughput-deadline-time":"0ms","type":"Dispatcher"},"default
                                                  //| -mailbox":{"mailbox-capa
                                                  //| Output exceeds cutoff limit.
  
  val url 	= conf.getString("gimme.url")     //> url  : String = https://ndb.nal.usda.gov/ndb/search/list
  
  val manu 	= conf.getString("gimme.parse.manu")
                                                  //> manu  : String = //select[@id="manu"]/option/text()
                                                                                          
                                                                                            
	printf("Manufacturer xpath: %s", conf.getString("gimme.parse.manu"))
                                                  //> Manufacturer xpath: //select[@id="manu"]/option/text()
                                                  

  val data = Source.fromFile(conf.getString("gimme.parse.html")).getLines.mkString
                                                  //> data  : String = <!DOCTYPE html><!--[if lt IE 7 ]> <html lang="en" class="no
                                                  //| -js ie6"> <![endif]--><!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> 
                                                  //| <![endif]--><!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-
                                                  //| -><!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]--><!--[if 
                                                  //| (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->     <h
                                                  //| ead>             <title>Foods List</title>        <link rel="shortcut icon" 
                                                  //| href="/ndb/static/images/favicon.ico" type="image/x-icon" />            <lin
                                                  //| k rel="stylesheet" href="/ndb/static/css/main.css" />   <script language="ja
                                                  //| vascript" id="_fed_an_ua_tag" src="https://data.nal.usda.gov/sites/all/theme
                                                  //| s/usda/usda_radix_theme/assets/javascripts/Universal-Federated-Analytics.1.0
                                                  //| .js?agency=USDA&subagency=ARS-NAL"></script><script type="text/JavaScript"> 
                                                  //| 		 var _gaq = _gaq || [];		 // NAL 		  _gaq.p
                                                  //| ush(['_setAccount', 'UA-28627214-1']);		  _gaq.push(['_
                                                  //| Output exceeds cutoff limit.

	val doc = Jsoup.parse(data)               //> doc  : org.jsoup.nodes.Document = <!doctype html>
                                                  //| <!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
                                                  //| <!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
                                                  //| <!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
                                                  //| <!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
                                                  //| <!--[if (gt IE 9)|!(IE)]><!-->
                                                  //| <html lang="en" class="no-js">
                                                  //|  <!--<![endif]-->
                                                  //|  <head> 
                                                  //|   <title>Foods List</title> 
                                                  //|   <link rel="shortcut icon" href="/ndb/static/images/favicon.ico" type="imag
                                                  //| e/x-icon"> 
                                                  //|   <link rel="stylesheet" href="/ndb/static/css/main.css"> 
                                                  //|   <script language="javascript" id="_fed_an_ua_tag" src="https://data.nal.us
                                                  //| da.gov/sites/all/themes/usda/usda_radix_theme/assets/javascripts/Universal-F
                                                  //| ederated-Analytics.1.0.js?agency=USDA&amp;subagency=ARS-NAL"></script>
                                                  //|   <script type="text/JavaScript"> 		 var _gaq = _gaq || [];		
                                                  //|  // NAL 		  _gaq.push(['_setAccount', 'UA-28627214-1']);	
                                                  //| Output exceeds cutoff limit.
		                                 
	          
  val res = Xsoup.compile(manu)
   	.evaluate(doc)
   	.list()
   	.toList
   	.filter(! _.matches(conf.getString("gimme.parse.stop")))

	           		
	for(x<-res) printf(">%s<\n", x)
	
	res.size
	

}
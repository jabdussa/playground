enablePlugins(JavaAppPackaging)

name := "mobilevoter"

organization := "io.collectx" 

version := "1.0"

scalaVersion := "2.11.2"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV       = "2.4.3"
  val scalaTestV  = "2.2.6"
  Seq(
    /*
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-stream" % akkaV,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaV,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaV,
    "org.scalatest"     %% "scalatest" % scalaTestV % "test",
    */
    "com.typesafe.akka" 	%% "akka-actor"			  % "2.4.4"
    ,"com.typesafe.akka" 	%% "akka-http-core" 	% "2.4.4"
    ,"com.typesafe.akka" 	%% "akka-slf4j"			  % "2.4.4"
    ,"ch.qos.logback"		  % "logback-classic" 	% "1.1.7"
    ,"org.jsoup"			    % "jsoup"				      % "1.9.1"
    ,"us.codecraft" 		  % "xsoup" 				    % "0.3.1"
  )
}

//Revolver.setting

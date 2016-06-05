enablePlugins(JavaAppPackaging)

name := "mobilevoter"

organization := "io.collectx" 

version := "1.0"

scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV       = "2.4.4"
  val scalaTestV  = "2.2.6"
  Seq(
    //
    // AKKA
    //
    "com.typesafe.akka"   %% "akka-actor"         % akkaV,
    "com.typesafe.akka"   %% "akka-stream"        % akkaV,
    "com.typesafe.akka"   %% "akka-http-experimental" % akkaV,
    "com.typesafe.akka"   %% "akka-http-spray-json-experimental" % akkaV,
    "com.typesafe.akka" 	%% "akka-http-core" 	  % akkaV,
    "com.typesafe.akka"   %% "akka-http-testkit"  % akkaV,
    "com.typesafe.akka" 	%% "akka-slf4j"			    % akkaV,
    //
    // HTML PARSER
    //
    "org.jsoup"			       % "jsoup"				    % "1.9.1",
    "us.codecraft" 		     % "xsoup" 				    % "0.3.1",
    //
    // JSON - SPRAY
    //
    "io.spray"            %% "spray-json"       % "1.3.2",
    //
    // LOGGING
    //
    "ch.qos.logback"		   % "logback-classic" 	% "1.1.7",
    //
    // TESTING
    //
    "org.scalatest"       %% "scalatest"          % scalaTestV % "test"
  )
}

Revolver.settings

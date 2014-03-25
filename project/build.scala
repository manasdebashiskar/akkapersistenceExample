import sbt._
import sbt.Keys._

import akka.sbt.AkkaKernelPlugin
import akka.sbt.AkkaKernelPlugin.{ Dist, distSettings, distJvmOptions }

object PersistedBuild extends Build {
  lazy val defaultSettings = Defaults.defaultSettings ++ Seq(
    organization := "exactEarth",
    version      := "1.0",
    scalaVersion := "2.10.2",
    fork         := true,
    //parallelExecution in Test := false,
    scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked"),
    libraryDependencies ++= Seq(
      "ch.qos.logback"      % "logback-classic" % "1.0.0",
      "com.typesafe.akka"   %% "akka-actor"   % "2.3.0",
      "com.typesafe.akka"   %% "akka-kernel"  % "2.3.0",
      "com.typesafe.akka"   %% "akka-remote"  % "2.3.0",
      "com.typesafe.akka"   %% "akka-slf4j"   % "2.3.0",
      "com.typesafe.akka"   %% "akka-contrib" % "2.3.0",
      "commons-net"         %  "commons-net"  % "3.3",
      "org.apache.commons"  %  "commons-math" % "2.2",
      "com.typesafe.akka"   %% "akka-persistence-experimental" % "2.3.0",
      "com.typesafe.akka"   %% "akka-testkit" % "2.3.0" % "test",
      "org.scalatest"       %% "scalatest"    % "2.0"   % "test"
    )
  )
  
  lazy val PersistedExample = Project(
    id = "PersistedExample",
    base = file("PersistedExample") ,
    settings = defaultSettings
            ++ distSettings 
  ) 
 
}



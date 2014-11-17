import sbt._
import sbt.Keys._

import akka.sbt.AkkaKernelPlugin
import akka.sbt.AkkaKernelPlugin.{ Dist, distSettings, distJvmOptions }

object versions {
    val akkaVersion = "2.3.7"
}

object PersistedBuild extends Build {
  lazy val defaultSettings = Defaults.defaultSettings ++ Seq(
    organization := "exactEarth",
    version      := "1.0",
    scalaVersion := "2.11.4",
    fork         := true,
    //parallelExecution in Test := false,
    scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked"),
    libraryDependencies ++= Seq(
      "ch.qos.logback"      % "logback-classic" % "1.0.0",
      "com.typesafe.akka"   %% "akka-actor"   % versions.akkaVersion,
      "com.typesafe.akka"   %% "akka-kernel"  % versions.akkaVersion,
      "com.typesafe.akka"   %% "akka-remote"  % versions.akkaVersion,
      "com.typesafe.akka"   %% "akka-slf4j"   % versions.akkaVersion,
      "com.typesafe.akka"   %% "akka-contrib" % versions.akkaVersion,
      "commons-net"         %  "commons-net"  % "3.3",
      "org.apache.commons"  %  "commons-math" % "2.2",
      "com.typesafe.akka"   %% "akka-persistence-experimental" % versions.akkaVersion,
      "com.typesafe.akka"   %% "akka-testkit" % versions.akkaVersion % "test",
      "org.scalatest"       %% "scalatest"    % "2.2.1"   % "test"
    )
  )
  
  lazy val PersistedExample = Project(
    id = "PersistedExample",
    base = file("PersistedExample") ,
    settings = defaultSettings
            ++ distSettings 
  ) 
 
}



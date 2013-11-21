import AssemblyKeys._

assemblySettings

name := "Akka playground"

version := "1.0"

scalaVersion := "2.10.3"

libraryDependencies += "junit" % "junit" % "4.10"

libraryDependencies += "org.scalatest" %% "scalatest" % "1.9.1"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.2.3"

libraryDependencies += "com.typesafe.akka" %% "akka-kernel" % "2.2.3"

libraryDependencies += "com.typesafe.akka" %% "akka-remote" % "2.2.3"

libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.2.3"
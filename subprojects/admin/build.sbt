name := """admin"""

version := "1.0-SNAPSHOT"

lazy val common = (project in file("../common"))
  .enablePlugins(PlayScala)

lazy val admin = (project in file("."))
  .enablePlugins(PlayScala)
  .dependsOn(common)
  .aggregate(common)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  cache,
  ws,
  "com.typesafe.play" %% "play-slick" % "1.0.0",
  "mysql" % "mysql-connector-java" % "5.1.24"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

// Scala Compiler Options
scalacOptions in ThisBuild ++= Seq(
  "-target:jvm-1.8",
  "-encoding", "UTF-8",
  "-deprecation", // warning and location for usages of deprecated APIs
  "-feature", // warning and location for usages of features that should be imported explicitly
  "-unchecked", // additional warnings where generated code depends on assumptions
  "-Xlint", // recommended additional warnings
  "-Xcheckinit", // runtime error when a val is not initialized due to trait hierarchies (instead of NPE somewhere else)
  "-Ywarn-adapted-args", // Warn if an argument list is modified to match the receiver
  "-Ywarn-inaccessible",
  "-Ywarn-dead-code"
)

routesGenerator := InjectedRoutesGenerator

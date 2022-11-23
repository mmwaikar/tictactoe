name         := """TicTacToe"""
organization := "com.codionics"
scalaVersion := "2.13.10"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  guice,
  "com.beachape"           %% "enumeratum"         % "1.7.0",
  "com.beachape"           %% "enumeratum-play"    % "1.7.0",
  "org.scalatest"          %% "scalatest"          % "3.2.14" % Test,
  "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0"  % Test
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.codionics.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.codionics.binders._"
play.sbt.routes.RoutesKeys.routesImport += "com.codionics.tictactoe.models.enums._"

val akka = Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.4.16",
  "com.typesafe.akka" %% "akka-cluster" % "2.4.16"
)

lazy val commonSettings = Seq(
  organization := "com.frenchcoder",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.8"
)

lazy val messages = (project in file("messages"))
  .settings(
    commonSettings
  )

lazy val router = (project in file("router"))
  .settings(
    commonSettings,
    libraryDependencies ++= akka
  )
  .dependsOn(messages)

lazy val worker = (project in file("worker"))
  .settings(
    commonSettings,
    libraryDependencies ++= akka
  )
  .dependsOn(messages)



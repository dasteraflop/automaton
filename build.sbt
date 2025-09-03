ThisBuild / version := "0.0.1-SNAPSHOT"

scalaVersion := "3.7.2"

assembly / mainClass := Some("automaton.Sandbox")

assembly / assemblyMergeStrategy  := {
  case PathList("META-INF", "services", xs @ _*) => MergeStrategy.concat
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case "module-info.class" => MergeStrategy.discard
  case "play/reference-overrides.conf" => MergeStrategy.discard
  // Add more specific cases for other conflicting files
  case "deriving.conf" => MergeStrategy.concat // Example for a specific config file
  case x => val oldStrategy = (assembly / assemblyMergeStrategy).value
    oldStrategy(x)
}

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "automaton"
  )

libraryDependencies ++= Seq(
  "com.openai" % "openai-java" % "3.1.2",
  "com.itextpdf" % "itextpdf" % "5.5.13.4",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.20.0",
  guice,

  "org.scalatestplus" %% "junit-4-13" % "3.2.19.1" % Test,
  "org.scalatest" %% "scalatest" % "3.2.19" % Test
)

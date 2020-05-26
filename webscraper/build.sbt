name := "webscraper"

version := "0.1"

scalaVersion := "2.13.2"

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
  "net.ruippeixotog" %% "scala-scraper" % "2.2.0",
  "com.clearspring.analytics" % "stream" % "2.9.0",
  "com.typesafe.akka" %% "akka-actor" % "2.6.5",
  "net.sourceforge.htmlcleaner" % "htmlcleaner" % "2.6.1"
)

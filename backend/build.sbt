// build.sbt
name := "RagaExperiments"
version := "0.1"
scalaVersion := "2.13.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.2.6",
  "com.typesafe.akka" %% "akka-stream" % "2.6.16",
  "javax.sound" % "midi" % "1.0.2"
)

// src/main/scala/RagaExperiments/Server.scala
package RagaExperiments

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import javax.sound.midi.MidiSystem

object Server extends App {
  implicit val system = ActorSystem("raga-experiments")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val route =
    path("play" / Segment) { swaraName =>
      get {
        val swaraFreq = swaraName match {
          case "sa" => 60 // Example frequency
          case "ga3" => 64 // Example frequency
          case _ => 60
        }
        val synth = MidiSystem.getSynthesizer
        synth.open()
        val channel = synth.getChannels()(0)
        channel.noteOn(swaraFreq, 100)
        Thread.sleep(500)
        channel.noteOff(swaraFreq)
        synth.close()
        complete(s"Played $swaraName")
      }
    }

  Http().newServerAt("localhost", 8080).bind(route)
}
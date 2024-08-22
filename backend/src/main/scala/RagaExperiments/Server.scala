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
    path("play" / Segment / IntNumber) { (swaraName, octaveOffset) =>
      get {
        val swaraInstance = new swara(swaraName, octaveOffset)
        val swaraFreq = swaraInstance.swaraFreq
        if (swaraFreq > 0) {
          val synth = MidiSystem.getSynthesizer
          synth.open()
          val channel = synth.getChannels()(0)
          channel.noteOn(swaraFreq, 100)
          Thread.sleep(500)
          channel.noteOff(swaraFreq)
          synth.close()
          complete(s"Played $swaraName at octave offset $octaveOffset")
        } else {
          complete(s"Invalid swara: $swaraName")
        }
      }
    }

  Http().newServerAt("localhost", 8080).bind(route)
}
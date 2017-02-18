/**
  * Created by Rajesh Sampathkumar on 2/18/2017.
  * This is a Scala object which has a separate main function
  * This is meant as a testbed for audio playback using the Java MIDI libraries
  * This functionality is repackaged into the RagaExperiments.scala file
  *
  */
package RagaExperiments

import javax.sound.midi.MidiSystem

object playSwara {

  def main(args: Array[String]) {

    //Creating new Swara objects
    val sa = new swara("sa",0)
    val ga3 = new swara("ga3",0)

    //Creating a Synthesizer object from the JVM packages and opening it
    val synth = MidiSystem.getSynthesizer
    synth.open()

    // Setting up a channel for sending notes to
    val channel = synth.getChannels()(0)

    // Playing a note, based on the frequency of the swara in question
    channel.noteOn(sa.swaraFreq, 100)
    Thread.sleep(500)
    channel.noteOff(sa.swaraFreq)

    //Playing the other note, in a similar way
    channel.noteOn(ga3.swaraFreq, 100)
    Thread.sleep(500)
    channel.noteOff(ga3.swaraFreq)
    Thread.sleep(500)

  }

}

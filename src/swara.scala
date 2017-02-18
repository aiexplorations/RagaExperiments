/**
  * Created by Rajesh Sampathkumar on 2/18/2017.
  *
  * The swara (or note) is the smallest element of a raga. It can take 12 different forms in Carnatic music.
  *
  * Based on the swara string passed, the appropriate frequency of note is modelled.
  * Using this frequency model, the note can be played through the JavaFX Midi interface.
  *
  * Note: Gaps in the alapana are considered interesting in Carnatic music as they suggest orchestration.
  * This class allows for such "gaps" in alapana by returning 13 total cases (including one for silence, or no note).
  *
  */


package RagaExperiments

import java.util
import javax.sound.midi.MidiDevice.Info
import javax.sound.midi._

class swara (note: String, octaveOffset: Int) {

  val swaraPos:String = note
  val offsetOctavesBy = octaveOffset
  val swaraFreq: Int = swaraPos match {
    case "sa" => 60 +(offsetOctavesBy * 12)
    case "ri1" => 61 +(offsetOctavesBy * 12)
    case "ri2" => 62 +(offsetOctavesBy * 12)
    case "ri3" => 63 +(offsetOctavesBy * 12)
    case "ga1" => 62 +(offsetOctavesBy * 12)
    case "ga2" => 63 +(offsetOctavesBy * 12)
    case "ga3" => 64 +(offsetOctavesBy * 12)
    case "ma1" => 65 +(offsetOctavesBy * 12)
    case "ma2"  => 66 +(offsetOctavesBy * 12)
    case "pa" => 67 +(offsetOctavesBy * 12)
    case "da1" => 68 +(offsetOctavesBy * 12)
    case "da2" => 69 +(offsetOctavesBy * 12)
    case "da3" => 70 +(offsetOctavesBy * 12)
    case "ni1" => 69 +(offsetOctavesBy * 12)
    case "ni2" => 70 +(offsetOctavesBy * 12)
    case "ni3" => 71 +(offsetOctavesBy * 12)
    case "Sa" => 72 +(offsetOctavesBy * 12)
    case " " => 0 // A zero is returned when there are gaps or spaces in the alapana.
  }



}

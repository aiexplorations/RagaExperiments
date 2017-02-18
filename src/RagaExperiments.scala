
/**
  * Created by Rajesh Sampathkumar on 2/18/2017.
  * This is a program in which you can define custom ragas (Carnatic music) and generate sequences from them
  * The playback is handled in the playSwaras() function
  * The main function depends on two classes, the raga class and a swara class.
  * In the world of Carnatic classical music, ragas are composed of sequences of swaras.
  * This is an attempt to model them at a rudimentary level, without the microtonal variations (gamakas).
  */

package RagaExperiments

/*
The below imports help control sequence generation and playback
 */

import javax.sound.midi._
import scala.util.Random
import scala.collection.mutable.ArrayBuffer


//The RagaExperiments object contains the main function, and also plays the swaras

object RagaExperiments {


  /*
   A function that takes a sequence of swaras, a set of speed and dwell parameters
   Plays back the sequence of swaras according to the user's preference
   Slower speeds are associated with higher speedSetter values
   minModifier and dwellModParam are together adjusted to get a range of variations
    */

  def playSwaras(swaraSeq:Array[swara], minModifier: Int = 2, dwellModParam: Int = 2, speedSetter: Int = 5) ={

    //We're using a Synthesizer from the JavaFX Midi interface
    val synth = MidiSystem.getSynthesizer
    synth.open()

    /*
    Once the synth channel is opened, we can set noteSpeed and dwellLength
    noteSpeed controls how fast the keys come down on the virtual instrument (a piano).
    Higher noteSpeed values are associated with a louder sound, while lower ones give a softer sound
    dwellLength controls how long (in absolute terms) each note is played
    dwellLength is modified by randDwellModifier (which depends on minModifier). See below.
     */
    val channel = synth.getChannels()(0)
    val noteSpeed = 150
    val dwellLength = 80

    //randDwellModifier is assigned a random value to adjust the extent of variation in the dwell time of a swara

    var randDwellModifier = Random.nextInt(1) * minModifier

    //This loop is where we play each note in the swara sequence (swaraSeq)

    for(swara <- swaraSeq){

      channel.noteOn(swara.swaraFreq, noteSpeed)
      randDwellModifier = (randDwellModifier + Random.nextInt(dwellModParam))%speedSetter
      Thread.sleep(dwellLength * randDwellModifier )
      channel.noteOff(swara.swaraFreq, noteSpeed)

    }

  }

  /*
  Main function in which we construct aarohanam and avarohanam sequences, set up ragas based on them, and generate sequences.
  An aarohanam is an ascending set of swaras that characterises a certain raga.
  An avarohanam is a descending set of swaras that characterises the raga.
  Improvization in Carnatic music is based on a complex set of rules, based on the aarohanam, avarohanam and other patterns.
  The program doesn't aim to capture the full complexity of Carnatic music, only the variations that can be easily shown on MIDI
   */


  def main(args: Array[String]) {

    /*
    Defining and playing the Kharaharapriya raga (Dorian mode in Western Classical Music)
    Kharaharapriya is a popular, "sampoorna" raga (or a Melakarta raga)
    The ascent (aarohanam) and descent (avarohanam) are modeled as sequences of strings
    The raga object is constructed by pattern-matching these strings to tonal frequencies (see swara Class).
     */
    val ascent1 = Array("sa", "ri2", "ga2", "ma1", "pa", "da2", "ni2", "Sa")
    val descent1 = Array("Sa", "ni2", "da2", "pa", "ma1", "ga2", "ri2", "sa")
    val kharaharapriya = new raga(ascent1, descent1)
    val kharaharapriyaAlapana = kharaharapriya.genSequence(300,6, 0.5)
      .++=(kharaharapriya.genSequence(300, 5, 0.4))
      .++=(kharaharapriya.genSequence(300, 8, 0.6))

    println("Kharaharapriya: ")


    /*
    The below function plays an "aalapana" of Kharaharapriya. An "alapana" is an extended sequence of notes.
    Alapanas are constructed using one or many "sangatis" - each "sangati" is literally a progression of notes.
    See the raga class for how the sangatis are randomly picked from the aarohanam and avarohanam and constructed
     */

    playSwaras(kharaharapriyaAlapana.toArray, 2, 2, 4)

    /*

    Below, there are numerous other Carnatic ragas. The code can be specifically uncommented to listen to these scales.

     */


    //Defining and playing the Reetigowla raga (unique to Carnatic music)
    val ascent2 = Array("sa", "ga2", "ri2", "ga2", "ma1", "ni2", "da2", "ma1", "ni2", "ni2", "Sa")
    val descent2 = Array("Sa", "ni2", "da2", "ma1", "ga2", "ma1", "pa", "ma1", "ga2", "ri2", "sa")
    val reetigowla = new raga(ascent2, descent2) //The actual raga object takes ascent and descent parameters
    val rgA1 = reetigowla.genSequence(10, 5, 0.3)
    val rgA2 = reetigowla.genSequence(20, 5, 0.8)
    val rgA3 = reetigowla.genSequence(20, 5, 0.5)
    val rgA4 = reetigowla.genSequence(10, 5, 0.2)
    val reetigowlaAlapana = rgA1.++=(rgA2).++=(rgA3).++=(rgA4) // Chains of sequences can be constructed as Arrays


    //println("\nReetigowla: ")
    //playSwaras(reetigowlaAlapana.toArray, 3,3,9)

    //Defining and playing the Revati raga (unique to Carnatic music)
    val ascent3 = Array("sa", "ri1", "ma1", "pa", "ni2", "Sa")
    val descent3 = Array("Sa", "ni2", "pa", "ma1", "ri1", "sa")
    val revati = new raga(ascent3, descent3)
    val revatiAlapana = revati.genSequence(350, 5, 0.3)

    //println("Revati alapana:")
    //playSwaras(revatiAlapana.toArray)

    //Defining and playing the Shri Ragam
    val ascent4 = Array("sa", "ri2", "ma1", "pa", "ni2", "Sa")
    val descent4 = Array("Sa", "ni2", "pa", "da2", "ni2", "pa", "ma1", "ri2", "ga2", "ri2", "sa")
    val shri = new raga(ascent4, descent4)
    val shriAlapana = shri.genSequence(1000, 5, 0.5)

    //println("Shri alapana:")
    //playSwaras(shriAlapana.toArray,3,3,5)

    //Defining and playing Vakulabharanam
    val ascent5 = Array("sa", "ri1", "ga3", "ma1", "pa", "da1", "ni2", "Sa")
    val descent5 = Array("Sa", "ni2", "da1", "pa", "ma1", "ga3", "ri1", "sa")
    val vakulabharanam = new raga(ascent5, descent5)
    val vakulabharanamAlapana = vakulabharanam.genSequence(300, 3, 0.5)
                                .++=(vakulabharanam.genSequence(300,5, 0.4))
                                .++=(vakulabharanam.genSequence(300,3, 0.6))

    //println("Vakulabharanam alapana:")
    //playSwaras(vakulabharanamAlapana.toArray, 2, 2, 3)

    //Defining and playing Vaagaadeshwari
    val ascent6 = Array("sa", "ri3", "ga3", "ma1", "pa", "da2", "ni2", "Sa")
    val descent6 = Array("Sa", "ni2", "da2", "pa", "ma1", "ga3", "ri3", "sa")
    val vaagaadeeshwari = new raga(ascent6, descent6)
    val vaagaadeeshwariAlapana = vaagaadeeshwari.genSequence(300, 5, 0.5)
      .++=(vaagaadeeshwari.genSequence(300, 5, 0.4))
      .++=(vaagaadeeshwari.genSequence(300, 6, 0.6))

    //println("Vaagaadeeshwari alapana:")
    //playSwaras(vaagaadeeshwariAlapana.toArray, 2, 2, 4)

    //Defining and playing Veeravasantham (Varunapriya)
    val ascent7 = Array("sa", "ga2", "ri2", "ga2", "ma1", "pa", "Sa")
    val descent7 = Array("Sa", "ni3", "da3", "pa", "ma1", "ga2", "ri2", "sa")
    val veeravasantam = new raga(ascent7,descent7)
    val veeravasantamAlapana = veeravasantam.genSequence(300, 5, 0.5)
      .++=(veeravasantam.genSequence(300, 5, 0.4))
      .++=(veeravasantam.genSequence(300, 6, 0.6))

    //println("Veeravasantam Alapana: ")
    //playSwaras(veeravasantamAlapana.toArray, 3,3,5)

  }

}

/**
  * Created by Rajesh Sampathkumar on 2/18/2017.
  *
  * A raga (raaga, rAga) is a scale in the Carnatic and Hindustani style of Indian music.
  * In this program we're modeling the ragas of the Carnatic (southern) style of music.
  * The raga class here comprises of many swaras (notes). In reality, ragas are aggregations of
  * swaras and "gamakas" (or microtonal variations). Presently, we can construct a raga as a combination of the
  * ascending notes (aarohanam) and descending notes (avarohanam).
  *
  * In this program, we can generate sequences of swaras (notes) from a given raga, having defined
  * its aarohanam and avarohanam pattern. The sequence can be varied beyond one octave using the s parameter.
  *
  * The maximum sequence length for generating aalapanas can be provided in the object.
  * The sequence generation can also be done to fit the mood - by choosing "uppity" values > 0.5
  *
  */


package RagaExperiments

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class raga(aarohanam: Array[String], avarohanam: Array[String]) {

  val aaro = aarohanam
  val avaro = avarohanam

  def raga = {

    def aaroDefine = aaro.foreach(x => new swara(x,0))
    def avaroDefine = avaro.foreach(x => new swara(x,0))

    aaroDefine
    avaroDefine
  }

  def genSequence(length: Int, maxSeqLength: Int, uppity:Double = 0.5) = {
    val swaraLength = length
    val sequence = new ArrayBuffer[swara](swaraLength)
    while (sequence.length <= swaraLength){

      /*
      The randomBool variable allows us to randomly choose to take sequences from either the aarohanam or avarohanam.
      The randomLength parameter takes anywhere between 3 and the total length of the aarohanam or avarohanam array.

       */

      val randomBool = scala.util.Random.nextDouble()
      val randomLength = scala.util.Random.nextInt(maxSeqLength) + 2

      /*
      This swara closure Array contains the swaras that will be appended to an alapana.
      The closure below also takes in uppity when constructing the sequence of interest.
       */

      val randomSwara:Array[swara] = if (randomBool<=uppity) {

        // This random number controls the number of swaras in sequence we take from aarohanam
        val r = scala.util.Random.nextInt(aaro.length)

        // This random number controls which octave the swaras will come from (upper, lower or middle)
        val s = scala.util.Random.nextInt(5) - 2

        aaro.slice(r-randomLength,r+randomLength).map( x => new swara(x, s))
      } else {

        // This random number controls the number of swaras in sequence we take from avarohanam
        val r = scala.util.Random.nextInt(avaro.length)

        // This random number controls which octave the swaras will come from (upper, lower or middle)
        val s = scala.util.Random.nextInt(5) - 2
        avaro.slice(r-randomLength,r+randomLength).map( x => new swara(x, s))
      }


      /*
      So far the random swara sequence has been constructed. Now it is stitched to the sequence to be returned.
      The sequence will be filled until the number of swaras is less than the maximum sequence length.
       */
      sequence.++=(randomSwara)

      //Until this point, the swaras were just String objects, now they become swara objects
      sequence.++=(Array(" ").map( x => new swara(x, 0)))

    }

  sequence // The sequence of swaras is finally returned from this function
  }

}

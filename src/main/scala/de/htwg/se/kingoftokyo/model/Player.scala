package de.htwg.se.kingoftokyo.model

import de.htwg.se.kingoftokyo.KingOfTokyo

case class Player(name:String, var heart: Int = KingOfTokyo.initHeart, var stars: Int = 0, var energy: Int = 0) {

   val maxHeart = 10
   val zero = 0
   val maxStar = 20

   //  var handCards = IndexedSeq[Card]()

   //  def addCard(card: Card): IndexedSeq[Card] = {
   //    var buffer = handCards.toBuffer
   //    buffer += card
   //    val newHand = buffer.toIndexedSeq
   //    newHand
   //  }

   def gainHeart(gain: Int): Player = {
      if (maxHeart < (gain + this.heart)) {
         copy(name, maxHeart, stars, energy)
      } else {
         copy(name, heart + gain, stars, energy)
      }
   }
   def looseHeart(lost: Int): Player = {
      if (zero > (heart - lost)) {
         copy(name, zero, stars, energy)
      } else {
         copy(name, heart - lost, stars, energy)
      }
   }

   def gainStar(gain: Int): Player = {
      if (maxStar < (stars + gain)) {
         copy(name, heart, maxStar, energy)
      } else {
         copy(name, heart, stars + gain, energy)
      }
   }
   def looseStar(lost: Int): Player = {
      if (zero > (stars - lost)) {
         copy(name, heart, zero, energy)
      } else {
         copy(name, heart, stars - lost, energy)
      }
   }

   //Gain as much as you want
   def gainEnergy(gain: Int): Player =  copy(name, heart, stars, energy + gain)
   //Buying cards has to check if you cn afford them but if else to make sure
   def spendEnergy(spend: Int): Player = {
      if (zero >= energy -spend) {
         copy(name, heart, stars, zero)
      } else {
         copy(name, heart, stars, energy - spend)
      }
   }

   def info : String = f"$name%s, Heart: $heart%d, Stars: $stars%d, Energy: $energy%d"

}



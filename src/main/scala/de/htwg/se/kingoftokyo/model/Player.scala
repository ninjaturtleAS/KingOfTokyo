package de.htwg.se.kingoftokyo.model

case class Player(name:String,var heart:Int = 10, var stars:Int = 0 ,var energy:Int = 0) {

   //  var handCards = IndexedSeq[Card]()

   //  def addCard(card: Card): IndexedSeq[Card] = {
   //    var buffer = handCards.toBuffer
   //    buffer += card
   //    val newHand = buffer.toIndexedSeq
   //    newHand
   //  }

   def gainHeart(gain: Int): Player = {

      copy(name, heart + gain, stars, energy)
   }
   def looseHeart(lost: Int): Player = copy(name, heart - lost, stars, energy)

   def gainStar(gain: Int): Player = copy(name, heart, stars + gain, energy)
   def looseStar(lost: Int): Player = copy(name, heart, stars - lost, energy)

   def gainEnergy(gain: Int): Player =  copy(name, heart, stars, energy + gain)
   def spendEnergy(spend: Int): Player = copy(name, heart, stars, energy - spend)

   def info : String = f"$name%s, Heart: $heart%d, Stars: $stars%d, Energy: $energy%d"

}


package de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent

case class Player(name:String, var energy: Int = 0, var heart: Int = 2*5, var stars: Int = 0) {

   val maxHeart = 10
   val zero = 0
   val maxStar = 20

   def gainHeart(gain: Int): Player = {
      if (maxHeart < (gain + this.heart)) {
         copy(name, energy, maxHeart, stars)
      } else {
         copy(name, energy, heart + gain, stars)
      }
   }
   def looseHeart(lost: Int): Player = {
      if (zero > (heart - lost)) {
         copy(name, energy, zero, stars)
      } else {
         copy(name, energy, heart - lost, stars)
      }
   }

   def gainStar(gain: Int): Player = {
      if (maxStar < (stars + gain)) {
         copy(name, energy, heart, maxStar)
      } else {
         copy(name, energy, heart, stars + gain)
      }
   }
   def looseStar(lost: Int): Player = {
      if (zero > (stars - lost)) {
         copy(name, energy, heart, zero)
      } else {
         copy(name, energy, heart, stars - lost)
      }
   }

   //Gain as much as you want
   def gainEnergy(gain: Int): Player =  copy(name,  energy + gain, heart, stars)
   //Buying cards has to check if you cn afford them but if else to make sure
   def spendEnergy(spend: Int): Player = {
      if (zero >= energy -spend) {
         copy(name, 0, heart, stars)
      } else {
         copy(name, energy - spend, heart, stars)
      }
   }

   def info : String = f"$name%s (Energy: $energy%d, Heart: $heart%d, Stars: $stars%d)"
}

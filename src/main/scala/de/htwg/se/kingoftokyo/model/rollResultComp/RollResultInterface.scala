package de.htwg.se.kingoftokyo.model.rollResultComp

import de.htwg.se.kingoftokyo.model.rollResultComp.rollResultComp1.RollResult

trait RollResultInterface {

  def throwOne(): Vector[Int]
  def filterThrowResult(selectionStr: String): RollResult
  def mapFacesToString(i: Int): String
  def toString(): String
  def evaluateHeart(): Int
  def evaluateEnergy(): Int
  def evaluateAttacks(): Int
  def evaluateStars(): Int
  def length: Int
  def toIntVector: Vector[Int]
}
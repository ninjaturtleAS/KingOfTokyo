package de.htwg.se.kingoftokyo.model.rollResultComp


trait RollResultInterface {

  def throwOne(): Vector[Int]
  def filterThrowResult(selectionStr: String): RollResultInterface
  def mapFacesToString(i: Int): String
  def toString(): String
  def evaluateHeart(): Int
  def evaluateEnergy(): Int
  def evaluateAttacks(): Int
  def evaluateStars(): Int
  def length: Int
  def toIntVector: Vector[Int]
  def rollResultXML: String
  def set(result: Vector[Int]): RollResultInterface
  def stringToIntVector(str: String): Vector[Int]
}

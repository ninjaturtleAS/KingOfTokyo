package de.htwg.se.kingoftokyo.controller

import java.io.ObjectInputFilter.Status

object State extends Enumeration {
  type GameState = Value
  val WaitForPlayerNames, WaitFor1stThrow, WaitFor2ndThrow, ThrowComplete, WaitForAttack, WaitForKotDecision = Value

//  def getMessageFromStatus: String = {
//    State match {
//      case WaitForPlayerNames => "Bitte Spielernamen kommagetrennt eingeben"
//      case WaitFor1stThrow|WaitFor2ndThrow => "Ihre Auswahl"
//      case ThrowComplete => "Wurf wird ausgewertet"
//      case WaitForAttack => "Wen wollen Sie angreifen?"
//      case WaitForKotDecision => "Wollen Sie King of Tokyo bleiben (J/N)"
//      case _ => "not implemeted yet"
//    }
//  }

  val map = Map[GameState, String] (
    WaitForPlayerNames -> "Bitte Spielernamen kommagetrennt eingeben",
    WaitFor1stThrow -> "Ihre Auswahl",
    WaitFor2ndThrow -> "Ihre Auswahl",
    ThrowComplete -> "Wurf wird ausgewertet",
    WaitForAttack -> "Wen wollen Sie angreifen?",
    WaitForKotDecision -> "Wollen Sie King of Tokyo bleiben (J/N)"
  )

  def message(state: GameState) = {
    map(state)
  }

}
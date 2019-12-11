package de.htwg.se.kingoftokyo.controller

import scala.swing.event.Event

class PlayersCreated extends Event
case class GridSizeChanged(newSize: Int) extends Event

/*
class ThrowEvaluated extends Event
class ThrowCompleted extends Event
class LapNrInc extends Event
class DiesThrown extends Event
class ResultsFiltered extends Event
*/

class PlaygroundChanged extends Event
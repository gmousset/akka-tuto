package com.exp.calculation

import akka.actor.Actor

/**
 * Created by gwendalmousset on 02/11/2015.
 */
class Worker extends Actor {

  //println("%s is working...".format(self))

  override def preStart = {
    super.preStart
    println("%s is working...".format(self))
  }


  def receive = {
    case Work(start, nrOfElements) =>
      sender ! Result(calculatePiFor(start, nrOfElements))
  }

  def calculatePiFor(start: Int, nrOfElements: Int): Double = {
    var acc = 0.0
    for (i ← start until (start + nrOfElements))
      acc += 4.0 * (1 - (i % 2) * 2) / (2 * i + 1)
    acc
  }
}

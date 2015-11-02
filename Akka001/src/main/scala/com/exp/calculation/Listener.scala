package com.exp.calculation

import akka.actor.Actor

/**
 * Created by gwendalmousset on 02/11/2015.
 */
class Listener extends Actor {

  def receive = {
    case PiApproximation(pi, duration) =>
      println("Pi approximation: \t\t%s\tCalculation time:\t%s".format(pi, duration))
      context.system.shutdown()
  }
}

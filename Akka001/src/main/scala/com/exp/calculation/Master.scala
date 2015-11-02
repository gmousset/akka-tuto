package com.exp.calculation

import akka.actor.{Props, Actor, ActorRef}
import akka.routing.RoundRobinRouter

import scala.concurrent.duration.Duration

/**
 * Created by gwendalmousset on 02/11/2015.
 */
class Master(nrOfWorkers: Int, nrOfMessages: Int, nrOfElements: Int, listener: ActorRef) extends Actor {

  var pi: Double = _
  var nrOfResults: Int = _
  val start: Long = System.currentTimeMillis

  val workerRouter = context.actorOf(Props[Worker].withRouter(RoundRobinRouter(nrOfWorkers)), name = "workerRouter")
  val welcomeMessage = "plouf..."

  override def preStart = {
    println("> " + welcomeMessage)
  }

  def receive = {
    case Calculate =>
      for (i <- 0 until nrOfMessages) workerRouter ! Work(i * nrOfElements, nrOfElements)
    case Result(value) =>
      pi += value
      nrOfResults += 1
      if (nrOfResults == nrOfMessages) {
        listener ! PiApproximation(pi, duration = Duration(System.currentTimeMillis() - start, "millis"))
        context.stop(self)
      }
  }
}

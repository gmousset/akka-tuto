package com.exp.calculation

import java.io.FileReader

import akka.actor.{Props, ActorSystem}
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration.Duration

/**
 * Created by gwendalmousset on 02/11/2015.
 */

sealed trait PiMessage
case object Calculate extends PiMessage
case class Work(start: Int, nrOfElement: Int) extends PiMessage
case class Result(value: Double) extends PiMessage
case class PiApproximation(pi: Double, duration: Duration)

object Pi extends App {

  calculate(nrOfWorkers = 4, nrOfElements = 10000, nrOfMessages = 10000)
/*
  val config = ConfigFactory.load()
  val tt = config.getInt("myapp1.toto.settings")
*/
  def calculate(nrOfWorkers: Int, nrOfElements: Int, nrOfMessages: Int): Unit = {
    println("nrOfWorkers = %s, nrOfElements = %s, nrOfMessages = %s".format(nrOfWorkers, nrOfElements, nrOfMessages))
    val system = ActorSystem("PiSystem")
    val listener = system.actorOf(Props[Listener], name = "listener")
    val master = system.actorOf(Props(new Master(nrOfWorkers, nrOfMessages, nrOfElements, listener)), name = "master")
    master ! Calculate
  }
}
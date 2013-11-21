/**
 *  Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
 */
package sample.remote.calculator
import scala.concurrent.duration._

/*
 * comments like //#<tag> are there for inclusion into docs, please don’t remove
 */

import akka.kernel.Bootable
import com.typesafe.config.ConfigFactory
import scala.util.Random
import akka.actor._
import akka.actor.SupervisorStrategy.{Escalate, Stop, Restart, Resume}

class CreationApplication extends Bootable {
  //#setup
  val system =
    ActorSystem("RemoteCreation", ConfigFactory.load.getConfig("remotecreation"))
  val localActor = system.actorOf(Props(classOf[CreationActor]),
    name = "creationActor")

  def doSomething(op: MathOp): Unit =
    localActor ! op
  //#setup

  def startup() {
  }

  def shutdown() {
    system.shutdown()
  }
}

//#actor
class CreationActor extends Actor {

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1.minute) {
      case e: ArithmeticException      ⇒
        println(s"Got $e from remote child")
        Resume
      case _: NullPointerException     ⇒ Restart
      case _: IllegalArgumentException ⇒ Stop
      case _: Exception                ⇒ Escalate
    }

  val remoteActor = context.actorOf(Props[AdvancedCalculatorActor],
    name = "advancedCalculator")

  def receive = {
    case op: MathOp ⇒ remoteActor ! op
    case result: MathResult ⇒ result match {
      case MultiplicationResult(n1, n2, r) ⇒
        printf("Mul result: %d * %d = %d\n", n1, n2, r)
      case DivisionResult(n1, n2, r) ⇒
        printf("Div result: %.0f / %d = %.2f\n", n1, n2, r)
    }
  }
}
//#actor

object CreationApp {
  def main(args: Array[String]) {
    val app = new CreationApplication
    println("Started Creation Application")
    while (true) {
      if (Random.nextInt(100) % 2 == 0)
        app.doSomething(Multiply(Random.nextInt(20), Random.nextInt(20)))
      else
        app.doSomething(Divide(Random.nextInt(10000), (Random.nextInt(10))))

      Thread.sleep(200)
    }
  }
}

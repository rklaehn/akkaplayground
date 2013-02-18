package rklaehn
import language.postfixOps
import scala.concurrent.duration._
import akka.actor.ActorSystem
import akka.actor.Actor
import akka.actor.Props
import akka.testkit.TestKit
import akka.testkit.ImplicitSender
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers
import org.scalatest.BeforeAndAfterAll
import akka.testkit._

object MessageDiscarderSpec {
  class EchoActor extends Actor {
    def receive = {
      case x ⇒ sender ! x
    }
  }  
}

@RunWith(classOf[JUnitRunner])
class MessageDiscarderSpec extends TestKit(ActorSystem("TimerBasedThrottlerSpec"))
  with ImplicitSender with WordSpec with MustMatchers with BeforeAndAfterAll { 
  
  override def afterAll {
    system.shutdown()
  }

  "A messagediscarder" must {
    "pass a test" in {
      
    } 
  }
}
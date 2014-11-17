
import akka.persistence._
import scala.concurrent.duration.{Duration, SECONDS}
import scala.util.{Try, Success, Failure }
import com.typesafe.config.Config
import org.apache.commons.net.ftp.FTPClient
import akka.actor.{Actor, ActorRef, Props, ActorLogging}
import scala.concurrent.duration._
import java.nio.{ByteBuffer, ByteOrder}

object PersistedActorSystem {
  def apply(ref: ActorRef = ActorRef.noSender) = {
      Props(classOf[PersistedActorSystem], ref)
  }
  
  val bytes = Array.fill(12*1024*1024)((scala.util.Random.nextInt(256) - 128).toByte)
}

class PersistedActorSystem(ref: ActorRef) 
    extends Actor with ActorLogging {
  import PersistedActorSystem._
  val channelName ="channel1"
  val channel = context.actorOf(PersistentChannel.props(
      PersistentChannelSettings(redeliverInterval = 10.seconds,
      pendingConfirmationsMax = 1, pendingConfirmationsMin = 0)),channelName)  
  val doIOActor = context.actorOf(DOIOActor())
  val MsgSender = context.actorOf(Props(classOf[MsgSender]))
  def receive = {
      case x => 
        println(s"msg -> persisted msg $x")
        channel ! Deliver(Persistent(x), doIOActor.path)
  }
}

object DOIOActor {  
  def apply() = Props(classOf[DOIOActor])
}
class DOIOActor extends Actor 
    with ActorLogging {
  import PersistedActorSystem._
  def receive = {
    case p @ ConfirmablePersistent(payload, sequenceNr, redeliveries) =>
      println("persistedmsg -> DOIOActor")
      Try(1+1)
      match 
      {
      case Success(v) =>
        println(s"will delete $p")
        p.confirm()
      case Failure(doioException) => 
        log.warning(s"Could not complete DOIO. $doioException")
        throw doioException
      }
  }
}

object MsgSender {
  def apply() = Props(classOf[MsgSender])
}
class MsgSender extends Actor with ActorLogging {
  import context.dispatcher
  import PersistedActorSystem._
  println("MsgSender started.")
  context.system.scheduler.schedule(1.second , 1.second)( context.parent ! 
      (bytes, 10.seconds.fromNow))
  def receive = Actor.emptyBehavior
}

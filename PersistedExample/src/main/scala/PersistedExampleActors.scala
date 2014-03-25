
import akka.persistence._
import scala.concurrent.duration.{Duration, SECONDS}
import scala.util.{Try, Success, Failure }
import com.typesafe.config.Config
import org.apache.commons.net.ftp.FTPClient
import akka.actor.{Actor, ActorRef, Props, ActorLogging}
import scala.concurrent.duration._

object PersistedActorSystem {
  def apply(ref: ActorRef = ActorRef.noSender) = {
      Props(classOf[PersistedActorSystem], ref)
  }
    val msg = """
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,6C*36
                    !AIVDM,1,1,,A,19NS:p@02:OkBD3qlnDLVJ<00@0W,0*6C
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,16*44
                    !AIVDM,1,1,,A,39NS8JE001Op2uB2jn2QJFp20000,0*16
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,59*4F
                    !AIVDM,1,1,,A,15Vkcj?P07PkMjSuK2=tfgv2280R,0*59
                        $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,6C*36
                    !AIVDM,1,1,,A,19NS:p@02:OkBD3qlnDLVJ<00@0W,0*6C
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,16*44
                    !AIVDM,1,1,,A,39NS8JE001Op2uB2jn2QJFp20000,0*16
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,59*4F
                    !AIVDM,1,1,,A,15Vkcj?P07PkMjSuK2=tfgv2280R,0*59
                        $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,6C*36
                    !AIVDM,1,1,,A,19NS:p@02:OkBD3qlnDLVJ<00@0W,0*6C
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,16*44
                    !AIVDM,1,1,,A,39NS8JE001Op2uB2jn2QJFp20000,0*16
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,59*4F
                    !AIVDM,1,1,,A,15Vkcj?P07PkMjSuK2=tfgv2280R,0*59
                        $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,6C*36
                    !AIVDM,1,1,,A,19NS:p@02:OkBD3qlnDLVJ<00@0W,0*6C
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,16*44
                    !AIVDM,1,1,,A,39NS8JE001Op2uB2jn2QJFp20000,0*16
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,59*4F
                    !AIVDM,1,1,,A,15Vkcj?P07PkMjSuK2=tfgv2280R,0*59
                        $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,6C*36
                    !AIVDM,1,1,,A,19NS:p@02:OkBD3qlnDLVJ<00@0W,0*6C
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,16*44
                    !AIVDM,1,1,,A,39NS8JE001Op2uB2jn2QJFp20000,0*16
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,59*4F
                    !AIVDM,1,1,,A,15Vkcj?P07PkMjSuK2=tfgv2280R,0*59
                        $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,6C*36
                    !AIVDM,1,1,,A,19NS:p@02:OkBD3qlnDLVJ<00@0W,0*6C
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,16*44
                    !AIVDM,1,1,,A,39NS8JE001Op2uB2jn2QJFp20000,0*16
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,59*4F
                    !AIVDM,1,1,,A,15Vkcj?P07PkMjSuK2=tfgv2280R,0*59
                        $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,6C*36
                    !AIVDM,1,1,,A,19NS:p@02:OkBD3qlnDLVJ<00@0W,0*6C
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,16*44
                    !AIVDM,1,1,,A,39NS8JE001Op2uB2jn2QJFp20000,0*16
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,59*4F
                    !AIVDM,1,1,,A,15Vkcj?P07PkMjSuK2=tfgv2280R,0*59
                        $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,6C*36
                    !AIVDM,1,1,,A,19NS:p@02:OkBD3qlnDLVJ<00@0W,0*6C
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,16*44
                    !AIVDM,1,1,,A,39NS8JE001Op2uB2jn2QJFp20000,0*16
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,59*4F
                    !AIVDM,1,1,,A,15Vkcj?P07PkMjSuK2=tfgv2280R,0*59
                        $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,6C*36
                    !AIVDM,1,1,,A,19NS:p@02:OkBD3qlnDLVJ<00@0W,0*6C
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,16*44
                    !AIVDM,1,1,,A,39NS8JE001Op2uB2jn2QJFp20000,0*16
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,59*4F
                    !AIVDM,1,1,,A,15Vkcj?P07PkMjSuK2=tfgv2280R,0*59
                        $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,6C*36
                    !AIVDM,1,1,,A,19NS:p@02:OkBD3qlnDLVJ<00@0W,0*6C
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,16*44
                    !AIVDM,1,1,,A,39NS8JE001Op2uB2jn2QJFp20000,0*16
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,59*4F
                    !AIVDM,1,1,,A,15Vkcj?P07PkMjSuK2=tfgv2280R,0*59
                        $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,6C*36
                    !AIVDM,1,1,,A,19NS:p@02:OkBD3qlnDLVJ<00@0W,0*6C
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,16*44
                    !AIVDM,1,1,,A,39NS8JE001Op2uB2jn2QJFp20000,0*16
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,59*4F
                    !AIVDM,1,1,,A,15Vkcj?P07PkMjSuK2=tfgv2280R,0*59
                        $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,6C*36
                    !AIVDM,1,1,,A,19NS:p@02:OkBD3qlnDLVJ<00@0W,0*6C
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,16*44
                    !AIVDM,1,1,,A,39NS8JE001Op2uB2jn2QJFp20000,0*16
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,59*4F
                    !AIVDM,1,1,,A,15Vkcj?P07PkMjSuK2=tfgv2280R,0*59
                        $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,6C*36
                    !AIVDM,1,1,,A,19NS:p@02:OkBD3qlnDLVJ<00@0W,0*6C
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,16*44
                    !AIVDM,1,1,,A,39NS8JE001Op2uB2jn2QJFp20000,0*16
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,59*4F
                    !AIVDM,1,1,,A,15Vkcj?P07PkMjSuK2=tfgv2280R,0*59
                        $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,6C*36
                    !AIVDM,1,1,,A,19NS:p@02:OkBD3qlnDLVJ<00@0W,0*6C
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,16*44
                    !AIVDM,1,1,,A,39NS8JE001Op2uB2jn2QJFp20000,0*16
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,59*4F
                    !AIVDM,1,1,,A,15Vkcj?P07PkMjSuK2=tfgv2280R,0*59
                        $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,6C*36
                    !AIVDM,1,1,,A,19NS:p@02:OkBD3qlnDLVJ<00@0W,0*6C
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,16*44
                    !AIVDM,1,1,,A,39NS8JE001Op2uB2jn2QJFp20000,0*16
                    $PGHP,1,2014,3,25,0,29,59,0,316,56,316999999,1AIS_S,59*4F
                    !AIVDM,1,1,,A,15Vkcj?P07PkMjSuK2=tfgv2280R,0*59
                    """
  val msgs = msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+
                msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+
                msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+
                msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+
                msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+
                msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+
                msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg+msg + msg
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
      (msgs.getBytes(), 10.seconds.fromNow))
  def receive = Actor.emptyBehavior
}

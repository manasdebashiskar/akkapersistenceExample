import akka.actor.ActorSystem
import akka.kernel.Bootable
import com.typesafe.config.Config
import scala.concurrent.duration._

class PersistenceExampleKernel extends Bootable {
    val system = ActorSystem("PersistenceExample")
    val config = system.settings.config
    val log = system.log
    log.info("Service successfully started")
    def startup = {
        val persistedActorSystem = system.actorOf(PersistedActorSystem(), "PersistedActorSystem")
    }
    def shutdown = system.shutdown()
}
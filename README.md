##Disclaimer
This example is **NOT** a demonstration of how to use [Akka Persistence](http://doc.akka.io/docs/akka/snapshot/scala/persistence.html) or [Persistent Channels](http://doc.akka.io/docs/akka/2.3.0/scala/persistence.html#Persistent_channels).
This example was created to show that the journal space increases over time even though the Confirmable persistence message is confirmed immediately. 
One would expect that the journal disk space would be maintained as any message that is persisted is getting confirmed (hence deleted asynchronously).

This repository contains a piece of code that reproduces this bug:

* [Mailing List](https://groups.google.com/forum/#!searchin/akka-user/akka-persistentchannel-does-not-delete-message-from-journal-upon-confirm/akka-user/_d9gpIJyKe0/B6Ie_axaFQMJ)
* [Assembla](https://www.assembla.com/spaces/akka/tickets/3962)
* [GitHub](https://github.com/akka/akka/issues/13962)

##Steps to run

* Clone this repo to your machine.
* Go to the project root.
* Run the following to create a distribution at PersistedExample/target:

	sbt dist
	
* Go to PersistedExample/target/dist/bin directory.
* Run the program by calling:

	./start PersistenceExampleKernel

* Observer the program for some time (30 minutes or more).

Notice that the number of journal files in the journal/ directory has increased, and so has the disk space used. Even though the messages are immediately confirmed the amount of disk space seems to increase without bound.

There is also a small bash included (watch_journal_files.sh) which can be run in the same directory as the 'start' program. It outputs the date, # of journal files, and disk space used by the journal files every two seconds. It outputs to a CSV file, and to stdout.

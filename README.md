Steps to run:
1) check out the code to your machine.
2) go to the project root. 
3) Run sbt dist.
	a) This will create a distribution under target folder of PersistedExample folder. 
4) go to PersistedExample/target/dist/bin folder.
5) Run '''start PersistenceExampleKernel'''

Disclaimer
This example is not a demonstration how to use Akka persistence or Persistence Channel.
On the other hand this example was created to show that the journal space increases over
time even though the Confirmable persistence message is confirmed immidiately. 
One would expect that the journal space would be maintained as any message that is 
persisted is getting confirmed(hence deleted asynchronously)
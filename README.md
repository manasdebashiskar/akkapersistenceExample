<h1> Steps to run:</h1>
<ul>
<li>1) check out the code to your machine.</li>
<li>2) go to the project root. </li>
<li>3) Run sbt dist.</li>
	<ul>
	<li>a) This will create a distribution under target folder of PersistedExample folder. </li>
	</ul>
<li>4) go to PersistedExample/target/dist/bin folder.</li>
<li>5) Run <b>start PersistenceExampleKernel</b></li>
<ul>
<h2>	
Disclaimer
</h2>
This example is not a demonstration how to use Akka persistence or Persistence Channel.
On the other hand this example was created to show that the journal space increases over
time even though the Confirmable persistence message is confirmed immidiately. 
One would expect that the journal space would be maintained as any message that is 
persisted is getting confirmed(hence deleted asynchronously)
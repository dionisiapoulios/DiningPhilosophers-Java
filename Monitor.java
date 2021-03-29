/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor
{
	/*
	 * ------------
	 * Data members
	 * ------------
	 */
	protected int numChopsticks;
	String eating="EATING";
	String hungry="HUNGRY";
	String thinking="THINKING";
	String talking="TALKING";
	String[] statePhilosopher =null;
	int nbPhilosophers;
	String[] stateChopstick = null;



	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{

		// TODO: set appropriate number of chopsticks based on the # of philosophers

		nbPhilosophers = piNumberOfPhilosophers;
		numChopsticks = piNumberOfPhilosophers;
		statePhilosopher = new String[piNumberOfPhilosophers];
		stateChopstick = new String[numChopsticks];



	}

	/*
	 * -------------------------------
	 * User-defined monitor procedures
	 * -------------------------------
	 */

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID)
	{
		statePhilosopher[piTID] = hungry;
		//check if both neighbours are NOT eating
		while(statePhilosopher[piTID]!=eating) {
			if (statePhilosopher[(piTID - 1) % nbPhilosophers] != eating && statePhilosopher[(piTID + 1) % nbPhilosophers] != eating && statePhilosopher[piTID]==hungry) {
				//start picking up the available chopsticks
				if (piTID % 2 == 0) {
					//pick up left then right
					statePhilosopher[piTID] = eating;
				} else {
					//pick up right then left
					statePhilosopher[piTID] = eating;
				}

				notifyAll();


			} else {
				//wait until both neighbours are done eating
				try {
					wait();
				} catch (InterruptedException e) {
					System.err.println("Monitor.pickUp():");
					DiningPhilosophers.reportException(e);
					System.exit(1);
				}
			}
		}

	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID)
	{
		// ...
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk()
	{
		// ...
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk()
	{
		// ...
	}
}

// EOF

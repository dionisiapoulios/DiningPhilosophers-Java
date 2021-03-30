import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

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
	ReentrantLock[] chopstickArray = null;
	ReentrantLock personTalking = null;
	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers)
	{

		// TODO: set appropriate number of chopsticks based on the # of philosophers

		nbPhilosophers = piNumberOfPhilosophers;
		numChopsticks = nbPhilosophers;
		statePhilosopher = new String[nbPhilosophers];
		chopstickArray = new ReentrantLock[numChopsticks];
		personTalking = new ReentrantLock(true);
		for(int i=0;i< numChopsticks;i++){
			chopstickArray[i] = new ReentrantLock(true);
		}


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
		// Stays in loop while he doesnt have both chopsticks
		while(!statePhilosopher[piTID].equals(eating)) {
			//check if both neighbours are NOT eating
			if (statePhilosopher[(piTID - 1 + nbPhilosophers)  % nbPhilosophers] != eating && statePhilosopher[(piTID + 1 + nbPhilosophers ) % nbPhilosophers] != eating && statePhilosopher[piTID]==hungry) {
				//start picking up the available chopsticks
				statePhilosopher[piTID] = eating;
				// If pair
				if (piTID % 2 == 0) {
					//pick up left then right
					chopstickArray[piTID].lock();
					chopstickArray[(piTID+1 + nbPhilosophers)% nbPhilosophers ].lock();


				} else { //if odd
					//pick up right then left
					chopstickArray[(piTID+1 + nbPhilosophers)% nbPhilosophers ].lock();
					chopstickArray[piTID].lock();
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
		if (piTID  % 2 == 0) {
			//put down right then left
			chopstickArray[(piTID+1 + nbPhilosophers)% nbPhilosophers ].unlock();
			chopstickArray[piTID].unlock();



		} else { //if odd
			//put down left then right
			chopstickArray[piTID].unlock();
			chopstickArray[(piTID+1 + nbPhilosophers)% nbPhilosophers ].unlock();

		}
		statePhilosopher[piTID] = thinking;

		notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk(int piTID)
	{
		personTalking.lock();
		statePhilosopher[piTID] = talking;
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk(int piTID)
	{
		statePhilosopher[piTID] = thinking;
		personTalking.unlock();

		notifyAll();
	}
}

// EOF

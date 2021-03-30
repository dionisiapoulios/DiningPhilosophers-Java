import common.BaseThread;
import java.util.Random;

import java.sql.SQLOutput;

/**
 * Class Philosopher.
 * Outlines main subrutines of our virtual philosopher.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Philosopher extends BaseThread
{
	/**
	 * Max time an action can take (in milliseconds)
	 */
	public static final long TIME_TO_WASTE = 1000;

	/**
	 * The act of eating.
	 * - Print the fact that a given phil (their TID) has started eating.
	 * - yield
	 * - Then sleep() for a random interval.
	 * - yield
	 * - The print that they are done eating.
	 */
	public void eat()
	{
		try
		{
			//PRINTING THAT A PHILOSOPHER STARTED EATING
			System.out.println("Philosopher "+iTID+" has started eating.");
			//YIELD
			Thread.yield();
			//sleep for a random interval
			sleep((long)(Math.random() * TIME_TO_WASTE));
			//YIELD
			Thread.yield();
			//PRINTING THAT A PHILOSOPHER IS DONE EATING
			System.out.println("Philosopher "+iTID+" has finished eating");
		}
		catch(InterruptedException e)
		{
			System.err.println("Philosopher.eat():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * The act of thinking.
	 * - Print the fact that a given phil (their TID) has started thinking.
	 * - yield
	 * - Then sleep() for a random interval.
	 * - yield
	 * - The print that they are done thinking.
	 */
	public void think()
	{
		try {
			//PRINTING THAT A PHILOSOPHER STARTED THINKING
			System.out.println("Philosopher " + iTID + " has started thinking.");
			//YIELD
			Thread.yield();
			//SLEEP FOR A RANDOM INTERVAL
			sleep((long) (Math.random() * TIME_TO_WASTE));
			//YIELD
			Thread.yield();
			//PRINTING THAT A PHILOSOPHER STOPPED THINKING
			System.out.println("Philosopher " + iTID + " has stopped thinking.");
		}
		catch(InterruptedException e){
			System.err.println("Philosopher.think():");
			DiningPhilosophers.reportException(e);
			System.exit(1);

			}


	}

	/**
	 * The act of talking.
	 * - Print the fact that a given phil (their TID) has started talking.
	 * - yield
	 * - Say something brilliant at random
	 * - yield
	 * - The print that they are done talking.
	 */
	public void talk()
	{
		//PRINTING THAT A PHILOSOPHER HAS STARTED TALKING
		System.out.println("Philosopher " + iTID + " has started talking.");
		//YIELD
		Thread.yield();
		//say something at random
		saySomething();
		//YIELD
		Thread.yield();
		//PRINTING THAT A PHILOSOPHER HAS FINISHED TALKING
		System.out.println("Philosopher " + iTID + " has finished talking.");

	}

	/**
	 * No, this is not the act of running, just the overridden Thread.run()
	 */
	public void run()
	{
		for(int i = 0; i < DiningPhilosophers.DINING_STEPS; i++)
		{
			DiningPhilosophers.soMonitor.pickUp(getTID() -1);

			eat();

			DiningPhilosophers.soMonitor.putDown(getTID()-1);

			think();

			/*
			 * TODO:
			 * A decision is made at random whether this particular
			 * philosopher is about to say something terribly useful.
			 */
			Random rand = new Random();
			int useful = rand.nextInt(20);
			if(useful%2 == 0)
			{
				DiningPhilosophers.soMonitor.requestTalk(getTID()-1);
				talk();
				DiningPhilosophers.soMonitor.endTalk(getTID()-1);
			}

			Thread.yield();
		}
	} // run()

	/**
	 * Prints out a phrase from the array of phrases at random.
	 * Feel free to add your own phrases.
	 */
	public void saySomething()
	{
		String[] astrPhrases =
		{
			"Eh, it's not easy to be a philosopher: eat, think, talk, eat...",
			"You know, true is false and false is true if you think of it",
			"2 + 2 = 5 for extremely large values of 2...",
			"If thee cannot speak, thee must be silent",
			"My number is " + getTID() + ""
		};

		System.out.println
		(
			"Philosopher " + getTID() + " says: " +
			astrPhrases[(int)(Math.random() * astrPhrases.length)]
		);
	}
}

// EOF

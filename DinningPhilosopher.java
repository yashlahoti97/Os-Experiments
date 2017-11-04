import java.util.*;
import java.lang.*;
import java.util.concurrent.Semaphore;

public class DinningPhilosopher {
	public static void main(String args[]) {
		String name[] = {"A","B","C","D","E"};
		Fork fork[] = new Fork[5];
		Philosopher[] philosopher = new Philosopher[5];

		for(int i=0;i<5;i++)
			fork[i] = new Fork(i);

		for(int i=0;i<5;i++) {		
			if(i!=4) {
				philosopher[i] = new Philosopher(fork[i],fork[(i+1)],name[i]);
				philosopher[i].start();
			}
			else {
				philosopher[i] = new Philosopher(fork[0],fork[i],name[i]);
				philosopher[i].start();
			}
		}
	}
}

class Fork {
	public static Semaphore fork = new Semaphore(1);
	public int id;

	Fork(int id) {
	    this.id = id;
	}

	public int getId() {
	    return id;
	}

	public void take() {
		try {
			fork.acquire();	
		}
	    catch(InterruptedException e) {}
	}

	public void putDown() {
	    fork.release();
	}
}

class Philosopher extends Thread {

	 Fork fork_l;
	 Fork fork_h;
	 String name;

	public Philosopher(Fork fork_low, Fork fork_high,String name) {
		this.fork_l = fork_low;
		this.fork_h = fork_high;
		this.name = name;
	}

	public void run() {
		try{
			System.out.println(name+" is started...");
			sleep(1000);
		}
		catch(InterruptedException e) {
			System.out.println("InterruptedException caught");
		}

		while(true) 
			eat();
	}

	public void eat() {
		System.out.println(name+" trys to eat...");
		/*if(fork_l.take()) {
			if(fork_h.take()) {
				try {
					System.out.println("***** \n"+name+" is eating... *****");
					sleep(2000);
					System.out.println(name+" is now thinking...");
				}
				catch(InterruptedException e) {
					System.out.println("InterruptedException caught");
				}
				fork_h.putDown();
				fork_l.putDown();
			}
			else {
				System.out.println(" Fails");
				fork_l.putDown();
			}
		}*/
		fork_l.take();
		System.out.println(name+" takes right fork");
		fork_h.take();
		System.out.println(name+" takes left fork");
		System.out.println("***** \n"+name+" is eating... *****");
		try {
			sleep(200);
		}
		catch(InterruptedException e) {
			System.out.println("InterruptedException caught");
		}
		System.out.println(name+" is now thinking...");
		fork_l.putDown();
		System.out.println( name+ " releases right fork.");
		fork_h.putDown();
		System.out.println( name+ " releases left fork.");
			
	}
}
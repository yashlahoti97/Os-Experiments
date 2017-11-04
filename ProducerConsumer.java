import java.util.*;
import java.lang.*;
import java.util.concurrent.Semaphore;
import java.lang.Math.*;

public class ProducerConsumer {
	public static void main(String args[]) {
		Q q = new Q();
		Producer p = new Producer(q);
		Consumer c = new Consumer(q);
		c.start();
		p.start();
	}

}

class Q {
	int item;
	static Semaphore semCon = new Semaphore(0);
	static Semaphore semPro = new Semaphore(1);

	void get() {
		try{
			semCon.acquire();
		}
		catch(InterruptedException e) {
			System.out.println("InterruptedException Caught");
		}
		System.out.println("Consumer consumed item - "+item);
		semPro.release();
	}

	void put(int item) {
		try{
			semPro.acquire();
		}
		catch(InterruptedException e) {
			System.out.println("InterruptedException Caught");
		}
		this.item = item;
		System.out.println("Producer produced item - "+item);
		semCon.release();
	}

}

class Producer extends Thread {
	Q q;

	Producer(Q q) {
		this.q = q;
	}

	public void run() {
		try {
			for(int i=0;i<5;i++) {
				q.put(i);
				this.sleep((int)(Math.random()*5000));
			}	
		}
		catch(InterruptedException e) {}
		
	}
}

class Consumer extends Thread {
	Q q;

	Consumer(Q q) {
		this.q = q;
	}

	public void run() {
		try {
			for(int i=0;i<5;i++) {
				q.get();	
				this.sleep((int)(Math.random()*5000));
			}
		}
		catch(InterruptedException e) {}
	}
}
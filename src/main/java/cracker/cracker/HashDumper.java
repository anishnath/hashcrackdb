package cracker.cracker;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import pwned.PwnedConsumer;
import pwned.PwnedProducer;

public class HashDumper {

	public static void main(String[] args) {

		
		String inputFile = "/tmp/1000.txt";
		if (args.length >=1) {
			// fileName=args[0];
			inputFile = args[0];
		}

		
		System.out.println("inputFile Name: " + inputFile);

		BlockingQueue queue = new ArrayBlockingQueue(32768);
		

		PwnedProducer producer = new PwnedProducer(queue, inputFile);
		PwnedConsumer consumer = new PwnedConsumer(queue);

		new Thread(producer).start();
		new Thread(consumer).start();

		// Thread.sleep(4000);
	}
}

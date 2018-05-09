package cracker.cracker;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class HashCreator {

	public static void main(String[] args) throws Exception {
		
		String  fileName="/tmp/HashCreated_"+System.currentTimeMillis() + ".txt";
		String inputFile="/tmp/f3.txt";
		if(args.length>1)
		{
			//fileName=args[0];
			inputFile = args[0];
		}
		
		

		
		System.out.println("File Name: "+fileName); 
		System.out.println("inputFile Name: "+inputFile); 
		
		BlockingQueue queue = new ArrayBlockingQueue(32768);
		BlockingQueue queue1 = new ArrayBlockingQueue(32768);

		Producer producer = new Producer(queue,inputFile);
		FileWriter fileWriter = new FileWriter(queue1, fileName);
		SHAConsumer consumer = new SHAConsumer(queue,fileWriter);
		

		new Thread(producer).start();
		new Thread(consumer).start();
		new Thread(fileWriter).start();

		//Thread.sleep(4000);
	}
}

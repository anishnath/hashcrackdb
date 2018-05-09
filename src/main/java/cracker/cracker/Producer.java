package cracker.cracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

	protected BlockingQueue queue = null;
	private String fileName;
	
	public static long x = 0;

	public Producer(BlockingQueue queue,String filename) {
		this.queue = queue;
		this.fileName=filename;
	}

	public void run() {
		try {

			System.out.println("Lets Read Again --<<<>>>");
			long startTime = System.currentTimeMillis();

			FileInputStream input = new FileInputStream(new File(this.fileName));
			// CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
			// decoder.onMalformedInput(CodingErrorAction.IGNORE);
			// InputStreamReader reader = new InputStreamReader(input, decoder);
			InputStreamReader reader = new InputStreamReader(input);
			BufferedReader bufferedReader = new BufferedReader(reader);
			// StringBuilder sb = new StringBuilder();
			String line = bufferedReader.readLine();
			while (line != null) {

				try {
					x++;
					line = bufferedReader.readLine();
					// System.out.println(line);
					if (line != null) {
						// System.out.println(line);
						queue.put(line);
					}

				} catch (Exception e) {
					System.out.println("Exception @ " + x);
					e.printStackTrace();
					SHAConsumer.isRunning = false;
					FileWriter.isRunning = false;
					queue.put("495980d1ebd25d66d0ee1bf33eccdb0d");
				}

			}
			bufferedReader.close();

			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;

			
			while (true) {
				//System.out.println(FileWriter.x + " " + x );
				if (FileWriter.x+1 >= x) {
					System.out.println(FileWriter.x + "---" + x );
					queue.put("495980d1ebd25d66d0ee1bf33eccdb0d");
					FileWriter.isRunning = false;
					SHAConsumer.isRunning = false;
					break;
				}
			}
			System.out.println("Total time " + elapsedTime);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

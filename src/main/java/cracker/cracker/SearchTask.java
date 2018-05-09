package cracker.cracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//public class SearchTask {
public class SearchTask implements Runnable {

	private static volatile boolean running = true;

	// public class SearchTask implements Callable<Integer> {
	private int localCounter = 0;
	private int start; // start index of search
	private int end;
	private List<String> words;
	private String token;

	public SearchTask(int start, int end, List<String> words, String token) {
		this.start = start;
		this.end = end;
		this.words = words;
		this.token = token;
	}

	public void run() {
		// System.out.println("running --> " + running);

		for (int i = start; i < end; i++) {

			if (!running) {
				break;
			}

			// System.out.println(Thread.currentThread().getName() + running);
			if (words.get(i) != null) {
				if (words.get(i).equals(token)) {
					System.out.println("Match Found");
					running = false;
					localCounter++;
					break;

				}
			}
		}
	}

	public int getCounter() {
		return localCounter;
	}

	public static void main(String[] args) throws Exception {

		List<String> words = new ArrayList<String>();

		// zzzzus

		FileInputStream input = new FileInputStream(new File("/tmp/fileaaae"));
		CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
		decoder.onMalformedInput(CodingErrorAction.IGNORE);
		InputStreamReader reader = new InputStreamReader(input, decoder);
		BufferedReader bufferedReader = new BufferedReader(reader);
		// StringBuilder sb = new StringBuilder();
		String line = bufferedReader.readLine();
		long x = 0;
		while (line != null) {
			x++;
			try {
				line = bufferedReader.readLine();
				words.add(line);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		bufferedReader.close();

		int y =words.size() / 3;

		int round1 = y + y;
		int round2 = round1 + y;

		String find = "zzzzuk";
		SearchTask task1 = new SearchTask(0, y, words, find);
		SearchTask task2 = new SearchTask(y, round1, words, find);
		SearchTask task3 = new SearchTask(round1, words.size(), words, find);

		// create threads
		Thread t1 = new Thread(task1, "A");
		Thread t2 = new Thread(task2, "B");
		Thread t3 = new Thread(task3, "C");

		// start threads
		t1.start();
		t2.start();
		t3.start();

		// wait for threads to finish

		boolean isrn = true;

		int counter = 0;

		t1.join();
		t2.join();
		t3.join();

		// collect results

		counter += task1.getCounter();
		counter += task2.getCounter();
		counter += task3.getCounter();
		System.out.println("counter" + counter);

	}

}

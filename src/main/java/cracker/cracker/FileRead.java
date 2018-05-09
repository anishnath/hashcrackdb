package cracker.cracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileRead {

	

	public  class Worker implements Runnable {
		private final String line;

		public Worker(String line) {
			this.line = line;
		}

		public void run() {
			// Process line here.
			 //System.out.println("Processing line: " + line);

			if ("龟鳖目".equals(line)) {
				System.out.println("Found  " +true);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		
		final  ExecutorService service = Executors.newFixedThreadPool(10);

		// Create worker thread pool.

		long startTime = System.currentTimeMillis();

		// LineIterator it = FileUtils.lineIterator(new File("/tmp/f3.txt"),
		// "UTF-8");
		//
		// try {
		// while (it.hasNext()) {
		// String line = it.nextLine();
		//
		// if ("?龥״".equals(line)) {
		// System.out.println(true);
		// }
		// // service.execute(new Worker(line));
		// }
		// } finally {
		// LineIterator.closeQuietly(it);
		// }

//		 String thisLine = null;
//		
//		 BufferedReader br = new BufferedReader(new
//		 FileReader("/tmp/f3.txt"));
//		
//		 while ((thisLine = br.readLine()) != null) {
//		 // service.execute(new Worker(thisLine));
//		 }

		// Path path = Paths.get("/tmp/", "f3.txt");
		// SeekableByteChannel sbc = Files.newByteChannel(path,
		// StandardOpenOption.READ);
		// ByteBuffer bf = ByteBuffer.allocate(941);// line size
		// long k =0;
		// int i = 0;
		// while ((i = sbc.read(bf)) > 0) {
		// bf.flip();
		// // service.execute(new Worker(new String(bf.array())));
		// bf.clear();
		// }

		// System.out.println("Scanner Starts ");
		// Scanner scan = new Scanner(new
		// File("/home/anish/Downloads/crackstation.txt"));
		// while (scan.hasNextLine()) {
		// String line = scan.nextLine();
		// //System.out.println(line);
		// }
		//
		// System.out.println("Scanner ENds ");

		String fileName = "/tmp/10000.txt";
		String p="龟鳖目";
		System.err.println("龟鳖目".equals(p));
		
		long x=0;
		
		System.out.println("Lets Read Again --");
		
		FileInputStream input = new FileInputStream(new File("/tmp/f3.txt"));
        CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
        decoder.onMalformedInput(CodingErrorAction.IGNORE);
        InputStreamReader reader = new InputStreamReader(input, decoder);
        BufferedReader bufferedReader = new BufferedReader( reader );
        //StringBuilder sb = new StringBuilder();
        String line = bufferedReader.readLine();
        
        while( line != null ) {
        	
        	x++;
        	
            
            try {
				line = bufferedReader.readLine();
				//service.execute(new Worker(line));
				
         // System.out.println(line);
			} catch (Exception e) {
				System.out.println("Exception @ "+ x);
			}
            
        }
        bufferedReader.close();
       
		
		

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
		System.out.println(elapsedTime);

		service.shutdown();
		try {
			service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}

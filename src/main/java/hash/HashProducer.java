package hash;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.concurrent.BlockingQueue;

import javax.management.MBeanServer;
import javax.management.ObjectName;

import jmx.FileProcessor;

import java.util.concurrent.BlockingQueue;

public class HashProducer implements Runnable {

	protected BlockingQueue sha1 = null;
	protected BlockingQueue sha224 = null;
	protected BlockingQueue sha256 = null;
	protected BlockingQueue sha384 = null;
	protected BlockingQueue sha512 = null;
	protected BlockingQueue tiger = null;
	protected BlockingQueue whirpool = null;
	protected BlockingQueue md5 = null;
	protected BlockingQueue md2 = null;

	protected BlockingQueue ripemd160 = null;
	protected BlockingQueue ripemd256 = null;
	protected BlockingQueue ripemd320 = null;
	protected BlockingQueue blake2b160 = null;
	protected BlockingQueue blake2b256 = null;
	protected BlockingQueue blake2b384 = null;
	protected BlockingQueue blake2b512 = null;
	protected BlockingQueue sm3 = null;
	protected BlockingQueue gost3411 = null;
	protected BlockingQueue gost34112012256 = null;
	protected BlockingQueue sha3224 = null;
	protected BlockingQueue sha3256 = null;
	protected BlockingQueue ntlm = null;
	private String fileName;
	public static long x = 0;

	public HashProducer(BlockingQueue sha1, BlockingQueue sha224, BlockingQueue sha256, BlockingQueue sha384,
			BlockingQueue sha512, BlockingQueue tiger, BlockingQueue whirpool, BlockingQueue md5, BlockingQueue md2,
			BlockingQueue ripemd128, BlockingQueue ripemd160, BlockingQueue ripemd256, BlockingQueue blake2b160,
			BlockingQueue blake2b256, BlockingQueue blake2b384, BlockingQueue blake2b512, BlockingQueue sm3,
			BlockingQueue gost3411, BlockingQueue gost34112012256, BlockingQueue sha3224, BlockingQueue sha3256,
			BlockingQueue ntlm, String fileName) {
		super();
		this.sha1 = sha1;
		this.sha224 = sha224;
		this.sha256 = sha256;
		this.sha384 = sha384;
		this.sha512 = sha512;
		this.tiger = tiger;
		this.whirpool = whirpool;
		this.md5 = md5;
		this.md2 = md2;
		this.ripemd160 = ripemd160;
		this.ripemd256 = ripemd256;
		this.ripemd320 = ripemd128;

		this.blake2b160 = blake2b160;
		this.blake2b256 = blake2b256;
		this.blake2b384 = blake2b384;
		this.blake2b512 = blake2b512;
		this.sm3 = sm3;
		this.gost3411 = gost3411;
		this.gost34112012256 = gost34112012256;
		this.sha3224 = sha3224;
		this.sha3256 = sha3256;
		this.ntlm = ntlm;
		this.fileName = fileName;
	}

	public void run() {
		try {

			System.out.println("Lets Read Again --<<<>>> HashProducer");
			long startTime = System.currentTimeMillis();
			
			MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
			ObjectName name = new ObjectName("hash.mbeans:type=FileProcessor");
			FileProcessor fileProcessor = new  FileProcessor();
			mbs.registerMBean(fileProcessor, name);


			File folder = new File(this.fileName);
			File[] listOfFiles = folder.listFiles();
			for (int i = 0; i < listOfFiles.length; i++) {

				if (listOfFiles[i].isFile()) {
					
					String fName = listOfFiles[i].getName();
					
					System.out.println("Processing file " + fName);

					FileInputStream input = new FileInputStream(this.fileName+"/"+fName);
					// CharsetDecoder decoder =
					// Charset.forName("UTF-8").newDecoder();
					// decoder.onMalformedInput(CodingErrorAction.IGNORE);
					// InputStreamReader reader = new InputStreamReader(input,
					// decoder);
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
								fileProcessor.setCacheSize(x);
								fileProcessor.displayWord(line);
								sha1.put(line);
								sha224.put(line);
								sha256.put(line);
								sha384.put(line);
								sha512.put(line);
								tiger.put(line);
								whirpool.put(line);
								md5.put(line);
								md2.put(line);

//								ripemd160.put(line);
//								ripemd256.put(line);
//								ripemd320.put(line);
//								blake2b160.put(line);
//								blake2b256.put(line);
//								blake2b384.put(line);
//								blake2b512.put(line);
//								sm3.put(line);
								// gost3411.put(line);
								// gost34112012256.put(line);
								sha3224.put(line);
								sha3256.put(line);
								ntlm.put(line);
							}

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					bufferedReader.close();
				}

			}

			long stopTime = System.currentTimeMillis();
			long elapsedTime = stopTime - startTime;

			System.out.println("Total time " + elapsedTime);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

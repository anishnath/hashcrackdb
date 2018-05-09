package hash;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Transaction;

public class BDBReader {

	private static Database sha1 = null;
	private static Database md5 = null;
	private static Database sha512 = null;
	private static Database sha256 = null;
	
	private static Database ripemd160 = null;
	private static Database ripemd256 = null;
	private static Database ripemd320 = null;
	private static Database sm3 = null;
	private static Database tiger = null;
	private static Database whirlpool = null;
	
	private static Database blake2b160=null;
	private static Database blake2b256=null;
	private static Database blake2b384=null;
	private static Database blake2b512=null;
	
	private static Database ntlm=null;
	
	private static Database sha2_512_224=null;
	private static Database sha2_512_256=null;
	

	public static void main(String[] args) throws Exception {

		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setTransactional(false);
		envConfig.setAllowCreate(false);
		envConfig.setReadOnly(false);

		Environment exampleEnv = new Environment(new File("/tmp/"), envConfig);

		// Transaction txn = exampleEnv.beginTransaction(null, null);
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setTransactional(false);
		dbConfig.setAllowCreate(false);
		dbConfig.setSortedDuplicates(true);

		List ls = exampleEnv.getDatabaseNames();

		for (Iterator iterator = ls.iterator(); iterator.hasNext();) {
			String object = (String) iterator.next();
			System.out.println("Database Name -- " + object);

		}

		sha1 = exampleEnv.openDatabase(null, "sha1", dbConfig);
		md5 = exampleEnv.openDatabase(null, "md5", dbConfig);
		sha512 = exampleEnv.openDatabase(null, "sha512", dbConfig);
		sha256 = exampleEnv.openDatabase(null, "sha256", dbConfig);
		
		ripemd160 = exampleEnv.openDatabase(null, "ripemd160", dbConfig);
		ripemd256 = exampleEnv.openDatabase(null, "ripemd256", dbConfig);
		ripemd320 = exampleEnv.openDatabase(null, "ripemd320", dbConfig);
		
		sm3 = exampleEnv.openDatabase(null, "sm3", dbConfig);
		tiger = exampleEnv.openDatabase(null, "tiger", dbConfig);
		whirlpool = exampleEnv.openDatabase(null, "whirpool", dbConfig);
		
		
		blake2b160 = exampleEnv.openDatabase(null, "blake2b160", dbConfig);
		blake2b256 = exampleEnv.openDatabase(null, "blake2b256", dbConfig);
		blake2b384 = exampleEnv.openDatabase(null, "blake2b384", dbConfig);
		blake2b512 = exampleEnv.openDatabase(null, "blake2b512", dbConfig);
		
		ntlm = exampleEnv.openDatabase(null, "ntlm", dbConfig);
		
		sha2_512_224 = exampleEnv.openDatabase(null, "sha2_512_224", dbConfig);
		sha2_512_256 = exampleEnv.openDatabase(null, "sha2_512_256", dbConfig);
		


		Cursor cursor = md5.openCursor(null, null);

		DatabaseEntry foundKey = new DatabaseEntry();
		DatabaseEntry foundData = new DatabaseEntry();

//		while (cursor.getNext(foundKey, foundData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
//			// getData() on the DatabaseEntry objects returns the byte array
//			// held by that object. We use this to get a String value. If the
//			// DatabaseEntry held a byte array representation of some other data
//			// type (such as a complex object) then this operation would look
//			// considerably different.
//			String keyString = new String(foundKey.getData());
//			String dataString = new String(foundData.getData());
//
//			
//				System.out.println("Key | Data : " + keyString.toLowerCase() + " | " + dataString + "");
//			
//		}
		//
		//
		// cursor=sha1.openCursor(null, null);

		// while (cursor.getNext(foundKey, foundData, LockMode.DEFAULT) ==
		// OperationStatus.SUCCESS) {
		// // getData() on the DatabaseEntry objects returns the byte array
		// // held by that object. We use this to get a String value. If the
		// // DatabaseEntry held a byte array representation of some other data
		// // type (such as a complex object) then this operation would look
		// // considerably different.
		// String keyString = new String(foundKey.getData());
		// String dataString = new String(foundData.getData());
		// System.out.println("Key | Data : " + keyString + " | " +
		// dataString + "");
		// }

		System.out.println("Lets Read Again --<<<>>> BDBReader");
		long startTime = System.currentTimeMillis();

		FileInputStream input = new FileInputStream(new File("/tmp/hash.txt"));
		// CharsetDecoder decoder = Charset.forName("UTF-8").newDecoder();
		// decoder.onMalformedInput(CodingErrorAction.IGNORE);
		// InputStreamReader reader = new InputStreamReader(input, decoder);
		InputStreamReader reader = new InputStreamReader(input);
		BufferedReader bufferedReader = new BufferedReader(reader);
		// StringBuilder sb = new StringBuilder();
		String line = bufferedReader.readLine();
		while (line != null) {

			try {

				line = bufferedReader.readLine();
				if (line != null) {
					line = line.toUpperCase();
					String x = getValue(line);
					if (x != null) {
						System.out.println("X--." + x);
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		bufferedReader.close();

		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;

		System.out.println("Total time " + elapsedTime);

	}

	public static String getValue( String hashValue) {
		try {
			
			hashValue = hashValue.trim().toUpperCase();
			
			
			DatabaseEntry theKey = new DatabaseEntry(hashValue.getBytes("UTF-8"));
			DatabaseEntry theData = new DatabaseEntry();
			StringBuilder builder = new StringBuilder();

			if (md5.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {

				byte[] retData = theData.getData();
				return getFormattedData(retData, "MD5");
			}
			if (sha1.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData, "SHA-1");
			}
			
			if (sha512.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData, "SHA-512");
			}
			
			if (sha256.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData, "SHA-256");
			}
			
			if (ripemd256.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"RIPEMD-256");
			}
			
			if (ripemd160.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"RIPEMD-160");
			}
			
			if (ripemd320.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"RIPEMD-320");
			}
			
			
			if (sm3.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"SM3");
			}
			
			if (tiger.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"TIGER");
			}
			
			if (whirlpool.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"WHIRLPOOL");
			}
			
			if (blake2b160.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"blake2b160");
			}
			if (blake2b256.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"blake2b256");
			}
			if (blake2b384.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"blake2b384");
			}
			if (blake2b512.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"blake2b512");
			}
			
			if (ntlm.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"NTLM");
			}
			
			if (sha2_512_224.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"sha2_512_224");
			}
			
			if (sha2_512_256.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"sha2_512_256");
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}

	static String getFormattedData(byte[] retData, String algo) {
		StringBuilder builder = new StringBuilder();
		builder.append(new String(retData));
		builder.append(",");
		builder.append(algo);
		return builder.toString();
	}

}

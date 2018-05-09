/*-
 * Copyright (C) 2002, 2017, Oracle and/or its affiliates. All rights reserved.
 *
 * This file was distributed by Oracle as part of a version of Oracle Berkeley
 * DB Java Edition made available at:
 *
 * http://www.oracle.com/technetwork/database/database-technologies/berkeleydb/downloads/index.html
 *
 * Please see the LICENSE file included in the top-level directory of the
 * appropriate version of Oracle Berkeley DB Java Edition for a copy of the
 * license and additional information.
 */

package bdb;

import java.io.File;
import java.io.UnsupportedEncodingException;

import com.sleepycat.bind.tuple.IntegerBinding;
import com.sleepycat.je.Cursor;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
import com.sleepycat.je.Transaction;

/**
 * SimpleExample creates a database environment, a database, and a database
 * cursor, inserts and retrieves data.
 */
public class SimpleExample {
	private static final int EXIT_SUCCESS = 0;
	private static final int EXIT_FAILURE = 1;

	private int numRecords; // num records to insert or retrieve
	private int offset; // where we want to start inserting
	private boolean doInsert; // if true, insert, else retrieve
	private File envDir;

	private Database sha1 = null;
	private Database sha224 = null;
	private Database sha256 = null;
	private Database sha384 = null;
	private Database sha512 = null;
	private Database tiger = null;
	private Database whirpool = null;
	private Database md5 = null;
	private Database md2 = null;
	private Database ripemd256 = null;
	private Database ripemd160 = null;
	private Database ripemd320 = null;
	private Database blake2b160 = null;
	private Database blake2b256 = null;
	private Database blake2b384 = null;
	private Database blake2b512 = null;
	private Database sm3 = null;
	private Database gost3411 = null;
	private Database gost34112012256 = null;
	private Database sha3224 = null;
	private Database sha3256 = null;
	private Database ntlm = null;

	private EnvironmentConfig envConfig = new EnvironmentConfig();
	private Environment exampleEnv = null;
	
	public Database getSha1() {
		return sha1;
	}


	public Database getSha224() {
		return sha224;
	}

	public Database getSha256() {
		return sha256;
	}

	public Database getSha384() {
		return sha384;
	}

	public Database getSha512() {
		return sha512;
	}

	public Database getTiger() {
		return tiger;
	}

	public Database getWhirpool() {
		return whirpool;
	}

	public Database getMd5() {
		return md5;
	}

	public Database getMd2() {
		return md2;
	}

	public Database getRipemd256() {
		return ripemd256;
	}

	public Database getRipemd160() {
		return ripemd160;
	}

	public Database getRipemd320() {
		return ripemd320;
	}

	public Database getBlake2b160() {
		return blake2b160;
	}

	public Database getBlake2b256() {
		return blake2b256;
	}

	public Database getBlake2b384() {
		return blake2b384;
	}

	public Database getBlake2b512() {
		return blake2b512;
	}

	public Database getSm3() {
		return sm3;
	}

	public Database getGost3411() {
		return gost3411;
	}

	public Database getGost34112012256() {
		return gost34112012256;
	}

	public Database getSha3224() {
		return sha3224;
	}

	public Database getSha3256() {
		return sha3256;
	}

	public Database getNtlm() {
		return ntlm;
	}

	public SimpleExample(File envDir) {
		try {
			this.envDir = envDir;

			envConfig.setTransactional(true);
			envConfig.setAllowCreate(true);
			exampleEnv = new Environment(envDir, envConfig);

			// Transaction txn = exampleEnv.beginTransaction(null, null);
			
			Transaction txn =  null;
			DatabaseConfig dbConfig = new DatabaseConfig();
			dbConfig.setTransactional(true);
			dbConfig.setAllowCreate(true);
			dbConfig.setSortedDuplicates(true);

			sha1 = exampleEnv.openDatabase(txn, "sha1", dbConfig);
			sha224 = exampleEnv.openDatabase(txn, "sha224", dbConfig);
			sha256 = exampleEnv.openDatabase(txn, "sha256", dbConfig);
			sha384 = exampleEnv.openDatabase(txn, "sha384", dbConfig);
			sha512 = exampleEnv.openDatabase(txn, "sha512", dbConfig);
			tiger = exampleEnv.openDatabase(txn, "tiger", dbConfig);
			whirpool = exampleEnv.openDatabase(txn, "whirpool", dbConfig);
			md5 = exampleEnv.openDatabase(txn, "md5", dbConfig);
			md2 = exampleEnv.openDatabase(txn, "md2", dbConfig);
			ripemd256 = exampleEnv.openDatabase(txn, "ripemd256", dbConfig);
			ripemd160 = exampleEnv.openDatabase(txn, "ripemd160", dbConfig);
			ripemd320 = exampleEnv.openDatabase(txn, "ripemd320", dbConfig);
			blake2b160 = exampleEnv.openDatabase(txn, "blake2b160", dbConfig);
			blake2b256 = exampleEnv.openDatabase(txn, "blake2b256", dbConfig);
			blake2b384 = exampleEnv.openDatabase(txn, "blake2b384", dbConfig);
			blake2b512 = exampleEnv.openDatabase(txn, "blake2b512", dbConfig);
			sm3 = exampleEnv.openDatabase(txn, "sm3", dbConfig);
			gost3411 = exampleEnv.openDatabase(txn, "gost3411", dbConfig);
			gost34112012256 = exampleEnv.openDatabase(txn, "gost34112012256", dbConfig);
			sha3224 = exampleEnv.openDatabase(txn, "sha3224", dbConfig);
			sha3256 = exampleEnv.openDatabase(txn, "sha3256", dbConfig);
			ntlm = exampleEnv.openDatabase(txn, "ntlm", dbConfig);
			
			Runtime.getRuntime().addShutdownHook(new Thread()
		    {
		      public void run()
		      {
		        closeAll();
		      }
		    });

		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}

	}

	public void closeAll() {
		System.out.println("Inside shutdown Hook ");
		try {
			getBlake2b160().close();
			getBlake2b256().close();
			getBlake2b384().close();
			getBlake2b512().close();

			getGost3411().close();
			getGost34112012256().close();

			getMd2().close();
			getMd5().close();

			getNtlm().close();

			getRipemd256().close();
			getRipemd160().close();
			getRipemd320().close();

			getSha1().close();
			getSha224().close();
			getSha256().close();
			getSha3224().close();
			getSha3256().close();
			getSha384().close();
			getSha512().close();
			getSm3().close();
			getTiger().close();
			getWhirpool().close();
			exampleEnv.close();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getValue(final String hashValue) {
		try {
			DatabaseEntry theKey = new DatabaseEntry(hashValue.getBytes("UTF-8"));
			DatabaseEntry theData = new DatabaseEntry();
			StringBuilder builder = new StringBuilder();
			
			if (getMd5().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {

				byte[] retData = theData.getData();
				return getFormattedData(retData,"MD5");
			}
			
			if (getMd2().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {

				byte[] retData = theData.getData();
				return getFormattedData(retData,"MD2");
			}
			
			if (getSha1().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"SHA-1");
			}
			
			if (getSha224().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"SHA-224");
			}
			
			if (getSha256().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"SHA-256");
			}
			
			if (getSha384().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"SHA3-384");
			}
			
			if (getSha512().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"SHA-512");
			}
			
			if (getSha3224().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"SHA3-224");
			}
			
			if (getSha3256().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"SHA3-256");
			}
			
			if (getSha3256().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"SHA3-256");
			}
			
			if (getTiger().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"TIGER");
			}
			
			if (getWhirpool().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"WHIRPOOL");
			}
			
			if (getSm3().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"SM3");
			}
			
			if (getRipemd256().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"RIPEMD-128");
			}
			
			if (getRipemd160().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"RIPEMD-160");
			}
			
			if (getRipemd320().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"RIPEMD-320");
			}
			
			if (getBlake2b160().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"BLAKE2b-160");
			}
			
			if (getBlake2b256().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"BLAKE2b-256");
			}
			
			if (getBlake2b384().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"BLAKE2b-384");
			}
			
			if (getBlake2b512().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"BLAKE2b-512");
			}
			
			if (getGost3411().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"GOST3411");
			}
			
			if (getGost34112012256().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"GOST34112012256");
			}
			
			if (getNtlm().get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				byte[] retData = theData.getData();
				return getFormattedData(retData,"GOST34112012256");
			}
	
			
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}


	private String getFormattedData(byte[] retData,String algo) {
		StringBuilder builder = new StringBuilder();
		builder.append(new String(retData));
		builder.append(",");
		builder.append(algo);
		return builder.toString();
	}

	public SimpleExample(int numRecords, boolean doInsert, File envDir, int offset) {
		this.numRecords = numRecords;
		this.doInsert = doInsert;
		this.envDir = envDir;
		this.offset = offset;
	}

	/**
	 * Usage string
	 */
	public static void usage() {
		System.out.println("usage: java " + "je.SimpleExample " + "<dbEnvHomeDirectory> "
				+ "<insert|retrieve> <numRecords> [offset]");
		System.exit(EXIT_FAILURE);
	}

	/**
	 * Main
	 */
	public static void main(String argv[]) {
		
		SimpleExample example =  new SimpleExample(new File("/tmp"));
		
	}

	/**
	 * Insert or retrieve data
	 */
	public void run() throws DatabaseException {
		/* Create a new, transactional database environment */
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setTransactional(true);
		envConfig.setAllowCreate(true);
		Environment exampleEnv = new Environment(envDir, envConfig);

		/*
		 * Make a database within that environment
		 *
		 * Notice that we use an explicit transaction to perform this database
		 * open, and that we immediately commit the transaction once the
		 * database is opened. This is required if we want transactional support
		 * for the database. However, we could have used autocommit to perform
		 * the same thing by simply passing a null txn handle to openDatabase().
		 */
		Transaction txn = exampleEnv.beginTransaction(null, null);
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setTransactional(true);
		dbConfig.setAllowCreate(true);
		dbConfig.setSortedDuplicates(false);
		Database sha1db = exampleEnv.openDatabase(txn, "sha1", dbConfig);

		txn.commit();

		/*
		 * Insert or retrieve data. In our example, database records are integer
		 * pairs.
		 */

		/* DatabaseEntry represents the key and data of each record */
		DatabaseEntry keyEntry = new DatabaseEntry();
		DatabaseEntry dataEntry = new DatabaseEntry();

		if (doInsert) {

			/* put some data in */
			for (int i = offset; i < numRecords + offset; i++) {
				/*
				 * Note that autocommit mode, described in the Getting Started
				 * Guide, is an alternative to explicitly creating the
				 * transaction object.
				 */
				txn = exampleEnv.beginTransaction(null, null);

				/* Use a binding to convert the int into a DatabaseEntry. */

				IntegerBinding.intToEntry(i, keyEntry);
				IntegerBinding.intToEntry(i + 1, dataEntry);
				OperationStatus status = sha1db.put(txn, keyEntry, dataEntry);

				/*
				 * Note that put will throw a DatabaseException when error
				 * conditions are found such as deadlock. However, the status
				 * return conveys a variety of information. For example, the put
				 * might succeed, or it might not succeed if the record alread
				 * exists and the database was not configured for duplicate
				 * records.
				 */
				if (status != OperationStatus.SUCCESS) {
					throw new RuntimeException("Data insertion got status " + status);
				}
				txn.commit();
			}
		} else {
			/* retrieve the data */
			Cursor cursor = sha1db.openCursor(null, null);

			while (cursor.getNext(keyEntry, dataEntry, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				System.out.println(
						"key=" + IntegerBinding.entryToInt(keyEntry) + " data=" + IntegerBinding.entryToInt(dataEntry));

			}
			cursor.close();
		}

		sha1db.close();

		exampleEnv.close();

	}
}

package hash;

import java.security.MessageDigest;
import java.util.concurrent.BlockingQueue;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseEntry;

import cracker.cracker.SHAConsumer;

public class MD5Consumer implements Runnable {

	protected BlockingQueue queue = null;
	protected Database db;

	public MD5Consumer(BlockingQueue queue, Database md5database) {
		this.queue = queue;
		this.db = md5database;
	}

	@Override
	public void run() {

		try {
			MessageDigest crypt = MessageDigest.getInstance("MD5");
			while (true) {
				String s = (String) queue.take();
				crypt.reset();
				crypt.update(s.getBytes("UTF-8"));
				String hexValue = SHAConsumer.byteToHex(crypt.digest());
				//System.out.println("MD5 Consumer -- " + hexValue);
				DatabaseEntry theKey = new DatabaseEntry(hexValue.toUpperCase().getBytes("UTF-8"));
				DatabaseEntry theData = new DatabaseEntry(s.getBytes("UTF-8"));
				db.put(null, theKey, theData);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
